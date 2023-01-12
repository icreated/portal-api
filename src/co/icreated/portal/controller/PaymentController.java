package co.icreated.portal.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

import javax.validation.Valid;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.PaymentsApi;
import co.icreated.portal.model.CreditCardDto;
import co.icreated.portal.model.OpenItemDto;
import co.icreated.portal.model.PaymentDto;
import co.icreated.portal.security.Authenticated;
import co.icreated.portal.service.InvoiceService;
import co.icreated.portal.service.PaymentService;

@RestController
public class PaymentController implements PaymentsApi, Authenticated {

  CLogger log = CLogger.getCLogger(PaymentController.class);

  PaymentService paymentService;
  InvoiceService invoiceService;

  public PaymentController(PaymentService paymentService, InvoiceService invoiceService) {
    this.paymentService = paymentService;
    this.invoiceService = invoiceService;
  }

  /**
   * Get payment List
   *
   * @param sessionUser
   * @return
   */
  public ResponseEntity<List<PaymentDto>> getPayments() {

    List<PaymentDto> payments =
        paymentService.findBPartnerPayments(getSessionUser().getPartnerId());
    return ResponseEntity.ok(payments);
  }


  /**
   * Get Credit Card Payment Here dont't forget to add @Valid before @RequestBody with corresponding
   * annotations in CreditCardDto to validate credit card fields with javax.validation. (You have to
   * add validation.jar to classpath for it)
   *
   * @param sessionUser
   * @param creditCard
   */
  @Override
  public ResponseEntity<Void> createPayment(@Valid CreditCardDto creditCardDto) {

    List<OpenItemDto> openItems = invoiceService.findOpenItems(getSessionUser().getPartnerId());
    BigDecimal openTotal = openItems.stream() //
        .map(OpenItemDto::getOpenAmt) //
        .reduce(Env.ZERO, (subtotal, value) -> subtotal.add(value));

    boolean isTotalEqual = openTotal.compareTo(creditCardDto.getPaymentAmount()) == 0;
    if (!isTotalEqual) {
      throw new AdempiereException("Totals from backend & frontend not are equal");
    }

    // TODO It's a good idea to put this Idempiere Trx deal to AOP Spring Annotation @Around
    // For instance it's a unique case, but later it's a "must be"
    String trxName = Trx.createTrxName("portalPayments");
    Trx trx = Trx.get(trxName, true);

    try {
      paymentService.createPayments(getSessionUser(), openItems, creditCardDto, trxName);
      trx.commit();
      log.log(Level.INFO, "Transaction Payments Completed");
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      log.log(Level.WARNING, "Not proceed. Transaction Payments Aborted");
      trx.rollback();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    } finally {
      trx.close();
      trx = null;
    }



  }



}

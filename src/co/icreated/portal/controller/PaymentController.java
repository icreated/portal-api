package co.icreated.portal.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.CreditCardDto;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.VOpenItemDto;
import co.icreated.portal.model.PaymentDto;
import co.icreated.portal.service.InvoiceService;
import co.icreated.portal.service.PaymentService;

@RestController
@RequestMapping(path = "/payments")
public class PaymentController {

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
  @GetMapping
  public List<PaymentDto> getInvoices(@AuthenticationPrincipal SessionUser sessionUser) {

    return paymentService.findInvoicePayments(sessionUser.getPartnerId());
  }


  /**
   * Get Credit Card Payment Here dont't forget to add @Valid before @RequestBody with corresponding
   * annotations in CreditCardDto to validate credit card fields with javax.validation. (You have to
   * add validation.jar to classpath for it)
   *
   * @param sessionUser
   * @param creditCard
   */
  @PostMapping("/pay")
  public void postPaymentCreditCard(@AuthenticationPrincipal SessionUser sessionUser,
      @RequestBody CreditCardDto creditCard) {

    List<VOpenItemDto> openItems = invoiceService.findOpenItems(sessionUser.getPartnerId());
    BigDecimal openTotal = openItems.stream().map(VOpenItemDto::getOpenAmt).reduce(Env.ZERO,
        (subtotal, value) -> subtotal.add(value));

    boolean isTotalEqual = openTotal.compareTo(creditCard.getAmt()) == 0;
    if (!isTotalEqual) {
      throw new AdempiereException("Totals from backend & frontend not are equal");
    }

    // TODO It's a good idea to put this Idempiere Trx deal to AOP Spring Annotation @Around
    // For instance it's a unique case, but later it's a "must be"
    String trxName = Trx.createTrxName("portalPayments");
    Trx trx = Trx.get(trxName, true);

    try {
      paymentService.createPayments(sessionUser, openItems, creditCard, trxName);
      trx.commit();
      log.log(Level.INFO, "Transaction Payments Completed");
    } catch (Exception e) {
      log.log(Level.WARNING, "Not proceed. Transaction Payments Aborted");
      trx.rollback();
    } finally {
      trx.close();
      trx = null;
    }


  }

}

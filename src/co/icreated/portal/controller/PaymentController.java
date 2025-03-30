package co.icreated.portal.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.model.CreditCardDto;
import co.icreated.portal.api.model.OpenItemDto;
import co.icreated.portal.api.model.PaymentDto;
import co.icreated.portal.api.service.PaymentsApi;
import co.icreated.portal.security.Authenticated;
import co.icreated.portal.service.InvoiceService;
import co.icreated.portal.service.PaymentService;
import co.icreated.portal.utils.Transaction;

@RestController
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
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
   *
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

    Transaction.run(trxName -> {
      paymentService.createPayments(getSessionUser(), openItems, creditCardDto, trxName);
    });
    return null;
  }

}

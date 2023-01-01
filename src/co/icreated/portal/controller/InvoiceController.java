package co.icreated.portal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.InvoicesApi;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.VOpenItemDto;
import co.icreated.portal.model.DocumentDto;
import co.icreated.portal.model.InvoiceDto;
import co.icreated.portal.security.Authenticated;
import co.icreated.portal.service.InvoiceService;

@RestController
public class InvoiceController implements InvoicesApi, Authenticated {

  InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;

  }


  @Override
  public ResponseEntity<List<DocumentDto>> getInvoices() {
    List<DocumentDto> invoices =
        invoiceService.findBPartnerInvoices(getSessionUser().getPartnerId());
    return ResponseEntity.ok(invoices);
  }


  @Override
  public ResponseEntity<InvoiceDto> getInvoice(Integer id) {
    return ResponseEntity.ok(invoiceService.findInvoiceById(id));
  }


  /**
   * Get OpenItems
   *
   * @param user
   * @return
   */
  @GetMapping("/openitems")
  public List<VOpenItemDto> getOpenItems(@AuthenticationPrincipal SessionUser user) {

    return invoiceService.findOpenItems(user.getPartnerId());

  }



}

package co.icreated.portal.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.api.model.DocumentDto;
import co.icreated.portal.api.model.InvoiceDto;
import co.icreated.portal.api.model.OpenItemDto;
import co.icreated.portal.api.service.InvoicesApi;
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


  @Override
  public ResponseEntity<List<OpenItemDto>> getOpenItems() {

    List<OpenItemDto> openItems = invoiceService.findOpenItems(getSessionUser().getPartnerId());
    return ResponseEntity.ok(openItems);
  }



}

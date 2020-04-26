package co.icreated.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.Document;
import co.icreated.portal.bean.Invoice;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.VOpenItem;
import co.icreated.portal.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;

	
	@GetMapping("/all")
	public List<Document>  getInvoices(@AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findBPartnerInvoices(user.getPartnerId());
		
	}
	
	
	@GetMapping("/invoice/{invoiceId}")
	public Invoice  getInvoiceById(@PathVariable int invoiceId, @AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findInvoiceById(invoiceId, user.getPartnerId());
		
	}
	
	
	@GetMapping("/openitems")
	public List<VOpenItem>  getOpenItems(@AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findOpenItems(user.getPartnerId());
		
	}

}
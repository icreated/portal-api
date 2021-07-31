package co.icreated.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.DocumentDto;
import co.icreated.portal.bean.InvoiceDto;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.VOpenItemDto;
import co.icreated.portal.service.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;

	
	@GetMapping
	public List<DocumentDto>  getInvoices(@AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findBPartnerInvoices(user.getPartnerId());
		
	}
	
	
	@GetMapping("/{invoiceId}")
	public InvoiceDto  getInvoiceById(@PathVariable int invoiceId, @AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findInvoiceById(invoiceId, user.getPartnerId());
		
	}
	
	
	@GetMapping("/openitems")
	public List<VOpenItemDto>  getOpenItems(@AuthenticationPrincipal SessionUser user) {
		
		return invoiceService.findOpenItems(user.getPartnerId());
		
	}

}
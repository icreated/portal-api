package co.icreated.portal.controller;

import java.util.List;

import org.compiere.model.I_AD_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.icreated.portal.bean.Document;
import co.icreated.portal.bean.Invoice;
import co.icreated.portal.bean.Payment;
import co.icreated.portal.bean.SessionUser;
import co.icreated.portal.bean.Token;
import co.icreated.portal.service.InvoiceService;
import co.icreated.portal.service.PaymentService;
import co.icreated.portal.service.UserService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	
	@Autowired
	PaymentService paymentService;

	
	@GetMapping("/all")
	public List<Payment>  getInvoices(@AuthenticationPrincipal SessionUser user) {
		
		return paymentService.findPayments(0, user.getPartnerId());
		
	}

}
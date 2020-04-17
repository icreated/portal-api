package co.icreated.portal.service;

import java.util.Properties;

import org.compiere.model.MInvoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
	
	@Autowired
	Properties ctx;
	
	
	public MInvoice getInvoice(int C_Invoice_ID) {
		
		return new MInvoice(ctx, C_Invoice_ID, null);
	}

}

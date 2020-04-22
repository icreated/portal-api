package co.icreated.portal.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Invoice extends Document {


	List<Payment> payments;
	List<Document> invoices;
	List<Tax> taxes;
	
	public Invoice() {}

	public Invoice(int id, String documentNo,String description, String docStatus,
			Date date, BigDecimal totalLines, BigDecimal grandTotal) {
		
		super(id, documentNo, null, description, docStatus, date, totalLines, grandTotal);

	}
	
	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public List<Document> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Document> invoices) {
		this.invoices = invoices;
	}

	public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}


}
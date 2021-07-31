package co.icreated.portal.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceDto extends DocumentDto {

	List<PaymentDto> payments;
	List<DocumentDto> invoices;
	List<TaxDto> taxes;
	
	public InvoiceDto() {}

	public InvoiceDto(int id, String documentNo,String description, String docStatus,
			Date date, BigDecimal totalLines, BigDecimal grandTotal) {
		
		super(id, documentNo, null, description, docStatus, date, totalLines, grandTotal);

	}
	
	public int getC_Invoice_ID() {
		return id;
	}

	public void setC_Invoice_ID(int C_Invoice_ID) {
		this.id = C_Invoice_ID;
	}
	
	public List<PaymentDto> getPayments() {
		return payments;
	}

	public void setPayments(List<PaymentDto> payments) {
		this.payments = payments;
	}

	public List<DocumentDto> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<DocumentDto> invoices) {
		this.invoices = invoices;
	}

	public List<TaxDto> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<TaxDto> taxes) {
		this.taxes = taxes;
	}


}
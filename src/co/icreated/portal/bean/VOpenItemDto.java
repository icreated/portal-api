package co.icreated.portal.bean;

import java.math.BigDecimal;
import java.util.Date;


public class VOpenItemDto {
	

	int invoiceId;
	int orderId;
	int bpartnerId;
	int bpartnerLocationId;
	int currencyId;
	String documentNo;
	String description;
	String docStatus;
	boolean isSoTrx;
	boolean isActive;
	Date dateOrdered;
	Date dateInvoiced;
	int netDays;
	Date dueDate;
	BigDecimal totalLines;
	BigDecimal grandTotal;
	BigDecimal paidAmt;
	BigDecimal openAmt;
	
	
	
	public VOpenItemDto(int invoiceId, int orderId, int bpartnerId, int bpartnerLocationId, int currencyId, String documentNo, String description,
			String docStatus, boolean isSOTrx, boolean isActive, Date dateOrdered, Date dateInvoiced, Date dueDate, int netDays,
			 BigDecimal totalLines, BigDecimal grandTotal, BigDecimal paidAmt, BigDecimal openAmt) {
		super();
		this.invoiceId = invoiceId;
		this.orderId = orderId;
		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.currencyId = currencyId;
		this.documentNo = documentNo;
		this.description = description;
		this.docStatus = docStatus;
		this.isSoTrx = isSOTrx;
		this.isActive = isActive;
		this.dateOrdered = dateOrdered;
		this.dateInvoiced = dateInvoiced;
		this.netDays = netDays;
		this.dueDate = dueDate;
		this.totalLines = totalLines;
		this.grandTotal = grandTotal;
		this.paidAmt = paidAmt;
		this.openAmt = openAmt;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public int getOrderId() {
		return orderId;
	}
	public int getBpartnerId() {
		return bpartnerId;
	}	
	public int getBpartnerLocationId() {
		return bpartnerLocationId;
	}
	public int getCurrencyId() {
		return currencyId;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public String getDescription() {
		return description;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public boolean isSoTrx() {
		return isSoTrx;
	}
	public boolean isActive() {
		return isActive;
	}
	public Date getDateOrdered() {
		return dateOrdered;
	}
	public Date getDateInvoiced() {
		return dateInvoiced;
	}
	public int getNetDays() {
		return netDays;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public BigDecimal getTotalLines() {
		return totalLines;
	}
	public BigDecimal getGrandTotal() {
		return grandTotal;
	}
	public BigDecimal getPaidAmt() {
		return paidAmt;
	}
	public BigDecimal getOpenAmt() {
		return openAmt;
	}
	@Override
	public String toString() {
		return "VOpenItem [invoiceId=" + invoiceId + ", documentNo=" + documentNo + ", openAmt=" + openAmt + "]";
	}
	
	


}

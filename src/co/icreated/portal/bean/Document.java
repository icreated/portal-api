package co.icreated.portal.bean;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Document {
	
	int id = 0;
	String documentNo;
	String poReference;
	String description;
	String docStatus;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	Date date;
	BigDecimal totalLines;
	BigDecimal grandTotal;
	String name;

	
	List<DocumentLine> lines = new ArrayList<DocumentLine>();
	
	Address shipAddress;
	Address billAddress;
	
	public Document() {}


	public Document(int id, String documentNo, String poReference,
			String description, String docStatus, Date date,
			BigDecimal totalLines, BigDecimal grandTotal) {
		super();
		this.id = id;
		this.documentNo = documentNo;
		this.poReference = poReference;
		this.description = description;
		this.docStatus = docStatus;
		this.date = date;
		this.totalLines = totalLines;
		this.grandTotal = grandTotal;
		
	}
	
	public List<DocumentLine> getLines() {
		return lines;
	}

	public void setLines(List<DocumentLine> lines) {
		this.lines = lines;
	}

	public int getId() {
		return id;
	}
	public String getDocumentNo() {
		return documentNo;
	}
	public String getPoReference() {
		return poReference;
	}
	public String getDescription() {
		return description;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public Date getDate() {
		return date;
	}
	public BigDecimal getTotalLines() {
		return totalLines;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public Address getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(Address deliveryAddress) {
		this.shipAddress = deliveryAddress;
	}

	public Address getBillAddress() {
		return billAddress;
	}

	public void setBillAddress(Address invoiceAddress) {
		this.billAddress = invoiceAddress;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public void setPoReference(String poReference) {
		this.poReference = poReference;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public void setTotalLines(BigDecimal totalLines) {
		this.totalLines = totalLines;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}	
	
	
	

}

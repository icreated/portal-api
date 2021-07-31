package co.icreated.portal.bean;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentLineDto {
	
	int id;
	int productId;
	int line;
	String name;
	String description;
	BigDecimal priceList;
	BigDecimal price;
	BigDecimal qty;
	BigDecimal lineNetAmt;
	
	
	public DocumentLineDto() {}

	
	

	public DocumentLineDto(int id, int productId,  int line, String name, String description, BigDecimal priceList, BigDecimal price, 
			BigDecimal qty, BigDecimal lineNetAmt) {
		super();
		this.id = id;
		this.productId = productId;
		this.line = line;
		this.name = name;
		this.description = description;
		this.priceList = priceList;
		this.price = price;
		this.qty = qty;
		this.lineNetAmt = lineNetAmt;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getProductId() {
		return productId;
	}




	public void setProductId(int productId) {
		this.productId = productId;
	}




	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public BigDecimal getPriceList() {
		return priceList;
	}


	public void setPriceList(BigDecimal priceList) {
		this.priceList = priceList;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public BigDecimal getQty() {
		return qty;
	}


	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}


	public BigDecimal getLineNetAmt() {
		return lineNetAmt;
	}


	public void setLineNetAmt(BigDecimal lineNetAmt) {
		this.lineNetAmt = lineNetAmt;
	}




	public int getLine() {
		return line;
	}




	public void setLine(int line) {
		this.line = line;
	}
	
	
	

}

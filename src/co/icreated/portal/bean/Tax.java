package co.icreated.portal.bean;

import java.math.BigDecimal;

public class Tax {
	
	String name;
	BigDecimal tax;
	
	public Tax() {
		
	}
	
	public Tax(String name, BigDecimal tax) {
		super();
		this.name = name;
		this.tax = tax;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	
	
	

}
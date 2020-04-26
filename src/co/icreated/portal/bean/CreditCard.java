package co.icreated.portal.bean;

import java.math.BigDecimal;

public class CreditCard {
	
    String cardType;
    String creditCard;
    String holderName;
    Integer expirationMonth;
    Integer expirationYear;
    String cvc;
	BigDecimal amt = null;
    
    
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public Integer getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	
	@Override
	public String toString() {
		return "CreditCard [cardType=" + cardType + ", creditCard=" + creditCard + ", holderName=" + holderName
				+ ", expirationMonth=" + expirationMonth + ", expirationYear=" + expirationYear + ", cvc=" + cvc
				+ ", amt=" + amt + "]";
	}

    
    
    

}

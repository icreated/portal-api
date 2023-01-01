package co.icreated.portal.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PaymentDto {

  int id;
  String documentNo;
  String description;
  String docStatus;
  BigDecimal payAmt;
  String trxid;
  String currency;
  String tenderType;
  @JsonFormat(pattern = "yyyy-MM-dd")
  Date date;

  public PaymentDto() {

  }

  public PaymentDto(int id, String documentNo, String description, String docStatus,
      BigDecimal payAmt, String trxid, String currency, String tenderType, Date date) {
    super();
    this.id = id;
    this.documentNo = documentNo;
    this.description = description;
    this.docStatus = docStatus;
    this.payAmt = payAmt;
    this.trxid = trxid;
    this.currency = currency;
    this.tenderType = tenderType;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(String docStatus) {
    this.docStatus = docStatus;
  }

  public BigDecimal getPayAmt() {
    return payAmt;
  }

  public void setPayAmt(BigDecimal payAmt) {
    this.payAmt = payAmt;
  }

  public String getTrxid() {
    return trxid;
  }

  public void setTrxid(String trxid) {
    this.trxid = trxid;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getTenderType() {
    return tenderType;
  }

  public void setTenderType(String tenderType) {
    this.tenderType = tenderType;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }



}

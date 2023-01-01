package co.icreated.portal.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * PaymentDto
 */

@JsonTypeName("Payment")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class PaymentDto {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("documentNo")
  private String documentNo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("docStatus")
  private String docStatus;

  @JsonProperty("payAmt")
  private Double payAmt;

  @JsonProperty("trxid")
  private String trxid;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("tenderType")
  private String tenderType;

  @JsonProperty("date")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  public PaymentDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", example = "4", required = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PaymentDto documentNo(String documentNo) {
    this.documentNo = documentNo;
    return this;
  }

  /**
   * The document number of the payment.
   * @return documentNo
  */
  
  @Schema(name = "documentNo", example = "1000001", description = "The document number of the payment.", required = false)
  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public PaymentDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the payment.
   * @return description
  */
  
  @Schema(name = "description", example = "Payment for order 1000001", description = "The description of the payment.", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PaymentDto docStatus(String docStatus) {
    this.docStatus = docStatus;
    return this;
  }

  /**
   * The document status of the payment.
   * @return docStatus
  */
  
  @Schema(name = "docStatus", example = "CO", description = "The document status of the payment.", required = false)
  public String getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(String docStatus) {
    this.docStatus = docStatus;
  }

  public PaymentDto payAmt(Double payAmt) {
    this.payAmt = payAmt;
    return this;
  }

  /**
   * The amount of the payment.
   * @return payAmt
  */
  
  @Schema(name = "payAmt", example = "100.0", description = "The amount of the payment.", required = false)
  public Double getPayAmt() {
    return payAmt;
  }

  public void setPayAmt(Double payAmt) {
    this.payAmt = payAmt;
  }

  public PaymentDto trxid(String trxid) {
    this.trxid = trxid;
    return this;
  }

  /**
   * The transaction ID of the payment.
   * @return trxid
  */
  
  @Schema(name = "trxid", example = "1000001", description = "The transaction ID of the payment.", required = false)
  public String getTrxid() {
    return trxid;
  }

  public void setTrxid(String trxid) {
    this.trxid = trxid;
  }

  public PaymentDto currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * The currency of the payment.
   * @return currency
  */
  
  @Schema(name = "currency", example = "USD", description = "The currency of the payment.", required = false)
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public PaymentDto tenderType(String tenderType) {
    this.tenderType = tenderType;
    return this;
  }

  /**
   * The tender type of the payment.
   * @return tenderType
  */
  
  @Schema(name = "tenderType", example = "C", description = "The tender type of the payment.", required = false)
  public String getTenderType() {
    return tenderType;
  }

  public void setTenderType(String tenderType) {
    this.tenderType = tenderType;
  }

  public PaymentDto date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * The transaction date of the payment.
   * @return date
  */
  @Valid 
  @Schema(name = "date", example = "Fri Jan 01 01:00:00 CET 2021", description = "The transaction date of the payment.", required = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PaymentDto payment = (PaymentDto) o;
    return Objects.equals(this.id, payment.id) &&
        Objects.equals(this.documentNo, payment.documentNo) &&
        Objects.equals(this.description, payment.description) &&
        Objects.equals(this.docStatus, payment.docStatus) &&
        Objects.equals(this.payAmt, payment.payAmt) &&
        Objects.equals(this.trxid, payment.trxid) &&
        Objects.equals(this.currency, payment.currency) &&
        Objects.equals(this.tenderType, payment.tenderType) &&
        Objects.equals(this.date, payment.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentNo, description, docStatus, payAmt, trxid, currency, tenderType, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PaymentDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    documentNo: ").append(toIndentedString(documentNo)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    docStatus: ").append(toIndentedString(docStatus)).append("\n");
    sb.append("    payAmt: ").append(toIndentedString(payAmt)).append("\n");
    sb.append("    trxid: ").append(toIndentedString(trxid)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    tenderType: ").append(toIndentedString(tenderType)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


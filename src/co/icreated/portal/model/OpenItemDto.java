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
 * OpenItemDto
 */

@JsonTypeName("OpenItem")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class OpenItemDto {

  @JsonProperty("invoiceId")
  private Integer invoiceId;

  @JsonProperty("orderId")
  private Integer orderId;

  @JsonProperty("bpartnerId")
  private Integer bpartnerId;

  @JsonProperty("bpartnerLocationId")
  private Integer bpartnerLocationId;

  @JsonProperty("currencyId")
  private Integer currencyId;

  @JsonProperty("documentNo")
  private String documentNo;

  @JsonProperty("description")
  private String description;

  @JsonProperty("docStatus")
  private String docStatus;

  @JsonProperty("isSOTRX")
  private Boolean isSOTRX;

  @JsonProperty("isActive")
  private Boolean isActive;

  @JsonProperty("dateOrdered")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateOrdered;

  @JsonProperty("dateInvoiced")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dateInvoiced;

  @JsonProperty("dueDate")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate dueDate;

  @JsonProperty("netDays")
  private Integer netDays;

  @JsonProperty("totalLines")
  private java.math.BigDecimal totalLines;

  @JsonProperty("grandTotal")
  private java.math.BigDecimal grandTotal;

  @JsonProperty("openAmt")
  private java.math.BigDecimal openAmt;

  @JsonProperty("paidAmt")
  private java.math.BigDecimal paidAmt;

  public OpenItemDto invoiceId(Integer invoiceId) {
    this.invoiceId = invoiceId;
    return this;
  }

  /**
   * Get invoiceId
   * @return invoiceId
  */
  
  @Schema(name = "invoiceId", example = "4", required = false)
  public Integer getInvoiceId() {
    return invoiceId;
  }

  public void setInvoiceId(Integer invoiceId) {
    this.invoiceId = invoiceId;
  }

  public OpenItemDto orderId(Integer orderId) {
    this.orderId = orderId;
    return this;
  }

  /**
   * Get orderId
   * @return orderId
  */
  
  @Schema(name = "orderId", example = "4", required = false)
  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  public OpenItemDto bpartnerId(Integer bpartnerId) {
    this.bpartnerId = bpartnerId;
    return this;
  }

  /**
   * Get bpartnerId
   * @return bpartnerId
  */
  
  @Schema(name = "bpartnerId", example = "4", required = false)
  public Integer getBpartnerId() {
    return bpartnerId;
  }

  public void setBpartnerId(Integer bpartnerId) {
    this.bpartnerId = bpartnerId;
  }

  public OpenItemDto bpartnerLocationId(Integer bpartnerLocationId) {
    this.bpartnerLocationId = bpartnerLocationId;
    return this;
  }

  /**
   * Get bpartnerLocationId
   * @return bpartnerLocationId
  */
  
  @Schema(name = "bpartnerLocationId", example = "4", required = false)
  public Integer getBpartnerLocationId() {
    return bpartnerLocationId;
  }

  public void setBpartnerLocationId(Integer bpartnerLocationId) {
    this.bpartnerLocationId = bpartnerLocationId;
  }

  public OpenItemDto currencyId(Integer currencyId) {
    this.currencyId = currencyId;
    return this;
  }

  /**
   * Get currencyId
   * @return currencyId
  */
  
  @Schema(name = "currencyId", example = "4", required = false)
  public Integer getCurrencyId() {
    return currencyId;
  }

  public void setCurrencyId(Integer currencyId) {
    this.currencyId = currencyId;
  }

  public OpenItemDto documentNo(String documentNo) {
    this.documentNo = documentNo;
    return this;
  }

  /**
   * The document number of the invoice.
   * @return documentNo
  */
  
  @Schema(name = "documentNo", example = "1000001", description = "The document number of the invoice.", required = false)
  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public OpenItemDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the invoice.
   * @return description
  */
  
  @Schema(name = "description", example = "Invoice for order 1000001", description = "The description of the invoice.", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public OpenItemDto docStatus(String docStatus) {
    this.docStatus = docStatus;
    return this;
  }

  /**
   * The document status of the invoice.
   * @return docStatus
  */
  
  @Schema(name = "docStatus", example = "CO", description = "The document status of the invoice.", required = false)
  public String getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(String docStatus) {
    this.docStatus = docStatus;
  }

  public OpenItemDto isSOTRX(Boolean isSOTRX) {
    this.isSOTRX = isSOTRX;
    return this;
  }

  /**
   * The transaction type is sale or purchase.
   * @return isSOTRX
  */
  
  @Schema(name = "isSOTRX", example = "true", description = "The transaction type is sale or purchase.", required = false)
  public Boolean getIsSOTRX() {
    return isSOTRX;
  }

  public void setIsSOTRX(Boolean isSOTRX) {
    this.isSOTRX = isSOTRX;
  }

  public OpenItemDto isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

  /**
   * The invoice is active.
   * @return isActive
  */
  
  @Schema(name = "isActive", example = "true", description = "The invoice is active.", required = false)
  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public OpenItemDto dateOrdered(LocalDate dateOrdered) {
    this.dateOrdered = dateOrdered;
    return this;
  }

  /**
   * The order date of the invoice.
   * @return dateOrdered
  */
  @Valid 
  @Schema(name = "dateOrdered", example = "Fri Jan 01 01:00:00 CET 2021", description = "The order date of the invoice.", required = false)
  public LocalDate getDateOrdered() {
    return dateOrdered;
  }

  public void setDateOrdered(LocalDate dateOrdered) {
    this.dateOrdered = dateOrdered;
  }

  public OpenItemDto dateInvoiced(LocalDate dateInvoiced) {
    this.dateInvoiced = dateInvoiced;
    return this;
  }

  /**
   * The invoice date of the invoice.
   * @return dateInvoiced
  */
  @Valid 
  @Schema(name = "dateInvoiced", example = "Fri Jan 01 01:00:00 CET 2021", description = "The invoice date of the invoice.", required = false)
  public LocalDate getDateInvoiced() {
    return dateInvoiced;
  }

  public void setDateInvoiced(LocalDate dateInvoiced) {
    this.dateInvoiced = dateInvoiced;
  }

  public OpenItemDto dueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
    return this;
  }

  /**
   * The due date of the invoice.
   * @return dueDate
  */
  @Valid 
  @Schema(name = "dueDate", example = "Fri Jan 01 01:00:00 CET 2021", description = "The due date of the invoice.", required = false)
  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public OpenItemDto netDays(Integer netDays) {
    this.netDays = netDays;
    return this;
  }

  /**
   * Get netDays
   * @return netDays
  */
  
  @Schema(name = "netDays", example = "20", required = false)
  public Integer getNetDays() {
    return netDays;
  }

  public void setNetDays(Integer netDays) {
    this.netDays = netDays;
  }

  public OpenItemDto totalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
    return this;
  }

  /**
   * The total lines of the invoice.
   * @return totalLines
  */
  
  @Schema(name = "totalLines", example = "100.0", description = "The total lines of the invoice.", required = false)
  public java.math.BigDecimal getTotalLines() {
    return totalLines;
  }

  public void setTotalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
  }

  public OpenItemDto grandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
    return this;
  }

  /**
   * The grand total of the invoice.
   * @return grandTotal
  */
  
  @Schema(name = "grandTotal", example = "100.0", description = "The grand total of the invoice.", required = false)
  public java.math.BigDecimal getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
  }

  public OpenItemDto openAmt(java.math.BigDecimal openAmt) {
    this.openAmt = openAmt;
    return this;
  }

  /**
   * The open amount of the invoice.
   * @return openAmt
  */
  
  @Schema(name = "openAmt", example = "100.0", description = "The open amount of the invoice.", required = false)
  public java.math.BigDecimal getOpenAmt() {
    return openAmt;
  }

  public void setOpenAmt(java.math.BigDecimal openAmt) {
    this.openAmt = openAmt;
  }

  public OpenItemDto paidAmt(java.math.BigDecimal paidAmt) {
    this.paidAmt = paidAmt;
    return this;
  }

  /**
   * The paid amount of the invoice.
   * @return paidAmt
  */
  
  @Schema(name = "paidAmt", example = "100.0", description = "The paid amount of the invoice.", required = false)
  public java.math.BigDecimal getPaidAmt() {
    return paidAmt;
  }

  public void setPaidAmt(java.math.BigDecimal paidAmt) {
    this.paidAmt = paidAmt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenItemDto openItem = (OpenItemDto) o;
    return Objects.equals(this.invoiceId, openItem.invoiceId) &&
        Objects.equals(this.orderId, openItem.orderId) &&
        Objects.equals(this.bpartnerId, openItem.bpartnerId) &&
        Objects.equals(this.bpartnerLocationId, openItem.bpartnerLocationId) &&
        Objects.equals(this.currencyId, openItem.currencyId) &&
        Objects.equals(this.documentNo, openItem.documentNo) &&
        Objects.equals(this.description, openItem.description) &&
        Objects.equals(this.docStatus, openItem.docStatus) &&
        Objects.equals(this.isSOTRX, openItem.isSOTRX) &&
        Objects.equals(this.isActive, openItem.isActive) &&
        Objects.equals(this.dateOrdered, openItem.dateOrdered) &&
        Objects.equals(this.dateInvoiced, openItem.dateInvoiced) &&
        Objects.equals(this.dueDate, openItem.dueDate) &&
        Objects.equals(this.netDays, openItem.netDays) &&
        Objects.equals(this.totalLines, openItem.totalLines) &&
        Objects.equals(this.grandTotal, openItem.grandTotal) &&
        Objects.equals(this.openAmt, openItem.openAmt) &&
        Objects.equals(this.paidAmt, openItem.paidAmt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(invoiceId, orderId, bpartnerId, bpartnerLocationId, currencyId, documentNo, description, docStatus, isSOTRX, isActive, dateOrdered, dateInvoiced, dueDate, netDays, totalLines, grandTotal, openAmt, paidAmt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OpenItemDto {\n");
    sb.append("    invoiceId: ").append(toIndentedString(invoiceId)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    bpartnerId: ").append(toIndentedString(bpartnerId)).append("\n");
    sb.append("    bpartnerLocationId: ").append(toIndentedString(bpartnerLocationId)).append("\n");
    sb.append("    currencyId: ").append(toIndentedString(currencyId)).append("\n");
    sb.append("    documentNo: ").append(toIndentedString(documentNo)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    docStatus: ").append(toIndentedString(docStatus)).append("\n");
    sb.append("    isSOTRX: ").append(toIndentedString(isSOTRX)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    dateOrdered: ").append(toIndentedString(dateOrdered)).append("\n");
    sb.append("    dateInvoiced: ").append(toIndentedString(dateInvoiced)).append("\n");
    sb.append("    dueDate: ").append(toIndentedString(dueDate)).append("\n");
    sb.append("    netDays: ").append(toIndentedString(netDays)).append("\n");
    sb.append("    totalLines: ").append(toIndentedString(totalLines)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    openAmt: ").append(toIndentedString(openAmt)).append("\n");
    sb.append("    paidAmt: ").append(toIndentedString(paidAmt)).append("\n");
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


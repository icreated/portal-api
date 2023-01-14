package co.icreated.portal.api.model;

import java.net.URI;
import java.util.Objects;
import co.icreated.portal.api.model.AddressDto;
import co.icreated.portal.api.model.InvoiceLineDto;
import co.icreated.portal.api.model.PaymentDto;
import co.icreated.portal.api.model.TaxDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * Object Invoice
 */

@Schema(name = "Invoice", description = "Object Invoice")
@JsonTypeName("Invoice")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class InvoiceDto {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("documentNo")
  private String documentNo;

  @JsonProperty("poReference")
  private String poReference;

  @JsonProperty("bpartnerName")
  private String bpartnerName;

  @JsonProperty("description")
  private String description;

  @JsonProperty("docStatus")
  private String docStatus;

  @JsonProperty("totalLines")
  private java.math.BigDecimal totalLines;

  @JsonProperty("grandTotal")
  private java.math.BigDecimal grandTotal;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("date")
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  @JsonProperty("billAddress")
  private AddressDto billAddress;

  @JsonProperty("lines")
  @Valid
  private List<InvoiceLineDto> lines = null;

  @JsonProperty("payments")
  @Valid
  private List<PaymentDto> payments = null;

  @JsonProperty("taxes")
  @Valid
  private List<TaxDto> taxes = null;

  public InvoiceDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @NotNull 
  @Schema(name = "id", example = "4", required = true)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public InvoiceDto documentNo(String documentNo) {
    this.documentNo = documentNo;
    return this;
  }

  /**
   * The document number of the invoice.
   * @return documentNo
  */
  @NotNull 
  @Schema(name = "documentNo", example = "1000001", description = "The document number of the invoice.", required = true)
  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public InvoiceDto poReference(String poReference) {
    this.poReference = poReference;
    return this;
  }

  /**
   * The PO reference of the invoice.
   * @return poReference
  */
  
  @Schema(name = "poReference", example = "1000001", description = "The PO reference of the invoice.", required = false)
  public String getPoReference() {
    return poReference;
  }

  public void setPoReference(String poReference) {
    this.poReference = poReference;
  }

  public InvoiceDto bpartnerName(String bpartnerName) {
    this.bpartnerName = bpartnerName;
    return this;
  }

  /**
   * The Business Partner of the order / invoice.
   * @return bpartnerName
  */
  @NotNull 
  @Schema(name = "bpartnerName", example = "John Smith", description = "The Business Partner of the order / invoice.", required = true)
  public String getBpartnerName() {
    return bpartnerName;
  }

  public void setBpartnerName(String bpartnerName) {
    this.bpartnerName = bpartnerName;
  }

  public InvoiceDto description(String description) {
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

  public InvoiceDto docStatus(String docStatus) {
    this.docStatus = docStatus;
    return this;
  }

  /**
   * The document status of the invoice.
   * @return docStatus
  */
  @NotNull 
  @Schema(name = "docStatus", example = "CO", description = "The document status of the invoice.", required = true)
  public String getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(String docStatus) {
    this.docStatus = docStatus;
  }

  public InvoiceDto totalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
    return this;
  }

  /**
   * The total lines of the invoice.
   * @return totalLines
  */
  @NotNull 
  @Schema(name = "totalLines", example = "100.0", description = "The total lines of the invoice.", required = true)
  public java.math.BigDecimal getTotalLines() {
    return totalLines;
  }

  public void setTotalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
  }

  public InvoiceDto grandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
    return this;
  }

  /**
   * The grand total of the invoice.
   * @return grandTotal
  */
  @NotNull 
  @Schema(name = "grandTotal", example = "100.0", description = "The grand total of the invoice.", required = true)
  public java.math.BigDecimal getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
  }

  public InvoiceDto currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * The currency of the invoice.
   * @return currency
  */
  @NotNull 
  @Schema(name = "currency", example = "USD", description = "The currency of the invoice.", required = true)
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public InvoiceDto date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * The transaction date of the invoice.
   * @return date
  */
  @NotNull @Valid 
  @Schema(name = "date", example = "Fri Jan 01 01:00:00 CET 2021", description = "The transaction date of the invoice.", required = true)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public InvoiceDto billAddress(AddressDto billAddress) {
    this.billAddress = billAddress;
    return this;
  }

  /**
   * Get billAddress
   * @return billAddress
  */
  @Valid 
  @Schema(name = "billAddress", required = false)
  public AddressDto getBillAddress() {
    return billAddress;
  }

  public void setBillAddress(AddressDto billAddress) {
    this.billAddress = billAddress;
  }

  public InvoiceDto lines(List<InvoiceLineDto> lines) {
    this.lines = lines;
    return this;
  }

  public InvoiceDto addLinesItem(InvoiceLineDto linesItem) {
    if (this.lines == null) {
      this.lines = new ArrayList<>();
    }
    this.lines.add(linesItem);
    return this;
  }

  /**
   * Get lines
   * @return lines
  */
  @Valid 
  @Schema(name = "lines", required = false)
  public List<InvoiceLineDto> getLines() {
    return lines;
  }

  public void setLines(List<InvoiceLineDto> lines) {
    this.lines = lines;
  }

  public InvoiceDto payments(List<PaymentDto> payments) {
    this.payments = payments;
    return this;
  }

  public InvoiceDto addPaymentsItem(PaymentDto paymentsItem) {
    if (this.payments == null) {
      this.payments = new ArrayList<>();
    }
    this.payments.add(paymentsItem);
    return this;
  }

  /**
   * Get payments
   * @return payments
  */
  @Valid 
  @Schema(name = "payments", required = false)
  public List<PaymentDto> getPayments() {
    return payments;
  }

  public void setPayments(List<PaymentDto> payments) {
    this.payments = payments;
  }

  public InvoiceDto taxes(List<TaxDto> taxes) {
    this.taxes = taxes;
    return this;
  }

  public InvoiceDto addTaxesItem(TaxDto taxesItem) {
    if (this.taxes == null) {
      this.taxes = new ArrayList<>();
    }
    this.taxes.add(taxesItem);
    return this;
  }

  /**
   * Get taxes
   * @return taxes
  */
  @Valid 
  @Schema(name = "taxes", required = false)
  public List<TaxDto> getTaxes() {
    return taxes;
  }

  public void setTaxes(List<TaxDto> taxes) {
    this.taxes = taxes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceDto invoice = (InvoiceDto) o;
    return Objects.equals(this.id, invoice.id) &&
        Objects.equals(this.documentNo, invoice.documentNo) &&
        Objects.equals(this.poReference, invoice.poReference) &&
        Objects.equals(this.bpartnerName, invoice.bpartnerName) &&
        Objects.equals(this.description, invoice.description) &&
        Objects.equals(this.docStatus, invoice.docStatus) &&
        Objects.equals(this.totalLines, invoice.totalLines) &&
        Objects.equals(this.grandTotal, invoice.grandTotal) &&
        Objects.equals(this.currency, invoice.currency) &&
        Objects.equals(this.date, invoice.date) &&
        Objects.equals(this.billAddress, invoice.billAddress) &&
        Objects.equals(this.lines, invoice.lines) &&
        Objects.equals(this.payments, invoice.payments) &&
        Objects.equals(this.taxes, invoice.taxes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentNo, poReference, bpartnerName, description, docStatus, totalLines, grandTotal, currency, date, billAddress, lines, payments, taxes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvoiceDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    documentNo: ").append(toIndentedString(documentNo)).append("\n");
    sb.append("    poReference: ").append(toIndentedString(poReference)).append("\n");
    sb.append("    bpartnerName: ").append(toIndentedString(bpartnerName)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    docStatus: ").append(toIndentedString(docStatus)).append("\n");
    sb.append("    totalLines: ").append(toIndentedString(totalLines)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    billAddress: ").append(toIndentedString(billAddress)).append("\n");
    sb.append("    lines: ").append(toIndentedString(lines)).append("\n");
    sb.append("    payments: ").append(toIndentedString(payments)).append("\n");
    sb.append("    taxes: ").append(toIndentedString(taxes)).append("\n");
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


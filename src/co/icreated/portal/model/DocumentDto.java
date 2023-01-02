package co.icreated.portal.model;

import java.time.LocalDate;
import java.util.*;
import java.util.Objects;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Order or Invoice for table display
 */

@Schema(name = "Document", description = "Order or Invoice for table display")
@JsonTypeName("Document")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class DocumentDto {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("documentNo")
  private String documentNo;

  @JsonProperty("poReference")
  private String poReference;

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

  @JsonProperty("bpartnerName")
  private String bpartnerName;

  public DocumentDto id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   */

  @Schema(name = "id", example = "4", required = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public DocumentDto documentNo(String documentNo) {
    this.documentNo = documentNo;
    return this;
  }

  /**
   * The document number of the order / invoice.
   *
   * @return documentNo
   */

  @Schema(name = "documentNo", example = "1000001",
      description = "The document number of the order / invoice.", required = false)
  public String getDocumentNo() {
    return documentNo;
  }

  public void setDocumentNo(String documentNo) {
    this.documentNo = documentNo;
  }

  public DocumentDto poReference(String poReference) {
    this.poReference = poReference;
    return this;
  }

  /**
   * The PO reference of the order / invoice.
   *
   * @return poReference
   */

  @Schema(name = "poReference", example = "1000001",
      description = "The PO reference of the order / invoice.", required = false)
  public String getPoReference() {
    return poReference;
  }

  public void setPoReference(String poReference) {
    this.poReference = poReference;
  }

  public DocumentDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the order / invoice.
   *
   * @return description
   */

  @Schema(name = "description", example = "Invoice for order 1000001",
      description = "The description of the order / invoice.", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DocumentDto docStatus(String docStatus) {
    this.docStatus = docStatus;
    return this;
  }

  /**
   * The document status of the order / invoice.
   *
   * @return docStatus
   */

  @Schema(name = "docStatus", example = "CO",
      description = "The document status of the order / invoice.", required = false)
  public String getDocStatus() {
    return docStatus;
  }

  public void setDocStatus(String docStatus) {
    this.docStatus = docStatus;
  }

  public DocumentDto totalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
    return this;
  }

  /**
   * The total lines of the order / invoice.
   *
   * @return totalLines
   */

  @Schema(name = "totalLines", example = "100.0",
      description = "The total lines of the order / invoice.", required = false)
  public java.math.BigDecimal getTotalLines() {
    return totalLines;
  }

  public void setTotalLines(java.math.BigDecimal totalLines) {
    this.totalLines = totalLines;
  }

  public DocumentDto grandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
    return this;
  }

  /**
   * The grand total of the order / invoice.
   *
   * @return grandTotal
   */

  @Schema(name = "grandTotal", example = "100.0",
      description = "The grand total of the order / invoice.", required = false)
  public java.math.BigDecimal getGrandTotal() {
    return grandTotal;
  }

  public void setGrandTotal(java.math.BigDecimal grandTotal) {
    this.grandTotal = grandTotal;
  }

  public DocumentDto currency(String currency) {
    this.currency = currency;
    return this;
  }

  /**
   * The currency of the order / invoice.
   *
   * @return currency
   */

  @Schema(name = "currency", example = "USD", description = "The currency of the order / invoice.",
      required = false)
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public DocumentDto date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * The transaction date of the order / invoice.
   *
   * @return date
   */
  @Valid
  @Schema(name = "date", example = "Fri Jan 01 01:00:00 CET 2021",
      description = "The transaction date of the order / invoice.", required = false)
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public DocumentDto bpartnerName(String bpartnerName) {
    this.bpartnerName = bpartnerName;
    return this;
  }

  /**
   * The Business Partner of the order / invoice.
   *
   * @return bpartnerName
   */

  @Schema(name = "bpartnerName", example = "John Smith",
      description = "The Business Partner of the order / invoice.", required = false)
  public String getBpartnerName() {
    return bpartnerName;
  }

  public void setBpartnerName(String bpartnerName) {
    this.bpartnerName = bpartnerName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentDto document = (DocumentDto) o;
    return Objects.equals(this.id, document.id)
        && Objects.equals(this.documentNo, document.documentNo)
        && Objects.equals(this.poReference, document.poReference)
        && Objects.equals(this.description, document.description)
        && Objects.equals(this.docStatus, document.docStatus)
        && Objects.equals(this.totalLines, document.totalLines)
        && Objects.equals(this.grandTotal, document.grandTotal)
        && Objects.equals(this.currency, document.currency)
        && Objects.equals(this.date, document.date)
        && Objects.equals(this.bpartnerName, document.bpartnerName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentNo, poReference, description, docStatus, totalLines, grandTotal,
        currency, date, bpartnerName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    documentNo: ").append(toIndentedString(documentNo)).append("\n");
    sb.append("    poReference: ").append(toIndentedString(poReference)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    docStatus: ").append(toIndentedString(docStatus)).append("\n");
    sb.append("    totalLines: ").append(toIndentedString(totalLines)).append("\n");
    sb.append("    grandTotal: ").append(toIndentedString(grandTotal)).append("\n");
    sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    bpartnerName: ").append(toIndentedString(bpartnerName)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}


package co.icreated.portal.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import javax.validation.Valid;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import javax.annotation.Generated;

/**
 * InvoiceLineDto
 */

@JsonTypeName("InvoiceLine")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class InvoiceLineDto {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("line")
  private Long line;

  @JsonProperty("name")
  private String name;

  @JsonProperty("description")
  private String description;

  @JsonProperty("qty")
  private Double qty;

  @JsonProperty("price")
  private Double price;

  @JsonProperty("priceList")
  private Double priceList;

  @JsonProperty("lineNetAmt")
  private Double lineNetAmt;

  public InvoiceLineDto id(Integer id) {
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

  public InvoiceLineDto line(Long line) {
    this.line = line;
    return this;
  }

  /**
   * The line number of the invoice line.
   * @return line
  */
  
  @Schema(name = "line", example = "10", description = "The line number of the invoice line.", required = false)
  public Long getLine() {
    return line;
  }

  public void setLine(Long line) {
    this.line = line;
  }

  public InvoiceLineDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the invoice line.
   * @return name
  */
  
  @Schema(name = "name", example = "Product 1", description = "The name of the invoice line.", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public InvoiceLineDto description(String description) {
    this.description = description;
    return this;
  }

  /**
   * The description of the invoice line.
   * @return description
  */
  
  @Schema(name = "description", example = "Invoice line for order 1000001", description = "The description of the invoice line.", required = false)
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public InvoiceLineDto qty(Double qty) {
    this.qty = qty;
    return this;
  }

  /**
   * The quantity of the invoice line.
   * @return qty
  */
  
  @Schema(name = "qty", example = "1.0", description = "The quantity of the invoice line.", required = false)
  public Double getQty() {
    return qty;
  }

  public void setQty(Double qty) {
    this.qty = qty;
  }

  public InvoiceLineDto price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * The price of the invoice line.
   * @return price
  */
  
  @Schema(name = "price", example = "100.0", description = "The price of the invoice line.", required = false)
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public InvoiceLineDto priceList(Double priceList) {
    this.priceList = priceList;
    return this;
  }

  /**
   * The price list of the invoice line.
   * @return priceList
  */
  
  @Schema(name = "priceList", example = "100.0", description = "The price list of the invoice line.", required = false)
  public Double getPriceList() {
    return priceList;
  }

  public void setPriceList(Double priceList) {
    this.priceList = priceList;
  }

  public InvoiceLineDto lineNetAmt(Double lineNetAmt) {
    this.lineNetAmt = lineNetAmt;
    return this;
  }

  /**
   * The line net amount of the invoice line.
   * @return lineNetAmt
  */
  
  @Schema(name = "lineNetAmt", example = "100.0", description = "The line net amount of the invoice line.", required = false)
  public Double getLineNetAmt() {
    return lineNetAmt;
  }

  public void setLineNetAmt(Double lineNetAmt) {
    this.lineNetAmt = lineNetAmt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InvoiceLineDto invoiceLine = (InvoiceLineDto) o;
    return Objects.equals(this.id, invoiceLine.id) &&
        Objects.equals(this.line, invoiceLine.line) &&
        Objects.equals(this.name, invoiceLine.name) &&
        Objects.equals(this.description, invoiceLine.description) &&
        Objects.equals(this.qty, invoiceLine.qty) &&
        Objects.equals(this.price, invoiceLine.price) &&
        Objects.equals(this.priceList, invoiceLine.priceList) &&
        Objects.equals(this.lineNetAmt, invoiceLine.lineNetAmt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, line, name, description, qty, price, priceList, lineNetAmt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InvoiceLineDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    line: ").append(toIndentedString(line)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    qty: ").append(toIndentedString(qty)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    priceList: ").append(toIndentedString(priceList)).append("\n");
    sb.append("    lineNetAmt: ").append(toIndentedString(lineNetAmt)).append("\n");
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


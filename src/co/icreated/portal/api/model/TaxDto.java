package co.icreated.portal.api.model;

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
 * TaxDto
 */

@JsonTypeName("Tax")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class TaxDto {

  @JsonProperty("name")
  private String name;

  @JsonProperty("tax")
  private java.math.BigDecimal tax;

  public TaxDto name(String name) {
    this.name = name;
    return this;
  }

  /**
   * The name of the tax.
   * @return name
  */
  @NotNull 
  @Schema(name = "name", example = "State Tax", description = "The name of the tax.", required = true)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TaxDto tax(java.math.BigDecimal tax) {
    this.tax = tax;
    return this;
  }

  /**
   * The tax rate.
   * @return tax
  */
  @NotNull 
  @Schema(name = "tax", example = "0.05", description = "The tax rate.", required = true)
  public java.math.BigDecimal getTax() {
    return tax;
  }

  public void setTax(java.math.BigDecimal tax) {
    this.tax = tax;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TaxDto tax = (TaxDto) o;
    return Objects.equals(this.name, tax.name) &&
        Objects.equals(this.tax, tax.tax);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, tax);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TaxDto {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    tax: ").append(toIndentedString(tax)).append("\n");
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


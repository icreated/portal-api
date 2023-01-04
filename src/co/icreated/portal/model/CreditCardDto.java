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
 * CreditCardDto
 */

@JsonTypeName("CreditCard")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class CreditCardDto {

  @JsonProperty("creditCardType")
  private String creditCardType;

  @JsonProperty("creditCardNumber")
  private String creditCardNumber;

  @JsonProperty("creditCardExpMM")
  private Integer creditCardExpMM;

  @JsonProperty("creditCardExpYY")
  private Integer creditCardExpYY;

  @JsonProperty("creditCardName")
  private String creditCardName;

  @JsonProperty("creditCardVV")
  private String creditCardVV;

  @JsonProperty("paymentAmount")
  private java.math.BigDecimal paymentAmount;

  public CreditCardDto creditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
    return this;
  }

  /**
   * The credit card type.
   *
   * @return creditCardType
   */

  @Schema(name = "creditCardType", example = "Visa", description = "The credit card type.",
      required = false)
  public String getCreditCardType() {
    return creditCardType;
  }

  public void setCreditCardType(String creditCardType) {
    this.creditCardType = creditCardType;
  }

  public CreditCardDto creditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
    return this;
  }

  /**
   * The credit card number.
   *
   * @return creditCardNumber
   */

  @Schema(name = "creditCardNumber", example = "4111111111111111",
      description = "The credit card number.", required = false)
  public String getCreditCardNumber() {
    return creditCardNumber;
  }

  public void setCreditCardNumber(String creditCardNumber) {
    this.creditCardNumber = creditCardNumber;
  }

  public CreditCardDto creditCardExpMM(Integer creditCardExpMM) {
    this.creditCardExpMM = creditCardExpMM;
    return this;
  }

  /**
   * The credit card expiration month.
   *
   * @return creditCardExpMM
   */

  @Schema(name = "creditCardExpMM", example = "12",
      description = "The credit card expiration month.", required = false)
  public Integer getCreditCardExpMM() {
    return creditCardExpMM;
  }

  public void setCreditCardExpMM(Integer creditCardExpMM) {
    this.creditCardExpMM = creditCardExpMM;
  }

  public CreditCardDto creditCardExpYY(Integer creditCardExpYY) {
    this.creditCardExpYY = creditCardExpYY;
    return this;
  }

  /**
   * The credit card expiration year.
   *
   * @return creditCardExpYY
   */

  @Schema(name = "creditCardExpYY", example = "2020",
      description = "The credit card expiration year.", required = false)
  public Integer getCreditCardExpYY() {
    return creditCardExpYY;
  }

  public void setCreditCardExpYY(Integer creditCardExpYY) {
    this.creditCardExpYY = creditCardExpYY;
  }

  public CreditCardDto creditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
    return this;
  }

  /**
   * The credit card name.
   *
   * @return creditCardName
   */

  @Schema(name = "creditCardName", example = "John Doe", description = "The credit card name.",
      required = false)
  public String getCreditCardName() {
    return creditCardName;
  }

  public void setCreditCardName(String creditCardName) {
    this.creditCardName = creditCardName;
  }

  public CreditCardDto creditCardVV(String creditCardVV) {
    this.creditCardVV = creditCardVV;
    return this;
  }

  /**
   * The credit card verification value.
   *
   * @return creditCardVV
   */

  @Schema(name = "creditCardVV", example = "123",
      description = "The credit card verification value.", required = false)
  public String getCreditCardVV() {
    return creditCardVV;
  }

  public void setCreditCardVV(String creditCardVV) {
    this.creditCardVV = creditCardVV;
  }

  public CreditCardDto paymentAmount(java.math.BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
    return this;
  }

  /**
   * The payment amount.
   *
   * @return paymentAmount
   */

  @Schema(name = "paymentAmount", example = "100.0", description = "The payment amount.",
      required = false)
  public java.math.BigDecimal getPaymentAmount() {
    return paymentAmount;
  }

  public void setPaymentAmount(java.math.BigDecimal paymentAmount) {
    this.paymentAmount = paymentAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreditCardDto creditCard = (CreditCardDto) o;
    return Objects.equals(this.creditCardType, creditCard.creditCardType)
        && Objects.equals(this.creditCardNumber, creditCard.creditCardNumber)
        && Objects.equals(this.creditCardExpMM, creditCard.creditCardExpMM)
        && Objects.equals(this.creditCardExpYY, creditCard.creditCardExpYY)
        && Objects.equals(this.creditCardName, creditCard.creditCardName)
        && Objects.equals(this.creditCardVV, creditCard.creditCardVV)
        && Objects.equals(this.paymentAmount, creditCard.paymentAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(creditCardType, creditCardNumber, creditCardExpMM, creditCardExpYY,
        creditCardName, creditCardVV, paymentAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreditCardDto {\n");
    sb.append("    creditCardType: ").append(toIndentedString(creditCardType)).append("\n");
    sb.append("    creditCardNumber: ").append(toIndentedString(creditCardNumber)).append("\n");
    sb.append("    creditCardExpMM: ").append(toIndentedString(creditCardExpMM)).append("\n");
    sb.append("    creditCardExpYY: ").append(toIndentedString(creditCardExpYY)).append("\n");
    sb.append("    creditCardName: ").append(toIndentedString(creditCardName)).append("\n");
    sb.append("    creditCardVV: ").append(toIndentedString(creditCardVV)).append("\n");
    sb.append("    paymentAmount: ").append(toIndentedString(paymentAmount)).append("\n");
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


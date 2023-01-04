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
 * AddressDto
 */

@JsonTypeName("Address")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AddressDto {

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("address1")
  private String address1;

  @JsonProperty("address2")
  private String address2;

  @JsonProperty("city")
  private String city;

  @JsonProperty("postal")
  private String postal;

  @JsonProperty("countryName")
  private String countryName;

  public AddressDto id(Integer id) {
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

  public AddressDto address1(String address1) {
    this.address1 = address1;
    return this;
  }

  /**
   * The first line of the address.
   *
   * @return address1
   */

  @Schema(name = "address1", example = "123 Main Street",
      description = "The first line of the address.", required = false)
  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public AddressDto address2(String address2) {
    this.address2 = address2;
    return this;
  }

  /**
   * The second line of the address.
   *
   * @return address2
   */

  @Schema(name = "address2", example = "Apt 1", description = "The second line of the address.",
      required = false)
  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public AddressDto city(String city) {
    this.city = city;
    return this;
  }

  /**
   * The city of the address.
   *
   * @return city
   */

  @Schema(name = "city", example = "New York", description = "The city of the address.",
      required = false)
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public AddressDto postal(String postal) {
    this.postal = postal;
    return this;
  }

  /**
   * The zip code of the address.
   *
   * @return postal
   */

  @Schema(name = "postal", example = "10001", description = "The zip code of the address.",
      required = false)
  public String getPostal() {
    return postal;
  }

  public void setPostal(String postal) {
    this.postal = postal;
  }

  public AddressDto countryName(String countryName) {
    this.countryName = countryName;
    return this;
  }

  /**
   * The country name
   *
   * @return countryName
   */

  @Schema(name = "countryName", description = "The country name", required = false)
  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressDto address = (AddressDto) o;
    return Objects.equals(this.id, address.id) && Objects.equals(this.address1, address.address1)
        && Objects.equals(this.address2, address.address2)
        && Objects.equals(this.city, address.city) && Objects.equals(this.postal, address.postal)
        && Objects.equals(this.countryName, address.countryName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, address1, address2, city, postal, countryName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddressDto {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    address1: ").append(toIndentedString(address1)).append("\n");
    sb.append("    address2: ").append(toIndentedString(address2)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    postal: ").append(toIndentedString(postal)).append("\n");
    sb.append("    countryName: ").append(toIndentedString(countryName)).append("\n");
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


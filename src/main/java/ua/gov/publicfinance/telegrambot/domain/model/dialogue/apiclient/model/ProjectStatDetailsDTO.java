/*
 * Сервіс чатбота
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package ua.gov.publicfinance.telegrambot.domain.model.dialogue.apiclient.model;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * ProjectStatDetailsDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2021-11-10T13:37:39.038490800+02:00[Europe/Helsinki]")
public class ProjectStatDetailsDTO {
  public static final String SERIALIZED_NAME_AMOUNT = "amount";
  @SerializedName(SERIALIZED_NAME_AMOUNT)
  private Integer amount;

  public static final String SERIALIZED_NAME_CURR = "curr";
  @SerializedName(SERIALIZED_NAME_CURR)
  private String curr;

  public static final String SERIALIZED_NAME_SUM = "sum";
  @SerializedName(SERIALIZED_NAME_SUM)
  private Double sum;


  public ProjectStatDetailsDTO amount(Integer amount) {
    
    this.amount = amount;
    return this;
  }

   /**
   * Get amount
   * @return amount
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Integer getAmount() {
    return amount;
  }


  public void setAmount(Integer amount) {
    this.amount = amount;
  }


  public ProjectStatDetailsDTO curr(String curr) {
    
    this.curr = curr;
    return this;
  }

   /**
   * Get curr
   * @return curr
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getCurr() {
    return curr;
  }


  public void setCurr(String curr) {
    this.curr = curr;
  }


  public ProjectStatDetailsDTO sum(Double sum) {
    
    this.sum = sum;
    return this;
  }

   /**
   * Get sum
   * @return sum
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Double getSum() {
    return sum;
  }


  public void setSum(Double sum) {
    this.sum = sum;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProjectStatDetailsDTO projectStatDetailsDTO = (ProjectStatDetailsDTO) o;
    return Objects.equals(this.amount, projectStatDetailsDTO.amount) &&
        Objects.equals(this.curr, projectStatDetailsDTO.curr) &&
        Objects.equals(this.sum, projectStatDetailsDTO.sum);
  }

  @Override
  public int hashCode() {
    return Objects.hash(amount, curr, sum);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProjectStatDetailsDTO {\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    curr: ").append(toIndentedString(curr)).append("\n");
    sb.append("    sum: ").append(toIndentedString(sum)).append("\n");
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


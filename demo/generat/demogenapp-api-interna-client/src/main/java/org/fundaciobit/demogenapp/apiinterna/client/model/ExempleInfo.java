/*
 * API REST INTERNA de DemoGenApp
 * Conjunt de Serveis REST de DemoGenApp per ser accedits des de l'interior
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: otae@fundaciobit.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.fundaciobit.demogenapp.apiinterna.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Model de dades de Informació bàsica a retornar.
 */
@JsonPropertyOrder({
  ExempleInfo.JSON_PROPERTY_ITEM_I_D,
  ExempleInfo.JSON_PROPERTY_NAME,
  ExempleInfo.JSON_PROPERTY_VALUE,
  ExempleInfo.JSON_PROPERTY_DATE
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ExempleInfo {
  public static final String JSON_PROPERTY_ITEM_I_D = "itemID";
  private Long itemID;

  public static final String JSON_PROPERTY_NAME = "name";
  private String name;

  public static final String JSON_PROPERTY_VALUE = "value";
  private String value;

  public static final String JSON_PROPERTY_DATE = "date";
  private Date date;

  public ExempleInfo() {
  }

  public ExempleInfo itemID(Long itemID) {
    
    this.itemID = itemID;
    return this;
  }

   /**
   * Identificador intern de l&#39;objecte
   * @return itemID
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_ITEM_I_D)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Long getItemID() {
    return itemID;
  }


  @JsonProperty(JSON_PROPERTY_ITEM_I_D)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setItemID(Long itemID) {
    this.itemID = itemID;
  }


  public ExempleInfo name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Nom de l&#39;entrada
   * @return name
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public String getName() {
    return name;
  }


  @JsonProperty(JSON_PROPERTY_NAME)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setName(String name) {
    this.name = name;
  }


  public ExempleInfo value(String value) {
    
    this.value = value;
    return this;
  }

   /**
   * Valor de l&#39;entrada
   * @return value
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getValue() {
    return value;
  }


  @JsonProperty(JSON_PROPERTY_VALUE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValue(String value) {
    this.value = value;
  }


  public ExempleInfo date(Date date) {
    
    this.date = date;
    return this;
  }

   /**
   * Data en que es va fer crear aquest element
   * @return date
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Date getDate() {
    return date;
  }


  @JsonProperty(JSON_PROPERTY_DATE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setDate(Date date) {
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
    ExempleInfo exempleInfo = (ExempleInfo) o;
    return Objects.equals(this.itemID, exempleInfo.itemID) &&
        Objects.equals(this.name, exempleInfo.name) &&
        Objects.equals(this.value, exempleInfo.value) &&
        Objects.equals(this.date, exempleInfo.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(itemID, name, value, date);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExempleInfo {\n");
    sb.append("    itemID: ").append(toIndentedString(itemID)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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


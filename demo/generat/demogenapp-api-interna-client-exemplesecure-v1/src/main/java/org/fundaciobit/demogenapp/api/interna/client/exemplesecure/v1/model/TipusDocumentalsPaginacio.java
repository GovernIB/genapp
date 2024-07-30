/*
 * API REST INTERNA de DemoGenApp - Exemple de Servei Securitzat
 * Conjunt de Serveis REST de DemoGenApp per ser accedits emprant autenticació
 *
 * The version of the OpenAPI document: 1.0-SNAPSHOT
 * Contact: otae@fundaciobit.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.fundaciobit.demogenapp.api.interna.client.exemplesecure.v1.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * TipusDocumentalsPaginacio
 */
@JsonPropertyOrder({
  TipusDocumentalsPaginacio.JSON_PROPERTY_PAGE,
  TipusDocumentalsPaginacio.JSON_PROPERTY_PAGESIZE,
  TipusDocumentalsPaginacio.JSON_PROPERTY_TOTALPAGES,
  TipusDocumentalsPaginacio.JSON_PROPERTY_TOTALCOUNT,
  TipusDocumentalsPaginacio.JSON_PROPERTY_DATA
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class TipusDocumentalsPaginacio {
  public static final String JSON_PROPERTY_PAGE = "page";
  private Integer page;

  public static final String JSON_PROPERTY_PAGESIZE = "pagesize";
  private Integer pagesize;

  public static final String JSON_PROPERTY_TOTALPAGES = "totalpages";
  private Integer totalpages;

  public static final String JSON_PROPERTY_TOTALCOUNT = "totalcount";
  private Integer totalcount;

  public static final String JSON_PROPERTY_DATA = "data";
  private List<String> data = new ArrayList<>();

  public TipusDocumentalsPaginacio() {
  }

  public TipusDocumentalsPaginacio page(Integer page) {
    
    this.page = page;
    return this;
  }

   /**
   * Número pàgina. Comença per 1.
   * @return page
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getPage() {
    return page;
  }


  @JsonProperty(JSON_PROPERTY_PAGE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPage(Integer page) {
    this.page = page;
  }


  public TipusDocumentalsPaginacio pagesize(Integer pagesize) {
    
    this.pagesize = pagesize;
    return this;
  }

   /**
   * Mida de pàgina
   * @return pagesize
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_PAGESIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getPagesize() {
    return pagesize;
  }


  @JsonProperty(JSON_PROPERTY_PAGESIZE)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setPagesize(Integer pagesize) {
    this.pagesize = pagesize;
  }


  public TipusDocumentalsPaginacio totalpages(Integer totalpages) {
    
    this.totalpages = totalpages;
    return this;
  }

   /**
   * Número total de pàgines
   * @return totalpages
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TOTALPAGES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getTotalpages() {
    return totalpages;
  }


  @JsonProperty(JSON_PROPERTY_TOTALPAGES)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalpages(Integer totalpages) {
    this.totalpages = totalpages;
  }


  public TipusDocumentalsPaginacio totalcount(Integer totalcount) {
    
    this.totalcount = totalcount;
    return this;
  }

   /**
   * Numero total d&#39;elements
   * @return totalcount
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_TOTALCOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public Integer getTotalcount() {
    return totalcount;
  }


  @JsonProperty(JSON_PROPERTY_TOTALCOUNT)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setTotalcount(Integer totalcount) {
    this.totalcount = totalcount;
  }


  public TipusDocumentalsPaginacio data(List<String> data) {
    
    this.data = data;
    return this;
  }

  public TipusDocumentalsPaginacio addDataItem(String dataItem) {
    if (this.data == null) {
      this.data = new ArrayList<>();
    }
    this.data.add(dataItem);
    return this;
  }

   /**
   * Elements retornats
   * @return data
  **/
  @javax.annotation.Nonnull
  @JsonProperty(JSON_PROPERTY_DATA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)

  public List<String> getData() {
    return data;
  }


  @JsonProperty(JSON_PROPERTY_DATA)
  @JsonInclude(value = JsonInclude.Include.ALWAYS)
  public void setData(List<String> data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TipusDocumentalsPaginacio tipusDocumentalsPaginacio = (TipusDocumentalsPaginacio) o;
    return Objects.equals(this.page, tipusDocumentalsPaginacio.page) &&
        Objects.equals(this.pagesize, tipusDocumentalsPaginacio.pagesize) &&
        Objects.equals(this.totalpages, tipusDocumentalsPaginacio.totalpages) &&
        Objects.equals(this.totalcount, tipusDocumentalsPaginacio.totalcount) &&
        Objects.equals(this.data, tipusDocumentalsPaginacio.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, pagesize, totalpages, totalcount, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TipusDocumentalsPaginacio {\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    pagesize: ").append(toIndentedString(pagesize)).append("\n");
    sb.append("    totalpages: ").append(toIndentedString(totalpages)).append("\n");
    sb.append("    totalcount: ").append(toIndentedString(totalcount)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

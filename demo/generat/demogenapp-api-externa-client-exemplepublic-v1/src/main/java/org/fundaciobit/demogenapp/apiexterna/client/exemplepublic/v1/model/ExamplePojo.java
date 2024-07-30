/*
 * API REST EXTERNA de DemoGenApp - Exemple de Servei Públic
 * Conjunt de Serveis REST de DemoGenApp per ser accedits públicament
 *
 * The version of the OpenAPI document: 1.0-SNAPSHOT
 * Contact: otae@fundaciobit.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.fundaciobit.demogenapp.apiexterna.client.exemplepublic.v1.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * ExamplePojo
 */
@JsonPropertyOrder({
  ExamplePojo.JSON_PROPERTY_VERSIO
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ExamplePojo {
  public static final String JSON_PROPERTY_VERSIO = "versio";
  private String versio;

  public ExamplePojo() {
  }

  public ExamplePojo versio(String versio) {
    
    this.versio = versio;
    return this;
  }

   /**
   * Get versio
   * @return versio
  **/
  @javax.annotation.Nullable
  @JsonProperty(JSON_PROPERTY_VERSIO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getVersio() {
    return versio;
  }


  @JsonProperty(JSON_PROPERTY_VERSIO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersio(String versio) {
    this.versio = versio;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExamplePojo examplePojo = (ExamplePojo) o;
    return Objects.equals(this.versio, examplePojo.versio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(versio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ExamplePojo {\n");
    sb.append("    versio: ").append(toIndentedString(versio)).append("\n");
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

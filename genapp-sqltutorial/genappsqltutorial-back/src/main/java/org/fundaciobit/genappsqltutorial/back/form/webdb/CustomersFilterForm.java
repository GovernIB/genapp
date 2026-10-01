
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class CustomersFilterForm extends GenAppSqlTutorialBaseFilterForm implements CustomersFields {

  private java.lang.Long customeridDesde;

  public java.lang.Long getCustomeridDesde() {
    return this.customeridDesde;
  }

  public void setCustomeridDesde(java.lang.Long customeridDesde) {
    this.customeridDesde = customeridDesde;
  }


  private java.lang.Long customeridFins;

  public java.lang.Long getCustomeridFins() {
    return this.customeridFins;
  }

  public void setCustomeridFins(java.lang.Long customeridFins) {
    this.customeridFins = customeridFins;
  }


  private java.lang.String customername;

  public java.lang.String getCustomername() {
    return this.customername;
  }

  public void setCustomername(java.lang.String customername) {
    this.customername = customername;
  }


  private java.lang.String contactname;

  public java.lang.String getContactname() {
    return this.contactname;
  }

  public void setContactname(java.lang.String contactname) {
    this.contactname = contactname;
  }


  private java.lang.String address;

  public java.lang.String getAddress() {
    return this.address;
  }

  public void setAddress(java.lang.String address) {
    this.address = address;
  }


  private java.lang.String city;

  public java.lang.String getCity() {
    return this.city;
  }

  public void setCity(java.lang.String city) {
    this.city = city;
  }


  private java.lang.String country;

  public java.lang.String getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.String country) {
    this.country = country;
  }


  private java.lang.String postalcode;

  public java.lang.String getPostalcode() {
    return this.postalcode;
  }

  public void setPostalcode(java.lang.String postalcode) {
    this.postalcode = postalcode;
  }


  public CustomersFilterForm() {
  }
  
  public CustomersFilterForm(CustomersFilterForm __toClone) {
    super(__toClone);
    this.customeridDesde = __toClone.customeridDesde;
    this.customeridFins = __toClone.customeridFins;
    this.customername = __toClone.customername;
    this.contactname = __toClone.contactname;
    this.address = __toClone.address;
    this.city = __toClone.city;
    this.country = __toClone.country;
    this.postalcode = __toClone.postalcode;
  }
  
  /* ========= UTILS ========== */

  @Override
  public List<Field<?>> getDefaultFilterByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] {  }));
  }

  @Override
  public List<Field<?>> getDefaultGroupByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] {  }));
  }


  protected OrderBy[] defaultOrderBy = null;


  public OrderBy[] getDefaultOrderBy() {
    return this.defaultOrderBy;
  }

  public void setDefaultOrderBy(OrderBy[] defOrderBy) {
    this.defaultOrderBy = defOrderBy;
  }

  @Override
  public String getTableModelName() {
    return _TABLE_MODEL;
  }

   // -----------------------
   // Maps de referencies.
   // -----------------------

   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}

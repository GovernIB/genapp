
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.SuppliersFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class SuppliersFilterForm extends GenAppSqlTutorialBaseFilterForm implements SuppliersFields {

  private java.lang.Long supplieridDesde;

  public java.lang.Long getSupplieridDesde() {
    return this.supplieridDesde;
  }

  public void setSupplieridDesde(java.lang.Long supplieridDesde) {
    this.supplieridDesde = supplieridDesde;
  }


  private java.lang.Long supplieridFins;

  public java.lang.Long getSupplieridFins() {
    return this.supplieridFins;
  }

  public void setSupplieridFins(java.lang.Long supplieridFins) {
    this.supplieridFins = supplieridFins;
  }


  private java.lang.String suppliername;

  public java.lang.String getSuppliername() {
    return this.suppliername;
  }

  public void setSuppliername(java.lang.String suppliername) {
    this.suppliername = suppliername;
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


  private java.lang.String postalcode;

  public java.lang.String getPostalcode() {
    return this.postalcode;
  }

  public void setPostalcode(java.lang.String postalcode) {
    this.postalcode = postalcode;
  }


  private java.lang.String country;

  public java.lang.String getCountry() {
    return this.country;
  }

  public void setCountry(java.lang.String country) {
    this.country = country;
  }


  private java.lang.String phone;

  public java.lang.String getPhone() {
    return this.phone;
  }

  public void setPhone(java.lang.String phone) {
    this.phone = phone;
  }


  public SuppliersFilterForm() {
  }
  
  public SuppliersFilterForm(SuppliersFilterForm __toClone) {
    super(__toClone);
    this.supplieridDesde = __toClone.supplieridDesde;
    this.supplieridFins = __toClone.supplieridFins;
    this.suppliername = __toClone.suppliername;
    this.contactname = __toClone.contactname;
    this.address = __toClone.address;
    this.city = __toClone.city;
    this.postalcode = __toClone.postalcode;
    this.country = __toClone.country;
    this.phone = __toClone.phone;
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

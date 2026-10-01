
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.EmployeesFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class EmployeesFilterForm extends GenAppSqlTutorialBaseFilterForm implements EmployeesFields {

  private java.lang.Long employeeidDesde;

  public java.lang.Long getEmployeeidDesde() {
    return this.employeeidDesde;
  }

  public void setEmployeeidDesde(java.lang.Long employeeidDesde) {
    this.employeeidDesde = employeeidDesde;
  }


  private java.lang.Long employeeidFins;

  public java.lang.Long getEmployeeidFins() {
    return this.employeeidFins;
  }

  public void setEmployeeidFins(java.lang.Long employeeidFins) {
    this.employeeidFins = employeeidFins;
  }


  private java.lang.String lastname;

  public java.lang.String getLastname() {
    return this.lastname;
  }

  public void setLastname(java.lang.String lastname) {
    this.lastname = lastname;
  }


  private java.lang.String firstname;

  public java.lang.String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(java.lang.String firstname) {
    this.firstname = firstname;
  }


  private java.sql.Date birthdateDesde;

  public java.sql.Date getBirthdateDesde() {
    return this.birthdateDesde;
  }

  public void setBirthdateDesde(java.sql.Date birthdateDesde) {
    this.birthdateDesde = birthdateDesde;
  }


  private java.sql.Date birthdateFins;

  public java.sql.Date getBirthdateFins() {
    return this.birthdateFins;
  }

  public void setBirthdateFins(java.sql.Date birthdateFins) {
    this.birthdateFins = birthdateFins;
  }


  private java.lang.String photo;

  public java.lang.String getPhoto() {
    return this.photo;
  }

  public void setPhoto(java.lang.String photo) {
    this.photo = photo;
  }


  private java.lang.String notes;

  public java.lang.String getNotes() {
    return this.notes;
  }

  public void setNotes(java.lang.String notes) {
    this.notes = notes;
  }


  public EmployeesFilterForm() {
  }
  
  public EmployeesFilterForm(EmployeesFilterForm __toClone) {
    super(__toClone);
    this.employeeidDesde = __toClone.employeeidDesde;
    this.employeeidFins = __toClone.employeeidFins;
    this.lastname = __toClone.lastname;
    this.firstname = __toClone.firstname;
    this.birthdateDesde = __toClone.birthdateDesde;
    this.birthdateFins = __toClone.birthdateFins;
    this.photo = __toClone.photo;
    this.notes = __toClone.notes;
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

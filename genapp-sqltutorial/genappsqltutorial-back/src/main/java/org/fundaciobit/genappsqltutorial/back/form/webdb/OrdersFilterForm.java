
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.OrdersFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class OrdersFilterForm extends GenAppSqlTutorialBaseFilterForm implements OrdersFields {

  private java.lang.Long orderidDesde;

  public java.lang.Long getOrderidDesde() {
    return this.orderidDesde;
  }

  public void setOrderidDesde(java.lang.Long orderidDesde) {
    this.orderidDesde = orderidDesde;
  }


  private java.lang.Long orderidFins;

  public java.lang.Long getOrderidFins() {
    return this.orderidFins;
  }

  public void setOrderidFins(java.lang.Long orderidFins) {
    this.orderidFins = orderidFins;
  }


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


  private java.sql.Date orderdateDesde;

  public java.sql.Date getOrderdateDesde() {
    return this.orderdateDesde;
  }

  public void setOrderdateDesde(java.sql.Date orderdateDesde) {
    this.orderdateDesde = orderdateDesde;
  }


  private java.sql.Date orderdateFins;

  public java.sql.Date getOrderdateFins() {
    return this.orderdateFins;
  }

  public void setOrderdateFins(java.sql.Date orderdateFins) {
    this.orderdateFins = orderdateFins;
  }


  private java.lang.Long shipperidDesde;

  public java.lang.Long getShipperidDesde() {
    return this.shipperidDesde;
  }

  public void setShipperidDesde(java.lang.Long shipperidDesde) {
    this.shipperidDesde = shipperidDesde;
  }


  private java.lang.Long shipperidFins;

  public java.lang.Long getShipperidFins() {
    return this.shipperidFins;
  }

  public void setShipperidFins(java.lang.Long shipperidFins) {
    this.shipperidFins = shipperidFins;
  }


  public OrdersFilterForm() {
  }
  
  public OrdersFilterForm(OrdersFilterForm __toClone) {
    super(__toClone);
    this.orderidDesde = __toClone.orderidDesde;
    this.orderidFins = __toClone.orderidFins;
    this.customeridDesde = __toClone.customeridDesde;
    this.customeridFins = __toClone.customeridFins;
    this.employeeidDesde = __toClone.employeeidDesde;
    this.employeeidFins = __toClone.employeeidFins;
    this.orderdateDesde = __toClone.orderdateDesde;
    this.orderdateFins = __toClone.orderdateFins;
    this.shipperidDesde = __toClone.shipperidDesde;
    this.shipperidFins = __toClone.shipperidFins;
    this.mapOfCustomersForCustomerid = __toClone.mapOfCustomersForCustomerid;
    this.mapOfEmployeesForEmployeeid = __toClone.mapOfEmployeesForEmployeeid;
    this.mapOfSuppliersForShipperid = __toClone.mapOfSuppliersForShipperid;
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
  private Map<String, String> mapOfCustomersForCustomerid;

  public Map<String, String> getMapOfCustomersForCustomerid() {
    return this.mapOfCustomersForCustomerid;
  }

  public void setMapOfCustomersForCustomerid(Map<String, String> mapOfCustomersForCustomerid) {
    this.mapOfCustomersForCustomerid = mapOfCustomersForCustomerid;
  }



  private Map<String, String> mapOfEmployeesForEmployeeid;

  public Map<String, String> getMapOfEmployeesForEmployeeid() {
    return this.mapOfEmployeesForEmployeeid;
  }

  public void setMapOfEmployeesForEmployeeid(Map<String, String> mapOfEmployeesForEmployeeid) {
    this.mapOfEmployeesForEmployeeid = mapOfEmployeesForEmployeeid;
  }



  private Map<String, String> mapOfSuppliersForShipperid;

  public Map<String, String> getMapOfSuppliersForShipperid() {
    return this.mapOfSuppliersForShipperid;
  }

  public void setMapOfSuppliersForShipperid(Map<String, String> mapOfSuppliersForShipperid) {
    this.mapOfSuppliersForShipperid = mapOfSuppliersForShipperid;
  }




   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}


package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.OrderDetailsFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class OrderDetailsFilterForm extends GenAppSqlTutorialBaseFilterForm implements OrderDetailsFields {

  private java.lang.Long orderdetailidDesde;

  public java.lang.Long getOrderdetailidDesde() {
    return this.orderdetailidDesde;
  }

  public void setOrderdetailidDesde(java.lang.Long orderdetailidDesde) {
    this.orderdetailidDesde = orderdetailidDesde;
  }


  private java.lang.Long orderdetailidFins;

  public java.lang.Long getOrderdetailidFins() {
    return this.orderdetailidFins;
  }

  public void setOrderdetailidFins(java.lang.Long orderdetailidFins) {
    this.orderdetailidFins = orderdetailidFins;
  }


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


  private java.lang.Long productidDesde;

  public java.lang.Long getProductidDesde() {
    return this.productidDesde;
  }

  public void setProductidDesde(java.lang.Long productidDesde) {
    this.productidDesde = productidDesde;
  }


  private java.lang.Long productidFins;

  public java.lang.Long getProductidFins() {
    return this.productidFins;
  }

  public void setProductidFins(java.lang.Long productidFins) {
    this.productidFins = productidFins;
  }


  private java.lang.Integer quantityDesde;

  public java.lang.Integer getQuantityDesde() {
    return this.quantityDesde;
  }

  public void setQuantityDesde(java.lang.Integer quantityDesde) {
    this.quantityDesde = quantityDesde;
  }


  private java.lang.Integer quantityFins;

  public java.lang.Integer getQuantityFins() {
    return this.quantityFins;
  }

  public void setQuantityFins(java.lang.Integer quantityFins) {
    this.quantityFins = quantityFins;
  }


  public OrderDetailsFilterForm() {
  }
  
  public OrderDetailsFilterForm(OrderDetailsFilterForm __toClone) {
    super(__toClone);
    this.orderdetailidDesde = __toClone.orderdetailidDesde;
    this.orderdetailidFins = __toClone.orderdetailidFins;
    this.orderidDesde = __toClone.orderidDesde;
    this.orderidFins = __toClone.orderidFins;
    this.productidDesde = __toClone.productidDesde;
    this.productidFins = __toClone.productidFins;
    this.quantityDesde = __toClone.quantityDesde;
    this.quantityFins = __toClone.quantityFins;
    this.mapOfOrdersForOrderid = __toClone.mapOfOrdersForOrderid;
    this.mapOfProductsForProductid = __toClone.mapOfProductsForProductid;
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
  private Map<String, String> mapOfOrdersForOrderid;

  public Map<String, String> getMapOfOrdersForOrderid() {
    return this.mapOfOrdersForOrderid;
  }

  public void setMapOfOrdersForOrderid(Map<String, String> mapOfOrdersForOrderid) {
    this.mapOfOrdersForOrderid = mapOfOrdersForOrderid;
  }



  private Map<String, String> mapOfProductsForProductid;

  public Map<String, String> getMapOfProductsForProductid() {
    return this.mapOfProductsForProductid;
  }

  public void setMapOfProductsForProductid(Map<String, String> mapOfProductsForProductid) {
    this.mapOfProductsForProductid = mapOfProductsForProductid;
  }




   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}

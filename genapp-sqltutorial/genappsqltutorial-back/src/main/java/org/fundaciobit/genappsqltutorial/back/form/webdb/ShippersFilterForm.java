
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.ShippersFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class ShippersFilterForm extends GenAppSqlTutorialBaseFilterForm implements ShippersFields {

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


  private java.lang.String shippername;

  public java.lang.String getShippername() {
    return this.shippername;
  }

  public void setShippername(java.lang.String shippername) {
    this.shippername = shippername;
  }


  private java.lang.String phone;

  public java.lang.String getPhone() {
    return this.phone;
  }

  public void setPhone(java.lang.String phone) {
    this.phone = phone;
  }


  public ShippersFilterForm() {
  }
  
  public ShippersFilterForm(ShippersFilterForm __toClone) {
    super(__toClone);
    this.shipperidDesde = __toClone.shipperidDesde;
    this.shipperidFins = __toClone.shipperidFins;
    this.shippername = __toClone.shippername;
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

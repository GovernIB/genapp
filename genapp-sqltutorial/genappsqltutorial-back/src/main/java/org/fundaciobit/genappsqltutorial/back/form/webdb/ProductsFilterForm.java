
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.ProductsFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class ProductsFilterForm extends GenAppSqlTutorialBaseFilterForm implements ProductsFields {

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


  private java.lang.String productname;

  public java.lang.String getProductname() {
    return this.productname;
  }

  public void setProductname(java.lang.String productname) {
    this.productname = productname;
  }


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


  private java.lang.Long categoryidDesde;

  public java.lang.Long getCategoryidDesde() {
    return this.categoryidDesde;
  }

  public void setCategoryidDesde(java.lang.Long categoryidDesde) {
    this.categoryidDesde = categoryidDesde;
  }


  private java.lang.Long categoryidFins;

  public java.lang.Long getCategoryidFins() {
    return this.categoryidFins;
  }

  public void setCategoryidFins(java.lang.Long categoryidFins) {
    this.categoryidFins = categoryidFins;
  }


  private java.lang.String unit;

  public java.lang.String getUnit() {
    return this.unit;
  }

  public void setUnit(java.lang.String unit) {
    this.unit = unit;
  }


  private java.lang.Integer priceDesde;

  public java.lang.Integer getPriceDesde() {
    return this.priceDesde;
  }

  public void setPriceDesde(java.lang.Integer priceDesde) {
    this.priceDesde = priceDesde;
  }


  private java.lang.Integer priceFins;

  public java.lang.Integer getPriceFins() {
    return this.priceFins;
  }

  public void setPriceFins(java.lang.Integer priceFins) {
    this.priceFins = priceFins;
  }


  public ProductsFilterForm() {
  }
  
  public ProductsFilterForm(ProductsFilterForm __toClone) {
    super(__toClone);
    this.productidDesde = __toClone.productidDesde;
    this.productidFins = __toClone.productidFins;
    this.productname = __toClone.productname;
    this.supplieridDesde = __toClone.supplieridDesde;
    this.supplieridFins = __toClone.supplieridFins;
    this.categoryidDesde = __toClone.categoryidDesde;
    this.categoryidFins = __toClone.categoryidFins;
    this.unit = __toClone.unit;
    this.priceDesde = __toClone.priceDesde;
    this.priceFins = __toClone.priceFins;
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

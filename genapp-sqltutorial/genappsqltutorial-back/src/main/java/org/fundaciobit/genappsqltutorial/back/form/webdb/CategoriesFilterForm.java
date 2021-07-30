
package org.fundaciobit.genappsqltutorial.back.form.webdb;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseFilterForm;

import org.fundaciobit.genappsqltutorial.model.fields.CategoriesFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class CategoriesFilterForm extends GenAppSqlTutorialBaseFilterForm implements CategoriesFields {

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


  private java.lang.String categoryName;

  public java.lang.String getCategoryName() {
    return this.categoryName;
  }

  public void setCategoryName(java.lang.String categoryName) {
    this.categoryName = categoryName;
  }


  private java.lang.String description;

  public java.lang.String getDescription() {
    return this.description;
  }

  public void setDescription(java.lang.String description) {
    this.description = description;
  }


  public CategoriesFilterForm() {
  }
  
  public CategoriesFilterForm(CategoriesFilterForm __toClone) {
    super(__toClone);
    this.categoryidDesde = __toClone.categoryidDesde;
    this.categoryidFins = __toClone.categoryidFins;
    this.categoryName = __toClone.categoryName;
    this.description = __toClone.description;
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

package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.CategoriesJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class CategoriesForm extends GenAppSqlTutorialBaseForm {
  
  private CategoriesJPA categories;
  
  public CategoriesForm() {
  }
  
  public CategoriesForm(CategoriesForm __toClone) {
    super(__toClone);
      this.categories = __toClone.categories;
  }
  
  public CategoriesForm(CategoriesJPA categories, boolean nou) {
    super(nou);
    this.categories = categories;
  }
  
  public CategoriesJPA getCategories() {
    return categories;
  }
  public void setCategories(CategoriesJPA categories) {
    this.categories = categories;
  }
  
  
  
} // Final de Classe 

package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.ProductsJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class ProductsForm extends GenAppSqlTutorialBaseForm {
  
  private ProductsJPA products;
  
  public ProductsForm() {
  }
  
  public ProductsForm(ProductsForm __toClone) {
    super(__toClone);
      this.products = __toClone.products;
  }
  
  public ProductsForm(ProductsJPA products, boolean nou) {
    super(nou);
    this.products = products;
  }
  
  public ProductsJPA getProducts() {
    return products;
  }
  public void setProducts(ProductsJPA products) {
    this.products = products;
  }
  
  
  
} // Final de Classe 

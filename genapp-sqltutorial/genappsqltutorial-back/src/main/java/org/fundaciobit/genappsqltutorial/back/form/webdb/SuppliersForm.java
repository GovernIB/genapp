package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.SuppliersJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class SuppliersForm extends GenAppSqlTutorialBaseForm {
  
  private SuppliersJPA suppliers;
  
  public SuppliersForm() {
  }
  
  public SuppliersForm(SuppliersForm __toClone) {
    super(__toClone);
      this.suppliers = __toClone.suppliers;
  }
  
  public SuppliersForm(SuppliersJPA suppliers, boolean nou) {
    super(nou);
    this.suppliers = suppliers;
  }
  
  public SuppliersJPA getSuppliers() {
    return suppliers;
  }
  public void setSuppliers(SuppliersJPA suppliers) {
    this.suppliers = suppliers;
  }
  
  
  
} // Final de Classe 

package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.CustomersJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class CustomersForm extends GenAppSqlTutorialBaseForm {
  
  private CustomersJPA customers;
  
  public CustomersForm() {
  }
  
  public CustomersForm(CustomersForm __toClone) {
    super(__toClone);
      this.customers = __toClone.customers;
  }
  
  public CustomersForm(CustomersJPA customers, boolean nou) {
    super(nou);
    this.customers = customers;
  }
  
  public CustomersJPA getCustomers() {
    return customers;
  }
  public void setCustomers(CustomersJPA customers) {
    this.customers = customers;
  }
  
  
  
} // Final de Classe 

package org.fundaciobit.genappsqltutorial.back.form.webdb;

import org.fundaciobit.genappsqltutorial.back.form.GenAppSqlTutorialBaseForm;
import org.fundaciobit.genappsqltutorial.persistence.ShippersJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class ShippersForm extends GenAppSqlTutorialBaseForm {
  
  private ShippersJPA shippers;
  
  public ShippersForm() {
  }
  
  public ShippersForm(ShippersForm __toClone) {
    super(__toClone);
      this.shippers = __toClone.shippers;
  }
  
  public ShippersForm(ShippersJPA shippers, boolean nou) {
    super(nou);
    this.shippers = shippers;
  }
  
  public ShippersJPA getShippers() {
    return shippers;
  }
  public void setShippers(ShippersJPA shippers) {
    this.shippers = shippers;
  }
  
  
  
} // Final de Classe 

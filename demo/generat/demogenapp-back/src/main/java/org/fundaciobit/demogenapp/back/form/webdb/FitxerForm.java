package org.fundaciobit.demogenapp.back.form.webdb;

import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseForm;
import org.fundaciobit.demogenapp.persistence.FitxerJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class FitxerForm extends DemoGenAppBaseForm {
  
  private FitxerJPA fitxer;
  
  public FitxerForm() {
  }
  
  public FitxerForm(FitxerForm __toClone) {
    super(__toClone);
      this.fitxer = __toClone.fitxer;
  }
  
  public FitxerForm(FitxerJPA fitxer, boolean nou) {
    super(nou);
    this.fitxer = fitxer;
  }
  
  public FitxerJPA getFitxer() {
    return fitxer;
  }
  public void setFitxer(FitxerJPA fitxer) {
    this.fitxer = fitxer;
  }
  
  
  
} // Final de Classe 

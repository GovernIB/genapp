package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseForm;
import org.fundaciobit.demogenapp.persistence.AssignaturaJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class AssignaturaForm extends DemoGenAppBaseForm {
  
  private AssignaturaJPA assignatura;
  
  public AssignaturaForm() {
  }
  
  public AssignaturaForm(AssignaturaForm __toClone) {
    super(__toClone);
      this.assignatura = __toClone.assignatura;
    this.listOfValuesForDiaSetmana = __toClone.listOfValuesForDiaSetmana;
  }
  
  public AssignaturaForm(AssignaturaJPA assignatura, boolean nou) {
    super(nou);
    this.assignatura = assignatura;
  }
  
  public AssignaturaJPA getAssignatura() {
    return assignatura;
  }
  public void setAssignatura(AssignaturaJPA assignatura) {
    this.assignatura = assignatura;
  }
  
  
  private List<StringKeyValue> listOfValuesForDiaSetmana;

  public List<StringKeyValue> getListOfValuesForDiaSetmana() {
    return this.listOfValuesForDiaSetmana;
  }

  public void setListOfValuesForDiaSetmana(List<StringKeyValue> listOfValuesForDiaSetmana) {
    this.listOfValuesForDiaSetmana = listOfValuesForDiaSetmana;
  }



  
} // Final de Classe 

package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseForm;
import org.fundaciobit.demogenapp.persistence.AssignaturaAlumneJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class AssignaturaAlumneForm extends DemoGenAppBaseForm {
  
  private AssignaturaAlumneJPA assignaturaAlumne;
  
  public AssignaturaAlumneForm() {
  }
  
  public AssignaturaAlumneForm(AssignaturaAlumneForm __toClone) {
    super(__toClone);
      this.assignaturaAlumne = __toClone.assignaturaAlumne;
    this.listOfAlumneForAlumneID = __toClone.listOfAlumneForAlumneID;
    this.listOfAssignaturaForAssignaturaID = __toClone.listOfAssignaturaForAssignaturaID;
  }
  
  public AssignaturaAlumneForm(AssignaturaAlumneJPA assignaturaAlumne, boolean nou) {
    super(nou);
    this.assignaturaAlumne = assignaturaAlumne;
  }
  
  public AssignaturaAlumneJPA getAssignaturaAlumne() {
    return assignaturaAlumne;
  }
  public void setAssignaturaAlumne(AssignaturaAlumneJPA assignaturaAlumne) {
    this.assignaturaAlumne = assignaturaAlumne;
  }
  
  
  private List<StringKeyValue> listOfAlumneForAlumneID;

  public List<StringKeyValue> getListOfAlumneForAlumneID() {
    return this.listOfAlumneForAlumneID;
  }

  public void setListOfAlumneForAlumneID(List<StringKeyValue> listOfAlumneForAlumneID) {
    this.listOfAlumneForAlumneID = listOfAlumneForAlumneID;
  }



  private List<StringKeyValue> listOfAssignaturaForAssignaturaID;

  public List<StringKeyValue> getListOfAssignaturaForAssignaturaID() {
    return this.listOfAssignaturaForAssignaturaID;
  }

  public void setListOfAssignaturaForAssignaturaID(List<StringKeyValue> listOfAssignaturaForAssignaturaID) {
    this.listOfAssignaturaForAssignaturaID = listOfAssignaturaForAssignaturaID;
  }



  
} // Final de Classe 

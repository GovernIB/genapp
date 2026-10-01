package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.List;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseForm;
import org.fundaciobit.demogenapp.persistence.AlumneJPA;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * Created by GenApp. Do not modify.
 */
public class AlumneForm extends DemoGenAppBaseForm {
  
  private AlumneJPA alumne;
  
  
  private CommonsMultipartFile fotoID;
  private boolean fotoIDDelete;
  
  public AlumneForm() {
  }
  
  public AlumneForm(AlumneForm __toClone) {
    super(__toClone);
      this.alumne = __toClone.alumne;
    this.listOfIdiomaForIdiomaID = __toClone.listOfIdiomaForIdiomaID;
    this.listOfValuesForSexe = __toClone.listOfValuesForSexe;
    this.listOfTraduccioForTitolAcademicID = __toClone.listOfTraduccioForTitolAcademicID;
  }
  
  public AlumneForm(AlumneJPA alumne, boolean nou) {
    super(nou);
    this.alumne = alumne;
  }
  
  public AlumneJPA getAlumne() {
    return alumne;
  }
  public void setAlumne(AlumneJPA alumne) {
    this.alumne = alumne;
  }
  
  java.util.List<org.fundaciobit.demogenapp.model.entity.Idioma> idiomesTraduccio = null;

  public java.util.List<org.fundaciobit.demogenapp.model.entity.Idioma> getIdiomesTraduccio() {
    return idiomesTraduccio;
  }

  public void setIdiomesTraduccio(java.util.List<org.fundaciobit.demogenapp.model.entity.Idioma> idiomesTraduccio) {
    this.idiomesTraduccio = idiomesTraduccio;
  }
  
  public CommonsMultipartFile getFotoID() {
    return fotoID;
  }
  
   public void setFotoID(CommonsMultipartFile fotoID) {
    this.fotoID = fotoID;
  }
  public boolean isFotoIDDelete() {
    return fotoIDDelete;
  }
  
  public void setFotoIDDelete(boolean fotoIDDelete) {
    this.fotoIDDelete = fotoIDDelete;
   }
  private List<StringKeyValue> listOfIdiomaForIdiomaID;

  public List<StringKeyValue> getListOfIdiomaForIdiomaID() {
    return this.listOfIdiomaForIdiomaID;
  }

  public void setListOfIdiomaForIdiomaID(List<StringKeyValue> listOfIdiomaForIdiomaID) {
    this.listOfIdiomaForIdiomaID = listOfIdiomaForIdiomaID;
  }



  private List<StringKeyValue> listOfValuesForSexe;

  public List<StringKeyValue> getListOfValuesForSexe() {
    return this.listOfValuesForSexe;
  }

  public void setListOfValuesForSexe(List<StringKeyValue> listOfValuesForSexe) {
    this.listOfValuesForSexe = listOfValuesForSexe;
  }



  private List<StringKeyValue> listOfTraduccioForTitolAcademicID;

  public List<StringKeyValue> getListOfTraduccioForTitolAcademicID() {
    return this.listOfTraduccioForTitolAcademicID;
  }

  public void setListOfTraduccioForTitolAcademicID(List<StringKeyValue> listOfTraduccioForTitolAcademicID) {
    this.listOfTraduccioForTitolAcademicID = listOfTraduccioForTitolAcademicID;
  }



  
} // Final de Classe 

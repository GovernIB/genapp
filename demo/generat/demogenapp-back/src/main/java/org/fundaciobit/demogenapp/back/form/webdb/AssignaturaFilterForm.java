
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseFilterForm;

import org.fundaciobit.demogenapp.model.fields.AssignaturaFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AssignaturaFilterForm extends DemoGenAppBaseFilterForm implements AssignaturaFields {

  private java.lang.Long assignaturaIDDesde;

  public java.lang.Long getAssignaturaIDDesde() {
    return this.assignaturaIDDesde;
  }

  public void setAssignaturaIDDesde(java.lang.Long assignaturaIDDesde) {
    this.assignaturaIDDesde = assignaturaIDDesde;
  }


  private java.lang.Long assignaturaIDFins;

  public java.lang.Long getAssignaturaIDFins() {
    return this.assignaturaIDFins;
  }

  public void setAssignaturaIDFins(java.lang.Long assignaturaIDFins) {
    this.assignaturaIDFins = assignaturaIDFins;
  }


  private java.lang.String nom;

  public java.lang.String getNom() {
    return this.nom;
  }

  public void setNom(java.lang.String nom) {
    this.nom = nom;
  }


  private java.lang.Integer creditsDesde;

  public java.lang.Integer getCreditsDesde() {
    return this.creditsDesde;
  }

  public void setCreditsDesde(java.lang.Integer creditsDesde) {
    this.creditsDesde = creditsDesde;
  }


  private java.lang.Integer creditsFins;

  public java.lang.Integer getCreditsFins() {
    return this.creditsFins;
  }

  public void setCreditsFins(java.lang.Integer creditsFins) {
    this.creditsFins = creditsFins;
  }


  private java.util.List<java.lang.Integer> diaSetmanaSelect;

  public java.util.List<java.lang.Integer> getDiaSetmanaSelect() {
    return this.diaSetmanaSelect;
  }

  public void setDiaSetmanaSelect(java.util.List<java.lang.Integer> diaSetmanaSelect) {
    this.diaSetmanaSelect = diaSetmanaSelect;
  }


  private java.sql.Time horaDesde;

  public java.sql.Time getHoraDesde() {
    return this.horaDesde;
  }

  public void setHoraDesde(java.sql.Time horaDesde) {
    this.horaDesde = horaDesde;
  }


  private java.sql.Time horaFins;

  public java.sql.Time getHoraFins() {
    return this.horaFins;
  }

  public void setHoraFins(java.sql.Time horaFins) {
    this.horaFins = horaFins;
  }


  private java.lang.String descripcio;

  public java.lang.String getDescripcio() {
    return this.descripcio;
  }

  public void setDescripcio(java.lang.String descripcio) {
    this.descripcio = descripcio;
  }


  public AssignaturaFilterForm() {
  }
  
  public AssignaturaFilterForm(AssignaturaFilterForm __toClone) {
    super(__toClone);
    this.assignaturaIDDesde = __toClone.assignaturaIDDesde;
    this.assignaturaIDFins = __toClone.assignaturaIDFins;
    this.nom = __toClone.nom;
    this.creditsDesde = __toClone.creditsDesde;
    this.creditsFins = __toClone.creditsFins;
    this.diaSetmanaSelect = __toClone.diaSetmanaSelect;
    this.horaDesde = __toClone.horaDesde;
    this.horaFins = __toClone.horaFins;
    this.descripcio = __toClone.descripcio;
    this.mapOfValuesForDiaSetmana = __toClone.mapOfValuesForDiaSetmana;
  }
  
  /* ========= UTILS ========== */

  @Override
  public List<Field<?>> getDefaultFilterByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { NOM ,DIASETMANA ,HORA ,DESCRIPCIO }));
  }

  @Override
  public List<Field<?>> getDefaultGroupByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { DIASETMANA ,HORA }));
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
  private Map<String, String> mapOfValuesForDiaSetmana;

  public Map<String, String> getMapOfValuesForDiaSetmana() {
    return this.mapOfValuesForDiaSetmana;
  }

  public void setMapOfValuesForDiaSetmana(Map<String, String> mapOfValuesForDiaSetmana) {
    this.mapOfValuesForDiaSetmana = mapOfValuesForDiaSetmana;
  }




   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}

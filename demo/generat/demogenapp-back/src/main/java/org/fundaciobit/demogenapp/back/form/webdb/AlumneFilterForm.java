
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseFilterForm;

import org.fundaciobit.demogenapp.model.fields.AlumneFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AlumneFilterForm extends DemoGenAppBaseFilterForm implements AlumneFields {

  private java.lang.Long alumneIDDesde;

  public java.lang.Long getAlumneIDDesde() {
    return this.alumneIDDesde;
  }

  public void setAlumneIDDesde(java.lang.Long alumneIDDesde) {
    this.alumneIDDesde = alumneIDDesde;
  }


  private java.lang.Long alumneIDFins;

  public java.lang.Long getAlumneIDFins() {
    return this.alumneIDFins;
  }

  public void setAlumneIDFins(java.lang.Long alumneIDFins) {
    this.alumneIDFins = alumneIDFins;
  }


  private java.lang.String nom;

  public java.lang.String getNom() {
    return this.nom;
  }

  public void setNom(java.lang.String nom) {
    this.nom = nom;
  }


  private java.lang.String idiomaID;

  public java.lang.String getIdiomaID() {
    return this.idiomaID;
  }

  public void setIdiomaID(java.lang.String idiomaID) {
    this.idiomaID = idiomaID;
  }


  private java.sql.Date dataNaixementDesde;

  public java.sql.Date getDataNaixementDesde() {
    return this.dataNaixementDesde;
  }

  public void setDataNaixementDesde(java.sql.Date dataNaixementDesde) {
    this.dataNaixementDesde = dataNaixementDesde;
  }


  private java.sql.Date dataNaixementFins;

  public java.sql.Date getDataNaixementFins() {
    return this.dataNaixementFins;
  }

  public void setDataNaixementFins(java.sql.Date dataNaixementFins) {
    this.dataNaixementFins = dataNaixementFins;
  }


  private java.sql.Timestamp darrerAccesDesde;

  public java.sql.Timestamp getDarrerAccesDesde() {
    return this.darrerAccesDesde;
  }

  public void setDarrerAccesDesde(java.sql.Timestamp darrerAccesDesde) {
    this.darrerAccesDesde = darrerAccesDesde;
  }


  private java.sql.Timestamp darrerAccesFins;

  public java.sql.Timestamp getDarrerAccesFins() {
    return this.darrerAccesFins;
  }

  public void setDarrerAccesFins(java.sql.Timestamp darrerAccesFins) {
    this.darrerAccesFins = darrerAccesFins;
  }


  private java.lang.Long titolAcademicIDDesde;

  public java.lang.Long getTitolAcademicIDDesde() {
    return this.titolAcademicIDDesde;
  }

  public void setTitolAcademicIDDesde(java.lang.Long titolAcademicIDDesde) {
    this.titolAcademicIDDesde = titolAcademicIDDesde;
  }


  private java.lang.Long titolAcademicIDFins;

  public java.lang.Long getTitolAcademicIDFins() {
    return this.titolAcademicIDFins;
  }

  public void setTitolAcademicIDFins(java.lang.Long titolAcademicIDFins) {
    this.titolAcademicIDFins = titolAcademicIDFins;
  }


  private java.sql.Time despertadorDesde;

  public java.sql.Time getDespertadorDesde() {
    return this.despertadorDesde;
  }

  public void setDespertadorDesde(java.sql.Time despertadorDesde) {
    this.despertadorDesde = despertadorDesde;
  }


  private java.sql.Time despertadorFins;

  public java.sql.Time getDespertadorFins() {
    return this.despertadorFins;
  }

  public void setDespertadorFins(java.sql.Time despertadorFins) {
    this.despertadorFins = despertadorFins;
  }


  private java.lang.String paginaWeb;

  public java.lang.String getPaginaWeb() {
    return this.paginaWeb;
  }

  public void setPaginaWeb(java.lang.String paginaWeb) {
    this.paginaWeb = paginaWeb;
  }


  private java.lang.String descripcio;

  public java.lang.String getDescripcio() {
    return this.descripcio;
  }

  public void setDescripcio(java.lang.String descripcio) {
    this.descripcio = descripcio;
  }


  public AlumneFilterForm() {
  }
  
  public AlumneFilterForm(AlumneFilterForm __toClone) {
    super(__toClone);
    this.alumneIDDesde = __toClone.alumneIDDesde;
    this.alumneIDFins = __toClone.alumneIDFins;
    this.nom = __toClone.nom;
    this.idiomaID = __toClone.idiomaID;
    this.dataNaixementDesde = __toClone.dataNaixementDesde;
    this.dataNaixementFins = __toClone.dataNaixementFins;
    this.darrerAccesDesde = __toClone.darrerAccesDesde;
    this.darrerAccesFins = __toClone.darrerAccesFins;
    this.titolAcademicIDDesde = __toClone.titolAcademicIDDesde;
    this.titolAcademicIDFins = __toClone.titolAcademicIDFins;
    this.despertadorDesde = __toClone.despertadorDesde;
    this.despertadorFins = __toClone.despertadorFins;
    this.paginaWeb = __toClone.paginaWeb;
    this.descripcio = __toClone.descripcio;
    this.mapOfIdiomaForIdiomaID = __toClone.mapOfIdiomaForIdiomaID;
    this.mapOfValuesForSexe = __toClone.mapOfValuesForSexe;
    this.mapOfTraduccioForTitolAcademicID = __toClone.mapOfTraduccioForTitolAcademicID;
  }
  
  /* ========= UTILS ========== */

  @Override
  public List<Field<?>> getDefaultFilterByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { NOM ,DATANAIXEMENT ,DARRERACCES }));
  }

  @Override
  public List<Field<?>> getDefaultGroupByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { IDIOMAID ,DATANAIXEMENT ,ACTIU }));
  }


  protected OrderBy[] defaultOrderBy = new OrderBy[] {new OrderBy(NOM )};


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
  private Map<String, String> mapOfIdiomaForIdiomaID;

  public Map<String, String> getMapOfIdiomaForIdiomaID() {
    return this.mapOfIdiomaForIdiomaID;
  }

  public void setMapOfIdiomaForIdiomaID(Map<String, String> mapOfIdiomaForIdiomaID) {
    this.mapOfIdiomaForIdiomaID = mapOfIdiomaForIdiomaID;
  }



  private Map<String, String> mapOfValuesForSexe;

  public Map<String, String> getMapOfValuesForSexe() {
    return this.mapOfValuesForSexe;
  }

  public void setMapOfValuesForSexe(Map<String, String> mapOfValuesForSexe) {
    this.mapOfValuesForSexe = mapOfValuesForSexe;
  }



  private Map<String, String> mapOfTraduccioForTitolAcademicID;

  public Map<String, String> getMapOfTraduccioForTitolAcademicID() {
    return this.mapOfTraduccioForTitolAcademicID;
  }

  public void setMapOfTraduccioForTitolAcademicID(Map<String, String> mapOfTraduccioForTitolAcademicID) {
    this.mapOfTraduccioForTitolAcademicID = mapOfTraduccioForTitolAcademicID;
  }




   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}

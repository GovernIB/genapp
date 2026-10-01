
package org.fundaciobit.demogenapp.back.form.webdb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.demogenapp.back.form.DemoGenAppBaseFilterForm;

import org.fundaciobit.demogenapp.model.fields.AssignaturaAlumneFields;

/**
 *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! 
 * @author GenApp
 * 
 */
@Component
public class AssignaturaAlumneFilterForm extends DemoGenAppBaseFilterForm implements AssignaturaAlumneFields {

  private java.lang.Long idDesde;

  public java.lang.Long getIdDesde() {
    return this.idDesde;
  }

  public void setIdDesde(java.lang.Long idDesde) {
    this.idDesde = idDesde;
  }


  private java.lang.Long idFins;

  public java.lang.Long getIdFins() {
    return this.idFins;
  }

  public void setIdFins(java.lang.Long idFins) {
    this.idFins = idFins;
  }


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


  private java.lang.Double notaDesde;

  public java.lang.Double getNotaDesde() {
    return this.notaDesde;
  }

  public void setNotaDesde(java.lang.Double notaDesde) {
    this.notaDesde = notaDesde;
  }


  private java.lang.Double notaFins;

  public java.lang.Double getNotaFins() {
    return this.notaFins;
  }

  public void setNotaFins(java.lang.Double notaFins) {
    this.notaFins = notaFins;
  }


  public AssignaturaAlumneFilterForm() {
  }
  
  public AssignaturaAlumneFilterForm(AssignaturaAlumneFilterForm __toClone) {
    super(__toClone);
    this.idDesde = __toClone.idDesde;
    this.idFins = __toClone.idFins;
    this.alumneIDDesde = __toClone.alumneIDDesde;
    this.alumneIDFins = __toClone.alumneIDFins;
    this.assignaturaIDDesde = __toClone.assignaturaIDDesde;
    this.assignaturaIDFins = __toClone.assignaturaIDFins;
    this.notaDesde = __toClone.notaDesde;
    this.notaFins = __toClone.notaFins;
    this.mapOfAlumneForAlumneID = __toClone.mapOfAlumneForAlumneID;
    this.mapOfAssignaturaForAssignaturaID = __toClone.mapOfAssignaturaForAssignaturaID;
  }
  
  /* ========= UTILS ========== */

  @Override
  public List<Field<?>> getDefaultFilterByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] {  }));
  }

  @Override
  public List<Field<?>> getDefaultGroupByFields() {
    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { ALUMNEID ,ASSIGNATURAID ,NOTA }));
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
  private Map<String, String> mapOfAlumneForAlumneID;

  public Map<String, String> getMapOfAlumneForAlumneID() {
    return this.mapOfAlumneForAlumneID;
  }

  public void setMapOfAlumneForAlumneID(Map<String, String> mapOfAlumneForAlumneID) {
    this.mapOfAlumneForAlumneID = mapOfAlumneForAlumneID;
  }



  private Map<String, String> mapOfAssignaturaForAssignaturaID;

  public Map<String, String> getMapOfAssignaturaForAssignaturaID() {
    return this.mapOfAssignaturaForAssignaturaID;
  }

  public void setMapOfAssignaturaForAssignaturaID(Map<String, String> mapOfAssignaturaForAssignaturaID) {
    this.mapOfAssignaturaForAssignaturaID = mapOfAssignaturaForAssignaturaID;
  }




   // --------------------------------
   // Camps traduibles de referencies.
   // ---------------------------------
   public static final List<String> traduibles;

   static {
     traduibles = new java.util.ArrayList<String>();
   };

}

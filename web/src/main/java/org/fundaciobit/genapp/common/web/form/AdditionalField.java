package org.fundaciobit.genapp.common.web.form;

import java.util.Map;

import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 * Camp que serveix per afegir una nova columna en els llistats 
 * o una nova fila en els formularis
 */
public class AdditionalField<ID,T> {

  
  protected  int position;
  
  /**
   * Traducció
   */
  protected String codeName;
  
  /**
   * Valor ve determinat per un camp d'unt tipus determinat
   */
  protected Field<T> valueField = null;  
  
  /**
   * Valor ve determinat segons l'ID de l'objecte
   */
  protected Map<ID, String> valueMap = null;

  protected Field<?> orderBy;

  protected Field<?> searchBy;
  
  protected String searchByValue;

  protected Field<?> groupBy;
  
  protected boolean escapeXml = true;

  /**
   * Nom del parametre en formulari.
   */
  protected String parameterName;

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  public Field<T> getValueField() {
    return valueField;
  }

  public void setValueField(Field<T> valueField) {
    this.valueField = valueField;
  }

  public Map<ID, String> getValueMap() {
    return valueMap;
  }

  public void setValueMap(Map<ID, String> valueMap) {
    this.valueMap = valueMap;
  }

  public Field<?> getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(Field<?> orderBy) {
    this.orderBy = orderBy;
  }

  public Field<?> getSearchBy() {
    return searchBy;
  }

  public void setSearchBy(Field<?> searchBy) {
    this.searchBy = searchBy;
  }

  public String getSearchByValue() {
    return searchByValue;
  }

  public void setSearchByValue(String searchByValue) {
    this.searchByValue = searchByValue;
  }

  public Field<?> getGroupBy() {
    return groupBy;
  }

  public void setGroupBy(Field<?> groupBy) {
    this.groupBy = groupBy;
  }

  public String getParameterName() {
    return parameterName;
  }

  public void setParameterName(String parameterName) {
    this.parameterName = parameterName;
  }

  public boolean isEscapeXml() {
    return escapeXml;
  }

  public void setEscapeXml(boolean escapeXml) {
    this.escapeXml = escapeXml;
  }

}

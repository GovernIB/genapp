package org.fundaciobit.genapp.common.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Select;

/**
 * 
 * @author anadal
 * 
 */
public class RefListBase {

  protected Select<?>[] selects;

  protected String separator = " ";

  protected OrderBy[] orderBy = null;

  protected List<Select<?>> campsTraduibles = new java.util.ArrayList<Select<?>>();

  public RefListBase() {
  }

  public RefListBase(RefListBase clone) {
    this.selects = clone.selects;
    this.separator = clone.separator;
    this.orderBy = clone.orderBy;
    this.campsTraduibles = new ArrayList<Select<?>>(clone.campsTraduibles);
  }

  public Select<?>[] getSelects() {
    return selects;
  }

  public void setSelects(Select<?>[] selects) {
    this.selects = selects;
  }

  public String getSeparator() {
    return separator;
  }

  public void setSeparator(String separator) {
    this.separator = separator;
  }

  public OrderBy[] getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(OrderBy[] orderBy) {
    this.orderBy = orderBy;
  }

  public void addCampTraduible(Select<?> campTraduible) {
    campsTraduibles.add(campTraduible);
  }

  public List<Select<?>> getCampsTraduibles() {
    return campsTraduibles;
  }

  public void setCampsTraduibles(List<Select<?>> campsTraduibles) {
    this.campsTraduibles = campsTraduibles;
  }

  public Select<Long> checkTranslationFields() throws I18NException {
    Select<?>[] selects = getSelects();
    if (selects.length == 1) {
      if (this.campsTraduibles.contains(selects[0])) {
        return (Select<Long>) selects[0];
      }

    } else {
      for (int i = 0; i < selects.length; i++) {
        if (this.campsTraduibles.contains(selects[i])) {
          throw new I18NException("error.unknown", "S'han elegit multiples referencies "
              + "per la taula Prova, entre les quals n'hi ha una de tipus traduible."
              + "Només n'hi pot haver una sola referencia si es de tipus Traduible.");
        }
      }
    }
    return null;
  }

}

package org.fundaciobit.genapp.common.query;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public abstract class SelectMultiple<I> extends Select<I> implements GroupBy {

  final Select<?>[] selects;

  protected int numberOfEmptySelects = 0;
  
  protected boolean[] emptySelects;

  public SelectMultiple(List<Select<?>> newSelects) throws I18NException {
    this( (newSelects == null)? (Select<?>[])null : newSelects.toArray(new Select<?>[newSelects.size()]));
  }
  
  
  /**
   * @param selects
   * @param separator if null then concatenate all elements
   */
  public SelectMultiple(Select<?>[] newSelects) throws I18NException {
    if (newSelects == null || newSelects.length == 0) {
      throw new I18NException("genapp.selectmultiple.emptyparam");
    }
    // Buidar de nulls
    List<Select<?>> nonulls = new ArrayList<Select<?>>(newSelects.length);
    for (int i = 0; i < newSelects.length; i++) {
      if (newSelects[i] != null) {
        nonulls.add(newSelects[i]);
      }
    }
            
    if (nonulls.size() == 0) {
      throw new I18NException("genapp.selectmultiple.nulls");
    }
    
    selects = nonulls.toArray(new Select<?>[nonulls.size()]);
    
  }

  @Override
  public String getGroupBy() {
    StringBuffer groupBy = new StringBuffer();
    
    for (int i = 0; i <  this.selects.length; i++) {
      if (selects[i] instanceof GroupBy) {
        GroupBy gb = (GroupBy)selects[i];
        if (groupBy.length() != 0) {
          groupBy.append(" , ");
        }
        groupBy.append(gb.getGroupBy());
      }
    }
    
    String gb = groupBy.toString().trim(); 
    if (gb.length() == 0) {
      return null;
    } else {
      return gb;
    }
  }

  @Override
  public final String getSelectString() {
    StringBuffer selectStr = new StringBuffer();
    numberOfEmptySelects = 0;
    emptySelects = new boolean[this.selects.length];
    for (int i = 0; i <  this.selects.length; i++) {
      String selectString = selects[i].getSelectString();
      if (selectString == null) {
        emptySelects[i] = true;
        numberOfEmptySelects++;
        continue;
      }
      if (i != 0) {
        selectStr.append(" , ");
      }
      selectStr.append(selectString);
    }
    return selectStr.toString();
  }

  @Override
  public final I getFromObject(Object obj) throws I18NException {
    if (obj == null) {
      throw new I18NException("genapp.selectmultiple.returnednull");
    }

    Class<?> c = obj.getClass();

    if (!c.isArray()) {
      throw new I18NException("genapp.selectmultiple.notarray", c.getName());
    }

    Object[] rs_objects = (Object[]) obj;

    if ((rs_objects.length + numberOfEmptySelects) != this.selects.length) {
      throw new I18NException("genapp.selectmultiple.incorrectsize",
          String.valueOf(this.selects.length - numberOfEmptySelects),
          String.valueOf(rs_objects.length));
    }
    
    Object[] objects = new Object[this.selects.length];
    
    
    int rsCounter = 0;
    for (int i = 0; i <  this.selects.length; i++) {
      if (emptySelects[i]) {
        objects[i] = this.selects[i].getFromObject(null); // No existeix en la bbdd
      } else {
        objects[i] = this.selects[i].getFromObject(rs_objects[rsCounter]);
        rsCounter ++;
      }
    }
    
    return objectArrayToI(objects);

  }
  
  
  public abstract I objectArrayToI(Object[] objects) throws I18NException;

}

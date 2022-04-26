package org.fundaciobit.genapp.common.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public abstract class SelectMultiple<I> extends Select<I> implements GroupBy {
    
    protected Logger log = Logger.getLogger(this.getClass()); 

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
  public I getFromObject(Object obj) throws I18NException {
    if (obj == null) {
      throw new I18NException("genapp.selectmultiple.returnednull");
    }

    Class<?> c = obj.getClass();

    if (!c.isArray()) {
        
        log.error("ERROR NO ARRAY: CLass: " + this.getClass());
        log.error("ERROR NO ARRAY: Value: " + obj);
        log.error("ERROR NO ARRAY: Value Class: " + obj.getClass());
        
      throw new I18NException("genapp.selectmultiple.notarray", c.getName());
    }

    Object[] rs_objects = (Object[]) obj;
    
    int numberOfItems = 0;
    for (Select<?> s : this.selects) {
        numberOfItems = numberOfItems + s.length();
    }
    
    
    if ((rs_objects.length + numberOfEmptySelects) != numberOfItems) {
        
        log.error("numberOfItems: " + numberOfItems);
        log.error("rs_objects.length: " + rs_objects.length);
        log.error("numberOfEmptySelects: " + numberOfEmptySelects);
        log.error("this.selects.length: " + this.selects.length);
        log.error("(rs_objects.length + numberOfEmptySelects) != this.selects.length");
        
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
          if (this.selects[i].length() == 1) {
              //log.info("XYZ ZZZ ["+ i + "] Passant un object ... " + this.selects[i].getClass());
             objects[i] = this.selects[i].getFromObject(rs_objects[rsCounter]);
             rsCounter ++;
          } else {
              // És un select multiple, li hem de passar un array
              
              Object[] arrayRS = Arrays.copyOfRange(rs_objects ,rsCounter,rsCounter + this.selects[i].length());
              
              //log.info("XYZ ZZZ ["+ i + "] Passant un array ... " +  this.selects[i].getClass());
              objects[i] = this.selects[i].getFromObject(arrayRS);

              rsCounter = rsCounter + this.selects[i].length();
          }
      }
    }
    
    return objectArrayToI(objects);

  }
  
  @Override
  public int length() {
      return this.selects.length;
  }
  
  public abstract I objectArrayToI(Object[] objects) throws I18NException;

}

package org.fundaciobit.genapp.common.events;

import java.io.Serializable;
import java.util.Hashtable;

import org.fundaciobit.genapp.common.query.Field;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 */
public class UpdateEventInfo implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 7558600816622784614L;

  private Hashtable<Field<?>, Serializable> primaryKeys;
  
  private Field<?> fieldToChange;

  private Serializable valueToChange;
  
  
  /**
   * @param primaryKeys
   * @param fieldToChange
   * @param valueToChange
   */
  public UpdateEventInfo(Field<?> f1, Serializable obj1,
      Field<?> fieldToChange, Serializable valueToChange) {
    this(toHash( new Field<?>[] { f1 }, new Serializable[] {obj1}),
        fieldToChange, valueToChange);
  }
  /**
   * @param primaryKeys
   * @param fieldToChange
   * @param valueToChange
   */
  public UpdateEventInfo(Field<?> f1, Serializable obj1,Field<?> f2, Serializable obj2,
      Field<?> fieldToChange, Serializable valueToChange) {
    this(toHash( new Field<?>[] { f1, f2 }, new Serializable[] {obj1, obj2}),
        fieldToChange, valueToChange);
  }
  
  /**
   * @param primaryKeys
   * @param fieldToChange
   * @param valueToChange
   */
  public UpdateEventInfo(Field<?> f1, Serializable obj1,Field<?> f2, Serializable obj2,
      Field<?> f3, Serializable obj3,
      Field<?> fieldToChange, Serializable valueToChange) {
    this(toHash( new Field<?>[] { f1, f2, f3 }, new Serializable[] {obj1, obj2, obj3}),
        fieldToChange, valueToChange);
  }
  
  /**
   * 
   * @param fields
   * @param objs
   * @return
   */
  private static final Hashtable<Field<?>, Serializable> toHash(
    Field<?>[] fields, Serializable objs[]) {
    Hashtable<Field<?>, Serializable> primaryKeys;
    primaryKeys = new Hashtable<Field<?>, Serializable>();
    for (int i = 0; i < objs.length; i++) {
      primaryKeys.put(fields[i],objs[i]);  
    }
    return primaryKeys;
  }

  /**
   * @param primaryKeys
   * @param fieldToChange
   * @param valueToChange
   */
  public UpdateEventInfo(Hashtable<Field<?>, Serializable> primaryKeys,
      Field<?> fieldToChange, Serializable valueToChange) {
    super();
    this.primaryKeys = primaryKeys;
    this.fieldToChange = fieldToChange;
    this.valueToChange = valueToChange;
  }

  public Hashtable<Field<?>, Serializable> getPrimaryKeys() {
    return primaryKeys;
  }

  public Field<?> getFieldToChange() {
    return fieldToChange;
  }

  public Serializable getValueToChange() {
    return valueToChange;
  }
  
  
}

package org.fundaciobit.genapp;

import java.io.Serializable;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class ForeignKey implements Serializable {
  
  public static final int EXPORTED = 0;
  public static final int IMPORTED = 1; 

  /**
   * 
   */
  private static final long serialVersionUID = -8516666830084572081L;
  
  String name;

  String table;
  
  String field;
  
  /* ForeignKeyType */
  int type; 
  
  public ForeignKey() {
  }

  public ForeignKey(String name, String table, String field, int type) {
    super();
    this.name = name;
    this.table = table;
    this.field = field;
    this.type = type;
  }

  public String getTable() {
    return table;
  }

  public void setTable(String table) {
    this.table = table;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  
}

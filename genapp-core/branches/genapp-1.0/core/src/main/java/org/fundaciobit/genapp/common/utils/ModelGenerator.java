package org.fundaciobit.genapp.common.utils;

import org.fundaciobit.genapp.common.query.Field;
/**
 * 
 * @author anadal
 *
 */
public abstract class ModelGenerator {

  
  public String get(Field<?> field) {
    return getTableModelName() + "." + field.javaName; 
  }
  
  public abstract String getTableModelName();
  
}

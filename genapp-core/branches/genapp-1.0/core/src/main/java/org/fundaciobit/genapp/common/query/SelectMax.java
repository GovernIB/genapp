package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectMax<R> extends Select<R> {
  
  protected final Field<R> field;
  
  public SelectMax(Field<R> field) {
    this.field = field;
  }

  @Override
  public String getSelectString() {
    return "MAX( " + field.fullName + ")";
  }

  @Override
  public R getFromObject(Object obj) throws I18NException {
    return (R)obj;    
  }

}

package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;



public class SelectDistinct<T extends Object>
  extends Select<T> {

  Field<T> field;
  
  public <K extends Field<T>> SelectDistinct(K field) {
    this.field = field;
  }

  @Override
  public String getSelectString() {
    return "DISTINCT( " + field.fullName + " )";
  }

  @Override
  public T getFromObject(Object obj) throws I18NException {
    return field.select.getFromObject(obj);
  }
  
  @Override
  public int length() {
      return 1;
  }

}


package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;


public abstract class Select<R> {
  
  public abstract String getSelectString();
  
  public abstract R getFromObject(Object rs) throws I18NException;
  
  public abstract int length();

}

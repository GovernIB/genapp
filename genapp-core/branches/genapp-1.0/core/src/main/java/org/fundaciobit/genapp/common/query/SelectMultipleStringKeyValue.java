package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectMultipleStringKeyValue  extends SelectMultiple<StringKeyValue> {
  
  
  protected final String separator;
  
  
  public SelectMultipleStringKeyValue(Select<?> keySelect, Select<?>... valueSelects)
    throws I18NException {
     this(keySelect, " ", valueSelects);
  }

  public SelectMultipleStringKeyValue(Select<?> keySelect, String separator,
      Select<?>... valueSelects) throws I18NException {
    super(SelectMultipleKeyValue.joinSelects(keySelect, valueSelects));
    this.separator = separator;
  }

  @Override
  public StringKeyValue objectArrayToI(Object[] args) throws I18NException {

    String key = String.valueOf(args[0]);

    StringBuffer value = new StringBuffer();
    for (int i = 1; i < args.length; i++) {
      if (args[i] == null) {
        continue;
      }
      if (value.length() != 0) {
        value.append(separator);
      }

      value.append(String.valueOf(args[i]));
    }

    return new StringKeyValue(key, value.toString());
  }

  
}


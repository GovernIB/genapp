package org.fundaciobit.genapp.common.query;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectMultipleKeyValue<T> extends SelectMultiple<KeyValue<T>> {
  
  protected final String separator;
  
  protected final Select<T> keySelect;
  protected final Select<?>[] valueSelects;
  
 
  public SelectMultipleKeyValue(Select<T> keySelect, Select<?>... valueSelects)
    throws I18NException {
     this(keySelect, " ", valueSelects);
  }

  public SelectMultipleKeyValue(Select<T> keySelect, String separator,
      Select<?>... valueSelects) throws I18NException {
    super(joinSelects(keySelect, valueSelects));
    this.separator = separator;
    this.keySelect = keySelect;
    this.valueSelects = valueSelects;
  }

  @Override
  public KeyValue<T> objectArrayToI(Object[] args) throws I18NException {

    T key = keySelect.getFromObject(args[0]);

    StringBuffer value = new StringBuffer();
    for (int i = 1; i < args.length; i++) {
      if (args[i] == null) {
        continue;
      }
      if (value.length() != 0) {
        value.append(separator);
      }
      // Ha de passar pel Select.getFromObject
      value.append(String.valueOf(valueSelects[i-1].getFromObject(args[i])));
    }

    return new KeyValue<T>(key, value.toString());
  }

  public static List<Select<?>> joinSelects(Select<?> keySelect, Select<?>[] valueSelects)
      throws I18NException {
    /*
    if (keySelect == null || valueSelects == null) {
      throw new Exception("keySelect o valueSelects es null");
    }
    */

    List<Select<?>> list = new ArrayList<Select<?>>();
    list.add(keySelect);
    for (Select<?> select : valueSelects) {
      list.add(select);
    }

    return list;
  }

}


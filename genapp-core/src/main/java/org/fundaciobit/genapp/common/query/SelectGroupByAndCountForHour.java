package org.fundaciobit.genapp.common.query;

import java.util.Date;

import org.fundaciobit.genapp.common.i18n.I18NException;
/**
 * 
 * @author anadal
 *
 */
public class SelectGroupByAndCountForHour extends SelectGroupByAndCountForField {

  public SelectGroupByAndCountForHour(Field<? extends Date> field) throws I18NException  {
    super(new FieldHour(field));    
  }

}

package org.fundaciobit.genapp.common.query;

import java.sql.Time;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class TimeField extends Field<Time> {

  private static final long serialVersionUID = 5298354449166110L;

  public TimeField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new TimeSelect(table,javaName));
  }
  
  public TimeField(String path, TimeField field) {
    super(null, path + field.javaName, null, new TimeSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class TimeSelect extends SelectField<Time> {

    public TimeSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public Time getFromObject(Object obj) throws I18NException {
      return (Time)obj;
    }

  };
}

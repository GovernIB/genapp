package org.fundaciobit.genapp.common.query;


import java.sql.Timestamp;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class TimestampField extends Field<Timestamp> {

  private static final long serialVersionUID = 5298354449166111L;

  public TimestampField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new TimestampSelect(table,javaName));
  }
  
  public TimestampField(String path, TimestampField field) {
    super(null, path + field.javaName, null, new TimestampSelect(null,path + field.javaName), field.codeLabel);
  }

  public static class TimestampSelect extends SelectField<Timestamp> {
    
    public TimestampSelect(String table,String sqlName) {
      super(table, sqlName);
    }

    @Override
    public Timestamp getFromObject(Object obj) throws I18NException {
      return (Timestamp)obj;
    }
  };
}

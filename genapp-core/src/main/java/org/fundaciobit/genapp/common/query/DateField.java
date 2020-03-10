package org.fundaciobit.genapp.common.query;

import java.sql.Date;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class DateField extends Field<Date> {
  
  private static final long serialVersionUID = 52983544491661632L;

  public DateField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new DateSelect(table,javaName)); 
  }
  
  public DateField(String path, DateField field) {
    super(null, path + field.javaName, null, new DateSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class DateSelect extends SelectField<Date> {
    
    public DateSelect(String table, String sqlName) {
      super(table, sqlName);
    }

    @Override
    public Date getFromObject(Object obj) throws I18NException {
      return (Date)obj;
    }

  };
  
}

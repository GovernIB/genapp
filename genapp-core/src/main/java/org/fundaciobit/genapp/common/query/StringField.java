package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class StringField extends Field<String> {
  
  private static final long serialVersionUID = 529835444916610L;
  
  public StringField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new StringSelect(table,javaName));
  }
  
  public StringField(String path, StringField field) {
    super(null, path + field.javaName, null, new StringSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class StringSelect extends SelectField<String> {

    public StringSelect(String table,String sqlName) {
      super(table, sqlName);
    }

    @Override
    public String getFromObject(Object obj) throws I18NException {
      return (String)obj;
    }

  };
}

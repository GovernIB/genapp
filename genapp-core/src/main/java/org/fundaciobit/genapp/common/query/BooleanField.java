package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class BooleanField extends Field<Boolean> {

  private static final long serialVersionUID = 529835444916611L;

  public BooleanField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new BooleanSelect(table,javaName));
  }
  
  public BooleanField(String path, BooleanField field) {
    super(null, path + field.javaName, null, new BooleanSelect(null,path + field.javaName), field.codeLabel);
  }
  
  
  public static class BooleanSelect extends SelectField<Boolean> {

    public BooleanSelect(String table, String sqlName) {
      super(table,sqlName);
    }
    @Override
    public Boolean getFromObject(Object obj) throws I18NException {
      return (Boolean)obj;
    }
  };
}

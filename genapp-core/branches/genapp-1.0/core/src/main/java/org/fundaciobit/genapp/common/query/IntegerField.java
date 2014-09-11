package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class IntegerField extends Field<Integer> {

  private static final long serialVersionUID = 529835444916612L;

  public IntegerField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new IntegerSelect(table,javaName));
  }
  
  public IntegerField(String path, IntegerField field) {
    super(null, path + field.javaName, null, new IntegerSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class IntegerSelect extends SelectField<Integer> {

    public IntegerSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public Integer getFromObject(Object obj) throws I18NException {
      return (Integer)obj;
    }

  };
}

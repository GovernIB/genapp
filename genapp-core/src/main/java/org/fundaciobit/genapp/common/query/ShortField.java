package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class ShortField extends Field<Short> {
  
  private static final long serialVersionUID = 529835444916613L;
  
  public ShortField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new ShortSelect(table,javaName));
  }
  
  public ShortField(String path, ShortField field) {
    super(null, path + field.javaName, null, new ShortSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class ShortSelect extends SelectField<Short> {

    public ShortSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public Short getFromObject(Object obj) throws I18NException {
      return (Short)obj;
    }

  };
}

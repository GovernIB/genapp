package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 */
public class LongField extends Field<Long> {
  private static final long serialVersionUID = 529835444916615L;

  public LongField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new LongSelect(table,javaName));
  }
  
  public LongField(String path, LongField field) {
    super(null, path + field.javaName, null, new LongSelect(null,path + field.javaName), field.codeLabel);
  }

  public static class LongSelect extends SelectField<Long> {

    public LongSelect(String table,String name) {
      super(table, name);
    }

    @Override
    public Long getFromObject(Object obj) throws I18NException {
      return (Long) obj;
    }

  };
}

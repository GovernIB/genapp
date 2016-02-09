package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class CustomField extends Field<String> {
  
  private static final long serialVersionUID = 529835444916610L;
  
  public CustomField(String name, SelectField<String> select) {
    super(null, name, name, select);
  }
  
  
  public CustomField(String name) {
    this(name, new CustomSelect((String)null, name));
  }
  

  
  public static class CustomSelect extends SelectField<String> {

    public CustomSelect(String table,String sqlName) {
      super(table, sqlName);
    }

    @Override
    public String getFromObject(Object obj) throws I18NException {
      return (String)obj;
    }

  };
}

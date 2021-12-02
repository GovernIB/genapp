package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class DoubleField extends AbstractDecimalField<Double> {
  
  private static final long serialVersionUID = 529835444916617L;
  
  public DoubleField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new DoubleSelect(table,javaName));
  }
  
  public DoubleField(String path, DoubleField field) {
    super(null, path + field.javaName, null, new DoubleSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class DoubleSelect extends SelectField<Double> {

    public DoubleSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public Double getFromObject(Object obj) throws I18NException {
      return (Double)obj;
    }

  };
}

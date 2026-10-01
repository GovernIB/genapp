package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class FloatField extends AbstractDecimalField<Float> {

  private static final long serialVersionUID = 529835444916618L;

  public FloatField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new FloatSelect(table,javaName));
  }
  
  public FloatField(String path, FloatField field) {
    super(null, path + field.javaName, null, new FloatSelect(null,path + field.javaName), field.codeLabel);
  }

  public static class FloatSelect extends SelectField<Float> {

    public FloatSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public Float getFromObject(Object obj) throws I18NException {
      return (Float)obj;
    }
  };
}


package org.fundaciobit.genapp.common.query;

import java.math.BigDecimal;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 */
public class BigDecimalField extends AbstractDecimalField<BigDecimal> {
  private static final long serialVersionUID = 529835444916616L;

  public BigDecimalField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new BigDecimalSelect(table, javaName));
  }
  
  public BigDecimalField(String path, BigDecimalField field) {
    super(null, path + field.javaName, null, new BigDecimalSelect(null,path + field.javaName), field.codeLabel);
  }

  public static class BigDecimalSelect extends SelectField<BigDecimal> {
    public BigDecimalSelect(String table, String javaName) {
      super(table, javaName);
    }

    @Override
    public BigDecimal getFromObject(Object rs) throws I18NException {
      return (BigDecimal) rs;
    }

  };
}

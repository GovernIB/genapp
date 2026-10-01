package org.fundaciobit.genapp.common.query;

import java.math.BigInteger;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 */
public class BigIntegerField extends AbstractIntegerField<BigInteger> {

  private static final long serialVersionUID = 52983456345612L;

  public BigIntegerField(String table, String javaName, String sqlName) {
    super(table, javaName, sqlName, new BigIntegerSelect(table,javaName));
  }
  
  public BigIntegerField(String path, BigIntegerField field) {
    super(null, path + field.javaName, null, new BigIntegerSelect(null,path + field.javaName), field.codeLabel);
  }
  
  public static class BigIntegerSelect extends SelectField<BigInteger> {

    public BigIntegerSelect(String table,String sqlName) {
      super(table, sqlName);
    }
    @Override
    public BigInteger getFromObject(Object obj) throws I18NException {
      
      if (obj instanceof BigInteger) {
        return (BigInteger)obj;
      }
      if (obj instanceof Number) {
        Number number = (Number)obj;
        return new BigInteger(number.toString());
      }
      
      return new BigInteger(obj.toString());
    }

  };
}

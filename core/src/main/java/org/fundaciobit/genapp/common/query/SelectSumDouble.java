package org.fundaciobit.genapp.common.query;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 *
 * @author anadal
 *
 */
public class SelectSumDouble extends Select<Double> {

  public final Field<? extends Number> field;

  /**
   * @param field
   */
  public SelectSumDouble(Field<? extends Number> field) {
    super();
    this.field = field;
  }

  @Override
  public String getSelectString() {
    return "sum(" + field.fullName + ")";
  }

  @Override
  public Double getFromObject(Object obj) throws I18NException {
    return objectToDouble(obj);
  }

  public static Double objectToDouble(Object obj) throws I18NException {
    if (obj == null) {
      return null;
    }
    if (obj instanceof BigDecimal) {
      BigDecimal bigdec = (BigDecimal) obj;
      return bigdec.doubleValue();
    }
    if (obj instanceof BigInteger) {
      BigInteger bigint = (BigInteger) obj;
      return bigint.doubleValue();
    }
    if (obj instanceof Number) {
      Number number = (Number) obj;
      return number.doubleValue();
    }
    System.err.println("Valor desconegut retornat per la funcion sum(Field<DOUBLE>)"
        + obj.getClass() + " [ " + obj + " ]");
    throw new I18NException("genapp.unknowtype", String.valueOf(obj.getClass().getName()),
        String.valueOf(obj));
  }

}

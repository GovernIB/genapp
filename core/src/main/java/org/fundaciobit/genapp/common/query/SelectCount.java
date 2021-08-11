package org.fundaciobit.genapp.common.query;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 *
 * @author anadal
 *
 */
public class SelectCount extends Select<Long> {

  final String countParam;

  public SelectCount() {
    this.countParam = "*";
  }

  public SelectCount(Field<?> field) {
    this.countParam = field.fullName;
  }

  public SelectCount(Select<?> select) {
    this.countParam = select.getSelectString().replace('(', ' ').replace(')', ' ');
  }

  @Override
  public String getSelectString() {
    return "count(" + countParam+ ")";
  }

  @Override
  public Long getFromObject(Object obj) throws I18NException {
    return objectToLong(obj);
  }

  public static Long objectToLong(Object obj) throws I18NException {
    if (obj == null) {
      return null;
    }
    if (obj instanceof BigDecimal) {
      BigDecimal bigdec = (BigDecimal) obj;
      return bigdec.longValue();
    }
    if (obj instanceof BigInteger) {
      BigInteger bigint = (BigInteger) obj;
      return bigint.longValue();
    }
    if (obj instanceof Number) {
      Number number = (Number) obj;
      return number.longValue();
    }
    System.err.println("Valor desconegut retornat per la funcio count(??): " + obj.getClass() + " [ " + obj + " ]");
    throw new I18NException("genapp.unknowtype", obj.getClass().getName(), String.valueOf(obj));
  }

}

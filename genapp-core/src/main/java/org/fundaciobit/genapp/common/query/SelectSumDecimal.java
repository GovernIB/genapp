package org.fundaciobit.genapp.common.query;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 *
 * @author anadal
 *
 */
public class SelectSumDecimal<T extends AbstractDecimalField<? extends Number>> extends Select<Double> {

    public final T  field;

    /**
     * @param field
     */
    public SelectSumDecimal(T field) {
        super();
        this.field = field;
    }

    @Override
    public String getSelectString() {
        return "sum(" + field.fullName + ")";
    }

    @Override
    public Double getFromObject(Object obj) throws I18NException {
        return (Double) obj;
    }

    public static BigDecimal objectToBigDecimal(Object obj) throws I18NException {
        if (obj == null) {
            return null;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof BigInteger) {
            BigInteger bigint = (BigInteger) obj;
            return new BigDecimal(bigint);
        }
        if (obj instanceof Number) {
            Number number = (Number) obj;
            return new BigDecimal(number.doubleValue());
        }
        System.err.println("Valor desconegut retornat per la funcion sum(Field<? extends Number>)"
                + obj.getClass() + " [ " + obj + " ]");
        throw new I18NException("genapp.unknowtype", String.valueOf(obj.getClass().getName()),
                String.valueOf(obj));
    }

}

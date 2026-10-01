package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 * 
 *         Double, Float, BigDecimal
 *
 */
public abstract class AbstractDecimalField<N extends Number> extends Field<N> {

    public AbstractDecimalField(String table, String javaName, String sqlName, Select<N> select,
            String codeLabel) {
        super(table, javaName, sqlName, select, codeLabel);
    }

    protected AbstractDecimalField(String table, String javaName, String sqlName,
            Select<N> select) {
        super(table, javaName, sqlName, select);
    }
}

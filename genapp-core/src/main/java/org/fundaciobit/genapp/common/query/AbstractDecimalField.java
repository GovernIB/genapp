package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 * 
 *         Double, Float, BigDecimal
 *
 */
public abstract class AbstractDecimalField<N extends Number> extends Field<N> {

    @Deprecated
    public AbstractDecimalField(String table, String javaName, String sqlName, Select<N> select, String codeLabel) {
        this(table, javaName, sqlName, select, codeLabel, null);
    }

    @Deprecated
    protected AbstractDecimalField(String table, String javaName, String sqlName, Select<N> select) {
        this(table, javaName, sqlName, select, null, null);
    }

    public AbstractDecimalField(String table, String javaName, String sqlName, Select<N> select, String codeLabel,
            Integer size) {
        super(table, javaName, sqlName, select, codeLabel, size);
    }

    protected AbstractDecimalField(String table, String javaName, String sqlName, Select<N> select, Integer size) {
        super(table, javaName, sqlName, select, size);
    }
}

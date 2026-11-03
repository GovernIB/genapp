package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 * 
 *  BigInteger, Byte, Long, Short, Integer
 *
 */
public abstract class AbstractIntegerField<N extends Number> extends Field<N> {

    @Deprecated
    public AbstractIntegerField(String table, String javaName, String sqlName, Select<N> select, String codeLabel) {
        this(table, javaName, sqlName, select, codeLabel, null);
    }

    @Deprecated
    protected AbstractIntegerField(String table, String javaName, String sqlName, Select<N> select) {
        this(table, javaName, sqlName, select, null, null);
    }

    public AbstractIntegerField(String table, String javaName, String sqlName, Select<N> select, String codeLabel,
            Integer size) {
        super(table, javaName, sqlName, select, codeLabel, size);
    }

    protected AbstractIntegerField(String table, String javaName, String sqlName, Select<N> select, Integer size) {
        super(table, javaName, sqlName, select, null, size);
    }
}

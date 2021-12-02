package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 * 
 *  BigInteger, Byte, Long, Short, Integer
 *
 */
public abstract class AbstractIntegerField<N extends Number> extends Field<N> {

    public AbstractIntegerField(String table, String javaName, String sqlName, Select<N> select,
            String codeLabel) {
        super(table, javaName, sqlName, select, codeLabel);
    }

    protected AbstractIntegerField(String table, String javaName, String sqlName,
            Select<N> select) {
        super(table, javaName, sqlName, select);
    }
}

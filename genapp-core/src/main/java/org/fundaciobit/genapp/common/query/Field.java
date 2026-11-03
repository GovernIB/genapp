package org.fundaciobit.genapp.common.query;

import java.io.Serializable;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2010
 * Company: XmasSoft
 * 
 * @author Xmas
 * @author anadal 
 *
 *
 * @param <C> Java Class associated to Field.
 */
public abstract class Field<C> extends Whereable<C> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1066502106008727838L;

    public final String table;

    public final String javaName;

    public final String sqlName;

    public final Select<C> select;

    public final String codeLabel;

    public final Integer size;

    @Deprecated
    protected Field(String table, String javaName, String sqlName, Select<C> select) {
        this(table, javaName, sqlName, select, null, null);
    }

    /**
     * @param fieldID
     * @param name
     */
    @Deprecated
    protected Field(String table, String javaName, String sqlName, Select<C> select, String codeLabel) {
        this(table, javaName, sqlName, select, codeLabel, null);
    }

    /**
     * @param fieldID
     * @param name
     */
    protected Field(String table, String javaName, String sqlName, Select<C> select, Integer size) {
        this(table, javaName, sqlName, select, null, size);
    }

    /**
     * @param fieldID
     * @param name
     */
    protected Field(String table, String javaName, String sqlName, Select<C> select, String codeLabel, Integer size) {
        super(table == null ? javaName : (table + "." + javaName));
        this.table = table;
        this.javaName = javaName;
        this.sqlName = sqlName;
        this.select = select;
        this.codeLabel = (codeLabel == null) ? this.fullName : codeLabel;
        this.size = size;
    }

    public String getTable() {
        return table;
    }

    public String getJavaName() {
        return javaName;
    }

    public String getSqlName() {
        return sqlName;
    }

    public String getCodeLabel() {
        return codeLabel;
    }

    public Class<?> getJavaClass() throws SecurityException, NoSuchFieldException {
        return Field.class.getField("javaClass").getType();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Field) {
            Field<?> fieldObj = (Field<?>) obj;
            return fullName.equals(fieldObj.fullName);
        } else {
            return false;
        }

    }

}

package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 *
 * @param <T>
 */
public class UpdateItemSql<T> implements UpdateItem<T> {

    protected Field<T> field;
    protected String sqlUpdate;

    public UpdateItemSql() {
        super();
    }

    public UpdateItemSql(Field<T> field, String sqlUpdate) {
        super();
        this.field = field;
        this.sqlUpdate = sqlUpdate;
    }

    public Field<T> getField() {
        return field;
    }

    public void setField(Field<T> field) {
        this.field = field;
    }

    public String getSqlUpdate() {
        return sqlUpdate;
    }

    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    @Override
    public boolean isSQL() {
        return true;
    }

}

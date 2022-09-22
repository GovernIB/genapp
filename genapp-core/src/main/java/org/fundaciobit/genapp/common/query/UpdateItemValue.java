package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 *
 * @param <T>
 */
public class UpdateItemValue<T> implements UpdateItem<T> {

    protected Field<T> field;
    protected T newValue;

    public UpdateItemValue() {
        super();
    }

    public UpdateItemValue(Field<T> field, T newValue) {
        super();
        this.field = field;
        this.newValue = newValue;
    }

    public Field<T> getField() {
        return field;
    }

    public void setField(Field<T> field) {
        this.field = field;
    }

    public T getNewValue() {
        return newValue;
    }

    public void setNewValue(T newValue) {
        this.newValue = newValue;
    }

    @Override
    public boolean isSQL() {
        return false;
    }

}

package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 *
 * @param <T>
 */
public interface UpdateItem<T> {

    public Field<T> getField();

    public boolean isSQL();

}

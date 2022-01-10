package org.fundaciobit.genapp.common.query.selectcolumn;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.Select;
import org.fundaciobit.genapp.common.query.SelectMultiple;

/**
 * 
 * @author anadal
 *
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
public class Select5Columns<A, B, C, D, E> extends SelectMultiple<Select5Values<A, B, C, D, E>> {

    protected SelectNColumns<A, B, C, D, E, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select5Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4, select5));

        nColumns = new SelectNColumns<A, B, C, D, E, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>(
                select1, select2, select3, select4, select5);

    }

    @Override
    public Select5Values<A, B, C, D, E> objectArrayToI(Object[] objects) throws I18NException {
        return new Select5Values<A, B, C, D, E>(nColumns.internalObjectArrayToI(objects));
    }

}
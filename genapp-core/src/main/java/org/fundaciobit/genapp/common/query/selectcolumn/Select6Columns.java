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
public class Select6Columns<A, B, C, D, E, F>
        extends SelectMultiple<Select6Values<A, B, C, D, E, F>> {

    protected SelectNColumns<A, B, C, D, E, F, Object, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select6Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4, select5, select6));

        nColumns = new SelectNColumns<A, B, C, D, E, F, Object, Object, Object, Object, Object, Object, Object, Object, Object>(
                select1, select2, select3, select4, select5, select6);

    }

    @Override
    public Select6Values<A, B, C, D, E, F> objectArrayToI(Object[] objects) throws I18NException {
        return new Select6Values<A, B, C, D, E, F>(nColumns.internalObjectArrayToI(objects));
    }

}
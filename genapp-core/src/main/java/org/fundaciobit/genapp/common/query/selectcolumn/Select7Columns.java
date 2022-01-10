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
public class Select7Columns<A, B, C, D, E, F, G>
        extends SelectMultiple<Select7Values<A, B, C, D, E, F, G>> {

    protected SelectNColumns<A, B, C, D, E, F, G, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select7Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6,  Select<G> select7) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4, select5, select6, select7));

        nColumns = new SelectNColumns<A, B, C, D, E, F, G, Object, Object, Object, Object, Object, Object, Object, Object>(
                select1, select2, select3, select4, select5, select6, select7);

    }

    @Override
    public Select7Values<A, B, C, D, E, F, G> objectArrayToI(Object[] objects) throws I18NException {
        return new Select7Values<A, B, C, D, E, F, G>(nColumns.internalObjectArrayToI(objects));
    }

}
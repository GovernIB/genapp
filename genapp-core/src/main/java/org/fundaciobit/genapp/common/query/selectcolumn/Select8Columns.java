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
public class Select8Columns<A, B, C, D, E, F, G, H>
        extends SelectMultiple<Select7Values<A, B, C, D, E, F, G>> {

    protected SelectNColumns<A, B, C, D, E, F, G, H, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select8Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6,  Select<G> select7, Select<H> select8) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4, select5, select6, select7, select8));

        nColumns = new SelectNColumns<A, B, C, D, E, F, G, H, Object, Object, Object, Object, Object, Object, Object>(
                select1, select2, select3, select4, select5, select6, select7, select8);

    }

    @Override
    public Select8Values<A, B, C, D, E, F, G, H> objectArrayToI(Object[] objects) throws I18NException {
        return new Select8Values<A, B, C, D, E, F, G, H>(nColumns.internalObjectArrayToI(objects));
    }

}
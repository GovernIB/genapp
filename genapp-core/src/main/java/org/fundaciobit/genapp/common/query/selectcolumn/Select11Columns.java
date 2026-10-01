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
public class Select11Columns<A, B, C, D, E, F, G, H, I, J, K>
        extends SelectMultiple<Select11Values<A, B, C, D, E, F, G, H, I,J, K >> {

    protected SelectNColumns<A, B, C, D, E, F, G, H, I, J, K, Object, Object, Object, Object> nColumns;

    public Select11Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4, Select<E> select5, Select<F> select6, Select<G> select7,
            Select<H> select8, Select<I> select9, Select<J> select10, Select<K> select11) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4, select5, select6,
                select7, select8, select9, select10, select11));

        nColumns = new SelectNColumns<A, B, C, D, E, F, G, H, I, J, K, Object, Object, Object, Object>(
                select1, select2, select3, select4, select5,
                select6, select7, select8, select9, select10,
                select11);
    }

    @Override
    public Select11Values<A, B, C, D, E, F, G, H, I, J, K> objectArrayToI(Object[] objects)
            throws I18NException {
        return new Select11Values<A, B, C, D, E, F, G, H, I, J, K>(
                nColumns.internalObjectArrayToI(objects));
    }

}
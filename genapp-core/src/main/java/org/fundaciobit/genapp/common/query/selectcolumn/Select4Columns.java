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
public class Select4Columns<A, B, C, D> extends SelectMultiple<Select4Values<A, B, C, D>> {

    protected SelectNColumns<A, B, C, D, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select4Columns(Select<A> select1, Select<B> select2, Select<C> select3,
            Select<D> select4) throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3, select4));

        nColumns = new SelectNColumns<A, B, C, D, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>(
                select1, select2, select3, select4);

    }

    @Override
    public Select4Values<A, B, C, D> objectArrayToI(Object[] objects) throws I18NException {
        return new Select4Values<A, B, C, D>(nColumns.internalObjectArrayToI(objects));
    }

}
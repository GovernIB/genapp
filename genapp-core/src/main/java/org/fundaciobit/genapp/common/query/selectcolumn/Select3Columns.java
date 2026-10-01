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
 */
public class Select3Columns<A, B, C> extends SelectMultiple<Select3Values<A, B, C>> {

    protected SelectNColumns<A, B, C, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select3Columns(Select<A> select1, Select<B> select2, Select<C> select3)
            throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2, select3));

        nColumns = new SelectNColumns<A, B, C, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>(select1, select2, select3);
               

    }

    @Override
    public Select3Values<A, B, C> objectArrayToI(Object[] objects) throws I18NException {
        return new Select3Values<A, B, C>(nColumns.internalObjectArrayToI(objects));
    }

}
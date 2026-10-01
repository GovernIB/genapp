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
 */
public class Select2Columns<A, B> extends SelectMultiple<Select2Values<A, B>> {

    protected SelectNColumns<A, B, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> nColumns;

    public Select2Columns(Select<A> select1, Select<B> select2)
            throws I18NException {

        super(SelectNColumns.selectSelects(select1, select2));

        nColumns = new SelectNColumns<A, B, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object>(select1, select2);
               

    }

    @Override
    public Select2Values<A, B> objectArrayToI(Object[] objects) throws I18NException {
        return new Select2Values<A, B>(nColumns.internalObjectArrayToI(objects));
    }

}
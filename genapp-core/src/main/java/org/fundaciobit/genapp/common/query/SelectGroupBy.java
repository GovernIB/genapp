package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 * @param <R>
 */
public class SelectGroupBy<R> extends Select<R> implements GroupBy {

    final Select<R> select;

    public SelectGroupBy(Field<R> field) {
        super();
        this.select = field.select;
    }
    
    public SelectGroupBy(Select<R> select) {
        super();
        this.select = select;
    }

    @Override
    public String getSelectString() {

        return this.select.getSelectString();
    }

    @Override
    public R getFromObject(Object rs) throws I18NException {

        return this.select.getFromObject(rs);
    }

    @Override
    public int length() {
        return this.select.length();
    }

    @Override
    public String getGroupBy() {
        return this.select.getSelectString();
    }

}

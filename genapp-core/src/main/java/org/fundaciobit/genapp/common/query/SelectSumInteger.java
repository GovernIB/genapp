package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 */
public class SelectSumInteger<T extends AbstractIntegerField<? extends Number>>  extends Select<Long> {

    protected final T field;

    public SelectSumInteger(T field) {
        this.field = field;
    }

    @Override
    public String getSelectString() {
        return "SUM( " + field.fullName + ")";
    }

    @Override
    public Long getFromObject(Object obj) throws I18NException {
        return (Long) obj;
    }

}
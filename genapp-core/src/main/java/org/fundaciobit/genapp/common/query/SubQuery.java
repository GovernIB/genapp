package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 * @param <I> bean que representa la taula sobre la que es fa la Query
 * @param <R> Objecte que retorna la Query
 */
public class SubQuery<I extends IGenAppEntity, R> {

    protected final Select<R> select;

    protected final ITableManager<I, ? extends Object> manager;

    protected final Where where;

    protected final Where having;

    protected final OrderBy[] orderBy;

    /**
     * @param select
     * @param from
     * @param where
     * @param orderBy
     * @param orderType
     */
    public SubQuery(Select<R> select, ITableManager<I, ? extends Object> manager, Where where,
            OrderBy[] orderBy) {

        this(select, manager, where, null, orderBy);
    }

    /**
     * @param select
     * @param from
     * @param where
     * @param orderBy
     * @param orderType
     */
    public SubQuery(Select<R> select, ITableManager<I, ? extends Object> manager, Where where,
            Where having, OrderBy[] orderBy) {
        super();
        this.select = select;
        this.manager = manager;
        this.where = where;
        this.having = having;
        this.orderBy = orderBy;
    }

    /**
     * @param select
     * @param from
     * @param where
     * @param orderBy
     * @param orderType
     */
    public SubQuery(Select<R> select, ITableManager<I, ? extends Object> manager, Where where) {
        this(select, manager, where, null);
    }

    public String toSQL() {
        return manager.generateSelectQueryString(select, where, having, orderBy);
    }

    public QuerySQL toSQL(int index) {
        return manager.generateSelectQueryString(select, where, having, orderBy, index);
    }

    public ITableManager<I, ? extends Object> getManager() {
        return manager;
    }

    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
        int newIndex = index;

        if (where != null) {
            newIndex = this.where.setValues(ps, newIndex);
        }

        if (this.having != null) {
            newIndex = this.having.setValues(ps, newIndex);
        }

        return newIndex;
    }

    @Override
    public String toString() {
        return toSQL();
    }

}

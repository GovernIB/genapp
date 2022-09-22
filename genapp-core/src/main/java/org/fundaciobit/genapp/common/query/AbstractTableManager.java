package org.fundaciobit.genapp.common.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;


/**
 * 
 * @author anadal
 *
 * @param <E>
 * @param <PK>
 */
public abstract class AbstractTableManager<E extends IGenAppEntity, PK extends Object>
        implements ITableManager<E, PK> {

    public final Logger log = Logger.getLogger(this.getClass());

    // ===========================================
    // (10) select tots
    @PermitAll
    @Override
    public List<E> select() throws I18NException {
        return this.select((Where) null, (OrderBy[]) null);
    }

    // ===========================================
    // (11) select ordernats
    /*
     * public List<I> select(Field<?> ... orderFields) throws Exception {
     * 
     * if (orderFields == null || orderFields.length == 0) { return this.select(); }
     * else { OrderBy[] orderBy = new OrderBy[orderFields.length]; for (int i = 0; i
     * < orderBy.length; i++) { orderBy[i] = new OrderBy(orderFields[i]); } return
     * this.select((Where)null, orderBy); } }
     */

    // (12) select ordenats
    @PermitAll
    @Override
    public List<E> select(OrderBy... orderBy) throws I18NException {
        return this.select((Where) null, (OrderBy[]) orderBy);
    };

    // (12B) select condicio
    @PermitAll
    @Override
    public List<E> select(Where where) throws I18NException {
        return this.select((Where) where, (OrderBy[]) null);
    }

    @PermitAll
    @Override
    public Long count(Where where) throws I18NException {
        List<Long> list = executeQuery(new SelectCount(), where);
        if (list == null || list.size() == 0) {
            return Long.valueOf(-1);
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T extends AbstractIntegerField<? extends Number>> Long sumInteger(T field, Where where)
            throws I18NException {
        List<Long> list = executeQuery(new SelectSumInteger<T>(field), where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T extends AbstractDecimalField<? extends Number>> Double sumDecimal(T field,
            Where where) throws I18NException {
        List<Double> list = executeQuery(new SelectSumDecimal<T>(field), where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public Double avg(Field<? extends Object> field, Where where) throws I18NException {
        List<Double> list = executeQuery(new SelectAvg(field), where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T> T max(Field<T> field, Where where) throws I18NException {
        List<T> list = executeQuery(new SelectMax<T>(field), where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T> T min(Field<T> field, Where where) throws I18NException {
        List<T> list = executeQuery(new SelectMin<T>(field), where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T extends Object> T executeQueryOne(Select<T> select, Where where)
            throws I18NException {
        List<T> list = executeQuery(select, where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            // TODO missatge d'error al log si hi hagues més d'un valor !!!!
            return list.get(0);
        }
    }

    @PermitAll
    @Override
    public <T extends Object> T executeQueryOne(Field<T> field, Where where) throws I18NException {
        List<T> list = executeQuery(field, where);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            // TODO missatge d'error al log si hi hagues més d'un valor !!!!
            return list.get(0);
        }
    }
    
    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Field<T> field, OrderBy... orderBy)
            throws I18NException {
        return executeQuery(field.select, null, orderBy);
    }

    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Field<T> field, Where where, OrderBy... orderBy)
            throws I18NException {
        return executeQuery(field.select, where, orderBy);
    }
    
    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Field<T> field, Where where, Where having, OrderBy... orderBy)
            throws I18NException {
        return executeQuery(field.select, where, orderBy);
    }

    @PermitAll
    @Override
    public <T extends Object> SubQuery<E, T> getSubQuery(Field<T> field, Where where)
            throws I18NException {
        return getSubQuery(field.select, where);
    }
    
    

    

    @PermitAll
    @Override
    public <T extends Object> SubQuery<E, T> getSubQuery(Select<T> select, Where where)
            throws I18NException {
        return new SubQuery<E, T>(select, this, where);
    }
    
    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Select<T> select, OrderBy... orderBy) throws I18NException {
        return executeQuery(select, null, null, orderBy);
    }
    
    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Select<T> select, Where where,
           OrderBy... orderBy) throws I18NException {
        return executeQuery(select, where, null, orderBy);
    }
    

    @PermitAll
    @Override
    public <T extends Object> List<T> executeQuery(Select<T> select, Where where,
           Where having, OrderBy... orderBy) throws I18NException {

        SubQuery<E, T> subquery = new SubQuery<E, T>(select, this, where, having, orderBy);

        String queryStr = subquery.toSQL();

        javax.persistence.Query query = getEntityManager().createQuery(queryStr.toString());

        int index = 1;
        subquery.setValues(query, index);

        
        List<Object> results = query.getResultList();

        List<T> list = new ArrayList<T>();
        for (Object item : results) {
            list.add(select.getFromObject(item));
        }
        return list;

    }

    @PermitAll
    @Override
    public void delete(E persistentInstance) {
        getEntityManager().remove(getEntityManager().merge(getJPAInstance(persistentInstance)));
    }

    @PermitAll
    @Override
    public void delete(PK id) {
        delete(findByPrimaryKey(id));
    }

    @PermitAll
    @Override
    public int delete(Where where) throws I18NException {
        Query q = getEntityManager().createQuery("delete " + generateWhereQueryString(where));
        if (where != null) {
            where.setValues(q);
        }
        return q.executeUpdate();
    }

    @PermitAll
    @Override
    public E create(E transientInstance) throws I18NException {
        try {
            E __tmp = getJPAInstance(transientInstance);
            getEntityManager().persist(__tmp);
            return __tmp;
        } catch (Exception e) {
            log.error("Error creant element de tipus " + getTableNameVariable(), e);
            throw new I18NException(e, "error.create",
                    new I18NArgumentString(getTableNameVariable()),
                    new I18NArgumentString(e.getMessage()));
        }
    }

    @PermitAll
    @Override
    public E update(E instance) throws I18NException {
        try {
            return getEntityManager().merge(getJPAInstance(instance));
        } catch (Exception e) {
            log.error(e);
            throw new I18NException(e, "error.update",
                    new I18NArgumentString(getTableNameVariable()),
                    new I18NArgumentString(e.getMessage()));
        }
    }

    @PermitAll
    @Override
    public <R> int update(Field<R> field, R newValue, Where where) throws I18NException {
        return update(where, new UpdateItemValue<R>(field, newValue));
    }

    @PermitAll
    @Override
    public <R, S> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, Where where) throws I18NException {
        return update(where, updateItem1, updateItem2);
    }

    @PermitAll
    @Override
    public <R, S, T> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            Where where) throws I18NException {
        return update(where, updateItem1, updateItem2, updateItem3);
    }

    @PermitAll
    @Override
    public <R, S, T, U> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            UpdateItem<U> updateItem4, Where where) throws I18NException {
        return update(where, updateItem1, updateItem2, updateItem3, updateItem4);
    }

    @PermitAll
    @Override
    public <R, S, T, U, V> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            UpdateItem<U> updateItem4, UpdateItem<V> updateItem5, Where where) throws I18NException {
        return update(where, updateItem1, updateItem2, updateItem3, updateItem4, updateItem5);
    }

    @PermitAll
    @Override
    public <R, S, T, U, V, W> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2,
            UpdateItem<T> updateItem3, UpdateItem<U> updateItem4, UpdateItem<V> updateItem5, UpdateItem<W> updateItem6,
            Where where) throws I18NException {
        return update(where, updateItem1, updateItem2, updateItem3, updateItem4, updateItem5, updateItem6);
    }

    @PermitAll
    @Override
    public int update(Where where, UpdateItem<?>... updateItems) throws I18NException {
        try {
            StringBuilder queryStr = new StringBuilder(
                    "update " + getTableName() + " " + getTableNameVariable() + " set ");

            for (int i = 0; i < updateItems.length; i++) {
                UpdateItem<?> updateItem = updateItems[i];
                if (i != 0) {
                    queryStr.append(", ");
                }
                
                if (updateItem instanceof UpdateItemValue) {
                    queryStr.append(updateItem.getField().javaName + "=:__" + updateItem.getField().javaName + "__");
                } else if (updateItem instanceof UpdateItemSql) {
                    queryStr.append(updateItem.getField().javaName + "=" + ((UpdateItemSql<?>)updateItem).getSqlUpdate());
                } else {
                    final String msg = "S'ha passat un tipus de UpdateItem desconegut: class "
                            + updateItem.getClass().getName(); 
                    log.error(msg, new Exception());
                    throw new I18NException("error.unknown", msg);
                }
            }

            if (where != null) {
                queryStr.append(" where ").append(where.toSQL());
            }

            javax.persistence.Query query = getEntityManager().createQuery(queryStr.toString());

            for (int i = 0; i < updateItems.length; i++) {
                UpdateItem<?> updateItem = updateItems[i];
                
                if (updateItem instanceof UpdateItemValue) {
                    UpdateItemValue<?> uiv = (UpdateItemValue<?>) updateItem;
                   query.setParameter("__" + uiv.getField().javaName + "__", uiv.newValue);
                }
            }

            if (where != null) {
                where.setValues(query);
            }

            return query.executeUpdate();
        } catch (I18NException e) {
            throw e;
        } catch (Exception e) {
            log.error(e);
            throw new I18NException(e, "error.update", new I18NArgumentString(getTableNameVariable()),
                    new I18NArgumentString(e.getMessage()));
        }

    }
    


    @PermitAll
    @Override
    public E findByPrimaryKey(PK id) {
        return id == null ? null : (E) getEntityManager().find(getJPAClass(), id);
    }

    protected abstract Class<?> getJPAClass();

    protected abstract E getJPAInstance(E __bean);

    public abstract EntityManager getEntityManager();

    protected abstract String getTableNameVariable();

    // public abstract Select<I> getSelectAll();

    @PermitAll
    @Override
    public List<E> select(Where where, OrderBy... orderBy) throws I18NException {
        return select(where, null, null, orderBy);
    }
    
    @PermitAll
    @Override
    public List<E> select(Where where, Where having, OrderBy... orderBy) throws I18NException {
        return select(where, having, null, null, orderBy);
    }

    public class SelectAll extends Select<E> {

        @Override
        public String getSelectString() {
            return getTableNameVariable();
        }

        @Override
        public E getFromObject(Object rs) throws I18NException {
            return (E) rs;
        }
        
        @Override
        public int length() {
            return -1;
        }
    }

    @PermitAll
    @Override
    public List<E> select(Where where, Integer firstResult, Integer maxResults, OrderBy... orderBy)
            throws I18NException {
        return select(where, null, firstResult, maxResults, orderBy);
    }
        
    @PermitAll
    @Override
    public List<E> select(Where where, Where having, Integer firstResult, Integer maxResults, OrderBy... orderBy)
            throws I18NException {

        String __query = generateQueryString(new SelectAll(), where, having, orderBy);

        Query q = getEntityManager().createQuery(__query);
        if (where != null) {
            where.setValues(q);
        }
        if (firstResult != null) {
            q.setFirstResult(firstResult);
        }
        if (maxResults != null) {
            q.setMaxResults(maxResults);
        }
        try {
            return q.getResultList();
        } catch (Exception e) {
            log.error("Error executant la sentència SQL: " + __query.toString());
            throw new I18NException(e, "error.query", new I18NArgumentString(__query.toString()),
                    new I18NArgumentString(e.getMessage()));
        }
    }

    protected String generateQueryString(Select<?> select, Where where, Where having, OrderBy[] orderBy) {
        // select
        StringBuffer query = new StringBuffer("select " + select.getSelectString());
        // from
        query.append(" from " + getTableName() + " " + getTableNameVariable());
        // where
        if (where != null) {
            query.append(" where " + where.toSQL());
        }
        // group by
        query.append(processGroupBy(select));
        // having
        query.append(processHaving(having));
        // order by
        query.append(OrderBy.processOrderBy(orderBy));

        return query.toString();
    }

    protected String processGroupBy(Select<?> select) {
        if (select instanceof GroupBy) {
            String groupby = ((GroupBy) select).getGroupBy();
            if (groupby != null && groupby.trim().length() != 0) {
                return " group by " + groupby;
            }
        }
        return "";
    }
    
    
    protected String processHaving(Where having) {
        return having==null? "" : having.toSQL();
    }
    

    @Override
    public String generateSelectQueryString(Select<?> select, Where where, Where having, OrderBy[] orderBy) {
        return generateSelectQueryString(select, where, having, orderBy, 1).sql;
    }

    @Override
    public QuerySQL generateSelectQueryString(Select<?> select, Where where, Where having, OrderBy[] orderBy,
            int index) {
        // select
        StringBuilder query = new StringBuilder("select " + select.getSelectString());
        // from i where
        QuerySQL whereSQL = generateWhereQueryString(where, index, false);
        query.append(whereSQL.sql);
        // group by
        query.append(processGroupBy(select));
        // having
        QuerySQL havingSQL = generateWhereQueryString(having, whereSQL.nextIndex, true);
        query.append(havingSQL.sql);
        // order by
        query.append(OrderBy.processOrderBy(orderBy));
        return new QuerySQL(havingSQL.nextIndex, query.toString());
    }

    protected String generateWhereQueryString(Where where) {
        return generateWhereQueryString(where, 1, false).sql;
    }

    protected QuerySQL generateWhereQueryString(Where where, int index, boolean isHaving) {
        int nextIndex = index;
        StringBuilder query = new StringBuilder();
        if (!isHaving) {
          // from
          query.append(" from ").append(getTableName()).append(" ").append(getTableNameVariable());
        }
        // where
        if (where != null) {
            QuerySQL whereSQL = where.toSQL(index);
            nextIndex = whereSQL.nextIndex;
            if (isHaving) {
              query.append(" having ");
            } else {
              query.append(" where ");
            }
            query.append(whereSQL.sql);
        }
        return new QuerySQL(nextIndex, query.toString());
    }

    @Override
    public E getReference(PK id) {
        return (E) getEntityManager().getReference(getJPAClass(), id);
    }

}

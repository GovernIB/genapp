package org.fundaciobit.genapp.common.query;


import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 * @param <I>
 *          bean que representa la taula sobre la que es fa la Query
 * @param <R>
 *          Objecte que retorna la Query
 */
public class SubQuery<I extends IGenAppEntity, R> {

  protected final Select<R> select;

  protected final ITableManager<I, ? extends Object> manager;

  protected final Where where;

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
    super();
    this.select = select;
    this.manager = manager;
    this.where = where;
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
  
  public String toSQL()  {
    return manager.generateSelectQueryString(select, where, orderBy);
  }

  public QuerySQL toSQL(int index) {
    return manager.generateSelectQueryString(select, where, orderBy, index);
  }

  public ITableManager<I, ? extends Object> getManager() {
    return manager;
  }

  public int setValues(javax.persistence.Query ps, int index) throws I18NException {
    if (where != null) {
      return where.setValues(ps, index);
    }
    return index;
  }
  
  @Override
  public String toString() {
    return toSQL();
  }

}

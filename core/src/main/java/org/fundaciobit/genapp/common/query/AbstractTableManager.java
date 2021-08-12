package org.fundaciobit.genapp.common.query;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.events.ModificationListener;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 *
 * @param <I>
 * @param <PK>
 */
public abstract class AbstractTableManager <I extends IGenAppEntity, PK extends Object>
    implements ITableManager<I, PK> {
  


  public void addListener(ModificationListener<I> newListener) {
    this.getEventManager().addListener(newListener);
  }

  public boolean removeListener(ModificationListener<I> listener) {
     return this.getEventManager().removeListener(listener);
  }


  // ===========================================
  // (10) select tots

  @PermitAll
  public List<I> select() throws I18NException {
    return this.select((Where)null,(OrderBy[]) null);
  }
  
  
  // ===========================================
  // (11) select ordernats
  /*
  public List<I> select(Field<?> ... orderFields) throws Exception {
  
    if (orderFields == null || orderFields.length == 0) {
     return this.select(); 
    } else {
      OrderBy[] orderBy = new OrderBy[orderFields.length];
      for (int i = 0; i < orderBy.length; i++) {
        orderBy[i] = new OrderBy(orderFields[i]);
      }
      return this.select((Where)null, orderBy);
    }    
  }
  */
  
  
  
  // (12) select ordenats

  @PermitAll
  public List<I> select(OrderBy ... orderBy) throws I18NException {
    return this.select((Where)null,(OrderBy[]) orderBy);
  };
  
  
  // (12B) select condicio

  @PermitAll
  public List<I> select(Where where) throws I18NException {
    return this.select((Where)where,(OrderBy[]) null);
  }



  @PermitAll
  public Long count(Where where) throws I18NException {
    List<Long> list = executeQuery(new SelectCount(), where);
    if (list == null || list.size() == 0) {
      return Long.valueOf(-1);
    } else {
      return list.get(0);
    }
  }

  @PermitAll
  public <T> T max(Field<T> field, Where where) throws I18NException {
    List<T> list = executeQuery(new SelectMax<T>(field), where);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }

  @PermitAll
  public <T> T min(Field<T> field, Where where) throws I18NException {
    List<T> list = executeQuery(new SelectMin<T>(field), where);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      return list.get(0);
    }
  }
  
  
  protected abstract EntityManager getEntityManager();

  @PermitAll
  public <T extends Object> T executeQueryOne(Select<T> select, Where where) throws I18NException {
    List<T> list = executeQuery(select, where);
    if (list == null || list.size() == 0) {
      return null;
    } else {
      // TODO missatge d'error al log si hi hagues més d'un valor !!!!
      return list.get(0);
    }
  }

  @PermitAll
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
  public <T extends Object> List<T> executeQuery(Field<T> field, Where where,
      OrderBy ... orderBy) throws I18NException {
    return executeQuery(field.select, where, orderBy);
  }

  @PermitAll
  public <T extends Object> List<T> executeQuery(Select<T> select, Where where,
      OrderBy ... orderBy) throws I18NException {
   

    SubQuery<I, T> subquery = new SubQuery<I,T>(select, this, where, orderBy);
    
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
  public <T extends Object> SubQuery<I,T> getSubQuery(Field<T> field, Where where) throws I18NException {
    return getSubQuery(field.select, where);
  }


  @PermitAll
  public <T extends Object> SubQuery<I,T> getSubQuery(Select<T> select, Where where) throws I18NException {
    return new SubQuery<I, T>(select, this, where);
  }

}

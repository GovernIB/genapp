package org.fundaciobit.genapp.common.query;

import java.util.List;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.events.ModificationListener;
import org.fundaciobit.genapp.common.events.ModificationManager;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Title: Rapid Entity Bean 2010 
 * Description: 
 * Copyright: Copyright (c) 2010
 * Company: XmasSoft
 * 
 * @author Xmas
 * @version 1.0
 */
public interface ITableManager<I extends IGenAppEntity, PK extends Object> {
  
  public I create(I bean) throws I18NException;
  
  public I update(I __bean) throws I18NException;
  
  public void delete(PK primaryKey);
  
  public void delete(I __bean);
  
  public int delete(Where where) throws I18NException;

  public I findByPrimaryKey(PK primaryKey);
  
  public Long count(Where where) throws I18NException;
  
  public <T> T max(Field<T> field, Where where) throws I18NException;
  
  public <T> T min(Field<T> field, Where where) throws I18NException;

  public List<I> select() throws I18NException;

  public List<I> select(OrderBy ... orderBy) throws I18NException;

  public List<I> select(Where where) throws I18NException;

  public List<I> select(Where where, OrderBy ... orderBy) throws I18NException;
  
  public List<I> select(Where where, Integer firstResult, Integer maxResults, OrderBy ... orderBy) throws I18NException; 

  public I[] listToArray(List<I> list);
  
  public <T extends Object> T executeQueryOne(Field<T> field, Where where) throws I18NException;
  
  public <T extends Object> T executeQueryOne(Select<T> select, Where where) throws I18NException;

  public <T extends Object> List<T> executeQuery(Field<T> field, Where where,
      OrderBy ... orderBy) throws I18NException;
  
  public <T extends Object> List<T> executeQuery(Select<T> select, Where where,
      OrderBy ... orderBy) throws I18NException;
  
  
  public <T extends Object> SubQuery<I,T> getSubQuery(Select<T> select, Where where
     ) throws I18NException;
  
  public <T extends Object> SubQuery<I,T> getSubQuery(Field<T> field, Where where
      ) throws I18NException;
  
  
  public ModificationManager<I> getEventManager();
  
  
  public void addListener(ModificationListener<I> newListener);
  
  public boolean removeListener(ModificationListener<I> listener);
  
  public TableName<I> getTableName();
  
  //public String generateQueryString(Select<?> select, Where where, OrderBy[] orderBy);
  
  public String generateSelectQueryString(Select<?> select, Where where, OrderBy[] orderBy);

  public QuerySQL generateSelectQueryString(Select<?> select, Where where, OrderBy[] orderBy, int index);

}

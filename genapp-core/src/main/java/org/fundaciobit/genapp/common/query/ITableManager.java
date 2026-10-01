package org.fundaciobit.genapp.common.query;

import java.util.List;

import javax.persistence.EntityManager;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Title: Rapid Entity Bean 2010 Description: Copyright: Copyright (c) 2010
 * Company: XmasSoft
 * 
 * @author areus
 * @author anadal
 */
public interface ITableManager<E extends IGenAppEntity, PK extends Object> {

	/**
	 * Crea un entity.
	 *
	 * @param entity l'entity a crear.
	 * @return El entity creat.
	 */
	E create(E entity) throws I18NException;

	/**
	 * Actualitza un entity.
	 *
	 * @param entity l'entity a actualitzar
	 * @return entity actualitzat.
	 */
	E update(E entity) throws I18NException;

    public <R> int update(Field<R> field, R newValue, Where where) throws I18NException;
    
    public <R, S> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, Where where) throws I18NException;
    
    public <R, S, T> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            Where where) throws I18NException;


    public <R, S, T, U> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            UpdateItem<U> updateItem4, Where where) throws I18NException;


    public <R, S, T, U, V> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2, UpdateItem<T> updateItem3,
            UpdateItem<U> updateItem4, UpdateItem<V> updateItem5, Where where) throws I18NException;


    public <R, S, T, U, V, W> int update(UpdateItem<R> updateItem1, UpdateItem<S> updateItem2,
            UpdateItem<T> updateItem3, UpdateItem<U> updateItem4, UpdateItem<V> updateItem5, UpdateItem<W> updateItem6,
            Where where) throws I18NException;

    public int update(Where where, UpdateItem<?>... updateItems) throws I18NException;

	public void delete(PK primaryKey) throws I18NException;

	public void delete(E __bean) throws I18NException;

	public int delete(Where where) throws I18NException;

	public E findByPrimaryKey(PK primaryKey) throws I18NException;

	public Long count(Where where) throws I18NException;
	
	public Double avg(Field<? extends Object> field, Where where) throws I18NException;
	
	//public Long sumInteger(AbstractIntegerField<? extends Number> field, Where where) throws I18NException;
	public <T extends AbstractIntegerField<? extends Number>> Long sumInteger(T field, Where where)
            throws I18NException;
	
	//public Double sumDecimal(AbstractDecimalField<? extends Number> field, Where where) throws I18NException;
	public <T extends AbstractDecimalField<? extends Number>> Double sumDecimal(T field, Where where) throws I18NException;

	public <T> T max(Field<T> field, Where where) throws I18NException;

	public <T> T min(Field<T> field, Where where) throws I18NException;

	public List<E> select() throws I18NException;

	public List<E> select(OrderBy... orderBy) throws I18NException;

	public List<E> select(Where where) throws I18NException;

	public List<E> select(Where where, OrderBy... orderBy) throws I18NException;
	
	public List<E> select(Where where, Where having, OrderBy... orderBy) throws I18NException;

	public List<E> select(Where where, Integer firstResult, Integer maxResults, OrderBy... orderBy)
			throws I18NException;
	
	public List<E> select(Where where, Where having, Integer firstResult, Integer maxResults, OrderBy... orderBy)
            throws I18NException;

	public E[] listToArray(List<E> list);

	public <T extends Object> T executeQueryOne(Field<T> field, Where where) throws I18NException;

	public <T extends Object> T executeQueryOne(Select<T> select, Where where) throws I18NException;

	public <T extends Object> List<T> executeQuery(Field<T> field, OrderBy... orderBy) throws I18NException;
	
	public <T extends Object> List<T> executeQuery(Field<T> field, Where where, OrderBy... orderBy)
			throws I18NException;
	
	public <T extends Object> List<T> executeQuery(Field<T> field, Where where, Where having, OrderBy... orderBy)
            throws I18NException;
	
	public <T extends Object> List<T> executeQuery(Select<T> select, OrderBy... orderBy)
            throws I18NException;

	public <T extends Object> List<T> executeQuery(Select<T> select, Where where, OrderBy... orderBy)
			throws I18NException;
	
	public <T extends Object> List<T> executeQuery(Select<T> select, Where where, Where having, OrderBy... orderBy)
            throws I18NException;

	public <T extends Object> SubQuery<E, T> getSubQuery(Select<T> select, Where where) throws I18NException;

	public <T extends Object> SubQuery<E, T> getSubQuery(Field<T> field, Where where) throws I18NException;

	public TableName<E> getTableName();

	public EntityManager getEntityManager();

	public String generateSelectQueryString(Select<?> select, Where where, Where having, OrderBy[] orderBy);

	public QuerySQL generateSelectQueryString(Select<?> select, Where where, Where having, OrderBy[] orderBy, int index);

	/**
	 * Obté una entitat en forma de referència, per tant sense carregar les seves
	 * dades. Emprat bàsicament per fixar claus foranes.
	 *
	 * @param id Identificador de l'entitat a carregar.
	 * @return L'entitat en forma de referència.
	 */
	public E getReference(PK id);

}

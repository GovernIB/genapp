package org.fundaciobit.genapp.common.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Query;

import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 *
 *
 * @param <C> Java Class associated to Field. 
 * @param <I> Represents the Table or interface mapped table of database (I{TableName})
 */
public abstract class Field<C> implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 1066502106008727838L;

  public final String table;
  
  public final String fullName;
  
  public final String javaName;
  
  public final String sqlName;
  
  public final Select<C> select;
  
  public final String codeLabel;

  /**
   * @param fieldID
   * @param name
   */
  protected Field(String table, String javaName, String sqlName, Select<C> select) {
    this(table, javaName, sqlName, select, null);
  }

  /**
   * @param fieldID
   * @param name
   */
  protected Field(String table, String javaName, String sqlName, Select<C> select, String codeLabel) {
    super();
    this.table = table;
    this.javaName = javaName;
    this.sqlName = sqlName;
    this.select = select;
    this.fullName = table == null ? javaName : (table + "." + javaName);
    this.codeLabel = (codeLabel == null )? this.fullName : codeLabel;
  }

  public String getTable() {
    return table;
  }
  
  public String getJavaName() {
    return javaName;
  }

  public String getSqlName() {
    return sqlName;
  }
  
  public String getFullName() {
    return fullName;
  }

  public String getCodeLabel() {
    return codeLabel;
  }

  public Class<?> getJavaClass() throws SecurityException, NoSuchFieldException {
    return Field.class.getField("javaClass").getType();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Field) {
       Field<?> fieldObj = (Field<?>)obj;
       return fullName.equals(fieldObj.fullName);
    } else {
      return false;
    }
    
  }
  
  @Override
  public int hashCode() {
    return this.fullName.hashCode();
  }
  

  public Where isNull() {
    return new IsNull();
  }
  
  public Where isNotNull() {
    return new IsNotNull();
  }
  
  public Where like(String like) {
    return new Like(like);
  }
  
  public Where equal(C value) {
    return new Equal<C>(value);
  }
  
  public Where equal(Field<C> value) {
    return new FieldOperation<C>(value, "=");
  }
  
  public Where notEqual(C value) {
    return new NotEqual<C>(value);
  }
  
  public Where notEqual(Field<C> value) {
    return new FieldOperation<C>(value, "<>");
  }

  public Where greaterThan(C value) {
    return new GreaterThan<C>(value);
  }

  public Where greaterThan(Field<C> value) {
    return new FieldOperation<C>(value, ">");
  }

  public Where greaterThanOrEqual(C value) {
    return new GreaterThanOrEqual<C>(value);
  }

  public Where greaterThanOrEqual(Field<C> value) {
    return new FieldOperation<C>(value, ">=");
  }

  public Where lessThan(C value) {
    return new LessThan<C>(value);
  }
  
  public Where lessThan(Field<C> value) {
    return new FieldOperation<C>(value, "<");
  }
  
  public Where lessThanOrEqual(C value) {
    return new LessThanOrEqual<C>(value);
  }
  
  public Where lessThanOrEqual(Field<C> value) {
    return new FieldOperation<C>(value, "<=");
  }

  public Where between(C from, C to) {
    return new Between<C>(from, to);
  }
  
  public Where in(C[] values) {
    return new In<C>(values);
  }
  
  public Where in(Collection<C> values) {
    return new InCollection<C>(values);
  }
  
  public <I extends IGenAppEntity> Where in(SubQuery<I, C> subquery) {
    return new InQuery<I, C>(subquery, InQueryType.IN);
  }
  
  public Where notIn(C[] values) {
    return new NotIn<C>(values);
  }
  
  public Where notIn(Collection<C> values) {
    return new NotInCollection<C>(values);
  }
  
  public <I extends IGenAppEntity> Where notIn(SubQuery<I, C> subquery) {
    return new InQuery<I, C>(subquery, InQueryType.NOTIN);
  }


  // =============================================================
  // =============================================================
  // ======================== CLASSES ============================
  // =============================================================
  // =============================================================
  
  
  protected class FieldOperation<T> extends ZeroParameterOperation {
    
    final Field<T> field;
    
    final String operation;
    
    public FieldOperation(Field<T> field, String operation) {
      this.field = field;
      this.operation = operation;
    }

    
    @Override
    public String toSQL() {
      return "( " + fullName + " " + operation + " " + field.fullName + " )";
    }
    
  }

  protected class IsNull extends ZeroParameterOperation {

    public IsNull() {
      super();
    }

    @Override
    public String toSQL() {
      return "( " + fullName + " IS NULL )";
    }
  }
  
  protected class IsNotNull extends ZeroParameterOperation {

    public IsNotNull() {
      super();
    }

    @Override
    public String toSQL() {
      return "( " + fullName + " IS NOT NULL )";
    }

  }
  
  
  
  protected class Like extends Where {

    final String like;
    
    public Like(String like) {
      this.like = like;
    }

    @Override
    public String toSQL() {
      // JPA lower
      return "( LOWER(" + fullName + ") LIKE ? )";
    }
    
    @Override
    public int setValues(javax.persistence.Query query, int index) throws I18NException {
      query.setParameter(index, (like == null? like : like.toLowerCase()));
      index++;
      return index;
    }
    
  }
  
  
  
  
  
  /*
  protected class Like extends ZeroParameterOperation {

    final String like;
    
    public Like(String like) {
      this.like = like;
    }

    @Override
    public String toSQL() {
      // JPA lower
      return "( LOWER(" + fullName + ") LIKE '" + (like == null? like : like.toLowerCase()) + "' )";
    }
    
  }
  */
  
  private class SimpleOperator<T> extends OneParameterOperation<T> {
    
    private final String operator;

    public SimpleOperator(T value, String operator) {
      super(value);
      this.operator = operator;
    }

    @Override
    public String toSQL() {
      return "( " + fullName + " " + this.operator + " ? )";
    }
  }

  protected class Equal<T> extends OneParameterOperation<T> {
    public Equal(T value) {
      super(value);
    }
    
    @Override
    public int setValues(Query query, int index) throws I18NException {
      if (this.value == null) {
        return index;
      } else {
        query.setParameter(index, value);
        index++;
        return index;
      }
    }

    @Override
    public String toSQL() {
      if (this.value == null) {
        return "( " + fullName + " IS NULL )";
      } else {
        return "( " + fullName + " = ? )";
      }
    }
  }
  
  protected class NotEqual<T> extends OneParameterOperation<T> {
    
    public NotEqual(T value) {
      super(value);
    }
    
    @Override
    public int setValues(Query query, int index) throws I18NException {
      if (this.value == null) {
        return index;
      } else {
        query.setParameter(index, value);
        index++;
        return index;
      }
    }

    @Override
    public String toSQL() {
      if (this.value == null) {
        return "( " + fullName + " IS NOT NULL )";
      } else {
        return "( " + fullName + " <> ? )";
      }
    }
    
  }
  
  protected class GreaterThan<T> extends SimpleOperator<T> {
    public GreaterThan(T value) {
      super(value, ">");
    }
  }
  
  protected class GreaterThanOrEqual<T> extends SimpleOperator<T> {
    public GreaterThanOrEqual(T value) {
      super(value, ">=");
    }
  }
  
  protected class LessThan<T> extends SimpleOperator<T> {
    public LessThan(T value) {
      super(value, "<");
    }
  }
  
  protected class LessThanOrEqual<T> extends SimpleOperator<T> {
    public LessThanOrEqual(T value) {
      super(value, "<=");
    }
  }

  protected class Between<T> extends TwoParameterOperation<T> {

    public Between(T from, T to) {
      super(from, to);
    }

    @Override
    public String toSQL() {
      return "( " + fullName + " BETWEEN ? AND ? )";
    }
    
  }
  
  protected class NotIn<T extends Object> extends In<T> {
    public NotIn(T[] listOfItems) {
      super(listOfItems, "NOT IN");
    }
  }
  
  protected class In<T extends Object> extends Where {
   
    final String oper;
    
    final T[] list;
    
    public In(T[] listOfItems) {
      this(listOfItems, "IN");
    }
    
    protected In(T[] listOfItems, String oper) {
      super();
      this.oper = oper;
      if (listOfItems == null || listOfItems.length == 0) {
        this.list = null;
      } else {
        this.list = listOfItems;
      }
    }

    @Override
    public String toSQL() {
      StringBuffer str = new StringBuffer();
      if (list != null) {
        for (int i = 0; i < list.length; i++) {
          if (i != 0) {
            str.append(", ");
          }
          str.append("?");
        }
        return "( " + fullName + " " + oper + " ( " + str.toString() + " ) )";
      } else {
        return "1=0"; // FALSE CONDITION
      }
      
    }
    
    @Override
    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
      if (list != null) {
        for (T __item : list) {
          ps.setParameter(index, __item);
          index++;  
        }
      }
      return index;
    }
    
  }
  
  
  protected class NotInCollection<T extends Object> extends InCollection<T> {
    public NotInCollection(Collection<T> listOfItems) {
      super(listOfItems, "NOT IN");
    }
  }
  
    
  protected class InCollection<T extends Object> extends Where {
    
    final String oper;
    
    final Collection<T> list;
    
    public InCollection(Collection<T> listOfItems) {
      this(listOfItems, "IN");
    }
    
    protected InCollection(Collection<T> listOfItems, String oper) {
      super();
      this.oper = oper;
      if (listOfItems == null || listOfItems.size() == 0) {
        this.list = null;
      } else {
        this.list = listOfItems;
      }
    }

    @Override
    public String toSQL() {
      StringBuffer str = new StringBuffer();
      if (list != null) {
        for (int i = 0; i < list.size(); i++) {
          if (i != 0) {
            str.append(", ");
          }
          str.append("?");
        }
        return "( " + fullName + " " + oper + " ( " + str.toString() + " ) )";
      } else {
        return "IN".equals(oper)?"1=0":"1=1"; // FALSE CONDITION si IN, TRUE Condition if "NOT IN"
      }
      
    }

    @Override
    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
      if (list != null) {
        for (T __item : list) {
          ps.setParameter(index, __item);
          index++;  
        }
      }
      return index;
    }

  }
  
  protected enum InQueryType { IN, NOTIN };
  
  protected class InQuery<I extends IGenAppEntity, R extends Object> extends Where {
    
    final SubQuery<I, R> query;
    
    final InQueryType inQueryType;
    
    public InQuery(SubQuery<I, R> query, InQueryType inQueryType ) {
      this.query = query;
      this.inQueryType = inQueryType;
    }
  
    @Override
    public String toSQL() {
      if (query != null) {
        return "( " + fullName + " "
          + (inQueryType == InQueryType.IN? "IN" : "NOT IN")
          + " ( " + query.toSQL() + " ) )";
      } else {
        return "1=0"; // FALSE CONDITION
      }
    }

    @Override
    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
      if (query != null) {
        return query.setValues(ps, index);
      }
      return index;
    }

  }
  

  
  // =============================================================
  // =============================================================
  // =================== ABSTRACT CLASSES ========================
  // =============================================================
  // =============================================================
  
  
  
private abstract class ZeroParameterOperation extends Where {

    /**
     * @param object
     */
    public ZeroParameterOperation() {
      super();     
    }

    @Override
    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
      return index;
    }

  }
  
  
  
  private abstract class OneParameterOperation<T extends Object> extends Where {

    final T value;

    /**
     * @param value
     */
    public OneParameterOperation(T value) {
      super();
      this.value = value;
    }

    @Override
    public int setValues(javax.persistence.Query query, int index) throws I18NException {
      query.setParameter(index, value);
      index++;
      return index;
    }

  }
  
  private abstract class TwoParameterOperation<T extends Object> extends Where {
    
    final T value1;
    final T value2;

    /**
     * @param object
     */
    public TwoParameterOperation(T value1, T value2) {
      super();
      this.value1 = value1;
      this.value2 = value2;
    }

    @Override
    public int setValues(javax.persistence.Query ps, int index) throws I18NException {
      ps.setParameter(index, value1);
      index++;
      ps.setParameter(index, value2);
      index++;
      return index;
    }

  }

}

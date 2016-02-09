package org.fundaciobit.genapp.common.events;

import java.io.Serializable;

import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.Where;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2010
 * Company:      XmasSoft
 * @author Xmas
 * @version 1.0
 */
public class ListEventInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 12345654675678L;

  private Field<?> select;
  
  private Where where;
 
  private OrderBy[] orderBy;

  /**
   * 
   */
  public ListEventInfo() {
    super();
  }

  /**
   * @param select
   * @param from
   * @param where
   */
  public ListEventInfo(Field<?> select, Where where) {
    this(select, where, null);
  }

  /**
   * @param select
   * @param from
   * @param where
   * @param order
   * @param orderType
   */
  public ListEventInfo(Field<?> select, Where where, OrderBy[] orderBy) {
    super();
    this.select = select;
    this.where = where;
    this.orderBy = orderBy;
  }

  public Field<?> getSelect() {
    return select;
  }

  public Where getWhere() {
    return where;
  }

  public OrderBy[] getOrderBy() {
    return this.orderBy;
  }

}

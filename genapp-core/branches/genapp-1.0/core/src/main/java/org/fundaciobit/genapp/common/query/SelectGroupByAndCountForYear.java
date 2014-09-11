package org.fundaciobit.genapp.common.query;

import java.util.Date;

import org.fundaciobit.genapp.common.i18n.I18NException;
/**
 * 
 * @author anadal
 *
 */
public class SelectGroupByAndCountForYear extends SelectGroupByAndCountForField {

  public SelectGroupByAndCountForYear(Field<? extends Date> field) throws I18NException {
    super(new FieldYear(field));    
  }

  
  @Override
  public OrderBy[] getOrderBy() {      
    return new OrderBy[] { new OrderBy(super.field, OrderType.DESC) };
  }

}

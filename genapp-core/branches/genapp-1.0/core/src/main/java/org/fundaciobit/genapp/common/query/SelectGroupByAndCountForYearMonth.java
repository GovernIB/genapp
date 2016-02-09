package org.fundaciobit.genapp.common.query;

import java.util.Date;

import org.fundaciobit.genapp.common.i18n.I18NException;
/**
 * 
 * @author anadal
 *
 */
public class SelectGroupByAndCountForYearMonth extends SelectMultiple<GroupByValueItem> {

  final Field<? extends Date> field;
  
  final FieldYear fieldYear;
  
  final FieldMonth fieldMonth;
  
  public SelectGroupByAndCountForYearMonth(Field<? extends Date> field) throws I18NException {
    super(new Select<?>[] { new SelectCount(),
        new FieldYear(field).select, new FieldMonth(field).select });
    this.field = field;
    this.fieldYear = new FieldYear(field);
    this.fieldMonth = new FieldMonth(field);
    
  }


  
  
  public String getGroupBy() {
    return fieldYear.select.getSelectString() + " , " + fieldMonth.select.getSelectString();
  }

  
  public OrderBy[] getOrderBy() {
    return new OrderBy[] {
        new OrderBy(fieldYear.select.getSelectString(), OrderType.DESC) ,
        new OrderBy(fieldMonth.select.getSelectString(), OrderType.DESC)    
    };
  }
  
  @Override
  public GroupByValueItem objectArrayToI(Object[] objects) throws I18NException {
    Object year = objects[1];
    Object month = objects[2];
    
    final String label = (year == null)? null : (year + "-" + month);
    final String value = (year == null)? "-" : (year + "-" + month);

    return new GroupByValueItem(field, label, value, false, SelectCount.objectToLong(objects[0]));
  }

}

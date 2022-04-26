package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;

/**
 * 
 * @author anadal
 * 
 */
public class SelectGroupByAndCountForField extends SelectMultiple<GroupByValueItem> implements GroupBy {

  final Field<?> field;

  /**
   * @param field
   */
  public SelectGroupByAndCountForField(Field<?> field) throws I18NException {
    super(new Select<?>[] { new SelectCount(), field.select});
    this.field = field;
  }

  public OrderBy[] getOrderBy() {
    return new OrderBy[] { new OrderBy(field) };
  }

  @Override
  public String getGroupBy() {
    return this.field.fullName;
  }
  
  public String getStringValue(Object obj) {
    return (obj == null)? null : String.valueOf(obj);
  }

  
  @Override
  public final GroupByValueItem getFromObject(Object obj) throws I18NException {
      
      if (obj instanceof GroupByValueItem) {
          return (GroupByValueItem)obj;
      }
      
      return super.getFromObject(obj);
      
      
  }
  
  

  @Override
  public GroupByValueItem objectArrayToI(Object[] objects) throws I18NException {
    return new GroupByValueItem(field, getStringValue(objects[1]), getStringValue(objects[1]), false,
        SelectCount.objectToLong(objects[0]));
  };
  
  

}

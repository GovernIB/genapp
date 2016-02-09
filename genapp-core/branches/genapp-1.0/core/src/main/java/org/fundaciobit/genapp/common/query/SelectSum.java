package org.fundaciobit.genapp.common.query;

/**
 * 
 * @author anadal
 *
 */
public class SelectSum extends SelectCount  {

  public final Field<?> field;
  
  
  
  
  /**
   * @param field
   */
  public SelectSum(Field<?> field) {
    super();
    this.field = field;
  }

  @Override
  public String getSelectString() {
    return "sum(" + field.fullName + ")";
  }
  
  
}

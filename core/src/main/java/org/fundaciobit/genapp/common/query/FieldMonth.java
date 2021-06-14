package org.fundaciobit.genapp.common.query;

import java.util.Date;


/**
 * 
 * @author anadal
 *
 */
public class FieldMonth extends IntegerField {

  /**
   * 
   */
  private static final long serialVersionUID = -682282527596490984L;

  final Field<? extends Date> field;

  /**
   * @param field
   */
  public FieldMonth(Field<? extends Date> field) {
    super(null, " MONTH(" + field.javaName + ")", "MONTH(" + field.sqlName + ")");
    this.field = field;
  }
  
}

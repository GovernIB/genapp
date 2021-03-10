package org.fundaciobit.genapp.common.query;

import java.util.Date;


/**
 * 
 * @author anadal
 *
 */
public class FieldHour extends IntegerField {

  /**
   * 
   */
  private static final long serialVersionUID = 888446446596323757L;


  final Field<? extends Date> field;

  /**
   * @param field
   */
  public FieldHour(Field<? extends Date> field) {
    super(null,"HOUR(" + field.javaName + ")", "HOUR(" + field.sqlName + ")");
    this.field = field;
  }
  
}

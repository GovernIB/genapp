package org.fundaciobit.genapp.common.query;

import java.util.Date;


/**
 * 
 * @author anadal
 *
 */
public class FieldYear extends IntegerField {

  /**
   * 
   */
  private static final long serialVersionUID = 8264306296587666446L;

  final Field<? extends Date> field;

  /**
   * @param field
   */
  public FieldYear(Field<? extends Date> field) {
    super(null,"YEAR(" + field.javaName + ")", "YEAR(" + field.sqlName + ")");
    this.field = field;
  }
  
}

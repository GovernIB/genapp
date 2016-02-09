package org.fundaciobit.genapp.common.query;

/**
 *
 * @author anadal
 * 
 */
public class LongConstantField extends Field<Long> {

  public LongConstantField(Long value) {
    super(null, String.valueOf(value), String.valueOf(value), null);
  }

}

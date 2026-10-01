package org.fundaciobit.genapp.common.validation;

import org.fundaciobit.genapp.common.i18n.I18NArgument;
import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 *
 */
public interface IValidatorResult<T> {

  public void rejectValue(Field<?> field, String code, I18NArgument ... args);
  
  public void rejectIfEmptyOrWhitespace(T target ,Field<?> field,
      String code, I18NArgument ... args);

  // TODO Object hauria de ser del tipus Field<?>
  public Object getFieldValue(T target, Field<?> field);
  
  public int getFieldErrorCount(Field<?> field);
  
  public boolean hasErrors();  

}

package org.fundaciobit.genapp.common.validation;

import java.util.List;

import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;

/**
 * 
 * @author anadal
 *
 */
public abstract class AbstractBeanValidator<T> implements IValidator<T> {

  public void throwValidationExceptionIfErrors(T target, boolean isNou)
    throws I18NValidationException, I18NException {
   
    List<I18NFieldError> fieldErrors;
    fieldErrors = this.validate(target, isNou);
    if (!fieldErrors.isEmpty()) {
      throw new I18NValidationException(fieldErrors);
    }
  }
  

}

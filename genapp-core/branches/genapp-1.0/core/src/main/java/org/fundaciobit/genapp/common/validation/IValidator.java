package org.fundaciobit.genapp.common.validation;

import java.util.List;


import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;


/**
 * 
 * @author anadal
 *
 */
public interface IValidator<T> {

  public List<I18NFieldError> validate(T target, boolean isNou) throws I18NException;

}

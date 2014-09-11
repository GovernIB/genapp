package org.fundaciobit.genapp.common.web.validation;

import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import org.fundaciobit.genapp.common.i18n.I18NTranslation;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.validation.Errors;

/**
 * 
 * @author anadal
 *
 */
public class ValidationWebUtils {

  public static void addFieldErrorsToBindingResult(Errors errors, I18NValidationException ve) {
    for(I18NFieldError fe : ve.getFieldErrorList()) {
      I18NTranslation tra = fe.getTranslation();
      errors.rejectValue(fe.getField().fullName, tra.getCode(),
          I18NUtils.tradueixArguments(tra.getArgs()), null);
    }
  }
}

package org.fundaciobit.genapp.common.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgument;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import org.fundaciobit.genapp.common.i18n.I18NTranslation;
import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 * 
 * @param <T>
 */
public class BeanValidatorResult<T> implements IValidatorResult<T> {
  
  protected final Logger log = Logger.getLogger(this.getClass());

  private List<I18NFieldError> errors = new ArrayList<I18NFieldError>();

  /**
   * @param target
   */
  public BeanValidatorResult() {
    super();
  }

  public List<I18NFieldError> getErrors() {
    return errors;
  }

  @Override
  public void rejectValue(Field<?> field, String code, I18NArgument... args) {
    errors.add(new I18NFieldError(field, new I18NTranslation(code, args)));
  }

  @Override
  public int getFieldErrorCount(Field<?> field) {
    int count = 0;
    for (I18NFieldError fieldError : this.errors) {
      if (field.equals(fieldError.getField())) {
        count++;
      }
    }
    return count;
  }

  @Override
  public Object getFieldValue(T target, Field<?> field)  {
    try {
      if (target == null) {
        return null;
      }
      java.lang.reflect.Field fieldR = target.getClass().getDeclaredField(field.javaName);
      fieldR.setAccessible(true);
      return fieldR.get(target);
    } catch (Exception e) {
      log.error("No puc obtenir el valor del camp " + field.fullName + " de l'instancia "
          + target.toString(), e);
      return null;
    }

  }

  @Override
  public void rejectIfEmptyOrWhitespace(T target, Field<?> field,
      String code, I18NArgument... args) {
    Object value = getFieldValue(target, field);
    if (ValidationUtils.isEmpty(value)) {
      this.errors.add(new I18NFieldError(field, new I18NTranslation(code, args)));
    }
  }

  @Override
  public boolean hasErrors() {
    return !errors.isEmpty();
  }

}

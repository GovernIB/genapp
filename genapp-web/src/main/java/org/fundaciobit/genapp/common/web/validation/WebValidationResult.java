package org.fundaciobit.genapp.common.web.validation;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgument;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.validation.IValidatorResult;
import org.fundaciobit.genapp.common.validation.ValidationUtils;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.validation.Errors;

/**
 * 
 * @author anadal
 * 
 * @param <T>
 */
public class WebValidationResult<T> implements IValidatorResult<T> {
  
  protected final Logger log = Logger.getLogger(getClass());

  protected final org.springframework.validation.Errors errors;

  protected final String targetBase;
  
  protected final Collection<Field<?>> ignoreFields;

  /**
   * @param errors
   */
  public WebValidationResult(Errors errors) {
    super();
    this.errors = errors;
    this.targetBase = null;
    this.ignoreFields = new ArrayList<Field<?>>();
  }

  /**
   * @param errors
   */
  public WebValidationResult(Errors errors, String targetBase) {
    super();
    this.errors = errors;
    this.targetBase = targetBase;
    this.ignoreFields = new ArrayList<Field<?>>();
  }

  /**
   * @param errors
   */
  public WebValidationResult(Errors errors, Collection<Field<?>> ignoreFields) {
     this(errors, null, ignoreFields);
  }  
  
  /**
   * @param errors
   */
  public WebValidationResult(Errors errors, String targetBase, Collection<Field<?>> ignoreFields) {
    super();
    this.errors = errors;
    this.targetBase = targetBase;
    if (ignoreFields == null) {
      this.ignoreFields = new ArrayList<Field<?>>();
    } else {
      this.ignoreFields = ignoreFields; 
    }
    
  }

  @Override
  public void rejectValue(Field<?> field, String code, I18NArgument... args) {
    if (!ignoreFields.contains(field)) {
      errors.rejectValue(get(field), code, I18NUtils.tradueixArguments(args), null);
    }
  }

  @Override
  public void rejectIfEmptyOrWhitespace(T target, Field<?> field, String code,
      I18NArgument... args)  {
    if (!ignoreFields.contains(field)) {
      Object value = getFieldValue(target, field);
  
      if (ValidationUtils.isEmpty(value)) {
        errors.rejectValue(get(field), code, I18NUtils.tradueixArguments(args), null);
      }
    }
  }

  @Override
  public Object getFieldValue(T target, Field<?> field)  {
    return errors.getFieldValue(get(field));
  }

  @Override
  public int getFieldErrorCount(Field<?> field) {
    return errors.getFieldErrorCount(get(field));
  }

  @Override
  public boolean hasErrors() {
    return errors.hasErrors();
  }

  protected String get(Field<?> field) {
    if (targetBase == null) {
      return field.fullName;
    } else {
      return targetBase + "." + field.javaName;
    }
  }

  public org.springframework.validation.Errors getErrors() {
    return errors;
  }

  public String getTargetBase() {
    return targetBase;
  }

}

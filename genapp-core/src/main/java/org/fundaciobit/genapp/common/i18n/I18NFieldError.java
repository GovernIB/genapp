package org.fundaciobit.genapp.common.i18n;

import org.fundaciobit.genapp.common.query.Field;

/**
 * 
 * @author anadal
 * 
 */
public class I18NFieldError {

  Field<?> field;

  I18NTranslation translation;

  /**
   * 
   */
  public I18NFieldError() {
  }

  /**
   * @param field
   * @param translation
   */
  public I18NFieldError(Field<?> field, I18NTranslation translation) {
    super();
    this.field = field;
    this.translation = translation;
  }

  public Field<?> getField() {
    return field;
  }

  public I18NTranslation getTranslation() {
    return translation;
  }

  public void setField(Field<?> field) {
    this.field = field;
  }

  public void setTranslation(I18NTranslation translation) {
    this.translation = translation;
  }

}

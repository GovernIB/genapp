package org.fundaciobit.genapp.common.web.i18n;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 
 * @author anadal
 * 
 */
public class I18NTimeFormat extends org.fundaciobit.genapp.common.i18n.I18NCommonTimeFormat {

  public I18NTimeFormat() {
    super(null);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 486077476917349L;

  @Override
  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

}

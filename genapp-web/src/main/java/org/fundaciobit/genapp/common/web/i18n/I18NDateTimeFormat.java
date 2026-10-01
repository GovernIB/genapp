package org.fundaciobit.genapp.common.web.i18n;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 
 * @author anadal
 * 
 */
public class I18NDateTimeFormat extends org.fundaciobit.genapp.common.i18n.I18NCommonDateTimeFormat {

  public I18NDateTimeFormat() {
    super(null);
  }

  /**
   * 
   */
  private static final long serialVersionUID = 831413402061068L;

  @Override
  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

}

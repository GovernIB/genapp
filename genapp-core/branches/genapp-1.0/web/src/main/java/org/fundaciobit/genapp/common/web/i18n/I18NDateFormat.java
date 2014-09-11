package org.fundaciobit.genapp.common.web.i18n;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 
 * @author anadal
 * 
 */
public class I18NDateFormat extends org.fundaciobit.genapp.common.i18n.I18NCommonDateFormat {

  /**
   * 
   */
  private static final long serialVersionUID = -4442480258308187147L;

  /**
   * 
   */
  public I18NDateFormat() {
    super(null);
  }

  @Override
  public Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }

}

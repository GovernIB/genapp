package org.fundaciobit.genapp.common.i18n;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author anadal
 *
 */
public class I18NCommonTimeFormat extends I18NAbstractFormat {

  protected final Locale locale;

  /**
   * @param locale
   */
  public I18NCommonTimeFormat(Locale locale) {
    super();
    this.locale = locale;
  }

  
  /**
   * 
   */
  private static final long serialVersionUID = 4860774079976917349L;

  @Override
  protected SimpleDateFormat getInstanceOfSimpleDateFormat(Locale loc) {
    return (SimpleDateFormat) SimpleDateFormat.getTimeInstance(DateFormat.MEDIUM,loc);
  }

  @Override
  protected Date convertToSql(Date d) {
    if (d == null) {
      return null;
    } else {
      return new java.sql.Time(d.getTime());
    }
  }
  
  @Override
  public Locale getLocale() {    
    return this.locale;
  }

}

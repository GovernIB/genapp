package org.fundaciobit.genapp.common.i18n;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * @author anadal
 *
 */
public abstract class I18NAbstractFormat extends DateFormat {
  
  /**
   * 
   */
  private static final long serialVersionUID = -6834323958134711507L;

  protected final Logger log = Logger.getLogger(getClass());

  private Map<String, SimpleDateFormat> dateFormatByLangCountry = new HashMap<String, SimpleDateFormat>();

  public abstract Locale getLocale();
  
  @Override
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    Locale loc = getLocale();
    
    if (log.isDebugEnabled()) {
       log.debug(" [format] [" + loc + "] [" + date + "]");
    }

    SimpleDateFormat simpleDateFormat = getSimpleDateFormat(loc);

    return simpleDateFormat.format(date, toAppendTo, fieldPosition);
  }

  public SimpleDateFormat getSimpleDateFormat(Locale loc) {
    final String langCountry = loc.toString();
    SimpleDateFormat simpleDateFormat = dateFormatByLangCountry.get(langCountry);
    if (simpleDateFormat == null) {
      String pattern = getInstanceOfSimpleDateFormat(loc).toPattern();

      // L'any sempre es mostrarà amb 4 digits
      if (pattern.indexOf("yyyy") == -1) {
        pattern = pattern.replace("yy", "yyyy");
      }

      simpleDateFormat = new SimpleDateFormat(pattern, loc);
      simpleDateFormat.setLenient(false);
      dateFormatByLangCountry.put(langCountry, simpleDateFormat);
    }
    return simpleDateFormat;
  }

  protected abstract SimpleDateFormat getInstanceOfSimpleDateFormat(Locale loc);

  protected abstract Date convertToSql(Date d);

  @Override
  public Date parse(String source, ParsePosition pos) {
    Locale loc = getLocale();
    if (log.isDebugEnabled()) {
      log.debug(getClass().getName() + " [parse] [" + loc.toString() + "] ["    + source + "]");
    }
    
    try {
      SimpleDateFormat simpleDateFormat = getSimpleDateFormat(loc);
      Date d = convertToSql(simpleDateFormat.parse(source, pos));
      if (log.isDebugEnabled()) {
        log.debug(getClass().getName() + " [parse.return] = ]"
           + simpleDateFormat.format(d) + "]");
      }
      return d;
    } catch (Throwable e) {
       log.error("Error en missatge: " + e.getMessage(), e);
       return new Date();
    }
   
  }

}

package org.fundaciobit.genapp.common.web.i18n;

import java.util.Calendar;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NArgument;
import org.fundaciobit.genapp.common.i18n.I18NArgumentCode;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;
import org.fundaciobit.genapp.common.i18n.I18NTranslation;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.fundaciobit.genapp.common.query.Field;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;


/**
 * 
 * @author anadal
 * 
 */
public final class I18NUtils {

  public static final I18NDateFormat i18NDateFormat = new I18NDateFormat();

  public static final I18NTimeFormat i18NTimeFormat = new I18NTimeFormat();

  public static final I18NDateTimeFormat i18NDateTimeFormat = new I18NDateTimeFormat();

  protected static final Logger log = Logger.getLogger(I18NUtils.class);

  public static MessageSource resource = null;

  /*
  static {
    resource = new ReloadableResourceBundleMessageSource();
    resource.setBasename("missatges");
    resource.setDefaultEncoding("UTF-8");
  }
  */

  public static String getDateTimePattern() {
    Locale loc = LocaleContextHolder.getLocale();
    return i18NDateTimeFormat.getSimpleDateFormat(loc).toPattern();
  }

  public static String getDatePattern() {
    Locale loc = LocaleContextHolder.getLocale();
    return i18NDateFormat.getSimpleDateFormat(loc).toPattern();
  }

  public static String getTimePattern() {
    Locale loc = LocaleContextHolder.getLocale();
    return i18NTimeFormat.getSimpleDateFormat(loc).toPattern();
  }

  public static String getJSDateTimePattern() {
    return fixPattern(getDateTimePattern());
  }

  public static String getJSDatePattern() {
    return fixPattern(getDatePattern());
  }

  public static String getJSTimePattern() {
    return fixPattern(getTimePattern());
  }

  public static int getFirstDayOfTheWeek() {
    Locale loc = LocaleContextHolder.getLocale();
    return Calendar.getInstance(loc).getFirstDayOfWeek() - 1;
  }

  public static String fixPattern(String datetimepattern) {
    // JAVA d/MM/yyyy H:mm:ss a => PICKER dd/MM/yyyy hh:mm:ss PP
    // JAVA dd/M/yyyy h:mm:ss a => PICKER dd/MM/yyyy HH:mm:ss PP
    if (datetimepattern.indexOf('h') != -1) {
      if (datetimepattern.indexOf("hh") != -1) {
        datetimepattern = datetimepattern.replace("hh", "HH");
      } else {
        datetimepattern = datetimepattern.replace("h", "HH");
      }
    } else {
      if (datetimepattern.indexOf('H') != -1) {
        if (datetimepattern.indexOf("HH") != -1) {
          datetimepattern = datetimepattern.replace("HH", "hh");
        } else {
          datetimepattern = datetimepattern.replace("H", "hh");
        }
      }
    }

    if (datetimepattern.indexOf('a') != -1) {
      // TODO Replace aa => a fins que no hi hagi més aa
      datetimepattern = datetimepattern.replace("a", "PP");
    }

    if (datetimepattern.indexOf('d') != -1) {
      if (datetimepattern.indexOf("dd") != -1) {
        datetimepattern = datetimepattern.replace("dd", "dd");
      } else {
        datetimepattern = datetimepattern.replace("d", "dd");
      }
    }

    if (datetimepattern.indexOf('M') != -1) {
      if (datetimepattern.indexOf("MM") != -1) {
        datetimepattern = datetimepattern.replace("MM", "MM");
      } else {
        datetimepattern = datetimepattern.replace("M", "MM");
      }
    }

    return datetimepattern;
  }
  
  public static void setMessageSource(MessageSource ms) {
    resource = ms;
  }
  
  
  public static String tradueix(boolean useCodeIfNotExist, String code, String... args) {
    Locale loc = LocaleContextHolder.getLocale();
    return tradueix(useCodeIfNotExist? code : null, loc, code, args);
  }
  
  
  public static Locale getLocale() {
    return LocaleContextHolder.getLocale();
  }
  
  
  public static String tradueix(I18NTranslation translation) {
    Locale loc = LocaleContextHolder.getLocale();
    String code = translation.getCode();
    String[] args = tradueixArguments(translation.getArgs());
    return tradueix(null, loc, code, args);
  }
  
  public static String tradueix(String code, String... args) {
    Locale loc = LocaleContextHolder.getLocale();
    return tradueix(null, loc, code, args);
  }

  public static String tradueix(Locale loc, String code, String... args) {
    return tradueix(null, loc, code, args);
  }
  
  
  public static String tradueix(String valueIfNotExist, Locale loc, String code, String... args) {

    if (code == null) {
      return null;
    }
    if (resource == null) {
      log.error("No s'ha definit l'objecte MessageSource de la classe " + I18NUtils.class
          + " cridant al mètode I18NUtils.setMessageSource(...);");
      return "{" + loc + "_" + code + "}";
    }
    try {      
      return resource.getMessage(code, args, loc);
    } catch (NoSuchMessageException nsme) {
      if (valueIfNotExist == null) {
        log.error("No es pot obtenir la clau de traducció [" + code 
          + "] per l'idioma " + loc + nsme.getMessage(), nsme);
        return "{" + loc + "_" + code + "}";
      } else {
        return valueIfNotExist;
      }
    }    
    
  }

  public static Exception throwException(I18NException e) throws Exception {
    throw new Exception(getMessage(e), e.getCause());
  }
  
  
  public static String getMessage(I18NValidationException ve) {
    StringBuffer str = new StringBuffer();

    for (I18NFieldError fe : ve.getFieldErrorList()) {
      I18NTranslation trans = fe.getTranslation();
      String code = trans.getCode();
      String[] args = tradueixArguments(trans.getArgs());
      String error = tradueix(code, args);
      Field<?> field = fe.getField();
      String fieldLabel = tradueix(field.fullName);

      if (str.length() != 0) {
        str.append("\n");
      }
      str.append(fieldLabel + "(" + field.javaName + "): " + error);

    }
    return str.toString();
  }
  
  
  
  public static String getMessage(I18NException e) {
    I18NTranslation traduccio = e.getTraduccio();
    return tradueix(traduccio.getCode(), tradueixArguments(traduccio.getArgs()));
  }

  public static String[] tradueixArguments(I18NArgument[] args) {
    if (args == null || args.length == 0) {
      return null;
    }
    String[] traduits = new String[args.length];
    for (int i = 0; i < args.length; i++) {
      if (args[i] != null) {
        if (args[i] instanceof I18NArgumentCode) {
          traduits[i] = tradueix(args[i].getValue());
        } else {
          traduits[i] = args[i].getValue();
        }
      }
    }
    return traduits;
  }

  
  

}

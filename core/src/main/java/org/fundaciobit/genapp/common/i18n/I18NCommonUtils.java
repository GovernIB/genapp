package org.fundaciobit.genapp.common.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.ResourceBundle.Control;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.query.Field;


/**
 * 
 * @author anadal
 *
 */
public class I18NCommonUtils {


  protected final static Logger log = Logger.getLogger(I18NCommonUtils.class);

  protected static TreeMap<String, ResourceBundle> bundles = new TreeMap<String, ResourceBundle>();

  public static String[] BUNDLES = new String[] { };

  protected static UTF8Control UTF8CONTROL = new UTF8Control();

  public static String tradueix(boolean useCodeIfNotExist, Locale loc, String code,
      String... args) {
    return tradueix(useCodeIfNotExist ? code : null, loc, code, args);
  }

  public static String tradueix(Locale loc, String code, String... args) {
    return tradueix(null, loc, code, args);
  }


  public static String tradueix(String valueIfNotExist, Locale loc, String code,
      String... args) {
    
    // Cache de resource bundle
    String msg = null;
    Exception lastException = null;
    
    for (String res : BUNDLES) {
      String key = res + "_" + loc.toString();
      ResourceBundle resource = bundles.get(key);
      if (resource == null) {
        resource = ResourceBundle.getBundle(res, loc, UTF8CONTROL);
        bundles.put(key, resource);
      }

      try {
        msg = resource.getString(code);
        break;
      } catch (Exception mre) {
        lastException = mre;
        continue;
      }
    }

    if (msg == null) {
      if (valueIfNotExist == null) {
        String lang = loc.toString().toUpperCase();
        if (lastException == null) {
          lastException = new Exception();
        }
        log.error("La clau de traducció [" + code + "] per l'idioma " + lang
            + " no existeix: " + lastException.getMessage(), lastException);
        return "{" + lang + "_" + code + "}";
      } else {
        return valueIfNotExist;
      }
    } else {
      if (args != null && args.length != 0) {
        msg = MessageFormat.format(msg, (Object[]) args);
      }
      return msg;
    }
  }

  public static class UTF8Control extends Control {
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
        ClassLoader loader, boolean reload) throws IllegalAccessException,
        InstantiationException, IOException {
      // The below is a copy of the default implementation.
      String bundleName = toBundleName(baseName, locale);
      String resourceName = toResourceName(bundleName, "properties");
      ResourceBundle bundle = null;
      InputStream stream = null;
      if (reload) {
        URL url = loader.getResource(resourceName);
        if (url != null) {
          URLConnection connection = url.openConnection();
          if (connection != null) {
            connection.setUseCaches(false);
            stream = connection.getInputStream();
          }
        }
      } else {
        stream = loader.getResourceAsStream(resourceName);
      }
      if (stream != null) {
        try {
          // Only this line is changed to make it to read properties files as
          // UTF-8.
          bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
        } finally {
          stream.close();
        }
      }
      return bundle;
    }
  }

  public static String getMessage(I18NValidationException ve, Locale locale) {
    StringBuffer str = new StringBuffer();

    for (I18NFieldError fe : ve.getFieldErrorList()) {
      I18NTranslation trans = fe.getTranslation();
      String code = trans.getCode();
      String[] args = tradueixArguments(locale, trans.getArgs());
      String error = tradueix(locale, code, args);
      Field<?> field = fe.getField();
      String fieldLabel = tradueix(locale, field.fullName);

      if (str.length() != 0) {
        str.append("\n");
      }
      str.append(fieldLabel + "(" + field.javaName + "): " + error);

    }
    return str.toString();
  }

  public static String getMessage(I18NException e, Locale locale) {
    return tradueix(locale, e.getMessage(),
        tradueixArguments(locale, e.getTraduccio().getArgs()));
  }

  public static String[] tradueixArguments(Locale locale, I18NArgument... args) {
    if (args == null || args.length == 0) {
      return null;
    }
    String[] traduits = new String[args.length];
    for (int i = 0; i < args.length; i++) {
      if (args[i] != null) {
        if (args[i] instanceof I18NArgumentCode) {
          traduits[i] = tradueix(locale, args[i].getValue());
        } else {
          traduits[i] = args[i].getValue();
        }
      }
    }
    return traduits;
  }
}

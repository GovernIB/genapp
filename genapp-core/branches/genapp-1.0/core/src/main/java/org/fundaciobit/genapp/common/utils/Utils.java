package org.fundaciobit.genapp.common.utils;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.StringKeyValue;


/**
 * 
 * @author anadal
 *
 */
public final class Utils {
  
  protected final static Logger log = Logger.getLogger(Utils.class);

  public static MimetypesFileTypeMap mimemanager;
  
  static {
    InputStream is = Utils.class.getResourceAsStream("mime.types");
    //System.out.println("MIME.TYPES = " + is);
    mimemanager = new MimetypesFileTypeMap(is);  
  }
  
  
  
  public static String getMimeType(String filename) {
    String mime = mimemanager.getContentType(filename);
    return mime == null ? "application/octet-stream" : mime;
  }
  

  public static Map<String, String> listToMap(List<StringKeyValue> list) {
    if (list == null) {
      return null;
    }
    
    Map<String, String> map = new HashMap<String, String>();
    for (StringKeyValue stringKeyValue : list) {
      map.put(stringKeyValue.key, stringKeyValue.value);
    }
    
    return map;
  }
  
  
  public static Map<String, String> listToMapInverse(List<StringKeyValue> list) {
    if (list == null) {
      return null;
    }

    Map<String, String> map = new HashMap<String, String>();
    for (StringKeyValue stringKeyValue : list) {
      map.put(stringKeyValue.value, stringKeyValue.key);
    }
    
    return map;
  }
  
  
  /**
   * Troba un valor dins una llista de StrignKeyValue a partir d'una clau donada.
   * @param key
   * @param list
   * @return
   */
  public static String findValue(Object key, Collection<StringKeyValue> list) {
    if (list != null && key != null) {
      final String keyStr = String.valueOf(key);
      for (StringKeyValue stringKeyValue : list) {
        if (keyStr.equals(stringKeyValue.key)) {
          return stringKeyValue.value;
        }
      }
    }
    return null;
  }
  
  public static boolean contains(@SuppressWarnings("rawtypes") Collection list, Object o) {
    if (list == null) {
      new Exception("La llista es buida").printStackTrace();
      return false;
    } else {
      if (o == null) {
        new Exception("El valor a comparar si existeix en la llista és null").printStackTrace();
        return false;
      }            
      return list.contains(o);
    }
  }
  
  /*
  public static boolean containsField(org.ibit.genapp.common.query.Field<?>[] list, String javaField) {
    if (list == null) {
      new Exception("La llista es buida").printStackTrace();
      return false;
    } else {
      if (javaField == null) {
        new Exception("El valor a comparar si existeix en la llista és null").printStackTrace();
        return false;
      }
      for (Field<?> field : list) {
        
        if (javaField.equals(field.javaName)) {
          return true;
        }
      }
      return false;
    }
  }
  */
  
  public static String empty2Null(String value) {
    if (value == null || value.trim().length() == 0) {
      return null;
    } else {
      return value;
    }
  }

  public static boolean hasProperty(Object o, String propertyName) {
      if (o == null || propertyName == null) {
          return false;
      }
      try {
        o.getClass().getDeclaredField(propertyName); //NoSuchFieldException
        return true;
      } catch (Exception e) {
        return false;
      }
  }
  
  

  public static String getChecksum(InputStream fis) throws Exception {

      byte[] buffer = new byte[4096];
      MessageDigest complete = MessageDigest.getInstance("MD5");
      int numRead;
  
      while( (numRead = fis.read(buffer)) != -1) {
        complete.update(buffer, 0, numRead);
      }
      fis.close();
      byte[] b = complete.digest();
      
      StringBuffer str = new StringBuffer();
      for (int i=0; i < b.length; i++) {
        str.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 ));
      }
      return str.toString();
      
  }
  
  
  public static Object getValueOfJavaField(Object instance, String field) {

    try {
      
      java.lang.reflect.Field fieldJava = null;
      Class<?> current = instance.getClass();
      do {
         try {
           fieldJava = current.getDeclaredField(field);
           break;
         } catch(Exception e) {
           
         }
      } while((current = current.getSuperclass()) != null);
      
      if (fieldJava == null) {
        throw new Exception();
      }
      fieldJava.setAccessible(true);
      return fieldJava.get(instance);
    } catch (Exception e) {
      log.error("Error cercant valor del camp " + field + " de la classe "
          + (instance == null ? "NULL" : String.valueOf(instance.getClass())), e);
      return null;
    }
  }

  
  public static boolean isEmpty(Object obj) {
    if (obj == null) return true;
    
    if (obj instanceof Collection) {
      
      return ((Collection<?>)obj).isEmpty();
      
    }
    
    return false;
  }
  
}

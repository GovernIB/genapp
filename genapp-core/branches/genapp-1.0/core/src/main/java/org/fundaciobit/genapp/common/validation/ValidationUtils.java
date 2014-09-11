package org.fundaciobit.genapp.common.validation;

/**
 * 
 * @author anadal
 * 
 */
public class ValidationUtils {


  /**
   * Number, Date, Time amb TimeStamp
   * @param value
   * @return
   */
  public static boolean isEmpty(Object value) {
    if (value == null) {
      return true;
    }
    if (value instanceof String) {
      return isEmpty((String)value);
    }
    return false;
  }

  /**
   * Empty String
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return (str == null || str.length() == 0 || str.trim().length() == 0);
  }

}

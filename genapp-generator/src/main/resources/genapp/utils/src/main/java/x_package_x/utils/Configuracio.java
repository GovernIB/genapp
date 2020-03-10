package ${package}.utils;

import java.io.File;

/**
 * 
 * @author anadal
 * 
 */
public class Configuracio implements Constants {
 
  public static boolean isCAIB() {
    return Boolean.getBoolean(${name_uppercase}_PROPERTY_BASE + "iscaib");
  }

  public static File getFilesDirectory() {
    String path = System.getProperty(${name_uppercase}_PROPERTY_BASE + "filesdirectory");
    return new File(path);
  }
  
  public static boolean isDesenvolupament() {
    return Boolean.getBoolean(${name_uppercase}_PROPERTY_BASE + "development");
  }


  public static String getAppUrl() {
    return System.getProperty(${name_uppercase}_PROPERTY_BASE + "url");
  }

  public static String getAppEmail() {
    return System.getProperty(${name_uppercase}_PROPERTY_BASE + "email.from");
  }

  public static String getAppName() {
    return System.getProperty(${name_uppercase}_PROPERTY_BASE + "name", "${fullname}");
  }

  public static String getDefaultLanguage() {
    return System.getProperty(${name_uppercase}_PROPERTY_BASE + "defaultlanguage", "ca");
  }


  public static byte[] getEncryptKey() {
    return System.getProperty(${name_uppercase}_PROPERTY_BASE + "encryptkey", "0123456789123456").getBytes();
  }

  public static Long getMaxUploadSizeInBytes() {
    return Long.getLong(${name_uppercase}_PROPERTY_BASE + "maxuploadsizeinbytes");
  }

  public static Long getMaxFitxerAdaptatSizeInBytes() {
    return Long.getLong(${name_uppercase}_PROPERTY_BASE + "maxfitxeradaptatsizeinbytes");
  }

}

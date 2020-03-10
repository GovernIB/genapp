package org.fundaciobit.genapp.common;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.AbstractKeyValue;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.generator.CodeGenUtils;



/**
 * Classe d'utilitats.
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * 
 */
public class GenAppUtils {
  
  private static final Logger log = Logger.getLogger(GenAppUtils.class.getSimpleName());

  /**
   * 
   * @param obj1
   * @param obj2
   * @return
   */
  public static boolean equals(Object obj1, Object obj2) {
    if (obj1 == null || obj2 == null) {
      return obj1 == obj2;
    }

    Class<?> c1 = obj1.getClass();
    Class<?> c2 = obj2.getClass();
    if (c1.isPrimitive() || c2.isPrimitive()) {
      return obj1 == obj2;
    }
    return obj1.equals(obj2);
  }

  public static void copyDirectory(File srcPath, File dstPath, boolean overwrite)
      throws IOException {
    if (srcPath.isDirectory()) {
      if (srcPath.getName().equals(".svn")) {
        return;
      }
      if (!dstPath.exists()) {
        dstPath.mkdir();
      }
      String files[] = srcPath.list();
      for (int i = 0; i < files.length; i++) {
        copyDirectory(new File(srcPath, files[i]), new File(dstPath, files[i]), overwrite);
      }
    } else {
      if (!srcPath.exists()) {
        log.info("File or directory does not exist.");
        return;
      } else {
        if (!dstPath.exists() || (dstPath.exists() && overwrite)) {  
          copyFile(srcPath, dstPath);
        }
      }
      // log.info("Copied from " + srcPath.getName() + " to " +
      // dstPath.getName());
    }

  }
  
  
  public static void delete(File f) throws IOException {
    if (f.isDirectory()) {
      for (File c : f.listFiles())
        delete(c);
    }
    if (!f.delete())
      throw new FileNotFoundException("Failed to delete file: " + f);
  }

  public  static void copyFile(File srcPath, File dstPath)
      throws FileNotFoundException, IOException {
    InputStream in = new FileInputStream(srcPath);
    OutputStream out = new FileOutputStream(dstPath);
    copy(in, out);
    in.close();
    out.close();
  }
  
  
  public  static void copyFile(byte[] data, File dstPath)
  throws FileNotFoundException, IOException {
    InputStream in = new ByteArrayInputStream(data);
    OutputStream out = new FileOutputStream(dstPath);
    copy(in, out);
    in.close();
    out.close();
  }
  

  private static void copy(InputStream in, OutputStream out) throws IOException {
    // Transfer bytes from in to out
    byte[] buf = new byte[1024];
    int len;
    while ((len = in.read(buf)) > 0) {
      out.write(buf, 0, len);
    }
  }

  public static List<Class<?>> getClassesForPackage(String pckgname)
      throws ClassNotFoundException {
    // This will hold a list of directories matching the pckgname.
    // There may be more than one if a package is split over multiple jars/paths
    List<Class<?>> classes = new ArrayList<Class<?>>();
    ArrayList<File> directories = new ArrayList<File>();
    try {
      ClassLoader cld = Thread.currentThread().getContextClassLoader();
      if (cld == null) {
        throw new ClassNotFoundException("Can't get class loader.");
      }
      // Ask for all resources for the path
      Enumeration<URL> resources = cld.getResources(pckgname.replace('.', '/'));
      while (resources.hasMoreElements()) {
        URL res = resources.nextElement();
        if (res.getProtocol().equalsIgnoreCase("jar")) {
          JarURLConnection conn = (JarURLConnection) res.openConnection();
          JarFile jar = conn.getJarFile();
          for (JarEntry e : Collections.list(jar.entries())) {

            if (e.getName().startsWith(pckgname.replace('.', '/'))
                && e.getName().endsWith(".class") && !e.getName().contains("$")) {
              String className = e.getName().replace("/", ".")
                  .substring(0, e.getName().length() - 6);
              log.info(className);
              classes.add(Class.forName(className));
            }
          }
        } else
          directories.add(new File(URLDecoder.decode(res.getPath(), "UTF-8")));
      }
    } catch (NullPointerException x) {
      throw new ClassNotFoundException(pckgname + " does not appear to be "
          + "a valid package (Null pointer exception)");
    } catch (UnsupportedEncodingException encex) {
      throw new ClassNotFoundException(pckgname + " does not appear to be "
          + "a valid package (Unsupported encoding)");
    } catch (IOException ioex) {
      throw new ClassNotFoundException("IOException was thrown when trying "
          + "to get all resources for " + pckgname);
    }

    // For every directory identified capture all the .class files
    for (File directory : directories) {
      if (directory.exists()) {
        // Get the list of the files contained in the package
        String[] files = directory.list();
        for (String file : files) {
          // we are only interested in .class files
          if (file.endsWith(".class")) {
            // removes the .class extension
            String cname = file.substring(0, file.length() - 6); 
            log.info(cname);
            classes.add(Class.forName(pckgname + '.' + cname));

          }
        }
      } else {
        throw new ClassNotFoundException(pckgname + " (" + directory.getPath()
            + ") does not appear to be a valid package");
      }
    }
    return classes;
  }

  
  public static String getOnlyName(org.fundaciobit.genapp.FieldInfo field, String methodGetName) {
    String javaField = field.getJavaName();
    return methodGetName + Character.toUpperCase(javaField.charAt(0)) + javaField.substring(1);
  }
  

  
  public static <T> void orderListByAnyField(T[] list, String fieldName,
      OrderType orderType) throws Exception {

    Class<?> cc = list.getClass().getComponentType();
    Method method = cc.getMethod(CodeGenUtils.getOnlyName(fieldName));
    Class<?> c = method.getReturnType();

    // log.info(" =========== ORDER CLASS " + c);

    if (c.isPrimitive() || Number.class.isAssignableFrom(c)) {
      Comparator<Integer> ci = new AbstractKeyValue.IntegerComparator();
      new OrderClass<Integer, T>(ci).orderListByAnyField(list, fieldName, orderType);
      return;
    }

    if (c.equals(java.sql.Date.class)) {
      Comparator<Date> cd = new AbstractKeyValue.DateComparator();
      new OrderClass<Date, T>(cd).orderListByAnyField(list, fieldName, orderType);
      return;
    }

    if (c.equals(java.sql.Timestamp.class)) {
      Comparator<Timestamp> cd = new AbstractKeyValue.TimestampComparator();
      new OrderClass<Timestamp, T>(cd).orderListByAnyField(list, fieldName, orderType);
      return;
    }

    Comparator<String> cs = new AbstractKeyValue.StringComparator();
    new OrderClass<String, T>(cs).orderListByAnyField(list, fieldName, orderType);
  }

  public static class OrderClass<S, T> {

    final Comparator<S> comparator;

    public OrderClass(Comparator<S> comparator) {
      this.comparator = comparator;
    }

    @SuppressWarnings({ "rawtypes" })
    void orderListByAnyField(T[] list, String fieldName, OrderType orderType)
        throws Exception {

      if (list == null || list.length == 0) {
        return;
      }
      Class<?> cc = list.getClass().getComponentType();
      Method method = cc.getMethod(CodeGenUtils.getOnlyName(fieldName));

      ArrayList<AbstractKeyValue<S, T>> values = new ArrayList<AbstractKeyValue<S, T>>();

      for (int i = 0; i < list.length; i++) {
        S value = (S) method.invoke(list[i]);
        AbstractKeyValue<S, T> kv = new AbstractKeyValue<S, T>(value, list[i]);
        values.add(kv);
      }

      Collections.sort(values, new AbstractKeyValue.KeyValueComparator(
          comparator));

      Iterator<AbstractKeyValue<S, T>> iter = values.iterator();

      if (orderType == OrderType.ASC) {
        int i = 0;
        while (iter.hasNext()) {
          list[i] = iter.next().object;
          i++;
        }
      } else {
        int i = 1;
        while (iter.hasNext()) {
          list[list.length - i] = iter.next().object;
          i++;
        }
      }
    }
  }

  /**
   * 
   * @param label
   * @return
   */
  public static String stringArrayToString(String[] label) {
    StringBuffer txt = new StringBuffer();
    for (int i = 0; i < label.length; i++) {
      if (i != 0) {
        txt.append(",");
      }
      txt.append(label[i]);
    }
    return txt.toString();
  }

  /**
   * 
   * @param value
   * @return
   */
  public static String[] strignToStringArray(String value) {
    String[] newLabel = value.split(",");
    for (int i = 0; i < newLabel.length; i++) {
      newLabel[i] = newLabel[i].trim();
    }
    return newLabel;
  }
  
  public static Object getObjectFromFile(File f) throws FileNotFoundException {
    XMLDecoder xe = new XMLDecoder(new FileInputStream(f));    
    Object obj = xe.readObject();
    xe.close();
    return obj;
  }

}

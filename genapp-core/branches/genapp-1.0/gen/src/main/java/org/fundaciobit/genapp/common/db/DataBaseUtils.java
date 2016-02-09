package org.fundaciobit.genapp.common.db;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.generator.CodeGenUtils;


/**
 * Title:        Rapid Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class DataBaseUtils {
  
  private static final Logger log = Logger.getLogger(DataBaseUtils.class.getSimpleName());
  
  
  public static <T> void orderList(java.sql.Connection con, T[] list,
    String select, String orderField, OrderType orderType) throws Exception {
    
    if (list == null || list.length == 0) {
      return;
    }
    Class<?> c = list[0].getClass();        
    Method method = c.getMethod(CodeGenUtils.getOnlyName(orderField));
    ArrayList<KeyValue<T>> values = new ArrayList<KeyValue<T>>();
    
    log.info(" ============= ORDER LIST ===============");
    log.info(" -- List Length: " + list.length);
    
    for (int i = 0; i < list.length; i++) {
      Object pk = method.invoke(list[i]);
      String value = executeQuery( con, select, pk);
      KeyValue<T> kv = new KeyValue<T>(list[i], value);
      values.add(kv);
    }
    
    Collections.sort(values, new KeyValue.KeyValueComparator<T>());
    
    log.info(" -- TreeSet length: " + values.size());

    Iterator<KeyValue<T>> iter = values.iterator();
    
    if (orderType == OrderType.ASC) {
      int i = 0;
      while(iter.hasNext()) {
        list[i] = iter.next().key;
        i++;
      }
    } else {
      int i = 1;
      while(iter.hasNext()) {
        list[list.length - i] = iter.next().key;
        i++;
      }
    }
  }

  /**
   * 
   * @param con
   * @param nullValue
   * @param select
   * @param pk
   * @return
   * @throws Exception
   */
  public static String executeQueryValueIfNull(java.sql.Connection con,
      String nullValue, String select, Object pk) throws Exception {
    String v = executeQuery( con, select, pk);
    if (v == null) {
      return nullValue;
    } else {
      return v;
    }
  }
  

  /**
   * 
   * @param con
   * @param select
   * @param pk
   * @return
   * @throws Exception
   */
  public static String executeQuery(java.sql.Connection con,
      String select, Object pk) throws Exception {
    con.setAutoCommit(true);
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;
    try {
      ps = con.prepareStatement(select);
      ps.setObject(1, pk);
      rs = ps.executeQuery();
      Object obj;
      if (rs.next()) {
        StringBuffer str = new StringBuffer();
        java.sql.ResultSetMetaData rsmd = rs.getMetaData();
        int numColumns = rsmd.getColumnCount();
        for (int i = 2; i <= numColumns; i++) {
          if (i > 1) {
            str.trimToSize();
            str.append(' ');
          }
          obj = rs.getObject(i);
          if (obj != null) { str.append(obj); }
        }
        return str.toString();
      } else {
        return null;
      }
    } finally {
      if (rs != null) { rs.close(); }
      if (ps != null) { ps.close(); }
    }
  }

  public static String[] executeQuery2(java.sql.Connection con,
      String select, Object ... pk) throws Exception {
    con.setAutoCommit(true);
    java.sql.PreparedStatement ps = null;
    java.sql.ResultSet rs = null;
    try {
      ps = con.prepareStatement(select);
      if (pk != null) {
        for(int x=0; x < pk.length; x++) {
          ps.setObject(x + 1, pk[x]);
        }
      }
      rs = ps.executeQuery();
      Object obj;
      ArrayList<String> list = new ArrayList<String>();
      java.sql.ResultSetMetaData rsmd = rs.getMetaData();
      int numColumns = rsmd.getColumnCount();
      while (rs.next()) {
        StringBuffer str = new StringBuffer();
        for (int i = 2; i <= numColumns; i++) {
          if (i > 2) {
            str.trimToSize();
            str.append(' ');
          }
          obj = rs.getObject(i);
          if (obj != null) { str.append(obj); }
        }
        list.add(str.toString());
      }
      
      if (list.size() == 0) {
        return null;
      } else {
        return list.toArray(new String[list.size()]);
      }
    } finally {
      if (rs != null) { rs.close(); }
      if (ps != null) { ps.close(); }
    }
  }

  
  public static Integer getIntegerFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    int n = rs.getInt(db_name);
    return rs.wasNull()? null: Integer.valueOf(n);
  }
  
  public static BigInteger getBigIntegerFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    String n = rs.getString(db_name);
    return rs.wasNull()? null: new BigInteger(n);
  }
  
  //  , and get
  
  public static Short getShortFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    short n = rs.getShort(db_name);
    return rs.wasNull()? null: Short.valueOf(n);
  }
  
  public static Long getLongFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    long n = rs.getLong(db_name);
    return rs.wasNull()? null: Long.valueOf(n);
  }
  
  public static Float getFloatFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    float n = rs.getFloat(db_name);
    return rs.wasNull()? null: Float.valueOf(n);
  }
  
  public static Double getDoubleFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    double n = rs.getDouble(db_name);
    return rs.wasNull()? null: Double.valueOf(n);
  }
  
  public static Byte getByteFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    byte n = rs.getByte(db_name);
    return rs.wasNull()? null: Byte.valueOf(n);
  }
  
  public static Boolean getBooleanFromResultSet(ResultSet rs,
      String db_name) throws SQLException {
    boolean n = rs.getBoolean(db_name);
    return rs.wasNull()? null: Boolean.valueOf(n);
  }
}

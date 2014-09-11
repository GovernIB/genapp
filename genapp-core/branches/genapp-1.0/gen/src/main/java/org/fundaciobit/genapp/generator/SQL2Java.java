package org.fundaciobit.genapp.generator;

import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 * @version 1.0
 */
public class SQL2Java {
  
  private static final Logger log = Logger.getLogger(SQL2Java.class);

  protected static Hashtable<Integer, String> sql2java = new Hashtable<Integer, String>();
  protected static Hashtable<Integer, String> sqltype2sqltypename
    = new Hashtable<Integer, String>();
  protected static Hashtable<String, Integer> java2sql = new Hashtable<String, Integer>();
  protected static Hashtable<Class<?>, Class<?>> object2PrimitiveType
    = new Hashtable<Class<?>, Class<?>>();
  protected static Hashtable<Class<?>, Class<?>> primitiveType2Object
    = new Hashtable<Class<?>, Class<?>>();
  protected static Hashtable<String, Class<?>> primitiveTypesNames
    = new Hashtable<String, Class<?>>();

  protected static final Object[][] SQL2JAVA = new Object[][] {
      // ============= BOOLEAN DATA ======================
      { new Integer(Types.BIT), "java.lang.Boolean", "BIT" },
      { new Integer(Types.BOOLEAN), "java.lang.Boolean", "BOOLEAN" },
      // ============ BYNARY DATA I ========================
      { new Integer(Types.BINARY), "java.io.InputStream", "BINARY" },
      { new Integer(Types.LONGVARBINARY), "java.io.InputStream",
          "LONGVARBINARY" },
      { new Integer(Types.VARBINARY), "java.io.InputStream", "VARBINARY" },
      // ============ BYNARY DATA II ========================
      { new Integer(Types.BLOB), "java.io.InputStream", "BLOB" },
      { new Integer(Types.CLOB), "java.io.InputStream", "CLOB" },
      //{ new Integer(Types.SQLXML), "java.io.InputStream", "SQLXML" },
      // ============ STRING DATA ========================
      { new Integer(Types.CHAR), "java.lang.String", "CHAR" },
      { new Integer(Types.VARCHAR), "java.lang.String", "VARCHAR" },
      { new Integer(Types.LONGVARCHAR), "java.lang.String", "LONGVARCHAR" },
      // ============ TIME DATA ========================
      { new Integer(Types.DATE), "java.sql.Date", "DATE" },
      { new Integer(Types.TIME), "java.sql.Time", "TIME" },
      { new Integer(Types.TIMESTAMP), "java.sql.Timestamp", "TIMESTAMP" },
      // ============= DECIMAL DATA======================
      { new Integer(Types.DOUBLE), "java.lang.Double", "DOUBLE" },
      { new Integer(Types.FLOAT), "java.lang.Double", "FLOAT" },
      { new Integer(Types.REAL), "java.lang.Float", "REAL" },
      { new Integer(Types.NUMERIC), "java.math.BigDecimal", "NUMERIC" },
      { new Integer(Types.DECIMAL), "java.math.BigDecimal", "DECIMAL" },
      // ============= INTEGER DATA======================
      { new Integer(Types.INTEGER), "java.lang.Integer", "INTEGER" },
      { new Integer(Types.SMALLINT), "java.lang.Short", "SMALLINT" },
      { new Integer(Types.TINYINT), "java.lang.Byte", "TINYINT" },
      { new Integer(Types.BIGINT), "java.lang.Long", "BIGINT" },
      // ============= INTEGER DATA======================
      { new Integer(Types.ARRAY), "java.lang.Object[]", "ARRAY" },
      { new Integer(Types.DATALINK), "java.net.URL", "DATALINK" }, };

  // DISTINCT
  // JAVA_OBJECT
  // NCHAR
  // NCLOB
  // NULL
  // NVARCHAR
  // OTHER
  // REF
  // ROWID
  // STRUCT

  protected static final Object[][] JAVA2SQL = new Object[][] {
      // ============ STRING DATA ========================
      { "java.lang.String", new Integer(Types.VARCHAR) },
      // ============= BOOLEAN DATA ======================
      { "java.lang.Boolean", new Integer(Types.BIT) },
      // ============= INTEGER DATA======================
      { "java.lang.Integer", new Integer(Types.INTEGER) },
      { "java.lang.Short", new Integer(Types.SMALLINT) },
      { "java.lang.Byte", new Integer(Types.TINYINT) },
      { "java.lang.Long", new Integer(Types.BIGINT) },
      { "java.math.BigDecimal", new Integer(Types.NUMERIC) },
      // ============= DECIMAL DATA======================
      { "java.lang.Double", new Integer(Types.DOUBLE) },
      { "java.lang.Float", new Integer(Types.REAL) },
      // ============ BYNARY DATA I ========================
      { "java.io.OutputStream", new Integer(Types.VARBINARY) },
      // ============ TIME DATA ========================
      { "java.sql.Date", new Integer(Types.DATE) },
      { "java.sql.Time", new Integer(Types.TIME) },
      { "java.sql.Timestamp", new Integer(Types.TIMESTAMP) } };

  protected static final Class<?>[][] OBJECT2PRIMITIVETYPE = new Class<?>[][] {
      // --- CLASS TYPE -- -- PRIMITIVE TYPE --
      // ============= BOOLEAN DATA ======================
      { java.lang.Boolean.class, java.lang.Boolean.TYPE }, // """boolean"},
      // ============= INTEGER DATA======================
      { java.lang.Integer.class, java.lang.Integer.TYPE }, // "int"},
      { java.lang.Short.class, java.lang.Short.TYPE }, // "short"},
      { java.lang.Byte.class, java.lang.Byte.TYPE }, // "byte"},
      { java.lang.Long.class, java.lang.Long.TYPE }, // "long"},
      // ============= DECIMAL DATA======================
      { java.lang.Double.class, java.lang.Double.TYPE }, // "double"},
      { java.lang.Float.class, java.lang.Float.TYPE }, // "float"}
  };

  protected static final Class<?>[][] PRIMITIVETYPE2OBJECT = new Class<?>[][] {
      // -- PRIMITIVE TYPE -- --- CLASS TYPE --
      // ============= BOOLEAN DATA ======================
      { java.lang.Boolean.TYPE, java.lang.Boolean.class }, // """boolean"},
      // ============= INTEGER DATA======================
      { java.lang.Integer.TYPE, java.lang.Integer.class }, // "int"},
      { java.lang.Short.TYPE, java.lang.Short.class }, // "short"},
      { java.lang.Byte.TYPE, java.lang.Byte.class }, // "byte"},
      { java.lang.Long.TYPE, java.lang.Long.class }, // "long"},
      // ============= DECIMAL DATA======================
      { java.lang.Double.TYPE, java.lang.Double.class }, // "double"},
      { java.lang.Float.TYPE, java.lang.Float.class }, // "float"}
  };

  static {

    int x;
    for (x = 0; x < SQL2JAVA.length; x++) {
      sql2java.put((Integer) SQL2JAVA[x][0], (String) SQL2JAVA[x][1]);
      sqltype2sqltypename
          .put((Integer) SQL2JAVA[x][0], (String) SQL2JAVA[x][2]);
    }

    for (x = 0; x < JAVA2SQL.length; x++) {
      java2sql.put((String) JAVA2SQL[x][0], (Integer) JAVA2SQL[x][1]);
    }

    for (x = 0; x < OBJECT2PRIMITIVETYPE.length; x++) {
      object2PrimitiveType.put(OBJECT2PRIMITIVETYPE[x][0],
          OBJECT2PRIMITIVETYPE[x][1]);
    }

    for (x = 0; x < PRIMITIVETYPE2OBJECT.length; x++) {
      primitiveType2Object.put(PRIMITIVETYPE2OBJECT[x][0],
          PRIMITIVETYPE2OBJECT[x][1]);
      primitiveTypesNames.put(PRIMITIVETYPE2OBJECT[x][0].toString(),
          PRIMITIVETYPE2OBJECT[x][0]);
    }

  }

  public static Class<?> sql2JavaClass(int type) {
    String classe = (String) sql2java.get(new Integer(type));
    if (classe == null)
      return (Object.class);
    try {
      return (Class.forName(classe));
    } catch (Exception e) {
      
      log.info("Error intentant parsejar classe " + classe + "(TYPE = " + type + " )");
      
      e.printStackTrace();
      return (Object.class);
    }
  }

  public static String getSqlTypeName(int sqltype) {
    return sqltype2sqltypename.get(new Integer(sqltype));
  }

  /*
   * public static String javaClassName2PrimitiveType(String c) { String
   * primitive=(String)object2PrimitiveType.get(c);
   * 
   * return(primitive); }
   */
  public static Class<?> javaClass2PrimitiveType(Class<?> c) {
    Class<?> primitive = object2PrimitiveType.get(c);

    return (primitive);
    // return(javaClassName2PrimitiveType(c.getName()));
  }

  public static boolean isPrimitiveType(String primitiveType) {
    return (primitiveTypesNames.containsKey(primitiveType));
  }

  public static Class<?> primitiveType2javaClassName(String primitiveType) {
    return primitiveTypesNames.get(primitiveType);
  }

  public static boolean isPrimitiveType(Class<?> primitiveType) {
    return (primitiveType2Object.containsKey(primitiveType));
  }

  public static Class<?> primitiveType2javaClassName(Class<?> primitiveType) {
    return primitiveType2Object.get(primitiveType);
  }

  /*
   * public static Class type2Class(int type) {
   * 
   * switch(type) { case Types.CHAR: case Types.VARCHAR: case Types.LONGVARCHAR:
   * return String.class;
   * 
   * case Types.BIT: return Boolean.class;
   * 
   * case Types.TINYINT: case Types.SMALLINT: case Types.INTEGER: return
   * Integer.class;
   * 
   * case Types.BIGINT: return Long.class;
   * 
   * case Types.FLOAT: case Types.DOUBLE: return Double.class;
   * 
   * case Types.DATE: return java.sql.Date.class;
   * 
   * case Types.TIME: case Types.TIMESTAMP: return(java.sql.Timestamp.class);
   * 
   * default: return Object.class; } }
   */
}; // Final de classe
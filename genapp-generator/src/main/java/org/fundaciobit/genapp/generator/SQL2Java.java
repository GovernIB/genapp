package org.fundaciobit.genapp.generator;

import java.util.*;
import java.sql.*;

import org.apache.log4j.Logger;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class SQL2Java {
  
  private static final Logger log = Logger.getLogger(SQL2Java.class.getSimpleName());

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
      { Integer.valueOf(Types.BIT), "java.lang.Boolean", "BIT" },
      { Integer.valueOf(Types.BOOLEAN), "java.lang.Boolean", "BOOLEAN" },
      // ============ BYNARY DATA I ========================
      { Integer.valueOf(Types.BINARY), "java.io.InputStream", "BINARY" },
      { Integer.valueOf(Types.LONGVARBINARY), "java.io.InputStream",
          "LONGVARBINARY" },
      { Integer.valueOf(Types.VARBINARY), "java.io.InputStream", "VARBINARY" },
      // ============ BYNARY DATA II ========================
      { Integer.valueOf(Types.BLOB), "java.io.InputStream", "BLOB" },
      { Integer.valueOf(Types.CLOB), "java.io.InputStream", "CLOB" },
      //{ Integer.valueOf(Types.SQLXML), "java.io.InputStream", "SQLXML" },
      // ============ STRING DATA ========================
      { Integer.valueOf(Types.CHAR), "java.lang.String", "CHAR" },
      { Integer.valueOf(Types.VARCHAR), "java.lang.String", "VARCHAR" },
      { Integer.valueOf(Types.LONGVARCHAR), "java.lang.String", "LONGVARCHAR" },
      // ============ TIME DATA ========================
      { Integer.valueOf(Types.DATE), "java.sql.Date", "DATE" },
      { Integer.valueOf(Types.TIME), "java.sql.Time", "TIME" },
      { Integer.valueOf(Types.TIMESTAMP), "java.sql.Timestamp", "TIMESTAMP" },
      // ============= DECIMAL DATA======================
      { Integer.valueOf(Types.DOUBLE), "java.lang.Double", "DOUBLE" },
      { Integer.valueOf(Types.FLOAT), "java.lang.Double", "FLOAT" },
      { Integer.valueOf(Types.REAL), "java.lang.Float", "REAL" },
      { Integer.valueOf(Types.NUMERIC), "java.math.BigDecimal", "NUMERIC" },
      { Integer.valueOf(Types.DECIMAL), "java.math.BigDecimal", "DECIMAL" },
      // ============= INTEGER DATA======================
      { Integer.valueOf(Types.INTEGER), "java.lang.Integer", "INTEGER" },
      { Integer.valueOf(Types.SMALLINT), "java.lang.Short", "SMALLINT" },
      { Integer.valueOf(Types.TINYINT), "java.lang.Byte", "TINYINT" },
      { Integer.valueOf(Types.BIGINT), "java.lang.Long", "BIGINT" },
      // ============= INTEGER DATA======================
      { Integer.valueOf(Types.ARRAY), "java.lang.Object[]", "ARRAY" },
      { Integer.valueOf(Types.DATALINK), "java.net.URL", "DATALINK" }, };

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
      { "java.lang.String", Integer.valueOf(Types.VARCHAR) },
      // ============= BOOLEAN DATA ======================
      { "java.lang.Boolean", Integer.valueOf(Types.BIT) },
      // ============= INTEGER DATA======================
      { "java.lang.Integer", Integer.valueOf(Types.INTEGER) },
      { "java.lang.Short", Integer.valueOf(Types.SMALLINT) },
      { "java.lang.Byte", Integer.valueOf(Types.TINYINT) },
      { "java.lang.Long", Integer.valueOf(Types.BIGINT) },
      { "java.math.BigDecimal", Integer.valueOf(Types.NUMERIC) },
      // ============= DECIMAL DATA======================
      { "java.lang.Double", Integer.valueOf(Types.DOUBLE) },
      { "java.lang.Float", Integer.valueOf(Types.REAL) },
      // ============ BYNARY DATA I ========================
      { "java.io.OutputStream", Integer.valueOf(Types.VARBINARY) },
      // ============ TIME DATA ========================
      { "java.sql.Date", Integer.valueOf(Types.DATE) },
      { "java.sql.Time", Integer.valueOf(Types.TIME) },
      { "java.sql.Timestamp", Integer.valueOf(Types.TIMESTAMP) } };

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
  
  
  public static final Map<String, Class<?>> STRINGTOCLASS = new HashMap<String, Class<?>>();

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
    
    
    for (x = 0; x < OBJECT2PRIMITIVETYPE.length; x++) {
        STRINGTOCLASS.put(OBJECT2PRIMITIVETYPE[x][0].getName(), OBJECT2PRIMITIVETYPE[x][0]);
        STRINGTOCLASS.put(OBJECT2PRIMITIVETYPE[x][1].getName(), OBJECT2PRIMITIVETYPE[x][1]);
    }
    STRINGTOCLASS.put(String.class.getName(), String.class);

    STRINGTOCLASS.put(java.sql.Date.class.getName(), java.sql.Date.class);
    STRINGTOCLASS.put(java.sql.Time.class.getName(), java.sql.Time.class);
    STRINGTOCLASS.put(java.sql.Timestamp.class.getName(), java.sql.Timestamp.class);

  }

  public static Class<?> sql2JavaClass(int type) {
    String classe = (String) sql2java.get(Integer.valueOf(type));
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
    return sqltype2sqltypename.get(Integer.valueOf(sqltype));
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
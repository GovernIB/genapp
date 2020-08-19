package org.fundaciobit.genapp;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.common.db.DataBaseInfo;
import org.fundaciobit.genapp.generator.SQL2Java;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */

public final class DatabaseManager {
  
  private static final Logger log = Logger.getLogger(DatabaseManager.class.getSimpleName());

  
// 1 TABLE_CAT String => table catalog (may be null) 
// 2 TABLE_SCHEM String => table schema (may be null) 
// 3 TABLE_NAME String => table name 
// 4 COLUMN_NAME String => column name 
// 5 DATA_TYPE int => SQL type from java.sql.Types 
// 6 TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified 
// 7 COLUMN_SIZE int => column size. 
// 8 BUFFER_LENGTH is not used. 
// 9 DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable. 
// 10 NUM_PREC_RADIX int => Radix (typically either 10 or 2) 
// 11 NULLABLE int => is NULL allowed. 
//        columnNoNulls - might not allow NULL values 
//        columnNullable - definitely allows NULL values 
//        columnNullableUnknown - nullability unknown 
// 12 REMARKS String => comment describing column (may be null) 
// 13 COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null) 
// 14 SQL_DATA_TYPE int => unused 
// 15 SQL_DATETIME_SUB int => unused 
// 16 CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column 
// 17 ORDINAL_POSITION int => index of column in table (starting at 1) 
// 18 IS_NULLABLE String => ISO rules are used to determine the nullability for a column. 
//            YES --- if the parameter can include NULLs 
//            NO --- if the parameter cannot include NULLs 
//            empty string --- if the nullability for the parameter is unknown 
// 19 SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF) 
// 20 SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF) 
// 21 SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF) 
// 22 SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF) 
// 23 IS_AUTOINCREMENT String => Indicates whether this column is auto incremented 
//             YES --- if the column is auto incremented 
//             NO --- if the column is not auto incremented 
//             empty string --- if it cannot be determined whether the column is auto incremented parameter is unknown 

  
  public static final int DB_COLUMN_NAME = 4;
  public static final int DB_DATA_TYPE = 5;
  public static final int DB_TYPE_NAME = 6;
  public static final int DB_COLUMN_SIZE = 7;
  public static final int DB_DECIMAL_DIGITS = 9;
  public static final int DB_NULLABLE = 11;
  public static final int DB_COLUMN_REMARKS = 12; // Descripció de la Columna
  public static final int DB_COLUMN_DEF_VALUE = 13; // Valor per defecte
  
  

  //public static final int[] DB_DATA = { DB_TYPE_NAME, DB_COLUMN_NAME,
  //    DB_DATA_TYPE, DB_COLUMN_SIZE, DB_DECIMAL_DIGITS, DB_NULLABLE };


  public static final String sJDBCTypes[][] = {
      {
        "MySQL",
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://devlinux:3306/vigitec" },
      {
          "HypersonicSQL Standalone",
          "org.hsqldb.jdbcDriver",
          "jdbc:hsqldb:file:<<PATH>>" },
      { "HypersonicSQL In-Memory", "org.hsql.jdbcDriver",
          "jdbc:HypersonicSQL:." },
      { "JDBC-ODBC Brigde from Sun", "sun.jdbc.odbc.JdbcOdbcDriver",
          "jdbc:odbc:test" },
      { "HypersonicSQL Server", "org.hsql.jdbcDriver",
          "jdbc:HypersonicSQL:hsql://localhost" },
      { "HypersonicSQL WebServer", "org.hsql.jdbcDriver",
          "jdbc:HypersonicSQL:http://localhost" },
      { "Oracle", "oracle.jdbc.driver.OracleDriver", "jdbc:oracle:oci8:@" },
      
      { "PostgreSQL", "org.postgresql.Driver", "jdbc:postgresql://[localhost]:5432/[bbdd]" },
      
      { "IBM DB2", "COM.ibm.db2.jdbc.app.DB2Driver", "jdbc:db2:test" },
      { "Cloudscape RMI", "RmiJdbc.RJDriver",
          "jdbc:rmi://localhost:1099/jdbc:cloudscape:test;create=true" },
      { "InstantDb", "jdbc.idbDriver", "jdbc:idb:sample.prp" },
      { "PointBase", "com.pointbase.jdbc.jdbcUniversalDriver",
          "jdbc:pointbase://localhost/sample" }, // PUBLIC / public
  };
  
	
	private final DataBaseInfo databaseInfo;
	
	
	
	
	
  public DatabaseManager(DataBaseInfo databaseInfo) {
    super();
    this.databaseInfo = databaseInfo;
  }

  
  
  public DataBaseInfo getDatabaseInfo() {
    return databaseInfo;
  }

  
  public static String schemaName = null;


  public TableInfo[] getTablesOfDataBase(String schema, boolean isNew, String[] idiomes) throws Exception, SQLException {

    Connection conn = this.databaseInfo.getConnection();
    DatabaseMetaData dMeta = conn.getMetaData();
    
    // Seleccionar Schema
    if (isNew) {
      ResultSet res = dMeta.getSchemas();
      log.info("List of schemas: "); 
      List<String> schemas = new ArrayList<String>();
      while (res.next()) {
        
        //log.info("Column Count " + res.getMetaData().getColumnCount());
        String s = res.getString(1);
         log.info(
            "SHEMA = "+ s
           );
         
         schemas.add(s);
      }
      res.close();
      
      Object[] possibilities = schemas.toArray();
      schemaName = (String)JOptionPane.showInputDialog(
                          null,
                          "Selecciona l'esquema:",
                          "Selecciona l'esquema",
                          JOptionPane.PLAIN_MESSAGE,
                          null,
                          possibilities,
                          possibilities[0]);
      
      schema = schemaName;
    }

    log.info("FINAL SCHEMA SELECCIONAT = " + schema); 

    
    String usertables[] = { "TABLE" } ; //"TABLE" };
    ResultSet result = dMeta.getTables(null, schema, null, usertables);
    List<TableInfo> tables = new ArrayList<TableInfo>();
    
//    1 TABLE_CAT String => table catalog (may be null) 
//    2 TABLE_SCHEM String => table schema (may be null) 
//    3 TABLE_NAME String => table name 
    final int DB_TABLE_NAME = 3;
//    4 TABLE_TYPE String => table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM". 
//    5 REMARKS String => explanatory comment on the table 
    final int DB_TABLE_REMARKS = 5;
//    6 TYPE_CAT String => the types catalog (may be null) 
//    7 TYPE_SCHEM String => the types schema (may be null) 
//    8 TYPE_NAME String => type name (may be null) 
//    9 SELF_REFERENCING_COL_NAME String => name of the designated "identifier" column of a typed table (may be null) 
//    10 REF_GENERATION String => specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED". (may be null) 

    

    while (result.next()) {
      String name = result.getString(DB_TABLE_NAME);
      String descripcio = result.getString(DB_TABLE_REMARKS);
      
      TableInfo tableInfos = new TableInfo(name, idiomes);
      tableInfos.setDescripcio(descripcio);
      Fields fields = getFieldsInfoOfTable(schema, name, idiomes);
      tableInfos.setFields(fields.fields);
      tableInfos.setMultipleUniques(getMultipleUniques(fields));
      
      tables.add(tableInfos);
    }

    result.close();
    return (TableInfo[]) tables.toArray(new TableInfo[tables.size()]);
  }
  
  
  
  public Integer getInt(Object obj) throws Exception {
    if (obj == null) {
      return null;
    }
    if (obj instanceof Integer) {
      return (Integer) obj;
    }
    if (obj instanceof BigDecimal) {
      return ((BigDecimal)obj).intValue();
    }
    throw new Exception("Cannot convert " + obj.getClass() + " to Integer.");
  }
  
  
  public static class Fields {
    
    public final FieldInfo[] fields;
    
    public final Map<String, List<String>> uniqueValues;

    /**
     * @param fileds
     * @param uniqueValues
     */
    public Fields(FieldInfo[] fileds, Map<String, List<String>> uniqueValues) {
      super();
      this.fields = fileds;
      this.uniqueValues = uniqueValues;
    }
    
    
    
  }
  

  public Fields getFieldsInfoOfTable(String schema, String tableName, String[] idiomes) throws Exception {

    Connection conn = this.databaseInfo.getConnection();
    DatabaseMetaData dMeta = conn.getMetaData();

    Vector<FieldInfo> rows = new Vector<FieldInfo>();
    // Recogemos los nombres de los campos que son PK y los guardamos
    // wn wl vector pks
    Map<String, String> pks = new HashMap<String, String>();
    String columnName = null;

    try {

      ResultSet rspk = dMeta.getPrimaryKeys(null, schema, tableName);

      while (rspk.next()) {
        columnName = rspk.getString(4);
        String pkName = rspk.getString(6);
        pks.put(columnName, pkName);
        log.info("Primary KEY [" + columnName + "] = " + pkName);
      }

      rspk.close();

    } catch (SQLException ex) {
      log.info("- Error leyendo PK's:" + ex);
    }

    // =========== AUTO INCREMENT ====================
    // AUTO INCREMENT
    Map<String, Boolean> autoIncrement = new HashMap<String, Boolean>();
    try {
      /*
       * # IS_AUTOINCREMENT String => Indicates whether this column is auto
       * incremented YES --- if the column is auto incremented NO --- if the
       * column is not auto incremented empty string --- if it cannot be
       * determined whether the column is auto incremented parameter is unknown
       */

      Statement s = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
          ResultSet.CONCUR_READ_ONLY);
      ResultSet rs = s.executeQuery("SELECT * FROM " + tableName
          + " WHERE 1 = 0");
      ResultSetMetaData meta = rs.getMetaData();
      int columnCount = meta.getColumnCount();

      for (int i = 1; i <= columnCount; i++) {
        String colName = meta.getColumnName(i);
        boolean isAutoInc = meta.isAutoIncrement(i);
        autoIncrement.put(colName, isAutoInc);
      }
      
      rs.close();
      s.close();
    } catch(Exception e) {
      // TODO 
      e.printStackTrace();
    }

    Map<String, List<ForeignKey>> foreignKeys = getForeignKeys(tableName, conn,
        dMeta);
    
    
    // informació de UNIQUE values
    Map<String, List<String>> uniqueValues = getUniqueFields(schema, tableName, dMeta);
    
    // Informacio de indexos
    Set<String> ignoreIndexes = new HashSet<String>();
    ignoreIndexes.addAll(pks.values());
    ignoreIndexes.addAll(uniqueValues.keySet());
    
    Map<String, String> indexesByColumnName = getIndexFields(schema, tableName, dMeta, ignoreIndexes);
    

    // Recogemos la informacion de cadacolumna de la tabla

    ResultSet resultSet = dMeta.getColumns(null,schema, tableName, null);
    // ResultSetMetaData metaData = resultSet.getMetaData();
    /*
     * String dbColumnNames[]; int columnTypes[];
     * 
     * dbColumnNames = new String[totalOfColumns]; columnTypes = new
     * int[totalOfColumns];
     * 
     * // (1) Recogemos informacion para la cabecera de la tabla
     * 
     * // (1.1) Definimos los datos adicionalesDefnimos el primer tipo de // la
     * tabla que es las PK's dbColumnNames[0] = "PRIMARYKEY"; columnTypes[0] =
     * Types.BIT;
     * 
     * // (1.2) Definimos los otros nombres y tipo de las columnas de la //
     * tabla for (int x = 0; x < DB_DATA.length; x++) { dbColumnNames[x + 1] =
     * metaData.getColumnLabel(DB_DATA[x]); columnTypes[x + 1] =
     * metaData.getColumnType(x + 1);
     * 
     * log.info("Name[" + (x + 1) + "]: " + dbColumnNames[x + 1]); }
     */
    

    // (2) Llenamos la tabla de informacion
    int x = 0;
    log.info("--- Camps --");

    while (resultSet.next()) {

      FieldInfo newRow = new FieldInfo();

      newRow.digits = getInt(resultSet.getObject(DB_DECIMAL_DIGITS));
 
      Object objN = resultSet.getObject(DB_NULLABLE);
      newRow.isNotNullable = (getInt(objN) == DatabaseMetaData.columnNoNulls);

      // (2.1) Rellenamos el campo de Java Name
      columnName = (String) resultSet.getObject(DB_COLUMN_NAME);
      newRow.javaName = columnName; // POS=1 --> Java Name
      
      for (int i = 0; i < idiomes.length; i++) {
        newRow.getLabels().put(idiomes[i], columnName);
      }
      
      
      log.info((x++) + ".- " + columnName);

      // JAVA TYPE
      {
        Object objD = resultSet.getObject(DB_DATA_TYPE);
        // log.info(">>>"+obj.getClass().getName()+" == "
        // +obj);
        newRow.setJavaType(SQL2Java.sql2JavaClass(getInt(objD)));
        // Verificamos si tiene asociado un tipo primitivo solo en el
        // caso de campos PrimaryKey o NotNull
        if (newRow.isPrimaryKey() || newRow.isNotNullable
          || Boolean.class.equals(newRow.getJavaType())) {
          try { 
            Class<?> primitive = SQL2Java
                .javaClass2PrimitiveType(newRow.getJavaType());

            if (primitive != null) {
              newRow.setJavaType(primitive);
            }

          } catch (Exception e) {
            log.info("SIMPLE: " + e.toString());
          }
        }
        
        // Descripcio
        String desc = resultSet.getString(DB_COLUMN_REMARKS);
        newRow.descripcio = ((desc == null)? "" : desc);
        
        // Valor per defecte
        newRow.setDefaultValue(resultSet.getString(DB_COLUMN_DEF_VALUE));
        
        log.info(" \n\n\n   VALOR PER DEFECTE => " +  newRow.getDefaultValue() + " \n\n\n   ");
        
        
      }

      // (2.1) Rellenamos el campo de PK

      // PRIMARY KEY
      String pk = pks.get(columnName);
      newRow.setPK(pk); // POS=0 -->
      
      // UNIQUE en PK ve implicit
      if (pk == null) {
        // UNIQUE
        newRow.setUnique(isUniqueOnly(uniqueValues, columnName));
      }
      
      // INDEX
      String index = indexesByColumnName.get(columnName);
      if (index != null) {
        newRow.setIndex(index);
      }

      // FOREIGN KEY
      List<ForeignKey> fk = foreignKeys.get(columnName);
      if (fk == null) {
        newRow.foreignKeys = new ForeignKey[0];
      } else {
        newRow.foreignKeys = fk.toArray(new ForeignKey[fk.size()]);
      }

      newRow.size = getInt(resultSet.getObject(DB_COLUMN_SIZE));

      newRow.sqlName = columnName;

      newRow.sqlType = getInt(resultSet.getObject(DB_DATA_TYPE));

      // AUTO INCREMENT
      Boolean autoInc =  autoIncrement.get(newRow.sqlName); 
      newRow.isAutoIncrement = autoInc == null? false : autoInc.booleanValue();

      newRow.webFieldInfo =
        WebFieldInfo.getDefaultWebTypeFromFieldInfo(tableName, newRow);

      // (2.3) Guardamos la fila
      rows.addElement(newRow);
    }
    log.info("------------");

    resultSet.close();
    
    return new Fields(rows.toArray(new FieldInfo[rows.size()]), uniqueValues);
  }
  
  
  
  public static MultipleUnique[] getMultipleUniques(Fields fields) {
    Map<String, List<String>> map = fields.uniqueValues;
  
    Set<String> primarykeyNames = new HashSet<String>();
    for(FieldInfo f : fields.fields) {
      if (f.isPrimaryKey()) {
        primarykeyNames.add(f.getSqlName());
      }
    }
    
    List<MultipleUnique> list = new ArrayList<MultipleUnique>();
    for(String name : map.keySet()) {
      List<String> columns = map.get(name);
      if (columns.size() > 1) {
        boolean allColArePrimary = true;
        
        for (String colName : columns) {
          if (!primarykeyNames.contains(colName)) {
            allColArePrimary = false;
            break;
          }
        }
        if (!allColArePrimary) {
          list.add(new MultipleUnique(name, columns.toArray(new String[columns.size()])));
        }
      }
    }
    
    if (list.size() == 0) {
      return null;
    } else {
      return list.toArray(new MultipleUnique[list.size()]);
    }
    
    /*
    Collection<List<String>> uniqueValuesOrig = map.values();
    
    List<List<String>> uniqueValues = new ArrayList<List<String>>();
    
    for (List<String> unics : uniqueValuesOrig) {
      if (unics.size() > 1) {        
        uniqueValues.add(unics);       
      }
    }
    
    if (uniqueValues.size() == 0) {
      return null;
    }
    
    String[][] uniques = new String[uniqueValues.size()][];
    
    int i = 0;
    for (List<String> unics : uniqueValues) {
      uniques[i] = unics.toArray(new String[unics.size()]);
      i++;
    }
    return uniques;
    */
  }
  
  
  
  public static String isUniqueOnly(Map<String, List<String>> map, String columnName) {
    Set<String> keys = map.keySet();
    for (String key : keys) {
      List<String> uniqueValues = map.get(key);
      //for (List<String> uniqueValues : col) 
      {
        if (uniqueValues.size() == 1 && uniqueValues.contains(columnName)) {
          return key;
        }
      }
    }
    
    return null;
  }
  
  
  
  private static Map<String, String> getIndexFields(String schemaName,
      String tableName, DatabaseMetaData meta, Set<String> ignoreIndexes)
      throws SQLException {

    Map<String, String> indexes = new HashMap<String, String>();
    ResultSet rs = null;
    try {
      
      rs = meta.getIndexInfo(null, schemaName, tableName, false, false);
      while (rs.next()) {
        String indexName = rs.getString("INDEX_NAME");
        
        if (ignoreIndexes.contains(indexName)) {
          continue;
        }
        
        /*
         * String table = rs.getString("TABLE_NAME"); String schema =
         * rs.getString("TABLE_SCHEM");
         */
        if (indexName != null) {
          String columnName = rs.getString("COLUMN_NAME");

          log.info("INDEX = " + indexName + " => " + columnName);
          

          
           indexes.put(columnName, indexName);

          /*
           * log.info("****************************************");
           * log.info("Table: " + schema + "." + table);
           * log.info("Index Name: " + indexName);
           * log.info("Column Name: " + columnName);
           */
        } else {

        }
      }
      /*
      if (indexes.size() != 0) {
        System.in.read();
      }
      */

    } catch (Exception e) {
      // TODO
      System.err.println("No puc obtenir informacio de INDEXES de la taula " + tableName);
      e.printStackTrace();
    } finally {
      if (rs != null) {
        rs.close();
      }
    }

    return indexes;

  }
  
  
  
  
  private static Map<String, List<String>> getUniqueFields(String schemaName,
      String tableName, DatabaseMetaData meta) throws SQLException {

    Map<String, List<String>> uniques = new HashMap<String, List<String>>();
    ResultSet rs = null;
    try {
      boolean listUniqueIndex = true;
      rs = meta.getIndexInfo(null, schemaName, tableName, listUniqueIndex, true);
      while (rs.next()) {
        String indexName = rs.getString("INDEX_NAME");
        /*
         * String table = rs.getString("TABLE_NAME"); String schema =
         * rs.getString("TABLE_SCHEM");
         */
        if (indexName != null) {
          String columnName = rs.getString("COLUMN_NAME");

          log.info("UNIQUES = " + indexName + " => " + columnName);

          List<String> unics = uniques.get(indexName);
          if (unics == null) {
            unics = new ArrayList<String>();
            uniques.put(indexName, unics);
          }

          unics.add(columnName);

          /*
           * log.info("****************************************");
           * log.info("Table: " + schema + "." + table);
           * log.info("Index Name: " + indexName);
           * log.info("Column Name: " + columnName);
           */
        } else {

        }
      }

    } catch (Exception e) {
      // TODO
      System.err.println("No puc obtenir informacio de UNIQUES de la taula " + tableName);
      e.printStackTrace();
    } finally {
      if (rs != null) {
        rs.close();
      }
    }

    return uniques;

  }

  
  
  

  /**
   * 
   */
  private Map<String, List<ForeignKey>> getForeignKeys(String tableName,
      Connection conn, DatabaseMetaData dMeta) throws SQLException {
    Map<String, List<ForeignKey>> foreigkeys = new Hashtable<String, List<ForeignKey>>();
    String methodName;
    for (int i = 0; i < 2; i++) {
      ResultSet rs;
      int type;
      if (i == 0) {
        methodName = ">>>> ExportedKeys";
        type = ForeignKey.EXPORTED;
        rs = dMeta.getExportedKeys(conn.getCatalog(), null, tableName);
      } else {
        methodName = "<<<< ImportedKeys";
        type = ForeignKey.IMPORTED;
        rs = dMeta.getImportedKeys(conn.getCatalog(), null, tableName);
      }

      String tableNameDst;
      String columnNameDst;
      String columnNameOrig;

      while (rs.next()) {
        String pkTableName = rs.getString("PKTABLE_NAME");
        String pkColumnName = rs.getString("PKCOLUMN_NAME");
        String fkTableName = rs.getString("FKTABLE_NAME");
        String fkColumnName = rs.getString("FKCOLUMN_NAME");
        
        String fkName = rs.getString("FK_NAME");
        // log.info(" FK NAME = " + fkName);
        
        /*
         * int fkSequence = rs.getInt("KEY_SEQ");
         * log.info("==== FOREIGN KEYs:");
         * log.info(methodName + ": pkTableName="+pkTableName);
         * log.info(methodName + ": pkColumnName="+pkColumnName);
         * log.info(methodName + ": fkTableName="+fkTableName);
         * log.info(methodName + ": fkColumnName="+fkColumnName);
         * log.info(methodName + ": fkSequence="+fkSequence);
         */
        if (pkTableName.equals(tableName)) {
          tableNameDst = fkTableName;
          columnNameDst = fkColumnName;
          columnNameOrig = pkColumnName;
        } else {
          tableNameDst = pkTableName;
          columnNameDst = pkColumnName;
          columnNameOrig = fkColumnName;
        }

        log.info("====[" + fkName + "] " + methodName + " FOREIGN KEY["
            + columnNameOrig + "] -> " + tableNameDst + "[" + columnNameDst
            + "]");

        List<ForeignKey> fk = foreigkeys.get(columnNameOrig);
        if (fk == null) {
          fk = new ArrayList<ForeignKey>();
          foreigkeys.put(columnNameOrig, fk);
        }
        fk.add(new ForeignKey(fkName, tableNameDst, columnNameDst, type));
      }
      rs.close();
    }
    return foreigkeys;
  }
  

  public String[][] execConsulta(String query) throws Exception {
    String[][] dades = null;

    Connection connection = this.databaseInfo.getConnection();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(query);
    ResultSetMetaData metaData = resultSet.getMetaData();

    int numberOfColumns = metaData.getColumnCount();
    int numfiles = 0;

    // Get all rows.

    Vector<String[]> v = new Vector<String[]>();
    String tmp[] = null;

    while (resultSet.next()) {
      numfiles++;
      tmp = new String[numberOfColumns];

      // System.out.print("\nFila["+numfiles+"]=");

      for (int i = 1; i < numberOfColumns + 1; i++) {
        tmp[i - 1] = resultSet.getString(i);
        // System.out.print(tmp[i-1]+"\t");
      }

      v.addElement(tmp);

    }
    resultSet.close();
    statement.close();
    // close(); Need to copy the metaData, bug in jdbc:odbc driver.

    dades = new String[numfiles][];

    for (int y = 0; y < numfiles; y++) {
      dades[y] = (String[]) v.elementAt(y);
    }

    return dades;

  }
  
  public int execute(String exec) {
    try {
      Connection conn = this.databaseInfo.getConnection();
      Statement stmnt = conn.createStatement();
      int num = stmnt.executeUpdate(exec);
      stmnt.close();

      conn.commit();

      return (num);
    } catch (Exception e) {
      log.info("JDBCAdapter::execute() --> " + e);
      return (-1);
    }

  }

  public int count(String exec) {
    try {
      Connection con = this.databaseInfo.getConnection();
      Statement statement = con.createStatement();
      try {
        return (statement.getUpdateCount());
      } finally {
        statement.close();
      }
    } catch (Exception e) {
      log.info("JDBCAdapter::count() --> " + e);
      return (-1);
    }
  }

  /*
   * public void executeQuery(String query) { if (connection == null ||
   * statement == null) {
   * System.err.println("There is no database to execute the query.");
   * return(null); } try { resultSet = statement.executeQuery(query); metaData =
   * resultSet.getMetaData();
   * 
   * int numberOfColumns = metaData.getColumnCount(); columnNames = new
   * String[numberOfColumns]; // Get the column names and cache them. // Then we
   * can close the connection. for(int column = 0; column < numberOfColumns;
   * column++) { columnNames[column] = metaData.getColumnLabel(column+1); }
   * 
   * // Get all rows. rows = new Vector(); while (resultSet.next()) { Vector
   * newRow = new Vector(); for (int i = 1; i <= getColumnCount(); i++) {
   * newRow.addElement(resultSet.getObject(i)); } rows.addElement(newRow); } //
   * close(); Need to copy the metaData, bug in jdbc:odbc driver.
   * fireTableChanged(null); // Tell the listeners a new table has arrived. }
   * catch (SQLException ex) { System.err.println(ex); } }
   */

  public String getColumnClass(ResultSetMetaData metaData, int column) {
    int type;
    try {
      type = metaData.getColumnType(column + 1);
    } catch (SQLException e) {
      return "super.getColumnClass(column)";
    }

    switch (type) {
    case Types.CHAR:
    case Types.VARCHAR:
    case Types.LONGVARCHAR:
      return "String.class";

    case Types.BIT:
      return "Boolean.class";

    case Types.TINYINT:
    case Types.SMALLINT:
    case Types.INTEGER:
      return "Integer.class";

    case Types.BIGINT:
      return "Long.class";

    case Types.FLOAT:
    case Types.DOUBLE:
      return "Double.class";

    case Types.DATE:
      return "java.sql.Date.class";

    default:
      return "Object.class";
    }
  }

  
}
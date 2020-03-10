package org.fundaciobit.genapp.generator;

import java.util.ArrayList;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.CodeGenerator.DaoCommonCode;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */

public class DaoSqlGenerator {
  
  
  /**
   * DAO MANAGER
   * @param modelPackage
   * @param daoPackage
   * @param project
   * @return
   */
  public static SourceFile generateSQLDaoManager(String sqldaoPackage,
      BasicPackages packages, Project project) {
    
    StringBuffer manager = new StringBuffer();
    
    String iname = "I" + project.projectName + "DaoManagers";
    
    String name = project.projectName + "SqlDaoManagers";
    
    manager.append("package " + sqldaoPackage + ";\n");
    manager.append("\n");
    manager.append("import " + packages.modelPackage + ".*;\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("\n");
    manager.append("public final class " + name + " implements " + iname + "{\n");
    
    
    for (int i = 0; i < project.tables.length; i++) {
      String managerFileName = project.tables[i].getNameJava() + "Manager";
      manager.append("\tpublic I" + managerFileName + " get" + managerFileName + "() {\n");
      manager.append("\t  return " + managerFileName + ".getInstance();\n");
      manager.append("\t};\n");
      manager.append("\n");
    }
    
    manager.append("\n");
    manager.append("}");
    
    
    return new SourceFile(name + ".java", manager.toString());
  }
  
  
  

  
  // ====================================================================
  // ========================== MANAGER =================================
  // ====================================================================

    
  public static SourceFile generateCodeForManager(String tableNameSQL, String tableNameJava, 
    String sqldaoPackage, BasicPackages packages,
    FieldInfo[] fields, Project project, BeanCode beanCode) {
    
    String projectName = project.projectName;
    String databaseManager = Project.getDBManagerName(projectName);
    DaoCommonCode dao = beanCode.daoCommonCode;
        
    /** Para metodos de Manager */   
    
    StringBuffer allDBNames = new StringBuffer();
    //StringBuffer createDBNames = new StringBuffer();
    StringBuffer createPrepStatValues = new StringBuffer();
    StringBuffer createPrepStatSetObjects = new StringBuffer();
    
    
    
    StringBuffer createPrepStatValuesNotNull = new StringBuffer();
    StringBuffer allDBNamesNotNull = new StringBuffer();
    StringBuffer createPrepStatSetObjectsNotNull = new StringBuffer();
    

    StringBuffer findGetmanager = new StringBuffer();
    StringBuffer findPrepStat = new StringBuffer();

    /** Variable de uso */
    Class<?> typeClass;
    String type;
    String name;

    boolean isPK, isNotNull;
    String wherePK = "";
    ArrayList<String> whereQuery = new ArrayList<String>();
    String prepStatPK = "";
    
    /**
     * Generamos el prepared statement para buscar un campo con un PK
     * determinada
     */
    for (int r = 0; r < fields.length; r++) {
      FieldInfo field = fields[r];
      isPK = field.isPrimaryKey();
      isNotNull = field.isNotNullable;

      if (isPK || isNotNull) {
        name = field.javaName;
        typeClass = field.getJavaType();
        type = typeClass.getName();

       
        


        if (isPK) {
          
          if (wherePK.length() > 0) {
            wherePK = wherePK + " + \" and \"";            
          }
          wherePK = wherePK + " + " +  name.toUpperCase() + ".sqlName + \" = ? \" ";
          
          whereQuery.add(name.toUpperCase() + ".equal(_" + name + "_)");
          
          
          String tmp;
          if (SQL2Java.isPrimitiveType(typeClass)) {
            tmp = "\t\t ps.set" + Character.toUpperCase(type.charAt(0))
                + type.substring(1, type.length()) + "(++posQuery,";
            prepStatPK = prepStatPK + tmp + name + ");\n";
            findPrepStat.append(tmp + "_" + name + "_);\n");
          } else {
            tmp = "\t\t ps.setObject(++posQuery,";
            prepStatPK = prepStatPK + tmp + name + ");\n";
            findPrepStat.append(tmp + "_" + name + "_);\n");
          }
        }
      }
    }
    wherePK = wherePK + "";
    
    

    String db_name;
    for (int r = 0; r < fields.length; r++) {
      FieldInfo field = fields[r];
      typeClass = field.getJavaType();
      type = typeClass.getName();
      name = field.javaName;
      db_name = field.sqlName;
      isPK = field.isPrimaryKey();
      isNotNull = field.isNotNullable;

      // Atributos
      if (isPK) {        
        allDBNamesNotNull.append("," + db_name);
        createPrepStatValuesNotNull.append(", ? ");
      } else {        
        if (field.isNotNullable) {
          allDBNamesNotNull.append("," + db_name);
          createPrepStatValuesNotNull.append(", ? ");
        }
      }

      

      // Parametres SQL de create i find
      if (allDBNames.length() != 0) {
        allDBNames.append(" + \", \" + "); 
      }
      allDBNames.append(db_name.toUpperCase() + ".sqlName ");

      if (isPK || isNotNull) {
        // Create
        createPrepStatSetObjectsNotNull.append(
            "\t\t ps.setObject(++posQuery,_" + name + "_);\n");
      }


      // Creació
      // TODO tot s'ha de fer via ps.setObject, ps.setInt, ...
      boolean isPrimitive = SQL2Java.isPrimitiveType(typeClass);
      if (isPrimitive) {
        createPrepStatValues.append(",\"+_" + name + "_+\"");
      } else {
        createPrepStatValues.append(", ? ");
        createPrepStatSetObjects.append("\t\t ps.setObject(++posQuery,_"
            + name + "_);\n");
      }

     
      findGetmanager.append("\t\t_new" + tableNameJava + "Object." + name
          + "=" + generateGetResultSet(field));

    }

    
    createPrepStatValues.deleteCharAt(0);
    //allDBNames.deleteCharAt(0);
    //createDBNames.deleteCharAt(0);


    if (createPrepStatValuesNotNull.length() > 0) {
      createPrepStatValuesNotNull.deleteCharAt(0);
    }
    if (allDBNamesNotNull.length() > 0) {
      allDBNamesNotNull.deleteCharAt(0);
    }

    
    StringBuffer manager = new StringBuffer("");

    /** Cabecera */
    manager.append("\n");

    if (sqldaoPackage != null)
      manager.append("package " + sqldaoPackage + ";\n");

    String managerFileName = tableNameJava + "Manager";

    manager.append("import java.sql.*;\n");
    manager.append("import java.util.*;\n");
    manager.append("import " + packages.modelPackage + ".*;\n");
    manager.append("import " + packages.entityPackage + ".*;\n");
    manager.append("import " + packages.beanPackage + ".*;\n");
    manager.append("import " + packages.fieldsPackage + ".*;\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".TableName;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".Where;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".TableManager;\n");
    manager.append("import " + GenAppPackages.PKG_BASE + ".common.db.DataBaseInfo;\n");
    
    // XYZ ZZZ
    //manager.append("import " + GenAppPackages.PKG_BASE + ".common.events.ModificationManager;\n");
    
    manager.append("\n\n");
    manager.append("public class " + managerFileName 
        + "\n\t\t extends TableManager<" + tableNameJava + ", " + dao.pkClass + ">"
        + "\n\t\t implements I"  + tableNameJava + "Manager, " + tableNameJava + "Fields {\n\n\n");
    
    manager.append("\n\n");
    manager.append("  private static final long serialVersionUID = "
        + managerFileName.hashCode() + "L;\n\n");
    
    manager.append("\t public static final TableName<" + tableNameJava
        + "> _TABLENAME =  new TableName<" + tableNameJava + ">(_TABLE_);\n");
    manager.append("\n");
    
    // (2) Listener
 // XYZ ZZZ
    /*
    String modification = "ModificationManager<" 
      + ModelGenerator.getInterfaceName(tableNameJava)+ ">";
    manager.append("\n\n");
    // TODO llevar static !!!!!!
    manager.append("  static final " + modification + " __eventManager =" +
        " new " + modification + "();\n\n");
        */
    
    // (0) Instance
    manager.append("  private static final " + managerFileName 
        + " __manager = new " + managerFileName + "();\n\n");


    // CONSTRUCTOR
    manager.append("  private " + managerFileName + "() { \n");
    manager.append("    super(_TABLENAME, ALL_" + tableNameJava.toUpperCase()
        + "_FIELDS," +  "__eventManager);\n");
    manager.append("  }\n");
    
   
    // (1) Parent Abstract Methods
    //DataBaseUtils.closeConnection(con);
    manager.append("\tpublic Connection getConnection() throws Exception {\n");
    manager.append("\t\t return " + databaseManager + ".getConnection();\n");
    manager.append("\t}\n\n");



    // (7) Creates
    
    // 7.1.- Common Code Creates
    
    
    
    boolean createEq;
    createEq = dao.createParamsNotNull.toString().equalsIgnoreCase(dao.createParams.toString());
    String[] versionParams = { dao.createParams.toString() ,
        createEq? null : dao.createParamsNotNull.toString() };
    String[] versionBeanParams = { dao.createVariableNames.toString(),
        dao.createVariableNamesNotNull.toString() };
    
    for (int i = 0; i < versionBeanParams.length; i++) {
      if ( versionParams[i] == null) continue;
      manager.append("\tpublic synchronized " + tableNameJava + " create("
          + versionParams[i] + ") throws Exception {\n");
      
      if (dao.existAutoIncrementKey != null) {

        // TODO S'HA DE CANVIAR PER NO SE QUE
        manager.append(selectMax(tableNameSQL, dao.existAutoIncrementKey));
        
      }
      manager.append("\t\t" + tableNameJava + " __bean =  new " + tableNameJava + "Bean("
          + versionBeanParams[i] + ");\n");
      manager.append("\t\treturn this.create(__bean);\n");

      manager.append("\t}\n\n");
    }

   

    // ============================================
    // Constructor Bean from resultset (rs)
    // ============================================
    StringBuffer constructBean = new StringBuffer("new " + tableNameJava
        + "Bean(\n");
    StringBuffer selectFields = new StringBuffer();
    for (int i = 0; i < fields.length; i++) {
      if (i != 0) {
        selectFields.append(" +  \" , \" + ");
        constructBean.append(" ,\n");
      }
      selectFields.append(fields[i].javaName.toUpperCase() + ".sqlName ");
      constructBean.append("        " + generateGetResultSet(fields[i]));
    }
    constructBean.append(")");

    
 // ========================================
    // (8) GetEventManager
    manager.append("\n");
    manager.append("\n");
    
 // XYZ ZZZ
    /*
    manager.append("\tpublic ModificationManager<" + tableNameJava + "> getEventManager() {\n");
    manager.append("\treturn __eventManager;\n");
    manager.append("\t}\n");
    */
    
    
 // ========================================
    // (8) GetTableName
    manager.append("\n");
    manager.append("\n");
    
    manager.append("\tpublic TableName<" + tableNameJava + "> getTableName() {\n");
    manager.append("\treturn _TABLENAME;\n");
    manager.append("\t}\n");
    
    
    
    // (9) findByPrimaryKey bean
    manager.append("\n\n");
    manager.append("  public " + tableNameJava + " findByPrimaryKey(" + tableNameJava + " __bean"
        + ") throws Exception {\n");
    
    manager.append("    if(__bean == null) {\n");
    manager.append("      return null;\n");
    manager.append("    }\n");
    manager.append("    return findByPrimaryKey(");
    String[] split = dao.findParamsCall.toString().replace('_',' ').split(",");
    for (int i = 0; i < split.length; i++) {
      if (i > 0 ) {
        manager.append(" , ");
      }
      manager.append("__bean." + CodeGenUtils.get(split[i].trim())); // TODO s'hauria de fer amb Field
    }
    manager.append(");\n");
    manager.append("  }\n\n");
    
    
    
 // (9) FindByPrimaryKEY Object KEY
    if (dao.pkClass.endsWith("PK") || SQL2Java.isPrimitiveType(dao.pkClass.toLowerCase())) {
      manager.append("\n\n");
      manager.append("  public " + tableNameJava + " findByPrimaryKey(" + dao.pkClass + " pk"
          + ") throws Exception {\n");
      manager.append("    if(pk == null) {\n");
      manager.append("      return null;\n");
      manager.append("    }\n");
      if (dao.pkClass.endsWith("PK")) {
        split = dao.findParamsCall.toString().replace('_',' ').split(",");
        manager.append("    return this.findByPrimaryKey(");
        for (int i = 0; i < split.length; i++) {
          if (i > 0 ) {
            manager.append(" , ");
          }
          manager.append("pk." + split[i].trim()); // TODO s'hauria de fer amb Field
        }
        manager.append(");\n");
        
      } else {
        manager.append("    return this.findByPrimaryKey(pk." + dao.pkClass.toLowerCase() + "Value());\n");
      }
      manager.append("  }\n\n");
    }
    
    
    
    
    // ========================================
    // (9) findByPrimaryKey
    manager.append("\n");
    manager.append("\n");
    manager.append("\tpublic " + tableNameJava + " findByPrimaryKey("
        + dao.findParams + ") throws Exception {\n");
    
    {
      String w;
      if (whereQuery.size() == 1) {
        w = whereQuery.get(0);
      } else {
        StringBuffer ww = new StringBuffer();
        for(String subw : whereQuery) {
          if (ww.length() != 0) {
            ww.append(" ,");
          }
          ww.append(subw);
        }
        w = "Where.AND(" + ww.toString() + ")";
      }
      manager.append("\t\t Where where = " + w.toString() + ";\n");
    }
    manager.append("\t\t List<" + tableNameJava + "> list =" +
        " this.select(where);\n");
    manager.append("\t\t if (list == null || list.size() == 0) {\n");
    manager.append("\t\t   return null;\n");
    manager.append("\t\t } else {\n");
    manager.append("\t\t   return list.get(0);\n");
    manager.append("\t\t }\n");
    manager.append("\t}\n");
    manager.append("\n\n");
   
    
    // (10) DELETE bean
    manager.append("\n\n");
    manager.append("  public void delete(" + tableNameJava + " __bean"
        + ") throws Exception {\n");
    
    manager.append("    if(__bean == null) {\n");
    manager.append("      return;\n");
    manager.append("    }\n");
    manager.append("      this.delete(");
    split = dao.findParamsCall.toString().replace('_',' ').split(",");
    for (int i = 0; i < split.length; i++) {
      if (i > 0 ) {
        manager.append(" , ");
      }
      manager.append("__bean." + CodeGenUtils.get(split[i].trim())); // TODO s'hauria de fer amb Field
    }
    manager.append(");\n");
    manager.append("  }\n\n");
    

    // (10) DELETE Object KEY
    if (dao.pkClass.endsWith("PK") || SQL2Java.isPrimitiveType(dao.pkClass.toLowerCase())) {
      manager.append("\n\n");
      manager.append("  public void delete(" + dao.pkClass + " pk"
          + ") throws Exception {\n");
      manager.append("    if(pk == null) {\n");
      manager.append("      return;\n");
      manager.append("    }\n");
      if (dao.pkClass.endsWith("PK")) {
        split = dao.findParamsCall.toString().replace('_',' ').split(",");
        manager.append("    this.delete(");
        for (int i = 0; i < split.length; i++) {
          if (i > 0 ) {
            manager.append(" , ");
          }
          manager.append("pk." + split[i].trim()); // TODO s'hauria de fer amb Field
        }
        manager.append(");\n");
        
      } else {
        manager.append("    this.delete(pk." + dao.pkClass.toLowerCase() + "Value());\n");
      }
      manager.append("  }\n\n");
    }

    
    // (11) DELETE Object
    manager.append("\n\n");
    manager.append("  public void delete(" + dao.findParams
        + ") throws Exception {\n");
    manager.append("\t  String query = \"DELETE FROM \" + _TABLE_ + \" where \""
        + wherePK + ";\n");
    manager.append("\t  " + ModelGenerator.getInterfaceName(tableNameJava) + " __bean;\n");
    manager.append("\t  long __eventID = 0;\n"); 
    manager.append("\t  if (!__eventManager.emptyListeners()) {\n");
    manager.append("\t    __bean =  findByPrimaryKey(" + dao.findParamsCall + ");\n");
    manager.append("\t    __eventID = __eventManager.preDelete (__bean);\n");
    manager.append("\t  } else {\n");
    manager.append("\t    __bean = null;\n");
    manager.append("\t  };\n\n");
    
    manager.append("\tConnection con = null;\n");
    manager.append("\tPreparedStatement ps = null;\n");
    manager.append("\ttry {\n");
    manager.append("\t\t con=" + databaseManager + ".getConnection();\n");
    manager.append("\t\t ps=con.prepareStatement(query);\n");
    manager.append("\t\t int posQuery=0;\n");
    manager.append(findPrepStat);
    manager.append("\t\t ps.executeUpdate();");
    manager.append("\t  if (__bean != null) {\n");
    manager.append("\t    __eventManager.postDelete(__eventID, __bean);\n");
    manager.append("\t  };\n\n");
    //manager.append("\t\t return __result;\n");
    
    manager.append("\t} catch(Exception __ex__) {\n");
    manager.append("\t  __eventManager.deleteError(__eventID, __bean, __ex__);\n");
    manager.append("\t  throw __ex__;\n");
    
    manager.append("\t} finally {\n");
    manager.append("\t\tif(ps != null) { ps.close(); }\n");
    manager.append("\t\tDataBaseInfo.closeConnection(con);\n");
    manager.append("\t}\n");
 
    manager.append("  }\n\n");
    
    // ===========================================
    // (14) List to Array
    
    manager.append("\tpublic " + tableNameJava + "[] listToArray" +
       "(List<" + tableNameJava + "> list) throws Exception {\n");
    manager.append("\t\treturn list.toArray(new " + tableNameJava + "[list.size()]);\n");
    manager.append("\t};\n\n");
    

    // ===========================================
    // 14.B Construct bean from ResultSet
    manager.append("\tpublic " + tableNameJava + " getRowFromResultSet(ResultSet rs)" +
        " throws Exception {\n");
    manager.append("\t\treturn " + constructBean.toString());
    manager.append(";\n\t}\n\n");
    

    
    // ===========================================
    // (15) getInstance Of TableManager
    manager.append("\tpublic static " + tableNameJava + "Manager getInstance() {\n");
    manager.append("\t  return __manager;\n");
    manager.append("\t};\n\n");


    // ===========================================
    // (16) Full Update
    String setParams = "";
    String valueParams = "";
    String fullSetInsert = "";
    String findByPrimaryKey = "";
    String setPKs = "";
    String valuePKs = "";

    for (int i = 0; i < fields.length; i++) {
      String set;
      type = fields[i].getJavaType().getName();
      if (SQL2Java.isPrimitiveType(type)) {
        set = "\t\t ps.set" + Character.toUpperCase(type.charAt(0))
            + type.substring(1, type.length()) + "(++posQuery,";
      } else {
        set = "\t\t ps.setObject(++posQuery,";
      }
      final String onlyGet = "__bean." + CodeGenUtils.get(fields[i]);
      set = set + onlyGet + ");\n";

      fullSetInsert = fullSetInsert + set;


      if (fields[i].isPrimaryKey()) {
        if (findByPrimaryKey.length() == 0) {
          findByPrimaryKey = onlyGet;
        } else {
          findByPrimaryKey = findByPrimaryKey + "," + onlyGet; 
        }

        if (setPKs.length() == 0) {
          setPKs = set;
          valuePKs = fields[i].sqlName + " = ?";
        } else {
          setPKs = setPKs + set;
          valuePKs = valuePKs + " AND " + fields[i].sqlName + " = ?";
        }
      } else {
        if (setParams.length() == 0) {
          setParams = set;
          valueParams = fields[i].sqlName + " = ?";
        } else {
          setParams = setParams + set;
          valueParams = valueParams + ", " + fields[i].sqlName + " = ?";
        }
      }
    }
    
    // ===========================================
    // 14.B Set bean values in PreparedStatement
    manager.append("\n\n");
    manager.append("\tpublic int setFromPreparedStatement( "
        + "PreparedStatement ps, int posQuery," + tableNameJava + " __bean)"
        + " throws Exception {\n");
    //AQUI FA FALTA ps.set[TYPE](++posQuery, __bean.get[FIELD]());
    manager.append(fullSetInsert);
    manager.append("\t\t return posQuery;\n");
    manager.append("\n\t}\n\n");
    

    // ======= FULL UPDATE

    // ===========================================
    // 9.B Reload bean from DATABASE
    /** TODO ABSTRACT CREATE */
    /*
    manager.append("\n\n");
    manager.append("\npublic " + tableNameJava 
        + " findByPrimaryKey(" + tableNameJava + " __bean) throws Exception {\n");
    manager.append("\t\treturn findByPrimaryKey(" + findByPrimaryKey + ");\n");
    manager.append("\n\t}\n\n");
    */
    
    
      manager.append("\n\n");
      manager.append("\tpublic void update(" + tableNameJava + " __bean)" +
          " throws Exception {\n");
      
      if (setParams.length() == 0) {
        manager.append("\t  throw new Exception(\"Aquest element no pot ser actualitzat"
           + " ja que tots els seus camps s�n Claus Prim�ries.\");\n");
      } else {
        manager.append("\t  long __eventID = -1;\n");
        manager.append("\t  if (!" + tableNameJava 
            + "Manager.__eventManager.emptyListeners()) {\n");
        manager.append("\t    __eventID = " + tableNameJava
            + "Manager.__eventManager.preFullUpdate(__bean);\n");
        manager.append("\t  }\n\n");
    
        manager.append("\t\t final String update=\"update " + tableNameSQL + " set "
            + valueParams + " where " + valuePKs + "\";\n");
        
        manager.append("\tConnection con = null;\n");
        manager.append("\tPreparedStatement ps = null;\n");
        manager.append("\ttry {\n");
        manager.append("\t\t con=" + databaseManager
            + ".getConnection();\n");
        manager
            .append("\t\t ps=con.prepareStatement(update);\n");
        manager.append("\t\t int posQuery=0;\n");
        manager.append(setParams);
        manager.append(setPKs);
        manager.append("\t\t ps.executeUpdate();\n");
        manager.append("\t\t ps.close();\n");
    
        manager.append("\t  if (__eventID != -1) {\n");
        manager.append("\t    " + tableNameJava + "Manager.__eventManager." +
            "postFullUpdate(__eventID, __bean);\n");
        manager.append("\t  };\n\n");
        
        manager.append("\t} catch(Exception __ex__) {\n");
        manager.append("\t    __eventManager.updateError(__eventID, __bean, __ex__);\n");
        manager.append("\t    throw __ex__;\n");

        manager.append("\t} finally {\n");
        manager.append("\t\tif(ps != null) { ps.close(); }\n");
        manager.append("\t\tDataBaseInfo.closeConnection(con);\n");
        manager.append("\t}\n");
      }         
      manager.append("}\n");
 
    
    
    // Final de Clase Manager
    manager.append("\n}");
    
    return new SourceFile(managerFileName + ".java", manager.toString());
    

  }
  
  

  private static String generateGetResultSet(FieldInfo field) {

    Class<?> javaType = field.getJavaType();
    String type = javaType.getName();
    String javaFieldName = field.javaName;
    
    if (javaType.equals(String.class)) {
      return "rs.getString(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    
    if (field.getJavaType().isPrimitive()) {
      return "rs.get" + Character.toUpperCase(type.charAt(0))
        + type.substring(1, type.length()) + "(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    
    
    
    if (javaType.equals(java.sql.Date.class)) {
      return "rs.getDate(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    
    if (javaType.equals(java.sql.Timestamp.class)) {
      return "rs.getTimestamp(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    
    if (javaType.equals(java.sql.Time.class)) {
      return "rs.getTime(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    if (javaType.equals(java.math.BigDecimal.class)) {
      return "rs.getBigDecimal(" + javaFieldName.toUpperCase() + ".sqlName)";
    }
    
    if (javaType.equals(Boolean.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getBooleanFromResultSet" +
          "(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    }
      
    if (javaType.equals(Double.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getDoubleFromResultSet" +
          "(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    }
    if(javaType.equals(Float.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getFloatFromResultSet" +
          "(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    }

    if (javaType.equals(Byte.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getByteFromResultSet" +
          "(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    } 
    if (javaType.equals(Short.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getShortFromResultSet" +
          "(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    } 
    if (javaType.equals(Long.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils.getLongFromResultSet(rs,"
        + javaFieldName.toUpperCase() + ".sqlName)";
    } 
    if (javaType.equals(Integer.class)) {
        return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils."
          + "getIntegerFromResultSet(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    }
    if (javaType.equals(java.math.BigInteger.class)) {
      return GenAppPackages.PKG_BASE + ".common.db.DataBaseUtils." +
          "getBigIntegerFromResultSet(rs," + javaFieldName.toUpperCase() + ".sqlName)";
    } 
    
    return "(" + type + ")rs.getObject(" + javaFieldName.toUpperCase() + ".sqlName)";
  }
  

  private static String selectMax(String tableName, String existAutoIncrementKey) {
    StringBuffer manager = new StringBuffer();
    manager.append("\t\t // Getting max ID\n");
    manager.append("\t\t int _" + existAutoIncrementKey + "_ = __manager.selectNextID("
        + existAutoIncrementKey.toUpperCase() + ");\n");

    return manager.toString();
  }
  
  
  public static SourceFile generateInterfaceGenApp(String className,
    String daoPackageName, String entityPackageName, String fieldsPackageName) {
    StringBuffer interfaceCode = new StringBuffer("");

    /** Cabecera */
    interfaceCode.append("\n");

    //if (packageName != null)
      interfaceCode.append("package " + daoPackageName + ";\n");

    String interfaceCodeFileName = ModelGenerator.getInterfaceName(className);
    interfaceCode.append("public interface I" + interfaceCodeFileName
        + " extends " + entityPackageName + "." + className
        + " , " + fieldsPackageName + "." +  className + "Fields "
        + " {\n");
    interfaceCode.append("}\n");
    return new SourceFile("I" + interfaceCodeFileName + ".java",
        interfaceCode.toString());
  }
  
  

  
  

  
  
}

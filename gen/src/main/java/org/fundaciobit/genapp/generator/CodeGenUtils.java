package org.fundaciobit.genapp.generator;

import java.util.ArrayList;
import java.util.List;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.common.GenAppUtils;

public class CodeGenUtils {
  

  private static TableInfo sqlTableFile = null;
  
  private static TableInfo sqlTableTranslation = null;

  
  public static boolean isFileTable(Project project, String sqltable) {
    if (sqltable == null) {
      return false;
    }
    TableInfo ti = getSqlTableFile(project.getTables()); 
    if (ti == null) {
      // TODO afegir warning
      return false;
    }
    return sqltable.equals(ti.getName());
  }

  public static TableInfo getSqlTableFile(TableInfo[] tables) {
    if (sqlTableFile == null) {
      for (TableInfo table : tables) {
        if (table.isFileMappingEntity()) {
          sqlTableFile = table;
          break;
        }
      }
    }
    return sqlTableFile;
  }
  
  
  

  public static List<FieldInfo> getFileFieldsOfTable(TableInfo[] tables, TableInfo table) {

    TableInfo ti = getSqlTableFile(tables);
    if (ti == null) {
      return null;
    }
    String tableFile = ti.name;
    if (tableFile == null) {
      return null;
    }

    List<FieldInfo> fileFields = new ArrayList<FieldInfo>();

    for (FieldInfo field : table.getFields()) {
      ForeignKey[] keys = field.getForeignKeysByType(ForeignKey.IMPORTED);

      for (ForeignKey fk : keys) {
        if (fk.getTable().equals(tableFile)) {
          fileFields.add(field);
          break;
        }
      }
    }
    return fileFields;
  }
  
  
  public static TableInfo getSqlTableTranslationMap(TableInfo[] tables) {
    if (sqlTableTranslation == null) {
      for (TableInfo table : tables) {
        if (table.isTranslationMapEntity()) {
          sqlTableTranslation = table;
          break;
        }
      }
    }
    return sqlTableTranslation;
  }

  
  public static boolean isTranslationMapTable(Project project, String sqltable) {
    if (sqltable == null) {
      return false;
    }
    TableInfo ti = getSqlTableTranslationMap(project.getTables()); 
    if (ti == null) {
      // TODO afegir warning
      return false;
    }
    return sqltable.equals(ti.getName());
  }
  
  // ----------------------------

  public static boolean isTranslationTable(Project project, String sqltable) {
    if (sqltable == null) {
      return false;
    }
    TableInfo ti = getSqlTableTranslation(project.getTables()); 
    if (ti == null) {
      // TODO afegir warning
      return false;
    }
    return sqltable.equals(ti.getName());
  }
  
  
  public static TableInfo getSqlTableTranslation(TableInfo[] tables) {
    if (sqlTableTranslation == null) {
      for (TableInfo table : tables) {
        if (table.isTranslationEntity()) {
          sqlTableTranslation = table;
          break;
        }
      }
    }
    return sqlTableTranslation;
  }
  
  

  
  public static List<FieldInfo> getTranslationFieldsOfTable(TableInfo[] tables, TableInfo table) {

    TableInfo ti = getSqlTableTranslation(tables);
    
    if (ti == null) {
      return null;
    }
    String tableTranslation = ti.name;
    if (tableTranslation == null) {
      return null;
    }

    List<FieldInfo> translationFields = new ArrayList<FieldInfo>();

    for (FieldInfo field : table.getFields()) {
      ForeignKey[] keys = field.getForeignKeysByType(ForeignKey.IMPORTED);

      for (ForeignKey fk : keys) {
        if (fk.getTable().equals(tableTranslation)) {
          translationFields.add(field);
          break;
        }
      }
    }
    return translationFields;
  }
  
  

  public static TableInfo findTableInfoByTableSQLName(Project project, String sqlTableName) {

    for (TableInfo table : project.tables) {

      if (table.name.equals(sqlTableName)) {
        return table;
      }
    }

    System.err.print("No s'ha trobat la taula " + sqlTableName);
    System.err.flush();

    return null;
  }

  public static FieldInfo findFieldInfoByColumnSQLName(TableInfo tableInfo,
      String sqlColumnName) {

    for (FieldInfo field : tableInfo.getFields()) {

      if (field.getSqlName().equals(sqlColumnName)) {
        return field;
      }
    }
    return null;
  }
  
  public static String generateGetSetBean(FieldInfo field) {
    return generateGetSetBean(field, true);
  }
  
  public static String generateGetSetBean(FieldInfo field, boolean generateSet) { 
    
    final String classeStr = field.getJavaType().getName();
    final String javaName = field.getJavaName();
    String f = generateSet ? "": " final ";
    
    StringBuffer beanCode = new StringBuffer();
    beanCode.append("  private " + f + classeStr + " " + javaName + ";\n\n"); 
    beanCode.append("  public " + classeStr + " " + get(field) + " {\n"); 
    beanCode.append("    return this." + javaName + ";\n"); 
    beanCode.append("  }\n\n"); 
    if (generateSet) {
      beanCode.append("  public void " + set(field) + "(" + classeStr + " " + javaName + ") {\n"); 
      beanCode.append("    this." + javaName + " = " + javaName + ";\n"); 
      beanCode.append("  }\n");
    }
    beanCode.append("\n\n"); 
    return beanCode.toString();
  }
  
  
  
  
public static String generateGetSetBean(String javaName, String javaClass, boolean generateSet) { 
    
    
    String f = generateSet ? "": " final ";
    
    StringBuffer beanCode = new StringBuffer();
    beanCode.append("  private " + f + javaClass + " " + javaName + ";\n\n"); 

    beanCode.append("  public " + javaClass + " " + get(javaName) + " {\n"); 
    beanCode.append("    return this." + javaName + ";\n"); 
    beanCode.append("  }\n\n"); 
    
    if (generateSet) {      
      beanCode.append("  public void " + set(javaName) + "(" + javaClass + " " + javaName + ") {\n"); 
      beanCode.append("    this." + javaName + " = " + javaName + ";\n"); 
      beanCode.append("  }\n");
    }
    beanCode.append("\n\n"); 
    return beanCode.toString();
  }
  

  public static String set(String javaField) {
    return "set" + Character.toUpperCase(javaField.charAt(0))
        + javaField.substring(1);
  }
  
  
  public static String set(FieldInfo field) {
    return "set" + Character.toUpperCase(field.getJavaName().charAt(0))
        + field.getJavaName().substring(1);
  }
  
  /**
   * 
   * @param javaField
   * @return
   * TODO S'ha de fer @ deprecated
   */
  public static String get(String javaField) {
    return CodeGenUtils.getOnlyName(javaField) + "()";
  }

  public static String get(FieldInfo field) {
    Class<?> typeClass = field.getJavaType();
    String methodGetName;
    /* typeClass.equals(java.lang.Boolean.class) || */
    if (typeClass.equals(java.lang.Boolean.TYPE)) {
      methodGetName = "is";
    } else {
      methodGetName = "get";
    }
    return GenAppUtils.getOnlyName(field, methodGetName) + "()";
  }

  public static String getModelName(String tableJavaName) {
    return Character.toLowerCase(tableJavaName.charAt(0))
      + tableJavaName.substring(1);
  }



  public static String getOnlyName(String javaField) {
    return "get" + Character.toUpperCase(javaField.charAt(0))
        + javaField.substring(1);
  }
  
  public static String isOnlyName(String javaField) {
    return "is" + Character.toUpperCase(javaField.charAt(0))
        + javaField.substring(1);
  }
  
  public static List<FieldInfo> getPKs(FieldInfo[] fields) {
    List<FieldInfo> PKs = new ArrayList<FieldInfo>();
    
    for (FieldInfo field : fields) {
      if (field.isPrimaryKey()) {
        PKs.add(field);        
      }
    }
    return PKs;    
  }
  
  
  public static boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    }
    // only got here if we didn't return false
    return true;
  }
  

}

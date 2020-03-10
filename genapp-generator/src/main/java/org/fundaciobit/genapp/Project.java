package org.fundaciobit.genapp;

import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.common.db.DataBaseInfo;


/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class Project implements Serializable {
  
  private static final Logger log = Logger.getLogger(Project.class.getSimpleName());

  /**
   * 
   */
  private static final long serialVersionUID = 2433616258104529953L;

  private String packageName = null;
  
  public String projectName = null;

  public DataBaseInfo dataBaseInfo = null;
  
  @Deprecated
  protected String modulSeguretat; 
  
  protected String prefix; // = "PFI";
  
  public String schema = null;

  public TableInfo[] tables = null;
  
  protected String[] languages = null;

  // SECURITY INFO
  
  Properties security_LDAP_users = null;
  
  protected boolean generateBack = true;
  
  protected boolean generateWS = true;
  
  /*
  DBSecUserFields security_db_users = null;

  DBSecRoleFields security_db_roles = null;

  DBSecUserRolesFields security_db_usersroles = null;
  */

  public String[] getLanguages() {
    return languages;
  }

  public void setLanguages(String[] languages) {
    this.languages = languages;
  }

  public Project() {
    super();
  }
  

  public Project(String packageName, String projectName,
      DataBaseInfo databaseinfo, TableInfo[] tables, String prefix,  String[] languages) {
    super();
    this.packageName = packageName;
    this.projectName = projectName;
    this.dataBaseInfo = databaseinfo;
    this.tables = tables;
    this.languages = languages;
    this.prefix = prefix;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

  
  public DataBaseInfo getDataBaseInfo() {
    return dataBaseInfo;
  }

  public void setDataBaseInfo(DataBaseInfo databaseinfo) {
    this.dataBaseInfo = databaseinfo;
  }

  public TableInfo[] getTables() {
    return tables;
  }

  public void setTables(TableInfo[] tables) {
    this.tables = tables;
  }
  
  public String getModulSeguretat() {
    return modulSeguretat;
  }

  public void setModulSeguretat(String modulSeguretat) {
    this.modulSeguretat = modulSeguretat;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
    
  public Properties getSecurity_LDAP_users() {
    return security_LDAP_users;
  }

  public void setSecurity_LDAP_users(Properties security_LDAP_users) {
    this.security_LDAP_users = security_LDAP_users;
  }
  
  
  
  
/*
  public DBSecUserFields getSecurity_db_users() {
    return security_db_users;
  }

  public void setSecurity_db_users(DBSecUserFields security_db_users) {
    this.security_db_users = security_db_users;
  }

  public DBSecRoleFields getSecurity_db_roles() {
    return security_db_roles;
  }

  public void setSecurity_db_roles(DBSecRoleFields security_db_roles) {
    this.security_db_roles = security_db_roles;
  }

  public DBSecUserRolesFields getSecurity_db_usersroles() {
    return security_db_usersroles;
  }

  public void setSecurity_db_usersroles(
      DBSecUserRolesFields security_db_usersroles) {
    this.security_db_usersroles = security_db_usersroles;
  }
*/
  
  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }
  
  
  
  

  public boolean isGenerateBack() {
    return generateBack;
  }

  public void setGenerateBack(boolean generateBack) {
    this.generateBack = generateBack;
  }

  public boolean isGenerateWS() {
    return generateWS;
  }

  public void setGenerateWS(boolean generateWS) {
    this.generateWS = generateWS;
  }

  public static String getDBManagerName(String projectName) {
    return "ManagerDB" + projectName;
  }
  
  public static String getSecManagerName(String projectName) {
    return "ManagerSec" + projectName;
  }
  
  public FieldInfo searchFieldInfo(String table, String fieldName) {
    
    log.info("--------------");
    log.info("-- searching " + table + "[" + fieldName + "]");
    
    TableInfo[] allTables = this.getTables();
    
    for (int i = 0; i < allTables.length; i++) {
      log.info("  " + i + ".- " + " table[" + allTables[i].getName() + "]");
      if (allTables[i].getName().equals(table)) {
        FieldInfo[] fields = allTables[i].getFields();
        for (int j = 0; j < fields.length; j++) {
          log.info("  " + i + "." + j + ".- "
              + " field[" + fields[j].getSqlName() + "]");
          if (fields[j].getSqlName().equals(fieldName)) {
            return fields[j];
          }
        }
        break;
      }
    }
    System.err.println("Can not find field '" + fieldName + "' in table '" + table + "'");
    return null;
  }
  
  public String createSelect(String table, String fieldName, String where) {
    FieldInfo fi = searchFieldInfo(table, fieldName);
    return createSelect(table, fi, where);
  }
  
  public String createSelect(String table, FieldInfo field, String where) {
    if (field.webFieldInfo.webtype == WebType.PrimaryKey) {
      String[] labels = field.webFieldInfo.getLabel();
      if (labels !=null && labels.length > 0) {
        StringBuffer camps = new StringBuffer();
        for (int i = 0; i < labels.length; i++) {
          if (i != 0) {
            camps.append(',');
          }
          camps.append(labels[i]);
        }
        StringBuffer txt = new StringBuffer("select ");
        txt.append(field.sqlName);
        txt.append(',');
        txt.append(camps.toString());
        txt.append(" from ").append(table).append(' ');
        txt.append(where);
        txt.append(" order by ").append(camps.toString());
        return txt.toString();
      }
    }
    return null;
  }
  
}

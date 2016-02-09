package org.fundaciobit.genapp.generator;

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
 * @version 1.0
 */
public class DaoHibernateGenerator {

  
  

  
  
  
//====================================================================
  // ========================== MANAGER =================================
  // ====================================================================

    
  public static SourceFile generateCodeForManager(String tableNameSQL, String tableNameJava, 
    String sqldaoPackage, BasicPackages packages, String jpaPackage,
    FieldInfo[] fields, Project project, BeanCode beanCode) {
    
    
    
    String databaseManager = Project.getDBManagerName(project.projectName);
    DaoCommonCode dao = beanCode.daoCommonCode;
    String className = tableNameJava + "JPA"; 
    
    
    StringBuffer manager = new StringBuffer("");

    /** Cabecera */
    manager.append("\n");

    if (sqldaoPackage != null)
      manager.append("package " + sqldaoPackage + ";\n");

    String managerFileName = tableNameJava + "Manager";

    manager.append("import java.io.Serializable;");
    manager.append("import java.sql.*;\n");
    manager.append("import java.util.*;\n");
    
    manager.append("import org.hibernate.HibernateException;\n");
    manager.append("import org.hibernate.Session;\n");
    manager.append("import org.hibernate.Transaction;\n");
    
    manager.append("import " + packages.modelPackage + ".*;\n");
    manager.append("import " + packages.entityPackage + ".*;\n");
    manager.append("import " + jpaPackage + ".*;\n");
    manager.append("import " + packages.fieldsPackage + ".*;\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".Field;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".TableName;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".Where;\n");
    //manager.append("import " + GenAppUtils.PKG_QUERY + ".OrderType;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".OrderBy;\n");
    manager.append("import " + GenAppPackages.PKG_QUERY + ".TableManager;\n");
    manager.append("import " + GenAppPackages.PKG_BASE + ".common.db.DataBaseInfo;\n");
    manager.append("import " + GenAppPackages.PKG_BASE + ".common.events.ModificationListener;\n");
    manager.append("import " + GenAppPackages.PKG_BASE + ".common.events.ModificationManager;\n");
    
    manager.append("\n\n");
    manager.append("public class " + managerFileName 
        //+ "\n\t\t extends TableManager<" + tableNameJava + ">"
        + "\n\t\t implements I"  + tableNameJava + "Manager, " + tableNameJava + "Fields {\n\n\n");
    
    manager.append("\n\n");
    manager.append("  private static final long serialVersionUID = "
        + managerFileName.hashCode() + "L;\n\n");
    
    manager.append("\t public static final TableName<" + tableNameJava
        + "> _TABLENAME =  new TableName<" + tableNameJava + ">(_TABLE_);\n");
    manager.append("\n");
    
    // (2) Listener
    String modification = "ModificationManager<" 
      + ModelGenerator.getInterfaceName(tableNameJava)+ ">";
    manager.append("\n\n");
    // TODO llevar static !!!!!!
    manager.append("  static final " + modification + " __eventManager =" +
        " new " + modification + "();\n\n");
    
    // (0) Instance
    manager.append("  private static final " + managerFileName 
        + " __manager = new " + managerFileName + "();\n\n");


    // CONSTRUCTOR
    manager.append("  private " + managerFileName + "() { \n");
    //manager.append("    super(_TABLENAME, ALL_" + tableNameJava.toUpperCase() + "_FIELDS," +  "__eventManager);\n");
    manager.append("  }\n");
    
   
    // (1) Parent Abstract Methods
    
    manager.append("\tpublic Connection getConnection() throws Exception {\n");
    /*
    session.doWork(new org.hibernate.jdbc.Work() {

      @Override
      public void execute(Connection arg0) throws SQLException {
      }
  });
  */

    
    manager.append("\t\t return " + databaseManager + ".getConnection();\n");
    manager.append("\t}\n\n");

    
    // ========================================
    // (8) GetEventManager
    manager.append("\n");
    manager.append("\n");
    
    manager.append("\tpublic ModificationManager<" + tableNameJava + "> getEventManager() {\n");
    manager.append("\treturn __eventManager;\n");
    manager.append("\t}\n");
    
    
 // ========================================
    // (8) GetTableName
    manager.append("\n");
    manager.append("\n");
    
    manager.append("\tpublic TableName<" + tableNameJava + "> getTableName() {\n");
    manager.append("\treturn _TABLENAME;\n");
    manager.append("\t}\n");
    

    
    
    
   // Creates from parameters
    
    
    
    boolean createEq;
    createEq = dao.createParamsNotNull.toString().equalsIgnoreCase(dao.createParams.toString());
    String[] versionParams = { dao.createParams.toString() ,
        createEq? null : dao.createParamsNotNull.toString() };
    String[] versionBeanParams = { dao.createVariableNamesWithoutPK.toString(),
        dao.createVariableNamesNotNullWithoutPK.toString() };
    
    for (int i = 0; i < versionBeanParams.length; i++) {
      if ( versionParams[i] == null) continue;
      manager.append("\tpublic synchronized " + tableNameJava + " create("
          + versionParams[i] + ") throws Exception {\n");
      
      manager.append("\t\t" + className + " __bean =  new " + className + "("
          + versionBeanParams[i] + ");\n");
      manager.append("\t\treturn this.create(__bean);\n");

      manager.append("\t}\n\n");
    }

    // Create from BEAN
    
    manager.append("\tpublic synchronized " + tableNameJava + " create(" + tableNameJava
         + " __item) throws Exception {\n");
    // TODO verificar que __item sigui <Entity>JPA    
    manager.append("  Session session = HibernateUtil.getSessionFactory().openSession();\n");
    manager.append("  Transaction transaction = null;\n");
    manager.append("  try {\n");
    manager.append("    transaction = session.beginTransaction();\n");
    manager.append("    Serializable __id = session.save(__item);\n");
    manager.append("    transaction.commit();\n");
    manager.append("    return (" + tableNameJava + ")session.load(" + className + ".class, __id);\n");
    manager.append("  } catch (HibernateException e) {\n");
    manager.append("    transaction.rollback();\n");
    manager.append("    throw new Exception(e);\n");
    manager.append("  } finally {\n");
    manager.append("    session.close();\n");
    manager.append("  }\n");
    manager.append("\t}\n\n");  
    
    
    
    

    // (10) DELETE
    manager.append("\n\n");
    manager.append(" public void delete(" + dao.findParams
        + ") {\n");
    manager.append("  Session session = HibernateUtil.getSessionFactory().openSession();\n");
    manager.append("  Transaction transaction = null;\n");
    manager.append("  try {\n");
    manager.append("    transaction = session.beginTransaction();\n");
    manager.append("    " + tableNameJava + " __item = (" + tableNameJava + ") session.get(" + tableNameJava + ".class, " + dao.findParamsCall + ");\n");
    manager.append("    session.delete(__item);\n");
    manager.append("    transaction.commit();\n");
    manager.append("  } catch (HibernateException e) {\n");
    manager.append("    transaction.rollback();\n");
    manager.append("    throw new Exception(e);\n");
    manager.append("  } finally {\n");
    manager.append("    session.close();\n");
    manager.append("  }\n");
    //manager.append("  return 1;\n");
    manager.append(" }\n\n\n");
    
    

    // Final de Clase Manager
    manager.append("\n}");
    
    return new SourceFile(managerFileName + ".java", manager.toString());
    
    
  }
  
  
  
}

package org.fundaciobit.genapp.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.common.IGenAppEntity;
import org.fundaciobit.genapp.common.db.DataBaseInfo;
import org.fundaciobit.genapp.common.filesystem.IFile;
import org.fundaciobit.genapp.common.filesystem.IFileManager;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.ITableManager;
import org.fundaciobit.genapp.generator.CodeGenerator.BeanFieldCode;
import org.fundaciobit.genapp.generator.CodeGenerator.DaoCommonCode;
import org.fundaciobit.genapp.generator.DaoJPAGenerator.CodeType;

/**
 * 
 * @author anadal
 *
 */
public class ModelGenerator {

  public static SourceFile generateInterfacesManager(BasicPackages packages, Project project) {

    StringBuffer manager = new StringBuffer();

    String name = "I" + project.projectName + "DaoManagers";

    manager.append("package " + packages.modelPackage + ";\n");
    manager.append("\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("\n");
    manager.append("public interface " + name + " {\n");

    for (int i = 0; i < project.tables.length; i++) {
 
      if (!project.tables[i].generate || project.tables[i].isTranslationMapEntity()) {
        continue;
      }
      
      String managerFileName = project.tables[i].getNameJava() + "Manager";
      manager.append("\tpublic I" + managerFileName + " get" + managerFileName + "();\n");
    }

    manager.append("\n");
    manager.append("}");

    return new SourceFile(name + ".java", manager.toString());
  }

  public static SourceFile generateManagerOfDaoManagers(String modelPackage, String project) {

    StringBuffer manager = new StringBuffer();

    String name = "I" + project + "DaoManagers";

    manager.append("package " + modelPackage + ";\n");
    manager.append("\n");
    manager.append("public class " + project + "DaoManager {\n");
    manager.append("  \n");

    manager.append("  private static " + name + " instance = null;\n");
    manager.append("  \n");

    manager.append("  public static void setDaoManagers(" + name + " managers) {\n");
    manager.append("    instance = managers;\n");
    manager.append("  }\n");
    manager.append("  \n");
    manager.append("  public static " + name + " getDaoManagers() throws Exception {\n");
    manager.append("    if(instance == null) {\n");
    manager
        .append("      throw new Exception(\"Ha de inicialitzar el sistema de Managers cridant \"\n");
    manager.append("          + \" al mètode " + project
        + "DaoManager.setDaoManagers(...)\");\n");
    manager.append("    }\n");
    manager.append("    return instance;\n");
    manager.append("  }\n");
    manager.append("  \n");
    manager.append("}\n");

    return new SourceFile(project + "DaoManager.java", manager.toString(), true);

  }

  public static SourceFile generateInterfacesManager(TableInfo tableInfo,
      BasicPackages packages, FieldInfo[] fields, BeanCode beanCode) {
    
    if (!tableInfo.isGenerate() || tableInfo.isTranslationMapEntity()) {
      return null;
    }
    
    String tableNameJava = tableInfo.getNameJava();

    String managerFileName = "I" + tableNameJava + "Manager";

    StringBuffer manager = new StringBuffer();

    manager.append("package " + packages.daoPackage + ";\n");
    manager.append("\n");
    manager.append("import " + packages.entityPackage + ".*;\n");
    
    manager.append("import " + I18NException.class.getName()+ ";\n");
    // manager.append("import " + GenAppUtils.PKG_QUERY + ".OrderType;\n");
    // manager.append("import " + GenAppUtils.PKG_QUERY + ".OrderBy;\n");
    /*
     * manager.append("import " + GenAppUtils.PKG_QUERY + ".TableManager;\n");
     * manager.append("import " + GenAppUtils.PKG_BASE +
     * ".common.db.DataBaseInfo;\n"); manager.append("import " +
     * GenAppUtils.PKG_BASE + ".common.events.ModificationListener;\n");
     * manager.append("import " + GenAppUtils.PKG_BASE +
     * ".common.events.ModificationManager;\n");
     */
    DaoCommonCode dao = beanCode.daoCommonCode;
    manager.append("\n\n");
    
    String pkClass = dao.pkClass;
    
    if (pkClass.equals("BigDecimal") || pkClass.equals("BigInteger")) {
      pkClass = "java.math." + pkClass;
    }
    
    manager.append("public interface " + managerFileName + " extends ");

    if (tableInfo.isFileMappingEntity()) {
      manager.append(IFileManager.class.getName());
    } else {
      manager.append(ITableManager.class.getName());
    }
    
    manager.append("<" + tableNameJava + ", " + pkClass + "> {\n\n\n");

    // -------------
    // --- CREATE

    {
      boolean createEq;
      createEq = dao.createParamsNotNull.toString().equalsIgnoreCase(
          dao.createParams.toString());
      String[] versionParams = { dao.createParams.toString(),
          createEq ? null : dao.createParamsNotNull.toString() };
      String[] versionBeanParams = { dao.createVariableNames.toString(),
          dao.createVariableNamesNotNull.toString() };

      for (int i = 0; i < versionBeanParams.length; i++) {
        if (versionParams[i] == null)
          continue;
        manager.append("\tpublic " + tableNameJava + " create(" + versionParams[i]
            + ") throws I18NException;\n\n");
      }
    }

    // -------------
    // --- FINDBYPRIMARYKEY
    manager.append("\tpublic " + tableNameJava + " findByPrimaryKey(" + dao.findParams
        + ");\n");

    // -------------
    // --- DELETE
    manager.append("\n");
    manager.append("\tpublic void delete(" + dao.findParams + ");\n");

    manager.append("\n}\n");

    return new SourceFile(managerFileName + ".java", manager.toString());
  }

  public static SourceFile generateTableFields(String tableNameSQL, BasicPackages packages,
      String javaName, FieldInfo[] fields, BeanCode beanCode) {

    StringBuffer indexes = new StringBuffer();
    Class<?> typeClass;
    String name;
    for (int r = 0; r < fields.length; r++) {
      FieldInfo field = fields[r];
      typeClass = field.getJavaType();
      name = field.javaName;
      // Indices de Atributos
      String fieldType = typeClass.isPrimitive() ? SQL2Java.primitiveType2javaClassName(
          typeClass).getName() : typeClass.getName();
      String fieldClass = getFieldType(fieldType);
      indexes.append("\t public static final " + fieldClass + " "
          // Field<" + fieldType + "> "
          + name.toUpperCase() + " = new " + fieldClass + "(_TABLE_MODEL, \"" + name + "\", \""
          + field.sqlName + "\");" + (field.isPrimaryKey() ? "  // PK" : "") + "\n");

    }

    StringBuffer interfaceCode = new StringBuffer("");

    /** Cabecera */
    interfaceCode.append("\n");

    interfaceCode.append("package " + packages.fieldsPackage + ";\n");

    String codeFileName = javaName + "Fields";

    // interfaceCode.append("import java.sql.*;\n");

    // interfaceCode.append("import " + GenAppUtils.PKG_BASE + ".common.*;\n");
    interfaceCode.append("import " + GenAppPackages.PKG_BASE + ".common.query.*;\n");
    // interfaceCode.append("\n\n");
    interfaceCode.append("public interface " + codeFileName
        + " extends java.io.Serializable {");
    interfaceCode.append("\n\n");
    interfaceCode
    .append("  public static final String _TABLE_SQL = \"" + tableNameSQL + "\";\n");
    interfaceCode.append("\n\n");
    
    interfaceCode.append("  public static final String _TABLE_MODEL = \""
        + CodeGenUtils.getModelName(javaName) + "\";\n");
    interfaceCode.append("\n\n");
    
    interfaceCode.append("  public static final String _TABLE_TRANSLATION = "
        + "_TABLE_MODEL + \".\" + _TABLE_MODEL;\n");
    interfaceCode.append("\n\n");

    interfaceCode.append(indexes.toString());
    interfaceCode.append("\n\n");



/**  ZZZZZZZZZZZZZZZZZ
    interfaceCode.append("\t public static final TableName<" + className
        + "> _TABLENAME =  new TableName<" + className + ">(_TABLE_);\n");
    interfaceCode.append("\n\n");
    */
    interfaceCode.append("  public static final Field<?>[] ALL_" + javaName.toUpperCase()
        + "_FIELDS = {\n");
    for (int i = 0; i < fields.length; i++) {
      if (i != 0) {
        interfaceCode.append(",\n");
      }
      interfaceCode.append("    ").append(fields[i].javaName.toUpperCase());
    }
    interfaceCode.append("\n  };\n");

    // (3) Llista de PK's
    interfaceCode.append("\n\n");
    interfaceCode.append("  public static final Field<?>[] PRIMARYKEY_FIELDS = {\n");
    
    boolean init = false;
    for (FieldInfo pkName : beanCode.pkFields) {
      if (init) {
        interfaceCode.append(", ");
      }
      interfaceCode.append(pkName.javaName.toUpperCase());
      init = true;
    }
    interfaceCode.append("\n  };\n");

    // Final de classe
    interfaceCode.append("}\n");

    return new SourceFile(codeFileName + ".java", interfaceCode.toString());
  }

  public static SourceFile generateInterfaceBase(Project project, String packageName, TableInfo table,
      BeanCode beanGenCode) throws Exception {

    String className = table.getNameJava();
    FieldInfo[] fields = table.fields;

    // Imports i Exports de la taula
    Set<String> imports = new HashSet<String>();

    String codeImportsExports = DaoJPAGenerator.generateImportsExports(project, table, fields, imports, null, CodeType.INTERFACE);

    Map<FieldInfo, BeanFieldCode> codeByField = beanGenCode.codeByField;
    /** Metodes per Interficies */
    StringBuffer interfaceFunctions = new StringBuffer();

    for (int r = 0; r < fields.length; r++) {
      
      if (table.isTranslationMapEntity() && fields[r].isPrimaryKey()) {
        continue;
      }

      // log.info("   ++++ Field " + fields[r].javaName);

      BeanFieldCode bfc = codeByField.get(fields[r]);

      interfaceFunctions.append(bfc.prototipusGet + ";\n");
      interfaceFunctions.append(bfc.prototipusSet + ";\n\n");
    }

    /*
     * String fileInterface = ""; if (table.isFileMappingEntity() ) {
     * fileInterface = ",\n     " + GenAppPackages.PKG_BASE + ".common.filesystem.IFile "; }
     * String idiomaInterface = ""; if (table.isLanguageMappingEntity()) {
     * idiomaInterface = ",\n   " + GenAppPackages.PKG_BASE + ".common.language.ILanguage "; }
     */

    StringBuffer interfaceCode = new StringBuffer("");

    /** Cabecera */
    interfaceCode.append("package " + packageName + ";\n");

    // Imports
    for (String imp : imports) {
      interfaceCode.append("import " +  imp + ";\n");
    }
    
    // TODO Message Generated Header
    interfaceCode.append("\n");
    interfaceCode.append("public interface " + className + " extends ");
    if ( table.isFileMappingEntity()) {
      interfaceCode.append(IFile.class.getName());
    } else {
      interfaceCode.append(IGenAppEntity.class.getName());
    }
    interfaceCode.append(" {\n\n");
    // Afegim indexos d'Atributs
    interfaceCode.append(interfaceFunctions);
    
    List<FieldInfo> fileFields= CodeGenUtils.getFileFieldsOfTable(project.tables, table);
    
    
    String fileTableSuffix = "";
    
    TableInfo fileTable = CodeGenUtils.getSqlTableFile(project.tables);
    if (fileTable != null) {
    
      for (FieldInfo field : fileFields) {
        String javaNameField = field.getJavaName();
        if (!javaNameField.endsWith("ID")) {                          
          throw new Exception("El camp " + field.getJavaName() + " de la taula "
                +  table.getNameJava() 
                + " fa referencia a la taula externa [FITXERS BBDD]" +
                    " però aquest camp java no acaba amb ID");
        }
        
        String fileTableName = fileTable.getNameJava() + fileTableSuffix;

        // Mètode get
        javaNameField= javaNameField.substring(0, javaNameField.length() - 2);
        interfaceCode.append("  // Fitxer\n");
        interfaceCode.append("  public <F extends " + fileTableName + "> F " + CodeGenUtils.get(javaNameField) +";\n");

      }
      
      if (fileTable != null && className.equals(fileTable.getNameJava())) {
        interfaceCode.append("  // DataHandler\n");
        interfaceCode.append("  javax.activation.DataHandler getData();\n\n");
        
        interfaceCode.append("  // Encrypted FileID\n");
        interfaceCode.append("  public String getEncryptedFileID();\n\n");
        interfaceCode.append("  public void setEncryptedFileID(String encryptedFileID);\n\n");
      }
    }

    interfaceCode.append("\n\n  // ======================================\n\n");
    
    interfaceCode.append(codeImportsExports);
    
    
    interfaceCode.append("}\n");
    return new SourceFile(className + ".java", interfaceCode.toString());
  }

  /**
   * 
   * @param fieldType
   * @return
   */
  public static String getFieldType(String fieldType) {

    int i = fieldType.lastIndexOf('.');
    return fieldType.substring(i + 1) + "Field";

  }

  public static SourceFile generatePKClass(String tableNameJava, BasicPackages packages,
      BeanCode beanCode) {

    String className = tableNameJava + "PK";

    StringBuffer classPK = new StringBuffer();

    classPK.append("package " + packages.daoPackage + ";\n\n");

    classPK.append("import java.io.Serializable;\n\n");

    DaoCommonCode dao = beanCode.daoCommonCode;

    classPK.append("public final class " + className + " implements Serializable  {\n\n");

    // TODO
    classPK.append("  private static final long serialVersionUID = 666" + className.hashCode() + "L;\n");

    // Atributs

    String[] fields = dao.findParams.toString().split(",");
    for (int i = 0; i < fields.length; i++) {
      int index = fields[i].indexOf('_');
      String tmp = fields[i];
      fields[i] = tmp.substring(0,index) + tmp.substring(index + 1, tmp.length() -1);
    }
    
    
    
    
   StringBuffer gets = new StringBuffer();
    
    for (int i = 0; i < fields.length; i++) {
      classPK.append("  public final " + fields[i] + ";\n");
            
      String[] info = fields[i].trim().split("\\s+");
      
      gets.append("  public " + info[0] + " " + CodeGenUtils.get(info[1]) + " {\n"); 
      gets.append("    return this." + info[1] + ";\n"); 
      gets.append("  }\n\n"); 
    }
    

    classPK.append("\n");

    // Constructor
    classPK.append("  public " + className + "(");
    //+ dao.findParams.toString().replace('_', ' ')
        for (int i = 0; i < fields.length; i++) {
          if (i != 0) {
            classPK.append(", ");
          }
          classPK.append(fields[i]);
        }
        
        
        classPK.append(") {\n");

    String[] params = dao.findParamsCall.toString().split(",");
    
    for (int i = 0; i < params.length; i++) {
      int index = params[i].indexOf('_');
      String tmp = params[i];
      params[i] = tmp.substring(0,index) + tmp.substring(index + 1, tmp.length() -1);
    }
    
    
    for (int i = 0; i < params.length; i++) {
      classPK.append("    this." + params[i].trim().replace(' ' , '_') + " = " + params[i].trim().replace(' ' , '_') + ";\n");
    }

    classPK.append("  }\n\n");
    
   
    classPK.append(gets.toString() );

    classPK.append("}\n");

    return new SourceFile(className + ".java", classPK.toString());

  }

  /**
   * 
   * @param project
   * @param packageName
   * @return
   */
  public static SourceFile generateManagerBD(Project project, String packageName) {

    String manager = Project.getDBManagerName(project.projectName);
    DataBaseInfo dbinfo = project.getDataBaseInfo();

    StringBuffer dbman = new StringBuffer("");

    dbman.append("package " + packageName + ";\n\n");
    dbman.append("import " + GenAppPackages.PKG_BASE + ".common.*;\n");
    dbman.append("import " + GenAppPackages.PKG_BASE + ".common.db.*;\n");
    dbman.append("import java.io.File;\n");
    dbman.append("import java.io.FileNotFoundException;\n");
    dbman.append("import java.sql.Connection;\n\n");

    dbman.append("/**\n");
    dbman.append(" * DO NOT MODIFY THIS FILE (IT IS AUTOGENERATED).\n");

    dbman.append(" * Change values of this class using " + project.projectName
        + "Initializations.java class.\n");

    dbman.append(" *\n");
    dbman.append(" * @author anadal\n");
    dbman.append(" *\n");
    dbman.append(" */\n");

    dbman.append("public final class " + manager + " implements ConnectionProvider {\n\n");

    dbman.append("  private static DataBaseInfo DataBaseInfo = null;\n\n");

    dbman.append("  \n");
    dbman.append("  private static " + manager + " instance = new " + manager + "();\n");
    dbman.append("  \n");
    dbman.append("  private " + manager + "() {\n");
    dbman.append("  }\n");
    dbman.append("  \n");
    dbman.append("  public static " + manager + " getInstance() {\n");
    dbman.append("    return instance;\n");
    dbman.append("  }\n");
    dbman.append("  \n");
    dbman.append("  public Connection getDBConnection() throws Exception {\n");
    dbman.append("   return " + manager + ".getConnection();\n");
    dbman.append("  }\n");
    dbman.append("  \n");
    dbman.append("  public static Connection getConnection() throws Exception {\n");
    dbman.append("\n");
    dbman.append("    if (DataBaseInfo == null) {\n");
    dbman.append("       throw new Exception(\"" + "You must define database info using "
        + manager + ".setDataBase(DataBaseInfo DataBaseInfo) method.\");\n");
    dbman.append("    }\n");
    dbman.append("    return DataBaseInfo.getConnection();\n");
    dbman.append("  }\n");
    dbman.append("\n");
    dbman.append("  public static void setDataBase(DataBaseInfo newDataBaseInfo) {\n");
    dbman.append("    if (newDataBaseInfo != null) {\n");
    dbman.append("     DataBaseInfo = newDataBaseInfo;\n");
    dbman.append("    }\n");
    dbman.append("  }\n");
    dbman.append("\n\n");
    dbman.append("  public static DataBaseInfo getDataBase() {\n");
    dbman.append("    return DataBaseInfo;\n");
    dbman.append("  }\n");
    dbman.append("\n\n");

    dbman.append("\n");
    dbman.append("  public static DataBaseInfo getDataBaseInfoFromFile(File f)"
        + " throws FileNotFoundException {\n");
    dbman.append("    return (DataBaseInfo)RebUtils.getObjectFromFile(f);\n");
    dbman.append("  }\n");

    dbman.append("  public static DataBaseInfo getDefaultDataBaseInfo() {\n");
    dbman.append("    final String url = \"" + dbinfo.getUrl() + "\";\n");
    dbman.append("    final String driver = \"" + dbinfo.getDriver() + "\";\n");
    dbman.append("    final String usr = \"" + dbinfo.getUsr() + "\";\n");
    dbman.append("    final String pwd = \"" + dbinfo.getPwd() + "\";\n");
    dbman.append("    return new DataBaseInfo(driver, url, usr, pwd);\n");
    dbman.append("  }\n\n");

    dbman.append("}\n");

    return new SourceFile(manager + ".java", dbman.toString());

  }

  // ==============================================================
  // ========================== BEAN ==============================
  // ==============================================================

  public static SourceFile generateBeanCode(Project project, TableInfo table,
      BasicPackages packages,  BeanCode beanGenCode) throws Exception {
    
    if (table.isTranslationMapEntity()) {
      return null;
    }

    String tableNameJava = table.nameJava;
    FieldInfo[] fields = table.fields;

    // Imports i Exports de la taula
    Set<String> imports = new HashSet<String>();
    

    
    String codeImportsExports = DaoJPAGenerator.generateImportsExports(project, table, fields, imports, null, CodeType.BEAN);
    
    

    /** Metodes per Bean */
    StringBuffer setgetBean = new StringBuffer();
    String className = tableNameJava;
    String interfaceBean = getInterfaceName(className);

    Map<FieldInfo, BeanFieldCode> codeByField = beanGenCode.codeByField;

    // METODES GET-SET PER BEAN
    for (int r = 0; r < fields.length; r++) {

      BeanFieldCode bfc = codeByField.get(fields[r]);

      setgetBean.append(bfc.prototipusGet + bfc.contentGet);

      setgetBean.append(bfc.prototipusSet + bfc.contentSet);
    }

    StringBuffer beanCode = new StringBuffer("");

    /** Cabecera */
    beanCode.append("\n");

    beanCode.append("package " + packages.beanPackage + ";\n");

    beanCode.append("\n");
    beanCode.append("import " + packages.entityPackage + "." + className + ";\n");
    

    // Imports
    for (String imp : imports) {
      beanCode.append("import " +  imp + ";\n");
    }

    String codeBeanFileName = className + "Bean";

    beanCode.append("\n\n");
    beanCode.append("public class " + codeBeanFileName + " implements " + interfaceBean
        + " {\n\n");
    beanCode.append("\n\n");
    beanCode.append("private static final long serialVersionUID = "
        + codeBeanFileName.hashCode() + "L;\n\n");
    // Anyadimos lista de Atributos
    beanCode.append(beanGenCode.attributesPK);
    beanCode.append(beanGenCode.attributesNonPK);
    beanCode.append("\n\n");
    


    beanCode.append(CodeGenerator.generateBeanConstructors(table, codeBeanFileName,
        beanGenCode.constructors));
    
    List<FieldInfo> fileFields= CodeGenUtils.getFileFieldsOfTable(project.tables, table);

    TableInfo fileTable = CodeGenUtils.getSqlTableFile(project.tables);
    
    
    beanCode.append(generateCopyBeanCode(table,
        fields, codeBeanFileName, "toBean", fileFields, 
        fileTable, "Bean", true));
    

    // Anyadimos metodos GET-SET
    beanCode.append(setgetBean.toString());
    beanCode.append("\n\n");
    
    beanCode.append("  // ======================================\n\n");
    
    beanCode.append(codeImportsExports);
    

    
    beanCode.append(generateCopyBeanCode(table,
        fields, codeBeanFileName, "toBean", fileFields, 
        fileTable, "Bean", false));
    
    
    if (table.isFileMappingEntity()) {
      // Generate DataHandler si és Fitxer
      beanCode.append("\n");      
      beanCode.append(ModelGenerator.generateDataHandler(""));
    } else {
      // Genera el Bean de FITXER   
      
      String fileTableSuffix = "Bean";
      
      
      if (fileTable != null) {
      
        for (FieldInfo field : fileFields) {
          
          
          
          String javaNameField = field.getJavaName();
          if (!javaNameField.endsWith("ID")) {                          
            throw new Exception("El camp " + field.getJavaName() + " de la taula "
                  +  table.getNameJava() 
                  + " fa referencia a la taula externa [FITXERS BBDD]" +
                      " però aquest camp java no acaba amb ID");
          }
          
          String fileTableName = fileTable.getNameJava() + fileTableSuffix;
          javaNameField= javaNameField.substring(0, javaNameField.length() - 2);
          
          // Propietat
          beanCode.append("  protected " + fileTableName + " " + javaNameField + ";\n");
          
          // Mètode get
          
          beanCode.append("  public " + fileTableName + " " + CodeGenUtils.get(javaNameField) +" {\n");
          beanCode.append("    return " + javaNameField + ";\n");
          beanCode.append("  }\n");
            
          // Mètode set
          beanCode.append("  public void " + CodeGenUtils.set(javaNameField) +"(" + fileTableName + " __field) {\n");
          beanCode.append("    this. " + javaNameField + " = __field;\n");
          beanCode.append("  }\n");

        }
      }
      
      
    }
    
    
    
    
    
    beanCode.append("\n\n");

    beanCode.append("}\n");

    return new SourceFile(codeBeanFileName + ".java", beanCode.toString());
  }
  
  

  public static String generateDataHandler(String annotation) {
    StringBuffer beanCode = new StringBuffer();
    beanCode.append(annotation + "\n");
    beanCode.append("  javax.activation.DataHandler data;\n");
    beanCode.append("\n");
    beanCode.append("  public javax.activation.DataHandler getData() {\n");
    beanCode.append("    return data;\n");
    beanCode.append("  }\n");
    beanCode.append("\n");
    beanCode.append("  public void setData(javax.activation.DataHandler data) {\n");
    beanCode.append("    this.data = data;\n");
    beanCode.append("  }\n");
    beanCode.append("\n");
    beanCode.append(annotation  + "\n");
    beanCode.append("  String encryptedFileID;\n");
    beanCode.append("\n");
    beanCode.append("  public String getEncryptedFileID() {\n");
    beanCode.append("    return encryptedFileID;\n");
    beanCode.append("  }\n");
    beanCode.append("\n");
    beanCode.append("  public void setEncryptedFileID(String encryptedFileID) {\n");
    beanCode.append("    this.encryptedFileID = encryptedFileID;\n");
    beanCode.append("  }\n");
    beanCode.append("\n");
    return beanCode.toString();
  }

  public static String getInterfaceName(String tableName) {
    return tableName;
  }
  
  public static String generateCopyBeanCode(TableInfo table,
      FieldInfo[] fields, String codeBeanFileName, String methodName,
     List<FieldInfo> fileFields, TableInfo fileTable, String fileSuffix,
     boolean isConstructor) throws Exception {
    
    

    StringBuffer beanCode = new StringBuffer();
    
    if (table.isTranslationMapEntity()) {
      if (isConstructor) {
        beanCode.append("  public " + codeBeanFileName + "(String _valor_) {\n");
        beanCode.append("    this.valor = _valor_;\n");
        beanCode.append("  }\n\n");
      }
      
      return beanCode.toString();
    }
    
    
    String tableNameJava = table.getNameJava();
    
    
    if (isConstructor) {
      beanCode.append("  public " + codeBeanFileName + "(" + tableNameJava + " __bean) {\n");
    } else {
      beanCode.append("  public static " + codeBeanFileName + " " + methodName + "(" + tableNameJava + " __bean) {\n");
      beanCode.append("    if (__bean == null) { return null;}\n");
      beanCode.append("    " + codeBeanFileName + " __tmp = new " + codeBeanFileName + "();\n");
    }
    
    String var = isConstructor? "this" : "__tmp";
    
    
    for (int r = 0; r < fields.length; r++) {
      beanCode.append("    " + var + "." + CodeGenUtils.set(fields[r].javaName) + "(" 
          + "__bean." + CodeGenUtils.get(fields[r]) + ");\n");
    }
    if (fileTable != null && tableNameJava.equals(fileTable.getNameJava())) {
      beanCode.append("    // DataHandler\n");
      beanCode.append("    " + var + ".setData(__bean.getData());\n");
      
      beanCode.append("    // EncryptedFileID\n");
      beanCode.append("    " + var + ".setEncryptedFileID(__bean.getEncryptedFileID());\n");
      
    } else {
      if (fileFields != null) {
        for (FieldInfo field : fileFields) {
          String javaNameField = field.getJavaName();
          if (!javaNameField.endsWith("ID")) {                          
            throw new Exception("El camp " + field.getJavaName() + " de la taula "
                  +  tableNameJava 
                  + " fa referencia a la taula externa [FITXERS BBDD]" +
                      " però aquest camp java no acaba amb ID");
          }
          javaNameField= javaNameField.substring(0, javaNameField.length() - 2);
          beanCode.append("    // Fitxer\n");
          String fileClass = fileTable.getNameJava() + fileSuffix;
          beanCode.append("    " + var + "." + CodeGenUtils.set(javaNameField) + "(");
          beanCode.append(fileClass +"." + methodName + "(");
          beanCode.append("__bean." + CodeGenUtils.get(javaNameField) + "));\n");
          
        }
      }
    }
    
    if (!isConstructor) {
      beanCode.append("\t\treturn __tmp;\n");
    }
    beanCode.append("\t}\n\n");
    
    return beanCode.toString();
  }

}

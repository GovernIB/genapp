package org.fundaciobit.genapp.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.query.TableName;
import org.fundaciobit.genapp.generator.CodeGenerator.BeanFieldCode;
import org.fundaciobit.genapp.generator.CodeGenerator.DaoCommonCode;


/**
 * Title:      GenApp 2015 (Rapit Entity Bean 2010)
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class DaoJPAGenerator {
  

  public static SourceFile[] generateHibernateUtils(String hibernatePackage,
      String hibernateName, String hibernateFileName) {
    
    String code = 
    "package " + hibernatePackage + ";\n"
    + "\n"
    + "import org.hibernate.SessionFactory;\n"
    + "import org.hibernate.cfg.AnnotationConfiguration;\n"
    + "\n"
    + "public class " + hibernateName +" {\n"    
    + "  \n"
    + "    private static final SessionFactory sessionFactory = buildSessionFactory();\n"
    + "    \n"
    + "    private static SessionFactory buildSessionFactory() {\n"
    + "        try {\n"
    + "            return new AnnotationConfiguration().configure().buildSessionFactory();\n"
    + "        } catch (Throwable ex) {\n"
    + "            System.err.println(\"Initial SessionFactory creation failed.\" + ex);\n"
    + "            throw new ExceptionInInitializerError(ex);\n"
    + "        }\n"
    + "   }\n"
    + "   \n"
    + "    public static SessionFactory getSessionFactory() {\n"
    + "        return sessionFactory;\n"
    + "    }\n"
    + "\n"
    + "    public static void shutdown() {\n"
    + "      // Close caches and connection pools\n"
    + "      getSessionFactory().close();\n"
    + "    }\n"
    + "    \n"
    + "}\n";
    
    
    String code2 =      
    "package " + hibernatePackage + ";\n"
    + "\n"
    + "import " + GenAppPackages.PKG_BASE + ".common.crypt.FileIDEncrypter;\n"
    + "\n"
    + "public class " + hibernateFileName + " {\n\n"  
    + "    private static FileIDEncrypter encrypter = null;\n"
    + "\n"
    + "    static {\n"
    + "      try {\n"
    + "        encrypter = new FileIDEncrypter(\"keyValuekeyValue\".getBytes(),\n"
    + "            FileIDEncrypter.ALGORITHM_AES);\n"
    + "      } catch (Exception e) {\n"
    + "        System.err.println(\"Error instanciant File Encrypter: \" + e.getMessage());\n"
    + "        e.printStackTrace(System.err);\n"
    + "      }\n"
    + "    }\n"
    + "\n"
    + "    public static FileIDEncrypter getEncrypter() {\n"
    + "      return encrypter;\n"
    + "    }\n"
    + "\n"
    + "    public static void setEncrypter(FileIDEncrypter encrypter) {\n"
    + "      HibernateFileUtil.encrypter = encrypter;\n"
    + "    }\n"
    + "\n"
    + "    public static String encryptFileID(long fileID) {\n"
    + "      try {\n"
    + "        return encrypter.encrypt(String.valueOf(fileID));\n"
    + "      } catch(Exception e) {\n"
    + "        System.err.print(\"Error executant File Encrypter: \" + e.getMessage());\n"
    + "        e.printStackTrace(System.err);\n"
    + "        return String.valueOf(fileID);\n"
    + "      }\n"
    + "    }\n"
    + "\n"
    + "    public static long decryptFileID(String encryptedData)  {\n"
    + "      try {\n"
    + "        return Long.parseLong(encrypter.decrypt(encryptedData));\n"
    + "      } catch(Exception e) {\n"
    + "        System.err.print(\"Error executant File Decrypter: \" + e.getMessage());\n"
    + "        e.printStackTrace(System.err);\n"
    + "        return 0;\n"
    + "      }\n"
    + "    }\n"
    + "\n"
    + "}\n";
    
    return new SourceFile[] {
        new SourceFile(hibernateName + ".java", code),
        new SourceFile(hibernateFileName + ".java", code2),
    };
  }
  
  
  
  
  
  public static SourceFile generateJPABeanCode(Project project, TableInfo table,
     String entityPackage, String jpaPackage, String hibernatePackage,
     BeanCode beanGenCode) throws Exception {

    String tableNameJava = table.nameJava;
    FieldInfo[] fields = table.fields;
    String codeBeanFileName = tableNameJava + "JPA";
    
    StringBuffer beanCode = new StringBuffer("");
   
    Set<String> imports = new HashSet<String>();
    if (table.getDescripcio() != null) {
      beanCode.append("/* " + table.getDescripcio() + " */\n");
    }
    
    String sequenceJPAName=project.projectName.toUpperCase() + "_SEQ";
      
      
      if (table.isTranslationMapEntity()) {
        beanCode.append("@javax.persistence.Embeddable\n");
        
      } else {
        imports.add("javax.persistence.Entity");        
        beanCode.append("@Entity\n");
      }
      
      beanCode.append("@Table(name = \"" + table.name + "\" ");
      
      if (!table.isTranslationMapEntity()) {
        MultipleUnique[] uniques = table.getMultipleUniques();
        
        if (uniques != null && uniques.length != 0) {
          imports.add("javax.persistence.UniqueConstraint");
          beanCode.append(" , uniqueConstraints = {\n");
          for (int i = 0; i < uniques.length; i++) {
            beanCode.append("           ");
            if (i != 0) {
              beanCode.append(",");     
            }
            String[] columns = uniques[i].getSqlColumns();
            
            beanCode.append(" @UniqueConstraint("
                // TODO Descomentar quan es passi a JPA 2.0
                //+ "name=\"" + uniques[i].getName()+ "\","
                + " columnNames={");
            
            for (int j = 0; j < columns.length; j++) {
              if (j!= 0) {
                beanCode.append(",");
              }
              beanCode.append("\"" + columns[j] + "\"");            
            }		
            beanCode.append("})");
          }
              
              
          beanCode.append(" } ");
        }
      }
        
      beanCode.append(")\n");

      if (!table.isTranslationMapEntity()) {
        String sequenceSQLName=project.getPrefix() + "_" + project.projectName.toLowerCase() + "_seq";
  
        imports.add("javax.persistence.SequenceGenerator");
        
        beanCode.append("@SequenceGenerator(name=\"" + sequenceJPAName + "\", sequenceName=\"" + sequenceSQLName + "\", allocationSize=1)\n"); 
      }
      
      beanCode.append("@javax.xml.bind.annotation.XmlRootElement\n");
      beanCode.append("public class " + codeBeanFileName
          + " implements " + tableNameJava + " {\n\n");
      beanCode.append("\n\n");
      beanCode.append("private static final long serialVersionUID = "
          + codeBeanFileName.hashCode() + "L;\n\n");
      //  Llista d'Atributs
     
      for (int r = 0; r < fields.length; r++) {
        FieldInfo field = fields[r];
        
        if (table.isTranslationMapEntity() && field.isPrimaryKey()) {
          continue;
        }
        
        Class<?> typeClass = field.getJavaType();
        String type = typeClass.getName();
        String name = field.javaName;
        

        
        if (field.getDescripcio() != null) {
          beanCode.append("  /** " + field.getDescripcio() + " */\n");
        }

        if (field.isTransientField()) {
          imports.add("javax.persistence.Transient");
          beanCode.append("\t" +"@Transient\n");
        } else {

          // Atributos
          if (field.isPrimaryKey()) {
            imports.add("javax.persistence.Id");
            beanCode.append("\t" +"@Id\n");         
          }
          if (field.isAutoIncrement) {
            // AUTONUMERIC
            //imports.add("static javax.persistence.GenerationType.IDENTITY");
            //beanCode.append("\t" +"@GeneratedValue(strategy = IDENTITY)\n");
            imports.add("javax.persistence.GeneratedValue");
            imports.add("javax.persistence.GenerationType");
            beanCode.append("\t" + "@GeneratedValue(strategy = GenerationType.SEQUENCE," +
            		" generator=\"" + sequenceJPAName + "\")\n");
          }

          if (field.getIndex() != null) {
            imports.add("org.hibernate.annotations.Index");
            beanCode.append("\t" + "@Index(name=\"" + field.getIndex() + "\")\n");
          }
          beanCode.append("\t" + "@Column(name=\"" + field.sqlName + "\"");
          
          
          // Si té IMP foreign keys llavors no es por modificar, només de lectura
          /*
          ForeignKey[] fk = field.getForeignKeysByType(ForeignKey.IMPORTED);
          if (fk != null && fk.length > 0) {
            beanCode.append(", insertable = false, updatable = false");
          }
          */

          if (field.isNotNullable) {
            beanCode.append(",nullable = false");
          }
          if (field.isUniqueKey() && !field.isPrimaryKey()) {
            beanCode.append(",unique = true");
          }
          Integer size = field.getSize(); 
          String clob = "";
          if (size != null) {
            beanCode.append(",length = " + size);
            // CLOB
            if (size > 65532 && String.class.equals(field.getJavaType())) {
               clob = "  @Lob\n";
               imports.add("javax.persistence.Lob");
            }
          }
          if (field.getDigits() != null && field.getDigits() != 0) {
            beanCode.append(",precision = " + field.getDigits());
          }
          beanCode.append(")\n");

          // CLOB
          beanCode.append(clob);

        }
        
        // Definicio
        beanCode.append("\t" + type + " " + name + ";\n\n");
      }
      
      
      
      beanCode.append("\n\n");
  
      // CONSTRUCTORS !!!!
      beanCode.append(CodeGenerator.generateBeanConstructors(table, codeBeanFileName, beanGenCode.constructors));
      
      // Nou Constructor a partir d'una interface
      
      List<FieldInfo> fileFields= CodeGenUtils.getFileFieldsOfTable(project.tables, table);
      
      TableInfo fileTable = CodeGenUtils.getSqlTableFile(project.tables);

      beanCode.append(ModelGenerator.generateCopyBeanCode(
          table, fields, codeBeanFileName, "toJPA", 
          fileFields, fileTable, "JPA", true));
      
      

      
      
      
  
      // Metodos GET-SET
      Map<FieldInfo, BeanFieldCode> codeByField = beanGenCode.codeByField;
      
      // METODES GET-SET PER BEAN
      for (int r = 0; r < fields.length; r++) {
        
        if (table.isTranslationMapEntity() && fields[r].isPrimaryKey()) {
          continue;
        }
        
        BeanFieldCode bfc = codeByField.get(fields[r]);

        beanCode.append(bfc.prototipusGet + bfc.contentGet);
       
        beanCode.append(bfc.prototipusSet + bfc.contentSet);
      }
      
      beanCode.append("\n\n"); 
      
      
      
      beanCode.append("  @Override\n");
      beanCode.append("  public boolean equals(Object __obj) {\n");
      beanCode.append("  boolean __result;\n");
      beanCode.append("    if (__obj != null && __obj instanceof " + tableNameJava + ") {\n");
      beanCode.append("      " + tableNameJava + " __instance = (" + tableNameJava + ")__obj;\n");
      beanCode.append("      __result = true;\n");
      
      if (table.isTranslationMapEntity()) {
        beanCode.append("      __result = super.equals(__instance);\n");
      } else {
        for (FieldInfo f : fields) {
          if (f.isPrimaryKey()) {  
    
            String method = CodeGenUtils.get(f);
            if (f.getJavaType().isPrimitive()) {
              beanCode.append("      __result = __result && (this." + method + " == __instance." + method + ") ;\n");
            } else {
          
              beanCode.append("      if (this." + method + " == null) {\n");
              beanCode.append("        __result = __result && (__instance." + method + " == null);\n");
              beanCode.append("      } else {\n");
              beanCode.append("        __result = __result && this." + method + ".equals(__instance." + method + ") ;\n");
              beanCode.append("      }\n");
              beanCode.append("\n");
            }
          }
        }
      }
      beanCode.append("    } else {\n");
      beanCode.append("      __result = false;\n");
      beanCode.append("    }\n");
      beanCode.append("    return __result;\n");
      beanCode.append("  }\n\n");

      
      HashMap<String, String>  importForeignKeys = new HashMap<String, String>();
      HashMap<String, String>  exportForeignKeys = new HashMap<String, String>();
      if (!table.isTranslationMapEntity()) {
        beanCode.append(generateImportsExports(project, table, fields, imports, 
            jpaPackage, CodeType.JPA, importForeignKeys, exportForeignKeys));
      }
      
      // Generate DataHandler si és Fitxer
      if (table.isFileMappingEntity()) {
        beanCode.append("\n");
        beanCode.append(ModelGenerator.generateDataHandler("  @javax.persistence.Transient"));
        
        beanCode.append("\n");
        beanCode.append("  final static ThreadLocal<String> threadLocal = new ThreadLocal<String>();\n"); 
        beanCode.append("\n");
        beanCode.append("  public static void enableEncryptedFileIDGeneration() {\n");
        beanCode.append("    threadLocal.set(\"\");\n");
        beanCode.append("  }\n");
        beanCode.append("\n");
        
        beanCode.append("  public static void disableEncryptedFileIDGeneration() {\n");
        beanCode.append("    threadLocal.remove();\n");
        beanCode.append("  }\n");
        beanCode.append("\n");
        beanCode.append("  @javax.persistence.PostPersist\n");
        beanCode.append("  @javax.persistence.PostLoad\n");
        beanCode.append("  void postLoad() {\n");
        beanCode.append("    if (threadLocal.get() != null) {\n");      
        beanCode.append("      this.encryptedFileID = " + hibernatePackage + ".HibernateFileUtil.encryptFileID(fitxerID);\n");
        beanCode.append("    }\n");
        beanCode.append("  }\n");
        beanCode.append("\n");
      }
      
      
      if (!table.isTranslationMapEntity()) {
        beanCode.append("\n // ---------------  STATIC METHODS ------------------\n");
        
        beanCode.append(ModelGenerator.generateCopyBeanCode(
            table, fields, codeBeanFileName, "toJPA", 
            fileFields, fileTable, "JPA", false));

        beanCode.append(generateCopyJPACode(table,  importForeignKeys, exportForeignKeys,
            fields, codeBeanFileName, "copyJPA", fileFields, fileTable, "JPA"));
      }

      // Final de Classe
      beanCode.append("\n\n");      
      beanCode.append("}\n");
  
      
      // -------------------------------------
      // ---------- HEADER 
      // -------------------------------------
      
      StringBuffer header = new StringBuffer();
      
      
      header.append("\n");
      
      
      header.append("package " + jpaPackage + ";\n");
      
      
      header.append("import " + entityPackage + ".*;\n");
      
      // SEMPRE PRESENTS
      header.append("import javax.persistence.Table;\n");      
      header.append("import javax.persistence.Column;\n");

      
      for (String i: imports) {
        header.append("import " + i + ";\n");
      }
      header.append("\n\n");

     return new SourceFile(codeBeanFileName + ".java",
         header.toString() + beanCode.toString(), true);

  }


  
  public enum CodeType {
    JPA,
    BEAN,
    INTERFACE
  }

  
  
  public static String generateCopyJPACode(TableInfo table,
      HashMap<String, String>  importForeignKeys, HashMap<String, String>  exportForeignKeys,
      FieldInfo[] fields, String codeBeanFileName, String copyMethodName,
     List<FieldInfo> fileFields, TableInfo fileTable, String fileSuffix) throws Exception {
    
    String tableNameJava = table.getNameJava();
    
    StringBuffer beanCode = new StringBuffer();
    
    String entityBase = "Object"; //IGenAppEntity.class.getCanonicalName();
    
    beanCode.append("\n");
    beanCode.append("  public static " + codeBeanFileName + " " + copyMethodName + "(" + codeBeanFileName + " __jpa) {\n");
    beanCode.append("    return " + copyMethodName + "(__jpa,new java.util.HashMap<" + entityBase + "," + entityBase + ">(), null);\n");
    beanCode.append("  }\n\n");
    
    
    /*static Set<PeticioDeFirmaJPA> copyJPA(Set<PeticioDeFirmaJPA> __jpaSet, java.util.Map<org.fundaciobit.genapp.common.IGenAppEntity,org.fundaciobit.genapp.common.IGenAppEntity> __alreadyCopied) {
      if (__jpaSet == null) { return null; }
      Set<PeticioDeFirmaJPA> __tmp = (Set<PeticioDeFirmaJPA>) __alreadyCopied.get(__jpaSet);
      if (__tmp != null) { return __tmp; };
      __tmp = new HashSet<PeticioDeFirmaJPA>();
      __alreadyCopied.put(__jpaSet, __tmp);
      */
    
    beanCode.append("  static java.util.Set<" + codeBeanFileName + "> " + copyMethodName + "(java.util.Set<" + codeBeanFileName + "> __jpaSet,\n"
        + "    java.util.Map<" + entityBase + "," + entityBase + "> __alreadyCopied, String origenJPA) {\n");
    beanCode.append("    if (__jpaSet == null) { return null; }\n");
    beanCode.append("    java.util.Set<" + codeBeanFileName + "> __tmpSet = (java.util.Set<" + codeBeanFileName + ">) __alreadyCopied.get(__jpaSet);\n");
    beanCode.append("    if (__tmpSet != null) { return __tmpSet; };\n");
    beanCode.append("    __tmpSet = new java.util.HashSet<"+ codeBeanFileName + ">(__jpaSet.size());\n");
    beanCode.append("    __alreadyCopied.put(__jpaSet, __tmpSet);\n");
    beanCode.append("    for (" + codeBeanFileName + " __jpa : __jpaSet) {\n");
    beanCode.append("      __tmpSet.add(copyJPA(__jpa, __alreadyCopied, origenJPA));\n");
    beanCode.append("    }\n");
    beanCode.append("    return __tmpSet;\n");
    beanCode.append("  }\n\n");
    
    

    beanCode.append("  static " + codeBeanFileName + " " + copyMethodName + "(" + codeBeanFileName + " __jpa,\n"
        + "    java.util.Map<" + entityBase + "," + entityBase + "> __alreadyCopied, String origenJPA) {\n");
    beanCode.append("    if (__jpa == null) { return null; }\n");
    beanCode.append("    " + codeBeanFileName + " __tmp = (" + codeBeanFileName + ") __alreadyCopied.get(__jpa);\n");
    beanCode.append("    if (__tmp != null) { return __tmp; };\n");
    beanCode.append("    __tmp = toJPA(__jpa);\n");
    beanCode.append("    __alreadyCopied.put(__jpa, __tmp);\n");
    beanCode.append("    // Copia de beans complexes (EXP)\n");
    for (String method : exportForeignKeys.keySet()) {
      String jpa = exportForeignKeys.get(method);
      // No copiam la classe d'on provenim
      beanCode.append("    if(!\"" + jpa +  "\".equals(origenJPA) \n");
      beanCode.append("       && ( !org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa." + CodeGenUtils.getModelName(method) 
          + ") || org.hibernate.Hibernate.isInitialized(__jpa." + CodeGenUtils.get(method) + ")) ) {\n");
      beanCode.append("      __tmp." + CodeGenUtils.set(method) + "(" + jpa +  "." + copyMethodName + "(__jpa." + CodeGenUtils.get(method) + ", __alreadyCopied,\"" +  codeBeanFileName + "\"));\n");
      beanCode.append("    }\n");
    }
    
    beanCode.append("    // Copia de beans complexes (IMP)\n");
    
    for (String method : importForeignKeys.keySet()) {
      String jpa = importForeignKeys.get(method);
      beanCode.append("    if(!\"" + jpa +  "\".equals(origenJPA) && \n");
      beanCode.append("       (!org.fundaciobit.genapp.common.utils.Utils.isEmpty(__jpa." + CodeGenUtils.getModelName(method) 
          + ") || org.hibernate.Hibernate.isInitialized(__jpa." + CodeGenUtils.get(method) + ") ) ) {\n");

      beanCode.append("      __tmp." + CodeGenUtils.set(method) + "(" + jpa +  "." + copyMethodName + "(__jpa." +  CodeGenUtils.get(method) + ", __alreadyCopied,\"" +  codeBeanFileName + "\"));\n");
      beanCode.append("    }\n");
    }
    
    if (table.isTranslationEntity()) {
      beanCode.append("    // Aquesta linia s'afeix de forma manual\n");
      beanCode.append("    __tmp.setTraduccions(new HashMap<String, TraduccioMapJPA>(__jpa.getTraduccions()));\n");
    }
    
    beanCode.append("\n");
    
    
    beanCode.append("    return __tmp;\n");
    beanCode.append("  }\n\n\n");
    
    return beanCode.toString();
  }
  

  
  protected static String generateImportsExports(Project project, TableInfo table,
      FieldInfo[] fields, Set<String> imports, String jpaPackage, CodeType codeType) throws Exception {
   
    return generateImportsExports(project, table,   fields, imports, jpaPackage,
        codeType, new HashMap<String, String>(), new HashMap<String, String>());
      
  }
  
  

  protected static String generateImportsExports(Project project, TableInfo table,
      FieldInfo[] fields, Set<String> imports, String jpaPackage, CodeType codeType,
      Map<String, String> importForeignKeys, Map<String, String> exportForeignKeys)
          throws Exception {
    

    StringBuffer jpaCode = new StringBuffer();
    StringBuffer interfaceCode;
    StringBuffer beanCode;
    
    switch (codeType) {
      case JPA:
        interfaceCode = jpaCode;
        beanCode = jpaCode;
      break;
      
      case BEAN:
        beanCode = new StringBuffer();
        interfaceCode = beanCode;
        return "";
      //break;
      
      case INTERFACE:
        beanCode = new StringBuffer();
        interfaceCode = new StringBuffer();
        return ("");
      //break;
      
      default:
        throw new Exception("Tipus de Codi desconegut: " + codeType);
    }
    
    String ignore = System.getProperty("ignoreimportexport");
    boolean ignoreJPARefs = "true".equals(ignore); 
    if (ignoreJPARefs) {
      interfaceCode.append("\n/*\n");
    }
    
    
    // CALCUL DE TAULES REPETIDES EN IMPORTACIO DE CLAUS FORANIES 
    Set<String> taulesImportadesRepetides = new HashSet<String>();
    Set<String> taulesExportadesRepetides = new HashSet<String>();
    {
      Set<String> all = new HashSet<String>();        
      for (int r = 0; r < fields.length; r++) {
        FieldInfo field = fields[r];
        ForeignKey[] keys =  field.getForeignKeysByType( ForeignKey.IMPORTED);
        for (ForeignKey fk : keys) {
          if (all.contains(fk.getTable())) {
            taulesImportadesRepetides.add(fk.getTable());
          }
          all.add(fk.getTable());
        }
        
        keys =  field.getForeignKeysByType( ForeignKey.EXPORTED);
        for (ForeignKey fk : keys) {
          if (all.contains(fk.getTable())) {
            taulesExportadesRepetides.add(fk.getTable());
          }
          all.add(fk.getTable());
        }
        
      }
    }
    
    // Elements d'ALT NIVELL
    
    TableInfo fileTableInfo = CodeGenUtils.getSqlTableFile(project.getTables());
    TableInfo translationTableInfo = CodeGenUtils.getSqlTableTranslation(project.getTables());

    for (int r = 0; r < fields.length; r++) {
      FieldInfo field = fields[r];
      /*
      Class<?> typeClass = field.getJavaType();
      String type = typeClass.getName();
      String name = field.javaName;
      */
      
      // =================================
      // =================================
      // ========= E X P O R T ===========
      // =================================
      // =================================

      ForeignKey[] keys =  field.getForeignKeysByType( ForeignKey.EXPORTED);
      
      if (keys.length != 0) {
        // Exported
        if (codeType == CodeType.JPA) {
          imports.add("javax.persistence.FetchType");
        }
        
        
        //imports.add("javax.persistence.JoinTable");
        //imports.add("javax.persistence.JoinColumn");
      }
      
      
      // trobar foreign keys que ataquen a una mateixa taula per� a diferents camps
      //Set<String> taulesRepetides = getTaulesRepetides(keys);
      
      for (ForeignKey fk : keys) {
          
        // TODO trobar java de classe fk.getTable()
        TableInfo expTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable()); 
        FieldInfo expField = CodeGenUtils.findFieldInfoByColumnSQLName(expTable, fk.getField());
        
        if (!expTable.generate || (table.isTranslationEntity() && expTable.isTranslationMapEntity())) {
          continue;
        }
        
        jpaCode.append("// EXP  Field:" + fk.getField() 
            + " | Table: " + fk.getTable()     
            + " | Type: " + fk.getType()
            + "  \n\n"); 
        
        
        String javaName = getJavaName(expTable.getNameJava(), codeType);

        String methodname;
        String mappedBy;
        
        
        // Revisar si és una relació OneToOne, és a dir, els camps inici i desti 
        // de la relació són claus primaries
        
        String newfield;
        Class<?> typeForeign; 
        if (isOneToOne(table, field,expTable, expField)) {
          
          if (codeType == CodeType.JPA) {
            //imports.add("javax.persistence.JoinColumn");
            imports.add("javax.persistence.OneToOne");
          }
          
          typeForeign = javax.persistence.OneToOne.class;
          
          // Codi Antic
          // jpaCode.append("\t@OneToOne\n");
          // jpaCode.append("\t@JoinColumn(name = \"" + field.getSqlName() + "\")\n");
          
          // Codi nou
          // Hem de llevar el ID de la foreigKey (si no te ID, llavors no ens agrada)
          // Per obtenir l'objecte pe. professorID (long) => professor (ProfessorJPA)
          String javaNameField = expField.getJavaName();
          if (!javaNameField.endsWith("ID")) {                          
            throw new Exception("El camp " + field.getJavaName() + " de la taula "
                  +  table.getNameJava() 
                  + " fa referencia a la taula externa "  + expTable.getNameJava() + " al camp "
                  + javaNameField + ",  però aquest camp java no acaba amb ID");
          }
          
          javaNameField= javaNameField.substring(0, javaNameField.length() - 2);
          jpaCode.append("\t@OneToOne(mappedBy=\"" + javaNameField + "\")\n");
          

          methodname = expTable.getNameJava();
          newfield =  CodeGenUtils.getModelName(methodname);
          
          
          
          String pre;
          if (codeType == CodeType.INTERFACE) {
            pre = "<F extends " + javaName + "> ";
            javaName = "F";
          } else {
            pre = "";
          }
          
          
          
          
          beanCode.append("\tprivate " + javaName + " " + newfield + ";\n"); 
          interfaceCode.append("\tpublic " + pre + javaName + " " + CodeGenUtils.get(methodname));
          if (codeType == CodeType.INTERFACE) {
            interfaceCode.append(";\n"); 
          } else {
            beanCode.append(" {\n"); 
            beanCode.append("\t  return this." + newfield + ";\n"); 
            beanCode.append("\t}\n\n");
          }
          
          beanCode.append("\tpublic " + pre + " void " + CodeGenUtils.set(methodname) + "(" + javaName + " " + newfield + ")");
          // TODO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
          /*
          if (codeType == CodeType.INTERFACE) {
            interfaceCode.append(";\n"); 
          } else */ {
            beanCode.append(" {\n"); 
            beanCode.append("\t  this." + newfield + " = " + newfield + ";\n"); 
            beanCode.append("\t}\n");
          }
          interfaceCode.append("\n\n"); 
          
          

        } else {
          if (codeType == CodeType.JPA) {
            imports.add("javax.persistence.OneToMany"); 
          }
          if (codeType != CodeType.INTERFACE) {
            if (!ignoreJPARefs) {
              imports.add("java.util.HashSet");
            }
          }
          if (!ignoreJPARefs) {
            imports.add("java.util.Set");
          }
          /** Check varis Foreign Key sobre la mateixa taula */
          if (taulesExportadesRepetides.contains(fk.getTable())) {
            // SI ESTA REPETIT: El camp ho feim amb el nom de la taula més el nom del camp remot
            methodname = fk.getField();            
            if (methodname.endsWith("ID")) {
              methodname= methodname.substring(0, methodname.length() - 2);
            }
            // Enlloc del nom de la taula, posam el nom del camp de la taula del fk
            // (La propietat Java, no el camp de BBDD)
           
            mappedBy = expField.getJavaName();  //mappedBy + "_" + methodname;
            // TODO Fer plurals
            methodname = expTable.getNameJava() + "_" + methodname + "s";
          } else {
            // SI NO ESTA REPETIT ho feim amb el nom de la taula
            // TODO Fer plurals
            
            methodname = expTable.getNameJava() + "s";  

             if ( (fileTableInfo != null && table.getName().equals(fileTableInfo.getName()))
                 || (translationTableInfo != null && table.getName().equals(translationTableInfo.getName()) )) {
               mappedBy = expField.getJavaName();
             } else {
               mappedBy = CodeGenUtils.getModelName(table.getNameJava());  //.toLowerCase(); // .name ZZZ
             }
          }
          boolean isFileMappingTable = CodeGenUtils.isTranslationTable(project, fk.getTable());
          String fetchType = (isFileMappingTable)?"EAGER" : "LAZY";
          String collectionType1 = isFileMappingTable?"java.util.List" : "Set";
          String collectionType2 = isFileMappingTable?"java.util.ArrayList" : "HashSet";
          
          jpaCode.append("\t@OneToMany(fetch = FetchType." + fetchType + ", mappedBy = \"" + mappedBy + "\")\n");
          typeForeign = javax.persistence.OneToMany.class;
          
          // Obtenir el nom java del camp de la taula fk.
          
          //beanCode.append("\t@OneToMany(fetch = FetchType.LAZY)\n");          
          //beanCode.append("\t@JoinTable(name = \"" + fk.getTable() + "\"," +
          //    " joinColumns = @JoinColumn(name = \"" + expField.javaName + "\"))\n");
          // TODO afegir inverseJoinColumn????
          
          newfield = CodeGenUtils.getModelName(methodname); //.toLowerCase();
          
          String pre;
          if (codeType == CodeType.INTERFACE) {
            pre = "<F extends " + javaName + "> ";
            javaName = "F";
          } else {
            pre = "";
          }
          
          
          beanCode.append("\tprivate " + collectionType1+ "<" + javaName + "> " + newfield + " = new " + collectionType2+ "<" + javaName + ">(0);\n"); 
          interfaceCode.append("\tpublic " + pre + " " + collectionType1+ "<" + javaName + "> " + CodeGenUtils.get(methodname));
          if (codeType == CodeType.INTERFACE) {
            interfaceCode.append(";\n");
          } else {
            beanCode.append(" {\n"); 
            beanCode.append("    return this." + newfield + ";\n"); 
            beanCode.append("  }\n");
          }
          interfaceCode.append("\n");
          beanCode.append("\tpublic void " + CodeGenUtils.set(methodname) + "(" + collectionType1+ "<" + javaName + "> " + newfield + ")");
          // TODO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
          {
            beanCode.append(" {\n");  
            beanCode.append("\t  this." + newfield + " = " + newfield + ";\n"); 
            beanCode.append("\t}\n");
            beanCode.append("\n\n"); 
          }
        }
        
        exportForeignKeys.put(methodname, javaName);
        
        foreignFields.add(new ForeignFieldInfo(newfield ,  expTable.getNameJava(), typeForeign));
        

      } // Final de for-EXP
      
      
      // =================================
      // =================================
      // ========= I M P O R T ===========
      // =================================
      // =================================
      
      
      if (table.isTranslationEntity() && codeType == CodeType.JPA) {
        /*
        beanCode.append("  @CollectionOfElements(fetch=FetchType.EAGER)\n");
        beanCode.append("  @OneToMany(fetch = FetchType.EAGER, mappedBy = \"traduccio\")\n");
        beanCode.append("  @JoinColumn(name = \"traduccioID\")\n");
        beanCode.append("  @JoinTable( joinColumns=@JoinColumn(name=\"traduccioGrupID\"))\n"); 
        beanCode.append("  @MapKey(name = \"idiomaID\")\n");
        */
        beanCode.append("  @CollectionOfElements(fetch= FetchType.EAGER,targetElement = " + jpaPackage + ".TraduccioMapJPA.class)\n");
        beanCode.append("  @Cascade(value=org.hibernate.annotations.CascadeType.ALL)\n");
        beanCode.append("  @LazyCollection(value= LazyCollectionOption.FALSE)\n");
        // TODO {prefix]_traducciomap -> Extreure de project.tables
        beanCode.append("  @JoinTable(name=\"" + project.getPrefix().toLowerCase() + "_traducciomap\",joinColumns={@JoinColumn(name=\"traducciomapid\")})\n");
        beanCode.append("  @org.hibernate.annotations.MapKey(columns={@Column(name=\"idiomaid\")})\n");
        // TODO FK ->   Extreure de project.tables foreig keys
        beanCode.append("  @ForeignKey(name=\"" + project.getPrefix().toLowerCase() + "_traducmap_traduccio_fk\") \n");
        beanCode.append("  private Map<String, " + jpaPackage + ".TraduccioMapJPA> traduccions =  new HashMap<String, " + jpaPackage + ".TraduccioMapJPA>();\n");
        beanCode.append("\n");
        beanCode.append("  public Map<String, " + jpaPackage + ".TraduccioMapJPA> getTraduccions() {\n");
        beanCode.append("    return this.traduccions;\n");
        beanCode.append("  }\n");
        beanCode.append("\n");
        beanCode.append("  public void setTraduccions(Map<String, " + jpaPackage + ".TraduccioMapJPA> _traduccions_) {\n");
        beanCode.append("    this.traduccions = _traduccions_;\n");
        beanCode.append("  }\n");
        beanCode.append("\n");
        
        
        beanCode.append("  public java.util.Set<String> getIdiomes() {\n");
        beanCode.append("    return this.traduccions.keySet();\n");
        beanCode.append("  }\n");
        beanCode.append("  \n");
        beanCode.append("  public " + jpaPackage + ".TraduccioMapJPA getTraduccio(String idioma) {\n");
        beanCode.append("    return (" + jpaPackage + ".TraduccioMapJPA) traduccions.get(idioma);\n");
        beanCode.append("  }\n");
        beanCode.append("  \n");
        beanCode.append("  public void addTraduccio(String idioma, " + jpaPackage + ".TraduccioMapJPA traduccio) {\n");
        beanCode.append("    if (traduccio == null) {\n");
        beanCode.append("      traduccions.remove(idioma);\n");
        beanCode.append("    } else {\n");
        beanCode.append("      traduccions.put(idioma, traduccio);\n");
        beanCode.append("    }\n");
        beanCode.append("  }\n");

        
        
        
        imports.add("java.util.Map");          
        imports.add("java.util.HashMap");
        imports.add("javax.persistence.JoinColumn");
        imports.add("javax.persistence.JoinTable");
        //imports.add("javax.persistence.MapKey");
        imports.add("org.hibernate.annotations.CollectionOfElements");
        imports.add("org.hibernate.annotations.Cascade");
        imports.add("org.hibernate.annotations.ForeignKey");
        imports.add("org.hibernate.annotations.Index");
        imports.add("org.hibernate.annotations.LazyCollection");
        imports.add("org.hibernate.annotations.LazyCollectionOption");
      }

      keys =  field.getForeignKeysByType( ForeignKey.IMPORTED);
      
      if (keys.length != 0 && codeType == CodeType.JPA) {
        // Imported
        imports.add("javax.persistence.JoinColumn");          
        imports.add("javax.persistence.FetchType");
      }
      
      
      for (ForeignKey fk : keys) {
          
        
        
       TableInfo impTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable()); 
       FieldInfo impField = CodeGenUtils.findFieldInfoByColumnSQLName(impTable, fk.getField());
       String javaName = getJavaName(impTable.getNameJava(), codeType);
        
        
        if (!impTable.generate) {
          continue;
        }
        
        jpaCode.append("// IMP Field:" + fk.getField() 
            + " | Table: " + fk.getTable()     
            + " | Type: " + fk.getType()
            + "  \n\n"); 
        
        
        String newfield;
        Class<?> typeForeign;
        String methodname;
        /*
        methodname = impTable.getNameJava(); //  field.getJavaName();      
        if (!methodname.endsWith("ID")) {
          throw new Exception("El camp " + field.getJavaName() + " de la taula "
            +  table.getNameJava() 
            + " fa referencia a una taula externa però el seu nom java no acaba amb ID");
        }
        
        // Llevam ID
        methodname= methodname.substring(0, methodname.length() - 2);
        */
        
        boolean isFiletable = CodeGenUtils.isFileTable(project, fk.getTable());
        boolean isTranslationTable = CodeGenUtils.isTranslationTable(project, fk.getTable())
                                       && !table.getNameJava().equals("TraduccioMap");
        
        /** Check varis Foreign Key sobre la mateixa taula */
        if (taulesImportadesRepetides.contains(fk.getTable())) {
          methodname = field.getJavaName();            
          if (methodname.endsWith("ID")) {
            methodname= methodname.substring(0, methodname.length() - 2);
          } else {
            throw new Exception("El camp " + field.getJavaName() + " de la taula "
                +  table.getNameJava() 
                + " fa referencia a una taula externa però el seu nom java no acaba amb ID");
          }
          /* methodname = impTable.getNameJava() + "_" + methodname; */
          
        } else {
         
          
          /*
          if (isOneToOne(table, field,expTable, expField)) {
          */
          if (!isFiletable && !(isTranslationTable)) {
            methodname = impTable.getNameJava();
          } else {
                         
            methodname = field.getJavaName(); 
            if (methodname.endsWith("ID")) {
              methodname= methodname.substring(0, methodname.length() - 2);
            } else {
              throw new Exception("El camp " + field.getJavaName() + " de la taula "
                  +  table.getNameJava() 
                  + " fa referencia a una taula externa però el seu nom java no acaba amb ID");
            }
            
          }
          
        }
        

        TableInfo expTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable()); 
        FieldInfo expField = CodeGenUtils.findFieldInfoByColumnSQLName(expTable, fk.getField());
        
        if (!isFiletable) {
          importForeignKeys.put(methodname, javaName);
        }
             
        
        newfield = CodeGenUtils.getModelName(methodname);
        String relType;
        if (isOneToOne(table, field, impTable, impField)) {
          if (codeType == CodeType.JPA) {
            imports.add("javax.persistence.OneToOne");
          }
          relType = "OneToOne";
          typeForeign = javax.persistence.OneToOne.class;
        } else {
          if (codeType == CodeType.JPA) {
            imports.add("javax.persistence.ManyToOne");
            imports.add("org.hibernate.annotations.ForeignKey");
          }
          relType = "ManyToOne";
          typeForeign = javax.persistence.ManyToOne.class;
        }
        
        String fetchType = (isFiletable || isTranslationTable)?"EAGER" : "LAZY";
        
        if (isTranslationTable) {
          fetchType = fetchType + ", cascade=javax.persistence.CascadeType.ALL";
        }
        
        if (codeType == CodeType.JPA) {
          jpaCode.append("\t@" + relType + "(fetch = FetchType." + fetchType + ")\n");

          String fkName = fk.getName();
          
          
          if (fkName == null) {
              fkName = project.getPrefix() + "_" + table.getNameJava().toLowerCase() + "_" + expTable.getNameJava().toLowerCase() + "_fk";
          }
          
          imports.add("org.hibernate.annotations.ForeignKey");
          jpaCode.append("\t@ForeignKey(name=\"" + fkName + "\")\n");

          jpaCode.append("\t@JoinColumn(name = \"" + field.getSqlName() 
              + ("ManyToOne".equals(relType)? ( "\", referencedColumnName =\"" + expField.getJavaName() ) : "")
              + "\", nullable = " + !field.isNotNullable() +
            ", insertable=false, updatable=false)" + "\n");
            //(isFiletable? ")" : ", insertable=false, updatable=false)") + "\n");
        }
        
        
        
        beanCode.append("\tprivate " + javaName + " " + newfield + ";\n");
        interfaceCode.append("\n");
        
        String pre;
        if (codeType == CodeType.INTERFACE) {
          pre = "<F extends " + javaName + "> ";
          javaName = "F";
        } else {
          pre = "";
        }
        
        
        interfaceCode.append("\tpublic " + pre + javaName + " " + CodeGenUtils.get(methodname));
        if (codeType == CodeType.INTERFACE) {
          interfaceCode.append(";\n");
        } else {
          beanCode.append(" {\n"); 
          beanCode.append("    return this." + newfield + ";\n"); 
          beanCode.append("  }\n");
        }
        beanCode.append("\n"); 
        beanCode.append("\tpublic " +  pre + " void " + CodeGenUtils.set(methodname) + "(" + javaName + " " + newfield + ")");
        ////  TODO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        /*
        if (codeType == CodeType.INTERFACE) {
          interfaceCode.append(";\n");
        } else */ {
          beanCode.append(" {\n");
          beanCode.append("    this." + newfield + " = " + newfield + ";\n"); 
          beanCode.append("  }\n"); 
        }
        
        
        if (isTranslationTable && codeType == CodeType.JPA) {
          beanCode.append("\n"); 
          beanCode.append("  @javax.xml.bind.annotation.XmlTransient\n");
          beanCode.append("  public java.util.Map<String, " + jpaPackage+ ".TraduccioMapJPA> " + CodeGenUtils.getOnlyName(methodname) + "Traduccions() {\n"); 
          beanCode.append("    return this." + newfield + ".getTraduccions();\n"); 
          beanCode.append("  }\n"); 
          beanCode.append("\n"); 
          beanCode.append("  public void " + CodeGenUtils.set(methodname) + "Traduccions(java.util.Map<String, " + jpaPackage+ ".TraduccioMapJPA> __traduccions__) {\n"); 
          beanCode.append("    this." + newfield + ".setTraduccions(__traduccions__);\n"); 
          beanCode.append("  }\n");
          beanCode.append("\n"); 
        }

        
        interfaceCode.append("\n");
        
        foreignFields.add(new ForeignFieldInfo(newfield ,  impTable.getNameJava(), typeForeign));
        
      } // Final de For-IMP
      
      
      
      
    } 
    
    
    // FINAL DE IF DE IGNORE FOREIGNKEY
    if (ignoreJPARefs) {
      interfaceCode.append("\n*/\n");
    }
    
    
    switch (codeType) {
      case JPA:
        return jpaCode.toString();
      
      
      case BEAN:
        return beanCode.toString();
      
      case INTERFACE:
        return interfaceCode.toString();
        
      default:
        throw new Exception("Tipus de Codi desconegut: " + codeType);
    }
    
  }
  
  
  
  public static List<ForeignFieldInfo> foreignFields = new ArrayList<ForeignFieldInfo>();
  
  
  public static String getJavaName(String name, CodeType codeType) throws Exception {
    switch (codeType) {
      case JPA:
        return name + "JPA";

      case BEAN:
        return name + "Bean";
      
      case INTERFACE:
        return name;
        
      default:
        throw new Exception("Tipus de Codi desconegut: " + codeType);
    }
  }
  

  
  public static boolean isOneToOne(TableInfo table, FieldInfo field,TableInfo expTable, FieldInfo expField) {
    if ((field.isPrimaryKey() || field.isUniqueKey()) && (expField.isPrimaryKey() || expField.isUniqueKey()) ) {
      
      if (field.isPrimaryKey()) {
        // Ha de ser l'únic PK
        FieldInfo[] fields = table.getFields();
        for (int i = 0; i < fields.length; i++) {
          if (fields[i].isPrimaryKey() && !fields[i].equals(field)) {
            return false;
          }
        }
      }
      if (expField.isPrimaryKey()) {
        // Ha de ser l'únic PK
        FieldInfo[] fields = expTable.getFields();
        for (int i = 0; i < fields.length; i++) {
          if (fields[i].isPrimaryKey() && !fields[i].equals(expField)) {
            return false;
          }
        }
      }
      
      return true;
      
    } 
    return false;
  }
  
  
  

  public static Set<String> getTaulesRepetides(ForeignKey[] keys) {
    Set<String> taulesRepetides = new HashSet<String>(); 
    {
      Set<String> all = new HashSet<String>(); 
      for (ForeignKey fk : keys) {
        if (all.contains(fk.getTable())) {
          taulesRepetides.add(fk.getTable());
        }
        all.add(fk.getTable());
      }
    }
    return taulesRepetides;
  }
  
  
  

  //====================================================================
  // ========================== MANAGER =================================
  // ====================================================================

    
  public static SourceFile generateCodeForManager(TableInfo tableInfo, 
    BasicPackages packages, String jpaPackage, Project project, BeanCode beanCode) {

    
    if (!tableInfo.generate || tableInfo.isTranslationMapEntity()) {
      return null;
    }
    
    //String tableNameSQL = tableInfo.getName(); 
    String tableNameJava = tableInfo.getNameJava();      
    //FieldInfo[] fields = tableInfo.getFields();
    

    //String databaseManager = Project.getDBManagerName(project.projectName);
    DaoCommonCode dao = beanCode.daoCommonCode;
    String className = tableNameJava + "JPA"; 

    StringBuffer manager = new StringBuffer("");

    /** Cabecera */
    manager.append("\n");

    manager.append("package " + jpaPackage + ";\n");

    String managerFileName = tableNameJava + "JPAManager";

    manager.append("import java.util.*;\n");
    manager.append("import javax.persistence.EntityManager;\n");
    manager.append("import javax.persistence.PersistenceContext;\n");
    manager.append("import " + packages.entityPackage + ".*;\n");
    manager.append("import " + packages.fieldsPackage + ".*;\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("import " + TableName.class.getName() + ";\n");
    manager.append("import " + I18NException.class.getName()+ ";\n");
    manager.append("import " + GenAppPackages.PKG_BASE + ".common.events.ModificationManager;\n");


    manager.append("\n\n");
    if (tableInfo.getDescripcio() != null) {
      manager.append("/* " + tableInfo.getDescripcio() + " */\n");
    }
    
    String pkClass = dao.pkClass;
    if (pkClass.equals("BigDecimal") || pkClass.equals("BigInteger")) {
      pkClass = "java.math." + pkClass;
    }
    
    manager.append("public class " + managerFileName 
        + "\n\t\t extends Abstract" + project.projectName + "JPAManager<" + tableNameJava + ", " + pkClass +">"
        + "\n\t\t implements I"  + tableNameJava + "Manager, " + tableNameJava + "Fields {\n\n\n");
    
    manager.append("\n\n");
    manager.append("  private static final long serialVersionUID = "
        + managerFileName.hashCode() + "L;\n\n");

    manager.append("\t public static final TableName<" + tableNameJava
        + "> _TABLENAME =  new TableName<" + tableNameJava + ">(\"" + tableNameJava + "JPA\");\n");
    manager.append("\n");

    // (2) Listener
    String modification = "ModificationManager<" 
      + ModelGenerator.getInterfaceName(tableNameJava)+ ">";
    manager.append("\n\n");
    // TODO llevar static !!!!!!
    manager.append("  static final " + modification + " __eventManager =" +
        " new " + modification + "();\n\n");
    
    // (0) Instance
    //manager.append("  private static final " + managerFileName 
    //    + " __manager = new " + managerFileName + "();\n\n");
    
    // ENTITI MANAGER
    manager.append("\n");
    
    manager.append("  @PersistenceContext\n");
    manager.append("  protected EntityManager __em;\n");

    // CONSTRUCTOR
    manager.append("  public " + managerFileName + "() {\n");
    manager.append("  }\n");
        
    manager.append("  protected " + managerFileName + "(EntityManager __em) {\n");
    manager.append("    this.__em = __em;\n");
    manager.append("  }\n");
    
    manager.append("\n");
    manager.append("\n");

    manager.append("  protected EntityManager getEntityManager() {\n");
    manager.append("    return this.__em;\n");
    manager.append("  }\n");
    
    
    // ===========================================
    // (15) getInstance Of TableManager
    /*
    manager.append("\tpublic static " + managerFileName + " getInstance() {\n");
    manager.append("\t  return __manager;\n");
    manager.append("\t};\n\n");
    */
    
   
    // (1) Parent Abstract Methods
    /*
    manager.append("\tpublic Connection getConnection() throws Exception {\n");
    manager.append("\t\t return " + databaseManager + ".getConnection();\n");
    manager.append("\t}\n\n");
    */

    
    manager.append("\tpublic Class<?> getJPAClass() {\n");
    manager.append("\t\treturn " + tableNameJava + "JPA. class;\n");
    manager.append("\t}\n\n");

    // ========================================
    // (8) GetEventManager
    manager.append("\n");
    manager.append("\n");
    
    manager.append("\tpublic ModificationManager<" + tableNameJava + "> getEventManager() {\n");
    manager.append("\treturn __eventManager;\n");
    manager.append("\t}\n");
    
    
 // ========================================
    // (8) GetTableName && getTableNameVariable
    manager.append("\n");
    manager.append("\n");
    
    manager.append("\tpublic TableName<" + tableNameJava + "> getTableName() {\n");
    manager.append("\t\treturn _TABLENAME;\n");
    manager.append("\t}\n\n\n");
    
    
    manager.append("\t@Override\n");
    manager.append("\tprotected String getTableNameVariable() {\n");
    manager.append("\t\treturn _TABLE_MODEL;\n");
    manager.append("\t}\n\n\n");
    

    // ===========================================
    // (14) List to Array
    
    manager.append("\tpublic " + tableNameJava + "[] listToArray" +
       "(List<" + tableNameJava + "> list)  {\n");
    manager.append("\t\tif(list == null) { return null; };\n");
    manager.append("\t\treturn list.toArray(new " + tableNameJava + "[list.size()]);\n");
    manager.append("\t};\n\n");
    
    
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
          + versionParams[i] + ") throws I18NException {\n");
      
      manager.append("\t\t" + className + " __bean =  new " + className + "("
          + versionBeanParams[i] + ");\n");
      manager.append("\t\treturn create(__bean);\n");

      manager.append("\t}\n\n");
    }

     
    
    
    

    // (10) DELETE
    manager.append("\n\n");
    manager.append(" public void delete(" + dao.findParams
        + ") {\n");
    manager.append("   delete(findByPrimaryKey(" + dao.findParamsCall + "));\n");
    //manager.append("   return 1;\n");
    manager.append(" }\n\n\n");
    
    
 // ========================================
    // (9) findByPrimaryKey
    manager.append("\n");
    manager.append("\n");
    manager.append("\tpublic " + tableNameJava + " findByPrimaryKey("
        + dao.findParams + ") {\n");
    if (dao.pkClass.endsWith("PK")) {
      manager.append("\t  return __em.find(" + tableNameJava + "JPA.class, new " + dao.pkClass + "(" + dao.findParamsCall + "));\n");
    } else {
      manager.append("\t  return __em.find(" + tableNameJava + "JPA.class, " + dao.findParamsCall + ");  \n");
    }
    manager.append("\t}\n");
    
    
    // ========================================
    // (9) selectList I i II
    /*
    manager.append("\n");
    manager.append("\t@SuppressWarnings(\"unchecked\")\n"); 
    manager.append("\tpublic List<" + tableNameJava 
        + "> select(Where where, OrderBy ... orderBy) throws Exception {\n");
    manager.append("\t  return select(where, null, null, orderBy);\n");
    manager.append("\t}\n\n");
    

    manager.append("\n");
    manager.append("\t@SuppressWarnings(\"unchecked\")\n"); 
    manager.append("\tpublic List<" + tableNameJava 
        + "> select(Where where, Integer firstResult, Integer maxResults, OrderBy ... orderBy) throws Exception {\n");
    String instance = tableNameJava.toLowerCase();

    manager.append("\t  StringBuffer __query = new StringBuffer();\n");
    manager.append("\t  __query.append(\"SELECT " + instance + " FROM " + className + " " + instance + " \");\n");

    manager.append("\t  if (where != null) {\n");
    manager.append("\t    __query.append(\" where \" + where.toSQL());\n");
    manager.append("\t  }\n");
    manager.append("\t  __query.append(OrderBy.processOrderBy(orderBy));\n");

    manager.append("\t  Query q = __em.createQuery(__query.toString());\n");
    manager.append("\t  if (where != null) {\n");
    manager.append("\t    where.setValues(q);\n");
    manager.append("\t  }\n");

    manager.append("\t  if (firstResult != null) { q.setFirstResult(firstResult); }\n");
    manager.append("\t  if (maxResults != null) {\n");
    manager.append("\t     q.setMaxResults(maxResults);\n");    
    manager.append("\t  }\n");
    manager.append("\t  try {\n");
    manager.append("\t    return q.getResultList();\n");
    manager.append("\t  } catch(Exception e) {\n");
    manager.append("\t     log.error(\"Error executant la sentència SQL: \" + __query.toString());\n");
    manager.append("\t     throw e;\n");
    manager.append("\t  }\n");

    manager.append("\t}\n\n");
    */
    

    
    

    // ========================================
    // (9) getJPAInstance
    manager.append("\t@Override\n");
    manager.append("\tprotected " + tableNameJava + " getJPAInstance(" + tableNameJava + " __bean) {\n");
    manager.append("\t\treturn convertToJPA(__bean);\n");
    manager.append("\t}\n\n\n");
    
    manager.append("\tpublic static " + className + " convertToJPA(" + tableNameJava + " __bean) {\n");
    manager.append("\t  if (__bean == null) {\n");
    manager.append("\t    return null;\n");
    manager.append("\t  }\n");
    manager.append("\t  if(__bean instanceof " + className + ") {\n");
    manager.append("\t    return (" + className + ")__bean;\n");    
    manager.append("\t  }\n");
    manager.append("\t  \n");
    manager.append("\t  return " + className + ".toJPA(__bean);\n");
    manager.append("\t}\n\n");
    
    
    List<FieldInfo> transFields = CodeGenUtils.getTranslationFieldsOfTable(project.getTables(), tableInfo);
    
    if (transFields != null && transFields.size() != 0) {
    
      manager.append("  @Override\n");
      manager.append("  public " + tableNameJava + " create(" + tableNameJava + " transientInstance) throws I18NException {\n");      
      manager.append("    if (transientInstance != null) {\n");
      for (FieldInfo fieldInfo : transFields) {
        String method = CodeGenUtils.get(fieldInfo);
        if (fieldInfo.getJavaType().isPrimitive()) {
	        manager.append("      if (transientInstance." + method + " == 0) {\n");
	    } else {
	        manager.append("      if (transientInstance." + method + " == null) {\n");
	    }
        manager.append("        if (transientInstance instanceof " + className + ") {\n");
        manager.append("          " + className + " _jpa = (" + className + ")transientInstance;\n");
        manager.append("          TraduccioJPA _trad = _jpa." + method.replace("ID()", "()")+ ";\n");
        manager.append("           if (_trad != null) {\n");
        manager.append("            if (_trad.getTraduccioID() == 0) {\n");
        manager.append("              getEntityManager().persist(_trad);\n");
        manager.append("            } \n");
        manager.append("            transientInstance." + CodeGenUtils.set(fieldInfo)+ "(_trad.getTraduccioID());\n");
        manager.append("          }\n");
        manager.append("        }\n");
        manager.append("      }\n");
      }
      manager.append("    }\n");
      manager.append("    return super.create(transientInstance);\n");
      manager.append("  }\n");
      manager.append("\n");
    
    }
    


    // Final de Clase Manager
    manager.append("\n}");
    
    return new SourceFile(managerFileName + ".java", manager.toString());
    
    
  }
  
  
  
  /**
   * DAO MANAGER
   * @param modelPackage
   * @param daoPackage
   * @param project
   * @return
   */
  public static SourceFile generateJPADaoManager(String jpaPackage,
      BasicPackages packages, Project project) {
    
    StringBuffer manager = new StringBuffer();
    
    String iname = "I" + project.projectName + "DaoManagers";
    
    String name = project.projectName + "JPADaoManagers";
    
    manager.append("package " + jpaPackage + ";\n");
    manager.append("\n");
    manager.append("import " + packages.modelPackage + ".*;\n");
    manager.append("import " + packages.daoPackage + ".*;\n");
    manager.append("import javax.persistence.EntityManager;\n");
    manager.append("\n");
    manager.append("public final class " + name + " implements " + iname + "{\n");
    manager.append("\n");

    // Variables
    for (int i = 0; i < project.tables.length; i++) {
      if (!project.tables[i].generate || project.tables[i].isTranslationMapEntity()) {
        continue;
      }
      String managerFileName = project.tables[i].getNameJava() + "JPAManager";
      manager.append("   private final " + managerFileName + " " + project.tables[i].name + ";\n");
    }
    
    // Constructor
    manager.append("\n");
    manager.append("  public  " + name + "(EntityManager __em) {\n");
    //manager.append("    this.__em = __em;\n");
    
    for (int i = 0; i < project.tables.length; i++) {
      if (!project.tables[i].generate || project.tables[i].isTranslationMapEntity()) {
        continue;
      }
      String managerFileName = project.tables[i].getNameJava() + "JPAManager";
      manager.append("    this." + project.tables[i].name + " = new " + managerFileName +  "(__em);\n");
    }
    
    manager.append("  }\n");
    
    // M�todes
    manager.append("\n");
    
    
    for (int i = 0; i < project.tables.length; i++) {
      if (!project.tables[i].generate || project.tables[i].isTranslationMapEntity()) {
        continue;
      }
      
      String managerFileName = project.tables[i].getNameJava() + "Manager";
      manager.append("\tpublic I" + managerFileName + " get" + managerFileName + "() {\n");
      manager.append("\t  return this." + project.tables[i].name + ";\n");
      manager.append("\t};\n");
      manager.append("\n");
    }
    
    manager.append("\n");
    manager.append("}");
    
    
    return new SourceFile(name + ".java", manager.toString(), true);
  }
  
  
  

  public static SourceFile generateQueryPath(BasicPackages packages,
      TableInfo tableInfo, FieldInfo[] fields, BeanCode beanCode) {

    String javaName = tableInfo.getNameJava();
    
    if (!tableInfo.isGenerate() || tableInfo.isTranslationMapEntity()) {
      return null;
    }
    
    String codeFileName = javaName + "QueryPath";

    String fieldsClass = javaName + "Fields";
    
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
      String fieldClass = ModelGenerator.getFieldType(fieldType);

      indexes.append("  public " + fieldClass + " " + name.toUpperCase() + "() {\n");
      
      indexes.append("    return new " + fieldClass + "(getQueryPath(), " + fieldsClass + "." + name.toUpperCase() + ");\n");
      //indexes.append("    return new " + fieldClass + "(null,\n");
      //indexes.append("      getQueryPath() + " + fieldsClass + "." + name.toUpperCase() + ".javaName, null);\n");
      indexes.append("  }\n");
      indexes.append("\n");

    }

    StringBuffer interfaceCode = new StringBuffer("");


    interfaceCode.append("\n");

    interfaceCode.append("package " + packages.fieldsPackage + ";\n");

    interfaceCode.append("import " + GenAppPackages.PKG_BASE + ".common.query.*;\n");
    interfaceCode.append("\n");
    interfaceCode.append("public class " + codeFileName
        + " extends " + org.fundaciobit.genapp.common.query.QueryPath.class.getName() + " {");
    interfaceCode.append("\n\n");
    
    
    interfaceCode.append("  public " + codeFileName + "() {\n");
    interfaceCode.append("  }\n");
    interfaceCode.append("\n");
    interfaceCode.append("  protected " + codeFileName + "(QueryPath parentQueryPath) {\n");
    interfaceCode.append("    super(parentQueryPath);\n");
    interfaceCode.append("  }\n");
    interfaceCode.append("\n");
    
    interfaceCode.append(indexes.toString());
    interfaceCode.append("\n\n");
    
    interfaceCode.append("  @Override\n");
    interfaceCode.append("  public String getQueryPath() {\n");
    interfaceCode.append("    return ((this.parentQueryPath == null) ? (" + fieldsClass + "._TABLE_MODEL + \".\")\n");
    interfaceCode.append("        : this.parentQueryPath.getQueryPath());\n");
    interfaceCode.append("  }\n");
    interfaceCode.append("\n\n");
    
    List<ForeignFieldInfo> list = DaoJPAGenerator.foreignFields;
    
    for(ForeignFieldInfo item : list) {
    
      String ref = item.name; 
      String table= item.table;
      
      if (item.type.equals(javax.persistence.OneToMany.class)) {
        interfaceCode.append("/* L'ús d'aquest camp (OneToMany) llança una exception:\n"
            + " [Illegal attempt to dereference a collection]\n\n"); 
        
        interfaceCode.append(" // TODO Solució dins el mètode testOneByOneDirect de la classe TestJPA \n\n");
        
      } 
    
      interfaceCode.append("  public " + table + "QueryPath " + ref.toUpperCase() + "() {\n");
      interfaceCode.append("    return new " + table + "QueryPath(new QueryPath() {\n");
      interfaceCode.append("      public String getQueryPath() {\n");
      interfaceCode.append("          return " + codeFileName + ".this.getQueryPath() + \"" + ref + "\" + \".\";\n");
      interfaceCode.append("      }\n");
      interfaceCode.append("    });\n");
      interfaceCode.append("  }\n");
      if (item.type.equals(javax.persistence.OneToMany.class)) {
        interfaceCode.append("*/\n");
      }
      
      interfaceCode.append("\n");
      
      
    }
    
    // Final de classe
    interfaceCode.append("}\n");

    return new SourceFile(codeFileName + ".java", interfaceCode.toString(), true);
  }
  
  
  
  
  public static class ForeignFieldInfo {
    
    final String name;
    
    final String table;
    
    final Class<?> type; // OneToOne, Many2One o One2Many

    /**
     * @param name
     * @param table
     * @param type
     */
    public ForeignFieldInfo(String name, String table, Class<?> type) {
      super();
      this.name = name;
      this.table = table;
      this.type = type;
    }
    
    
  }
  
  
}

package org.fundaciobit.genapp.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.WebFieldInfo;
import org.fundaciobit.genapp.WebType;
import org.fundaciobit.genapp.common.i18n.I18NArgumentCode;
import org.fundaciobit.genapp.common.i18n.I18NArgumentString;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NFieldError;

/**
 * 
 * @author anadal
 *
 */
public class JPAValidatorGenerator {

  
  public static List<SourceFile> generateValidator(String packageValidatorLogic,
      String jpaPackage, BasicPackages packages,
      Project project) throws Exception {

    TableInfo[] tables = project.getTables();

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);

    for (int i = 0; i < tables.length; i++) {
      
      if (!tables[i].generate || tables[i].isTranslationMapEntity()) {
        continue;
      }

      TableInfo table = tables[i];

      FieldInfo[] fields = table.getFields();

      final String tableJavaName = table.getNameJava();
      //final String model = CodeGenUtils.getModelName(tableJavaName);
      final String instanceEjb = getJPAManagerInstance(tableJavaName);
      final String jpaClass = tableJavaName + "JPA";
      final String fieldsClass = tableJavaName + "Fields";
      final String validator = tableJavaName + "Validator";
      final String validatorBean = tableJavaName + "BeanValidator";
      
      final String managerPkg = packages.daoPackage;
      /*
      final String ivalidator = tableJavaName + "ValidatorResult";
      final String validatorEJB = tableJavaName + "LogicValidatorEJB";
      final String validatorLocal = tableJavaName + "LogicValidatorLocal";
      */
      
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }

      Set<String> ejbs = new TreeSet<String>();
      StringBuffer code;
      
      // Sempre ha de tenir accés a la pròpia classe
      ejbs.add(table.getNameJava());

      // ================ METHOD VALIDATOR ================


      code = null;
      code = new StringBuffer();
      

      // Serveix per seleccionar les claus primaries i a més els items
      // que no tenen la clau primaria igual a la que estam editant (aquest
      // darrer
      // cas només s'usarà si estem editant)
      List<FieldInfo> PKs = CodeGenUtils.getPKs(fields);
      List<DataGenUnique> dataGenUniqueNotPK = new ArrayList<DataGenUnique>();
      for (FieldInfo field : PKs) {        
        dataGenUniqueNotPK.add(new DataGenUnique(field, false));
      }

      code.append("    // Valors Not Null\n");
      for (FieldInfo field : fields) {

        if (field.isNotNullable() && !field.isAutoIncrement()) {
          String camp = field.javaName.toUpperCase();
          if (!fileFields.contains(field)) {
            code.append("    __vr.rejectIfEmptyOrWhitespace(__target__," + camp
                + ", \n");
            code.append("        \"genapp.validation.required\",\n"  
                + "        new " + I18NArgumentCode.class.getName() + "(get(" + camp + ")));\n");
            code.append("\n");
          }
        }
      }

      code.append("    // Check size\n");
      for (FieldInfo field : fields) {
        // TODO altres tipus ????
        if (field.getJavaType().equals(String.class)) {
          
          // Mira si el tamany s'ajusta          
          String camp = field.javaName.toUpperCase();
          String campLower = field.javaName.toLowerCase();
          Class<?> typeClass = field.getJavaType();
          String cls = typeClass.isPrimitive() ? SQL2Java.primitiveType2javaClassName(
              typeClass).getName() : typeClass.getName();
          code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) {\n");
          code.append("      " + cls + " __" + campLower + " = (" + cls
              + ")__vr.getFieldValue(__target__," + camp + ");\n");
          code.append("      if (__" + campLower + "!= null && __" + campLower + ".length() > " + field.size + ") {\n");
          code.append("        __vr.rejectValue(" + camp
              + ", \"genapp.validation.sizeexceeds\",\n");
          code.append("            new " + I18NArgumentCode.class.getName() + "(get(" + camp
              + ")), new " + I18NArgumentString.class.getName() + "(String.valueOf(" + field.size + ")));\n");
          code.append("      }\n");
          code.append("    }\n");
          code.append("    \n");

          // Mira si s'ha definit un patro
          String pattern = field.getMinAllowedValue();
          if (pattern != null) {
            if (CodeGenUtils.isInteger(pattern)) {
               // Check min len
              code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) {\n");
              code.append("      " + cls + " __" + campLower + " = (" + cls
                  + ")__vr.getFieldValue(__target__," + camp + ");\n");
              code.append("      if (__" + campLower + "!= null && __" + campLower + ".length() < " + pattern + ") {\n");
              code.append("        __vr.rejectValue(" + camp
                  + ", \"genapp.validation.minsizerequired\",\n");
              code.append("            new " + I18NArgumentCode.class.getName() + "(get(" + camp
                  + ")), new " + I18NArgumentString.class.getName() + "(String.valueOf(" + pattern + ")));\n");
              code.append("      }\n");
              code.append("    }\n");
              code.append("    \n");
              
            } else {
               // Check pattern
              
              String nouPatro = pattern.replace("\\", "\\\\");
              if (field.getWebFieldInfo().getWebtype() == WebType.ComboBox) {
                nouPatro = "(" + nouPatro.replace(',', '|') + ")";
              }
            
              code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) {\n");           
              code.append("      String val = String.valueOf(__vr.getFieldValue(__target__," + camp + "));\n");
              code.append("      if (val != null && val.trim().length() != 0) {\n");
              code.append("        java.util.regex.Pattern p = java.util.regex.Pattern.compile(\"" + nouPatro + "\");\n");
              code.append("        if (!p.matcher(val).matches()) {\n");
              code.append("          __vr.rejectValue(" + camp + ", \"genapp.validation.malformed\",\n");
              code.append("             new " + I18NArgumentString.class.getName() + "(val), new " + I18NArgumentCode.class.getName() + "(get(" + camp  + ")));\n");
              code.append("        }\n");
              code.append("      }\n");
              code.append("    }\n");
              code.append("\n");
            }
          }
        }

        if (!field.getJavaType().equals(Boolean.class) &&
            !field.getJavaType().equals(Boolean.TYPE) &&
            (
              field.getJavaType().isAssignableFrom(Number.class)
              || (field.getJavaType().isPrimitive() && !field.getJavaType().equals(Character.TYPE))
            )) {
          // checkMinim
          code.append(checkNumberMaxMin(table, field, true)); 
          // checkMaxim
          code.append(checkNumberMaxMin(table, field, false));
        }
        
      }
      


      
      code.append("    if (__isNou__) { // Creació\n");

      code.append("      // ================ CREATION\n");

      code.append("      // Fitxers \n");
      for (FieldInfo field : fields) {

        if (field.isNotNullable() && !field.isAutoIncrement()) {
          
          if (fileFields.contains(field)) {

            String camp = field.javaName.toUpperCase();
            code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) { // FITXER\n"); 
            code.append("      Object __value = __vr.getFieldValue(__target__," + camp + ");\n");
            code.append("      if (__value == null || String.valueOf(__value).trim().length() == 0 || String.valueOf(__value).trim().equals(\"0\") ) {\n");
            code.append("          __vr.rejectValue(" + camp + ", \"genapp.validation.required\",\n");
            code.append("             new " + I18NArgumentCode.class.getName() + "(get(" + camp + ")));\n");
            code.append("      }\n");
            code.append("    }\n");
            code.append("\n");
            
            /*
             * TODO
            String camp = field.javaName.toUpperCase();
            code.append("      CommonsMultipartFile " + field.javaName + " = ((" + form + ")target)." + CodeGenUtils.get(field)+ ";\n");
            code.append("      if (" + field.javaName + " == null || " + field.javaName + ".isEmpty()) {\n");
            code.append("          rejectValue(" + camp + ", \"genapp.validation.required\",\n");
            code.append("          new I18NArgument[]{ new " + I18NArgumentCode.class.getName() + "(get(" + camp + ")) },\n");
            code.append("          null);\n");
            code.append("      }\n");
            code.append("\n");
            */
          }
        }
      }

      code.append("      // ====== Check Unique MULTIPLES - NOU =======\n\n");
      
      
      // CLAUS MULTIPLES 
      MultipleUnique[] multipleUniques = table.getMultipleUniques();
      Map<String,List<DataGenUnique>> multipleUniquesList = new HashMap<String,List<DataGenUnique>>();
      if (multipleUniques != null && multipleUniques.length != 0) {
        
        ejbs.add(tableJavaName);
        
        for (int j = 0; j < multipleUniques.length; j++) {
          String[] dbFields = multipleUniques[j].getSqlColumns();
          FieldInfo[] fieldInfoUniques = new FieldInfo[dbFields.length];
          
          StringBuffer title = new StringBuffer("      // Check Unique MULTIPLE for (");
          for (int k = 0; k < dbFields.length; k++) {
            if (k != 0) {
              title.append(", ");              
            }
            title.append(dbFields[k]);
            fieldInfoUniques[k] = CodeGenUtils.findFieldInfoByColumnSQLName(table, dbFields[k]); 
          }
          title.append(")\n");

          List<DataGenUnique> dataGenUnique = new ArrayList<DataGenUnique>();
          for (FieldInfo fi : fieldInfoUniques) {
            if (fi.isAutoIncrement()) {
              throw new Exception("No es poden crear UNIQUES multiples que incloquin la clau primaria");
            }
            dataGenUnique.add(new DataGenUnique(fi));
          }
          multipleUniquesList.put(title.toString(), dataGenUnique);
        }
        
      }
      
      

      for (String title : multipleUniquesList.keySet()) {
        
        List<DataGenUnique> dataGenUnique = multipleUniquesList.get(title);
        code.append(title);
        code.append(genUniqueBlock(instanceEjb, dataGenUnique, null));  
      }


      code.append("      // Check Unique - no PK\n");
      for (FieldInfo field : fields) {
        if (field.isUniqueKey() && !field.isPrimaryKey()) {
          ejbs.add(tableJavaName);
          code.append(genUniqueBlock(instanceEjb, new DataGenUnique(field), null));
        }
      }

      if (PKs.size() == 1) {
        code.append("      // Check Unique - PK no AutoIncrement amb UNA SOLA PK \n");
        for (FieldInfo field : fields) {
          if (field.isPrimaryKey() && !field.isAutoIncrement()) {
            ejbs.add(tableJavaName);
            code.append(genUniqueBlock(instanceEjb, new DataGenUnique(field), null));
          }
        }
      } else {
        code.append("      // Check Unique - PK no AutoIncrement amb MULTIPLES PK \n");
        List<DataGenUnique> dataGenUnique = new ArrayList<DataGenUnique>();
        for (FieldInfo pk : PKs) {
          if (!pk.isAutoIncrement()) {
            dataGenUnique.add(new DataGenUnique(pk));
          }
        }
        ejbs.add(tableJavaName);
        code.append(genUniqueBlock(instanceEjb, dataGenUnique, null));

      }
      code.append("    } else {\n");
      code.append("      // ================ UPDATE\n\n");
      
      
      code.append("      // ====== Check Unique MULTIPLES - EDIT  =======\n\n");
      
      for (String title : multipleUniquesList.keySet()) {
        List<DataGenUnique> dataGenUnique = multipleUniquesList.get(title);
        code.append(title);
        code.append(genUniqueBlock(instanceEjb, dataGenUnique, dataGenUniqueNotPK));  
      }
      
      code.append("      // Check Unique - no PK\n");

      for (FieldInfo field : fields) {

        if (field.isUniqueKey() && !field.isPrimaryKey()) {
          ejbs.add(tableJavaName);
          code.append(genUniqueBlock(instanceEjb, new DataGenUnique(field), dataGenUniqueNotPK));
        }
      }
      code.append("    }\n");

      code.append("\n");
      code.append("    // Fields with References to Other tables \n");
      for (FieldInfo queryField : fields) {
        // Ignorar fitxers
        if (fileFields.contains(queryField)) {
          continue;
        }

        WebFieldInfo wfi = queryField.getWebFieldInfo();
        if (wfi.webtype == WebType.Query) {
          String camp = queryField.javaName.toUpperCase();
          String campLower = "__" + queryField.javaName.toLowerCase();
          Class<?> typeClass = queryField.getJavaType();
          String cls = typeClass.isPrimitive() ? SQL2Java.primitiveType2javaClassName(
              typeClass).getName() : typeClass.getName();

          //code.append("      // FK Field SQL: " + wfi.getForeignKey().getField() + "\n");
          //code.append("      // FK Table SQL: " + wfi.getForeignKey().getTable() + "\n");
          //code.append("      // FK type: "
          //    + (wfi.getForeignKey().getType() == ForeignKey.IMPORTED) + "\n");

          TableInfo impTable = CodeGenUtils.findTableInfoByTableSQLName(project, wfi
              .getForeignKey().getTable());
          System.out.println("impTable: " + impTable.getNameJava());
          System.out.println("wfi.getForeignKey().getField(): " + wfi.getForeignKey().getField());
          
          FieldInfo impField = CodeGenUtils.findFieldInfoByColumnSQLName(impTable, wfi
              .getForeignKey().getField());

          String jTableName = impTable.getNameJava();
          //code.append("      // FK Table Java: " + jTableName + "\n");
          String jFieldName = impField.getJavaName();
          //code.append("      // FK Field Java: " + jFieldName + "\n");

          ejbs.add(jTableName);
          final String impModel = CodeGenUtils.getModelName(jTableName);
          final String instanceLocal = getJPAManagerInstance(jTableName); //impModel + "Ejb";

          String where = jTableName + "Fields." + jFieldName.toUpperCase() + ".equal("
              + campLower + ")";

          code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) {\n");
          code.append("      " + cls + " " + campLower + " = (" + cls
              + ")__vr.getFieldValue(__target__," + camp + ");\n");
          String tab = "";
          if (!queryField.isNotNullable) {
            String emptyCondition = "";
            if (String.class.equals(queryField.getJavaType())) {
              emptyCondition = " && " + campLower + ".length() != 0";
            }            
            code.append("      if (" + campLower + " != null " + emptyCondition + ") {\n");
            tab = "  ";
          }
          code.append(tab + "      Long __count_ = null;\n");
          code.append(tab + "      try { __count_ = " + instanceLocal + ".count(" + where
              + "); } catch(" + I18NException.class.getName() + " e) { e.printStackTrace(); };\n");
          code.append(tab + "      if (__count_ == null || __count_ == 0) {        \n");
          // error.notfound=No s´ha trobat cap {0} amb {1} igual a {2}
          code.append(tab + "        __vr.rejectValue(" + camp
              + ", \"error.notfound\",\n");
          code.append(
              "         new " + I18NArgumentCode.class.getName() + "(\"" + impModel + "." + impModel + "\"),\n"
              + "         new " + I18NArgumentCode.class.getName() + "(\"" + impModel + "." + jFieldName + "\"),\n"
              + "         new " + I18NArgumentString.class.getName() + "(String.valueOf(" + campLower + ")));\n");
          code.append(tab + "      }\n");
          if (!queryField.isNotNullable) {
            code.append("      }\n");
          }
          code.append("    }\n");
          code.append("\n");

        }
      }
      
      
      final String CONTENT = code.toString();
      
      
      
   // =================  BASE CLASS .JAVA ==================
      
      code = null;
      code = new StringBuffer();

      code.append("package " + packageValidatorLogic + ";\n");
      code.append("\n");

      code.append("import org.apache.log4j.Logger;\n");
      

      code.append("\n");
      // code.append("import " + ejbPackage +"." + local +";\n");
      // code.append("import " + packages.entityPackage + "." + tableJavaName +
      // ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.query.Field;\n");
      code.append("import " + packages.fieldsPackage + "." + fieldsClass +";\n");
      
      // EJBs
      for (String tableName : ejbs) {
        if (!tableName.equals(tableJavaName)) {
          code.append("import " + packages.fieldsPackage + "." + tableName +"Fields;\n");
        }
      }
      
      code.append("\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.validation.IValidatorResult;\n");

      // TODO
      /*
      if (fileFields.size() != 0) {
        for (FieldInfo f : fileFields) {
          if (f.isNotNullable) {
            code.append("import org.springframework.web.multipart.commons.CommonsMultipartFile;\n");
            break;
          }
        }
      }
      */
      
      code.append("\n");
      code.append("\n");
      code.append("/**\n");
      code.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      code.append(" * @author GenApp\n");
      code.append(" * @author anadal\n");
      code.append(" */\n");
      code.append("public class " + validator);
      code.append("<T> implements " + fieldsClass + " {\n");
      code.append("\n");
      code.append("  protected final Logger log = Logger.getLogger(getClass());\n");
      code.append("\n");

      code.append("\n");
      code.append("  public " + validator + "() {\n");
      code.append("    super();    \n");
      code.append("  }\n");
      code.append("  \n");

      code.append("\n");
      
      code.append("  /** Constructor */\n");
      code.append("  public void validate(IValidatorResult<T> __vr," +
      		" T __target__, boolean __isNou__");
      // EJBs
      for (String tableName : ejbs) {
        code.append("\n    ," +  getJPAManagerClass(managerPkg, tableName) + " "
            + getJPAManagerInstance(tableName));        
      }

      code.append(") {\n");
      code.append("\n");
      
      
      code.append(CONTENT);

      code.append("  } // Final de mètode\n");

    
      
      code.append("  public String get(Field<?> field) {\n");
      code.append("    return field.fullName;\n");
      code.append("  }\n");
      code.append("  \n");
      code.append("}");

      sourceFiles.add(new SourceFile(validator + ".java", code.toString(), true));
      
      
      // ================ EJB ======================
      

      
      // ================  BeanValidator ======================
      
      code= null;
      code = new StringBuffer();
      
      code.append("package " + packageValidatorLogic + ";\n");
      code.append("\n");

      code.append("import " + jpaPackage + "." + jpaClass + ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.validation.BeanValidatorResult;\n");
      code.append("import java.util.List;\n");
      code.append("import " + I18NFieldError.class.getName() + ";\n");
      code.append("import " + I18NException.class.getName() + ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.validation.AbstractBeanValidator;\n");
      code.append("\n");
      code.append("/**\n");
      code.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      code.append(" * @author GenApp\n");
      code.append(" * @author anadal\n");
      code.append(" */\n");
      code.append("public class " + validatorBean + " \n");
      code.append("      extends AbstractBeanValidator<" + jpaClass + "> {\n");
      
      code.append("\n\n");
      code.append("  // EJB's\n");
      for (String tableName : ejbs) {
        code.append("  protected final " +  getJPAManagerClass(managerPkg , tableName) 
             + " " + getJPAManagerInstance(tableName) + ";\n");
        code.append("\n");
      }
      code.append("\n");

      code.append("  public final " + validator + "<" + jpaClass + "> _validator;\n");
      
      //
      code.append("\n\n");
      
      // Constructor BASIC 
      
      code.append("  public " + validatorBean + "("); 
      // EJBs
      int count = 0;
      for (String tableName : ejbs) {
        if (count != 0) {
          code.append(",\n     ");
        }
        code.append(getJPAManagerClass(managerPkg , tableName) + " " + getJPAManagerInstance(tableName));
        count++;
      }
      code.append(") { \n");
      for (String tableName : ejbs) {
        String instance = getJPAManagerInstance(tableName);
        code.append("    this." + instance + " = " + instance + ";\n");        
      }
      code.append("    _validator = new " + validator + "<" + jpaClass + ">();\n");
      code.append("  }\n\n");
      
      // Constructor amb Validador
      
      code.append("  public " + validatorBean + "("  + validator + "<" + jpaClass + "> _validator"); 
      // EJBs
      
      for (String tableName : ejbs) {
        code.append(",\n     " + getJPAManagerClass(managerPkg , tableName) + " " + getJPAManagerInstance(tableName));
      }
      code.append(") {\n");
      for (String tableName : ejbs) {
        String instance = getJPAManagerInstance(tableName);
        code.append("    this." + instance + " = " + instance + ";\n");        
      }
      code.append("    this._validator = _validator;\n");
      code.append("  }\n\n");
      

      code.append("  @Override\n");
      code.append("  public List<" + I18NFieldError.class.getSimpleName() + "> validate(" + jpaClass + " target, boolean isNou) throws I18NException {\n");
      
      code.append("    BeanValidatorResult<"+ jpaClass +"> _bvr_ = new BeanValidatorResult<"+ jpaClass +">();\n");
      code.append("    _validator.validate(_bvr_, target, isNou");
      for (String tableName : ejbs) {
        code.append(", " +  getJPAManagerInstance(tableName));
      }
      code.append(");\n");
      code.append("    return _bvr_.getErrors();\n");
      code.append("  }\n"); // Final mètode

      code.append("}\n"); // Final classes
      
      sourceFiles.add(new SourceFile(validatorBean + ".java", code.toString(), true));

      
      BackGenerator.ejbsByValidator.put(tableJavaName, ejbs);     
      
      

    }

    return sourceFiles;
  }

  protected static String checkNumberMaxMin(TableInfo table, FieldInfo field,
      boolean checkMinim) throws Exception {
    String valueStr = checkMinim? field.getMinAllowedValue() : field.getMaxAllowedValue();
    

    
    if (valueStr == null || valueStr.trim().length() == 0) {
      return "";
    }
    
    if (field.getWebFieldInfo().getWebtype() == WebType.ComboBox) {
      return "";
    }
      
    BigDecimal value;
    try {
      value = new BigDecimal(valueStr);
    } catch (NumberFormatException e) {
      throw new Exception("El valor " + (checkMinim?"minim":"màxim") 
          + " permes (get" + (checkMinim?"Min":"Max") + "AllowedValue) del camp "
          + field.javaName + " de la classe " + table.getNameJava() 
          + " no es convertible a numero (" + valueStr + ")");
    }
    String camp = field.javaName.toUpperCase();          
    
    String campLower = field.javaName.toLowerCase();
    Class<?> typeClass = field.getJavaType();
    
    String cls = typeClass.isPrimitive() ? SQL2Java.primitiveType2javaClassName(
        typeClass).getName() : typeClass.getName();
    StringBuffer code = new StringBuffer();
    code.append("    if (__vr.getFieldErrorCount(" + camp + ") == 0) {\n");
    code.append("      " + cls + " __" + campLower + " = (" + cls
        + ")__vr.getFieldValue(__target__," + camp + ");\n");
    
    String comparation = " new java.math.BigDecimal(__" + campLower + ".toString()).compareTo(new java.math.BigDecimal(\"" + value + "\"))";
    String errorMsgCode;
    if (checkMinim) {
      // Vol dir que és menor que el minim, per la qual cosa hi ha error
      comparation = comparation + " == -1"; 
      errorMsgCode = "genapp.validation.minimum";
    } else {
      // Vol dir que és major que el maxim, per la qual cosa hi ha error
      comparation = comparation + " == +1";
      errorMsgCode = "genapp.validation.maximum";
    }
    
    code.append("      if (__" + campLower + "!= null && " + comparation + ") {\n");
    code.append("        __vr.rejectValue(" + camp
        + ", \"" + errorMsgCode + "\",\n");
    code.append("            new " + I18NArgumentString.class.getName() + "(String.valueOf(__" + campLower + ")), "
        + "new " + I18NArgumentCode.class.getName() + "(get(" + camp + ")), new " + I18NArgumentString.class.getName() + "(String.valueOf(" + value + ")));\n");
    code.append("      }\n");
    code.append("    }\n");
    code.append("\n");
      
    return code.toString();
  }

  private static String genUniqueBlock(final String instanceEjb, DataGenUnique dataGenUnique,
      List<DataGenUnique> dataGenUniquesPKs) {
    List<DataGenUnique> list = new ArrayList<DataGenUnique>();
    list.add(dataGenUnique);
    return genUniqueBlock(instanceEjb, list, dataGenUniquesPKs);

  }

  private static String genUniqueBlock(final String instanceEjb,
      List<DataGenUnique> dataGenUnique, List<DataGenUnique> dataGenUniquesPKs) {

    StringBuffer code = new StringBuffer();

    if (dataGenUnique.size() != 0) {

      StringBuffer errorFree = new StringBuffer();
      StringBuffer vars = new StringBuffer();
      StringBuffer where = new StringBuffer();

      List<DataGenUnique> all = new ArrayList<DataGenUnique>();
      all.addAll(dataGenUnique);
      if (dataGenUniquesPKs != null) {
        all.addAll(dataGenUniquesPKs);
      }

      for (DataGenUnique dgu : all) {
        if (errorFree.length() != 0) {
          errorFree.append(" && ");
        }
        errorFree.append("__vr.getFieldErrorCount(" + dgu.camp + ") == 0");

        vars.append("        " + dgu.theClass + " " + dgu.campLower + " = (" + dgu.theClass
            + ")__vr.getFieldValue(__target__," + dgu.camp + ");\n");

        if (where.length() != 0) {
          where.append(", ");
        }
        if (dgu.equal) {
          where.append(dgu.camp + ".equal(" + dgu.campLower + ")");
        } else {
          where.append(dgu.camp + ".notEqual(" + dgu.campLower + ")");
        }
      }

      code.append("      if (" + errorFree.toString() + ") {\n");
      code.append(vars.toString());
      // code.append("        if (" + campLower + " != null) {\n");
      // code.append("          if (" + instanceEjb+ ".findByPrimaryKey(" +
      // paramsFinds.toString() + ") != null) {\n");

      code.append("        Long __count_ = null;\n");
      code.append("        try { __count_ = " + instanceEjb
          + ".count(" + GenAppPackages.PKG_BASE + ".common.query.Where.AND(" + where.toString()
          + ")); } catch(" + I18NException.class.getName() + " e) { e.printStackTrace(); };\n");
      code.append("        if (__count_ == null || __count_ != 0) {        \n");

      for (DataGenUnique dgu : dataGenUnique) {
        code.append("            __vr.rejectValue(" + dgu.camp
            + ", \"genapp.validation.unique\",\n");
        code.append("                new " + I18NArgumentString.class.getName() + "(String.valueOf(" + dgu.campLower + ")),\n");
        code.append("                     new " + I18NArgumentCode.class.getName() + "(get(" + dgu.camp
            + ")));\n");
      }
      code.append("        }\n");
      // code.append("        }\n");
      code.append("      }\n");
      code.append("\n");
    }

    return code.toString();
  }

  public static class DataGenUnique {

    final String camp;
    final String campLower;

    final String theClass;

    final boolean equal;

    public DataGenUnique(FieldInfo field) {
      this(field, true);
    }

    public DataGenUnique(FieldInfo field, boolean equal) {
      camp = field.javaName.toUpperCase();
      campLower = "__" + field.javaName.toLowerCase();
      Class<?> typeClass = field.getJavaType();
      theClass = typeClass.isPrimitive() ? SQL2Java.primitiveType2javaClassName(typeClass)
          .getName() : typeClass.getName();
      this.equal = equal;
    }
  }

  
  //TODO Canviar-ho per Managers JPA
  public static String getJPAManagerInstance(String tableJavaName) {
    return "__" + CodeGenUtils.getModelName(tableJavaName) + "Manager";
  }
  
  
  //TODO Canviar-ho per Managers JPA
  public static String getJPAManagerClass(String packageStr, String tableName) {
    //return packageStr + "." + tableName + "Local";
    return packageStr + ".I" + tableName + "Manager";
  }
  
}

package org.fundaciobit.genapp.generator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.WebFieldInfo;
import org.fundaciobit.genapp.WebType;
import org.fundaciobit.genapp.common.GenAppUtils;
import org.fundaciobit.genapp.common.KeyValue;
import org.fundaciobit.genapp.common.StringKeyValue;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.common.i18n.I18NValidationException;
import org.fundaciobit.genapp.common.query.Field;
import org.fundaciobit.genapp.common.query.OrderBy;
import org.fundaciobit.genapp.common.query.OrderType;
import org.fundaciobit.genapp.common.query.Where;
import org.fundaciobit.genapp.common.utils.Utils;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.fundaciobit.genapp.common.web.validation.WebValidationResult;

/**
 * 
 * @author anadal
 * 
 */
public class BackGenerator {
  
  public static Map<String, Set<String>> ejbsByValidator = new HashMap<String, Set<String>>();

  public static List<SourceFile> generateValidator(String packageValidatorBack,
      String packageFormBack, String jpaPackage, String ejbPackage, String logicValidationPkg, BasicPackages packages,
      Project project) throws Exception {

    TableInfo[] tables = project.getTables();

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);

    for (int i = 0; i < tables.length; i++) {
      
      if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity() ) {
        continue;
      }

      TableInfo table = tables[i];

      //FieldInfo[] fields = table.getFields();

      final String tableJavaName = table.getNameJava();
      final String model = CodeGenUtils.getModelName(tableJavaName);
      //final String instanceEjb = model + "Ejb";
      final String form = tableJavaName + "Form";
      // final String jpa = tableJavaName + "JPA";
      final String fieldsClass = tableJavaName + "Fields";
      final String validator = tableJavaName + "WebValidator";
      final String logicValidator = tableJavaName + "Validator";
      
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }

      Set<String> ejbs = new HashSet<String>();
      ejbs = ejbsByValidator.get(tableJavaName);

      StringBuffer code = new StringBuffer();

      code.append("package " + packageValidatorBack + ";\n");
      code.append("\n");

      code.append("import org.apache.log4j.Logger;\n");
      
      code.append("\n");
      
      code.append("import " + Field.class.getName() + ";\n");
      code.append("import " + WebValidationResult.class.getName() + ";\n");
      
      code.append("import " + packages.fieldsPackage + ".*;\n");
      code.append("\n");
      code.append("import org.springframework.stereotype.Component;\n");
      code.append("import org.springframework.validation.Errors;\n");
      //code.append("import org.springframework.validation.ValidationUtils;\n");
      code.append("import org.springframework.validation.Validator;\n");
      code.append("import " + logicValidationPkg + "." + logicValidator + ";\n");
      code.append("\n");
      code.append("import " + packageFormBack + "." + form + ";\n");
      
      
      if (fileFields.size() != 0) {
        for (FieldInfo f : fileFields) {
          if (f.isNotNullable) {
            code.append("import org.springframework.web.multipart.commons.CommonsMultipartFile;\n");
            break;
          }
        }
      }
      
      code.append("\n");
      code.append("\n");
      code.append("/**\n");
      code.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      code.append(" * @author anadal\n");
      code.append(" */\n");
      code.append("@Component\n");
      code.append("public class " + validator /*+ " extends BaseValidator */);
      code.append("  implements Validator, " + fieldsClass + " {\n");
      code.append("\n");
      code.append("  protected final Logger log = Logger.getLogger(getClass());\n");
      code.append("\n");
      
      
      code.append("  protected " + logicValidator + "<Object> validator = new " + logicValidator + "<Object>();\n");

      
      code.append("\n");
      code.append("  // EJB's\n");
      for (String tableName : ejbs) {
        final String ejb = tableName + "EJB";
        final String instance = CodeGenUtils.getModelName(tableName) + "Ejb";
        code.append("  @javax.ejb.EJB(mappedName = \""
            + project.getProjectName().toLowerCase() + "/" + ejb + "/local\")\n");
        code.append("  protected " + ejbPackage + "." + tableName + "Local " + instance + ";\n");
        code.append("\n");
      }
      code.append("\n");
      
      
      
      code.append("\n");
      code.append("  public " + validator + "() {\n");
      code.append("    super();    \n");
      code.append("  }\n");
      code.append("  \n");
      code.append("  @Override\n");
      code.append("  public boolean supports(Class<?> clazz) {\n");
      code.append("    return " + form + ".class.isAssignableFrom(clazz);\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  @Override\n");
      code.append("  public void validate(Object target, Errors errors) {\n");
      code.append("\n");

      
      List<FieldInfo> transFields = CodeGenUtils.getTranslationFieldsOfTable(tables, table);
      if (transFields != null && transFields.size() != 0) {
        code.append("java.util.List<Field<?>> _ignoreFields = new java.util.ArrayList<Field<?>>();\n");
        for (FieldInfo fieldInfo : transFields) {
          code.append("_ignoreFields.add(" + fieldInfo.getJavaName().toUpperCase() + ");\n");          
        }
      }

      
      code.append("    WebValidationResult<Object> wvr;\n");
      code.append("    wvr = new WebValidationResult<Object>(errors");
      
      if (transFields != null && transFields.size() != 0) {
        code.append(", _ignoreFields");
      }
      code.append(");\n");
      code.append("\n");
      code.append("    Boolean nou = (Boolean)errors.getFieldValue(\"nou\");\n");
      code.append("    boolean isNou =  nou != null && nou.booleanValue();\n");
      code.append("\n");
      code.append("    validate(target, errors, wvr, isNou);\n");
      
      code.append("  }\n");
      code.append("\n\n");
      
      
      code.append("  public void validate(Object target, Errors errors,\n");
      code.append("    WebValidationResult<Object> wvr, boolean isNou) {\n\n");
      
      
      
      
      if (transFields != null && transFields.size() != 0) {
        code.append("  {\n");
        code.append("    " + jpaPackage+ "."+ table.getNameJava()+"JPA " + model + ";\n");
        code.append("    if (target instanceof " + form + ") {\n");
        code.append("      " + model + " = ((" + form + ")target)." + CodeGenUtils.get(model)+ ";\n");
        code.append("    } else {\n");
        code.append("      " + model + " = (" + jpaPackage+ "."+ table.getNameJava()+"JPA)target;\n");
        code.append("    }\n");
        
        for (FieldInfo fieldInfo : transFields) {
          
          code.append("    {\n");
          code.append("      // IF CAMP ES NOT NULL verificar que totes les traduccions son not null\n");
          code.append("      " + jpaPackage+ ".TraduccioJPA tradJPA = " + model + "." + CodeGenUtils.get(fieldInfo).replace("ID()", "()") +";\n");
          boolean isNullable = !fieldInfo.isNotNullable;
          //code.append("      boolean isNullable = " + !fieldInfo.isNotNullable + ";\n");
          code.append("      if (tradJPA != null) {\n");
          // TODO Aquesta no és el codi d'error: "revisar les traduccions ja que hi ha errors."
          code.append("        // TODO ERROR\n");
          String path = model + "." + fieldInfo.getJavaName().substring(0, fieldInfo.getJavaName().length() - 2);
          String codeError = "\"genapp.validation.required\", new String[] {" + I18NUtils.class.getName() + ".tradueix(" + fieldInfo.getJavaName().toUpperCase() + ".fullName)}, null);\n";
          
          
          code.append("        java.util.Map<String,"+ jpaPackage + ".TraduccioMapJPA> _trad = tradJPA.getTraduccions();\n");
          if (isNullable) {
          code.append("        int countNull= 0;\n");
          }
          code.append("        int countNotNull = 0;\n");
          code.append("        for (String _idioma : _trad.keySet()) {\n");
          code.append("          " + jpaPackage + ".TraduccioMapJPA _map = _trad.get(_idioma);\n");
          code.append("          if (_map == null || (_map.getValor() == null || _map.getValor().length() == 0 )) {\n");
          if (isNullable) {
          code.append("            countNull++;\n");
          }
          code.append("          } else {\n");
          code.append("            countNotNull++;\n");
          code.append("          }\n");
          code.append("        }\n");
          code.append("\n");
          if (isNullable) {
          code.append("        if (countNull == _trad.size()) {\n");
          code.append("          // OK Tot esta buit ==> passam el camp a NULL\n");
          String setMethod = CodeGenUtils.set(fieldInfo);
          code.append("          " + model + "." + setMethod + "(null);\n");
          code.append("          " + model + "." + setMethod.substring(0, setMethod.length() - 2) + "(null);\n");
          code.append("        } else {\n");
          }
          code.append("          if (countNotNull  == _trad.size()) {\n");
          code.append("            // OK Tot esta ple\n");
          code.append("          } else {\n");
          code.append("            for (String _idioma : _trad.keySet()) {\n");
          code.append("              " + jpaPackage + ".TraduccioMapJPA _map = _trad.get(_idioma);\n");
          code.append("              if (_map == null || (_map.getValor() == null || _map.getValor().length() == 0 )) {\n");
          code.append("                errors.rejectValue(\"" + path + "\", " + codeError);
          code.append("                errors.rejectValue(\"" + path + ".traduccions[\"+ _idioma +\"].valor\",\n");
          code.append("                  " + codeError);
          code.append("              }\n");
          code.append("            }\n");
          code.append("          }\n");
          if (isNullable) {
          code.append("        }\n");
          }
          code.append("      } else {\n");
          code.append("        errors.rejectValue(\"" + path + "\", " + codeError);
          code.append("      }\n");
          code.append("    }\n");
          
          
          
          
          /* XYZ
          code.append("    {\n");
          code.append("    // IF CAMP ES NOT NULL verificar que totes les traduccions son not null\n");
          code.append("    " + jpaPackage+ ".TraduccioJPA tradJPA = " + model + "." + CodeGenUtils.get(fieldInfo).replace("ID()", "()") +";\n");
          code.append("    if (tradJPA == null) {\n");
          // TODO Aquesta no és el codi d'error: "revisar les traduccions ja que hi ha errors."
          code.append("      // TODO ERROR\n");
          String path = model + "." + fieldInfo.getJavaName().substring(0, fieldInfo.getJavaName().length() - 2);
          String codeError = "\"genapp.validation.required\", new String[] {" + I18NUtils.class.getName() + ".tradueix(" + fieldInfo.getJavaName().toUpperCase() + ".fullName)}, null);\n";
          code.append("      errors.rejectValue(\"" + path + "\", " + codeError);
          code.append("    } else {\n");
          code.append("      java.util.Map<String,"+ jpaPackage + ".TraduccioMapJPA> _trad = tradJPA.getTraduccions();\n");
          code.append("      for (String _idioma : _trad.keySet()) {\n");
          code.append("        " + jpaPackage + ".TraduccioMapJPA _map = _trad.get(_idioma);\n");
          code.append("        if (_map == null || _map.getValor() == null) {\n");
          code.append("          errors.rejectValue(\"" + path + "\", " + codeError);
          code.append("          errors.rejectValue(\"" + path + ".traduccions[\"+ _idioma +\"].valor\",\n");
          code.append("              " + codeError);
          code.append("        }\n");
          code.append("      }\n");
          code.append("    }\n");
          code.append("    }\n");
          */
        }
        
        code.append("\n");
        code.append("  }\n");
      }
      
      
      
      
      
      
      
      
      
      
      
      if (fileFields != null && fileFields.size() != 0) {
      
        code.append("    if (isNou) { // Creacio\n");
  
        code.append("      // ================ CREATION\n");
  
        code.append("      // Fitxers \n");
        for (FieldInfo field : fileFields) {
  
          if (field.isNotNullable() && !field.isAutoIncrement()) {
            
            //if (fileFields.contains(field)) 
            {
              String camp = field.javaName.toUpperCase();
              code.append("      CommonsMultipartFile " + field.javaName + " = ((" + form + ")target)." + CodeGenUtils.get(field)+ ";\n");
              code.append("      if (" + field.javaName + " == null || " + field.javaName + ".isEmpty()) {\n");
              code.append("        errors.rejectValue(get(" + camp + "), \"genapp.validation.required\",\n");
              code.append("          new String[]{ " + I18NUtils.class.getName() + ".tradueix(get(" + camp + ")) },\n");
              code.append("          null);\n");
              code.append("      }\n");
              code.append("\n");
            }
          }
        }
  
        code.append("    }\n");
      }
      
      code.append("    validator.validate(wvr, target,\n");
      code.append("      isNou");
      for (String tableName : ejbs) {        
        code.append(", " + CodeGenUtils.getModelName(tableName) + "Ejb");
      }      
      code.append(");\n");
      code.append("\n");

      /*
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
            code.append("    ValidationUtils.rejectIfEmptyOrWhitespace(errors, get(" + camp
                + "),\n");
            code.append("        \"genapp.validation.required\", new Object[] { I18NUtils.tradueix(get("
                + camp + ")) });\n");
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
          code.append("    if (errors.getFieldErrorCount(get(" + camp + ")) == 0) {\n");
          code.append("      " + cls + " __" + campLower + " = (" + cls
              + ")errors.getFieldValue(get(" + camp + "));\n");
          code.append("      if (__" + campLower + "!= null && __" + campLower + ".length() > " + field.size + ") {\n");
          code.append("        errors.rejectValue(get(" + camp
              + "), \"genapp.validation.sizeexceeds\",\n");
          code.append("            new String[]{ I18NUtils.tradueix(get(" + camp
              + ")), String.valueOf(" + field.size + ")},\n");
          code.append("            null);\n");
          code.append("      }\n");
          code.append("    }\n");
          code.append("    \n");
          
          // Mira si s'ha definit un patro
          String pattern = field.getMinAllowedValue();
          if (pattern != null) {
            code.append("    {\n");              
            code.append("      String val = String.valueOf(errors.getFieldValue(get(" + camp + ")));\n");
            code.append("      if (val != null && val.trim().length() != 0) {\n");
            code.append("        java.util.regex.Pattern p = java.util.regex.Pattern.compile(\"" + pattern.replace("\\", "\\\\") + "\");\n");
            code.append("        if (!p.matcher(val).matches()) {\n");
            code.append("          errors.rejectValue(get(" + camp + "), \"genapp.validation.malformed\",\n");
            code.append("             new String[]{val, I18NUtils.tradueix(get(" + camp  + "))},\n");
            code.append("             null);\n");
            code.append("        }\n");
            code.append("      }\n");
            code.append("    }\n");
            code.append("\n");
          }
          
        }
        
        if (field.getJavaType().isAssignableFrom(Number.class)
            || (field.getJavaType().isPrimitive() && !field.getJavaType().equals(Character.TYPE))) {
          // checkMinim
          code.append(checkNumberMaxMin(table, field, true)); 
          // checkMaxim
          code.append(checkNumberMaxMin(table, field, false));
        }
        
      }
      
      // CLAUS MULTIPLES 
      String[][] multipleUniques = table.getMultipleUniques();
      Map<String,List<DataGenUnique>> multipleUniquesList = new HashMap<String,List<DataGenUnique>>();
      if (multipleUniques != null && multipleUniques.length != 0) {
        
        ejbs.add(tableJavaName);
        
        for (int j = 0; j < multipleUniques.length; j++) {
          String[] dbFields = multipleUniques[j];
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

      code.append("    Boolean nou = (Boolean)errors.getFieldValue(\"nou\");\n");      
      code.append("    if (nou.booleanValue()) { // Creació\n");

      code.append("      // ================ CREATION\n");

      code.append("      // Fitxers \n");
      for (FieldInfo field : fields) {

        if (field.isNotNullable() && !field.isAutoIncrement()) {
          
          if (fileFields.contains(field)) {
            String camp = field.javaName.toUpperCase();
            code.append("      CommonsMultipartFile " + field.javaName + " = ((" + form + ")target)." + CodeGenUtils.get(field)+ ";\n");
            code.append("      if (" + field.javaName + " == null || " + field.javaName + ".isEmpty()) {\n");
            code.append("        errors.rejectValue(get(" + camp + "), \"genapp.validation.required\",\n");
            code.append("          new String[]{ I18NUtils.tradueix(get(" + camp + ")) },\n");
            code.append("          null);\n");
            code.append("      }\n");
            code.append("\n");
          }
        }
      }

      code.append("      // ====== Check Unique MULTIPLES - NOU =======\n\n");

      for (String title : multipleUniquesList.keySet()) {
        
        List<DataGenUnique> dataGenUnique = multipleUniquesList.get(title);
        code.append(title);
        code.append(genUniqueBlock(instanceEjb, dataGenUnique, null));  
      }
      
      
      
      

      code.append("      // Check Unique - no PK\n");
      for (FieldInfo field : fields) {
        if (field.isUnique() && !field.isPK) {
          ejbs.add(tableJavaName);
          code.append(genUniqueBlock(instanceEjb, new DataGenUnique(field), null));
        }
      }

      if (PKs.size() == 1) {
        code.append("      // Check Unique - PK no AutoIncrement amb UNA SOLA PK \n");
        for (FieldInfo field : fields) {
          if (field.isUnique() && field.isPK() && !field.isAutoIncrement()) {
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

        if (field.isUnique() && !field.isPK) {
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
          FieldInfo impField = CodeGenUtils.findFieldInfoByColumnSQLName(impTable, wfi
              .getForeignKey().getField());

          String jTableName = impTable.getNameJava();
          //code.append("      // FK Table Java: " + jTableName + "\n");
          String jFieldName = impField.getJavaName();
          //code.append("      // FK Field Java: " + jFieldName + "\n");

          ejbs.add(jTableName);
          final String impModel = CodeGenUtils.getModelName(jTableName);
          final String instanceLocal = impModel + "Ejb";

          String where = jTableName + "Fields." + jFieldName.toUpperCase() + ".equal("
              + campLower + ")";

          code.append("    if (errors.getFieldErrorCount(get(" + camp + ")) == 0) {\n");
          code.append("      " + cls + " " + campLower + " = (" + cls
              + ")errors.getFieldValue(get(" + camp + "));\n");
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
              + "); } catch(Exception e) {};\n");
          code.append(tab + "      if (__count_ == null || __count_ == 0) {        \n");
          // error.notfound=No s´ha trobat cap {0} amb {1} igual a {2}
          code.append(tab + "        errors.rejectValue(get(" + camp
              + "), \"error.notfound\",\n");
          code.append(tab + "          new String[]{ "
              +	"I18NUtils.tradueix(\"" + impModel + "." + impModel + "\"),"
              +	" I18NUtils.tradueix(\"" + impModel + "." + jFieldName + "\"),"
          		+ " String.valueOf(" + campLower + ")"
              + " },\n");
          code.append(tab + "          null);\n");
          code.append(tab + "      }\n");
          if (!queryField.isNotNullable) {
            code.append("      }\n");
          }
          code.append("    }\n");
          code.append("\n");

        }
      }
      
      */

      code.append("  } // Final de metode\n\n");
/*
      code.append("\n\n");
      code.append("  // EJB's\n");
      for (String tableName : ejbs) {
        final String ejb = tableName + "EJB";
        final String instance = CodeGenUtils.getModelName(tableName) + "Ejb";
        code.append("  @javax.ejb.EJB(mappedName = \""
            + project.getProjectName().toLowerCase() + "/" + ejb + "/local\")\n");
        code.append("  private " + ejbPackage + "." + tableName + "Local " + instance + ";\n");
        code.append("\n");
      }
      code.append("\n");

      code.append("  @Override\n");
      code.append("  public String getTableModelName() {\n");
      code.append("    return _TABLE_MODEL;\n");
      code.append("  }\n");
      code.append("  \n");
      code.append("}");
      */

      code.append("  public String get(Field<?> field) {\n");
      code.append("    return field.fullName;\n");
      code.append("  }\n");
      
      
      code.append("\n");
      code.append("  public " + logicValidator + "<Object> getValidator() {\n");
      code.append("    return validator;\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  public void setValidator(" + logicValidator + "<Object> validator) {\n");
      code.append("    this.validator = validator;\n");
      code.append("  }\n");
      
      
      
      code.append("\n");
      code.append("}");
      sourceFiles.add(new SourceFile(validator + ".java", code.toString()));

    }

    return sourceFiles;
  }

  protected static String checkNumberMaxMin(TableInfo table, FieldInfo field,
      boolean checkMinim) throws Exception {
    String valueStr = checkMinim? field.getMinAllowedValue() : field.getMaxAllowedValue();
    
    if (valueStr == null || valueStr.trim().length() == 0) {
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
    code.append("    if (errors.getFieldErrorCount(get(" + camp + ")) == 0) {\n");
    code.append("      " + cls + " __" + campLower + " = (" + cls
        + ")errors.getFieldValue(get(" + camp + "));\n");
    
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
    code.append("        errors.rejectValue(get(" + camp
        + "), \"" + errorMsgCode + "\",\n");
    code.append("            new String[]{ String.valueOf(__" + campLower + "), "
        + "I18NUtils.tradueix(get(" + camp + ")), String.valueOf(" + value + ") },\n");
    code.append("            null);\n");
    code.append("      }\n");
    code.append("    }\n");
    code.append("    \n");
      
    return code.toString();
  }

  public static List<SourceFile> generateForms(String packageForm,
      String packageFormBack, BasicPackages packages, String packageJPA, Project project) {
    
    TableInfo[] tables = project.getTables();
    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);
    
    TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
    

    for (int i = 0; i < tables.length; i++) {
      
      if (!tables[i].isGenerate()  || tables[i].isTranslationMapEntity()) {
        continue;
      }

      TableInfo table = tables[i];

      final String tableJavaName = table.getNameJava();
      final String model = CodeGenUtils.getModelName(tableJavaName);
      final String form = tableJavaName + "Form";
      final String jpa = tableJavaName + "JPA";
      
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }
      
      
      // Informació pels ComboBox dels camps referenciats
      StringBuffer refs = new StringBuffer();
      final String prefix = "listOf";
      final String refType = "List<StringKeyValue>";
      
      List<FieldInfo> transFields = CodeGenUtils.getTranslationFieldsOfTable(tables, table);
      List<String> refsCamps = generateReferencesForForm(project, table, fileTable, transFields, refs, prefix, refType);
      
      
      StringBuffer str = new StringBuffer();

      str.append("package " + packageFormBack + ";\n");
      str.append("\n");
      
      // Imports per les References
      if (refs.length() != 0) {
        str.append("import java.util.List;\n");
        str.append("import " + StringKeyValue.class.getName() + ";\n");
      }

      if (fileFields.size() != 0) {
        str.append("import org.springframework.web.multipart.commons.CommonsMultipartFile;\n");
      }
      
      str.append("import " +  packageForm + "." + project.projectName + "BaseForm;\n");

      str.append("import " + packageJPA + "." + jpa + ";\n");
      str.append("\n");
      str.append("/**\n");
      str.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      str.append(" * Created by GenApp. Do not modify.\n");
      str.append(" */\n");
      str.append("public class " + form + " extends " + project.projectName + "BaseForm {\n");
      //str.append("  \n");
      //str.append("  protected final Log log = LogFactory.getLog(getClass());\n");
      str.append("  \n");
      str.append("  private " + jpa + " " + model + ";\n");
      str.append("  \n");

      //str.append("  private boolean nou;\n");
      //str.append("  \n");

      for (FieldInfo fileField : fileFields) {
        String field = fileField.getJavaName();
        str.append("  \n");
        str.append("  private CommonsMultipartFile " + field + ";\n");
        str.append("  private boolean " + field + "Delete;\n");
        str.append("  \n");
      }

      str.append("  public " + form + "() {\n");
      str.append("  }\n");
      str.append("  \n");
      
      str.append("  public " + form + "(" + form + " __toClone) {\n");
      
      str.append("    super(__toClone);\n");
      
      str.append("      this." + model + " = __toClone." + model + ";\n"); 
      for (String ref : refsCamps) {
        str.append("    this." + ref + " = __toClone." + ref + ";\n"); 
      }
      str.append("  }\n");
      str.append("  \n");
      

      str.append("  public " + form + "(" + jpa + " " + model + ", boolean nou) {\n");
      str.append("    super(nou);\n");
      str.append("    this." + model + " = " + model + ";\n");      
      str.append("  }\n");
      str.append("  \n");

      str.append("  public " + jpa + " get" + tableJavaName + "() {\n");
      str.append("    return " + model + ";\n");
      str.append("  }\n");

      str.append("  public void set" + tableJavaName + "(" + jpa + " " + model + ") {\n");
      str.append("    this." + model + " = " + model + ";\n");
      str.append("  }\n");
/*
      str.append("  public void setNou(boolean nou) {\n");
      str.append("    this.nou = nou;\n");
      str.append("  }\n");

      str.append("  public boolean isNou() {\n");
      str.append("    return nou;\n");
      str.append("  }\n");
*/
      str.append("  \n");

      if (transFields != null && transFields.size() != 0) {
        str.append("  java.util.List<" + packages.entityPackage +".Idioma> idiomesTraduccio = null;\n");
        str.append("\n");
        str.append("  public java.util.List<" + packages.entityPackage +".Idioma> getIdiomesTraduccio() {\n");
        str.append("    return idiomesTraduccio;\n");
        str.append("  }\n");
        str.append("\n");
        str.append("  public void setIdiomesTraduccio(java.util.List<" + packages.entityPackage +".Idioma> idiomesTraduccio) {\n");
        str.append("    this.idiomesTraduccio = idiomesTraduccio;\n");
        str.append("  }\n");
      }

      str.append("  \n");
      

      for (FieldInfo fileField : fileFields) {

        String field = fileField.getJavaName();
        String get = CodeGenUtils.get(fileField);
        String is = GenAppUtils.getOnlyName(fileField, "is");
        String set = CodeGenUtils.set(fileField.getJavaName());

        str.append("  public CommonsMultipartFile " + get + " {\n");
        str.append("    return " + field + ";\n");
        str.append("  }\n");
        str.append("  \n");

        str.append("   public void " + set + "(CommonsMultipartFile " + field + ") {\n");
        str.append("    this." + field + " = " + field + ";\n");
        str.append("  }\n");

        str.append("  public boolean " + is + "Delete() {\n");
        str.append("    return " + field + "Delete;\n");
        str.append("  }\n");
        str.append("  \n");

        str.append("  public void " + set + "Delete(boolean " + field + "Delete) {\n");
        str.append("    this." + field + "Delete = " + field + "Delete;\n");
        str.append("   }\n");
      }
      
      str.append(refs.toString());
      
      
      str.append("  \n");
      str.append("} // Final de Classe \n");

      sourceFiles.add(new SourceFile(form + ".java", str.toString()));

    }

    return sourceFiles;
  }

  public static List<String> generateReferencesForForm(Project project, TableInfo table,
      TableInfo fileTable, List<FieldInfo> transFields, StringBuffer refs, final String prefix, final String refType) {
    
    List<String> getSet = new ArrayList<String>();
    //Set<String> generateds = new HashSet<String>();  
    for (FieldInfo field : table.getFields()) {
      

    
      WebFieldInfo wfi = field.getWebFieldInfo(); 
      
      
      if (wfi.getWebtype() == WebType.Query || wfi.getWebtype() == WebType.ComboBox) {
       
        // Si és fitxer omitim
        if (fileTable!=null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
          continue;
        }
      
      
        TableInfo fkTable = null;
        if (wfi.getWebtype() == WebType.Query) {
          ForeignKey fk =field.getWebFieldInfo().getForeignKey();
         
          fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
        }
        // Generam una llista per cada tipus QUERY
        String name = generateReferenceMethodName(prefix, field, fkTable);
        refs.append(CodeGenUtils.generateGetSetBean(name , refType, true));
        
        getSet.add(name);
        
        refs.append("\n");
      }
    }
    return getSet;
    
  }

  protected static String generateReferenceMethodName(final String prefix, FieldInfo field,
      TableInfo fkTable) {
    String name;
    
    String fkTableName = (fkTable == null) ? "Values" : fkTable.getNameJava();
    
    name = prefix + fkTableName + "For" + Character.toUpperCase(field.javaName.charAt(0))
    + field.javaName.substring(1);
    return name;
  }

  public static List<SourceFile> generateFilterForms(
      String packageForm, String packageFormBack, String jpaPackage,
      String ejbPackage, BasicPackages packages, TableInfo[] tables,
      Project project) throws Exception {

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);
    
    TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
    
    TableInfo transTable = CodeGenUtils.getSqlTableTranslation(tables);

    for (int i = 0; i < tables.length; i++) {
      
      if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity() ) {
        continue;
      }

      TableInfo table = tables[i];

      

      final String tableJavaName = table.getNameJava();
      final String model = CodeGenUtils.getModelName(tableJavaName);
      final String form = tableJavaName + "FilterForm";
      final String local = tableJavaName + "Local";
      

      final String fieldsClass = tableJavaName + "Fields";
      // List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables,
      // table);
      
      // Informació pels ComboBox dels camps referenciats
      StringBuffer refs = new StringBuffer();
      final String prefix = "mapOf";
      final String refType = "Map<String, String>";
      List<FieldInfo> transFields = CodeGenUtils.getTranslationFieldsOfTable(tables, table);
      List<String> refsCamps = generateReferencesForForm(project, table, fileTable, transFields, refs, prefix, refType);
      

      StringBuffer code = new StringBuffer();
      code.append("\n");
      code.append("package " + packageFormBack + ";\n");
      code.append("\n");
      code.append("import java.util.Arrays;\n");
      code.append("import java.util.List;\n");
      
   // Imports per les References
      if (refs.length() != 0) {
        code.append("import java.util.Map;\n");
      }
      
     
      code.append("\n");
      code.append("import org.springframework.stereotype.Component;\n");
      code.append("\n");
      code.append("import " + Field.class.getName() + ";\n");
      code.append("import " + OrderBy.class.getName() + ";\n");
      code.append("import " + packageForm + "." + project.projectName + "BaseFilterForm;\n");
      code.append("\n");
      code.append("import " + packages.fieldsPackage + "." + fieldsClass + ";\n");
      code.append("\n");
      code.append("/**\n");
      code.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      code.append(" * @author GenApp\n");      
      code.append(" * \n");
      code.append(" */\n");
      code.append("@Component\n");
      code.append("public class " + form + " extends " 
          + project.projectName + "BaseFilterForm implements " + fieldsClass + " {\n");
      code.append("\n");
      
  
      
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }
      

      StringBuffer nomsFilter = new StringBuffer();
      StringBuffer nomsGroup = new StringBuffer();
      StringBuffer orderBy = new StringBuffer();
      List<FieldInfo> referenciesTraduibles = new ArrayList<FieldInfo>();
      
      List<String> createdFields = new ArrayList<String>();
      
      for (FieldInfo field : table.getFields()) {
       
        // Informacio per GroupBy per saber si ha de traduir la referència o no
        WebFieldInfo wfi = field.getWebFieldInfo(); 
        if (wfi.getWebtype() == WebType.Query || wfi.getWebtype() == WebType.ComboBox) {
          // Si és fitxer omitim
          if (fileTable!= null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
            // No fer res si es fitxer
          } else {
            boolean tradueix;
            
            if (wfi.getWebtype() == WebType.Query) {
              ForeignKey fk =wfi.getForeignKey();
                        
              TableInfo fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
              tradueix = fillReferences(fkTable, new StringBuffer());
            } else {
              tradueix = field.traduible;
            }

            if (tradueix) {
              referenciesTraduibles.add(field);
            }
          }
        }

        int order = field.getOrderBy();
        if (order != 0) {
          // TODO ORDRE !!!!!
          if (orderBy.length() != 0) {
            orderBy.append(" , ");
          }
          if (order > 0) {
            orderBy.append("new OrderBy(" + field.getJavaName().toUpperCase()+  " )");
          } else {
            orderBy.append("new OrderBy(" + field.getJavaName().toUpperCase()
                +  ", " + OrderType.class.getName() + ".DESC )");
          }
        }
        
        
        if (field.isGroupBy()) {
          if (nomsGroup.length() != 0) {
            nomsGroup.append(" ,");
          }
          nomsGroup.append(field.getJavaName().toUpperCase());
        }
        
        
        //if (!field.isFilter()) {
        //  continue;
        //}
        
        
        Class<?> type = field.getJavaType();
        // Ignoram boolean en filtre
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
          continue;
        }
        
        // ignoram fitxers
        if (fileFields.contains(field)) {         
          continue;
        }
        
        if (field.isFilter()) {      
          if (nomsFilter.length() != 0) {
            nomsFilter.append(" ,");
          }
          nomsFilter.append(field.getJavaName());
        }

        if (type.equals(String.class)) {
          //code.append("  // FILTER STRING \n");
          
          createdFields.add(field.getJavaName());
          
          code.append(CodeGenUtils.generateGetSetBean(new FieldInfo(field.getJavaName(), field.getJavaType())));
        } else {
          
          if (type.isPrimitive()) {
            type = SQL2Java.primitiveType2javaClassName(type);
          }

          String name;
          
          name = field.getJavaName() + "Desde";
          createdFields.add(name);
          
          code.append(CodeGenUtils.generateGetSetBean(new FieldInfo(name, type)));
          
          name = field.getJavaName() + "Fins";
          createdFields.add(name);
          
          code.append(CodeGenUtils.generateGetSetBean(new FieldInfo(name , type)));

        }

      }
      
      
      code.append("  public " + form + "() {\n");
      code.append("  }\n");
      code.append("  \n");
      
      code.append("  public " + form + "(" + form + " __toClone) {\n");
      
      code.append("    super(__toClone);\n");
      // camps de filter
      for (String camp : createdFields) {
        code.append("    this." + camp + " = __toClone." + camp + ";\n");
      }
      // camps de referencies
      for (String ref : refsCamps) {
        code.append("    this." + ref + " = __toClone." + ref + ";\n"); 
      }
      code.append("  }\n");
      code.append("  \n");
      
      
      

      code.append("  /* ========= UTILS ========== */\n");
      code.append("\n");
      code.append("  @Override\n");
      code.append("  public List<Field<?>> getDefaultFilterByFields() {\n");
      code.append("    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { " + nomsFilter.toString().toUpperCase() + " }));\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  @Override\n");
      code.append("  public List<Field<?>> getDefaultGroupByFields() {\n");
      code.append("    return new java.util.ArrayList<Field<?>>(Arrays.asList(new Field<?>[] { " + nomsGroup + " }));\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");
      code.append("  protected OrderBy[] defaultOrderBy = ");
      if (orderBy.toString().length() == 0) {
        code.append("null;\n");
      } else {
        code.append("new OrderBy[] {" + orderBy.toString() + "};\n");
      }
      code.append("\n\n");
      
      code.append("  public OrderBy[] getDefaultOrderBy() {\n");
      code.append("    return this.defaultOrderBy;\n");
      code.append("  }\n");
      code.append("\n");
      

      code.append("  public void setDefaultOrderBy(OrderBy[] defOrderBy) {\n");
      code.append("    this.defaultOrderBy = defOrderBy;\n");
      code.append("  }\n");
      code.append("\n");
      /*
      code.append("  @Override\n");
      code.append("  public ITableManager<?, ?> getTableManager() {\n");
      code.append("    return " + instanceEjb + ";\n");
      code.append("  }\n");
      code.append("\n");
      */
      code.append("  @Override\n");
      code.append("  public String getTableModelName() {\n");
      code.append("    return _TABLE_MODEL;\n");
      code.append("  }\n");
      code.append("\n");
      
      
      // FALTA FER SEGONS VALORS DE REFERENCE
      // FOREIGN KEY???? ,
      StringBuffer refSelects = new StringBuffer(); 
      boolean traduible = fillReferences(table, refSelects);

      
      if (refSelects.length() == 0) {
        throw new Exception("No s'ha definit cap camp de referencia en la taula " + table.nameJava);
      }

      
      
      code.append("   // -----------------------\n");
      code.append("   // Maps de referencies.\n");
      code.append("   // -----------------------\n");
      code.append(refs.toString());
      code.append("\n");
      
      code.append("   // --------------------------------\n");
      code.append("   // Camps traduibles de referencies.\n");
      code.append("   // ---------------------------------\n");
      code.append("   public static final List<String> traduibles;\n\n");
      code.append("   static {\n");      
      //referenciesTraduibles
      code.append("     traduibles = new java.util.ArrayList<String>();\n");
      for (FieldInfo fieldInfo : referenciesTraduibles) {
        code.append("   traduibles.add(" + fieldInfo.javaName.toUpperCase() + ".javaName);\n");  
      }
      code.append("   };\n");

      code.append("\n");
      code.append("}\n");
      sourceFiles.add(new SourceFile(form + ".java", code.toString()));

      final String refList = tableJavaName + "RefList";
      StringBuffer codeRefList = new StringBuffer();
      {
      
      
      
      final String instanceEjb = model + "Ejb";
      
      
      codeRefList.append("\n");
      codeRefList.append("package " + packageFormBack + ";\n");
      
      codeRefList.append("\n");
      
      codeRefList.append("import java.util.List;\n");
      codeRefList.append("import javax.ejb.EJB;\n");      
      
      codeRefList.append("import org.springframework.stereotype.Component;\n");
      codeRefList.append("\n");
      
      codeRefList.append("import " + GenAppPackages.PKG_BASE + ".common.StringKeyValue;\n");
      codeRefList.append("import " + Field.class.getName() + ";\n");
      codeRefList.append("import " + GenAppPackages.PKG_BASE + ".common.query.OrderBy;\n");
      codeRefList.append("import " + GenAppPackages.PKG_BASE + ".common.query.Select;\n");
      codeRefList.append("import " + GenAppPackages.PKG_BASE + ".common.query.Where;\n");
      codeRefList.append("\n");
      codeRefList.append("import " + ejbPackage + "." + local + ";\n");
      if (transFields != null && transFields.size() != 0) {
        codeRefList.append("import " + ejbPackage + ".TraduccioLocal;\n");
      }
      codeRefList.append("import " + I18NException.class.getName() + ";\n");
      codeRefList.append("import " + packages.fieldsPackage + "." + fieldsClass + ";\n");
      codeRefList.append("import " + GenAppPackages.PKG_BASE + ".common.web.controller.RefListBase;\n");
      codeRefList.append("\n");
      codeRefList.append("/**\n");
      codeRefList.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      codeRefList.append(" * @author GenApp\n");      
      codeRefList.append(" * \n");
      codeRefList.append(" */\n");
      codeRefList.append("@Component\n");
      
      codeRefList.append("public class " + refList + " extends RefListBase\n    implements " + fieldsClass + " {\n");
      codeRefList.append("\n");
      codeRefList.append("  @EJB(mappedName = " + local + ".JNDI_NAME)\n");
      codeRefList.append("  private " + local + " " + instanceEjb + ";\n");
      codeRefList.append("\n");
      
      
      if (transFields != null && transFields.size() != 0) {
          // TODO TraducioLocal llegir-ho de CodeGenutils
        codeRefList.append("  @EJB(mappedName = TraduccioLocal.JNDI_NAME)\n");
        codeRefList.append("  private TraduccioLocal traduccioEjb;\n");
      }
      
      
      codeRefList.append("  public " + refList + "(" + refList + " __clone) {\n");
      codeRefList.append("    super(__clone);\n");
      codeRefList.append("    this." + instanceEjb + " = __clone." + instanceEjb + ";\n");
      if (transFields != null && transFields.size() != 0) {
        codeRefList.append("    this.traduccioEjb = __clone.traduccioEjb;\n");
      }
      codeRefList.append("  }\n");
      
      codeRefList.append("  public " + refList + "() {\n");
      codeRefList.append("    setSelects(new Select<?>[] { " + refSelects.toString() + " });\n");
      if (transFields != null && transFields.size() != 0) {
        for (FieldInfo info : transFields) {
          codeRefList.append("    addCampTraduible(" + info.getJavaName().toUpperCase()+ ".select);\n");  
        }
      }
      
      // TODO Com obtenir l'ordre ?? 
      //codeRefList.append("    setOrderBy(" + form + ".DEFAULT_ORDER_BY);\n");
      codeRefList.append("  }\n");
      
      codeRefList.append("  public List<StringKeyValue> getReferenceList(Field<?> keyField, Where where, OrderBy ... orderBy) throws I18NException {\n");
      // TODO ReferencedFieldSelect a GenAPP
      
      boolean isTaulaTraduccio = (transTable == null)? false : table.getName().equals(transTable.getName());
      
      if (!isTaulaTraduccio) {
      
        if (transFields != null && transFields.size() != 0) {
          codeRefList.append("    Select<Long> _transSelect = checkTranslationFields();\n");
        }
        
        
        
        
        codeRefList.append("    Select<StringKeyValue> select =  new " + GenAppPackages.PKG_BASE + ".common.query.SelectMultipleStringKeyValue(keyField.select, getSeparator(), getSelects());\n");
        codeRefList.append("    List<StringKeyValue> list = " + instanceEjb + ".executeQuery(select, where, (orderBy==null || orderBy.length == 0) ? getOrderBy() : orderBy);\n");
        if (traduible) {
          codeRefList.append("    for (StringKeyValue skv : list) {\n");
          codeRefList.append("      skv.setValue(" + I18NUtils.class.getName() + ".tradueix(skv.getValue()));\n");
          codeRefList.append("    }\n");
        }
        
        if (transFields == null || transFields.size() == 0) {
          codeRefList.append("    return list;\n");
        }
      }
      
      
      
      if (isTaulaTraduccio || (transFields != null && transFields.size() != 0)) {

        if (!isTaulaTraduccio) {
        codeRefList.append("    if (_transSelect == null) {\n");
        codeRefList.append("      return list;\n");
        codeRefList.append("    }\n");
        codeRefList.append("    // key => TransID | value => " +instanceEjb + "_PK\n");
        codeRefList.append("    java.util.Map<String,String> keysMap = " + Utils.class.getName() + ".listToMapInverse(list);\n");
        codeRefList.append("    " + Where.class.getName()  +" _w1 = " + packages.fieldsPackage + ".TraduccioFields.TRADUCCIOID.in(" + instanceEjb + ".executeQuery(_transSelect, where));\n");
        }
        
        codeRefList.append("    List<" + packages.entityPackage + ".Traduccio> traduccions = traduccioEjb.select(" + (isTaulaTraduccio?"where": "_w1") + ");\n");
        codeRefList.append("    List<StringKeyValue> _list = new java.util.ArrayList<StringKeyValue>(traduccions.size());\n");
        codeRefList.append("    final String _lang = " + I18NUtils.class.getName() + ".getLocale().getLanguage();\n");
        codeRefList.append("    for (" + packages.entityPackage + ".Traduccio traduccio : traduccions) {\n");
        codeRefList.append("      " + jpaPackage + ".TraduccioJPA traduccioJPA = (" + jpaPackage + ".TraduccioJPA) traduccio;\n");
        if (isTaulaTraduccio) {
          codeRefList.append("      String key = String.valueOf(traduccioJPA.getTraduccioID());\n");
        } else {
          codeRefList.append("      String key = keysMap.get(String.valueOf(traduccioJPA.getTraduccioID()));\n");
        }
        codeRefList.append("      String value = traduccioJPA.getTraduccio(_lang).getValor();\n");
        codeRefList.append("      StringKeyValue skv = new StringKeyValue(key, value);\n");
        codeRefList.append("      _list.add(skv);\n");
        codeRefList.append("    }\n");
        codeRefList.append("    java.util.Collections.sort(_list, new " + KeyValue.class.getName() + ".KeyValueComparator<String>());\n");
        codeRefList.append("    return _list;\n");
        codeRefList.append("\n");

      }

      codeRefList.append("  }\n");
      
      codeRefList.append("}\n");
      }
      
      sourceFiles.add(new SourceFile(refList + ".java", codeRefList.toString()));
      

    }
    return sourceFiles;
  }

  public static boolean fillReferences(TableInfo table, StringBuffer refSelects) {
    Boolean traduible = null;
    for (FieldInfo field : table.getFields()) {
      if(field.isShowInReferenceList()) {
        if (refSelects.length() != 0) {
          refSelects.append(", ");
        }
        refSelects.append(field.javaName.toUpperCase() + ".select");
        if (field.isTraduible()) {
          if (traduible == null) {
            traduible = true;
          } else {
            //throw new Exception("En la taula " + table + " )
            //traduible = false;
          }
        }
      }
    }
    return (traduible == null)? false : traduible;
  }

  
  
  public static List<SourceFile> generateControllers( String packageControllerBase,
      String packageControllerBack,
      String packageFormBack, String packageValidatorBack,String jpaPackage,
      String ejbPackage, BasicPackages packages, Project project,
      Map<String,BeanCode> beanCodeBySqlTableName) throws Exception {
    
    TableInfo[] tables = project.getTables();

    List<SourceFile> sourceFiles = new ArrayList<SourceFile>(tables.length);

    for (int i = 0; i < tables.length; i++) {

      TableInfo table = tables[i];
      if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity() ) {
        continue;
      }

      final String tableJavaName = table.getNameJava();
      
      final String model = CodeGenUtils.getModelName(tableJavaName);
      final String jpa = tableJavaName + "JPA";
      final String form = tableJavaName + "Form";
      final String instanceForm = CodeGenUtils.getModelName(form);
      //final String tileForm = CodeGenUtils.getModelName(form) + "WebDB";
      //final String tileList = CodeGenUtils.getModelName(form) + "WebDB";
      
      final String filterForm = tableJavaName + "FilterForm";
      final String instanceFilterForm = CodeGenUtils.getModelName(filterForm);
      
      final String refList = tableJavaName + "RefList";
      final String instanceRefList = CodeGenUtils.getModelName(refList);

      final String local = tableJavaName + "Local";
      final String instanceEjb = model + "Ejb";
      final String validator = tableJavaName + "WebValidator";
      final String instanceValidator = CodeGenUtils.getModelName(validator);
      final String fieldsClass = tableJavaName + "Fields";
      final String controller = tableJavaName + "Controller";
      
      
      
      FieldInfo[] fields = table.getFields();
      
      //BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);
      
      List<FieldInfo> PKs = CodeGenUtils.getPKs(table.getFields());
      final boolean multiplePKs = (PKs.size() > 1);
      final String pkClass;
      final String pkClassInstance;
      final String pkFromModel;
      String pkParams;
      String pkParamsSense;
      String pkMapping;
      String pkFromParams;
      String argNames = "";
      String argValues = "";
      if (multiplePKs) {
        pkClass = tableJavaName + "PK";
        pkClassInstance = CodeGenUtils.getModelName(pkClass);
        StringBuffer tmp = new StringBuffer();
        pkMapping = "";
        pkParams = "";
        pkParamsSense = "";
        pkFromParams = "";
        
        for (FieldInfo fieldInfo : PKs) {
          if (tmp.length() != 0) {
            tmp.append(" , ");
            pkParams = pkParams + " , ";
            pkParamsSense = pkParamsSense + ",";
            pkFromParams = pkFromParams + " , ";
            argNames = argNames + " + \" , \" + ";
            argValues = argValues + " + \" , \" + ";
          }
          String map = CodeGenUtils.getModelName(fieldInfo.getJavaName());
          pkMapping = pkMapping + " /{" + map + "}";
          pkParamsSense = pkParamsSense + fieldInfo.getJavaType().getName() + " " + map;
          pkParams = pkParams + "@PathVariable(\"" + map + "\") " 
             + fieldInfo.getJavaType().getName() + " " + map;
          pkFromParams = pkFromParams + map;
          
          argNames = argNames + " I18NUtils.tradueix(\"" + model + "." + fieldInfo.getJavaName() + "\")";
          argValues = argValues + " String.valueOf(" + pkClassInstance + "." + CodeGenUtils.get(fieldInfo) + ")";
          tmp.append(model + "." + CodeGenUtils.getOnlyName(fieldInfo.getJavaName()) + "()");
        }
        pkFromModel = " new " + pkClass + "(" +  tmp.toString() + ")";
        pkFromParams =  " new " + pkClass + "(" +  pkFromParams + ")";

        argNames = " \"(\" + " + argNames  + "  + \")\"";
        argValues = " \"(\" + " + argValues  + "  + \")\",\n";
        
      } else {
        FieldInfo pk = PKs.get(0);
        if (pk.getJavaType().isPrimitive()) {
          pkClass = SQL2Java.primitiveType2javaClassName(pk.getJavaType()).getName();
        } else {
          pkClass = pk.getJavaType().getName();
        }
        pkClassInstance = CodeGenUtils.getModelName(pk.getJavaName());
        
        pkFromModel = model + "." + CodeGenUtils.getOnlyName(pk.getJavaName()) + "()";
        pkMapping = "/{" + pkClassInstance + "}";
        pkParamsSense = pkClass + " " + pkClassInstance;
        pkParams = "@PathVariable(\"" + pkClassInstance + "\") " + pkParamsSense;
        pkFromParams = pkClassInstance;
        argNames = " I18NUtils.tradueix(\"" + model + "." + pk.getJavaName() + "\")";
        argValues = " String.valueOf(" + pkClassInstance + "),\n";
      }
      
      
      
      TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, table);
      if (fileFields == null) {
        fileFields = new ArrayList<FieldInfo>();
      }
      
      
      TableInfo translateTable = CodeGenUtils.getSqlTableTranslation(tables);
      
      
      List<FieldInfo> transFields = CodeGenUtils.getTranslationFieldsOfTable(tables, table);
      
      StringBuffer codeTmp = new StringBuffer();
      
      StringBuffer codeExportList = new StringBuffer();
      
      boolean needTmpMap = false;

      for (FieldInfo field : fields) {
        WebFieldInfo wfi = field.getWebFieldInfo(); 
        
        
        
        Class<?> type = field.getJavaType(); 
        if (type.equals(Boolean.class) || type.equals(Boolean.TYPE)) {
          codeTmp.append("\n");
          String prefix = field.getMinAllowedValue();
          
          if (prefix == null || prefix.trim().length() == 0) {
            prefix = "genapp.checkbox";
          }

          codeTmp.append("      fillValuesToGroupByItemsBoolean(\"" + prefix + "\", groupByItemsMap, " + field.javaName.toUpperCase()
              /* + "," + filterForm + ".traduibles);\n"); */
              + ");\n");
          codeTmp.append("\n");
          continue;
        }

        if ( !(wfi.getWebtype() == WebType.Query || wfi.getWebtype() == WebType.ComboBox)) {
          continue;
        }
        
        // Si és fitxer omitim
        if (fileTable!= null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
          continue;
        }
        
        needTmpMap = true;
        TableInfo fkTable = null;
        if (wfi.getWebtype() == WebType.Query) { 
          ForeignKey fk =field.getWebFieldInfo().getForeignKey();
          fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
        }
        codeTmp.append("    // Field " + field.javaName + "\n");
        String name = Character.toUpperCase(field.javaName.charAt(0))
            + field.javaName.substring(1);
        //codeTmp.append("    // TODO Fer un if de si ja estat definit, no tornar-ho definir\n");
        //final String prefixGet = "getMapOf";
        //final String getmethod = generateReferenceMethodName(prefixGet, field, fkTable); 
        //codeTmp.append("    // Comprovam si ja esta definit el MAP\n");
        //codeTmp.append("    tmp = " + instanceFilterForm + "." + getmethod + "();\n");
        //codeTmp.append("    if (tmp == null) {\n");
        codeTmp.append("    {\n");
        codeTmp.append("      _listSKV = getReferenceListFor"+ name + "(request, mav, filterForm, list, groupByItemsMap, null);\n");
        codeTmp.append("      _tmp = Utils.listToMap(_listSKV);\n");
        final String prefixSet = "setMapOf";
        final String setmethod = generateReferenceMethodName(prefixSet, field, fkTable); 
        
        codeExportList.append("    __mapping.put(" + field.getJavaName().toUpperCase() + ", filterForm." + generateReferenceMethodName("getMapOf", field, fkTable) + "());\n");
        
        codeTmp.append("      filterForm." + setmethod + "(_tmp);\n");
        codeTmp.append("      if (filterForm.getGroupByFields().contains(" + field.javaName.toUpperCase() + ")) {\n");
        codeTmp.append("        fillValuesToGroupByItems(_tmp, groupByItemsMap, " + field.javaName.toUpperCase()
            + ", false);\n");
        codeTmp.append("      };\n");
//            + "," + filterForm + ".traduibles);\n");
       
        codeTmp.append("    }\n\n");
        
      }

      StringBuffer code = new StringBuffer();
    
      code.append("package " + packageControllerBack + ";\n");
      code.append("\n");
      if (needTmpMap) {
        code.append("import " + StringKeyValue.class.getName() + ";\n");
        code.append("import " + Utils.class.getName() + ";\n");
      }

      code.append("import " + I18NUtils.class.getName() + ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.i18n.I18NException;\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.query.GroupByItem;\n");
      code.append("import " + Field.class.getName() + ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.query.Where;\n");
      code.append("import " + I18NValidationException.class.getCanonicalName() + ";\n");
      code.append("import " + GenAppPackages.PKG_BASE + ".common.web.validation.ValidationWebUtils;\n");
      code.append("\n");
      code.append("import org.springframework.beans.factory.annotation.Autowired;\n");
      code.append("import org.springframework.core.annotation.AnnotationUtils;\n");
      code.append("import org.springframework.stereotype.Controller;\n");
      code.append("import org.springframework.validation.BindingResult;\n");
      code.append("import org.springframework.web.bind.WebDataBinder;\n");
      code.append("import org.springframework.web.bind.annotation.*;\n");
      code.append("import org.springframework.web.bind.support.SessionStatus;\n");
      code.append("import org.springframework.web.servlet.ModelAndView;\n");
      code.append("import org.springframework.web.servlet.view.RedirectView;\n");
      code.append("\n");
      code.append("import javax.ejb.EJB;\n");
      code.append("import javax.servlet.http.HttpServletRequest;\n");
      code.append("import javax.servlet.http.HttpServletResponse;\n");
      code.append("import javax.validation.Valid;\n");
      code.append("\n");
      code.append("import java.util.List;\n");
      code.append("import java.util.Map;\n");
      code.append("import java.util.HashMap;\n");

     
      code.append("\n");
      if (multiplePKs) {
        code.append("import " + packages.daoPackage + "." + pkClass+ ";\n");
      }

      code.append("import " + packageFormBack + ".*;\n");
      code.append("import " + packageFormBack + "." + form + ";\n");
      code.append("\n");
      
      code.append("import " + packageValidatorBack + "." + validator + ";\n");
      code.append("\n");
      
      String derivatDe;
      if (fileFields.size() == 0) {
        // NO TE FITXERS
        // TODO 
        derivatDe = packageControllerBase + "." + project.getProjectName() + "BaseController";
        
      } else {
        // TE FITXERS
        // TODO
        code.append("import " + packages.entityPackage + "." + fileTable.getNameJava() + ";\n");
        code.append("import " + jpaPackage + "." + fileTable.getNameJava() + "JPA;\n");
        code.append("import " + GenAppPackages.PKG_BASE + ".common.web.controller.FilesFormManager;\n");
        
        derivatDe = packageControllerBase + "." + project.getProjectName() + "FilesBaseController";
        /*
        for (FieldInfo fieldInfo : fileFields) {
          if (fieldInfo.isNotNullable) {
            code.append("import org.springframework.validation.ObjectError;\n");
            break;
          }
        }
        */
        
        
      }
      
      code.append("import " + jpaPackage + "." + jpa + ";\n");
      code.append("import " + packages.entityPackage + "." + tableJavaName + ";\n");
      //code.append("import " + packages.fieldsPackage + "." + fieldsClass + ";\n");
      code.append("import " + packages.fieldsPackage + ".*;\n");
      code.append("\n");
      code.append("/**\n");
      code.append(" * Controller per gestionar un " + tableJavaName + "\n");
      code.append(" *  ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!! \n");
      code.append(" * \n");
      code.append(" * @author GenApp\n");
      code.append(" */\n");
      code.append("@Controller\n");
      code.append("@RequestMapping(value = \"/webdb/" + model + "\")\n");
      code.append("@SessionAttributes(types = { " + form + ".class, " + filterForm + ".class })\n");
      code.append("public class " + controller + "\n");
      if (fileFields.size() != 0) {
        code.append("    extends " + derivatDe + "<" + tableJavaName + ", " + pkClass +", " + form + ">");
      } else {
        code.append("    extends " + derivatDe + "<" + tableJavaName + ", " + pkClass +">");
      }
      code.append(" implements " + fieldsClass + " {\n");
      
      code.append("\n");
      
      // TODO reeemplaçar Idioma per la taula de mapping d'idioma
      if (transFields != null && transFields.size() != 0  && !"Idioma".equals(table.getNameJava())) {
        code.append("  @EJB(mappedName = " + ejbPackage+ ".IdiomaLocal.JNDI_NAME)\n");
        code.append("  protected " + ejbPackage+ ".IdiomaLocal idiomaEjb;\n");
        code.append("\n");
      }

      
      //code.append("  @EJB(mappedName = \"" + project.getProjectName().toLowerCase() + "/" + tableJavaName + "EJB/local\")\n");
      code.append("  @EJB(mappedName = " + ejbPackage + "." + local + ".JNDI_NAME)\n");
      code.append("  protected " + ejbPackage + "." + local + " " + instanceEjb + ";\n");
      code.append("\n");
      code.append("  @Autowired\n");
      code.append("  private " + validator + " " + instanceValidator + ";\n");
      code.append("\n");
      
      code.append("  @Autowired\n");
      code.append("  protected " + refList + " " + instanceRefList + ";\n");
      
      //code.append("  @Autowired\n");
      //code.append("  protected " + filterForm + " " + instanceFilterForm + ";\n");
      code.append("\n");
      
      
      
      // Aqui van les variables de RefList per poder omplir 
      // els combobox dels camps que són referències a altres taules 
      {
        Set<String> generateds = new HashSet<String>();
        generateds.add(table.name); // igonram la taula actual
        for (FieldInfo field : fields) {
          WebFieldInfo wfi = field.getWebFieldInfo(); 
          if (wfi.getWebtype() != WebType.Query) { // No necessitam ref list per combo box
            continue;
          }
          // Si és fitxer omitim
          if (fileTable != null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
            continue;
          }
          ForeignKey fk =field.getWebFieldInfo().getForeignKey();
          if (generateds.contains(fk.getTable())) {
            continue;
          }
          generateds.add(fk.getTable());
          
          TableInfo fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());

          code.append("  // References \n");
          code.append("  @Autowired\n");
          code.append("  protected " + fkTable.getNameJava() + "RefList "
              + CodeGenUtils.getModelName(fkTable.getNameJava()) + "RefList;\n");
          code.append("\n");
        }
      }
      
      
      
      code.append("  /**\n");
      code.append("   * Llistat de totes " + tableJavaName + "\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"/list\", method = RequestMethod.GET)\n");
      code.append("  public String llistat(HttpServletRequest request,\n");
      code.append("    HttpServletResponse response) throws I18NException {\n");
      code.append("    " + filterForm + " ff;\n");
      code.append("    ff = (" + filterForm + ") request.getSession().getAttribute(getSessionAttributeFilterForm());\n");
      code.append("    int pagina = (ff == null)? 1: ff.getPage();\n");
      code.append("    return \"redirect:\" + getContextWeb() + \"/list/\" + pagina;\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  /**\n");
      code.append("   * Primera peticio per llistar " + tableJavaName + " de forma paginada\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"/list/{pagina}\", method = RequestMethod.GET)\n");
      code.append("  public ModelAndView llistatPaginat(HttpServletRequest request,\n");
      code.append("    HttpServletResponse response, @PathVariable Integer pagina)\n");
      code.append("      throws I18NException {\n");
      code.append("    if(!isActiveList()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n");
      code.append("    ModelAndView mav = new ModelAndView(getTileList());\n");
      code.append("    llistat(mav, request, get" + filterForm + "(pagina, mav, request));\n");
      code.append("    return mav;\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public " + filterForm + " get" + filterForm + "(Integer pagina, ModelAndView mav,\n");
      code.append("    HttpServletRequest request) throws I18NException {\n");
      code.append("    " + filterForm +" " + instanceFilterForm + ";\n");
      code.append("    " + instanceFilterForm + " = (" + filterForm + ") request.getSession().getAttribute(getSessionAttributeFilterForm());\n");
      code.append("    if(" + instanceFilterForm + " == null) {\n");
      code.append("      " + instanceFilterForm +  " = new " + filterForm + "();\n");
      code.append("      " + instanceFilterForm + ".setContexte(getContextWeb());\n");
      //code.append("      " + instanceFilterForm + ".setTitleCode(\"" + model + ".llistat\");\n");
      code.append("      " + instanceFilterForm + ".setEntityNameCode(getEntityNameCode());\n");
      code.append("      " + instanceFilterForm + ".setEntityNameCodePlural(getEntityNameCodePlural());\n");
      code.append("      " + instanceFilterForm + ".setNou(true);\n");
      code.append("    } else {\n");
      code.append("      " + instanceFilterForm + ".setNou(false);\n");
      code.append("    }\n");
      
      code.append("    " + instanceFilterForm + ".setPage(pagina == null ? 1 : pagina);\n");
      code.append("    return " + instanceFilterForm + ";\n");
      code.append("  }\n");
      

      code.append("\n");
      code.append("  /**\n");
      code.append("   * Segona i següent peticions per llistar " + tableJavaName + " de forma paginada\n");
      code.append("   * \n");
      code.append("   * @param request\n");
      code.append("   * @param pagina\n");
      code.append("   * @param filterForm\n");
      code.append("   * @return\n");
      code.append("   * @throws I18NException\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"/list/{pagina}\", method = RequestMethod.POST)\n");
      code.append("  public ModelAndView llistatPaginat(HttpServletRequest request,\n");
      code.append("      HttpServletResponse response,@PathVariable Integer pagina,\n");
      code.append("      @ModelAttribute " + filterForm + " filterForm) throws I18NException {\n");
      code.append("    if(!isActiveList()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n\n");
      code.append("    ModelAndView mav = new ModelAndView(getTileList());\n\n");
      code.append("    filterForm.setPage(pagina == null ? 1 : pagina);\n");
      code.append("    // Actualitza el filter form\n\n");
      code.append("    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);\n");
      code.append("    filterForm = get" + filterForm + "(pagina, mav, request);\n\n");

      code.append("    llistat(mav, request, filterForm);\n");
      code.append("    return mav;\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  /**\n");
      code.append("   * Codi centralitzat de llistat de " + tableJavaName + " de forma paginada.\n");
      code.append("   * \n");
      code.append("   * @param request\n");
      code.append("   * @param filterForm\n");
      code.append("   * @param pagina\n");
      code.append("   * @return\n");
      code.append("   * @throws I18NException\n");
      code.append("   */\n");
      code.append("  protected List<" + tableJavaName + "> llistat(ModelAndView mav, HttpServletRequest request,\n");
      code.append("     " + filterForm + " filterForm) throws I18NException {\n\n");
      code.append("    int pagina = filterForm.getPage();\n");
      code.append("    request.getSession().setAttribute(getSessionAttributeFilterForm(), filterForm);\n");
      code.append("\n");
      code.append("    captureSearchByValueOfAdditionalFields(request, filterForm);\n");
      code.append("\n");
      code.append("    preList(request, mav, filterForm);\n");
      code.append("\n");
      code.append("    List<" + tableJavaName + "> " + model + " = processarLlistat(" + instanceEjb + ",\n");
      code.append("        filterForm, pagina, getAdditionalCondition(request), mav);\n");
      code.append("\n");
      code.append("    mav.addObject(\"" + model + "Items\", " + model + ");\n");
      code.append("\n");
      code.append("    mav.addObject(\"" + instanceFilterForm + "\", filterForm);\n\n");

      code.append("    fillReferencesForList(filterForm,request, mav, " + model + ", (List<GroupByItem>)mav.getModel().get(\"groupby_items\"));\n");
      code.append("\n");
      code.append("    postList(request, mav, filterForm, " + model + ");\n");
      code.append("\n");
      code.append("    return " + model + ";\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");

      code.append("  public Map<Field<?>, GroupByItem> fillReferencesForList(" + filterForm + " filterForm,\n" +
      		"    HttpServletRequest request, ModelAndView mav,\n");
      code.append("      List<" + tableJavaName + "> list"
          + ", List<GroupByItem> groupItems) throws I18NException {\n");
      
      //code.append("    // relaciona els camps amb el mapeig de valors i noms.\n");
      //code.append("    Map<String, Map<String, String>> fieldMappingLabel = new HashMap<String, Map<String,String>>();");

      code.append("    Map<Field<?>, GroupByItem> groupByItemsMap = new HashMap<Field<?>, GroupByItem>();\n");
      code.append("    for (GroupByItem groupByItem : groupItems) {\n");
      code.append("      groupByItemsMap.put(groupByItem.getField(),groupByItem);\n");
      code.append("    }\n\n");
      
      
      
      if (needTmpMap) {
        code.append("    Map<String, String> _tmp;\n");
        code.append("    List<StringKeyValue> _listSKV;\n\n");
      }
      
      code.append(codeTmp.toString());
      
      
      code.append("\n");
      code.append("    return groupByItemsMap;\n");
      code.append("  }\n");
      
      
      code.append("\n");
      
      
      code.append("  @RequestMapping(value = \"/export/{dataExporterID}\", method = RequestMethod.POST)\n");
      code.append("  public void exportList(@PathVariable(\"dataExporterID\") String dataExporterID,\n");
      code.append("    HttpServletRequest request, HttpServletResponse response,\n");
      code.append("    " + filterForm + " filterForm) throws Exception, I18NException {\n");
      code.append("\n");
      code.append("    ModelAndView mav = new ModelAndView(getTileList());\n");
      code.append("    List<" + tableJavaName + "> list = llistat(mav, request, filterForm);\n");
      code.append("    Field<?>[] allFields = ALL_" + tableJavaName.toUpperCase() + "_FIELDS;\n");
      code.append("\n");
      
      
      code.append("    java.util.Map<Field<?>, java.util.Map<String, String>> __mapping;\n");
      code.append("    __mapping = new java.util.HashMap<Field<?>, java.util.Map<String, String>>();\n");

      code.append(codeExportList.toString());

      code.append("    exportData(request, response, dataExporterID, filterForm,\n");
      code.append("          list, allFields, __mapping, PRIMARYKEY_FIELDS);\n");
      code.append("  }\n");
      code.append("\n");

      code.append("\n");
      code.append("\n");
      code.append("  /**\n");
      code.append("   * Carregar el formulari per un nou " + tableJavaName + "\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"/new\", method = RequestMethod.GET)\n");
      code.append("  public ModelAndView crear" + tableJavaName + "Get(HttpServletRequest request,\n");
      code.append("      HttpServletResponse response) throws I18NException {\n");
      code.append("\n");
      code.append("    if(!isActiveFormNew()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n");
      code.append("    ModelAndView mav = new ModelAndView(getTileForm());\n");
      
      code.append("    " + form + " " + instanceForm + " = get" + form + "(null, false, request, mav);\n");
      if(transFields != null) {
        for (FieldInfo fieldInfo : transFields) {
          code.append("    \n");
          String getmethod = CodeGenUtils.get(fieldInfo); // -4 == Llevar ID i '()'
          code.append("    if (" + instanceForm + ".get" + tableJavaName + "()." + getmethod.substring(0,getmethod.length() - 4 ) + "() == null){\n");
          code.append("      " + jpaPackage + ".TraduccioJPA trad = new " + jpaPackage + ".TraduccioJPA();\n");
          code.append("      for (" + packages.entityPackage + ".Idioma idioma : " + instanceForm + ".getIdiomesTraduccio()) {\n");
          code.append("        trad.addTraduccio(idioma.getIdiomaID(), new " + jpaPackage + ".TraduccioMapJPA());\n");
          code.append("      }\n");
          
          String setmethod = CodeGenUtils.set(fieldInfo); // -2 == Llevar ID
          code.append("      " + instanceForm + ".get" + tableJavaName + "()." + setmethod.substring(0,setmethod.length() - 2 ) + "(trad);\n");
          code.append("    }\n");
          code.append("\n");
        }
      }
      
      
      
      code.append("    mav.addObject(\"" + instanceForm+ "\" ," + instanceForm + ");\n");
      
      
      code.append("    fillReferencesForForm(" + instanceForm + ", request, mav);\n  ");
      code.append("\n");
      code.append("    return mav;\n");
      code.append("  }\n");
      
      code.append("  \n");
      code.append("  /**\n");
      code.append("   * \n");
      code.append("   * @return\n");
      code.append("   * @throws Exception\n");
      code.append("   */\n");
      code.append("  public " + form + " get" + form + "(" + jpa + " _jpa,\n");
      code.append("       boolean __isView, HttpServletRequest request, ModelAndView mav) throws I18NException {\n");      
      
      code.append("    " + form + " " + instanceForm + ";\n");
      code.append("    if(_jpa == null) {\n");
      code.append("      " + instanceForm + " = new " + form + "(new " + jpa + "(), true);\n");
      //code.append("      " + instanceForm + ".setTitleCode(\"" + model + ".crear\");\n");      
      code.append("    } else {\n");
      code.append("      " + instanceForm + " = new " + form + "(_jpa, false);\n");
      code.append("      " + instanceForm + ".setView(__isView);\n");
      code.append("    }\n");
      code.append("    " + instanceForm + ".setContexte(getContextWeb());\n");
      code.append("    " + instanceForm + ".setEntityNameCode(getEntityNameCode());\n");
      code.append("    " + instanceForm + ".setEntityNameCodePlural(getEntityNameCodePlural());\n");
     
      if (transFields != null && transFields.size() != 0) {
        code.append("    " + instanceForm + ".setIdiomesTraduccio(getIdiomesSuportats());\n");
      }
      
      code.append("    return " + instanceForm + ";\n");
      code.append("  }\n");
      code.append("\n");
      
      
      
      code.append("  public void fillReferencesForForm(" + form + " " + instanceForm + ",\n" +
      "    HttpServletRequest request, ModelAndView mav) throws I18NException {\n");

      // Aquin van les dades dels combobox dels camps que són referències a altres taules 
      for (FieldInfo field : fields) {
        WebFieldInfo wfi = field.getWebFieldInfo(); 
        if ( !(wfi.getWebtype() == WebType.Query || wfi.getWebtype() == WebType.ComboBox)) {
          continue;
        }
        // Si és fitxer omitim
        if (fileTable != null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
          continue;
        }
        if (translateTable != null && wfi.getForeignKey() != null && translateTable.getName().equals(wfi.getForeignKey().getTable())) {
          continue;
        }
        
        /*
        if (transFields!= null && transFields.contains(field)) {
          continue;
        }
        */
        TableInfo fkTable = null;
        if (wfi.getWebtype() == WebType.Query) {
          ForeignKey fk =field.getWebFieldInfo().getForeignKey();
          fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
        }
        
        final String prefixSet = "setListOf";
        final String prefixGet = "getListOf";
        
        String name = Character.toUpperCase(field.javaName.charAt(0)) + field.javaName.substring(1);
        String setmethod = generateReferenceMethodName(prefixSet, field, fkTable);
        String getmethod = generateReferenceMethodName(prefixGet, field, fkTable);
        code.append("    // Comprovam si ja esta definida la llista\n");
        code.append("    if (" + instanceForm + "." + getmethod + "() == null) {\n");
        code.append("      List<StringKeyValue> _listSKV = getReferenceListFor" + name + "(request, mav, " + instanceForm + ", null);\n\n");
        
        code.append(" if (!_listSKV.isEmpty())    {\n");
        code.append("      java.util.Collections.sort(_listSKV, STRINGKEYVALUE_COMPARATOR);\n");
        code.append("    }\n");
        
        code.append("      " + instanceForm + "." + setmethod + "(_listSKV);\n");
        code.append("    }\n");
      }
      code.append("    \n");
      code.append("  }\n");
      
      String validationException = 
          "      if (__e instanceof " + I18NValidationException.class.getSimpleName() + ") {\n"
        + "        ValidationWebUtils.addFieldErrorsToBindingResult(result, (" + I18NValidationException.class.getSimpleName() + ")__e);\n"
        + "        return getTileForm();\n"
        + "      }\n";
      
      // import es.caib.portafib.model.entity.Idioma;
      // import es.caib.portafib.model.fields.IdiomaFields;
      
      if (transFields != null && transFields.size() != 0) {
        code.append("\n");
        code.append("\n"); 
        code.append("  public List<" + packages.entityPackage + ".Idioma> getIdiomesSuportats() throws I18NException {\n");
        code.append("    List<" + packages.entityPackage + ".Idioma> idiomes = idiomaEjb.select(" + packages.fieldsPackage + ".IdiomaFields.SUPORTAT.equal(true));\n");
        code.append("    return idiomes;\n");
        code.append("  }\n");
        code.append("\n");
      }

      code.append("\n");
      code.append("  /**\n");
      code.append("   * Guardar un nou " + tableJavaName + "\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"/new\", method = RequestMethod.POST)\n");
      code.append("  public String crear" + tableJavaName + "Post(@ModelAttribute " + form + " " + instanceForm + ",\n");
      code.append("      BindingResult result, HttpServletRequest request,\n");
      code.append("      HttpServletResponse response) throws Exception {\n");
      code.append("    if(!isActiveFormNew()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n");
      code.append("\n");
      code.append("    " + jpa + " " + model + " = " + instanceForm + ".get" + tableJavaName + "();\n");
      code.append("\n");
      if (fileFields.size() != 0) {
        code.append("    FilesFormManager<" + fileTable.getNameJava() + "> afm = getFilesFormManager(); // FILE\n\n");
      }
      code.append("    try {\n");
      if (fileFields.size() != 0) {
        code.append("      this.setFilesFormToEntity(afm, " + model + ", " + instanceForm + "); // FILE\n");
      }
      code.append("      preValidate(request, " + instanceForm + ", result);\n");
      code.append("      getWebValidator().validate(" + instanceForm + ", result);\n");
      code.append("      postValidate(request," + instanceForm + ", result);\n");
      code.append("\n");
      code.append("      if (result.hasErrors()) {\n");
      if (fileFields.size() != 0) {
        code.append("        afm.processErrorFilesWithoutThrowException(); // FILE\n");
      }
      code.append("        result.reject(\"error.form\");\n");
      code.append("        return getTileForm();\n");
      code.append("      } else {\n");

      
      code.append("        " + model + " = create(request, " + model + ");\n");
      if (fileFields.size() != 0) {
      code.append("        afm.postPersistFiles(); // FILE\n");
      }
      code.append("        createMessageSuccess(request, \"success.creation\", " + pkFromModel + ");\n");
      code.append("        " + instanceForm + ".set" + tableJavaName + "(" + model + ");\n");
      code.append("        return getRedirectWhenCreated(request, " + instanceForm + ");\n");
      code.append("      }\n");
      code.append("    } catch (Throwable __e) {\n");
      if (fileFields.size() != 0) {
      code.append("      afm.processErrorFilesWithoutThrowException(); // FILE\n");
      }
      code.append(validationException);
      code.append("      String msg = createMessageError(request, \"error.creation\", null, __e);\n");
      code.append("      log.error(msg, __e);\n");
      code.append("      return getTileForm();\n");
      code.append("    }\n");

      code.append("  }\n");
      code.append("\n");

      code.append("  @RequestMapping(value = \"/view" + pkMapping + "\", method = RequestMethod.GET)\n");
      code.append("  public ModelAndView veure" + tableJavaName + "Get(" + pkParams + ",\n");
      code.append("      HttpServletRequest request,\n");
      code.append("      HttpServletResponse response) throws I18NException {\n");
      code.append("      return editAndView" + tableJavaName + "Get(" + pkFromParams + ",\n");
      code.append("        request, response, true);\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");

      
      code.append("  protected ModelAndView editAndView" + tableJavaName + "Get(" + pkParams + ",\n");
      code.append("      HttpServletRequest request,\n");
      code.append("      HttpServletResponse response, boolean __isView) throws I18NException {\n");

      code.append("    if((!__isView) && !isActiveFormEdit()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    } else {\n");
      code.append("      if(__isView && !isActiveFormView()) {\n");
      code.append("        response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("        return null;\n");
      code.append("      }\n");
      code.append("    }\n");
      code.append("    " + jpa + " " + model + " = findByPrimaryKey(request, " + pkFromParams + ");\n");
      code.append("\n");
      code.append("    if (" + model + " == null) {\n");
      code.append("      createMessageWarning(request, \"error.notfound\", " + pkFromParams + ");\n");
      code.append("      new ModelAndView(new RedirectView(getRedirectWhenCancel(request, " + pkFromParams + "), true));\n");
      code.append("      return llistatPaginat(request, response, 1);\n");
      code.append("    } else {\n");
      code.append("      ModelAndView mav = new ModelAndView(getTileForm());\n");   
      code.append("      " + form + " " + instanceForm + " = get" + form + "(" + model + ", __isView, request, mav);\n");
      code.append("      " + instanceForm + ".setView(__isView);\n");
      code.append("      if(__isView) {\n");
      code.append("        " + instanceForm + ".setAllFieldsReadOnly(ALL_" + tableJavaName.toUpperCase() + "_FIELDS);\n");
      code.append("        " + instanceForm + ".setSaveButtonVisible(false);\n");
      code.append("        " + instanceForm + ".setDeleteButtonVisible(false);\n");
      code.append("      }\n");
      code.append("      fillReferencesForForm(" + instanceForm + ", request, mav);\n");
      code.append("      mav.addObject(\"" + instanceForm + "\", " + instanceForm + ");\n");
      code.append("      return mav;\n");
      code.append("    }\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");
      
      
      code.append("  /**\n");
      code.append("   * Carregar el formulari per modificar un " + tableJavaName + " existent\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"" + pkMapping + "/edit\", method = RequestMethod.GET)\n");
      code.append("  public ModelAndView editar" + tableJavaName + "Get(" + pkParams + ",\n");
      code.append("      HttpServletRequest request,\n");
      code.append("      HttpServletResponse response) throws I18NException {\n");
      code.append("      return editAndView" + tableJavaName + "Get(" + pkFromParams + ",\n");
      code.append("        request, response, false);\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");

      
      code.append("\n");
      code.append("  /**\n");
      code.append("   * Editar un " + tableJavaName + " existent\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"" + pkMapping + "/edit\", method = RequestMethod.POST)\n");
      code.append("  public String editar" + tableJavaName + "Post(@ModelAttribute @Valid " + form + " " + instanceForm + ",\n");
      code.append("      BindingResult result, SessionStatus status, HttpServletRequest request,\n");
      code.append("      HttpServletResponse response) throws I18NException {\n");
      code.append("\n");
      code.append("    if(!isActiveFormEdit()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n");
      code.append("    " + jpa + " " + model + " = " + instanceForm + ".get" + tableJavaName + "();\n\n");
      
      if (fileFields.size() != 0) {
        
        code.append("    FilesFormManager<" + fileTable.getNameJava() + "> afm = getFilesFormManager(); // FILE\n");
      }
      code.append("    try {\n");
      if (fileFields.size() != 0) {
        code.append("      this.setFilesFormToEntity(afm, " + model + ", " + instanceForm + "); // FILE\n"); 
        //, " + instanceForm + ", result); // FILE\n");
        //code.append("        if (result.hasErrors()) { // FILE\n");
        //code.append("          return getTileForm(); // FILE\n");
        //code.append("        } // FILE\n ");
      }
      
      
      code.append("      preValidate(request, " + instanceForm + ", result);\n");
      code.append("      getWebValidator().validate(" + model + ", result);\n");
      code.append("      postValidate(request, " + instanceForm + ", result);\n");
      code.append("\n");
      code.append("      if (result.hasErrors()) {\n");
      if (fileFields.size() != 0) {
        code.append("        afm.processErrorFilesWithoutThrowException(); // FILE\n");
      }
      code.append("        result.reject(\"error.form\");\n");
      code.append("        return getTileForm();\n");
      code.append("      } else {\n");

      code.append("        " + model + " = update(request, " + model + ");\n");
      if (fileFields.size() != 0) {
      code.append("        afm.postPersistFiles(); // FILE\n");
      }
      code.append("        createMessageSuccess(request, \"success.modification\", " +  pkFromModel + ");\n");
      
      code.append("        status.setComplete();\n");
      
      code.append("        return getRedirectWhenModified(request, " + instanceForm + ", null);\n");
      
      code.append("      }\n");
      code.append("    } catch (Throwable __e) {\n");
      if (fileFields.size() != 0) {
      code.append("      afm.processErrorFilesWithoutThrowException(); // FILE\n");
      }
      code.append(validationException);
      code.append("      String msg = createMessageError(request, \"error.modification\",\n");
      code.append("          " + pkFromModel + ", __e);\n");
      code.append("      log.error(msg, __e);\n");
      code.append("      return getRedirectWhenModified(request, " + instanceForm + ", __e);\n");
      code.append("    }\n");
      code.append("\n");
      code.append("  }\n");
      code.append("\n");
      


      code.append("\n");
      code.append("  /**\n");
      code.append("   * Eliminar un " + tableJavaName + " existent\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"" + pkMapping + "/delete\")\n");
      code.append("  public String eliminar" + tableJavaName + "(" + pkParams + ",\n");
      code.append("      HttpServletRequest request,HttpServletResponse response) {\n");
      code.append("\n");
      code.append("    if(!isActiveDelete()) {\n");
      code.append("      response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("      return null;\n");
      code.append("    }\n");
      code.append("    try {\n");
      code.append("      " + tableJavaName + " " + model + " = findByPrimaryKey(request, " + pkFromParams + ");\n");
      code.append("      if (" + model + " == null) {\n");
      code.append("        String __msg =createMessageError(request, \"error.notfound\", " + pkFromParams + ");\n");
      code.append("        return getRedirectWhenDelete(request, " + pkFromParams + ", new Exception(__msg));\n");
      code.append("      } else {\n");
      code.append("        delete(request, " + model + ");\n");
      code.append("        createMessageSuccess(request, \"success.deleted\", " + pkFromParams + ");\n");      
      code.append("        return getRedirectWhenDelete(request, " + pkFromParams + ",null);\n");
      code.append("      }\n");
      //code.append("      return \"redirect:\" + getContextWeb() + \"/list\";\n");
      code.append("\n");
      code.append("    } catch (Throwable e) {\n");
      code.append("      String msg = createMessageError(request, \"error.deleting\", " + pkFromParams + ", e);\n");
      code.append("      log.error(msg, e);\n");
      code.append("      return getRedirectWhenDelete(request, " + pkFromParams + ", e);\n");
      //code.append("      return getTileForm();\n");
      code.append("    }\n");
      code.append("  }\n");
      code.append("\n");
      
      
      
      
      code.append("\n");
      code.append("@RequestMapping(value = \"/deleteSelected\", method = RequestMethod.POST)\n");
      code.append("public String deleteSelected(HttpServletRequest request,\n");
      code.append("    HttpServletResponse response,\n");
      code.append("    @ModelAttribute " + filterForm + " filterForm) throws Exception {\n");
      code.append("\n");
      code.append("  if(!isActiveDelete()) {\n");
      code.append("    response.setStatus(HttpServletResponse.SC_NOT_FOUND);\n");
      code.append("    return null;\n");
      code.append("  }\n");
      code.append("  \n");
      if (multiplePKs) {
        code.append(" throw new Exception(\"Classes with multiple PK, must overwrite method deleteSelected()\");\n");
        code.append("/*");
      }
      code.append("  String[] seleccionats = filterForm.getSelectedItems();\n");
      code.append("  String redirect = null;\n");
      code.append("  if (seleccionats != null && seleccionats.length != 0) {\n");  
      code.append("    for (int i = 0; i < seleccionats.length; i++) {\n");
      code.append("      redirect = eliminar" + tableJavaName + "(stringToPK(seleccionats[i]), request, response);\n");      
      code.append("    }\n");
      code.append("  }\n");
      code.append("  if (redirect == null) {\n");
      code.append("    redirect = getRedirectWhenDelete(request, null,null);\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  return redirect;\n");
      
      if (multiplePKs) {
        code.append("*/");
      }
      
      code.append("}\n");
      code.append("\n");
      
      if (!multiplePKs) {
        code.append("\n");
        code.append("\n");
        code.append("public " + pkClass + " stringToPK(String value) {\n");
        
        FieldInfo pk = PKs.get(0);
        if (pk.getJavaType().equals(String.class)) {
          code.append("  return value;\n");
        } else {
          code.append("  return new " + pkClass + "(value);\n"); // TODO
        }
        code.append("}\n");
        code.append("\n");
      }
      
      
      code.append("  @Override\n");
      code.append("  public String[] getArgumentsMissatge(Object __" + pkClassInstance + ", Throwable e) {\n");
      code.append("    " + pkClass + " " + pkClassInstance + " = (" + pkClass + ")__" + pkClassInstance + ";\n");
      code.append("    String exceptionMsg = \"\";\n");
      code.append("    if (e != null) {\n");
      code.append("      if (e instanceof I18NException) {\n");
      code.append("        exceptionMsg = I18NUtils.getMessage((I18NException)e);\n");
      code.append("      } else if (e instanceof " + I18NValidationException.class.getSimpleName() + ") {\n");
      code.append("      } else {\n");
      code.append("        exceptionMsg = e.getMessage();\n");
      code.append("      };\n");
      code.append("    };\n");
      code.append("    if (" + pkClassInstance + " == null) {\n");
      code.append("      return new String[] { I18NUtils.tradueix(getEntityNameCode()),\n");
      code.append("         getPrimaryKeyColumnsTranslated(), null, exceptionMsg };\n");
      code.append("    } else {\n");
      code.append("      return new String[] { I18NUtils.tradueix(getEntityNameCode()),\n");
      code.append("        getPrimaryKeyColumnsTranslated(),\n");
      code.append("        " + argValues + " exceptionMsg };\n");
      code.append("    }\n");
      code.append("  }\n");

      code.append("\n");      
      code.append("  public String getEntityNameCode() {\n");
      code.append("    return \"" + model + "." + model + "\";\n");
      code.append("  }\n");
      
      code.append("\n");      
      code.append("  public String getEntityNameCodePlural() {\n");
      code.append("    return \"" + model + "." + model + ".plural\";\n");
      code.append("  }\n");
      
      code.append("\n");      
      code.append("  public String getPrimaryKeyColumnsTranslated() {\n");
      code.append("    return " + argNames + ";\n");
      code.append("  }\n");
      
      
      code.append("\n");
      code.append("  @InitBinder(\"" + instanceFilterForm + "\")\n");
      code.append("  public void initBinderFilterForm(WebDataBinder binder) {\n");
      code.append("    super.initBinder(binder);\n");
      code.append("  }\n");
      code.append("\n");
      code.append("  @InitBinder(\"" + instanceForm + "\")\n");
      code.append("  public void initBinderForm(WebDataBinder binder) {\n");
      code.append("    super.initBinder(binder);\n");
      code.append("\n");
      code.append("    binder.setValidator(getWebValidator());\n");
      code.append("\n");
      // Els camps autogernats s'han d'ignorar.
      StringBuilder autoFields = new StringBuilder();
      for (FieldInfo field : fields) {
        if (field.isAutoIncrement()) {
          autoFields.append(", \"").append(model).append(".").append(field.getJavaName()).append("\"");
        }
      }
      code.append("    initDisallowedFields(binder" + autoFields.toString() + ");");
      code.append("\n");
      code.append("  }\n");
      code.append("\n");
      
      
      // ------------ VALIDADORS
      code.append("  public " + validator+ " getWebValidator() {\n");
      code.append("    return " + instanceValidator + ";\n");
      code.append("  }\n");
      code.append("\n\n");
      
      
      code.append("  public void setWebValidator(" + validator+ " __val) {\n");
      code.append("    if (__val != null) {\n");
      code.append("      this." + instanceValidator + "= __val;\n");
      code.append("    }\n");
      code.append("  }\n");
      code.append("\n\n");
      
      
      code.append("  /**\n");
      code.append("   * Entra aqui al pitjar el boto cancel en el llistat de " + tableJavaName + "\n");
      code.append("   */\n");
      code.append("  @RequestMapping(value = \"" + pkMapping + "/cancel\")\n");
      code.append("  public String cancel" + tableJavaName + "(" + pkParams + ",\n");
      code.append("      HttpServletRequest request,HttpServletResponse response) {\n");
      code.append("     return getRedirectWhenCancel(request, " + pkFromParams + ");\n"); 
      code.append("  }\n");
      code.append("\n");
      
     
      code.append("  @Override\n");
      code.append("  public String getTableModelName() {\n");
      code.append("    return _TABLE_MODEL;\n");
      code.append("  }\n");

      code.append("\n");
      
      if (fileFields.size() != 0) {
      code.append("  // FILE\n");
      code.append("  @Override\n");
      code.append("  public void setFilesFormToEntity(FilesFormManager<" + fileTable.getNameJava() + "> afm, " + tableJavaName + " " + model + ",\n      " + form + " form");
      //code.append(", BindingResult result");
      code.append(") throws I18NException {\n");
      code.append("\n");

      code.append("    " + fileTable.getNameJava() + "JPA f;\n");
      
      for (FieldInfo fieldInfo : fileFields) {
        String jn = fieldInfo.getJavaName();
        code.append("    f = (" + fileTable.getNameJava() + "JPA)afm.preProcessFile(form." + CodeGenUtils.getOnlyName(jn) + "(), form." + CodeGenUtils.isOnlyName(jn) + "Delete(),\n");

        if (jn.endsWith("ID")) {
          jn= jn.substring(0, jn.length() - 2);
        }

        code.append("        form.isNou()? null : " + model + "." + CodeGenUtils.getOnlyName(jn) + "());\n");
        code.append("    ((" + jpa + ")" + model + ")." +  CodeGenUtils.set(jn) + "(f);\n");

        code.append("    if (f != null) { \n");
        code.append("      " + model + "." +  CodeGenUtils.set(fieldInfo) + "(f.getFitxerID());\n");
        code.append("    } else {\n");
        code.append("      " + model + "." +  CodeGenUtils.set(fieldInfo) + "("
            + (fieldInfo.getJavaType().isPrimitive()? "0" : "null") + ");\n");        
        code.append("    }\n\n");
        if (fieldInfo.isNotNullable) {  
          /*
          String camp = fieldInfo.javaName.toUpperCase();
          code.append(" else {\n");     
          code.append("      result.addError(new ObjectError(get(" + camp + "),\n");
          code.append("             new String[] { \"genapp.validation.required\" }, new Object[] { I18NUtils.tradueix(get("
          + camp + ")) }, null));\n");
          code.append("    }\n");
          */
        } else {
          code.append("\n");
        }

      }
     
      code.append("  }\n");
      code.append("\n");
      code.append("  // FILE\n");
      code.append("  @Override\n");
      code.append("  public void deleteFiles(" + tableJavaName + " " + model + ") {\n");
      for (FieldInfo fieldInfo : fileFields) {
        String jn = fieldInfo.getJavaName();
        code.append("    deleteFile(" + model + "." + CodeGenUtils.getOnlyName(jn) + "());\n");
      }
      code.append("  }\n");
      }
      
      // ====================
      // Mètodes a sobreescriure
      // ====================
      
      code.append("  // Mètodes a sobreescriure \n");
      
      final String[] actives = new String[] { "List" , "FormNew", "FormEdit", "Delete" };
      
      for (String type : actives) {
        code.append("\n");
        code.append("  public boolean isActive" + type + "() {\n");
        code.append("    return true;\n");
        code.append("  }\n");
        code.append("\n");
      }
      
      code.append("\n");
      code.append("  public boolean isActiveFormView() {\n");
      code.append("    return isActiveFormEdit();\n");
      code.append("  }\n");
      code.append("\n");
      

      for (FieldInfo field : fields) {
        WebFieldInfo wfi = field.getWebFieldInfo(); 
        if ( !(wfi.getWebtype() == WebType.Query || wfi.getWebtype() == WebType.ComboBox)) {
          continue;
        }
        
        
        // Si és fitxer omitim
        if (fileTable != null && wfi.getForeignKey() != null && fileTable.getName().equals(wfi.getForeignKey().getTable())) {
          continue;
        }
        TableInfo fkTable = null;;
        FieldInfo fkInfo = null;
        ForeignKey fk = null;
        if (wfi.getWebtype() == WebType.Query) {
          fk =field.getWebFieldInfo().getForeignKey();
          fkTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
          fkInfo = CodeGenUtils.findFieldInfoByColumnSQLName(fkTable, fk.getField());
        }
                
        String name = Character.toUpperCase(field.javaName.charAt(0))
        + field.javaName.substring(1);
        
        if (transFields!= null && !transFields.contains(field)) {
                  
          // ReferenceList a cridar per NEW/EDIT
          code.append("\n");
          code.append("  public List<StringKeyValue> getReferenceListFor" + name + "("
              + "HttpServletRequest request,\n" 
              + "       ModelAndView mav, " + form + " " + instanceForm 
              + ", Where where)  throws I18NException {\n");
  
          
          code.append("    if (" + instanceForm + ".isHiddenField(" + field.javaName.toUpperCase() + ")) {\n");
          code.append("      return EMPTY_STRINGKEYVALUE_LIST_UNMODIFIABLE;\n");
          code.append("    }\n");

          if (wfi.getWebtype() == WebType.Query) {
            //code.append("    final String _fieldName =  " + instanceForm + ".getStringOfField(" + field.javaName.toUpperCase() +  ");\n");
            code.append("    Where _where = null;\n");
            code.append("    if (" + instanceForm + ".isReadOnlyField(" + field.javaName.toUpperCase() + ")) {\n");
            code.append("      _where = " + fkTable.getNameJava() + "Fields." + fkInfo.getJavaName().toUpperCase() + ".equal(" + instanceForm + ".get" + tableJavaName + "().get" + name + "());\n");
            code.append("    }\n");
            code.append("    return getReferenceListFor" + name + "(request, mav, Where.AND(where, _where));\n"); 
          } else {
            code.append("    return getReferenceListFor" + name + "(request, mav, where);\n"); 
          }
          
          
          code.append("  }\n\n");
          code.append("\n");
        }
        // ReferenceList a cridar per LIST
        code.append("  public List<StringKeyValue> getReferenceListFor" + name + "("
            + "HttpServletRequest request,\n" 
            + "       ModelAndView mav, " + filterForm + " " + instanceFilterForm + ",\n"
            +	"       List<" + tableJavaName + "> list, Map<Field<?>, GroupByItem> _groupByItemsMap" 
            + ", Where where)  throws I18NException {\n");
        //code.append("    final String _fieldName =  " + instanceFilterForm + ".getStringOfField(" + field.javaName.toUpperCase() +  ");\n");
        code.append("    if (" + instanceFilterForm + ".isHiddenField(" + field.javaName.toUpperCase() +  ")\n");
        code.append("      && !" + instanceFilterForm + ".isGroupByField(" + field.javaName.toUpperCase() +  ")) {\n");
        code.append("      return EMPTY_STRINGKEYVALUE_LIST_UNMODIFIABLE;\n");
        code.append("    }\n");
        code.append("    Where _w = null;\n");
        if (wfi.getWebtype() == WebType.Query) {
          
          code.append("    if (!_groupByItemsMap.containsKey(" + field.javaName.toUpperCase() +  ")) {\n");
          code.append("      // OBTENIR TOTES LES CLAUS (PK) i despres només cercar referències d'aquestes PK\n");
          Class<?> type = field.getJavaType();
          boolean primitive = SQL2Java.isPrimitiveType(type);
          if (SQL2Java.isPrimitiveType(type)) {
            type = SQL2Java.primitiveType2javaClassName(type);
          }
          code.append("      java.util.Set<" + type.getName() + "> _pkList = new java.util.HashSet<" + type.getName() + ">();\n");
          code.append("      for (" + tableJavaName + " _item : list) {\n");
          if (!primitive && !field.isNotNullable) {
            code.append("        if(_item.get" + name + "() == null) { continue; };\n");
          }
          code.append("        _pkList.add(_item.get" + name + "());\n");
          code.append("        }\n");
          
          
          code.append("        _w = " + fkTable.getNameJava() + "Fields." + fkInfo.getJavaName().toUpperCase() + ".in(_pkList);\n");
          // OLD CODE code.append("        _w = " + fkTable.getNameJava() + "Fields." + fk.getField().toUpperCase() + ".in(_pkList);\n");
          code.append("      }\n");
        }
        
        code.append("    return getReferenceListFor" + name + "(request, mav, Where.AND(where,_w));\n");
        code.append("  }\n\n");
        code.append("\n");
        
        // ReferenceList GENERIC
        code.append("  public List<StringKeyValue> getReferenceListFor" + name + "("
            + "HttpServletRequest request,\n" 
            + "       ModelAndView mav, Where where)  throws I18NException {\n");
        
        if (wfi.getWebtype() == WebType.Query) {
          code.append("    return " + CodeGenUtils.getModelName(fkTable.getNameJava()) 
            + "RefList.getReferenceList("
            + fkTable.getNameJava() + "Fields."
            // + fk.getField().toUpperCase()
            + fkInfo.getJavaName().toUpperCase()
            + ", where );\n");
        } else {
          // MinAllowedValue conte la llista de possibles valors separats per coma
          // Si va null o un string buit llavors 
          String values = field.getMinAllowedValue();
          if (values == null || values.trim().length() == 0) {
            throw new Exception("El camp " + field.javaName + " de la taula " + 
                tableJavaName + " ha de definir la llista de valors separats per comes " +
                " en el camp 'MinAllowedValue'");
          }
          String[] split = values.split(",");
          
          code.append("    List<StringKeyValue> __tmp = new java.util.ArrayList<StringKeyValue>();\n");
          for (int j = 0; j < split.length; j++) {
            code.append("    __tmp.add(new StringKeyValue(\"" + split[j] + "\" , \"" + split[j] + "\"));\n");            		
          }
          code.append("    return __tmp;\n");
        }
        code.append("  }\n\n");
        
      }

      code.append("\n");
      code.append("  public void preValidate(HttpServletRequest request," + form + " " + instanceForm 
          + " , BindingResult result)  throws I18NException {\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public void postValidate(HttpServletRequest request," + form + " " + instanceForm 
           + ", BindingResult result)  throws I18NException {\n");
      //code.append("    return true;\n");
      code.append("  }\n");

      code.append("\n");
      code.append("  public void preList(HttpServletRequest request,"
          + " ModelAndView mav, " + filterForm + " filterForm)  throws I18NException {\n");
      code.append("  }\n");

      code.append("\n");
      code.append("  public void postList(HttpServletRequest request, " 
          + "ModelAndView mav, " + filterForm + " filterForm, "
          + " List<" + tableJavaName + "> list) throws I18NException {\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public String getRedirectWhenCreated(HttpServletRequest request, " +form + " " + instanceForm + ") {\n");
      code.append("    return \"redirect:\" + getContextWeb() + \"/list/1\";\n");
      code.append("  }\n");
      
      
      code.append("\n");
      code.append("  public String getRedirectWhenModified(HttpServletRequest request, " +form + " " + instanceForm + ", Throwable __e) {\n");
      code.append("    if (__e == null) {\n");
      code.append("      return \"redirect:\" + getContextWeb() + \"/list\";\n");
      code.append("    } else {\n");
      code.append("      return  getTileForm();\n");
      code.append("    }\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public String getRedirectWhenDelete(HttpServletRequest request, " + pkParamsSense + ", Throwable __e) {\n");
      code.append("    return \"redirect:\" + getContextWeb() + \"/list\";\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public String getRedirectWhenCancel(HttpServletRequest request, " + pkParamsSense + ") {\n");
      code.append("    return \"redirect:\" + getContextWeb() + \"/list\";\n");
      code.append("  }\n");


      code.append("\n");
      code.append("  public String getTileForm() {\n");
      code.append("    return \"" + model + "FormWebDB\";\n");
      code.append("  }\n");
      
      
      code.append("\n");
      code.append("  public String getTileList() {\n");
      code.append("    return \"" + model + "ListWebDB\";\n");
      code.append("  }\n");

      
      code.append("\n");
      code.append("  @Override\n");
      code.append("  /** Ha de ser igual que el RequestMapping de la Classe */\n");
      code.append("  public String getContextWeb() {\n");
      code.append("    RequestMapping rm = AnnotationUtils.findAnnotation(this.getClass(), RequestMapping.class);\n");
      code.append("    return rm.value()[0];\n");
      code.append("  }\n");
      
      code.append("\n");
      code.append("  public String getSessionAttributeFilterForm() {\n");
      code.append("    return \"" + tableJavaName + "WebDB_FilterForm\";\n");
      code.append("  }\n");
      code.append("\n");
      code.append("\n");
      
      
      code.append("\n");
      code.append("  public Where getAdditionalCondition(HttpServletRequest request) throws I18NException {\n");
      code.append("    return null;\n");
      code.append("  }\n");
      code.append("\n");
      
      code.append("\n");      
      // Llevar @PathVariable("fluxDeFirmesID") de pkParams
      String pkParams2 = pkParams;
      int index;
      do {
        index = pkParams2.indexOf("@PathVariable");
        if (index != -1) {
          int index2 = pkParams2.indexOf("\")");
          pkParams2 = pkParams2.substring(0,index) + pkParams2.substring(index2 + 2); 
        }
      } while (index != -1);
      
      
      code.append("  public " + jpa + " findByPrimaryKey(HttpServletRequest request, " + pkParams2.trim() + ") throws I18NException {\n");
      code.append("    return (" + jpa + ") " + instanceEjb + ".findByPrimaryKey(" + pkFromParams + ");\n");
      code.append("  }\n");
      code.append("\n");
      
      
      code.append("\n");
      code.append("  public " + jpa + " create(HttpServletRequest request, " + jpa + " " + model + ")\n");
      code.append("    throws Exception,I18NException, " + I18NValidationException.class.getSimpleName() + " {\n");
      code.append("    return (" + jpa + ") " + instanceEjb + ".create(" + model + ");\n");
      code.append("  }\n");
      code.append("\n");
      
      code.append("\n");
      code.append("  public " + jpa + " update(HttpServletRequest request, " + jpa + " " + model + ")\n");
      code.append("    throws Exception,I18NException, " + I18NValidationException.class.getSimpleName() + " {\n");
      code.append("    return (" + jpa + ") " + instanceEjb + ".update(" + model + ");\n");
      code.append("  }\n");
      code.append("\n");
      
      code.append("\n");
      code.append("  public void delete(HttpServletRequest request, " + tableJavaName + " " + model + ") throws Exception,I18NException {\n");
      code.append("    " + instanceEjb + ".delete(" + model + ");\n");
      //code.append("    return true;\n");
      code.append("  }\n");
      code.append("\n");
 

      
      
      code.append("} // Final de Classe\n");
      code.append("\n");

    
    sourceFiles.add(new SourceFile(controller + ".java", code.toString(), true));

  }

  return sourceFiles;
  }
  
  
}

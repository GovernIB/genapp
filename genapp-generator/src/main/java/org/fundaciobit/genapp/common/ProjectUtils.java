package org.fundaciobit.genapp.common;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.WebType;

/**
 * 
 * @author anadal
 *
 */
public class ProjectUtils {
    
    private static final Logger log = Logger.getLogger(Project.class);

    public static FieldInfo searchFieldInfo(Project project, String table, String fieldName) {
        
        log.info("--------------");
        log.info("-- searching " + table + "[" + fieldName + "]");
        
        TableInfo[] allTables = project.getTables();
        
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
      
      public static String createSelect(Project project, String table, String fieldName, String where) {
        FieldInfo fi = searchFieldInfo(project, table, fieldName);
        return createSelect(table, fi, where);
      }
      
      public static String createSelect(String table, FieldInfo field, String where) {
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

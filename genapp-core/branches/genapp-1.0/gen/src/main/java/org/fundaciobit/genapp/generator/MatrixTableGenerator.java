package org.fundaciobit.genapp.generator;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class MatrixTableGenerator extends TableGenerator {

  StringBuffer jsp = new StringBuffer();

  /**
   * @param columns
   * @param contentTable
   */
  public MatrixTableGenerator(String name, ConditionalColumn[] columns,
      String[][] contentTable) {
    super(name, columns);
    
    if (contentTable != null) {
      String align;
      for (int x = 0; x < contentTable.length; x++) {
  
        if (contentTable[x][0] == null) {
          continue;
        }
        jsp.append("<tr class=").append((x % 2 == 0) ?  "\"parell\"" : "\"odd\"")
            .append(">\n");
  
        for (int i = 0; i < contentTable[x].length; i++) {
          align = (i == 0)? "right" : "left";
          if (columns != null && columns[i].condition != null) {
            jsp.append("<% if (" + columns[i].condition +") { %>");
          }
          jsp.append("  <td style=\"vertical-align: middle;text-align: "
              + align + ";\"  >").append(contentTable[x][i]).append("</td>\n");
          if (columns != null && columns[i].condition != null) {
            jsp.append("<% } %>");
          }
        }
        jsp.append("</tr>\n");
      }
    }
  }
  
  
  
  

  @Override
  public String getTableContent() {

    return jsp.toString();
  }

}

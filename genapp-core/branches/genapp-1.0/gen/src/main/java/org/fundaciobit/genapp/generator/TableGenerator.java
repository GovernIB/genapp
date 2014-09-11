/**
 * 
 */
package org.fundaciobit.genapp.generator;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public abstract class TableGenerator {

  protected final String name;
  
  protected final ConditionalColumn[] columns;

  /**
   * @param name
   */
  public TableGenerator(String name, ConditionalColumn[] columns) {
    super();
    this.name = name;
    this.columns = columns;
  }

  public String getName() {
    return name;
  }

  public ConditionalColumn[] getColumns() {
    return columns;
  }

  protected abstract String getTableContent();
  
  public String generateTableCode() {
    
    StringBuffer table = new StringBuffer();
    table.append(getTableHeader());
    table.append(getTableHeaderContent());
    table.append(getTableContent());
    table.append(getTableFooter());
    
    return table.toString();
    
  }

  protected String getTableHeader() {
    return getCenterTableHeader() 
        + getTitleHeader(name)
        + getContentTableHeader(name);
  }
  
  /**
   * genera els titols de les columns
   * @return
   */
  public String getTableHeaderContent() {
    StringBuffer jsp = new StringBuffer();
    if (columns != null) {
      jsp.append("<tr class=\"header\">\n");
      for (int i = 0; i < columns.length; i++) {
        jsp.append(columns[i].toString());  
      }
      jsp.append("</tr>\n");
    }
    return jsp.toString();
  }
  
  
  protected String getTableFooter() {
    return getContentTableFooter() + getTitleFooter() + getCenterTableFooter();
  }
  
  protected String getCenterTableHeader() {
    return "<table width=\"100%\" height=\"100%\">" +
    		"<tr><td valign=\"top\" align=\"center\">\n";
  }
  
  protected String getCenterTableFooter() {
    return "</td></tr>\n</table>\n";
  }
  
  protected String getTitleHeader(String name) {
    return "<div id=\"itsthetable\">\n"
    + " <p class=\"caption\">"
    + name + "</p>\n";
  }
  
  protected String getTitleFooter() {
    return "</div>\n";
  }
  
  protected String getContentTableHeader(String name) {
    return "<table border=\"1\" cellpadding=\"1\" cellspacing=\"0\" summary=\""
    + name
    + "\">\n"
    + "<tbody class=\"tabledata\">\n";
    
  }
  
  protected String getContentTableFooter() {
    return "</tbody>\n</table>\n";
  }
  
  
  public static ConditionalColumn[] columnString2ConditionalColumns(String[] columns) {
    if (columns == null) {
      return null;
    }
    ConditionalColumn[] cc = new ConditionalColumn[columns.length];
    for (int i = 0; i < cc.length; i++) {
      cc[i] = new ConditionalColumn(columns[i], null);
    }
    return cc;
  }
  

  public static class ConditionalColumn {

    final String columnName;
    
    final String condition;

    /**
     * @param columnName
     * @param condition
     */
    public ConditionalColumn(String columnName, String condition) {
      super();
      this.columnName = columnName;
      this.condition = condition;
    }
    
    
    public String toString() {
      StringBuffer str = new StringBuffer();
      if (condition != null) {        
        str.append("<% if (" + condition + ") {%>");
      }
      str.append("<td>").append(columnName).append("</td>\n");
      if (condition != null) {
        str.append("<% } %>\n");
      }
      return str.toString();
    }
    
    
    
  }
  
}

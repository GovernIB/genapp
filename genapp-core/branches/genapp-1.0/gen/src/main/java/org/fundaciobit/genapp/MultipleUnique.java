package org.fundaciobit.genapp;

/**
 * 
 * @author anadal
 * 
 */
public class MultipleUnique {

  String name;

  String[] sqlColumns;

  /**
   * @param name
   * @param sqlColumns
   */
  public MultipleUnique(String name, String[] sqlColumns) {
    super();
    this.name = name;
    this.sqlColumns = sqlColumns;
  }

  /**
   * 
   */
  public MultipleUnique() {
    super();
    // TODO Auto-generated constructor stub
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getSqlColumns() {
    return sqlColumns;
  }

  public void setSqlColumns(String[] sqlColumns) {
    this.sqlColumns = sqlColumns;
  }

}

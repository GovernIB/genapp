package org.fundaciobit.genapp.common.query;
/**
 * 
 * @author anadal
 *
 */
public abstract class SelectField<R> extends Select<R> {

  final String fullName;
  
  
  public SelectField(String table, String javaName) {
    this.fullName = table == null ? javaName : (table + "." + javaName);
  }


  @Override
  public String getSelectString() {
    return fullName;
  }

  @Override
  public int length() {
      return 1;
  }
  
}

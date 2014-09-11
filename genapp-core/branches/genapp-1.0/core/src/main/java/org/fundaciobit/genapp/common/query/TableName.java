package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.IGenAppEntity;

/**
 * 
 * @author anadal
 *
 */
public class TableName<I extends IGenAppEntity> {

  private final String tableName;

  /**
   * @param tableName
   */
  public TableName(String tableName) {
    super();
    this.tableName = tableName;
  }

  public String getTableName() {
    return tableName;
  }

  @Override
  public String toString() {
    return this.tableName;
  }
}

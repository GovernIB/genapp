package org.fundaciobit.genapp.common.db;

import java.sql.Connection;

public interface ConnectionProvider {
  
  
  public Connection getDBConnection() throws Exception;

}

package org.fundaciobit.genapp.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.db.DataBaseInfo;

/**
 * 
 * @author anadal
 *
 */
public class DataBaseInfoUtils {
    

    private static final Logger log = Logger.getLogger(DataBaseInfoUtils.class);

    private static transient Connection connection;
    
    public static transient boolean reuseConnection = false;
    

    public static final String DATABASE_USER = "user";

    public static final String DATABASE_PASSWORD = "password";

    
    
    public static Connection getConnection(DataBaseInfo dbInfo) throws Exception {
        //Connection connection;
        if (!reuseConnection || connection == null || connection.isClosed())
          // || !connection.isValid(3)) 
        {
          try {
            Class.forName(dbInfo.getDriver());

            log.debug("A Connecting with DB(" + dbInfo.getUrl() + ", '" + dbInfo.getUsr() + "'");

            if (dbInfo.getProperties() == null) {
              connection = DriverManager.getConnection(dbInfo.getUrl(),  dbInfo.getUsr(),  dbInfo.getPwd());
            } else {
              Properties tmp = new Properties(dbInfo.getProperties());
              tmp.put(DATABASE_USER,  dbInfo.getUsr());
              tmp.put(DATABASE_PASSWORD, dbInfo.getPwd());
              connection = DriverManager.getConnection(dbInfo.getUrl(), tmp);
            }
            
            // connection.setAutoCommit(true);
            // Statement statement = connection.createStatement();

            //log.info("Opened db connection");
          } catch (ClassNotFoundException ex) {
            throw new Exception("Cannot find the database driver classes.\n"
                + ex.toString());
          } catch (SQLException ex) {
            throw new Exception("Cannot connect to this database.\n"
                + ex.toString());
          }
        }
        return connection;
      }

      
      public static void close(Connection conn) {
        log.info("Closing db connection");
        try {
          conn.close();
        } catch (SQLException e) {      
        }    
      }
      
      public static void closeConnection(Connection con) throws Exception {
          if(con != null && !reuseConnection) {
            con.close(); 
          }
        }
    
}

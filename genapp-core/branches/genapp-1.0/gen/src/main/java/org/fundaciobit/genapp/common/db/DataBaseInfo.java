package org.fundaciobit.genapp.common.db;

import java.io.Serializable;
import java.sql.*;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Title:        Rapid Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 *
 */
public class DataBaseInfo implements Serializable {

  private static final Logger log = Logger.getLogger(DataBaseInfo.class.getSimpleName());
  /**
   * 
   */
  private static final long serialVersionUID = 373121556163269387L;

  public static final String DATABASE_USER = "user";

  public static final String DATABASE_PASSWORD = "password";

  private transient Connection connection;
  
  public static transient boolean reuseConnection = false;

  String url;
  String driver;
  String usr;
  String pwd;
  
  Properties properties = null;
  
  public DataBaseInfo() {
    
  }

  public DataBaseInfo(String driverName, String url, String user, String passwd) {
    this.url = url;
    this.driver = driverName;
    this.usr = user;
    this.pwd = passwd;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public void setUsr(String usr) {
    this.usr = usr;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }
  
  public String getUrl() {
    return url;
  }

  public String getDriver() {
    return driver;
  }

  public String getUsr() {
    return usr;
  }

  public String getPwd() {
    return pwd;
  }

  public Connection getConnection() throws Exception {
    //Connection connection;
    if (!reuseConnection || connection == null || connection.isClosed())
      // || !connection.isValid(3)) 
    {
      try {
        Class.forName(this.driver);

        log.debug("A Connecting with DB(" + url + ", '" + usr + "'");

        if (properties == null) {
          connection = DriverManager.getConnection(url, usr, pwd);
        } else {
          Properties tmp = new Properties(this.properties);
          tmp.put(DATABASE_USER, usr);
          tmp.put(DATABASE_PASSWORD, pwd);
          connection = DriverManager.getConnection(url, tmp);
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
  
  /*
  protected void finalize() throws Throwable {
    close();
    super.finalize();
  }
  */

  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  public Properties getProperties() {
    return properties;
  }

  public static void closeConnection(Connection con) throws Exception {
    if(con != null && !reuseConnection) {
      con.close(); 
    }
  }

}

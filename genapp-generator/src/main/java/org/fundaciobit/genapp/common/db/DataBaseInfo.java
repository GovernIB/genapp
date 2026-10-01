package org.fundaciobit.genapp.common.db;

import java.io.Serializable;
import java.util.Properties;

/**
 * Title: Rapid Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 *
 */
public class DataBaseInfo implements Serializable {

    String url;
    String driver;
    String usr;
    String pwd;
    

    
    private transient String databaseProductName = null;


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

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Properties getProperties() {
        return properties;
    }
    
    public String getDatabaseProductName() {
        return databaseProductName;
    }


    public void setDatabaseProductName(String databaseProductName) {
        this.databaseProductName = databaseProductName;
    }

}

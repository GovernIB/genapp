package org.fundaciobit.genapp;

import java.io.Serializable;


import org.fundaciobit.genapp.common.db.DataBaseInfo;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class Project implements Serializable {


    private String packageName = null;

    public String projectName = null;

    public DataBaseInfo dataBaseInfo = null;

    @Deprecated
    protected String modulSeguretat;

    protected String prefix; // = "PFI";

    protected String prefixDirectori; // = "${projectname.lowercase}-";

    public String schema = null;

    public TableInfo[] tables = null;

    protected String[] languages = null;

    protected boolean generateBack = true;

    protected boolean generateWS = true;

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public Project() {
        super();
    }

    public Project(String packageName, String projectName, DataBaseInfo databaseinfo, TableInfo[] tables, String prefix,
            String prefixDirectori, String[] languages) {
        super();
        this.packageName = packageName;
        this.projectName = projectName;
        this.dataBaseInfo = databaseinfo;
        this.tables = tables;
        this.languages = languages;
        this.prefix = prefix;
        this.prefixDirectori = prefixDirectori;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public DataBaseInfo getDataBaseInfo() {
        return dataBaseInfo;
    }

    public void setDataBaseInfo(DataBaseInfo databaseinfo) {
        this.dataBaseInfo = databaseinfo;
    }

    public TableInfo[] getTables() {
        return tables;
    }

    public void setTables(TableInfo[] tables) {
        this.tables = tables;
    }

    public String getModulSeguretat() {
        return modulSeguretat;
    }

    public void setModulSeguretat(String modulSeguretat) {
        this.modulSeguretat = modulSeguretat;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefixDirectori() {
        return prefixDirectori == null ? "" : prefixDirectori;
    }

    public void setPrefixDirectori(String prefixDirectori) {
        this.prefixDirectori = prefixDirectori;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public boolean isGenerateBack() {
        return generateBack;
    }

    public void setGenerateBack(boolean generateBack) {
        this.generateBack = generateBack;
    }

    public boolean isGenerateWS() {
        return generateWS;
    }

    public void setGenerateWS(boolean generateWS) {
        this.generateWS = generateWS;
    }

    public static String getDBManagerName(String projectName) {
        return "ManagerDB" + projectName;
    }

    public static String getSecManagerName(String projectName) {
        return "ManagerSec" + projectName;
    }

}

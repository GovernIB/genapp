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

    protected String packageName = null;

    protected String projectName = null;

    protected DataBaseInfo dataBaseInfo = null;

    @Deprecated
    protected String modulSeguretat;

    protected String prefix; // = "PFI";

    protected String prefixDirectori; // = "${projectname.lowercase}-";

    protected String schema = null;

    protected TableInfo[] tables = null;

    protected String[] languages = null;

    protected boolean generateBack = true;

    protected boolean generateFront = false;

    protected boolean generateApiInterna = false;

    protected boolean generateApiExterna = false;

    protected String rolesAdmin = null;

    protected String rolesUser = null;

    protected String rolesWs = null;

    @Deprecated
    protected Boolean generateWS = null;

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public Project() {
        super();
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

    public boolean isGenerateFront() {
        return generateFront;
    }

    public void setGenerateFront(boolean generateFront) {
        this.generateFront = generateFront;
    }

    @Deprecated
    public Boolean isGenerateWS() {
        return generateWS;
    }

    @Deprecated
    public void setGenerateWS(Boolean generateWS) {
        this.generateWS = generateWS;
    }

    @Deprecated
    public Boolean getGenerateWS() {
        return generateWS;
    }

    public boolean isGenerateApiInterna() {
        return generateApiInterna;
    }

    public void setGenerateApiInterna(boolean generateApiInterna) {
        this.generateApiInterna = generateApiInterna;
    }

    public boolean isGenerateApiExterna() {
        return generateApiExterna;
    }

    public void setGenerateApiExterna(boolean generateApiExterna) {
        this.generateApiExterna = generateApiExterna;
    }

    public static String getDBManagerName(String projectName) {
        return "ManagerDB" + projectName;
    }

    public static String getSecManagerName(String projectName) {
        return "ManagerSec" + projectName;
    }

    public String[] getRolesAdminArray() {
        if (rolesAdmin == null || rolesAdmin.trim().isEmpty()) {
            return new String[] { this.getPrefix().toUpperCase() + "_" + "ADMIN" };
        } else {
            return rolesAdmin.split(",");
        }
    }

    public String getRolesAdmin() {
        return rolesAdmin;
    }

    public void setRolesAdmin(String rolesAdmin) {
        this.rolesAdmin = rolesAdmin;
    }

    public String[] getRolesUserArray() {
        if (rolesUser == null || rolesUser.trim().isEmpty()) {
            return new String[] { this.getPrefix().toUpperCase() + "_" + "USER" };
        } else {
            return rolesUser.split(",");
        }
    }

    public String getRolesUser() {
        return rolesUser;
    }

    public void setRolesUser(String rolesUser) {
        this.rolesUser = rolesUser;
    }

    public String[] getRolesWsArray() {
        if (rolesWs == null || rolesWs.trim().isEmpty()) {
            return new String[] { this.getPrefix().toUpperCase() + "_WS" };
        } else {
            return rolesWs.split(",");
        }
    }

    public String getRolesWs() {
        return rolesWs;
    }

    public void setRolesWs(String rolesWs) {
        this.rolesWs = rolesWs;
    }

}

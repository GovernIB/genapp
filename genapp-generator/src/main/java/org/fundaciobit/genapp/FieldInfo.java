package org.fundaciobit.genapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class FieldInfo implements Serializable, Comparable<FieldInfo>, TraduibleInfo {

    // Camps especifics de BBDD
    protected String PK;
    protected String unique;
    protected String index;
    public boolean isAutoIncrement;
    public String sqlName;
    public Integer sqlType;
    public Integer size;
    public Integer digits;
    public boolean isNotNullable;

    // ===================== IMPORTANT =============================
    // -- Per cada nova propietat s'ha d'anar a la linia 525 de --
    // -- la classe Panel_1_SelectDB i afegir el merge de classes --
    // -- d'aquesta nova propietat --
    // =============================================================

    // Camps editables per l'usuari
    // public String label = null;

    public Map<String, String> labels = new HashMap<String, String>();

    public String javaName;
    private Class<?> javaType;

    // indica si aquest camp és o no és traduible
    public boolean traduible;

    // Indica si s'ha de mostrar en els llistats WEB
    public boolean showInWebList = true;

    // Indica si s'ha de mostrar en els llistats de ListBox com a referencia
    public boolean showInReferenceList;

    // Camps no es persistent
    public boolean transientField;

    // Descripció del camp
    public String descripcio;

    // Valor per Defecte (Entre cometes és un String)
    public String defaultValue;

    // Validació, Valor menor
    public String minAllowedValue;

    // Validació, Valor major
    public String maxAllowedValue;

    // Anotacions adicionals
    public String additonalAnnotations;

    // Aplicar filter per aquest camp
    public boolean filter;

    // Unic per Grup de camps
    // public boolean uniqueGroup;

    // 0 = No ordernat || >0 = Ordre ascendent || <0 Ordre descendent
    public int orderBy;

    // Agrupar
    public boolean groupBy;

    public WebFieldInfo webFieldInfo;

    protected ForeignKey[] foreignKeys = new ForeignKey[0];

    /**
     * 
     */
    public FieldInfo() {
        super();
    }

    /**
     * @param javaName
     * @param javaType
     */
    public FieldInfo(String javaName, Class<?> javaType) {
        super();
        this.javaName = javaName;
        this.javaType = javaType;
    }

    public String getPK() {
        return PK;
    }

    public void setPK(String PK) {
        this.PK = PK;
    }

    public boolean isPrimaryKey() {
        return PK != null;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setIsAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public void setAutoIncrement(boolean isAutoIncrement) {
        this.isAutoIncrement = isAutoIncrement;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getJavaName() {
        return javaName;
    }

    public void setJavaName(String javaName) {
        this.javaName = javaName;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public void setJavaType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public String getSqlName() {
        return sqlName;
    }

    public void setSqlName(String sqlName) {
        this.sqlName = sqlName;
    }

    public Integer getSqlType() {
        return sqlType;
    }

    public void setSqlType(Integer sqlType) {
        this.sqlType = sqlType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public boolean isUniqueKey() {
        return this.unique != null;
    }

    public boolean isNotNullable() {
        return isNotNullable;
    }

    public void setNotNullable(boolean isNotNullable) {
        this.isNotNullable = isNotNullable;
    }

    public WebFieldInfo getWebFieldInfo() {
        return webFieldInfo;
    }

    public void setWebFieldInfo(WebFieldInfo webFieldInfo) {
        this.webFieldInfo = webFieldInfo;
    }

    public ForeignKey[] getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(ForeignKey[] foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public ForeignKey[] getForeignKeysByType(int type) {
        if (foreignKeys == null) {
            return new ForeignKey[0];
        }
        List<ForeignKey> fk = new ArrayList<ForeignKey>();
        for (int i = 0; i < this.foreignKeys.length; i++) {
            if (this.foreignKeys[i].type == type) {
                fk.add(this.foreignKeys[i]);
            }
        }
        return fk.toArray(new ForeignKey[fk.size()]);
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isShowInWebList() {
        return showInWebList;
    }

    public void setShowInWebList(boolean showInWebList) {
        this.showInWebList = showInWebList;
    }

    public boolean isShowInReferenceList() {
        return showInReferenceList;
    }

    public void setShowInReferenceList(boolean showInReferenceList) {
        this.showInReferenceList = showInReferenceList;
    }

    public boolean isTraduible() {
        return traduible;
    }

    public void setTraduible(boolean traduible) {
        this.traduible = traduible;
    }

    public boolean isTransientField() {
        return transientField;
    }

    public void setTransientField(boolean transientField) {
        this.transientField = transientField;
    }

    public String getMinAllowedValue() {
        return minAllowedValue;
    }

    public void setMinAllowedValue(String minAllowedValue) {
        this.minAllowedValue = minAllowedValue;
    }

    public String getMaxAllowedValue() {
        return maxAllowedValue;
    }

    public void setMaxAllowedValue(String maxAllowedValue) {
        this.maxAllowedValue = maxAllowedValue;
    }

    public String getAdditonalAnnotations() {
        return additonalAnnotations;
    }

    public void setAdditonalAnnotations(String additonalAnnotations) {
        this.additonalAnnotations = additonalAnnotations;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public boolean isGroupBy() {
        return groupBy;
    }

    public void setGroupBy(boolean groupBy) {
        this.groupBy = groupBy;
    }

    // @Override
    public int compareTo(FieldInfo o) {
        return this.sqlName.compareTo(o.sqlName);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof FieldInfo) {
            FieldInfo fi = (FieldInfo) o;
            return this.compareTo(fi) == 0;
        } else {
            return false;
        }
    }

}

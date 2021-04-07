package org.fundaciobit.genapp;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 */
public class TableInfo implements Serializable, Comparable<TableInfo>, Cloneable, TraduibleInfo {
  
  /**
   * 
   */
  private static final long serialVersionUID = 5950086220475741405L;

  public String name;
  
  public String nameJava;
  
  
  /** 
   * Nom curt en miniscules i de com a molt 10 caracters de longitud
   */
  public String shortName;
  
  //public String label;
  
  private Map<String, String> labels = new HashMap<String, String>();
  
  private Map<String, String> labelsPlural = new HashMap<String, String>();

  public String descripcio;
     
  public boolean generate;
  
  protected boolean fileMappingEntity;
  
  protected boolean languageMappingEntity;
  
  protected boolean translationEntity=false;
  
  protected boolean translationMapEntity=false; 
  
  
  public FieldInfo[] fields = null;
  
  protected MultipleUnique[] multipleUniques = null;
  
  public TableInfo() {
  }
  
  public TableInfo(String name, String[] idiomes) {
    this.name = name;
    this.nameJava = name;

    for (String lang : idiomes) {
      this.labels.put(lang, name + "_XX_"+ lang);
    }
  }
  
  
  /*
  public TableInfo(String nameBBDD, String nameJava, String label) {
    this.name = nameBBDD;
    this.nameJava = nameJava;
    this.label = label;
  }
  */

  public TableInfo(String nameBBDD, String nameJava, String shortName, Map<String, String> labels,
      Map<String, String> labelsPlural,FieldInfo[] fields, boolean generate) {
    super();
    this.name = nameBBDD;
    this.shortName = shortName;
    this.nameJava = nameJava;
    this.labels = labels;
    this.labelsPlural = labelsPlural;
    this.fields = fields;
    this.generate = generate;
  }

  public String getName() {
    return name;
  }

  public void setName(String newNameBBDD) {
    this.name = newNameBBDD;
  }

  public String getNameJava() {
    return nameJava;
  }

  public void setNameJava(String nameJava) {
    this.nameJava = nameJava;
  }

  public FieldInfo[] getFields() {
    return fields;
  }

  public void setFields(FieldInfo[] fields) {
    this.fields = fields;
  }

  public boolean isGenerate() {
    return generate;
  }

  public void setGenerate(boolean generate) {
    this.generate = generate;
  }

  public String getDescripcio() {
    return descripcio;
  }

  public void setDescripcio(String descripcio) {
    this.descripcio = descripcio;
  }

  //@Override
  public int compareTo(TableInfo o) {
    return this.name.compareTo(o.name);
  }
 
  public boolean isFileMappingEntity() {
    return fileMappingEntity;
  }

  public void setFileMappingEntity(boolean fileMappingEntity) {
    this.fileMappingEntity = fileMappingEntity;
  }

  public boolean isLanguageMappingEntity() {
    return languageMappingEntity;
  }

  public void setLanguageMappingEntity(boolean languageMappingEntity) {
    this.languageMappingEntity = languageMappingEntity;
  }


  public MultipleUnique[] getMultipleUniques() {
    return multipleUniques;
  }

  public void setMultipleUniques(MultipleUnique[] multipleUniques) {
    this.multipleUniques = multipleUniques;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof TableInfo) {
      TableInfo ti = (TableInfo)o;
      return this.compareTo(ti) == 0;
    } else {
      return false;
    }
  }
  
  public static String[] tableInfoToTableName(TableInfo[] items) {
    String[] names = new String[items.length];
    for (int i = 0; i < names.length; i++) {
      names[i] = items[i].getName();
    }
    return names;
  }

  public Map<String, String> getLabels() {
    return labels;
  }

  public void setLabels(Map<String, String> labels) {
    this.labels = labels;
  }

  public Map<String, String> getLabelsPlural() {
    return labelsPlural;
  }

  public void setLabelsPlural(Map<String, String> labelsPlural) {
    this.labelsPlural = labelsPlural;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public boolean isTranslationEntity() {
    return translationEntity;
  }

  public void setTranslationEntity(boolean translationEntity) {
    this.translationEntity = translationEntity;
  }

  public boolean isTranslationMapEntity() {
    return translationMapEntity;
  }

  public void setTranslationMapEntity(boolean translationMapEntity) {
    this.translationMapEntity = translationMapEntity;
  }

  
  
}

package org.fundaciobit.genapp.traductor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.generator.gui.SharedData;


/**
 * 
 * @author anadal
 *
 */
public class TraduccioGenAppFieldItem implements ITraduccioItem {

  final String key;
  final String type;

  final FieldInfo fieldInfo;

  public TraduccioGenAppFieldItem(String type, FieldInfo fieldInfo, String key) {
    super();
    this.key = key;
    this.type = type;
    this.fieldInfo = fieldInfo;
  }

  @Override
  public String getKey() {
    return this.key;
  }

  @Override
  public String getType() {
    return this.type;
  }

  @Override
  public String getStringValue(String lang) {
    return fieldInfo.getLabels().get(lang);
  }

  @Override
  public void setStringValue(String lang, String newValue) {
    fieldInfo.getLabels().put(lang, newValue);
  }

  @Override
  public Set<String> getLanguages() {
    
    return new HashSet<String>(Arrays.asList(SharedData.data.getLanguages()));
  }

}

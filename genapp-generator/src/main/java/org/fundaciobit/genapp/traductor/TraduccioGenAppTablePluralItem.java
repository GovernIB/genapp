package org.fundaciobit.genapp.traductor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.generator.gui.SharedData;

/**
 * 
 * @author anadal
 *
 */
public class TraduccioGenAppTablePluralItem implements ITraduccioItem {

  final String key;
  final String type;

  final TableInfo table;

  public TraduccioGenAppTablePluralItem(String type, TableInfo table, String key) {
    super();
    this.key = key;
    this.type = type;
    this.table = table;
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
    return table.getLabelsPlural().get(lang);
  }

  @Override
  public void setStringValue(String lang, String newValue) {
    table.getLabelsPlural().put(lang, newValue);
  }

  @Override
  public Set<String> getLanguages() {

    return new HashSet<String>(Arrays.asList(SharedData.data.getLanguages()));
  }

}

package org.fundaciobit.genapp.traductor;

import java.util.Set;

/**
 * 
 * @author anadal
 *
 */
public interface ITraduccioItem {

  public String getKey();

  public String getType();

  public String getStringValue(String lang);

  public void setStringValue(String lang, String newValue);

  public Set<String> getLanguages();
  
}

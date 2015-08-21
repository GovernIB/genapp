package org.fundaciobit.genapp.common.query;

import org.fundaciobit.genapp.common.i18n.I18NException;



/**
 * 
 * @author anadal
 *
 */
public class SelectConstant extends Select<String> {

  public final String value;
  
  
  
  /**
   * @param value
   */
  public SelectConstant(String value) {
    super();
    this.value = value;
  }

  @Override
  public String getSelectString() {
    // Indicam que aquest valor no serà visible en la sentència SQL
    return null;
  }

  @Override
  public String getFromObject(Object obj) throws I18NException {
    return value;
  }
  
  

}

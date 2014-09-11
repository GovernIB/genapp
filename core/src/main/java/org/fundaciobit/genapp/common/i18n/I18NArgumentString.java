package org.fundaciobit.genapp.common.i18n;

/**
 * 
 * @author anadal
 *
 */
public class I18NArgumentString implements I18NArgument {

  public final String message;

  /**
   * @param message
   */
  public I18NArgumentString(String message) {
    super();
    this.message = message;
  }

  public String getValue() {
    return this.message;
  }


  
  
  
}

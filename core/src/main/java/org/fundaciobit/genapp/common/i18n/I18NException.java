package org.fundaciobit.genapp.common.i18n;

/**
 * 
 * @author anadal
 * 
 *
 */
public class I18NException extends Throwable {

  /**
   * 
   */
  private static final long serialVersionUID = 125634754654675478L;
  
  
  protected final I18NTranslation traduccio;

  /**
   * @param traduccio
   */
  public I18NException(I18NTranslation traduccio) {
    super(traduccio.getCode());
    this.traduccio = traduccio;
  }

  /**
   * @param code
   * @param cause
   */
  public I18NException(Throwable cause, String code, I18NArgument ... args) {
    super(code, cause);
    this.traduccio = new I18NTranslation(code, args);
  }

  /**
   * @param code
   */
  public I18NException(String code, I18NArgument ...  args) {
    super(code);
    this.traduccio = new I18NTranslation(code, args);
  }
  
  
  /**
   * @param code
   */
  public I18NException(String code, String ...  args) {
    super(code);
    this.traduccio = new I18NTranslation(code, args);
  }
  
  /**
   * @param code
   */
  public I18NException(String code) {
    super(code);
    this.traduccio = new I18NTranslation(code);
  }
  
  public I18NTranslation getTraduccio() {
    return this.traduccio;
  }

  /*
  public I18NArgument[] getArgs() {
    return args;
  }
  
  public String getCode() {
    return this.getMessage();
  }
  */

}

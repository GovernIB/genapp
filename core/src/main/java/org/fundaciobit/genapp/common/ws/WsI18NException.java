package org.fundaciobit.genapp.common.ws;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.WebFault;

/**
 * 
 * @author anadal
 * 
 */
@WebFault(name = "WsI18NError")
@XmlSeeAlso({WsI18NTranslation.class})
public class WsI18NException extends Exception {
  
  @XmlElement
  protected WsI18NTranslation translation;
  
  /**
   *
   */
  public WsI18NException() {
    super();
  }
  
  /**
   * @param message
   */
  public WsI18NException(WsI18NTranslation translation, String message) {
    super(message);
    this.translation = translation;
  }
  
  /**
   * @param message
   */
  public WsI18NException(WsI18NTranslation translation, String message, Throwable cause) {
    super(message, cause);
    this.translation = translation;
  }
  

  public WsI18NTranslation getTranslation() {
    return translation;
  }

  public void setTranslation(WsI18NTranslation translation) {
    this.translation = translation;
  }
  
}
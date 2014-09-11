package org.fundaciobit.genapp.common.ws;

import java.util.List;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.WebFault;

/**
 * 
 * @author anadal
 * 
 */
@WebFault(name = "WsValidationErrors")
@XmlSeeAlso({WsFieldValidationError.class})
public class WsValidationException extends Exception {

  protected List<WsFieldValidationError> fieldFaults = null;

  /**
   * 
   */
  public WsValidationException() {
    super();
  }

  /**
   * @param message
   */
  public WsValidationException(String message) {
    super(message);
  }

  /**
   * @param fieldFaults
   */
  public WsValidationException(String message, List<WsFieldValidationError> fieldFaults) {
    super(message);
    this.fieldFaults = fieldFaults;
  }

  public List<WsFieldValidationError> getFieldFaults() {
    return fieldFaults;
  }

  public void setFieldFaults(List<WsFieldValidationError> fieldFaults) {
    this.fieldFaults = fieldFaults;
  }

}

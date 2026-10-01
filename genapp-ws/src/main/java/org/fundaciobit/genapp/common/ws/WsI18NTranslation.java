package org.fundaciobit.genapp.common.ws;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author anadal
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "code", "args"})
@XmlSeeAlso({WsI18NArgument.class})
public class WsI18NTranslation {

  @XmlElement(name="code")
  protected String code;
  
  @XmlElement(name="args")
  protected List<WsI18NArgument> args;
  
  /**
   * 
   */
  public WsI18NTranslation() {
  }
  
  /**
   * @param code
   */
  public WsI18NTranslation(String code, List<WsI18NArgument> args) {
    this.code = code;
    this.args = args;
  }
  
  
  /**
   * @param code
   */
  public WsI18NTranslation(String code) {
    this(code, null);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<WsI18NArgument> getArgs() {
    return args;
  }

  public void setArgs(List<WsI18NArgument> args) {
    this.args = args;
  }
  
}

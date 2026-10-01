package org.fundaciobit.genapp.common.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author anadal
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "value", "translate" })
public class WsI18NArgument {

  @XmlElement(name = "value")
  protected String value;

  @XmlElement(name = "translate")
  protected boolean translate;

  /**
   * 
   */
  public WsI18NArgument() {
    super();
  }

  /**
   * @param value
   */
  public WsI18NArgument(String value, boolean translate) {
    super();
    this.value = value;
    this.translate = translate;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isTranslate() {
    return translate;
  }

  public void setTranslate(boolean translate) {
    this.translate = translate;
  }

  @Override
  public String toString() {
    return (translate ? "*" : "") + this.value;
  }

}

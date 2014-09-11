package org.fundaciobit.genapp.common.web.form;

/**
 * 
 * @author anadal
 * 
 */
public class AdditionalButton {

  public String icon;

  /**
   * Identificador de la traducció pel texte En llistes s'usa com alternative
   * text (alt)
   */
  public String codeText;

  public String link; // {0} serà reemplaçat per l'ID de 'element si és una
                      // llista

  public String type; // css class (danger, info, primary, ...)

  public String getIcon() {
    return icon;
  }

  /**
   * @param icon
   * @param text
   * @param link
   * @param type
   */
  public AdditionalButton(String icon, String codeText, String link, String type) {
    super();
    this.icon = icon;
    this.codeText = codeText;
    this.link = link;
    this.type = type;
  }

  /**
   * 
   */
  public AdditionalButton() {
    super();
    // TODO Auto-generated constructor stub
  }

  public void setIcon(String icon) {
    this.icon = icon;
  }

  public String getCodeText() {
    return codeText;
  }

  public void setCodeText(String codeText) {
    this.codeText = codeText;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}

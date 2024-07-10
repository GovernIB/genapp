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

    public AdditionalButtonStyle style; // css class (danger, info, primary, ...)

    public String getIcon() {
        return icon;
    }

    
    /**
     * @param icon
     * @param text
     * @param link
     * @param style
     */
    public AdditionalButton(String icon, String codeText, String link, AdditionalButtonStyle style) {
        super();
        this.icon = icon;
        this.codeText = codeText;
        this.link = link;
        this.style = style;
    }
    
    /**
     * @param icon
     * @param text
     * @param link
     * @param type
     */
    @Deprecated
    public AdditionalButton(String icon, String codeText, String link, String type) {
        super();
        this.icon = icon;
        this.codeText = codeText;
        this.link = link;
        this.style = AdditionalButtonStyle.fromStyle(type);
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

    @Deprecated
    public String getType() {
        return this.style == null ? null : style.getStyle();
    }

    @Deprecated
    public void setType(String type) {
        this.style = AdditionalButtonStyle.fromStyle(type);
    }

    public AdditionalButtonStyle getStyle() {
        return style;
    }

    public void setStyle(AdditionalButtonStyle style) {
        this.style = style;
    }

    @Override
    public int hashCode() {
        return (this.icon + "==" + this.codeText + "==" + this.link + "==" + this.style).hashCode();
    }

}

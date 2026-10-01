package org.fundaciobit.genapp.common.web.form;

/**
 * 
 * Estils de botó:
 * PRIMARY  btn-primary
 * SECONDARY   btn-secondary
 * SUCCESS btn-success
 * DANGER  btn-danger
 * WARNING btn-warning
 * INFO    btn-info
 * LIGHT   btn-light
 * DARK    btn-dark
 * @author anadal
 */
public enum AdditionalButtonStyle {
    PRIMARY("btn-primary"), SECONDARY("btn-secondary"), SUCCESS("btn-success"), DANGER("btn-danger"),
    WARNING("btn-warning"), INFO("btn-info"), LIGHT("btn-light"), DARK("btn-dark");

    private String style;

    private AdditionalButtonStyle(String style) {
        this.style = style;
    }

    public String getStyle() {
        return style;
    }

    public static AdditionalButtonStyle fromStyle(String style) {
        if (style != null && !style.isEmpty()) {
            style = style.trim();
            for (AdditionalButtonStyle s : AdditionalButtonStyle.values()) {
                if (s.getStyle().equalsIgnoreCase(style)) {
                    return s;
                }
            }
        }
        
        return DARK;
    }

    @Override
    public String toString() {
        return this.getStyle();
    }
    
    
    public static void main(String[] args) {
        System.out.println(AdditionalButtonStyle.fromStyle("btn-primary"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-secondary"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-success"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-danger"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-warning"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-info"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-light"));
        System.out.println(AdditionalButtonStyle.fromStyle("btn-dark"));
    }
}

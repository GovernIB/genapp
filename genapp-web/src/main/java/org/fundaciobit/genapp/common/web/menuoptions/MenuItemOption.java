package org.fundaciobit.genapp.common.web.menuoptions;

/**
 * 
 * @author anadal
 * 10 ene 2025 9:04:24
 */
class MenuItemOption extends MenuItem {

    protected boolean addSeparatorBefore = false;

    /**
     * @return  Si es vol afegir un separador després de l'opció
     */
    protected boolean addSeparatorAfter = false;

    /**     
     * @return El Menu en que s'ha de ficar. "" Significa cercar a partir del context web
     */
    protected String group = "";
    
    
    /**
     * @return Si es vol que l'opció de menú sigui visible només si es compleix una condició.
     */
    public Class<? extends MenuOptionCondition> condition = null;

    
    public MenuItemOption(String label, String url, String urlbase, int order, boolean addSeparatorBefore,
            boolean addSeparatorAfter, String group, Class<? extends MenuOptionCondition> condition) {
        super(label, url, urlbase, order);
        this.addSeparatorBefore = addSeparatorBefore;
        this.addSeparatorAfter = addSeparatorAfter;
        this.group = group;
        this.condition = condition;
    }
    
    @Deprecated
    public MenuItemOption(String label, String url, String urlbase, int order, boolean addSeparatorBefore,
            boolean addSeparatorAfter, String group) {
        this(label, url, urlbase, order, addSeparatorBefore, addSeparatorAfter, group, null);
    }
    

    public MenuItemOption(String label, String url, int order) {
        super(label, url, order);
    }

    public MenuItemOption(String label, String url, String urlbase, int order) {
        super(label, url, urlbase, order);
    }

    public MenuItemOption(String label, String url, String urlbase) {
        super(label, url, urlbase);
    }

    public MenuItemOption(String label, String url) {
        super(label, url);
    }

    public boolean isAddSeparatorBefore() {
        return addSeparatorBefore;
    }

    public void setAddSeparatorBefore(boolean addSeparatorBefore) {
        this.addSeparatorBefore = addSeparatorBefore;
    }

    public boolean isAddSeparatorAfter() {
        return addSeparatorAfter;
    }

    public void setAddSeparatorAfter(boolean addSeparatorAfter) {
        this.addSeparatorAfter = addSeparatorAfter;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Class<? extends MenuOptionCondition> getCondition() {
        return condition;
    }

    public void setCondition(Class<? extends MenuOptionCondition> condition) {
        this.condition = condition;
    }

}

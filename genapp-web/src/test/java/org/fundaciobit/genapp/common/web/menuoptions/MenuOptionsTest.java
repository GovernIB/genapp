package org.fundaciobit.genapp.common.web.menuoptions;

import java.util.List;

/**
 * @author anadal
 */

public class MenuOptionsTest {
    
    public static final String MENUOPTION_GROUP_TEST="TEST";
    
    public static void main(String[] args) {
        try {
            
            MenuOptionManager.setDiscoverMenuOptionAnnotations(
                    new DiscoverMenuOptionAnnotations("org.fundaciobit.genapp.common.web.menuoptions"));

            List<MenuItem>  menus = MenuOptionManager.getMenuItems(MENUOPTION_GROUP_TEST);
            
            for (MenuItem menuItem : menus) {
                if (menuItem == null) {
                    System.out.println("-------------------------------------");
                } else {
                
                System.out.println(menuItem.getLabel() + " => " + menuItem.getUrlbase() + menuItem.getUrl());
                }
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

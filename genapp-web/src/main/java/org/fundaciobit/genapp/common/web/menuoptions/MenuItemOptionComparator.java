package org.fundaciobit.genapp.common.web.menuoptions;

import java.util.Comparator;

/**
 * 
 * @author anadal
 * 7 ene 2025 12:28:57
 */
class MenuItemOptionComparator implements Comparator<MenuItemOption> {

    @Override
    public int compare(MenuItemOption o1, MenuItemOption o2) {
        int o = o1.getOrder() - o2.getOrder();
        if (o == 0) {
            o = o1.getLabel().compareTo(o2.getLabel());
            if (o == 0) {
                o =  ("" + o1.getUrlbase()).compareTo(o2.getUrlbase());
            }
        }
        return o;
    }

}
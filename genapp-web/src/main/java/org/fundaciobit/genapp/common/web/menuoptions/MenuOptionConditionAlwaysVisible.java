package org.fundaciobit.genapp.common.web.menuoptions;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author anadal (u80067)
 * 18 nov 2026 13:00:21
 */
public class MenuOptionConditionAlwaysVisible implements MenuOptionCondition {

    @Override
    public boolean isVisible(HttpServletRequest r) {
        return true;
    }

}

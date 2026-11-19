package org.fundaciobit.genapp.common.web.menuoptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Serveix per saber si un menú s'ha de mostrar o no.    
 * 
 * @author anadal (u80067)
 * 18 nov 2026 12:57:58
 */
public interface MenuOptionCondition {

    /** Retorna true si l'opció s'ha de mostrar. */
    boolean isVisible(HttpServletRequest request) throws Exception;
}

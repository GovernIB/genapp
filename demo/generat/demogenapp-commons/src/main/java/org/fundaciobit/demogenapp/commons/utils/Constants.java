package org.fundaciobit.demogenapp.commons.utils;

/**
 *
 * @author anadal
 *
 */
public interface Constants {

  public static final String DEMOGENAPP_PROPERTY_BASE="org.fundaciobit.demogenapp.";

    public static final String MAIL_SERVICE = "java:/org.fundaciobit.demogenapp.mail";

    // TRUE ROLES
    public static final String DEM_ADMIN="DEM_ADMIN";
    public static final String DEM_USER="DEM_USER";
    public static final String DEM_WS="DEM_WS";

    // VIRTUAL SECURITY ROLES
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    
    // EJB HIGH LEVEL ROLES
    public static final String ROLE_EJB_FULL_ACCESS  = DEM_ADMIN;
    public static final String ROLE_EJB_BASIC_ACCESS = DEM_USER;
    public static final String ROLE_EJB_WS_ACCESS = DEM_WS;
}

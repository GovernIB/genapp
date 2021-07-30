package org.fundaciobit.genappsqltutorial.commons.utils;

/**
 *
 * @author anadal
 *
 */
public interface Constants {

  public static final String GENAPPSQLTUTORIAL_PROPERTY_BASE="org.fundaciobit.genappsqltutorial.";

    public static final String MAIL_SERVICE = "java:/org.fundaciobit.genappsqltutorial.mail";

    // TRUE ROLES
    public static final String GAS_ADMIN="GAS_ADMIN";
    public static final String GAS_USER="GAS_USER";

    // VIRTUAL SECURITY ROLES
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    
    // EJB HIGH LEVEL ROLES
    public static final String ROLE_EJB_FULL_ACCESS  = GAS_ADMIN;
    public static final String ROLE_EJB_BASIC_ACCESS = GAS_USER;

}

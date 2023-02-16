package ${package}.commons.utils;

/**
 *
 * @author anadal
 *
 */
public interface Constants {

  public static final String ${name_uppercase}_PROPERTY_BASE="${package}.";

    public static final String MAIL_SERVICE = "java:/${package}.mail";

    // TRUE ROLES
    public static final String ${prefix}_ADMIN="${prefix}_ADMIN";
    public static final String ${prefix}_USER="${prefix}_USER";
    public static final String ${prefix}_WS="${prefix}_WS";

    // VIRTUAL SECURITY ROLES
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    
    // EJB HIGH LEVEL ROLES
    public static final String ROLE_EJB_FULL_ACCESS  = ${prefix}_ADMIN;
    public static final String ROLE_EJB_BASIC_ACCESS = ${prefix}_USER;
    public static final String ROLE_EJB_WS_ACCESS = ${prefix}_WS;
}

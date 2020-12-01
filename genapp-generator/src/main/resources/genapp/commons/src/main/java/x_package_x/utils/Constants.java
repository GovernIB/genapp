package ${package}.utils;


/**
 *
 * @author anadal
 *
 */
public interface Constants {

  public static final String ${name_uppercase}_PROPERTY_BASE = "${package}.";
  public static final String ${name_uppercase}_PROPERTY_HIBERNATE = "hibernate.";
  public static final String ${name_uppercase}_PROPERTY_BASE_HIBERNATE = ${name_uppercase}_PROPERTY_BASE + ${name_uppercase}_PROPERTY_HIBERNATE;

  public static final String MAIL_SERVICE = "java:/${package}.mail";
 
  //  TRUE ROLES
  public static final String ${prefix}_ADMIN = "${prefix}_ADMIN";
  public static final String ${prefix}_USER = "${prefix}_USER";
  
  // VIRTUAL SECURITY ROLES
  public static final String ROLE_ADMIN = "ROLE_ADMIN";
  public static final String ROLE_USER = "ROLE_USER";  

}

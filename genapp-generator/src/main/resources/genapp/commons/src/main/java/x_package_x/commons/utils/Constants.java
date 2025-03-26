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
    <#list admin_roles as role>
    public static final String ${role?upper_case}="${role}";
    </#list>
    <#list user_roles as role>
    public static final String ${role?upper_case}="${role}";
    </#list>
    <#list ws_roles as role>
    public static final String ${role?upper_case}="${role}";
    </#list>

    // VIRTUAL SECURITY ROLES
    <#list virtual_roles as role>
    public static final String ${role?upper_case}="${role}";
    </#list>

    // EJB HIGH LEVEL ROLES
    <#list ejb_roles as role_key,role_value>
    public static final String ${role_key} = ${role_value};
    </#list>

}

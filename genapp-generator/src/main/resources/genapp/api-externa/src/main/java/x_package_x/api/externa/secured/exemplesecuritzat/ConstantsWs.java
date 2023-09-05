package ${package}.api.externa.secured.exemplesecuritzat;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

import ${package}.commons.utils.Constants;

/**
 * 
 * @author anadal
 *
 */

@Schema(name = "ConstantsWs")
public class ConstantsWs {

    @Schema(
            description = "Constant ROLE_EJB_FULL_ACCESS",
            nullable = false,
            defaultValue = "" + Constants.ROLE_EJB_FULL_ACCESS,
            implementation = String.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLE_EJB_FULL_ACCESS;

    @Schema(
            description = "Constant ROLE_EJB_BASIC_ACCESS",
            nullable = false,
            defaultValue = "" + Constants.ROLE_EJB_BASIC_ACCESS,
            implementation = Integer.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLE_EJB_BASIC_ACCESS;

    @Schema(
            description = "Constant ROLE_EJB_WS_ACCESS",
            nullable = false,
            defaultValue = "" + Constants.ROLE_EJB_BASIC_ACCESS,
            implementation = Integer.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLE_EJB_WS_ACCESS;

}
package ${package}.api.externa.secure.exemplesecuritzat;


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
            description = "Constant ROLE_EJB_FULL _ ACCESS",
            nullable = false,
            defaultValue = "Constants.ROLE_EJB_FULL _ ACCESS",
            implementation = String.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLEEJBFULLACCESS;

    @Schema(
            description = "Constant ROLE_EJB_BASIC _ ACCESS",
            nullable = false,
            defaultValue = "Constants.ROLE_EJB_BASIC _ACCESS",
            implementation = Integer.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLEEJBBASICACCESS;

    @Schema(
            description = "Constant ROLE_EJB_WS _ ACCESS",
            nullable = false,
            defaultValue = "Constants.ROLE_EJB_BASIC _ACCESS",
            implementation = Integer.class,
            required = true,
            accessMode = AccessMode.READ_ONLY)
    public String ROLEEJBWSACCESS;

}
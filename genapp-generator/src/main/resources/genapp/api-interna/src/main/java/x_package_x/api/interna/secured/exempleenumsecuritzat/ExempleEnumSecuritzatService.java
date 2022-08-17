package ${package}.api.interna.secured.exempleenumsecuritzat;

import javax.annotation.security.RolesAllowed;

import java.util.Locale;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ${package}.commons.utils.Constants;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.i18n.I18NCommonUtils;
import org.fundaciobit.genapp.common.i18n.I18NException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 *
 * @author anadal
 *
 */
@Path("/secured/exempleenum")
@OpenAPIDefinition(
        tags = @Tag(name = "Notificacions", description = "Notificacions a l'APP de Carpeta (missateg a Mòbil)"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "BasicAuth", scheme = "basic")
public class ExempleEnumSecuritzatService {

    protected Logger log = Logger.getLogger(ExempleEnumSecuritzatService.class);

    
    @Path("/sendnotificationtomobile")
	@GET
	@RolesAllowed({ Constants.${prefix}_ADMIN, Constants.${prefix}_USER })
	@SecurityRequirement(name = "BasicAuth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
            tags = { "Notificacions" },
            operationId = "sendNotificationToMobile",
            summary = "Envia un missatge al mòbil del ciutada a traves de l'App de Carpeta.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON)),
                    @ApiResponse(
                            responseCode = "401",
                            description = "No Autenticat",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON)),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No Autoritzat",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON)),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error durant el processament o enviament del missatge",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = SendMessageResult.class))),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Enviat missatge correctament",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = SendMessageResult.class))) })
    public Response sendNotificationToMobile(

            @Parameter(
                    description = "Exemple de Llistat d'String",
                    required = true,
                    example = "",
                    array = @ArraySchema(
                            schema = @Schema(type = "string"))) @NotNull @QueryParam("notificationParameters")
            String[] notificationParameters,

            @Parameter(
                    description = "Idioma en que s'enviaran els missatges d'error",
                    required = true,
                    example = "ca",
                    schema = @Schema(implementation = String.class)) @Pattern(
                            regexp = "^ca|es$") @QueryParam("langError")
            String langError) {

        try {

            switch ((int) (System.nanoTime() % 10)) {

                case 0:
                    return generateError(SendMessageResultCode.NOTIFICATION_CODE_DO_NOT_EXIST,
                            "El codi de notificacio és null o buit. ");

                case 1:
                    return generateError(SendMessageResultCode.NOTIFICATION_CODE_DO_NOT_EXIST,
                            "El codi de notificacio 'notificationCode' no està registrat."
                                    + "Consulti amb l'administrador de Carpeta.");

                case 2:
                    return generateError(SendMessageResultCode.ENTITYCODE_DO_NOT_EXIST,
                            "No existeix cap entitat dins Carpeta amb ID  `notificacio.getEntitatID()"
                                    + "`. Consulti amb l'administrador de Carpeta.");

                case 3:
                    return generateError(SendMessageResultCode.ENTITY_DISABLED,
                            "No existeix cap entitat dins Carpeta amb codi `entitat.getCodi()"
                                    + "`. Consulti amb l'administrador de Carpeta.");

                case 4:

                    return generateError(SendMessageResultCode.PLUGIN_DISABLED,
                            "El plugin `plugin.getNom().getTraduccio(langError)"
                                    + "` associat al codi de notificació  `notificationCode"
                                    + "` no està actiu. Consulti amb l'administrador de Carpeta.");

                case 5:

                    return generateError(SendMessageResultCode.PLUGIN_ENTITY_DO_NOT_EXIST,
                            "El plugin `plugin.getNom().getTraduccio(langError)"
                                    + "` associat al codi de notificació  `notificationCode"
                                    + "` no existeix en l'entitat `entitat.getCodi()"
                                    + "`. Consulti amb l'administrador de Carpeta.");
                case 6:

                    return generateError(SendMessageResultCode.PLUGIN_ENTITY_DISABLED,
                            "El plugin `plugin.getNom().getTraduccio(langError)"
                                    + "` associat al codi de notificació  `notificationCode"
                                    + "` en l'entitat `entitat.getCodi()"
                                    + "` no està actiu. Consulti amb l'administrador de Carpeta.");

                case 7:
                    return generateError(SendMessageResultCode.CITIZEN_DO_NOT_EXIST,
                            "No es té registrat el mòbil del ciutada/empresa amb NIF ]nif[");

                case 8:

                    SendMessageResult smr = new SendMessageResult();
                    smr.setCode(SendMessageResultCode.OK);
                    return Response.ok().entity(smr).build();

                default:
                case 9:

                    return generateError(SendMessageResultCode.ERROR_SENDING_NOTIFICATION, "Error");
            }

        } catch (Throwable th) {

            String msg;
            if (th instanceof I18NException) {
                I18NException ie = (I18NException) th;
                msg = I18NCommonUtils.getMessage(ie, new Locale(langError));
            } else {
                msg = th.getMessage();
            }

            log.error("Error desconegut en la cridada api rest enviar notificacions: " + msg, th);

            return generateError(SendMessageResultCode.UNKNOWN_ERROR, msg);

        }

    }

    protected Response generateError(SendMessageResultCode errorCode, String msg) {
        SendMessageResult smr = new SendMessageResult();
        smr.setCode(errorCode);
        smr.setMessage(msg);
        return Response.status(Response.Status.BAD_REQUEST).entity(smr).build();
    }


}

package ${package}.api.externa.secure.exemplesecuritzat;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import ${package}.commons.utils.Constants;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 * 
 * @author anadal
 *
 */
@Path("/secure/exempleseguritzat")
@OpenAPIDefinition(tags = { @Tag(name = ExempleSeguritzatService.TAG , description = "Exemple de Servei Securitzat"), })
@SecurityScheme(type = SecuritySchemeType.HTTP, name = ExempleSeguritzatService.SEC, scheme = "basic")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExempleSeguritzatService {
    
    protected static final String TAG = "ExempleSeguritzat";
    
    protected static final String SEC = "BasicAuth";

    protected static final Logger log = Logger.getLogger(ExempleSeguritzatService.class);

    @Path("/echo")
    @GET
    @RolesAllowed({ Constants.${prefix}_ADMIN, Constants.${prefix}_USER })
	@SecurityRequirement(name = SEC)  
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(operationId = "echo", summary = "Fa un ECHO", tags = { TAG })
    @ApiResponses(
            value = {
        @ApiResponse(
            responseCode = "200",
            description = "Respon el valor enviat per paràmetre",
            content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = String.class))),
        @ApiResponse(
            responseCode = "510",
            description = "Només s'utilitza per crear fitxer de constants...",
            content = { @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = ConstantsWs.class)) }) })
    public Response echo(@Parameter(
            description = "Cadena a retornar",
            required = false,
            example = "hola caracola",
            schema = @Schema(implementation = String.class)) @QueryParam("echoInput")  String echoInput) {

        log.info(" Entra a ECHO de Secure ... ");

        try {
            final String echoOutput = new String(echoInput);

            return Response.ok().entity(echoOutput).build();

        } catch (Exception e) {
            log.error("Error cridada api rest estadistiques accessos: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

}

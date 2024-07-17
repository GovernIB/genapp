package org.fundaciobit.demogenapp.api.externa.secure.exemplesecuritzat;

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
import org.fundaciobit.demogenapp.commons.utils.Constants;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
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
@OpenAPIDefinition(
        tags = @Tag(
              name = ExempleSeguritzatService.TAG ,
              description = "Exemple de Servei Securitzat"),
        info = @Info(
                title = "API REST EXTERNA de DemoGenApp - Exemple de Servei Securitzat",
                description = "Conjunt de Serveis REST de DemoGenApp per ser accedits emprant autenticació",
                version = "1.0-SNAPSHOT",
                license = @License(
                        name = "European Union Public Licence (EUPL v1.2)",
                        url = "https://joinup.ec.europa.eu/sites/default/files/custom-page/attachment/eupl_v1.2_es.pdf"),
                contact = @Contact(
                        name = "Departament de Govern Digital a la Fundació Bit",
                        email = "otae@fundaciobit.org",
                        url = "https://governdigital.fundaciobit.org")),
        externalDocs = @ExternalDocumentation(
                description = "Java Client (GovernIB Github)",
                url = "https://github.com/GovernIB/demogenapp/tree/demogenapp-1.0/demogenapp-api-externa-client-exemplesecure-v1"))
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = ExempleSeguritzatService.SEC, scheme = "basic")
public class ExempleSeguritzatService {

    protected Logger log = Logger.getLogger(ExempleSeguritzatService.class);
    
    protected static final String TAG = "ExempleSeguritzat";
    
    protected static final String SEC = "BasicAuth";



    @Path("/echo")
    @GET
    @RolesAllowed({ Constants.DEM_ADMIN, Constants.DEM_USER })
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

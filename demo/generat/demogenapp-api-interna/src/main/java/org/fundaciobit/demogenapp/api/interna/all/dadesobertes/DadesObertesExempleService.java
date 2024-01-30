package org.fundaciobit.demogenapp.api.interna.all.dadesobertes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.fundaciobit.pluginsib.utils.rest.RestException;
import org.fundaciobit.pluginsib.utils.rest.RestExceptionInfo;
import org.fundaciobit.pluginsib.utils.rest.RestUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

/**
 * Exemple de Servei de Dades obertes emprant Paginació de PluginsIB-Utils
 *
 */
@Path(DadesObertesExempleService.PATH)
@OpenAPIDefinition(
        tags = @Tag(
                name = DadesObertesExempleService.TAG_NAME,
                description = "Exemple de Servei de Dades obertes emprant Paginació de PluginsIB-Utils"))
public class DadesObertesExempleService extends RestUtils {

    protected static Logger log = Logger.getLogger(DadesObertesExempleService.class);

    public static final int DEFAULT_ITEMS_PER_PAGE = 15;

    // TODO Canviar 'items' pel nom dels objectes a retornar
    public static final String PATH = "/public/dadesobertes/items";

    public static final String TAG_NAME = "Items";

    @Path("/consulta")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(tags = TAG_NAME, operationId = "consulta", summary = "Retorna informació d'exemple per Dades Obertes")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Operació realitzada correctament",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = LlistaDeExempleInfoPaginada.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Paràmetres incorrectes",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = RestExceptionInfo.class))),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error no controlat",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = RestExceptionInfo.class))) })
    public LlistaDeExempleInfoPaginada consulta(
            @Parameter(
                    name = "name",
                    description = "Filtre pel Nom del Item. Opcional.",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = String.class)) @QueryParam("name") String name,
            @Parameter(
                    name = "startdate",
                    description = "Filtre Data d'inici de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601)",
                    required = false,
                    example = "2023-07-19",
                    in = ParameterIn.QUERY,
                    schema = @Schema(
                            implementation = String.class,
                            pattern = DATE_PATTERN_ISO8601_ONLYDATE)) @QueryParam("startdate") String startdate,

            @Parameter(
                    name = "enddate",
                    description = "Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601)",
                    required = false,
                    example = "2024-12-31",
                    in = ParameterIn.QUERY,
                    schema = @Schema(
                            implementation = String.class,
                            pattern = DATE_PATTERN_ISO8601_ONLYDATE)) @QueryParam("enddate") String enddate,
            @Parameter(
                    name = "page",
                    description = "Numero de pàgina quan el llistat és paginat. Per defecte 1.",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = Integer.class)) @QueryParam("page") Integer page,

            @Parameter(
                    name = "page-size",
                    description = "Número d'elements a retornar per pàgina. Per defecte " + DEFAULT_ITEMS_PER_PAGE,
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = Integer.class)) @QueryParam("page-size") Integer pageSize,

            @Parameter(
                    name = "language",
                    description = "Idioma en que retornar les dades. Opcional.",
                    required = false,
                    in = ParameterIn.QUERY,
                    schema = @Schema(implementation = String.class)) @QueryParam("language") String language,

            @Parameter(hidden = true) @Context HttpServletRequest request) throws RestException {

        // Check de page i pagesize
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_ITEMS_PER_PAGE;
        }

        // TODO Canviar request.getRequestURL() pel nom del servidor
        // En entorns CAIB no funciona  request.getRequestURL()
        StringBuilder nextQuery = new StringBuilder(request.getRequestURL() +  "?page=" + (page + 1) + "&page-size=" + pageSize);

        // Check de language
        if (StringUtils.isBlank(language)) {
            language = "ca";
        } else {
            if (!"es".equals(language) && !"ca".equals(language)) {
                language = "ca";
            }
            nextQuery.append("&language=" + language);
        }

        // Check de filtre per nom
        if (name != null) {
            nextQuery.append("&name=" + name);
        }

        // Convertir Dates a tipus Date, check de dates i check de rang
        Date dateStart;
        Date dateEnd;
        {
            Date[] dates = checkRangeOfOnlyDates(startdate, "startdate", enddate, "enddate");
            dateStart = dates[0];
            dateEnd = dates[1];
        }

        if (dateStart != null) {
            nextQuery.append("&startdate=" + startdate);
        }
        if (dateEnd != null) {
            nextQuery.append("&enddate=" + enddate);
        }

        // Realitzar Consulta
        try {
            LlistaDeExempleInfoPaginada paginacio = consultaInternaBBDD(name, dateStart, dateEnd, page, pageSize, language,
                    nextQuery.toString());
            return paginacio;
        } catch (Throwable th) {
            String msg = th.getMessage();
            log.error("Error desconegut retornant dades obertes: " + msg, th);
            throw new RestException(msg, th, Status.INTERNAL_SERVER_ERROR);
        }

    }

    protected static final ExempleInfo[] BBDD;

    static {
        // Simula una BBDD
        BBDD = new ExempleInfo[40];
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < BBDD.length; i++) {
            BBDD[i] = new ExempleInfo(i, "nom_" + i, "valor_" + i, new Timestamp(cal.getTimeInMillis()));
            cal.add(Calendar.DATE, 1);
        }
    }

    // TODO Aquest mètode s'ha de canviar
    protected LlistaDeExempleInfoPaginada consultaInternaBBDD(String name, Date startdate, Date enddate, int page,
            int pageSize, String language, String nextUrl) throws Exception {

        // TODO Com que això es un exemple de funcionament, no s'ha implementat els filtres per nom i dates

        List<ExempleInfo> data = new ArrayList<ExempleInfo>();

        int from = (page - 1) * pageSize;
        int to = Math.min(BBDD.length, from + pageSize);
        if (from < to) {
            for (int i = from; i < to; i++) {
                data.add(BBDD[i]);
            }
        }

        final int totalcount = BBDD.length;
        final int totalPages = (int) (totalcount / pageSize) + ((totalcount % pageSize == 0) ? 0 : 1);
        final String dateDownload = convertToDateTimeToISO8601(new Date());

        if (page >= totalPages) {
            nextUrl = null;
        }

        return new LlistaDeExempleInfoPaginada(data, page, pageSize, totalPages, totalcount, nextUrl, dateDownload);
    }
}

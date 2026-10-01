package org.fundaciobit.demogenapp.api.externa.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

/**
 * 
 * @author anadal
 *
 */
@OpenAPIDefinition(
        servers = {
                @Server(url = "/demogenappapi/externa"),
                @Server(url = "http://localhost:8080/demogenappapi/externa"),
                @Server(url = "https://dev.caib.es/demogenappapi/externa"),
                @Server(url = "https://proves.caib.es/demogenappapi/externa"),
                @Server(url = "https://se.caib.es/demogenappapi/externa"),
                @Server(url = "https://www.caib.es/demogenappapi/externa")
            }
)
@ApplicationPath("/")
public class JAXRSConfiguration extends Application {

    protected Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    /**
     * Les aplicacions JAX-RS necessiten un constructor buid.
     */
    public JAXRSConfiguration() {
    }

    /**
     * Podem introduir tasques a realitzar per la inicialització de l'API REST.
     */
    @PostConstruct
    private void init() {
        log.info("Iniciant API REST EXTERNA de DemoGenApp");
    }

}

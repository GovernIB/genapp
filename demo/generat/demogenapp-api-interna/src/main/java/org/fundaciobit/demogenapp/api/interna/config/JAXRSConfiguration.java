package org.fundaciobit.demogenapp.api.interna.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
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
        servers = { @Server(url = "/demogenappapi/interna"),
                @Server(url = "http://localhost:8080/demogenappapi/interna"),
                @Server(url = "https://dev.caib.es/demogenappapi/interna"),
                @Server(url = "https://proves.caib.es/demogenappapi/interna"),
                @Server(url = "https://se.caib.es/demogenappapi/interna"),
                @Server(url = "https://www.caib.es/demogenappapi/interna") 
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
        log.info("Iniciant API REST INTERNA de DemoGenApp");
    }

}

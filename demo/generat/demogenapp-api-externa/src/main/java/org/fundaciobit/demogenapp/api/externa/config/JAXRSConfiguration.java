package org.fundaciobit.demogenapp.api.externa.config;

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
        info = @Info(
                title = "API REST EXTERNA de DemoGenApp",
                description = "Conjunt de Serveis REST de DemoGenApp per ser accedits des de l'exterior", 
                version = "1.0.0",
                license = @License(
                        name = "License Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"),
                contact = @Contact(
                        name = "Departament de Govern Digital a la Fundació Bit",
                        email = "otae@fundaciobit.org",
                        url = "http://otae.fundaciobit.org")

        ),
        servers = {
                @Server(url = "/demogenappapi/externa"),
                @Server(url = "http://localhost:8080/demogenappapi/externa"),
                @Server(url = "https://dev.caib.es/demogenappapi/externa"),
                @Server(url = "https://proves.caib.es/demogenappapi/externa"),
                @Server(url = "https://se.caib.es/demogenappapi/externa"),
                @Server(url = "https://www.caib.es/demogenappapi/externa") },
        externalDocs = @ExternalDocumentation(
                description = "Java Client (GovernIB Github)",
                url = "https://github.com/GovernIB/demogenapp/tree/demogenapp-1.0/demogenapp-api-externa-client")

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
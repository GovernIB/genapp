# demogenapp-api-interna-client-exemplesecure-v1

API REST INTERNA de DemoGenApp - Exemple de Servei Securitzat

- API version: 1.0-SNAPSHOT

Conjunt de Serveis REST de DemoGenApp per ser accedits emprant autenticació

  For more information, please visit [https://governdigital.fundaciobit.org](https://governdigital.fundaciobit.org)

*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*

## Requirements

Building the API client library requires:

1. Java 1.8+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>org.fundaciobit.demogenapp</groupId>
  <artifactId>demogenapp-api-interna-client-exemplesecure-v1</artifactId>
  <version>1.0-SNAPSHOT</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
  repositories {
    mavenCentral()     // Needed if the 'demogenapp-api-interna-client-exemplesecure-v1' jar has been published to maven central.
    mavenLocal()       // Needed if the 'demogenapp-api-interna-client-exemplesecure-v1' jar has been published to the local maven repo.
  }

  dependencies {
     implementation "org.fundaciobit.demogenapp:demogenapp-api-interna-client-exemplesecure-v1:1.0-SNAPSHOT"
  }
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

- `target/demogenapp-api-interna-client-exemplesecure-v1-1.0-SNAPSHOT.jar`
- `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import org.fundaciobit.demogenapp.apiinterna.client.services.*;
import org.fundaciobit.demogenapp.apiinterna.client.services.auth.*;
import org.fundaciobit.demogenapp.apiinterna.client.model.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.NotificacionsApi;

public class NotificacionsApiExample {

    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");
        
        // Configure HTTP basic authorization: BasicAuth
        HttpBasicAuth BasicAuth = (HttpBasicAuth) defaultClient.getAuthentication("BasicAuth");
        BasicAuth.setUsername("YOUR USERNAME");
        BasicAuth.setPassword("YOUR PASSWORD");

        NotificacionsApi apiInstance = new NotificacionsApi(defaultClient);
        Integer page = 1; // Integer | Pàgina de la que es volen obtenir les dades. Comença per 1.
        Integer pagesize = 10; // Integer | Quantitat d'elements a retornar
        String language = "ca"; // String | Idioma en que s'han de retornar les dades(Només suportat 'ca' o 'es')
        try {
            TipusDocumentalsPaginacio result = apiInstance.callList(page, pagesize, language);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NotificacionsApi#callList");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to */demogenappapi/interna*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*NotificacionsApi* | [**callList**](docs/NotificacionsApi.md#callList) | **GET** /secure/exempleenum/list | Retorna un llistat de tipus documentals
*NotificacionsApi* | [**sendNotificationToMobile**](docs/NotificacionsApi.md#sendNotificationToMobile) | **GET** /secure/exempleenum/sendnotificationtomobile | Envia un missatge al mòbil del ciutada a traves de l&#39;App de Carpeta.


## Documentation for Models

 - [RestExceptionInfo](docs/RestExceptionInfo.md)
 - [SendMessageResult](docs/SendMessageResult.md)
 - [SendMessageResultCode](docs/SendMessageResultCode.md)
 - [TipusDocumentalsPaginacio](docs/TipusDocumentalsPaginacio.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization


Authentication schemes defined for the API:
<a id="BasicAuth"></a>
### BasicAuth


- **Type**: HTTP basic authentication


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

otae@fundaciobit.org

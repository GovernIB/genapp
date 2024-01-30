# NotificacionsApi

All URIs are relative to */demogenappapi/interna*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**callList**](NotificacionsApi.md#callList) | **GET** /secure/exempleenum/list | Retorna un llistat de tipus documentals |
| [**sendNotificationToMobile**](NotificacionsApi.md#sendNotificationToMobile) | **GET** /secure/exempleenum/sendnotificationtomobile | Envia un missatge al mòbil del ciutada a traves de l&#39;App de Carpeta. |



## callList

> TipusDocumentalsPaginacio callList(inici, fi, page, pagesize, language)

Retorna un llistat de tipus documentals

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.auth.*;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.NotificacionsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");
        
        // Configure HTTP basic authorization: BasicAuth
        HttpBasicAuth BasicAuth = (HttpBasicAuth) defaultClient.getAuthentication("BasicAuth");
        BasicAuth.setUsername("YOUR USERNAME");
        BasicAuth.setPassword("YOUR PASSWORD");

        NotificacionsApi apiInstance = new NotificacionsApi(defaultClient);
        String inici = "2022-08-29"; // String | Data d'inici, en format yyyy-MM-dd (ISO 8601), a partir de la qual volem obtenir dades
        String fi = "2023-12-31"; // String | Data fi, en format yyyy-MM-dd (ISO 8601), fins la qual volem tenir dades
        Integer page = 1; // Integer | Pàgina de la que es volen obtenir les dades. Comença per 1.
        Integer pagesize = 10; // Integer | Quantitat d'elements a retornar
        String language = "ca"; // String | Idioma en que s'han de retornar les dades(Només suportat 'ca' o 'es')
        try {
            TipusDocumentalsPaginacio result = apiInstance.callList(inici, fi, page, pagesize, language);
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

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **inici** | **String**| Data d&#39;inici, en format yyyy-MM-dd (ISO 8601), a partir de la qual volem obtenir dades | [optional] |
| **fi** | **String**| Data fi, en format yyyy-MM-dd (ISO 8601), fins la qual volem tenir dades | [optional] |
| **page** | **Integer**| Pàgina de la que es volen obtenir les dades. Comença per 1. | [optional] |
| **pagesize** | **Integer**| Quantitat d&#39;elements a retornar | [optional] |
| **language** | **String**| Idioma en que s&#39;han de retornar les dades(Només suportat &#39;ca&#39; o &#39;es&#39;) | [optional] |

### Return type

[**TipusDocumentalsPaginacio**](TipusDocumentalsPaginacio.md)

### Authorization

[BasicAuth](../README.md#BasicAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **400** | EFIB: Paràmetres incorrectes |  -  |
| **401** | EFIB: No Autenticat |  -  |
| **403** | EFIB: No Autoritzat |  -  |
| **500** | EFIB: Error durant la consulta de les dades obertes |  -  |
| **200** | EFIB: Retornades dades obertes correctament |  -  |


## sendNotificationToMobile

> SendMessageResult sendNotificationToMobile(notificationParameters, langError)

Envia un missatge al mòbil del ciutada a traves de l&#39;App de Carpeta.

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.auth.*;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.NotificacionsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");
        
        // Configure HTTP basic authorization: BasicAuth
        HttpBasicAuth BasicAuth = (HttpBasicAuth) defaultClient.getAuthentication("BasicAuth");
        BasicAuth.setUsername("YOUR USERNAME");
        BasicAuth.setPassword("YOUR PASSWORD");

        NotificacionsApi apiInstance = new NotificacionsApi(defaultClient);
        List<String> notificationParameters = Arrays.asList(); // List<String> | Exemple de parametre Llistat d'Strings
        String langError = "ca"; // String | Idioma en que s'enviaran els missatges d'error
        try {
            SendMessageResult result = apiInstance.sendNotificationToMobile(notificationParameters, langError);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling NotificacionsApi#sendNotificationToMobile");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **notificationParameters** | [**List&lt;String&gt;**](String.md)| Exemple de parametre Llistat d&#39;Strings | |
| **langError** | **String**| Idioma en que s&#39;enviaran els missatges d&#39;error | |

### Return type

[**SendMessageResult**](SendMessageResult.md)

### Authorization

[BasicAuth](../README.md#BasicAuth)

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operació realitzada correctament |  -  |
| **400** | Paràmetres incorrectes |  -  |
| **401** | No Autenticat |  -  |
| **403** | No Autoritzat |  -  |
| **500** | Error no controlat |  -  |


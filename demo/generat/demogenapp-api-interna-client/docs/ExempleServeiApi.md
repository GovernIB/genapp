# ExempleServeiApi

All URIs are relative to */demogenappapi/interna*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**consultapaginada**](ExempleServeiApi.md#consultapaginada) | **GET** /public/exempleservei/exempleconsultapaginada | Retorna informació d&#39;exemple paginada |
| [**exempleconsultacompleta**](ExempleServeiApi.md#exempleconsultacompleta) | **GET** /public/exempleservei/exempleconsultacompleta | Retorna tots els elements d&#39;informació d&#39;exemple d&#39;una vegada |



## consultapaginada

> LlistaDeExempleInfoPaginada consultapaginada(name, startdate, enddate, page, pageSize, language)

Retorna informació d&#39;exemple paginada

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.ExempleServeiApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");

        ExempleServeiApi apiInstance = new ExempleServeiApi(defaultClient);
        String name = "name_example"; // String | Filtre pel Nom de l'objecte ExempleInfo. Opcional.
        String startdate = "2023-07-19"; // String | Filtre Data d'inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601)
        String enddate = "2024-12-31"; // String | Filtre Data final de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601)
        Integer page = 56; // Integer | Numero de pàgina quan el llistat és paginat. Opcional. Per defecte 1.
        Integer pageSize = 56; // Integer | Número d'elements a retornar per pàgina. Opcional. Per defecte 15
        String language = "ca"; // String | Idioma en que s'han de retornar les dades(Només suportat 'ca' o 'es')
        try {
            LlistaDeExempleInfoPaginada result = apiInstance.consultapaginada(name, startdate, enddate, page, pageSize, language);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExempleServeiApi#consultapaginada");
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
| **name** | **String**| Filtre pel Nom de l&#39;objecte ExempleInfo. Opcional. | [optional] |
| **startdate** | **String**| Filtre Data d&#39;inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) | [optional] |
| **enddate** | **String**| Filtre Data final de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) | [optional] |
| **page** | **Integer**| Numero de pàgina quan el llistat és paginat. Opcional. Per defecte 1. | [optional] |
| **pageSize** | **Integer**| Número d&#39;elements a retornar per pàgina. Opcional. Per defecte 15 | [optional] |
| **language** | **String**| Idioma en que s&#39;han de retornar les dades(Només suportat &#39;ca&#39; o &#39;es&#39;) | [optional] [default to ca] |

### Return type

[**LlistaDeExempleInfoPaginada**](LlistaDeExempleInfoPaginada.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operació realitzada correctament |  -  |
| **404** | Paràmetres incorrectes |  -  |
| **500** | Error no controlat |  -  |


## exempleconsultacompleta

> LlistaDeExempleInfoCompleta exempleconsultacompleta(name, startdate, enddate, language)

Retorna tots els elements d&#39;informació d&#39;exemple d&#39;una vegada

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.ExempleServeiApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");

        ExempleServeiApi apiInstance = new ExempleServeiApi(defaultClient);
        String name = "name_example"; // String | Filtre pel Nom de l'objecte ExempleInfo. Opcional.
        String startdate = "2023-07-19"; // String | Filtre Data d'inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601)
        String enddate = "2024-12-31"; // String | Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601)
        String language = "ca"; // String | Idioma en que s'han de retornar les dades(Només suportat 'ca' o 'es')
        try {
            LlistaDeExempleInfoCompleta result = apiInstance.exempleconsultacompleta(name, startdate, enddate, language);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ExempleServeiApi#exempleconsultacompleta");
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
| **name** | **String**| Filtre pel Nom de l&#39;objecte ExempleInfo. Opcional. | [optional] |
| **startdate** | **String**| Filtre Data d&#39;inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) | [optional] |
| **enddate** | **String**| Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601) | [optional] |
| **language** | **String**| Idioma en que s&#39;han de retornar les dades(Només suportat &#39;ca&#39; o &#39;es&#39;) | [optional] [default to ca] |

### Return type

[**LlistaDeExempleInfoCompleta**](LlistaDeExempleInfoCompleta.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Operació realitzada correctament |  -  |
| **404** | Paràmetres incorrectes |  -  |
| **500** | Error no controlat |  -  |


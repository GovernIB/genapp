# ItemsApi

All URIs are relative to */demogenappapi/interna*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**consulta**](ItemsApi.md#consulta) | **GET** /public/dadesobertes/items/consulta | Retorna informació d&#39;exemple per Dades Obertes |



## consulta

> LlistaDeExempleInfoPaginada consulta(name, startdate, enddate, page, pageSize, language)

Retorna informació d&#39;exemple per Dades Obertes

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.ItemsApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");

        ItemsApi apiInstance = new ItemsApi(defaultClient);
        String name = "name_example"; // String | Filtre pel Nom del Item. Opcional.
        String startdate = "2023-07-19"; // String | Filtre Data d'inici de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601)
        String enddate = "2024-12-31"; // String | Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601)
        Integer page = 56; // Integer | Numero de pàgina quan el llistat és paginat. Per defecte 1.
        Integer pageSize = 56; // Integer | Número d'elements a retornar per pàgina. Per defecte 15
        String language = "language_example"; // String | Idioma en que retornar les dades. Opcional.
        try {
            LlistaDeExempleInfoPaginada result = apiInstance.consulta(name, startdate, enddate, page, pageSize, language);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling ItemsApi#consulta");
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
| **name** | **String**| Filtre pel Nom del Item. Opcional. | [optional] |
| **startdate** | **String**| Filtre Data d&#39;inici de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601) | [optional] |
| **enddate** | **String**| Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601) | [optional] |
| **page** | **Integer**| Numero de pàgina quan el llistat és paginat. Per defecte 1. | [optional] |
| **pageSize** | **Integer**| Número d&#39;elements a retornar per pàgina. Per defecte 15 | [optional] |
| **language** | **String**| Idioma en que retornar les dades. Opcional. | [optional] |

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


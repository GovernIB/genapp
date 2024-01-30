# VersioApi

All URIs are relative to */demogenappapi/externa*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**versio**](VersioApi.md#versio) | **GET** /public/exemplepublic/versio | Versio de l&#39;Aplicació |



## versio

> ExamplePojo versio(idioma)

Versio de l&#39;Aplicació

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiexterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiexterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiexterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiexterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiexterna.client.api.VersioApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/externa");

        VersioApi apiInstance = new VersioApi(defaultClient);
        String idioma = "ca"; // String | Codi de l'idioma
        try {
            ExamplePojo result = apiInstance.versio(idioma);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling VersioApi#versio");
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
| **idioma** | **String**| Codi de l&#39;idioma | [optional] |

### Return type

[**ExamplePojo**](ExamplePojo.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **404** | Paràmetres incorrectes |  -  |
| **200** | Versió i idioma |  -  |


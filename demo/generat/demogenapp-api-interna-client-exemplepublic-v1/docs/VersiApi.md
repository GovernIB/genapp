# VersiApi

All URIs are relative to */demogenappapi/interna*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**versio**](VersiApi.md#versio) | **GET** /public/exemplepublic/versio | Versio de l&#39;Aplicació |



## versio

> ExamplePojo versio(idioma)

Versio de l&#39;Aplicació

### Example

```java
// Import classes:
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.models.*;
import org.fundaciobit.demogenapp.apiinterna.client.api.VersiApi;

public class Example {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("/demogenappapi/interna");

        VersiApi apiInstance = new VersiApi(defaultClient);
        String idioma = "ca"; // String | Codi de l'idioma
        try {
            ExamplePojo result = apiInstance.versio(idioma);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling VersiApi#versio");
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


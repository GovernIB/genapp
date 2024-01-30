package org.fundaciobit.demogenapp.apiexterna.client.api;

import org.fundaciobit.demogenapp.apiexterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiexterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiexterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiexterna.client.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.demogenapp.apiexterna.client.model.ConstantsWs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ExempleSeguritzatApi {
  private ApiClient apiClient;

  public ExempleSeguritzatApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ExempleSeguritzatApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Fa un ECHO
   * 
   * @param echoInput Cadena a retornar (optional)
   * @return a {@code String}
   * @throws ApiException if fails to make API call
   */
  public String echo(String echoInput) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/secure/exempleseguritzat/echo".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "echoInput", echoInput));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "BasicAuth" };

    GenericType<String> localVarReturnType = new GenericType<String>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

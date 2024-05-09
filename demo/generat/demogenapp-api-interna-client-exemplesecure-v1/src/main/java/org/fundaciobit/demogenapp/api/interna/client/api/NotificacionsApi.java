package org.fundaciobit.demogenapp.apiinterna.client.api;

import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.demogenapp.apiinterna.client.model.RestExceptionInfo;
import org.fundaciobit.demogenapp.apiinterna.client.model.SendMessageResult;
import org.fundaciobit.demogenapp.apiinterna.client.model.TipusDocumentalsPaginacio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class NotificacionsApi {
  private ApiClient apiClient;

  public NotificacionsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public NotificacionsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Retorna un llistat de tipus documentals
   * 
   * @param page Pàgina de la que es volen obtenir les dades. Comença per 1. (optional)
   * @param pagesize Quantitat d&#39;elements a retornar (optional)
   * @param language Idioma en que s&#39;han de retornar les dades(Només suportat &#39;ca&#39; o &#39;es&#39;) (optional)
   * @return a {@code TipusDocumentalsPaginacio}
   * @throws ApiException if fails to make API call
   */
  public TipusDocumentalsPaginacio callList(Integer page, Integer pagesize, String language) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/secure/exempleenum/list".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "pagesize", pagesize));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "language", language));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "BasicAuth" };

    GenericType<TipusDocumentalsPaginacio> localVarReturnType = new GenericType<TipusDocumentalsPaginacio>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Envia un missatge al mòbil del ciutada a traves de l&#39;App de Carpeta.
   * 
   * @param notificationParameters Exemple de parametre Llistat d&#39;Strings (required)
   * @param langError Idioma en que s&#39;enviaran els missatges d&#39;error (required)
   * @return a {@code SendMessageResult}
   * @throws ApiException if fails to make API call
   */
  public SendMessageResult sendNotificationToMobile(List<String> notificationParameters, String langError) throws ApiException {
    Object localVarPostBody = null;
    
    // verify the required parameter 'notificationParameters' is set
    if (notificationParameters == null) {
      throw new ApiException(400, "Missing the required parameter 'notificationParameters' when calling sendNotificationToMobile");
    }
    
    // verify the required parameter 'langError' is set
    if (langError == null) {
      throw new ApiException(400, "Missing the required parameter 'langError' when calling sendNotificationToMobile");
    }
    
    // create path and map variables
    String localVarPath = "/secure/exempleenum/sendnotificationtomobile".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("multi", "notificationParameters", notificationParameters));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "langError", langError));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] { "BasicAuth" };

    GenericType<SendMessageResult> localVarReturnType = new GenericType<SendMessageResult>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

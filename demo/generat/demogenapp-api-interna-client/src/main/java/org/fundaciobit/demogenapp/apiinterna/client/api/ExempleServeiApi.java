package org.fundaciobit.demogenapp.apiinterna.client.api;

import org.fundaciobit.demogenapp.apiinterna.client.services.ApiException;
import org.fundaciobit.demogenapp.apiinterna.client.services.ApiClient;
import org.fundaciobit.demogenapp.apiinterna.client.services.Configuration;
import org.fundaciobit.demogenapp.apiinterna.client.services.Pair;

import javax.ws.rs.core.GenericType;

import org.fundaciobit.demogenapp.apiinterna.client.model.LlistaDeExempleInfoCompleta;
import org.fundaciobit.demogenapp.apiinterna.client.model.LlistaDeExempleInfoPaginada;
import org.fundaciobit.demogenapp.apiinterna.client.model.RestExceptionInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen")
public class ExempleServeiApi {
  private ApiClient apiClient;

  public ExempleServeiApi() {
    this(Configuration.getDefaultApiClient());
  }

  public ExempleServeiApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * Retorna informació d&#39;exemple paginada
   * 
   * @param name Filtre pel Nom de l&#39;objecte ExempleInfo. Opcional. (optional)
   * @param startdate Filtre Data d&#39;inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) (optional)
   * @param enddate Filtre Data final de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) (optional)
   * @param page Numero de pàgina quan el llistat és paginat. Opcional. Per defecte 1. (optional)
   * @param pageSize Número d&#39;elements a retornar per pàgina. Opcional. Per defecte 15 (optional)
   * @param language Idioma en que retornar les dades. Opcional. (optional)
   * @return a {@code LlistaDeExempleInfoPaginada}
   * @throws ApiException if fails to make API call
   */
  public LlistaDeExempleInfoPaginada consultapaginada(String name, String startdate, String enddate, Integer page, Integer pageSize, String language) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/public/exempleservei/exempleconsultapaginada".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "name", name));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "startdate", startdate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "enddate", enddate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page", page));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "page-size", pageSize));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "language", language));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LlistaDeExempleInfoPaginada> localVarReturnType = new GenericType<LlistaDeExempleInfoPaginada>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
  /**
   * Retorna tots els elements d&#39;informació d&#39;exemple d&#39;una vegada
   * 
   * @param name Filtre pel Nom de l&#39;objecte ExempleInfo. Opcional. (optional)
   * @param startdate Filtre Data d&#39;inici de la consulta. Opcional. Format yyyy-MM-dd (ISO 8601) (optional)
   * @param enddate Filtre Data final de la consulta. Opcional. Format  yyyy-MM-dd (ISO 8601) (optional)
   * @param language Idioma en que retornar les dades. Opcional. (optional)
   * @return a {@code LlistaDeExempleInfoCompleta}
   * @throws ApiException if fails to make API call
   */
  public LlistaDeExempleInfoCompleta exempleconsultacompleta(String name, String startdate, String enddate, String language) throws ApiException {
    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/public/exempleservei/exempleconsultacompleta".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, String> localVarCookieParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "name", name));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "startdate", startdate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "enddate", enddate));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "language", language));

    
    
    
    final String[] localVarAccepts = {
      "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    String[] localVarAuthNames = new String[] {  };

    GenericType<LlistaDeExempleInfoCompleta> localVarReturnType = new GenericType<LlistaDeExempleInfoCompleta>() {};
    return apiClient.invokeAPI(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAccept, localVarContentType, localVarAuthNames, localVarReturnType);
      }
}

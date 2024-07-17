/*
 * API REST INTERNA de DemoGenApp - Exemple de Servei Securitzat
 * Conjunt de Serveis REST de DemoGenApp per ser accedits emprant autenticació
 *
 * The version of the OpenAPI document: 1.0-SNAPSHOT
 * Contact: otae@fundaciobit.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.fundaciobit.demogenapp.apiexterna.client.exemplesecure.v1.services.auth;

import org.fundaciobit.demogenapp.apiexterna.client.exemplesecure.v1.services.Pair;

import java.util.Map;
import java.util.List;

public interface Authentication {
    /**
     * Apply authentication settings to header and query params.
     *
     * @param queryParams List of query parameters
     * @param headerParams Map of header parameters
     * @param cookieParams Map of cookie parameters
     */
    void applyToParams(List<Pair> queryParams, Map<String, String> headerParams, Map<String, String> cookieParams);
}

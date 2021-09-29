package ${package}.ws.v1.impl;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;

import org.jboss.wsf.spi.annotation.TransportGuarantee;
import org.jboss.wsf.spi.annotation.WebContext;

import javax.validation.constraints.Null;

import org.fundaciobit.genapp.common.ws.WsI18NException;
import org.fundaciobit.genapp.common.ws.WsValidationException;

import ${package}.utils.Constants;
import ${package}.ws.utils.AuthenticatedBaseWsImpl;


/**
 * 
 * @author anadal
 * 
 */
@Stateless(name = ${fullname}HelloWorldWithSecurityWsImpl.NAME + "Ejb")
@RolesAllowed({ Constants.${prefix}_USER, Constants.${prefix}_ADMIN })
@SOAPBinding(style = SOAPBinding.Style.RPC)
@org.apache.cxf.interceptor.InInterceptors(interceptors = { "${package}.ws.utils.${fullname}InInterceptor" })
@org.apache.cxf.interceptor.InFaultInterceptors(interceptors = { "${package}.ws.utils.${fullname}InInterceptor" })
@WebService(name = ${fullname}HelloWorldWithSecurityWsImpl.NAME_WS, portName = ${fullname}HelloWorldWithSecurityWsImpl.NAME_WS, serviceName = ${fullname}HelloWorldWithSecurityWsImpl.NAME_WS
    + "Service")
@WebContext(contextRoot = "/${name}/ws", urlPattern = "/v1/"
    + ${fullname}HelloWorldWithSecurityWsImpl.NAME, transportGuarantee = TransportGuarantee.NONE, secureWSDLAccess = false, authMethod = "WSBASIC")
public class ${fullname}HelloWorldWithSecurityWsImpl extends AuthenticatedBaseWsImpl {

  public static final String NAME = "${fullname}HelloWorldWithSecurity";

  public static final String NAME_WS = NAME + "Ws";
  

  @Resource
  private WebServiceContext wsContext;

  @RolesAllowed({ ${prefix}_ADMIN, ${prefix}_USER})
  @WebMethod
  public String echo(@WebParam (name ="echo") @Null String echo) throws WsValidationException, WsI18NException, Throwable {

  	/* Quan hi hagi fixers
  	 ${fitxer}PA.enableEncryptedFileIDGeneration();
  	try {
  	*/
    log.info("${fullname}HelloWorldWithSecurityWsImpl :: echo = " + echo);
    return "USER: " + wsContext.getUserPrincipal().getName() + " | ECHO: " + echo;
  	/* Quan hi hagi fixers
  	} finally {
  	  ${fitxer}JPA.disableEncryptedFileIDGeneration();
  	}      
  	*/
  }
  

}

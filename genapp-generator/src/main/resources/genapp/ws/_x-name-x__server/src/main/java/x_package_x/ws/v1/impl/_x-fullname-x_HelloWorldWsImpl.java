package ${package}.ws.v1.impl;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.validation.constraints.Null;

import org.jboss.wsf.spi.annotation.TransportGuarantee;
import org.jboss.wsf.spi.annotation.WebContext;

import ${package}.ws.utils.BaseWsImpl;

/**
 * 
 * @author anadal
 * 
 */
@Stateless(name= ${fullname}HelloWorldWsImpl.NAME + "Ejb")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@WebService
(
    name=${fullname}HelloWorldWsImpl.NAME_WS,
    portName = ${fullname}HelloWorldWsImpl.NAME_WS,
    serviceName = ${fullname}HelloWorldWsImpl.NAME_WS + "Service"
)
@WebContext
(
    contextRoot="/${name}/ws",
    urlPattern="/v1/" + ${fullname}HelloWorldWsImpl.NAME,    
    transportGuarantee= TransportGuarantee.NONE,
    secureWSDLAccess = false
)
public class ${fullname}HelloWorldWsImpl extends BaseWsImpl {
  
  public static final String NAME = "${fullname}HelloWorld";
  
  public static final String NAME_WS = NAME + "Ws";
  
  @WebMethod
  public String echo(@WebParam (name ="echo") @Null String echo) {
    log.info("${fullname}HelloWorldWsImpl :: echo = " + echo);
    return echo;
  }

}

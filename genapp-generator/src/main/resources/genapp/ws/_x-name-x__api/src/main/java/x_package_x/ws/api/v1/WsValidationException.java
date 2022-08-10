
package ${package}.ws.api.v1;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.12-patch-04
 * Fri Jul 04 08:48:46 CEST 2014
 * Generated source version: 2.2.12-patch-04
 * 
 */

@WebFault(name = "WsValidationErrors", targetNamespace = "http://impl.v1.ws.${packageInverse}/")
public class WsValidationException extends Exception {
    
    private ${package}.ws.api.v1.WsValidationErrors wsValidationErrors;

    public WsValidationException() {
        super();
    }
    
    public WsValidationException(String message) {
        super(message);
    }
    
    public WsValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WsValidationException(String message, ${package}.ws.api.v1.WsValidationErrors wsValidationErrors) {
        super(message);
        this.wsValidationErrors = wsValidationErrors;
    }

    public WsValidationException(String message, ${package}.ws.api.v1.WsValidationErrors wsValidationErrors, Throwable cause) {
        super(message, cause);
        this.wsValidationErrors = wsValidationErrors;
    }

    public ${package}.ws.api.v1.WsValidationErrors getFaultInfo() {
        return this.wsValidationErrors;
    }
}

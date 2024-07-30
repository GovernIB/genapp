
/*
 * 
 */

package ${package}.ws.api.v1;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.12-patch-04
 * Fri Jul 04 08:48:34 CEST 2014
 * Generated source version: 2.2.12-patch-04
 * 
 */


@WebServiceClient(name = "${fullname}HelloWorldWithSecurityWsService", 
                  wsdlLocation = "http://localhost:8080/${name}/ws/v1/${fullname}HelloWorldWithSecurity?wsdl",
                  targetNamespace = "http://impl.v1.ws.${packageInverse}/") 
public class ${fullname}HelloWorldWithSecurityWsService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://impl.v1.ws.${packageInverse}/", "${fullname}HelloWorldWithSecurityWsService");
    public final static QName ${fullname}HelloWorldWithSecurityWs = new QName("http://impl.v1.ws.${packageInverse}/", "${fullname}HelloWorldWithSecurityWs");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/${name}/ws/v1/${fullname}HelloWorldWithSecurity?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:8080/${name}/ws/v1/${fullname}HelloWorldWithSecurity?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public ${fullname}HelloWorldWithSecurityWsService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public ${fullname}HelloWorldWithSecurityWsService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ${fullname}HelloWorldWithSecurityWsService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     * 
     * @return
     *     returns ${fullname}HelloWorldWithSecurityWs
     */
    @WebEndpoint(name = "${fullname}HelloWorldWithSecurityWs")
    public ${fullname}HelloWorldWithSecurityWs get${fullname}HelloWorldWithSecurityWs() {
        return super.getPort(${fullname}HelloWorldWithSecurityWs, ${fullname}HelloWorldWithSecurityWs.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ${fullname}HelloWorldWithSecurityWs
     */
    @WebEndpoint(name = "${fullname}HelloWorldWithSecurityWs")
    public ${fullname}HelloWorldWithSecurityWs get${fullname}HelloWorldWithSecurityWs(WebServiceFeature... features) {
        return super.getPort(${fullname}HelloWorldWithSecurityWs, ${fullname}HelloWorldWithSecurityWs.class, features);
    }

}
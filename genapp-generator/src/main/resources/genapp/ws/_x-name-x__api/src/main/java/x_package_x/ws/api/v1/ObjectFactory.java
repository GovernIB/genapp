
package ${package}.ws.api.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ${package}.ws.api.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _WsValidationErrors_QNAME = new QName("http://impl.v1.ws.${packageInverse}/", "WsValidationErrors");
    private final static QName _WsI18NError_QNAME = new QName("http://impl.v1.ws.${packageInverse}/", "WsI18NError");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ${package}.ws.api.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link WsFieldValidationError }
     * 
     */
    public WsFieldValidationError createWsFieldValidationError() {
        return new WsFieldValidationError();
    }

    /**
     * Create an instance of {@link WsI18NTranslation }
     * 
     */
    public WsI18NTranslation createWsI18NTranslation() {
        return new WsI18NTranslation();
    }

    /**
     * Create an instance of {@link WsI18NArgument }
     * 
     */
    public WsI18NArgument createWsI18NArgument() {
        return new WsI18NArgument();
    }

    /**
     * Create an instance of {@link WsI18NError }
     * 
     */
    public WsI18NError createWsI18NError() {
        return new WsI18NError();
    }

    /**
     * Create an instance of {@link FitxerBean }
     * 
     */
    public FitxerBean createFitxerBean() {
        return new FitxerBean();
    }

    /**
     * Create an instance of {@link WsValidationErrors }
     * 
     */
    public WsValidationErrors createWsValidationErrors() {
        return new WsValidationErrors();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsValidationErrors }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.v1.ws.${packageInverse}/", name = "WsValidationErrors")
    public JAXBElement<WsValidationErrors> createWsValidationErrors(WsValidationErrors value) {
        return new JAXBElement<WsValidationErrors>(_WsValidationErrors_QNAME, WsValidationErrors.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WsI18NError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://impl.v1.ws.${packageInverse}/", name = "WsI18NError")
    public JAXBElement<WsI18NError> createWsI18NError(WsI18NError value) {
        return new JAXBElement<WsI18NError>(_WsI18NError_QNAME, WsI18NError.class, null, value);
    }

}

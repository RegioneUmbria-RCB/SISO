
package it.webred.siso.ws.client.esb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.webred.siso.esb package. 
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

    private final static QName _DoServicePayload_QNAME = new QName("http://esb.siso.webred.it", "payload");
    private final static QName _DoServiceResponseReturn_QNAME = new QName("http://esb.siso.webred.it", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.webred.siso.esb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DoService }
     * 
     */
    public DoService createDoService() {
        return new DoService();
    }

    /**
     * Create an instance of {@link DoServiceResponse }
     * 
     */
    public DoServiceResponse createDoServiceResponse() {
        return new DoServiceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esb.siso.webred.it", name = "payload", scope = DoService.class)
    public JAXBElement<String> createDoServicePayload(String value) {
        return new JAXBElement<String>(_DoServicePayload_QNAME, String.class, DoService.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esb.siso.webred.it", name = "return", scope = DoServiceResponse.class)
    public JAXBElement<String> createDoServiceResponseReturn(String value) {
        return new JAXBElement<String>(_DoServiceResponseReturn_QNAME, String.class, DoServiceResponse.class, value);
    }

}

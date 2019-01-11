
package it.webred.siso.esb;

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

    private final static QName _CodiceRegionale_QNAME = new QName("http://esb.siso.webred.it", "codiceRegionale");
    private final static QName _GetMedicoReturn_QNAME = new QName("http://esb.siso.webred.it", "getMedicoReturn");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.webred.siso.esb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Medico }
     * 
     */
    public Medico createMedico() {
        return new Medico();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esb.siso.webred.it", name = "codiceRegionale")
    public JAXBElement<String> createCodiceRegionale(String value) {
        return new JAXBElement<String>(_CodiceRegionale_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Medico }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://esb.siso.webred.it", name = "getMedicoReturn")
    public JAXBElement<Medico> createGetMedicoReturn(Medico value) {
        return new JAXBElement<Medico>(_GetMedicoReturn_QNAME, Medico.class, null, value);
    }

}

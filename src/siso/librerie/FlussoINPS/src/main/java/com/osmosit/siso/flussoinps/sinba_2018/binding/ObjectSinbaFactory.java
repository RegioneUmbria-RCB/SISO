//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.09.25 alle 10:25:39 AM CEST 
//


package com.osmosit.siso.flussoinps.sinba_2018.binding;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.baywaylabs.sina package. 
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
public class ObjectSinbaFactory {

    private final static QName _Flusso_QNAME = new QName("http://inps.it/Casellario", "Flusso");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.baywaylabs.sina
     * 
     */
    public ObjectSinbaFactory() {
    }

    /**
     * Create an instance of {@link SinbaType }
     * 
     */
    public SINBA createSinba() {
        return new SINBA();
    }

    /**
     * Create an instance of {@link FlussoType }
     * 
     */
    public Flusso createFlusso() {
        return new Flusso();
    }

    /**
     * Create an instance of {@link EnteType }
     * 
     */
    public Ente createEnte() {
        return new Ente();
    }

    /**
     * Create an instance of {@link IdentificazioneFlussoType }
     * 
     */
    public IdentificazioneFlusso createIdentificazioneFlusso() {
        return new IdentificazioneFlusso();
    }

    /**
     * Create an instance of {@link SinbaType.Prestazioni }
     * 
     */
    public SINBA.Prestazioni createSinbaPrestazioni() {
        return new SINBA.Prestazioni();
    }

    /**
     * Create an instance of {@link SinbaType.Famiglia }
     * 
     */
    public SINBA.Famiglia createSinbaFamiglia() {
        return new SINBA.Famiglia();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlussoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://inps.it/Casellario", name = "Flusso")
    public JAXBElement<Flusso> createFlusso(Flusso value) {
        return new JAXBElement<Flusso>(_Flusso_QNAME, Flusso.class, null, value);
    }

}

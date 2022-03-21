//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.08.07 alle 09:17:06 AM CEST 
//


package com.osmosit.siso.flussoinps.psa_2018;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.osmosit.siso.flussoinps.psa_2018 package. 
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

    private final static QName _Flusso_QNAME = new QName("http://inps.it/Casellario", "Flusso");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.osmosit.siso.flussoinps.psa_2018
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SINA }
     * 
     */
    public SINA createSINA() {
        return new SINA();
    }

    /**
     * Create an instance of {@link Flusso }
     * 
     */
    public Flusso createFlusso() {
        return new Flusso();
    }

    /**
     * Create an instance of {@link Indirizzo }
     * 
     */
    public Indirizzo createIndirizzo() {
        return new Indirizzo();
    }

    /**
     * Create an instance of {@link Ente }
     * 
     */
    public Ente createEnte() {
        return new Ente();
    }

    /**
     * Create an instance of {@link IdentificazioneFlusso }
     * 
     */
    public IdentificazioneFlusso createIdentificazioneFlusso() {
        return new IdentificazioneFlusso();
    }

    /**
     * Create an instance of {@link Beneficiario }
     * 
     */
    public Beneficiario createBeneficiario() {
        return new Beneficiario();
    }

    /**
     * Create an instance of {@link Genere }
     * 
     */
    public Genere createGenere() {
        return new Genere();
    }

    /**
     * Create an instance of {@link PrestazioniSociali }
     * 
     */
    public PrestazioniSociali createPrestazioniSociali() {
        return new PrestazioniSociali();
    }

    /**
     * Create an instance of {@link Anagrafica }
     * 
     */
    public Anagrafica createAnagrafica() {
        return new Anagrafica();
    }

    /**
     * Create an instance of {@link SINA.NecessitaInterventiSociali }
     * 
     */
    public SINA.NecessitaInterventiSociali createSINANecessitaInterventiSociali() {
        return new SINA.NecessitaInterventiSociali();
    }

    /**
     * Create an instance of {@link SINA.InvaliditaCivile }
     * 
     */
    public SINA.InvaliditaCivile createSINAInvaliditaCivile() {
        return new SINA.InvaliditaCivile();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Flusso }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://inps.it/Casellario", name = "Flusso")
    public JAXBElement<Flusso> createFlusso(Flusso value) {
        return new JAXBElement<Flusso>(_Flusso_QNAME, Flusso.class, null, value);
    }

}

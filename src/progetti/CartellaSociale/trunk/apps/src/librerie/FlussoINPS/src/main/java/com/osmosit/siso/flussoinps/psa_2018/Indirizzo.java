//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.08.07 alle 09:17:06 AM CEST 
//


package com.osmosit.siso.flussoinps.psa_2018;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IndirizzoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IndirizzoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="CodiceRegione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{2})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceComune">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{6})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceNazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{3})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndirizzoType")
public class Indirizzo {

    @XmlAttribute(name = "CodiceRegione")
    protected String codiceRegione;
    @XmlAttribute(name = "CodiceComune")
    protected String codiceComune;
    @XmlAttribute(name = "CodiceNazione")
    protected String codiceNazione;

    /**
     * Recupera il valore della proprietà codiceRegione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegione() {
        return codiceRegione;
    }

    /**
     * Imposta il valore della proprietà codiceRegione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegione(String value) {
        this.codiceRegione = value;
    }

    /**
     * Recupera il valore della proprietà codiceComune.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComune() {
        return codiceComune;
    }

    /**
     * Imposta il valore della proprietà codiceComune.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComune(String value) {
        this.codiceComune = value;
    }

    /**
     * Recupera il valore della proprietà codiceNazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNazione() {
        return codiceNazione;
    }

    /**
     * Imposta il valore della proprietà codiceNazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNazione(String value) {
        this.codiceNazione = value;
    }

}

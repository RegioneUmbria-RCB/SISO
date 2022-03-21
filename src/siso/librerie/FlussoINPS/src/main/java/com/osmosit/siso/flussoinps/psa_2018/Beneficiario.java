//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.08.07 alle 09:17:06 AM CEST 
//


package com.osmosit.siso.flussoinps.psa_2018;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per BeneficiarioType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="BeneficiarioType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Anagrafica" type="{http://inps.it/Casellario}AnagraficaType"/>
 *         &lt;element name="Indirizzo" type="{http://inps.it/Casellario}IndirizzoType" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="PrestazioniSociali" type="{http://inps.it/Casellario}PrestazioniSocialiType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="CodiceFiscale" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([A-Za-z]{6}\d{2}[A-Za-z]\d{2}[A-Za-z][A-Za-z0-9]{3}[A-Za-z])|(\d{11} {0,5})"/>
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
@XmlType(name = "BeneficiarioType", propOrder = {
    "anagrafica",
    "indirizzo",
    "prestazioniSociali"
})
public class Beneficiario {

    @XmlElement(name = "Anagrafica", required = true)
    protected Anagrafica anagrafica;
    @XmlElement(name = "Indirizzo")
    protected List<Indirizzo> indirizzo;
    @XmlElement(name = "PrestazioniSociali", required = true)
    protected List<PrestazioniSociali> prestazioniSociali;
    @XmlAttribute(name = "CodiceFiscale", required = true)
    protected String codiceFiscale;

    /**
     * Recupera il valore della proprietà anagrafica.
     * 
     * @return
     *     possible object is
     *     {@link Anagrafica }
     *     
     */
    public Anagrafica getAnagrafica() {
        return anagrafica;
    }

    /**
     * Imposta il valore della proprietà anagrafica.
     * 
     * @param value
     *     allowed object is
     *     {@link Anagrafica }
     *     
     */
    public void setAnagrafica(Anagrafica value) {
        this.anagrafica = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the indirizzo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIndirizzo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Indirizzo }
     * 
     * 
     */
    public List<Indirizzo> getIndirizzo() {
        if (indirizzo == null) {
            indirizzo = new ArrayList<Indirizzo>();
        }
        return this.indirizzo;
    }

    /**
     * Gets the value of the prestazioniSociali property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prestazioniSociali property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrestazioniSociali().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PrestazioniSociali }
     * 
     * 
     */
    public List<PrestazioniSociali> getPrestazioniSociali() {
        if (prestazioniSociali == null) {
            prestazioniSociali = new ArrayList<PrestazioniSociali>();
        }
        return this.prestazioniSociali;
    }

    /**
     * Recupera il valore della proprietà codiceFiscale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Imposta il valore della proprietà codiceFiscale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

}

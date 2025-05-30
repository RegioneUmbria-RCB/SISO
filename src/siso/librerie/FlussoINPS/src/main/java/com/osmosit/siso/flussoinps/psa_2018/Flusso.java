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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per FlussoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="FlussoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IdentificazioneFlusso" type="{http://inps.it/Casellario}IdentificazioneFlussoType"/>
 *         &lt;element name="Beneficiario" type="{http://inps.it/Casellario}BeneficiarioType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlussoType", propOrder = {
    "identificazioneFlusso",
    "beneficiario"
})
public class Flusso {

    @XmlElement(name = "IdentificazioneFlusso", required = true)
    protected IdentificazioneFlusso identificazioneFlusso;
    @XmlElement(name = "Beneficiario", required = true)
    protected List<Beneficiario> beneficiario;

    /**
     * Recupera il valore della proprietà identificazioneFlusso.
     * 
     * @return
     *     possible object is
     *     {@link IdentificazioneFlusso }
     *     
     */
    public IdentificazioneFlusso getIdentificazioneFlusso() {
        return identificazioneFlusso;
    }

    /**
     * Imposta il valore della proprietà identificazioneFlusso.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificazioneFlusso }
     *     
     */
    public void setIdentificazioneFlusso(IdentificazioneFlusso value) {
        this.identificazioneFlusso = value;
    }

    /**
     * Gets the value of the beneficiario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the beneficiario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBeneficiario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Beneficiario }
     * 
     * 
     */
    public List<Beneficiario> getBeneficiario() {
        if (beneficiario == null) {
            beneficiario = new ArrayList<Beneficiario>();
        }
        return this.beneficiario;
    }

}

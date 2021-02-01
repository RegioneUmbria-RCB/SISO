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
 * <p>Classe Java per SINAType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SINAType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NecessitaInterventiSociali">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CodicePrestazione" maxOccurs="unbounded">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;minLength value="5"/>
 *                         &lt;maxLength value="10"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="InvaliditaCivile">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="InvCiv" type="{http://inps.it/Casellario}InvCivType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="Mobilita" use="required" type="{http://inps.it/Casellario}MobilitaType" />
 *       &lt;attribute name="AttivitaVitaQuotidiana" use="required" type="{http://inps.it/Casellario}AttivitaVitaQuotidianaType" />
 *       &lt;attribute name="DisturbiAreaCognitiva" type="{http://inps.it/Casellario}DisturbiAreaCognitivaType" />
 *       &lt;attribute name="DisturbiComportamentali" use="required" type="{http://inps.it/Casellario}DisturbiComportamentaliType" />
 *       &lt;attribute name="NecessitaCureSanitarie" use="required" type="{http://inps.it/Casellario}NecessitaCureSanitarieType" />
 *       &lt;attribute name="AreaReddituale" use="required" type="{http://inps.it/Casellario}AreaRedditualeType" />
 *       &lt;attribute name="AreaSupporto" use="required" type="{http://inps.it/Casellario}AreaSupportoType" />
 *       &lt;attribute name="FonteDerivazioneValutazione" use="required" type="{http://inps.it/Casellario}FonteDerivazioneValutazioneType" />
 *       &lt;attribute name="StrumentoValutazione" use="required" type="{http://inps.it/Casellario}StrumentoValutazioneType" />
 *       &lt;attribute name="FonteDerivazioneInvalidita" use="required" type="{http://inps.it/Casellario}FonteDerivazioneInvaliditaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SINAType", propOrder = {
    "necessitaInterventiSociali",
    "invaliditaCivile"
})
public class SINA {

    @XmlElement(name = "NecessitaInterventiSociali")//, required = true)
    protected SINA.NecessitaInterventiSociali necessitaInterventiSociali;
    @XmlElement(name = "InvaliditaCivile")//, required = true)
    protected SINA.InvaliditaCivile invaliditaCivile;
    @XmlAttribute(name = "Mobilita", required = true)
    protected String mobilita;
    @XmlAttribute(name = "AttivitaVitaQuotidiana", required = true)
    protected String attivitaVitaQuotidiana;
    @XmlAttribute(name = "DisturbiAreaCognitiva")
    protected String disturbiAreaCognitiva;
    @XmlAttribute(name = "DisturbiComportamentali", required = true)
    protected String disturbiComportamentali;
    @XmlAttribute(name = "NecessitaCureSanitarie", required = true)
    protected String necessitaCureSanitarie;
    @XmlAttribute(name = "AreaReddituale", required = true)
    protected String areaReddituale;
    @XmlAttribute(name = "AreaSupporto", required = true)
    protected String areaSupporto;
    @XmlAttribute(name = "FonteDerivazioneValutazione", required = true)
    protected String fonteDerivazioneValutazione;
    @XmlAttribute(name = "StrumentoValutazione", required = true)
    protected String strumentoValutazione;
    @XmlAttribute(name = "FonteDerivazioneInvalidita")//, required = true)
    protected String fonteDerivazioneInvalidita;

    /**
     * Recupera il valore della proprietà necessitaInterventiSociali.
     * 
     * @return
     *     possible object is
     *     {@link SINA.NecessitaInterventiSociali }
     *     
     */
    public SINA.NecessitaInterventiSociali getNecessitaInterventiSociali() {
        return necessitaInterventiSociali;
    }

    /**
     * Imposta il valore della proprietà necessitaInterventiSociali.
     * 
     * @param value
     *     allowed object is
     *     {@link SINA.NecessitaInterventiSociali }
     *     
     */
    public void setNecessitaInterventiSociali(SINA.NecessitaInterventiSociali value) {
        this.necessitaInterventiSociali = value;
    }

    /**
     * Recupera il valore della proprietà invaliditaCivile.
     * 
     * @return
     *     possible object is
     *     {@link SINA.InvaliditaCivile }
     *     
     */
    public SINA.InvaliditaCivile getInvaliditaCivile() {
        return invaliditaCivile;
    }

    /**
     * Imposta il valore della proprietà invaliditaCivile.
     * 
     * @param value
     *     allowed object is
     *     {@link SINA.InvaliditaCivile }
     *     
     */
    public void setInvaliditaCivile(SINA.InvaliditaCivile value) {
        this.invaliditaCivile = value;
    }

    /**
     * Recupera il valore della proprietà mobilita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobilita() {
        return mobilita;
    }

    /**
     * Imposta il valore della proprietà mobilita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobilita(String value) {
        this.mobilita = value;
    }

    /**
     * Recupera il valore della proprietà attivitaVitaQuotidiana.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttivitaVitaQuotidiana() {
        return attivitaVitaQuotidiana;
    }

    /**
     * Imposta il valore della proprietà attivitaVitaQuotidiana.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttivitaVitaQuotidiana(String value) {
        this.attivitaVitaQuotidiana = value;
    }

    /**
     * Recupera il valore della proprietà disturbiAreaCognitiva.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisturbiAreaCognitiva() {
        return disturbiAreaCognitiva;
    }

    /**
     * Imposta il valore della proprietà disturbiAreaCognitiva.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisturbiAreaCognitiva(String value) {
        this.disturbiAreaCognitiva = value;
    }

    /**
     * Recupera il valore della proprietà disturbiComportamentali.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisturbiComportamentali() {
        return disturbiComportamentali;
    }

    /**
     * Imposta il valore della proprietà disturbiComportamentali.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisturbiComportamentali(String value) {
        this.disturbiComportamentali = value;
    }

    /**
     * Recupera il valore della proprietà necessitaCureSanitarie.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNecessitaCureSanitarie() {
        return necessitaCureSanitarie;
    }

    /**
     * Imposta il valore della proprietà necessitaCureSanitarie.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNecessitaCureSanitarie(String value) {
        this.necessitaCureSanitarie = value;
    }

    /**
     * Recupera il valore della proprietà areaReddituale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaReddituale() {
        return areaReddituale;
    }

    /**
     * Imposta il valore della proprietà areaReddituale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaReddituale(String value) {
        this.areaReddituale = value;
    }

    /**
     * Recupera il valore della proprietà areaSupporto.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaSupporto() {
        return areaSupporto;
    }

    /**
     * Imposta il valore della proprietà areaSupporto.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaSupporto(String value) {
        this.areaSupporto = value;
    }

    /**
     * Recupera il valore della proprietà fonteDerivazioneValutazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonteDerivazioneValutazione() {
        return fonteDerivazioneValutazione;
    }

    /**
     * Imposta il valore della proprietà fonteDerivazioneValutazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonteDerivazioneValutazione(String value) {
        this.fonteDerivazioneValutazione = value;
    }

    /**
     * Recupera il valore della proprietà strumentoValutazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrumentoValutazione() {
        return strumentoValutazione;
    }

    /**
     * Imposta il valore della proprietà strumentoValutazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrumentoValutazione(String value) {
        this.strumentoValutazione = value;
    }

    /**
     * Recupera il valore della proprietà fonteDerivazioneInvalidita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonteDerivazioneInvalidita() {
        return fonteDerivazioneInvalidita;
    }

    /**
     * Imposta il valore della proprietà fonteDerivazioneInvalidita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonteDerivazioneInvalidita(String value) {
        this.fonteDerivazioneInvalidita = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="InvCiv" type="{http://inps.it/Casellario}InvCivType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "invCiv"
    })
    public static class InvaliditaCivile {

        @XmlElement(name = "InvCiv")//, required = true)
        protected List<String> invCiv;

        /**
         * Gets the value of the invCiv property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the invCiv property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInvCiv().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getInvCiv() {
            if (invCiv == null) {
                invCiv = new ArrayList<String>();
            }
            return this.invCiv;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CodicePrestazione" maxOccurs="unbounded">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="5"/>
     *               &lt;maxLength value="10"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codicePrestazione"
    })
    public static class NecessitaInterventiSociali {

        @XmlElement(name = "CodicePrestazione")//, required = true)
        protected List<String> codicePrestazione;

        /**
         * Gets the value of the codicePrestazione property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the codicePrestazione property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCodicePrestazione().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getCodicePrestazione() {
            if (codicePrestazione == null) {
                codicePrestazione = new ArrayList<String>();
            }
            return this.codicePrestazione;
        }

    }

}

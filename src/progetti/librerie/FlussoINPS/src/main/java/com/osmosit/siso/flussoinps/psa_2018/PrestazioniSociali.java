//
// Questo file � stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andr� persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.08.07 alle 09:17:06 AM CEST 
//


package com.osmosit.siso.flussoinps.psa_2018;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per PrestazioniSocialiType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PrestazioniSocialiType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SINA" type="{http://inps.it/Casellario}SINAType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="TipoOperazione" type="{http://inps.it/Casellario}TipoOperazioneType" />
 *       &lt;attribute name="PresenzaProvaMezzi" use="required" type="{http://inps.it/Casellario}PresenzaProvaMezziType" />
 *       &lt;attribute name="CaratterePrestazione" type="{http://inps.it/Casellario}CaratterePrestazioneType" />
 *       &lt;attribute name="NumeroProtocolloDSU">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="0"/>
 *             &lt;maxLength value="30"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="AnnoProtocolloDSU">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{4})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DataDSU">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodicePrestazione" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="5"/>
 *             &lt;maxLength value="10"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DenominazionePrestazione" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="10"/>
 *             &lt;maxLength value="200"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ProtocolloDomanda" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="32"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DataEvento">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DataInizioPrestazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DataFinePrestazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="DataErogazionePrestazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}date">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoPrestazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="PeriodoErogazione">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;minInclusive value="1"/>
 *             &lt;maxInclusive value="12"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoMensile">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoQuotaEnte">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoQuotaUtente">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoQuotaSSN">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoQuotaRichiesta">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="ImportoSogliaISEE">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="OreServizioMensile">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;maxInclusive value="999"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="PresaCarico" use="required" type="{http://inps.it/Casellario}PresaCaricoType" />
 *       &lt;attribute name="AreaUtenza" type="{http://inps.it/Casellario}AreaUtenzaType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrestazioniSocialiType", propOrder = {
    "sina"
})
public class PrestazioniSociali {

    @XmlElement(name = "SINA")
    protected SINA sina;
    @XmlAttribute(name = "TipoOperazione")
    protected TipoOperazione tipoOperazione;
    @XmlAttribute(name = "PresenzaProvaMezzi", required = true)
    protected String presenzaProvaMezzi;
    @XmlAttribute(name = "CaratterePrestazione")
    protected String caratterePrestazione;
    @XmlAttribute(name = "NumeroProtocolloDSU")
    protected String numeroProtocolloDSU;
    @XmlAttribute(name = "AnnoProtocolloDSU")
    protected String annoProtocolloDSU;
    @XmlAttribute(name = "DataDSU")
    protected XMLGregorianCalendar dataDSU;
    @XmlAttribute(name = "CodicePrestazione", required = true)
    protected String codicePrestazione;
    @XmlAttribute(name = "DenominazionePrestazione", required = true)
    protected String denominazionePrestazione;
    @XmlAttribute(name = "ProtocolloDomanda", required = true)
    protected String protocolloDomanda;
    @XmlAttribute(name = "DataEvento")
    protected XMLGregorianCalendar dataEvento;
    @XmlAttribute(name = "DataInizioPrestazione")
    protected XMLGregorianCalendar dataInizioPrestazione;
    @XmlAttribute(name = "DataFinePrestazione")
    protected XMLGregorianCalendar dataFinePrestazione;
    @XmlAttribute(name = "DataErogazionePrestazione")
    protected XMLGregorianCalendar dataErogazionePrestazione;
    @XmlAttribute(name = "ImportoPrestazione")
    protected BigDecimal importoPrestazione;
    @XmlAttribute(name = "PeriodoErogazione")
    protected Integer periodoErogazione;
    @XmlAttribute(name = "ImportoMensile")
    protected BigDecimal importoMensile;
    @XmlAttribute(name = "ImportoQuotaEnte")
    protected BigDecimal importoQuotaEnte;
    @XmlAttribute(name = "ImportoQuotaUtente")
    protected BigDecimal importoQuotaUtente;
    @XmlAttribute(name = "ImportoQuotaSSN")
    protected BigDecimal importoQuotaSSN;
    @XmlAttribute(name = "ImportoQuotaRichiesta")
    protected BigDecimal importoQuotaRichiesta;
    @XmlAttribute(name = "ImportoSogliaISEE")
    protected BigDecimal importoSogliaISEE;
    @XmlAttribute(name = "OreServizioMensile")
    protected String oreServizioMensile;
    @XmlAttribute(name = "PresaCarico", required = true)
    protected String presaCarico;
    @XmlAttribute(name = "AreaUtenza")
    protected String areaUtenza;

    /**
     * Recupera il valore della proprietà sina.
     * 
     * @return
     *     possible object is
     *     {@link SINA }
     *     
     */
    public SINA getSINA() {
        return sina;
    }

    /**
     * Imposta il valore della proprietà sina.
     * 
     * @param value
     *     allowed object is
     *     {@link SINA }
     *     
     */
    public void setSINA(SINA value) {
        this.sina = value;
    }

    /**
     * Recupera il valore della proprietà tipoOperazione.
     * 
     * @return
     *     possible object is
     *     {@link TipoOperazione }
     *     
     */
    public TipoOperazione getTipoOperazione() {
        return tipoOperazione;
    }

    /**
     * Imposta il valore della proprietà tipoOperazione.
     * 
     * @param value
     *     allowed object is
     *     {@link TipoOperazione }
     *     
     */
    public void setTipoOperazione(TipoOperazione value) {
        this.tipoOperazione = value;
    }

    /**
     * Recupera il valore della proprietà presenzaProvaMezzi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresenzaProvaMezzi() {
        return presenzaProvaMezzi;
    }

    /**
     * Imposta il valore della proprietà presenzaProvaMezzi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresenzaProvaMezzi(String value) {
        this.presenzaProvaMezzi = value;
    }

    /**
     * Recupera il valore della proprietà caratterePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaratterePrestazione() {
        return caratterePrestazione;
    }

    /**
     * Imposta il valore della proprietà caratterePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaratterePrestazione(String value) {
        this.caratterePrestazione = value;
    }

    /**
     * Recupera il valore della proprietà numeroProtocolloDSU.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroProtocolloDSU() {
        return numeroProtocolloDSU;
    }

    /**
     * Imposta il valore della proprietà numeroProtocolloDSU.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroProtocolloDSU(String value) {
        this.numeroProtocolloDSU = value;
    }

    /**
     * Recupera il valore della proprietà annoProtocolloDSU.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoProtocolloDSU() {
        return annoProtocolloDSU;
    }

    /**
     * Imposta il valore della proprietà annoProtocolloDSU.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoProtocolloDSU(String value) {
        this.annoProtocolloDSU = value;
    }

    /**
     * Recupera il valore della proprietà dataDSU.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDSU() {
        return dataDSU;
    }

    /**
     * Imposta il valore della proprietà dataDSU.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDSU(XMLGregorianCalendar value) {
        this.dataDSU = value;
    }

    /**
     * Recupera il valore della proprietà codicePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodicePrestazione() {
        return codicePrestazione;
    }

    /**
     * Imposta il valore della proprietà codicePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodicePrestazione(String value) {
        this.codicePrestazione = value;
    }

    /**
     * Recupera il valore della proprietà denominazionePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazionePrestazione() {
        return denominazionePrestazione;
    }

    /**
     * Imposta il valore della proprietà denominazionePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazionePrestazione(String value) {
        this.denominazionePrestazione = value;
    }

    /**
     * Recupera il valore della proprietà protocolloDomanda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolloDomanda() {
        return protocolloDomanda;
    }

    /**
     * Imposta il valore della proprietà protocolloDomanda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolloDomanda(String value) {
        this.protocolloDomanda = value;
    }

    /**
     * Recupera il valore della proprietà dataEvento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataEvento() {
        return dataEvento;
    }

    /**
     * Imposta il valore della proprietà dataEvento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataEvento(XMLGregorianCalendar value) {
        this.dataEvento = value;
    }

    /**
     * Recupera il valore della proprietà dataInizioPrestazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioPrestazione() {
        return dataInizioPrestazione;
    }

    /**
     * Imposta il valore della proprietà dataInizioPrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioPrestazione(XMLGregorianCalendar value) {
        this.dataInizioPrestazione = value;
    }

    /**
     * Recupera il valore della proprietà dataFinePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFinePrestazione() {
        return dataFinePrestazione;
    }

    /**
     * Imposta il valore della proprietà dataFinePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFinePrestazione(XMLGregorianCalendar value) {
        this.dataFinePrestazione = value;
    }

    /**
     * Recupera il valore della proprietà dataErogazionePrestazione.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataErogazionePrestazione() {
        return dataErogazionePrestazione;
    }

    /**
     * Imposta il valore della proprietà dataErogazionePrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataErogazionePrestazione(XMLGregorianCalendar value) {
        this.dataErogazionePrestazione = value;
    }

    /**
     * Recupera il valore della proprietà importoPrestazione.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoPrestazione() {
        return importoPrestazione;
    }

    /**
     * Imposta il valore della proprietà importoPrestazione.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoPrestazione(BigDecimal value) {
        this.importoPrestazione = value;
    }

    /**
     * Recupera il valore della proprietà periodoErogazione.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPeriodoErogazione() {
        return periodoErogazione;
    }

    /**
     * Imposta il valore della proprietà periodoErogazione.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPeriodoErogazione(Integer value) {
        this.periodoErogazione = value;
    }

    /**
     * Recupera il valore della proprietà importoMensile.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoMensile() {
        return importoMensile;
    }

    /**
     * Imposta il valore della proprietà importoMensile.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoMensile(BigDecimal value) {
        this.importoMensile = value;
    }

    /**
     * Recupera il valore della proprietà importoQuotaEnte.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoQuotaEnte() {
        return importoQuotaEnte;
    }

    /**
     * Imposta il valore della proprietà importoQuotaEnte.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoQuotaEnte(BigDecimal value) {
        this.importoQuotaEnte = value;
    }

    /**
     * Recupera il valore della proprietà importoQuotaUtente.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoQuotaUtente() {
        return importoQuotaUtente;
    }

    /**
     * Imposta il valore della proprietà importoQuotaUtente.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoQuotaUtente(BigDecimal value) {
        this.importoQuotaUtente = value;
    }

    /**
     * Recupera il valore della proprietà importoQuotaSSN.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoQuotaSSN() {
        return importoQuotaSSN;
    }

    /**
     * Imposta il valore della proprietà importoQuotaSSN.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoQuotaSSN(BigDecimal value) {
        this.importoQuotaSSN = value;
    }

    /**
     * Recupera il valore della proprietà importoQuotaRichiesta.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoQuotaRichiesta() {
        return importoQuotaRichiesta;
    }

    /**
     * Imposta il valore della proprietà importoQuotaRichiesta.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoQuotaRichiesta(BigDecimal value) {
        this.importoQuotaRichiesta = value;
    }

    /**
     * Recupera il valore della proprietà importoSogliaISEE.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getImportoSogliaISEE() {
        return importoSogliaISEE;
    }

    /**
     * Imposta il valore della proprietà importoSogliaISEE.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setImportoSogliaISEE(BigDecimal value) {
        this.importoSogliaISEE = value;
    }

    /**
     * Recupera il valore della proprietà oreServizioMensile.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOreServizioMensile() {
        return oreServizioMensile;
    }

    /**
     * Imposta il valore della proprietà oreServizioMensile.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOreServizioMensile(String value) {
        this.oreServizioMensile = value;
    }

    /**
     * Recupera il valore della proprietà presaCarico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPresaCarico() {
        return presaCarico;
    }

    /**
     * Imposta il valore della proprietà presaCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPresaCarico(String value) {
        this.presaCarico = value;
    }

    /**
     * Recupera il valore della proprietà areaUtenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaUtenza() {
        return areaUtenza;
    }

    /**
     * Imposta il valore della proprietà areaUtenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaUtenza(String value) {
        this.areaUtenza = value;
    }

}

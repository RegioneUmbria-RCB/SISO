//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.09.25 alle 10:25:39 AM CEST 
//


package com.osmosit.siso.flussoinps.sinba_2018.binding;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SinbaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="SinbaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Prestazioni">
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
 *         &lt;element name="Famiglia">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ComposizioneFamiglia" type="{http://inps.it/Casellario}ComposizioneFamigliaType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="IDBeneficiario" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="20"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="MinoreStranieroNoAcc" use="required" type="{http://inps.it/Casellario}MinoreStranieroNoAccType" />
 *       &lt;attribute name="CondizioneMinore" use="required" type="{http://inps.it/Casellario}CondizioneMinoreType" />
 *       &lt;attribute name="LuogoVita" use="required" type="{http://inps.it/Casellario}LuogoVitaType" />
 *       &lt;attribute name="ScuolaFrequentata" use="required" type="{http://inps.it/Casellario}ScuolaFrequentataType" />
 *       &lt;attribute name="CondizioneLavoro" use="required" type="{http://inps.it/Casellario}CondizioneLavoroType" />
 *       &lt;attribute name="Disabilita" use="required" type="{http://inps.it/Casellario}DisabilitaType" />
 *       &lt;attribute name="TipoDisabilita" type="{http://inps.it/Casellario}TipoDisabilitaType" />
 *       &lt;attribute name="InvCivCertificazioni" type="{http://inps.it/Casellario}InvCivCertificazioniType" />
 *       &lt;attribute name="CodiceCittadinanzaPadre">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{3})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceCittadinanzaMadre">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{3})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceRegioneResidenzaPadre">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{2})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceRegioneResidenzaMadre">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{2})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="TitoloStudioPadre" type="{http://inps.it/Casellario}TitoloStudioType" />
 *       &lt;attribute name="TitoloStudioMadre" type="{http://inps.it/Casellario}TitoloStudioType" />
 *       &lt;attribute name="OccupazionePadre" type="{http://inps.it/Casellario}OccupazioneType" />
 *       &lt;attribute name="OccupazioneMadre" type="{http://inps.it/Casellario}OccupazioneType" />
 *       &lt;attribute name="DataPrimaSegnalazione" use="required" type="{http://inps.it/Casellario}DataMeseAnnoType" />
 *       &lt;attribute name="FonteSegnalazione" use="required" type="{http://inps.it/Casellario}FonteSegnalazioneType" />
 *       &lt;attribute name="ValutazioneMinore" use="required" type="{http://inps.it/Casellario}ValutazioneMinoreType" />
 *       &lt;attribute name="ValutazioneFamigliaMinore" use="required" type="{http://inps.it/Casellario}ValutazioneFamigliaMinoreType" />
 *       &lt;attribute name="SegnalazioneAutoritaGiudiziaria" use="required" type="{http://inps.it/Casellario}SegnalazioneAutoritaGiudiziariaType" />
 *       &lt;attribute name="DataSegnalazione" type="{http://inps.it/Casellario}DataMeseAnnoType" />
 *       &lt;attribute name="ProvvedimentoGiudiziario" use="required" type="{http://inps.it/Casellario}ProvvedimentoGiudiziarioType" />
 *       &lt;attribute name="DataProvvedimentoGiudiziario" type="{http://inps.it/Casellario}DataMeseAnnoType" />
 *       &lt;attribute name="AutoritaProvvedimento" type="{http://inps.it/Casellario}AutoritaProvvedimentoType" />
 *       &lt;attribute name="PotestaTutela" type="{http://inps.it/Casellario}PotestaTutelaType" />
 *       &lt;attribute name="TipoProvvedimentoGiudiziario" type="{http://inps.it/Casellario}TipoProvvedimentoGiudiziarioType" />
 *       &lt;attribute name="FormaInterventoAffido" type="{http://inps.it/Casellario}FormaInterventoAffidoType" />
 *       &lt;attribute name="TipoInterventoAffido" type="{http://inps.it/Casellario}TipoInterventoAffidoType" />
 *       &lt;attribute name="DurataAffido" type="{http://inps.it/Casellario}DurataAffidoType" />
 *       &lt;attribute name="CarattereAffido" type="{http://inps.it/Casellario}CarattereAffidoType" />
 *       &lt;attribute name="EsitoAffido" type="{http://inps.it/Casellario}EsitoAffidoType" />
 *       &lt;attribute name="CarattereInserimentoResidenziale" type="{http://inps.it/Casellario}CarattereInserimentoResidenzialeType" />
 *       &lt;attribute name="FormaInserimentoResidenziale" type="{http://inps.it/Casellario}FormaInserimentoResidenzialeType" />
 *       &lt;attribute name="TipoInserimentoResidenziale" type="{http://inps.it/Casellario}TipoInserimentoResidenzialeType" />
 *       &lt;attribute name="EsitoInserimentoStruttura" type="{http://inps.it/Casellario}EsitoInserimentoStrutturaType" />
 *       &lt;attribute name="MotivazioneChiusuraCarico" type="{http://inps.it/Casellario}MotivazioneChiusuraCaricoType" />
 *       &lt;attribute name="SituazioneChiusuraCarico" type="{http://inps.it/Casellario}SituazioneChiusuraCaricoType" />
 *       &lt;attribute name="CollaborazioniInterventi" type="{http://inps.it/Casellario}CollaborazioniInterventiType" />
 *       &lt;attribute name="NumeroComponentiISEE">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}int">
 *             &lt;maxInclusive value="9"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="FasciaEtaBeneficiario" use="required" type="{http://inps.it/Casellario}FasciaEtaBeneficiarioType" />
 *       &lt;attribute name="FasciaISEEBeneficiario" type="{http://inps.it/Casellario}FasciaISEEBeneficiarioType" />
 *       &lt;attribute name="AnnoNascita" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{4})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceRegioneResidenza">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{2})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceNazioneResidenza" use="required">
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
@XmlType(name = "SinbaType", propOrder = {
    "prestazioni",
    "famiglia"
})
public class SINBA {

    @XmlElement(name = "Prestazioni", required = true)
    protected SINBA.Prestazioni prestazioni;
    @XmlElement(name = "Famiglia", required = true)
    protected SINBA.Famiglia famiglia;
    @XmlAttribute(name = "IDBeneficiario", required = true)
    protected String idBeneficiario;
    @XmlAttribute(name = "MinoreStranieroNoAcc", required = true)
    protected String minoreStranieroNoAcc;
    @XmlAttribute(name = "CondizioneMinore", required = true)
    protected String condizioneMinore;
    @XmlAttribute(name = "LuogoVita", required = true)
    protected String luogoVita;
    @XmlAttribute(name = "ScuolaFrequentata", required = true)
    protected String scuolaFrequentata;
    @XmlAttribute(name = "CondizioneLavoro", required = true)
    protected String condizioneLavoro;
    @XmlAttribute(name = "Disabilita", required = true)
    protected String disabilita;
    @XmlAttribute(name = "TipoDisabilita")
    protected String tipoDisabilita;
    @XmlAttribute(name = "InvCivCertificazioni")
    protected String invCivCertificazioni;
    @XmlAttribute(name = "CodiceCittadinanzaPadre")
    protected String codiceCittadinanzaPadre;
    @XmlAttribute(name = "CodiceCittadinanzaMadre")
    protected String codiceCittadinanzaMadre;
    @XmlAttribute(name = "CodiceRegioneResidenzaPadre")
    protected String codiceRegioneResidenzaPadre;
    @XmlAttribute(name = "CodiceRegioneResidenzaMadre")
    protected String codiceRegioneResidenzaMadre;
    @XmlAttribute(name = "TitoloStudioPadre")
    protected String titoloStudioPadre;
    @XmlAttribute(name = "TitoloStudioMadre")
    protected String titoloStudioMadre;
    @XmlAttribute(name = "OccupazionePadre")
    protected String occupazionePadre;
    @XmlAttribute(name = "OccupazioneMadre")
    protected String occupazioneMadre;
    @XmlAttribute(name = "DataPrimaSegnalazione", required = true)
    protected String dataPrimaSegnalazione;
    @XmlAttribute(name = "FonteSegnalazione", required = true)
    protected String fonteSegnalazione;
    @XmlAttribute(name = "ValutazioneMinore", required = true)
    protected String valutazioneMinore;
    @XmlAttribute(name = "ValutazioneFamigliaMinore", required = true)
    protected String valutazioneFamigliaMinore;
    @XmlAttribute(name = "SegnalazioneAutoritaGiudiziaria", required = true)
    protected String segnalazioneAutoritaGiudiziaria;
    @XmlAttribute(name = "DataSegnalazione")
    protected String dataSegnalazione;
    @XmlAttribute(name = "ProvvedimentoGiudiziario", required = true)
    protected String provvedimentoGiudiziario;
    @XmlAttribute(name = "DataProvvedimentoGiudiziario")
    protected String dataProvvedimentoGiudiziario;
    @XmlAttribute(name = "AutoritaProvvedimento")
    protected String autoritaProvvedimento;
    @XmlAttribute(name = "PotestaTutela")
    protected String potestaTutela;
    @XmlAttribute(name = "TipoProvvedimentoGiudiziario")
    protected String tipoProvvedimentoGiudiziario;
    @XmlAttribute(name = "FormaInterventoAffido")
    protected String formaInterventoAffido;
    @XmlAttribute(name = "TipoInterventoAffido")
    protected String tipoInterventoAffido;
    @XmlAttribute(name = "DurataAffido")
    protected String durataAffido;
    @XmlAttribute(name = "CarattereAffido")
    protected String carattereAffido;
    @XmlAttribute(name = "EsitoAffido")
    protected String esitoAffido;
    @XmlAttribute(name = "CarattereInserimentoResidenziale")
    protected String carattereInserimentoResidenziale;
    @XmlAttribute(name = "FormaInserimentoResidenziale")
    protected String formaInserimentoResidenziale;
    @XmlAttribute(name = "TipoInserimentoResidenziale")
    protected String tipoInserimentoResidenziale;
    @XmlAttribute(name = "EsitoInserimentoStruttura")
    protected String esitoInserimentoStruttura;
    @XmlAttribute(name = "MotivazioneChiusuraCarico")
    protected String motivazioneChiusuraCarico;
    @XmlAttribute(name = "SituazioneChiusuraCarico")
    protected String situazioneChiusuraCarico;
    @XmlAttribute(name = "CollaborazioniInterventi")
    protected String collaborazioniInterventi;
    @XmlAttribute(name = "NumeroComponentiISEE")
    protected String numeroComponentiISEE;
    @XmlAttribute(name = "FasciaEtaBeneficiario", required = true)
    protected String fasciaEtaBeneficiario;
    @XmlAttribute(name = "FasciaISEEBeneficiario")
    protected String fasciaISEEBeneficiario;
    @XmlAttribute(name = "AnnoNascita", required = true)
    protected String annoNascita;
    @XmlAttribute(name = "CodiceRegioneResidenza")
    protected String codiceRegioneResidenza;
    @XmlAttribute(name = "CodiceNazioneResidenza", required = true)
    protected String codiceNazioneResidenza;

    /**
     * Recupera il valore della proprietà prestazioni.
     * 
     * @return
     *     possible object is
     *     {@link SinbaType.Prestazioni }
     *     
     */
    public SINBA.Prestazioni getPrestazioni() {
        return prestazioni;
    }

    /**
     * Imposta il valore della proprietà prestazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link SinbaType.Prestazioni }
     *     
     */
    public void setPrestazioni(SINBA.Prestazioni value) {
        this.prestazioni = value;
    }

    /**
     * Recupera il valore della proprietà famiglia.
     * 
     * @return
     *     possible object is
     *     {@link SinbaType.Famiglia }
     *     
     */
    public SINBA.Famiglia getFamiglia() {
        return famiglia;
    }

    /**
     * Imposta il valore della proprietà famiglia.
     * 
     * @param value
     *     allowed object is
     *     {@link SinbaType.Famiglia }
     *     
     */
    public void setFamiglia(SINBA.Famiglia value) {
        this.famiglia = value;
    }

    /**
     * Recupera il valore della proprietà idBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDBeneficiario() {
        return idBeneficiario;
    }

    /**
     * Imposta il valore della proprietà idBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDBeneficiario(String value) {
        this.idBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà minoreStranieroNoAcc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMinoreStranieroNoAcc() {
        return minoreStranieroNoAcc;
    }

    /**
     * Imposta il valore della proprietà minoreStranieroNoAcc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMinoreStranieroNoAcc(String value) {
        this.minoreStranieroNoAcc = value;
    }

    /**
     * Recupera il valore della proprietà condizioneMinore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondizioneMinore() {
        return condizioneMinore;
    }

    /**
     * Imposta il valore della proprietà condizioneMinore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondizioneMinore(String value) {
        this.condizioneMinore = value;
    }

    /**
     * Recupera il valore della proprietà luogoVita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuogoVita() {
        return luogoVita;
    }

    /**
     * Imposta il valore della proprietà luogoVita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuogoVita(String value) {
        this.luogoVita = value;
    }

    /**
     * Recupera il valore della proprietà scuolaFrequentata.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScuolaFrequentata() {
        return scuolaFrequentata;
    }

    /**
     * Imposta il valore della proprietà scuolaFrequentata.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScuolaFrequentata(String value) {
        this.scuolaFrequentata = value;
    }

    /**
     * Recupera il valore della proprietà condizioneLavoro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondizioneLavoro() {
        return condizioneLavoro;
    }

    /**
     * Imposta il valore della proprietà condizioneLavoro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondizioneLavoro(String value) {
        this.condizioneLavoro = value;
    }

    /**
     * Recupera il valore della proprietà disabilita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisabilita() {
        return disabilita;
    }

    /**
     * Imposta il valore della proprietà disabilita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisabilita(String value) {
        this.disabilita = value;
    }

    /**
     * Recupera il valore della proprietà tipoDisabilita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoDisabilita() {
        return tipoDisabilita;
    }

    /**
     * Imposta il valore della proprietà tipoDisabilita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoDisabilita(String value) {
        this.tipoDisabilita = value;
    }

    /**
     * Recupera il valore della proprietà invCivCertificazioni.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvCivCertificazioni() {
        return invCivCertificazioni;
    }

    /**
     * Imposta il valore della proprietà invCivCertificazioni.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvCivCertificazioni(String value) {
        this.invCivCertificazioni = value;
    }

    /**
     * Recupera il valore della proprietà codiceCittadinanzaPadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCittadinanzaPadre() {
        return codiceCittadinanzaPadre;
    }

    /**
     * Imposta il valore della proprietà codiceCittadinanzaPadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCittadinanzaPadre(String value) {
        this.codiceCittadinanzaPadre = value;
    }

    /**
     * Recupera il valore della proprietà codiceCittadinanzaMadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCittadinanzaMadre() {
        return codiceCittadinanzaMadre;
    }

    /**
     * Imposta il valore della proprietà codiceCittadinanzaMadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCittadinanzaMadre(String value) {
        this.codiceCittadinanzaMadre = value;
    }

    /**
     * Recupera il valore della proprietà codiceRegioneResidenzaPadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneResidenzaPadre() {
        return codiceRegioneResidenzaPadre;
    }

    /**
     * Imposta il valore della proprietà codiceRegioneResidenzaPadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneResidenzaPadre(String value) {
        this.codiceRegioneResidenzaPadre = value;
    }

    /**
     * Recupera il valore della proprietà codiceRegioneResidenzaMadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneResidenzaMadre() {
        return codiceRegioneResidenzaMadre;
    }

    /**
     * Imposta il valore della proprietà codiceRegioneResidenzaMadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneResidenzaMadre(String value) {
        this.codiceRegioneResidenzaMadre = value;
    }

    /**
     * Recupera il valore della proprietà titoloStudioPadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloStudioPadre() {
        return titoloStudioPadre;
    }

    /**
     * Imposta il valore della proprietà titoloStudioPadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloStudioPadre(String value) {
        this.titoloStudioPadre = value;
    }

    /**
     * Recupera il valore della proprietà titoloStudioMadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloStudioMadre() {
        return titoloStudioMadre;
    }

    /**
     * Imposta il valore della proprietà titoloStudioMadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloStudioMadre(String value) {
        this.titoloStudioMadre = value;
    }

    /**
     * Recupera il valore della proprietà occupazionePadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupazionePadre() {
        return occupazionePadre;
    }

    /**
     * Imposta il valore della proprietà occupazionePadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupazionePadre(String value) {
        this.occupazionePadre = value;
    }

    /**
     * Recupera il valore della proprietà occupazioneMadre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOccupazioneMadre() {
        return occupazioneMadre;
    }

    /**
     * Imposta il valore della proprietà occupazioneMadre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOccupazioneMadre(String value) {
        this.occupazioneMadre = value;
    }

    /**
     * Recupera il valore della proprietà dataPrimaSegnalazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataPrimaSegnalazione() {
        return dataPrimaSegnalazione;
    }

    /**
     * Imposta il valore della proprietà dataPrimaSegnalazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataPrimaSegnalazione(String value) {
        this.dataPrimaSegnalazione = value;
    }

    /**
     * Recupera il valore della proprietà fonteSegnalazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonteSegnalazione() {
        return fonteSegnalazione;
    }

    /**
     * Imposta il valore della proprietà fonteSegnalazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonteSegnalazione(String value) {
        this.fonteSegnalazione = value;
    }

    /**
     * Recupera il valore della proprietà valutazioneMinore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValutazioneMinore() {
        return valutazioneMinore;
    }

    /**
     * Imposta il valore della proprietà valutazioneMinore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValutazioneMinore(String value) {
        this.valutazioneMinore = value;
    }

    /**
     * Recupera il valore della proprietà valutazioneFamigliaMinore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValutazioneFamigliaMinore() {
        return valutazioneFamigliaMinore;
    }

    /**
     * Imposta il valore della proprietà valutazioneFamigliaMinore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValutazioneFamigliaMinore(String value) {
        this.valutazioneFamigliaMinore = value;
    }

    /**
     * Recupera il valore della proprietà segnalazioneAutoritaGiudiziaria.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegnalazioneAutoritaGiudiziaria() {
        return segnalazioneAutoritaGiudiziaria;
    }

    /**
     * Imposta il valore della proprietà segnalazioneAutoritaGiudiziaria.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegnalazioneAutoritaGiudiziaria(String value) {
        this.segnalazioneAutoritaGiudiziaria = value;
    }

    /**
     * Recupera il valore della proprietà dataSegnalazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSegnalazione() {
        return dataSegnalazione;
    }

    /**
     * Imposta il valore della proprietà dataSegnalazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSegnalazione(String value) {
        this.dataSegnalazione = value;
    }

    /**
     * Recupera il valore della proprietà provvedimentoGiudiziario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvvedimentoGiudiziario() {
        return provvedimentoGiudiziario;
    }

    /**
     * Imposta il valore della proprietà provvedimentoGiudiziario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvvedimentoGiudiziario(String value) {
        this.provvedimentoGiudiziario = value;
    }

    /**
     * Recupera il valore della proprietà dataProvvedimentoGiudiziario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataProvvedimentoGiudiziario() {
        return dataProvvedimentoGiudiziario;
    }

    /**
     * Imposta il valore della proprietà dataProvvedimentoGiudiziario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataProvvedimentoGiudiziario(String value) {
        this.dataProvvedimentoGiudiziario = value;
    }

    /**
     * Recupera il valore della proprietà autoritaProvvedimento.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoritaProvvedimento() {
        return autoritaProvvedimento;
    }

    /**
     * Imposta il valore della proprietà autoritaProvvedimento.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoritaProvvedimento(String value) {
        this.autoritaProvvedimento = value;
    }

    /**
     * Recupera il valore della proprietà potestaTutela.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPotestaTutela() {
        return potestaTutela;
    }

    /**
     * Imposta il valore della proprietà potestaTutela.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPotestaTutela(String value) {
        this.potestaTutela = value;
    }

    /**
     * Recupera il valore della proprietà tipoProvvedimentoGiudiziario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoProvvedimentoGiudiziario() {
        return tipoProvvedimentoGiudiziario;
    }

    /**
     * Imposta il valore della proprietà tipoProvvedimentoGiudiziario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoProvvedimentoGiudiziario(String value) {
        this.tipoProvvedimentoGiudiziario = value;
    }

    /**
     * Recupera il valore della proprietà formaInterventoAffido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormaInterventoAffido() {
        return formaInterventoAffido;
    }

    /**
     * Imposta il valore della proprietà formaInterventoAffido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormaInterventoAffido(String value) {
        this.formaInterventoAffido = value;
    }

    /**
     * Recupera il valore della proprietà tipoInterventoAffido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInterventoAffido() {
        return tipoInterventoAffido;
    }

    /**
     * Imposta il valore della proprietà tipoInterventoAffido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInterventoAffido(String value) {
        this.tipoInterventoAffido = value;
    }

    /**
     * Recupera il valore della proprietà durataAffido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDurataAffido() {
        return durataAffido;
    }

    /**
     * Imposta il valore della proprietà durataAffido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDurataAffido(String value) {
        this.durataAffido = value;
    }

    /**
     * Recupera il valore della proprietà carattereAffido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarattereAffido() {
        return carattereAffido;
    }

    /**
     * Imposta il valore della proprietà carattereAffido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarattereAffido(String value) {
        this.carattereAffido = value;
    }

    /**
     * Recupera il valore della proprietà esitoAffido.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoAffido() {
        return esitoAffido;
    }

    /**
     * Imposta il valore della proprietà esitoAffido.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoAffido(String value) {
        this.esitoAffido = value;
    }

    /**
     * Recupera il valore della proprietà carattereInserimentoResidenziale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarattereInserimentoResidenziale() {
        return carattereInserimentoResidenziale;
    }

    /**
     * Imposta il valore della proprietà carattereInserimentoResidenziale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarattereInserimentoResidenziale(String value) {
        this.carattereInserimentoResidenziale = value;
    }

    /**
     * Recupera il valore della proprietà formaInserimentoResidenziale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormaInserimentoResidenziale() {
        return formaInserimentoResidenziale;
    }

    /**
     * Imposta il valore della proprietà formaInserimentoResidenziale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormaInserimentoResidenziale(String value) {
        this.formaInserimentoResidenziale = value;
    }

    /**
     * Recupera il valore della proprietà tipoInserimentoResidenziale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoInserimentoResidenziale() {
        return tipoInserimentoResidenziale;
    }

    /**
     * Imposta il valore della proprietà tipoInserimentoResidenziale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoInserimentoResidenziale(String value) {
        this.tipoInserimentoResidenziale = value;
    }

    /**
     * Recupera il valore della proprietà esitoInserimentoStruttura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsitoInserimentoStruttura() {
        return esitoInserimentoStruttura;
    }

    /**
     * Imposta il valore della proprietà esitoInserimentoStruttura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsitoInserimentoStruttura(String value) {
        this.esitoInserimentoStruttura = value;
    }

    /**
     * Recupera il valore della proprietà motivazioneChiusuraCarico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivazioneChiusuraCarico() {
        return motivazioneChiusuraCarico;
    }

    /**
     * Imposta il valore della proprietà motivazioneChiusuraCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivazioneChiusuraCarico(String value) {
        this.motivazioneChiusuraCarico = value;
    }

    /**
     * Recupera il valore della proprietà situazioneChiusuraCarico.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSituazioneChiusuraCarico() {
        return situazioneChiusuraCarico;
    }

    /**
     * Imposta il valore della proprietà situazioneChiusuraCarico.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSituazioneChiusuraCarico(String value) {
        this.situazioneChiusuraCarico = value;
    }

    /**
     * Recupera il valore della proprietà collaborazioniInterventi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCollaborazioniInterventi() {
        return collaborazioniInterventi;
    }

    /**
     * Imposta il valore della proprietà collaborazioniInterventi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCollaborazioniInterventi(String value) {
        this.collaborazioniInterventi = value;
    }

    /**
     * Recupera il valore della proprietà numeroComponentiISEE.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public String getNumeroComponentiISEE() {
        return numeroComponentiISEE;
    }

    /**
     * Imposta il valore della proprietà numeroComponentiISEE.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroComponentiISEE(String value) {
        this.numeroComponentiISEE = value;
    }

    /**
     * Recupera il valore della proprietà fasciaEtaBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFasciaEtaBeneficiario() {
        return fasciaEtaBeneficiario;
    }

    /**
     * Imposta il valore della proprietà fasciaEtaBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFasciaEtaBeneficiario(String value) {
        this.fasciaEtaBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà fasciaISEEBeneficiario.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFasciaISEEBeneficiario() {
        return fasciaISEEBeneficiario;
    }

    /**
     * Imposta il valore della proprietà fasciaISEEBeneficiario.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFasciaISEEBeneficiario(String value) {
        this.fasciaISEEBeneficiario = value;
    }

    /**
     * Recupera il valore della proprietà annoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnnoNascita() {
        return annoNascita;
    }

    /**
     * Imposta il valore della proprietà annoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnnoNascita(String value) {
        this.annoNascita = value;
    }

    /**
     * Recupera il valore della proprietà codiceRegioneResidenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneResidenza() {
        return codiceRegioneResidenza;
    }

    /**
     * Imposta il valore della proprietà codiceRegioneResidenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneResidenza(String value) {
        this.codiceRegioneResidenza = value;
    }

    /**
     * Recupera il valore della proprietà codiceNazioneResidenza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceNazioneResidenza() {
        return codiceNazioneResidenza;
    }

    /**
     * Imposta il valore della proprietà codiceNazioneResidenza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceNazioneResidenza(String value) {
        this.codiceNazioneResidenza = value;
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
     *         &lt;element name="ComposizioneFamiglia" type="{http://inps.it/Casellario}ComposizioneFamigliaType" maxOccurs="unbounded"/>
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
        "composizioneFamiglia"
    })
    public static class Famiglia {

        @XmlElement(name = "ComposizioneFamiglia", required = true)
        protected List<String> composizioneFamiglia;

        /**
         * Gets the value of the composizioneFamiglia property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the composizioneFamiglia property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getComposizioneFamiglia().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getComposizioneFamiglia() {
            if (composizioneFamiglia == null) {
                composizioneFamiglia = new ArrayList<String>();
            }
            return this.composizioneFamiglia;
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
    public static class Prestazioni {

        @XmlElement(name = "CodicePrestazione", required = true)
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

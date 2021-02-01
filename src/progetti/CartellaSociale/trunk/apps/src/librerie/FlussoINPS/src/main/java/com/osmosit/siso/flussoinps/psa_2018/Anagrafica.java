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
 * <p>Classe Java per AnagraficaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AnagraficaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Cognome" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="36"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Nome" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;minLength value="1"/>
 *             &lt;maxLength value="36"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="AnnoNascita" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{4})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="LuogoNascita" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;length value="4"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="Genere" use="required" type="{http://inps.it/Casellario}GenereType" />
 *       &lt;attribute name="CodiceCittadinanza" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="([0-9]{3})"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="CodiceSecondaCittadinanza">
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
@XmlType(name = "AnagraficaType")
public class Anagrafica {

    @XmlAttribute(name = "Cognome", required = true)
    protected String cognome;
    @XmlAttribute(name = "Nome", required = true)
    protected String nome;
    @XmlAttribute(name = "AnnoNascita", required = true)
    protected String annoNascita;
    @XmlAttribute(name = "LuogoNascita", required = true)
    protected String luogoNascita;
    @XmlAttribute(name = "Genere", required = true)
    protected Genere genere;
    @XmlAttribute(name = "CodiceCittadinanza", required = true)
    protected String codiceCittadinanza;
    @XmlAttribute(name = "CodiceSecondaCittadinanza")
    protected String codiceSecondaCittadinanza;

    /**
     * Recupera il valore della proprietà cognome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Imposta il valore della proprietà cognome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Recupera il valore della proprietà nome.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Imposta il valore della proprietà nome.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
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
     * Recupera il valore della proprietà luogoNascita.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuogoNascita() {
        return luogoNascita;
    }

    /**
     * Imposta il valore della proprietà luogoNascita.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuogoNascita(String value) {
        this.luogoNascita = value;
    }

    /**
     * Recupera il valore della proprietà genere.
     * 
     * @return
     *     possible object is
     *     {@link Genere }
     *     
     */
    public Genere getGenere() {
        return genere;
    }

    /**
     * Imposta il valore della proprietà genere.
     * 
     * @param value
     *     allowed object is
     *     {@link Genere }
     *     
     */
    public void setGenere(Genere value) {
        this.genere = value;
    }

    /**
     * Recupera il valore della proprietà codiceCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceCittadinanza() {
        return codiceCittadinanza;
    }

    /**
     * Imposta il valore della proprietà codiceCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceCittadinanza(String value) {
        this.codiceCittadinanza = value;
    }

    /**
     * Recupera il valore della proprietà codiceSecondaCittadinanza.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceSecondaCittadinanza() {
        return codiceSecondaCittadinanza;
    }

    /**
     * Imposta il valore della proprietà codiceSecondaCittadinanza.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceSecondaCittadinanza(String value) {
        this.codiceSecondaCittadinanza = value;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.14 at 03:12:17 PM CEST 
//


package it.webred.siso.ws.client.atlante.model.GetServiziOspite.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServizioOspiteResponse" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="DataFine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IGUCausFineServ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IdEntitaDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IdEntitaProp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="IdMessaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
    "servizioOspiteResponse"
})
@XmlRootElement(name = "ServiziOspite")
public class ServiziOspite {

    @XmlElement(name = "ServizioOspiteResponse")
    protected List<ServiziOspite.ServizioOspiteResponse> servizioOspiteResponse;

    /**
     * Gets the value of the servizioOspiteResponse property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the servizioOspiteResponse property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServizioOspiteResponse().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiziOspite.ServizioOspiteResponse }
     * 
     * 
     */
    public List<ServiziOspite.ServizioOspiteResponse> getServizioOspiteResponse() {
        if (servizioOspiteResponse == null) {
            servizioOspiteResponse = new ArrayList<ServiziOspite.ServizioOspiteResponse>();
        }
        return this.servizioOspiteResponse;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="DataFine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DataInizio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IGUCausFineServ" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IdEntitaDes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IdEntitaProp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="IdMessaggio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "dataFine",
        "dataInizio",
        "iguCausFineServ",
        "idEntitaDes",
        "idEntitaProp",
        "idMessaggio"
    })
    public static class ServizioOspiteResponse {

        @XmlElement(name = "DataFine")
        protected String dataFine;
        @XmlElement(name = "DataInizio")
        protected String dataInizio;
        @XmlElement(name = "IGUCausFineServ")
        protected String iguCausFineServ;
        @XmlElement(name = "IdEntitaDes")
        protected String idEntitaDes;
        @XmlElement(name = "IdEntitaProp")
        protected String idEntitaProp;
        @XmlElement(name = "IdMessaggio")
        protected String idMessaggio;

        /**
         * Gets the value of the dataFine property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataFine() {
            return dataFine;
        }

        /**
         * Sets the value of the dataFine property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataFine(String value) {
            this.dataFine = value;
        }

        /**
         * Gets the value of the dataInizio property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataInizio() {
            return dataInizio;
        }

        /**
         * Sets the value of the dataInizio property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataInizio(String value) {
            this.dataInizio = value;
        }

        /**
         * Gets the value of the iguCausFineServ property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIGUCausFineServ() {
            return iguCausFineServ;
        }

        /**
         * Sets the value of the iguCausFineServ property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIGUCausFineServ(String value) {
            this.iguCausFineServ = value;
        }

        /**
         * Gets the value of the idEntitaDes property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdEntitaDes() {
            return idEntitaDes;
        }

        /**
         * Sets the value of the idEntitaDes property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdEntitaDes(String value) {
            this.idEntitaDes = value;
        }

        /**
         * Gets the value of the idEntitaProp property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdEntitaProp() {
            return idEntitaProp;
        }

        /**
         * Sets the value of the idEntitaProp property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdEntitaProp(String value) {
            this.idEntitaProp = value;
        }

        /**
         * Gets the value of the idMessaggio property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIdMessaggio() {
            return idMessaggio;
        }

        /**
         * Sets the value of the idMessaggio property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIdMessaggio(String value) {
            this.idMessaggio = value;
        }

    }

}

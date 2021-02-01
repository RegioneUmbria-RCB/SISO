/**
 * Famiglia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class Famiglia  implements java.io.Serializable {
    private java.lang.String codiceFamiglia;

    private it.roma.comune.servizi.dto.Componente[] componenti;

    private it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo;

    private java.lang.String flagFamigliaConvivenza;

    public Famiglia() {
    }

    public Famiglia(
           java.lang.String codiceFamiglia,
           it.roma.comune.servizi.dto.Componente[] componenti,
           it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo,
           java.lang.String flagFamigliaConvivenza) {
           this.codiceFamiglia = codiceFamiglia;
           this.componenti = componenti;
           this.datiIndirizzo = datiIndirizzo;
           this.flagFamigliaConvivenza = flagFamigliaConvivenza;
    }


    /**
     * Gets the codiceFamiglia value for this Famiglia.
     * 
     * @return codiceFamiglia
     */
    public java.lang.String getCodiceFamiglia() {
        return codiceFamiglia;
    }


    /**
     * Sets the codiceFamiglia value for this Famiglia.
     * 
     * @param codiceFamiglia
     */
    public void setCodiceFamiglia(java.lang.String codiceFamiglia) {
        this.codiceFamiglia = codiceFamiglia;
    }


    /**
     * Gets the componenti value for this Famiglia.
     * 
     * @return componenti
     */
    public it.roma.comune.servizi.dto.Componente[] getComponenti() {
        return componenti;
    }


    /**
     * Sets the componenti value for this Famiglia.
     * 
     * @param componenti
     */
    public void setComponenti(it.roma.comune.servizi.dto.Componente[] componenti) {
        this.componenti = componenti;
    }


    /**
     * Gets the datiIndirizzo value for this Famiglia.
     * 
     * @return datiIndirizzo
     */
    public it.roma.comune.servizi.dto.DatiIndirizzo getDatiIndirizzo() {
        return datiIndirizzo;
    }


    /**
     * Sets the datiIndirizzo value for this Famiglia.
     * 
     * @param datiIndirizzo
     */
    public void setDatiIndirizzo(it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo) {
        this.datiIndirizzo = datiIndirizzo;
    }


    /**
     * Gets the flagFamigliaConvivenza value for this Famiglia.
     * 
     * @return flagFamigliaConvivenza
     */
    public java.lang.String getFlagFamigliaConvivenza() {
        return flagFamigliaConvivenza;
    }


    /**
     * Sets the flagFamigliaConvivenza value for this Famiglia.
     * 
     * @param flagFamigliaConvivenza
     */
    public void setFlagFamigliaConvivenza(java.lang.String flagFamigliaConvivenza) {
        this.flagFamigliaConvivenza = flagFamigliaConvivenza;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Famiglia)) return false;
        Famiglia other = (Famiglia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFamiglia==null && other.getCodiceFamiglia()==null) || 
             (this.codiceFamiglia!=null &&
              this.codiceFamiglia.equals(other.getCodiceFamiglia()))) &&
            ((this.componenti==null && other.getComponenti()==null) || 
             (this.componenti!=null &&
              java.util.Arrays.equals(this.componenti, other.getComponenti()))) &&
            ((this.datiIndirizzo==null && other.getDatiIndirizzo()==null) || 
             (this.datiIndirizzo!=null &&
              this.datiIndirizzo.equals(other.getDatiIndirizzo()))) &&
            ((this.flagFamigliaConvivenza==null && other.getFlagFamigliaConvivenza()==null) || 
             (this.flagFamigliaConvivenza!=null &&
              this.flagFamigliaConvivenza.equals(other.getFlagFamigliaConvivenza())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCodiceFamiglia() != null) {
            _hashCode += getCodiceFamiglia().hashCode();
        }
        if (getComponenti() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getComponenti());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getComponenti(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDatiIndirizzo() != null) {
            _hashCode += getDatiIndirizzo().hashCode();
        }
        if (getFlagFamigliaConvivenza() != null) {
            _hashCode += getFlagFamigliaConvivenza().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Famiglia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Famiglia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFamiglia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "codiceFamiglia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("componenti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "componenti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Componente"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://test.servizi.comune.roma.it", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiIndirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "datiIndirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "DatiIndirizzo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagFamigliaConvivenza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "flagFamigliaConvivenza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}

/**
 * Persona.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class Persona  implements java.io.Serializable {
    private it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma;

    private it.roma.comune.servizi.dto.Nascita datiDiNascita;

    private it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo;

    private it.roma.comune.servizi.dto.PersonaCompleta personaCompleta;

    public Persona() {
    }

    public Persona(
           it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma,
           it.roma.comune.servizi.dto.Nascita datiDiNascita,
           it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo,
           it.roma.comune.servizi.dto.PersonaCompleta personaCompleta) {
           this.datiAnagrafeRoma = datiAnagrafeRoma;
           this.datiDiNascita = datiDiNascita;
           this.datiIndirizzo = datiIndirizzo;
           this.personaCompleta = personaCompleta;
    }


    /**
     * Gets the datiAnagrafeRoma value for this Persona.
     * 
     * @return datiAnagrafeRoma
     */
    public it.roma.comune.servizi.dto.DatiAnagrafeRoma getDatiAnagrafeRoma() {
        return datiAnagrafeRoma;
    }


    /**
     * Sets the datiAnagrafeRoma value for this Persona.
     * 
     * @param datiAnagrafeRoma
     */
    public void setDatiAnagrafeRoma(it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma) {
        this.datiAnagrafeRoma = datiAnagrafeRoma;
    }


    /**
     * Gets the datiDiNascita value for this Persona.
     * 
     * @return datiDiNascita
     */
    public it.roma.comune.servizi.dto.Nascita getDatiDiNascita() {
        return datiDiNascita;
    }


    /**
     * Sets the datiDiNascita value for this Persona.
     * 
     * @param datiDiNascita
     */
    public void setDatiDiNascita(it.roma.comune.servizi.dto.Nascita datiDiNascita) {
        this.datiDiNascita = datiDiNascita;
    }


    /**
     * Gets the datiIndirizzo value for this Persona.
     * 
     * @return datiIndirizzo
     */
    public it.roma.comune.servizi.dto.DatiIndirizzo getDatiIndirizzo() {
        return datiIndirizzo;
    }


    /**
     * Sets the datiIndirizzo value for this Persona.
     * 
     * @param datiIndirizzo
     */
    public void setDatiIndirizzo(it.roma.comune.servizi.dto.DatiIndirizzo datiIndirizzo) {
        this.datiIndirizzo = datiIndirizzo;
    }


    /**
     * Gets the personaCompleta value for this Persona.
     * 
     * @return personaCompleta
     */
    public it.roma.comune.servizi.dto.PersonaCompleta getPersonaCompleta() {
        return personaCompleta;
    }


    /**
     * Sets the personaCompleta value for this Persona.
     * 
     * @param personaCompleta
     */
    public void setPersonaCompleta(it.roma.comune.servizi.dto.PersonaCompleta personaCompleta) {
        this.personaCompleta = personaCompleta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Persona)) return false;
        Persona other = (Persona) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.datiAnagrafeRoma==null && other.getDatiAnagrafeRoma()==null) || 
             (this.datiAnagrafeRoma!=null &&
              this.datiAnagrafeRoma.equals(other.getDatiAnagrafeRoma()))) &&
            ((this.datiDiNascita==null && other.getDatiDiNascita()==null) || 
             (this.datiDiNascita!=null &&
              this.datiDiNascita.equals(other.getDatiDiNascita()))) &&
            ((this.datiIndirizzo==null && other.getDatiIndirizzo()==null) || 
             (this.datiIndirizzo!=null &&
              this.datiIndirizzo.equals(other.getDatiIndirizzo()))) &&
            ((this.personaCompleta==null && other.getPersonaCompleta()==null) || 
             (this.personaCompleta!=null &&
              this.personaCompleta.equals(other.getPersonaCompleta())));
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
        if (getDatiAnagrafeRoma() != null) {
            _hashCode += getDatiAnagrafeRoma().hashCode();
        }
        if (getDatiDiNascita() != null) {
            _hashCode += getDatiDiNascita().hashCode();
        }
        if (getDatiIndirizzo() != null) {
            _hashCode += getDatiIndirizzo().hashCode();
        }
        if (getPersonaCompleta() != null) {
            _hashCode += getPersonaCompleta().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Persona.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Persona"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiAnagrafeRoma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "datiAnagrafeRoma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "DatiAnagrafeRoma"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiDiNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "datiDiNascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Nascita"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiIndirizzo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "datiIndirizzo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "DatiIndirizzo"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaCompleta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "personaCompleta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "PersonaCompleta"));
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

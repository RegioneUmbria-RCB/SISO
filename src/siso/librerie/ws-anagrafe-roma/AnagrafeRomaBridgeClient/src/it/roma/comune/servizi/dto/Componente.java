/**
 * Componente.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class Componente  implements java.io.Serializable {
    private java.lang.String codiceIndividuale;

    private it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma;

    private it.roma.comune.servizi.dto.Nascita nascita;

    private it.roma.comune.servizi.dto.PersonaCompleta personaCompleta;

    private java.lang.String rapportoParentela;

    public Componente() {
    }

    public Componente(
           java.lang.String codiceIndividuale,
           it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma,
           it.roma.comune.servizi.dto.Nascita nascita,
           it.roma.comune.servizi.dto.PersonaCompleta personaCompleta,
           java.lang.String rapportoParentela) {
           this.codiceIndividuale = codiceIndividuale;
           this.datiAnagrafeRoma = datiAnagrafeRoma;
           this.nascita = nascita;
           this.personaCompleta = personaCompleta;
           this.rapportoParentela = rapportoParentela;
    }


    /**
     * Gets the codiceIndividuale value for this Componente.
     * 
     * @return codiceIndividuale
     */
    public java.lang.String getCodiceIndividuale() {
        return codiceIndividuale;
    }


    /**
     * Sets the codiceIndividuale value for this Componente.
     * 
     * @param codiceIndividuale
     */
    public void setCodiceIndividuale(java.lang.String codiceIndividuale) {
        this.codiceIndividuale = codiceIndividuale;
    }


    /**
     * Gets the datiAnagrafeRoma value for this Componente.
     * 
     * @return datiAnagrafeRoma
     */
    public it.roma.comune.servizi.dto.DatiAnagrafeRoma getDatiAnagrafeRoma() {
        return datiAnagrafeRoma;
    }


    /**
     * Sets the datiAnagrafeRoma value for this Componente.
     * 
     * @param datiAnagrafeRoma
     */
    public void setDatiAnagrafeRoma(it.roma.comune.servizi.dto.DatiAnagrafeRoma datiAnagrafeRoma) {
        this.datiAnagrafeRoma = datiAnagrafeRoma;
    }


    /**
     * Gets the nascita value for this Componente.
     * 
     * @return nascita
     */
    public it.roma.comune.servizi.dto.Nascita getNascita() {
        return nascita;
    }


    /**
     * Sets the nascita value for this Componente.
     * 
     * @param nascita
     */
    public void setNascita(it.roma.comune.servizi.dto.Nascita nascita) {
        this.nascita = nascita;
    }


    /**
     * Gets the personaCompleta value for this Componente.
     * 
     * @return personaCompleta
     */
    public it.roma.comune.servizi.dto.PersonaCompleta getPersonaCompleta() {
        return personaCompleta;
    }


    /**
     * Sets the personaCompleta value for this Componente.
     * 
     * @param personaCompleta
     */
    public void setPersonaCompleta(it.roma.comune.servizi.dto.PersonaCompleta personaCompleta) {
        this.personaCompleta = personaCompleta;
    }


    /**
     * Gets the rapportoParentela value for this Componente.
     * 
     * @return rapportoParentela
     */
    public java.lang.String getRapportoParentela() {
        return rapportoParentela;
    }


    /**
     * Sets the rapportoParentela value for this Componente.
     * 
     * @param rapportoParentela
     */
    public void setRapportoParentela(java.lang.String rapportoParentela) {
        this.rapportoParentela = rapportoParentela;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Componente)) return false;
        Componente other = (Componente) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceIndividuale==null && other.getCodiceIndividuale()==null) || 
             (this.codiceIndividuale!=null &&
              this.codiceIndividuale.equals(other.getCodiceIndividuale()))) &&
            ((this.datiAnagrafeRoma==null && other.getDatiAnagrafeRoma()==null) || 
             (this.datiAnagrafeRoma!=null &&
              this.datiAnagrafeRoma.equals(other.getDatiAnagrafeRoma()))) &&
            ((this.nascita==null && other.getNascita()==null) || 
             (this.nascita!=null &&
              this.nascita.equals(other.getNascita()))) &&
            ((this.personaCompleta==null && other.getPersonaCompleta()==null) || 
             (this.personaCompleta!=null &&
              this.personaCompleta.equals(other.getPersonaCompleta()))) &&
            ((this.rapportoParentela==null && other.getRapportoParentela()==null) || 
             (this.rapportoParentela!=null &&
              this.rapportoParentela.equals(other.getRapportoParentela())));
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
        if (getCodiceIndividuale() != null) {
            _hashCode += getCodiceIndividuale().hashCode();
        }
        if (getDatiAnagrafeRoma() != null) {
            _hashCode += getDatiAnagrafeRoma().hashCode();
        }
        if (getNascita() != null) {
            _hashCode += getNascita().hashCode();
        }
        if (getPersonaCompleta() != null) {
            _hashCode += getPersonaCompleta().hashCode();
        }
        if (getRapportoParentela() != null) {
            _hashCode += getRapportoParentela().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Componente.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Componente"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIndividuale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "codiceIndividuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datiAnagrafeRoma");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "datiAnagrafeRoma"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "DatiAnagrafeRoma"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "nascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Nascita"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("personaCompleta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "personaCompleta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "PersonaCompleta"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rapportoParentela");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "rapportoParentela"));
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

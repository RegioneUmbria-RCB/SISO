/**
 * RicercaResult.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto.xsd;

public class RicercaResult  implements java.io.Serializable {
    private it.roma.comune.servizi.dto.xsd.Persona[] elencoPersona;

    private java.lang.String esito;

    public RicercaResult() {
    }

    public RicercaResult(
           it.roma.comune.servizi.dto.xsd.Persona[] elencoPersona,
           java.lang.String esito) {
           this.elencoPersona = elencoPersona;
           this.esito = esito;
    }


    /**
     * Gets the elencoPersona value for this RicercaResult.
     * 
     * @return elencoPersona
     */
    public it.roma.comune.servizi.dto.xsd.Persona[] getElencoPersona() {
        return elencoPersona;
    }


    /**
     * Sets the elencoPersona value for this RicercaResult.
     * 
     * @param elencoPersona
     */
    public void setElencoPersona(it.roma.comune.servizi.dto.xsd.Persona[] elencoPersona) {
        this.elencoPersona = elencoPersona;
    }

    public it.roma.comune.servizi.dto.xsd.Persona getElencoPersona(int i) {
        return this.elencoPersona[i];
    }

    public void setElencoPersona(int i, it.roma.comune.servizi.dto.xsd.Persona _value) {
        this.elencoPersona[i] = _value;
    }


    /**
     * Gets the esito value for this RicercaResult.
     * 
     * @return esito
     */
    public java.lang.String getEsito() {
        return esito;
    }


    /**
     * Sets the esito value for this RicercaResult.
     * 
     * @param esito
     */
    public void setEsito(java.lang.String esito) {
        this.esito = esito;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RicercaResult)) return false;
        RicercaResult other = (RicercaResult) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.elencoPersona==null && other.getElencoPersona()==null) || 
             (this.elencoPersona!=null &&
              java.util.Arrays.equals(this.elencoPersona, other.getElencoPersona()))) &&
            ((this.esito==null && other.getEsito()==null) || 
             (this.esito!=null &&
              this.esito.equals(other.getEsito())));
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
        if (getElencoPersona() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getElencoPersona());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getElencoPersona(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getEsito() != null) {
            _hashCode += getEsito().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RicercaResult.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "RicercaResult"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("elencoPersona");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "elencoPersona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "Persona"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("esito");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "esito"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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

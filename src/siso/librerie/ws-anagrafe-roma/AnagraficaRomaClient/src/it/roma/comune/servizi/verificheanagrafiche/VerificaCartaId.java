/**
 * VerificaCartaId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaCartaId  implements java.io.Serializable {
    private java.lang.String carta;

    private org.apache.axis.types.UnsignedByte serviceLevel;

    public VerificaCartaId() {
    }

    public VerificaCartaId(
           java.lang.String carta,
           org.apache.axis.types.UnsignedByte serviceLevel) {
           this.carta = carta;
           this.serviceLevel = serviceLevel;
    }


    /**
     * Gets the carta value for this VerificaCartaId.
     * 
     * @return carta
     */
    public java.lang.String getCarta() {
        return carta;
    }


    /**
     * Sets the carta value for this VerificaCartaId.
     * 
     * @param carta
     */
    public void setCarta(java.lang.String carta) {
        this.carta = carta;
    }


    /**
     * Gets the serviceLevel value for this VerificaCartaId.
     * 
     * @return serviceLevel
     */
    public org.apache.axis.types.UnsignedByte getServiceLevel() {
        return serviceLevel;
    }


    /**
     * Sets the serviceLevel value for this VerificaCartaId.
     * 
     * @param serviceLevel
     */
    public void setServiceLevel(org.apache.axis.types.UnsignedByte serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaCartaId)) return false;
        VerificaCartaId other = (VerificaCartaId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.carta==null && other.getCarta()==null) || 
             (this.carta!=null &&
              this.carta.equals(other.getCarta()))) &&
            ((this.serviceLevel==null && other.getServiceLevel()==null) || 
             (this.serviceLevel!=null &&
              this.serviceLevel.equals(other.getServiceLevel())));
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
        if (getCarta() != null) {
            _hashCode += getCarta().hashCode();
        }
        if (getServiceLevel() != null) {
            _hashCode += getServiceLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaCartaId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Carta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "ServiceLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
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

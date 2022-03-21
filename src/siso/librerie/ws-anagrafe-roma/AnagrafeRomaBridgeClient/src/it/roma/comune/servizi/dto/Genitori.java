/**
 * Genitori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class Genitori  implements java.io.Serializable {
    private java.lang.String madre;

    private java.lang.String nominativoMadre;

    private java.lang.String nominativoPadre;

    private java.lang.String padre;

    public Genitori() {
    }

    public Genitori(
           java.lang.String madre,
           java.lang.String nominativoMadre,
           java.lang.String nominativoPadre,
           java.lang.String padre) {
           this.madre = madre;
           this.nominativoMadre = nominativoMadre;
           this.nominativoPadre = nominativoPadre;
           this.padre = padre;
    }


    /**
     * Gets the madre value for this Genitori.
     * 
     * @return madre
     */
    public java.lang.String getMadre() {
        return madre;
    }


    /**
     * Sets the madre value for this Genitori.
     * 
     * @param madre
     */
    public void setMadre(java.lang.String madre) {
        this.madre = madre;
    }


    /**
     * Gets the nominativoMadre value for this Genitori.
     * 
     * @return nominativoMadre
     */
    public java.lang.String getNominativoMadre() {
        return nominativoMadre;
    }


    /**
     * Sets the nominativoMadre value for this Genitori.
     * 
     * @param nominativoMadre
     */
    public void setNominativoMadre(java.lang.String nominativoMadre) {
        this.nominativoMadre = nominativoMadre;
    }


    /**
     * Gets the nominativoPadre value for this Genitori.
     * 
     * @return nominativoPadre
     */
    public java.lang.String getNominativoPadre() {
        return nominativoPadre;
    }


    /**
     * Sets the nominativoPadre value for this Genitori.
     * 
     * @param nominativoPadre
     */
    public void setNominativoPadre(java.lang.String nominativoPadre) {
        this.nominativoPadre = nominativoPadre;
    }


    /**
     * Gets the padre value for this Genitori.
     * 
     * @return padre
     */
    public java.lang.String getPadre() {
        return padre;
    }


    /**
     * Sets the padre value for this Genitori.
     * 
     * @param padre
     */
    public void setPadre(java.lang.String padre) {
        this.padre = padre;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Genitori)) return false;
        Genitori other = (Genitori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.madre==null && other.getMadre()==null) || 
             (this.madre!=null &&
              this.madre.equals(other.getMadre()))) &&
            ((this.nominativoMadre==null && other.getNominativoMadre()==null) || 
             (this.nominativoMadre!=null &&
              this.nominativoMadre.equals(other.getNominativoMadre()))) &&
            ((this.nominativoPadre==null && other.getNominativoPadre()==null) || 
             (this.nominativoPadre!=null &&
              this.nominativoPadre.equals(other.getNominativoPadre()))) &&
            ((this.padre==null && other.getPadre()==null) || 
             (this.padre!=null &&
              this.padre.equals(other.getPadre())));
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
        if (getMadre() != null) {
            _hashCode += getMadre().hashCode();
        }
        if (getNominativoMadre() != null) {
            _hashCode += getNominativoMadre().hashCode();
        }
        if (getNominativoPadre() != null) {
            _hashCode += getNominativoPadre().hashCode();
        }
        if (getPadre() != null) {
            _hashCode += getPadre().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Genitori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "Genitori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("madre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "madre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nominativoMadre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "nominativoMadre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nominativoPadre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "nominativoPadre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("padre");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "padre"));
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

/**
 * TopoServiceTypeGetCivicoTipoViaType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfTopo;

public class TopoServiceTypeGetCivicoTipoViaType  implements java.io.Serializable {
    private java.lang.String code;

    private java.lang.String descriptioShort;

    private java.lang.String descriptionExtended;

    public TopoServiceTypeGetCivicoTipoViaType() {
    }

    public TopoServiceTypeGetCivicoTipoViaType(
           java.lang.String code,
           java.lang.String descriptioShort,
           java.lang.String descriptionExtended) {
           this.code = code;
           this.descriptioShort = descriptioShort;
           this.descriptionExtended = descriptionExtended;
    }


    /**
     * Gets the code value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @return code
     */
    public java.lang.String getCode() {
        return code;
    }


    /**
     * Sets the code value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @param code
     */
    public void setCode(java.lang.String code) {
        this.code = code;
    }


    /**
     * Gets the descriptioShort value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @return descriptioShort
     */
    public java.lang.String getDescriptioShort() {
        return descriptioShort;
    }


    /**
     * Sets the descriptioShort value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @param descriptioShort
     */
    public void setDescriptioShort(java.lang.String descriptioShort) {
        this.descriptioShort = descriptioShort;
    }


    /**
     * Gets the descriptionExtended value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @return descriptionExtended
     */
    public java.lang.String getDescriptionExtended() {
        return descriptionExtended;
    }


    /**
     * Sets the descriptionExtended value for this TopoServiceTypeGetCivicoTipoViaType.
     * 
     * @param descriptionExtended
     */
    public void setDescriptionExtended(java.lang.String descriptionExtended) {
        this.descriptionExtended = descriptionExtended;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoServiceTypeGetCivicoTipoViaType)) return false;
        TopoServiceTypeGetCivicoTipoViaType other = (TopoServiceTypeGetCivicoTipoViaType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.code==null && other.getCode()==null) || 
             (this.code!=null &&
              this.code.equals(other.getCode()))) &&
            ((this.descriptioShort==null && other.getDescriptioShort()==null) || 
             (this.descriptioShort!=null &&
              this.descriptioShort.equals(other.getDescriptioShort()))) &&
            ((this.descriptionExtended==null && other.getDescriptionExtended()==null) || 
             (this.descriptionExtended!=null &&
              this.descriptionExtended.equals(other.getDescriptionExtended())));
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
        if (getCode() != null) {
            _hashCode += getCode().hashCode();
        }
        if (getDescriptioShort() != null) {
            _hashCode += getDescriptioShort().hashCode();
        }
        if (getDescriptionExtended() != null) {
            _hashCode += getDescriptionExtended().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoServiceTypeGetCivicoTipoViaType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("code");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptioShort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "DescriptioShort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionExtended");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "DescriptionExtended"));
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

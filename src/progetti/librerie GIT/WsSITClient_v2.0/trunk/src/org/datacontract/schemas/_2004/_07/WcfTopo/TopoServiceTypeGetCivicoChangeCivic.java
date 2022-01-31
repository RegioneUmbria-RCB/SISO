/**
 * TopoServiceTypeGetCivicoChangeCivic.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfTopo;

public class TopoServiceTypeGetCivicoChangeCivic  implements java.io.Serializable {
    private java.lang.String dateDown;

    private java.lang.String dateUp;

    private java.lang.Integer IDC;

    public TopoServiceTypeGetCivicoChangeCivic() {
    }

    public TopoServiceTypeGetCivicoChangeCivic(
           java.lang.String dateDown,
           java.lang.String dateUp,
           java.lang.Integer IDC) {
           this.dateDown = dateDown;
           this.dateUp = dateUp;
           this.IDC = IDC;
    }


    /**
     * Gets the dateDown value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @return dateDown
     */
    public java.lang.String getDateDown() {
        return dateDown;
    }


    /**
     * Sets the dateDown value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @param dateDown
     */
    public void setDateDown(java.lang.String dateDown) {
        this.dateDown = dateDown;
    }


    /**
     * Gets the dateUp value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @return dateUp
     */
    public java.lang.String getDateUp() {
        return dateUp;
    }


    /**
     * Sets the dateUp value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @param dateUp
     */
    public void setDateUp(java.lang.String dateUp) {
        this.dateUp = dateUp;
    }


    /**
     * Gets the IDC value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @return IDC
     */
    public java.lang.Integer getIDC() {
        return IDC;
    }


    /**
     * Sets the IDC value for this TopoServiceTypeGetCivicoChangeCivic.
     * 
     * @param IDC
     */
    public void setIDC(java.lang.Integer IDC) {
        this.IDC = IDC;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoServiceTypeGetCivicoChangeCivic)) return false;
        TopoServiceTypeGetCivicoChangeCivic other = (TopoServiceTypeGetCivicoChangeCivic) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dateDown==null && other.getDateDown()==null) || 
             (this.dateDown!=null &&
              this.dateDown.equals(other.getDateDown()))) &&
            ((this.dateUp==null && other.getDateUp()==null) || 
             (this.dateUp!=null &&
              this.dateUp.equals(other.getDateUp()))) &&
            ((this.IDC==null && other.getIDC()==null) || 
             (this.IDC!=null &&
              this.IDC.equals(other.getIDC())));
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
        if (getDateDown() != null) {
            _hashCode += getDateDown().hashCode();
        }
        if (getDateUp() != null) {
            _hashCode += getDateUp().hashCode();
        }
        if (getIDC() != null) {
            _hashCode += getIDC().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoServiceTypeGetCivicoChangeCivic.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoChangeCivic"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateDown");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "DateDown"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateUp");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "DateUp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "IDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
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

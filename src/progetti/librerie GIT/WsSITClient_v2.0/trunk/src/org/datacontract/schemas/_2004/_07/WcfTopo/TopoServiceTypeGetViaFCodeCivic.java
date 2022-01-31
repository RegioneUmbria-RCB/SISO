/**
 * TopoServiceTypeGetViaFCodeCivic.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfTopo;

public class TopoServiceTypeGetViaFCodeCivic  implements java.io.Serializable {
    private java.lang.Double coordinateX;

    private java.lang.Double coordinateY;

    private java.lang.Integer IDC;

    private java.lang.Integer stateCode;

    private java.lang.String toponym;

    public TopoServiceTypeGetViaFCodeCivic() {
    }

    public TopoServiceTypeGetViaFCodeCivic(
           java.lang.Double coordinateX,
           java.lang.Double coordinateY,
           java.lang.Integer IDC,
           java.lang.Integer stateCode,
           java.lang.String toponym) {
           this.coordinateX = coordinateX;
           this.coordinateY = coordinateY;
           this.IDC = IDC;
           this.stateCode = stateCode;
           this.toponym = toponym;
    }


    /**
     * Gets the coordinateX value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @return coordinateX
     */
    public java.lang.Double getCoordinateX() {
        return coordinateX;
    }


    /**
     * Sets the coordinateX value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @param coordinateX
     */
    public void setCoordinateX(java.lang.Double coordinateX) {
        this.coordinateX = coordinateX;
    }


    /**
     * Gets the coordinateY value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @return coordinateY
     */
    public java.lang.Double getCoordinateY() {
        return coordinateY;
    }


    /**
     * Sets the coordinateY value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @param coordinateY
     */
    public void setCoordinateY(java.lang.Double coordinateY) {
        this.coordinateY = coordinateY;
    }


    /**
     * Gets the IDC value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @return IDC
     */
    public java.lang.Integer getIDC() {
        return IDC;
    }


    /**
     * Sets the IDC value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @param IDC
     */
    public void setIDC(java.lang.Integer IDC) {
        this.IDC = IDC;
    }


    /**
     * Gets the stateCode value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @return stateCode
     */
    public java.lang.Integer getStateCode() {
        return stateCode;
    }


    /**
     * Sets the stateCode value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @param stateCode
     */
    public void setStateCode(java.lang.Integer stateCode) {
        this.stateCode = stateCode;
    }


    /**
     * Gets the toponym value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @return toponym
     */
    public java.lang.String getToponym() {
        return toponym;
    }


    /**
     * Sets the toponym value for this TopoServiceTypeGetViaFCodeCivic.
     * 
     * @param toponym
     */
    public void setToponym(java.lang.String toponym) {
        this.toponym = toponym;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoServiceTypeGetViaFCodeCivic)) return false;
        TopoServiceTypeGetViaFCodeCivic other = (TopoServiceTypeGetViaFCodeCivic) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.coordinateX==null && other.getCoordinateX()==null) || 
             (this.coordinateX!=null &&
              this.coordinateX.equals(other.getCoordinateX()))) &&
            ((this.coordinateY==null && other.getCoordinateY()==null) || 
             (this.coordinateY!=null &&
              this.coordinateY.equals(other.getCoordinateY()))) &&
            ((this.IDC==null && other.getIDC()==null) || 
             (this.IDC!=null &&
              this.IDC.equals(other.getIDC()))) &&
            ((this.stateCode==null && other.getStateCode()==null) || 
             (this.stateCode!=null &&
              this.stateCode.equals(other.getStateCode()))) &&
            ((this.toponym==null && other.getToponym()==null) || 
             (this.toponym!=null &&
              this.toponym.equals(other.getToponym())));
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
        if (getCoordinateX() != null) {
            _hashCode += getCoordinateX().hashCode();
        }
        if (getCoordinateY() != null) {
            _hashCode += getCoordinateY().hashCode();
        }
        if (getIDC() != null) {
            _hashCode += getIDC().hashCode();
        }
        if (getStateCode() != null) {
            _hashCode += getStateCode().hashCode();
        }
        if (getToponym() != null) {
            _hashCode += getToponym().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoServiceTypeGetViaFCodeCivic.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetViaFCodeCivic"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinateX");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "CoordinateX"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinateY");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "CoordinateY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IDC");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "IDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "StateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toponym");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Toponym"));
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

/**
 * TopoServiceTypeGetTipoVia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfTopo;

public class TopoServiceTypeGetTipoVia  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeError err;

    private org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType[] type;

    public TopoServiceTypeGetTipoVia() {
    }

    public TopoServiceTypeGetTipoVia(
           org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeError err,
           org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType[] type) {
           this.err = err;
           this.type = type;
    }


    /**
     * Gets the err value for this TopoServiceTypeGetTipoVia.
     * 
     * @return err
     */
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeError getErr() {
        return err;
    }


    /**
     * Sets the err value for this TopoServiceTypeGetTipoVia.
     * 
     * @param err
     */
    public void setErr(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeError err) {
        this.err = err;
    }


    /**
     * Gets the type value for this TopoServiceTypeGetTipoVia.
     * 
     * @return type
     */
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType[] getType() {
        return type;
    }


    /**
     * Sets the type value for this TopoServiceTypeGetTipoVia.
     * 
     * @param type
     */
    public void setType(org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoTipoViaType[] type) {
        this.type = type;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoServiceTypeGetTipoVia)) return false;
        TopoServiceTypeGetTipoVia other = (TopoServiceTypeGetTipoVia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.err==null && other.getErr()==null) || 
             (this.err!=null &&
              this.err.equals(other.getErr()))) &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              java.util.Arrays.equals(this.type, other.getType())));
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
        if (getErr() != null) {
            _hashCode += getErr().hashCode();
        }
        if (getType() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getType());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getType(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoServiceTypeGetTipoVia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetTipoVia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("err");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Err"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeError"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetCivicoTipoViaType"));
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

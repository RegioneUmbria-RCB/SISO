/**
 * VerificaLeggeRutelliResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaLeggeRutelliResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponseVerificaLeggeRutelliResult verificaLeggeRutelliResult;

    public VerificaLeggeRutelliResponse() {
    }

    public VerificaLeggeRutelliResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponseVerificaLeggeRutelliResult verificaLeggeRutelliResult) {
           this.verificaLeggeRutelliResult = verificaLeggeRutelliResult;
    }


    /**
     * Gets the verificaLeggeRutelliResult value for this VerificaLeggeRutelliResponse.
     * 
     * @return verificaLeggeRutelliResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponseVerificaLeggeRutelliResult getVerificaLeggeRutelliResult() {
        return verificaLeggeRutelliResult;
    }


    /**
     * Sets the verificaLeggeRutelliResult value for this VerificaLeggeRutelliResponse.
     * 
     * @param verificaLeggeRutelliResult
     */
    public void setVerificaLeggeRutelliResult(it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponseVerificaLeggeRutelliResult verificaLeggeRutelliResult) {
        this.verificaLeggeRutelliResult = verificaLeggeRutelliResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaLeggeRutelliResponse)) return false;
        VerificaLeggeRutelliResponse other = (VerificaLeggeRutelliResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaLeggeRutelliResult==null && other.getVerificaLeggeRutelliResult()==null) || 
             (this.verificaLeggeRutelliResult!=null &&
              this.verificaLeggeRutelliResult.equals(other.getVerificaLeggeRutelliResult())));
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
        if (getVerificaLeggeRutelliResult() != null) {
            _hashCode += getVerificaLeggeRutelliResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaLeggeRutelliResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLeggeRutelliResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaLeggeRutelliResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLeggeRutelliResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaLeggeRutelliResponse>VerificaLeggeRutelliResult"));
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

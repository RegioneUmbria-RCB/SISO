/**
 * VerificaCartaIdResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaCartaIdResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponseVerificaCartaIdResult verificaCartaIdResult;

    public VerificaCartaIdResponse() {
    }

    public VerificaCartaIdResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponseVerificaCartaIdResult verificaCartaIdResult) {
           this.verificaCartaIdResult = verificaCartaIdResult;
    }


    /**
     * Gets the verificaCartaIdResult value for this VerificaCartaIdResponse.
     * 
     * @return verificaCartaIdResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponseVerificaCartaIdResult getVerificaCartaIdResult() {
        return verificaCartaIdResult;
    }


    /**
     * Sets the verificaCartaIdResult value for this VerificaCartaIdResponse.
     * 
     * @param verificaCartaIdResult
     */
    public void setVerificaCartaIdResult(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponseVerificaCartaIdResult verificaCartaIdResult) {
        this.verificaCartaIdResult = verificaCartaIdResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaCartaIdResponse)) return false;
        VerificaCartaIdResponse other = (VerificaCartaIdResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaCartaIdResult==null && other.getVerificaCartaIdResult()==null) || 
             (this.verificaCartaIdResult!=null &&
              this.verificaCartaIdResult.equals(other.getVerificaCartaIdResult())));
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
        if (getVerificaCartaIdResult() != null) {
            _hashCode += getVerificaCartaIdResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaCartaIdResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaCartaIdResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaIdResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaCartaIdResponse>VerificaCartaIdResult"));
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

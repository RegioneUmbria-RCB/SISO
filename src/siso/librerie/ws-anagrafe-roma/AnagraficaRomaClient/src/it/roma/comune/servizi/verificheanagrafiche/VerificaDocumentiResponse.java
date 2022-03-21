/**
 * VerificaDocumentiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaDocumentiResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponseVerificaDocumentiResult verificaDocumentiResult;

    public VerificaDocumentiResponse() {
    }

    public VerificaDocumentiResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponseVerificaDocumentiResult verificaDocumentiResult) {
           this.verificaDocumentiResult = verificaDocumentiResult;
    }


    /**
     * Gets the verificaDocumentiResult value for this VerificaDocumentiResponse.
     * 
     * @return verificaDocumentiResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponseVerificaDocumentiResult getVerificaDocumentiResult() {
        return verificaDocumentiResult;
    }


    /**
     * Sets the verificaDocumentiResult value for this VerificaDocumentiResponse.
     * 
     * @param verificaDocumentiResult
     */
    public void setVerificaDocumentiResult(it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponseVerificaDocumentiResult verificaDocumentiResult) {
        this.verificaDocumentiResult = verificaDocumentiResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaDocumentiResponse)) return false;
        VerificaDocumentiResponse other = (VerificaDocumentiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaDocumentiResult==null && other.getVerificaDocumentiResult()==null) || 
             (this.verificaDocumentiResult!=null &&
              this.verificaDocumentiResult.equals(other.getVerificaDocumentiResult())));
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
        if (getVerificaDocumentiResult() != null) {
            _hashCode += getVerificaDocumentiResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaDocumentiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDocumentiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaDocumentiResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDocumentiResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDocumentiResponse>VerificaDocumentiResult"));
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

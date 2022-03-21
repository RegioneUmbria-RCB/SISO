/**
 * VerificaStoricaDocumentiResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaStoricaDocumentiResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponseVerificaStoricaDocumentiResult verificaStoricaDocumentiResult;

    public VerificaStoricaDocumentiResponse() {
    }

    public VerificaStoricaDocumentiResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponseVerificaStoricaDocumentiResult verificaStoricaDocumentiResult) {
           this.verificaStoricaDocumentiResult = verificaStoricaDocumentiResult;
    }


    /**
     * Gets the verificaStoricaDocumentiResult value for this VerificaStoricaDocumentiResponse.
     * 
     * @return verificaStoricaDocumentiResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponseVerificaStoricaDocumentiResult getVerificaStoricaDocumentiResult() {
        return verificaStoricaDocumentiResult;
    }


    /**
     * Sets the verificaStoricaDocumentiResult value for this VerificaStoricaDocumentiResponse.
     * 
     * @param verificaStoricaDocumentiResult
     */
    public void setVerificaStoricaDocumentiResult(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponseVerificaStoricaDocumentiResult verificaStoricaDocumentiResult) {
        this.verificaStoricaDocumentiResult = verificaStoricaDocumentiResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaStoricaDocumentiResponse)) return false;
        VerificaStoricaDocumentiResponse other = (VerificaStoricaDocumentiResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaStoricaDocumentiResult==null && other.getVerificaStoricaDocumentiResult()==null) || 
             (this.verificaStoricaDocumentiResult!=null &&
              this.verificaStoricaDocumentiResult.equals(other.getVerificaStoricaDocumentiResult())));
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
        if (getVerificaStoricaDocumentiResult() != null) {
            _hashCode += getVerificaStoricaDocumentiResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaStoricaDocumentiResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaDocumentiResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaStoricaDocumentiResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaDocumentiResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricaDocumentiResponse>VerificaStoricaDocumentiResult"));
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

/**
 * VerificaStoricaIndividuoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaStoricaIndividuoResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponseVerificaStoricaIndividuoResult verificaStoricaIndividuoResult;

    public VerificaStoricaIndividuoResponse() {
    }

    public VerificaStoricaIndividuoResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponseVerificaStoricaIndividuoResult verificaStoricaIndividuoResult) {
           this.verificaStoricaIndividuoResult = verificaStoricaIndividuoResult;
    }


    /**
     * Gets the verificaStoricaIndividuoResult value for this VerificaStoricaIndividuoResponse.
     * 
     * @return verificaStoricaIndividuoResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponseVerificaStoricaIndividuoResult getVerificaStoricaIndividuoResult() {
        return verificaStoricaIndividuoResult;
    }


    /**
     * Sets the verificaStoricaIndividuoResult value for this VerificaStoricaIndividuoResponse.
     * 
     * @param verificaStoricaIndividuoResult
     */
    public void setVerificaStoricaIndividuoResult(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponseVerificaStoricaIndividuoResult verificaStoricaIndividuoResult) {
        this.verificaStoricaIndividuoResult = verificaStoricaIndividuoResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaStoricaIndividuoResponse)) return false;
        VerificaStoricaIndividuoResponse other = (VerificaStoricaIndividuoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaStoricaIndividuoResult==null && other.getVerificaStoricaIndividuoResult()==null) || 
             (this.verificaStoricaIndividuoResult!=null &&
              this.verificaStoricaIndividuoResult.equals(other.getVerificaStoricaIndividuoResult())));
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
        if (getVerificaStoricaIndividuoResult() != null) {
            _hashCode += getVerificaStoricaIndividuoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaStoricaIndividuoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricaIndividuoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaStoricaIndividuoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricaIndividuoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricaIndividuoResponse>VerificaStoricaIndividuoResult"));
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

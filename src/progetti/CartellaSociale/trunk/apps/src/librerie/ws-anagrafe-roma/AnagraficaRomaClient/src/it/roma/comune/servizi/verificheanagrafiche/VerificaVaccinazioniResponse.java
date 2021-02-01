/**
 * VerificaVaccinazioniResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaVaccinazioniResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponseVerificaVaccinazioniResult verificaVaccinazioniResult;

    public VerificaVaccinazioniResponse() {
    }

    public VerificaVaccinazioniResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponseVerificaVaccinazioniResult verificaVaccinazioniResult) {
           this.verificaVaccinazioniResult = verificaVaccinazioniResult;
    }


    /**
     * Gets the verificaVaccinazioniResult value for this VerificaVaccinazioniResponse.
     * 
     * @return verificaVaccinazioniResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponseVerificaVaccinazioniResult getVerificaVaccinazioniResult() {
        return verificaVaccinazioniResult;
    }


    /**
     * Sets the verificaVaccinazioniResult value for this VerificaVaccinazioniResponse.
     * 
     * @param verificaVaccinazioniResult
     */
    public void setVerificaVaccinazioniResult(it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponseVerificaVaccinazioniResult verificaVaccinazioniResult) {
        this.verificaVaccinazioniResult = verificaVaccinazioniResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaVaccinazioniResponse)) return false;
        VerificaVaccinazioniResponse other = (VerificaVaccinazioniResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaVaccinazioniResult==null && other.getVerificaVaccinazioniResult()==null) || 
             (this.verificaVaccinazioniResult!=null &&
              this.verificaVaccinazioniResult.equals(other.getVerificaVaccinazioniResult())));
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
        if (getVerificaVaccinazioniResult() != null) {
            _hashCode += getVerificaVaccinazioniResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaVaccinazioniResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaVaccinazioniResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaVaccinazioniResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaVaccinazioniResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaVaccinazioniResponse>VerificaVaccinazioniResult"));
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

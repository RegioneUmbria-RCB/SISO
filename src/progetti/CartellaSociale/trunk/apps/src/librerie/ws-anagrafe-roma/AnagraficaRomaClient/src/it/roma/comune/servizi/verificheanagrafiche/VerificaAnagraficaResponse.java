/**
 * VerificaAnagraficaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaAnagraficaResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult verificaAnagraficaResult;

    public VerificaAnagraficaResponse() {
    }

    public VerificaAnagraficaResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult verificaAnagraficaResult) {
           this.verificaAnagraficaResult = verificaAnagraficaResult;
    }


    /**
     * Gets the verificaAnagraficaResult value for this VerificaAnagraficaResponse.
     * 
     * @return verificaAnagraficaResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult getVerificaAnagraficaResult() {
        return verificaAnagraficaResult;
    }


    /**
     * Sets the verificaAnagraficaResult value for this VerificaAnagraficaResponse.
     * 
     * @param verificaAnagraficaResult
     */
    public void setVerificaAnagraficaResult(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult verificaAnagraficaResult) {
        this.verificaAnagraficaResult = verificaAnagraficaResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaAnagraficaResponse)) return false;
        VerificaAnagraficaResponse other = (VerificaAnagraficaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaAnagraficaResult==null && other.getVerificaAnagraficaResult()==null) || 
             (this.verificaAnagraficaResult!=null &&
              this.verificaAnagraficaResult.equals(other.getVerificaAnagraficaResult())));
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
        if (getVerificaAnagraficaResult() != null) {
            _hashCode += getVerificaAnagraficaResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaAnagraficaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaAnagraficaResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagraficaResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaAnagraficaResponse>VerificaAnagraficaResult"));
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

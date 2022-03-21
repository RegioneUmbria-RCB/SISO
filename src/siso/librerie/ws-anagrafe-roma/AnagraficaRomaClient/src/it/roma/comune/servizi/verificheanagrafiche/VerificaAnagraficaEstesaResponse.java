/**
 * VerificaAnagraficaEstesaResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaAnagraficaEstesaResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult verificaAnagraficaEstesaResult;

    public VerificaAnagraficaEstesaResponse() {
    }

    public VerificaAnagraficaEstesaResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult verificaAnagraficaEstesaResult) {
           this.verificaAnagraficaEstesaResult = verificaAnagraficaEstesaResult;
    }


    /**
     * Gets the verificaAnagraficaEstesaResult value for this VerificaAnagraficaEstesaResponse.
     * 
     * @return verificaAnagraficaEstesaResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult getVerificaAnagraficaEstesaResult() {
        return verificaAnagraficaEstesaResult;
    }


    /**
     * Sets the verificaAnagraficaEstesaResult value for this VerificaAnagraficaEstesaResponse.
     * 
     * @param verificaAnagraficaEstesaResult
     */
    public void setVerificaAnagraficaEstesaResult(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult verificaAnagraficaEstesaResult) {
        this.verificaAnagraficaEstesaResult = verificaAnagraficaEstesaResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaAnagraficaEstesaResponse)) return false;
        VerificaAnagraficaEstesaResponse other = (VerificaAnagraficaEstesaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaAnagraficaEstesaResult==null && other.getVerificaAnagraficaEstesaResult()==null) || 
             (this.verificaAnagraficaEstesaResult!=null &&
              this.verificaAnagraficaEstesaResult.equals(other.getVerificaAnagraficaEstesaResult())));
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
        if (getVerificaAnagraficaEstesaResult() != null) {
            _hashCode += getVerificaAnagraficaEstesaResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaAnagraficaEstesaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaAnagraficaEstesaResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaAnagraficaEstesaResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaAnagraficaEstesaResponse>VerificaAnagraficaEstesaResult"));
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

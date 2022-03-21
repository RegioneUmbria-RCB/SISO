/**
 * VerificaStoricoFamigliaXDataResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaStoricoFamigliaXDataResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponseVerificaStoricoFamigliaXDataResult verificaStoricoFamigliaXDataResult;

    public VerificaStoricoFamigliaXDataResponse() {
    }

    public VerificaStoricoFamigliaXDataResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponseVerificaStoricoFamigliaXDataResult verificaStoricoFamigliaXDataResult) {
           this.verificaStoricoFamigliaXDataResult = verificaStoricoFamigliaXDataResult;
    }


    /**
     * Gets the verificaStoricoFamigliaXDataResult value for this VerificaStoricoFamigliaXDataResponse.
     * 
     * @return verificaStoricoFamigliaXDataResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponseVerificaStoricoFamigliaXDataResult getVerificaStoricoFamigliaXDataResult() {
        return verificaStoricoFamigliaXDataResult;
    }


    /**
     * Sets the verificaStoricoFamigliaXDataResult value for this VerificaStoricoFamigliaXDataResponse.
     * 
     * @param verificaStoricoFamigliaXDataResult
     */
    public void setVerificaStoricoFamigliaXDataResult(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponseVerificaStoricoFamigliaXDataResult verificaStoricoFamigliaXDataResult) {
        this.verificaStoricoFamigliaXDataResult = verificaStoricoFamigliaXDataResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaStoricoFamigliaXDataResponse)) return false;
        VerificaStoricoFamigliaXDataResponse other = (VerificaStoricoFamigliaXDataResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaStoricoFamigliaXDataResult==null && other.getVerificaStoricoFamigliaXDataResult()==null) || 
             (this.verificaStoricoFamigliaXDataResult!=null &&
              this.verificaStoricoFamigliaXDataResult.equals(other.getVerificaStoricoFamigliaXDataResult())));
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
        if (getVerificaStoricoFamigliaXDataResult() != null) {
            _hashCode += getVerificaStoricoFamigliaXDataResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaStoricoFamigliaXDataResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXDataResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaStoricoFamigliaXDataResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaStoricoFamigliaXDataResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaStoricoFamigliaXDataResponse>VerificaStoricoFamigliaXDataResult"));
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

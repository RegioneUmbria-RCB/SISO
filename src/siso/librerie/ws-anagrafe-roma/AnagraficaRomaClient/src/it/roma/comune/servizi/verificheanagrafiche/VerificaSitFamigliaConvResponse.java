/**
 * VerificaSitFamigliaConvResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaSitFamigliaConvResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponseVerificaSitFamigliaConvResult verificaSitFamigliaConvResult;

    public VerificaSitFamigliaConvResponse() {
    }

    public VerificaSitFamigliaConvResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponseVerificaSitFamigliaConvResult verificaSitFamigliaConvResult) {
           this.verificaSitFamigliaConvResult = verificaSitFamigliaConvResult;
    }


    /**
     * Gets the verificaSitFamigliaConvResult value for this VerificaSitFamigliaConvResponse.
     * 
     * @return verificaSitFamigliaConvResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponseVerificaSitFamigliaConvResult getVerificaSitFamigliaConvResult() {
        return verificaSitFamigliaConvResult;
    }


    /**
     * Sets the verificaSitFamigliaConvResult value for this VerificaSitFamigliaConvResponse.
     * 
     * @param verificaSitFamigliaConvResult
     */
    public void setVerificaSitFamigliaConvResult(it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponseVerificaSitFamigliaConvResult verificaSitFamigliaConvResult) {
        this.verificaSitFamigliaConvResult = verificaSitFamigliaConvResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaSitFamigliaConvResponse)) return false;
        VerificaSitFamigliaConvResponse other = (VerificaSitFamigliaConvResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaSitFamigliaConvResult==null && other.getVerificaSitFamigliaConvResult()==null) || 
             (this.verificaSitFamigliaConvResult!=null &&
              this.verificaSitFamigliaConvResult.equals(other.getVerificaSitFamigliaConvResult())));
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
        if (getVerificaSitFamigliaConvResult() != null) {
            _hashCode += getVerificaSitFamigliaConvResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaSitFamigliaConvResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaSitFamigliaConvResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaSitFamigliaConvResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaSitFamigliaConvResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaSitFamigliaConvResponse>VerificaSitFamigliaConvResult"));
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

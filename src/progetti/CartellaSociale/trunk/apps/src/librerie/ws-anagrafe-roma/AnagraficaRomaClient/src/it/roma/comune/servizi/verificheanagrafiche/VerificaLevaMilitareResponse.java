/**
 * VerificaLevaMilitareResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaLevaMilitareResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponseVerificaLevaMilitareResult verificaLevaMilitareResult;

    public VerificaLevaMilitareResponse() {
    }

    public VerificaLevaMilitareResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponseVerificaLevaMilitareResult verificaLevaMilitareResult) {
           this.verificaLevaMilitareResult = verificaLevaMilitareResult;
    }


    /**
     * Gets the verificaLevaMilitareResult value for this VerificaLevaMilitareResponse.
     * 
     * @return verificaLevaMilitareResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponseVerificaLevaMilitareResult getVerificaLevaMilitareResult() {
        return verificaLevaMilitareResult;
    }


    /**
     * Sets the verificaLevaMilitareResult value for this VerificaLevaMilitareResponse.
     * 
     * @param verificaLevaMilitareResult
     */
    public void setVerificaLevaMilitareResult(it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponseVerificaLevaMilitareResult verificaLevaMilitareResult) {
        this.verificaLevaMilitareResult = verificaLevaMilitareResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaLevaMilitareResponse)) return false;
        VerificaLevaMilitareResponse other = (VerificaLevaMilitareResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaLevaMilitareResult==null && other.getVerificaLevaMilitareResult()==null) || 
             (this.verificaLevaMilitareResult!=null &&
              this.verificaLevaMilitareResult.equals(other.getVerificaLevaMilitareResult())));
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
        if (getVerificaLevaMilitareResult() != null) {
            _hashCode += getVerificaLevaMilitareResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaLevaMilitareResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaLevaMilitareResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaLevaMilitareResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaLevaMilitareResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaLevaMilitareResponse>VerificaLevaMilitareResult"));
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

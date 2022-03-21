/**
 * VerificaDomiciliatiperIndirizzoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaDomiciliatiperIndirizzoResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponseVerificaDomiciliatiperIndirizzoResult verificaDomiciliatiperIndirizzoResult;

    public VerificaDomiciliatiperIndirizzoResponse() {
    }

    public VerificaDomiciliatiperIndirizzoResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponseVerificaDomiciliatiperIndirizzoResult verificaDomiciliatiperIndirizzoResult) {
           this.verificaDomiciliatiperIndirizzoResult = verificaDomiciliatiperIndirizzoResult;
    }


    /**
     * Gets the verificaDomiciliatiperIndirizzoResult value for this VerificaDomiciliatiperIndirizzoResponse.
     * 
     * @return verificaDomiciliatiperIndirizzoResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponseVerificaDomiciliatiperIndirizzoResult getVerificaDomiciliatiperIndirizzoResult() {
        return verificaDomiciliatiperIndirizzoResult;
    }


    /**
     * Sets the verificaDomiciliatiperIndirizzoResult value for this VerificaDomiciliatiperIndirizzoResponse.
     * 
     * @param verificaDomiciliatiperIndirizzoResult
     */
    public void setVerificaDomiciliatiperIndirizzoResult(it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponseVerificaDomiciliatiperIndirizzoResult verificaDomiciliatiperIndirizzoResult) {
        this.verificaDomiciliatiperIndirizzoResult = verificaDomiciliatiperIndirizzoResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaDomiciliatiperIndirizzoResponse)) return false;
        VerificaDomiciliatiperIndirizzoResponse other = (VerificaDomiciliatiperIndirizzoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaDomiciliatiperIndirizzoResult==null && other.getVerificaDomiciliatiperIndirizzoResult()==null) || 
             (this.verificaDomiciliatiperIndirizzoResult!=null &&
              this.verificaDomiciliatiperIndirizzoResult.equals(other.getVerificaDomiciliatiperIndirizzoResult())));
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
        if (getVerificaDomiciliatiperIndirizzoResult() != null) {
            _hashCode += getVerificaDomiciliatiperIndirizzoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaDomiciliatiperIndirizzoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaDomiciliatiperIndirizzoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomiciliatiperIndirizzoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDomiciliatiperIndirizzoResponse>VerificaDomiciliatiperIndirizzoResult"));
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

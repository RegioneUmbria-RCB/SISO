/**
 * VerificaDomXRangeCiviciResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaDomXRangeCiviciResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponseVerificaDomXRangeCiviciResult verificaDomXRangeCiviciResult;

    public VerificaDomXRangeCiviciResponse() {
    }

    public VerificaDomXRangeCiviciResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponseVerificaDomXRangeCiviciResult verificaDomXRangeCiviciResult) {
           this.verificaDomXRangeCiviciResult = verificaDomXRangeCiviciResult;
    }


    /**
     * Gets the verificaDomXRangeCiviciResult value for this VerificaDomXRangeCiviciResponse.
     * 
     * @return verificaDomXRangeCiviciResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponseVerificaDomXRangeCiviciResult getVerificaDomXRangeCiviciResult() {
        return verificaDomXRangeCiviciResult;
    }


    /**
     * Sets the verificaDomXRangeCiviciResult value for this VerificaDomXRangeCiviciResponse.
     * 
     * @param verificaDomXRangeCiviciResult
     */
    public void setVerificaDomXRangeCiviciResult(it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponseVerificaDomXRangeCiviciResult verificaDomXRangeCiviciResult) {
        this.verificaDomXRangeCiviciResult = verificaDomXRangeCiviciResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaDomXRangeCiviciResponse)) return false;
        VerificaDomXRangeCiviciResponse other = (VerificaDomXRangeCiviciResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaDomXRangeCiviciResult==null && other.getVerificaDomXRangeCiviciResult()==null) || 
             (this.verificaDomXRangeCiviciResult!=null &&
              this.verificaDomXRangeCiviciResult.equals(other.getVerificaDomXRangeCiviciResult())));
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
        if (getVerificaDomXRangeCiviciResult() != null) {
            _hashCode += getVerificaDomXRangeCiviciResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaDomXRangeCiviciResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomXRangeCiviciResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaDomXRangeCiviciResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaDomXRangeCiviciResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaDomXRangeCiviciResponse>VerificaDomXRangeCiviciResult"));
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

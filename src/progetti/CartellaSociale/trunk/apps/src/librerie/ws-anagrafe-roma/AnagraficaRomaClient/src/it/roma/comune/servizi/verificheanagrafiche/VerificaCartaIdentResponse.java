/**
 * VerificaCartaIdentResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaCartaIdentResponse  implements java.io.Serializable {
    private it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponseVerificaCartaIdentResult verificaCartaIdentResult;

    public VerificaCartaIdentResponse() {
    }

    public VerificaCartaIdentResponse(
           it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponseVerificaCartaIdentResult verificaCartaIdentResult) {
           this.verificaCartaIdentResult = verificaCartaIdentResult;
    }


    /**
     * Gets the verificaCartaIdentResult value for this VerificaCartaIdentResponse.
     * 
     * @return verificaCartaIdentResult
     */
    public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponseVerificaCartaIdentResult getVerificaCartaIdentResult() {
        return verificaCartaIdentResult;
    }


    /**
     * Sets the verificaCartaIdentResult value for this VerificaCartaIdentResponse.
     * 
     * @param verificaCartaIdentResult
     */
    public void setVerificaCartaIdentResult(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponseVerificaCartaIdentResult verificaCartaIdentResult) {
        this.verificaCartaIdentResult = verificaCartaIdentResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaCartaIdentResponse)) return false;
        VerificaCartaIdentResponse other = (VerificaCartaIdentResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.verificaCartaIdentResult==null && other.getVerificaCartaIdentResult()==null) || 
             (this.verificaCartaIdentResult!=null &&
              this.verificaCartaIdentResult.equals(other.getVerificaCartaIdentResult())));
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
        if (getVerificaCartaIdentResult() != null) {
            _hashCode += getVerificaCartaIdentResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaCartaIdentResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaCartaIdentResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("verificaCartaIdentResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "VerificaCartaIdentResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">>VerificaCartaIdentResponse>VerificaCartaIdentResult"));
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

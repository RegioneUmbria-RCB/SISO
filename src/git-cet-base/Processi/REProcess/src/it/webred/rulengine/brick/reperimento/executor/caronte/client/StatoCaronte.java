package it.webred.rulengine.brick.reperimento.executor.caronte.client;

import java.io.Serializable;

public class StatoCaronte implements Serializable {

	private java.lang.String errorMessage;

    private boolean finitoClient;

    private boolean finitoServer;

    private boolean inErrore;

    private java.lang.String processId;

	public StatoCaronte() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StatoCaronte(String errorMessage, boolean finitoClient,
			boolean finitoServer, boolean inErrore, String processId) {
		super();
		this.errorMessage = errorMessage;
		this.finitoClient = finitoClient;
		this.finitoServer = finitoServer;
		this.inErrore = inErrore;
		this.processId = processId;
	}

	public java.lang.String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(java.lang.String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isFinitoClient() {
		return finitoClient;
	}

	public void setFinitoClient(boolean finitoClient) {
		this.finitoClient = finitoClient;
	}

	public boolean isFinitoServer() {
		return finitoServer;
	}

	public void setFinitoServer(boolean finitoServer) {
		this.finitoServer = finitoServer;
	}

	public boolean isInErrore() {
		return inErrore;
	}

	public void setInErrore(boolean inErrore) {
		this.inErrore = inErrore;
	}

	public java.lang.String getProcessId() {
		return processId;
	}

	public void setProcessId(java.lang.String processId) {
		this.processId = processId;
	}
    
	
	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatoCaronte)) return false;
        StatoCaronte other = (StatoCaronte) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errorMessage==null && other.getErrorMessage()==null) || 
             (this.errorMessage!=null &&
              this.errorMessage.equals(other.getErrorMessage()))) &&
            this.finitoClient == other.isFinitoClient() &&
            this.finitoServer == other.isFinitoServer() &&
            this.inErrore == other.isInErrore() &&
            ((this.processId==null && other.getProcessId()==null) || 
             (this.processId!=null &&
              this.processId.equals(other.getProcessId())));
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
        if (getErrorMessage() != null) {
            _hashCode += getErrorMessage().hashCode();
        }
        _hashCode += (isFinitoClient() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isFinitoServer() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        _hashCode += (isInErrore() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getProcessId() != null) {
            _hashCode += getProcessId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatoCaronte.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://client.caronte.webred.it", "StatoCaronte"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://client.caronte.webred.it", "errorMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finitoClient");
        elemField.setXmlName(new javax.xml.namespace.QName("http://client.caronte.webred.it", "finitoClient"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finitoServer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://client.caronte.webred.it", "finitoServer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inErrore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://client.caronte.webred.it", "inErrore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("processId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://client.caronte.webred.it", "processId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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

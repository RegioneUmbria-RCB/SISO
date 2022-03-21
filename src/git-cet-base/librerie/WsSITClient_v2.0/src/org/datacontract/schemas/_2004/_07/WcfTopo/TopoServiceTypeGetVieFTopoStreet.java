/**
 * TopoServiceTypeGetVieFTopoStreet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.WcfTopo;

public class TopoServiceTypeGetVieFTopoStreet  implements java.io.Serializable {
    private java.lang.Integer streetCode;

    private java.lang.String topo1;

    private java.lang.String topo2;

    private java.lang.String topo3;

    private java.lang.String topo4;

    private java.lang.String topo5;

    private java.lang.String topoVia;

    private java.lang.String toponym;
    
    private java.lang.String stateCode;

    public TopoServiceTypeGetVieFTopoStreet() {
    }

    public TopoServiceTypeGetVieFTopoStreet(
           java.lang.Integer streetCode,
           java.lang.String topo1,
           java.lang.String topo2,
           java.lang.String topo3,
           java.lang.String topo4,
           java.lang.String topo5,
           java.lang.String topoVia,
           java.lang.String toponym,
           java.lang.String stateCode) {
           this.streetCode = streetCode;
           this.topo1 = topo1;
           this.topo2 = topo2;
           this.topo3 = topo3;
           this.topo4 = topo4;
           this.topo5 = topo5;
           this.topoVia = topoVia;
           this.toponym = toponym;
           this.stateCode = stateCode;
    }


    /**
     * Gets the streetCode value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return streetCode
     */
    public java.lang.Integer getStreetCode() {
        return streetCode;
    }


    /**
     * Sets the streetCode value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param streetCode
     */
    public void setStreetCode(java.lang.Integer streetCode) {
        this.streetCode = streetCode;
    }


    /**
     * Gets the topo1 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topo1
     */
    public java.lang.String getTopo1() {
        return topo1;
    }


    /**
     * Sets the topo1 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topo1
     */
    public void setTopo1(java.lang.String topo1) {
        this.topo1 = topo1;
    }


    /**
     * Gets the topo2 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topo2
     */
    public java.lang.String getTopo2() {
        return topo2;
    }


    /**
     * Sets the topo2 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topo2
     */
    public void setTopo2(java.lang.String topo2) {
        this.topo2 = topo2;
    }


    /**
     * Gets the topo3 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topo3
     */
    public java.lang.String getTopo3() {
        return topo3;
    }


    /**
     * Sets the topo3 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topo3
     */
    public void setTopo3(java.lang.String topo3) {
        this.topo3 = topo3;
    }


    /**
     * Gets the topo4 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topo4
     */
    public java.lang.String getTopo4() {
        return topo4;
    }


    /**
     * Sets the topo4 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topo4
     */
    public void setTopo4(java.lang.String topo4) {
        this.topo4 = topo4;
    }


    /**
     * Gets the topo5 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topo5
     */
    public java.lang.String getTopo5() {
        return topo5;
    }


    /**
     * Sets the topo5 value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topo5
     */
    public void setTopo5(java.lang.String topo5) {
        this.topo5 = topo5;
    }


    /**
     * Gets the topoVia value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return topoVia
     */
    public java.lang.String getTopoVia() {
        return topoVia;
    }


    /**
     * Sets the topoVia value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param topoVia
     */
    public void setTopoVia(java.lang.String topoVia) {
        this.topoVia = topoVia;
    }


    /**
     * Gets the toponym value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @return toponym
     */
    public java.lang.String getToponym() {
        return toponym;
    }


    /**
     * Sets the toponym value for this TopoServiceTypeGetVieFTopoStreet.
     * 
     * @param toponym
     */
    public void setToponym(java.lang.String toponym) {
        this.toponym = toponym;
    }

    public java.lang.String getStateCode() {
		return stateCode;
	}

	public void setStateCode(java.lang.String stateCode) {
		this.stateCode = stateCode;
	}

	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TopoServiceTypeGetVieFTopoStreet)) return false;
        TopoServiceTypeGetVieFTopoStreet other = (TopoServiceTypeGetVieFTopoStreet) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.streetCode==null && other.getStreetCode()==null) || 
             (this.streetCode!=null &&
              this.streetCode.equals(other.getStreetCode()))) &&
            ((this.topo1==null && other.getTopo1()==null) || 
             (this.topo1!=null &&
              this.topo1.equals(other.getTopo1()))) &&
            ((this.topo2==null && other.getTopo2()==null) || 
             (this.topo2!=null &&
              this.topo2.equals(other.getTopo2()))) &&
            ((this.topo3==null && other.getTopo3()==null) || 
             (this.topo3!=null &&
              this.topo3.equals(other.getTopo3()))) &&
            ((this.topo4==null && other.getTopo4()==null) || 
             (this.topo4!=null &&
              this.topo4.equals(other.getTopo4()))) &&
            ((this.topo5==null && other.getTopo5()==null) || 
             (this.topo5!=null &&
              this.topo5.equals(other.getTopo5()))) &&
            ((this.topoVia==null && other.getTopoVia()==null) || 
             (this.topoVia!=null &&
              this.topoVia.equals(other.getTopoVia()))) &&
            ((this.toponym==null && other.getToponym()==null) || 
             (this.toponym!=null &&
              this.toponym.equals(other.getToponym()))) &&
            ((this.stateCode==null && other.getStateCode()==null) || 
              (this.stateCode!=null &&
               this.stateCode.equals(other.getStateCode())))   
              ;
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
        if (getStreetCode() != null) {
            _hashCode += getStreetCode().hashCode();
        }
        if (getTopo1() != null) {
            _hashCode += getTopo1().hashCode();
        }
        if (getTopo2() != null) {
            _hashCode += getTopo2().hashCode();
        }
        if (getTopo3() != null) {
            _hashCode += getTopo3().hashCode();
        }
        if (getTopo4() != null) {
            _hashCode += getTopo4().hashCode();
        }
        if (getTopo5() != null) {
            _hashCode += getTopo5().hashCode();
        }
        if (getTopoVia() != null) {
            _hashCode += getTopoVia().hashCode();
        }
        if (getToponym() != null) {
            _hashCode += getToponym().hashCode();
        }
        if (getStateCode() != null) {
            _hashCode += getStateCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TopoServiceTypeGetVieFTopoStreet.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoService.typeGetVieFTopoStreet"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "StreetCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topo1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Topo1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topo2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Topo2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topo3");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Topo3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topo4");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Topo4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topo5");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Topo5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("topoVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "TopoVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toponym");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "Toponym"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stateCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/WcfTopo", "StateCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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

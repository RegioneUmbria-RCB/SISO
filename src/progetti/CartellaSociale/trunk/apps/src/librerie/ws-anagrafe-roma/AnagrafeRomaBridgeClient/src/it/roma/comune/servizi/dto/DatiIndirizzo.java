/**
 * DatiIndirizzo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class DatiIndirizzo  implements java.io.Serializable {
    private java.lang.String cap;

    private java.lang.String indirizzoBreve;

    private java.lang.String interno;

    private java.lang.String municipio;

    private java.lang.String numero;

    private java.lang.String piano;

    private java.lang.String scala;

    private java.lang.String toponimo;

    public DatiIndirizzo() {
    }

    public DatiIndirizzo(
           java.lang.String cap,
           java.lang.String indirizzoBreve,
           java.lang.String interno,
           java.lang.String municipio,
           java.lang.String numero,
           java.lang.String piano,
           java.lang.String scala,
           java.lang.String toponimo) {
           this.cap = cap;
           this.indirizzoBreve = indirizzoBreve;
           this.interno = interno;
           this.municipio = municipio;
           this.numero = numero;
           this.piano = piano;
           this.scala = scala;
           this.toponimo = toponimo;
    }


    /**
     * Gets the cap value for this DatiIndirizzo.
     * 
     * @return cap
     */
    public java.lang.String getCap() {
        return cap;
    }


    /**
     * Sets the cap value for this DatiIndirizzo.
     * 
     * @param cap
     */
    public void setCap(java.lang.String cap) {
        this.cap = cap;
    }


    /**
     * Gets the indirizzoBreve value for this DatiIndirizzo.
     * 
     * @return indirizzoBreve
     */
    public java.lang.String getIndirizzoBreve() {
        return indirizzoBreve;
    }


    /**
     * Sets the indirizzoBreve value for this DatiIndirizzo.
     * 
     * @param indirizzoBreve
     */
    public void setIndirizzoBreve(java.lang.String indirizzoBreve) {
        this.indirizzoBreve = indirizzoBreve;
    }

    public String getDescrizioneCivicoAltro(){

        String altro = "";

        altro+= this.getScala()!=null && !this.getScala().isEmpty()? "Scala "+this.getScala() : "";

        altro+= this.piano!=null && !piano.isEmpty()? "Piano "+piano : "";

        altro+= this.interno!=null && !interno.isEmpty()? "Int. "+interno : "";

        return altro;

  }
    /**
     * Gets the interno value for this DatiIndirizzo.
     * 
     * @return interno
     */
    public java.lang.String getInterno() {
        return interno;
    }


    /**
     * Sets the interno value for this DatiIndirizzo.
     * 
     * @param interno
     */
    public void setInterno(java.lang.String interno) {
        this.interno = interno;
    }


    /**
     * Gets the municipio value for this DatiIndirizzo.
     * 
     * @return municipio
     */
    public java.lang.String getMunicipio() {
        return municipio;
    }


    /**
     * Sets the municipio value for this DatiIndirizzo.
     * 
     * @param municipio
     */
    public void setMunicipio(java.lang.String municipio) {
        this.municipio = municipio;
    }


    /**
     * Gets the numero value for this DatiIndirizzo.
     * 
     * @return numero
     */
    public java.lang.String getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this DatiIndirizzo.
     * 
     * @param numero
     */
    public void setNumero(java.lang.String numero) {
        this.numero = numero;
    }


    /**
     * Gets the piano value for this DatiIndirizzo.
     * 
     * @return piano
     */
    public java.lang.String getPiano() {
        return piano;
    }


    /**
     * Sets the piano value for this DatiIndirizzo.
     * 
     * @param piano
     */
    public void setPiano(java.lang.String piano) {
        this.piano = piano;
    }


    /**
     * Gets the scala value for this DatiIndirizzo.
     * 
     * @return scala
     */
    public java.lang.String getScala() {
        return scala;
    }


    /**
     * Sets the scala value for this DatiIndirizzo.
     * 
     * @param scala
     */
    public void setScala(java.lang.String scala) {
        this.scala = scala;
    }


    /**
     * Gets the toponimo value for this DatiIndirizzo.
     * 
     * @return toponimo
     */
    public java.lang.String getToponimo() {
        return toponimo;
    }


    /**
     * Sets the toponimo value for this DatiIndirizzo.
     * 
     * @param toponimo
     */
    public void setToponimo(java.lang.String toponimo) {
        this.toponimo = toponimo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiIndirizzo)) return false;
        DatiIndirizzo other = (DatiIndirizzo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cap==null && other.getCap()==null) || 
             (this.cap!=null &&
              this.cap.equals(other.getCap()))) &&
            ((this.indirizzoBreve==null && other.getIndirizzoBreve()==null) || 
             (this.indirizzoBreve!=null &&
              this.indirizzoBreve.equals(other.getIndirizzoBreve()))) &&
            ((this.interno==null && other.getInterno()==null) || 
             (this.interno!=null &&
              this.interno.equals(other.getInterno()))) &&
            ((this.municipio==null && other.getMunicipio()==null) || 
             (this.municipio!=null &&
              this.municipio.equals(other.getMunicipio()))) &&
            ((this.numero==null && other.getNumero()==null) || 
             (this.numero!=null &&
              this.numero.equals(other.getNumero()))) &&
            ((this.piano==null && other.getPiano()==null) || 
             (this.piano!=null &&
              this.piano.equals(other.getPiano()))) &&
            ((this.scala==null && other.getScala()==null) || 
             (this.scala!=null &&
              this.scala.equals(other.getScala()))) &&
            ((this.toponimo==null && other.getToponimo()==null) || 
             (this.toponimo!=null &&
              this.toponimo.equals(other.getToponimo())));
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
        if (getCap() != null) {
            _hashCode += getCap().hashCode();
        }
        if (getIndirizzoBreve() != null) {
            _hashCode += getIndirizzoBreve().hashCode();
        }
        if (getInterno() != null) {
            _hashCode += getInterno().hashCode();
        }
        if (getMunicipio() != null) {
            _hashCode += getMunicipio().hashCode();
        }
        if (getNumero() != null) {
            _hashCode += getNumero().hashCode();
        }
        if (getPiano() != null) {
            _hashCode += getPiano().hashCode();
        }
        if (getScala() != null) {
            _hashCode += getScala().hashCode();
        }
        if (getToponimo() != null) {
            _hashCode += getToponimo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiIndirizzo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "DatiIndirizzo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cap");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "cap"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indirizzoBreve");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "indirizzoBreve"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "interno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("municipio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "municipio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("piano");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "piano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scala");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "scala"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toponimo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "toponimo"));
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

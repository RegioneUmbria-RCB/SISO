/**
 * VerificaDomiciliatiperIndirizzo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaDomiciliatiperIndirizzo  implements java.io.Serializable {
    private org.apache.axis.types.UnsignedByte tipoInterr;

    private int codiceVia;

    private java.lang.String denominazioneVia;

    private short numero;

    private java.lang.String lettera;

    private java.lang.String lotto;

    private java.lang.String palazzina;

    private java.lang.String km;

    private java.lang.String scala;

    private java.lang.String piano;

    private java.lang.String interno;

    private org.apache.axis.types.UnsignedByte serviceLevel;

    public VerificaDomiciliatiperIndirizzo() {
    }

    public VerificaDomiciliatiperIndirizzo(
           org.apache.axis.types.UnsignedByte tipoInterr,
           int codiceVia,
           java.lang.String denominazioneVia,
           short numero,
           java.lang.String lettera,
           java.lang.String lotto,
           java.lang.String palazzina,
           java.lang.String km,
           java.lang.String scala,
           java.lang.String piano,
           java.lang.String interno,
           org.apache.axis.types.UnsignedByte serviceLevel) {
           this.tipoInterr = tipoInterr;
           this.codiceVia = codiceVia;
           this.denominazioneVia = denominazioneVia;
           this.numero = numero;
           this.lettera = lettera;
           this.lotto = lotto;
           this.palazzina = palazzina;
           this.km = km;
           this.scala = scala;
           this.piano = piano;
           this.interno = interno;
           this.serviceLevel = serviceLevel;
    }


    /**
     * Gets the tipoInterr value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return tipoInterr
     */
    public org.apache.axis.types.UnsignedByte getTipoInterr() {
        return tipoInterr;
    }


    /**
     * Sets the tipoInterr value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param tipoInterr
     */
    public void setTipoInterr(org.apache.axis.types.UnsignedByte tipoInterr) {
        this.tipoInterr = tipoInterr;
    }


    /**
     * Gets the codiceVia value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return codiceVia
     */
    public int getCodiceVia() {
        return codiceVia;
    }


    /**
     * Sets the codiceVia value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param codiceVia
     */
    public void setCodiceVia(int codiceVia) {
        this.codiceVia = codiceVia;
    }


    /**
     * Gets the denominazioneVia value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return denominazioneVia
     */
    public java.lang.String getDenominazioneVia() {
        return denominazioneVia;
    }


    /**
     * Sets the denominazioneVia value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param denominazioneVia
     */
    public void setDenominazioneVia(java.lang.String denominazioneVia) {
        this.denominazioneVia = denominazioneVia;
    }


    /**
     * Gets the numero value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return numero
     */
    public short getNumero() {
        return numero;
    }


    /**
     * Sets the numero value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param numero
     */
    public void setNumero(short numero) {
        this.numero = numero;
    }


    /**
     * Gets the lettera value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return lettera
     */
    public java.lang.String getLettera() {
        return lettera;
    }


    /**
     * Sets the lettera value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param lettera
     */
    public void setLettera(java.lang.String lettera) {
        this.lettera = lettera;
    }


    /**
     * Gets the lotto value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return lotto
     */
    public java.lang.String getLotto() {
        return lotto;
    }


    /**
     * Sets the lotto value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param lotto
     */
    public void setLotto(java.lang.String lotto) {
        this.lotto = lotto;
    }


    /**
     * Gets the palazzina value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return palazzina
     */
    public java.lang.String getPalazzina() {
        return palazzina;
    }


    /**
     * Sets the palazzina value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param palazzina
     */
    public void setPalazzina(java.lang.String palazzina) {
        this.palazzina = palazzina;
    }


    /**
     * Gets the km value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return km
     */
    public java.lang.String getKm() {
        return km;
    }


    /**
     * Sets the km value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param km
     */
    public void setKm(java.lang.String km) {
        this.km = km;
    }


    /**
     * Gets the scala value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return scala
     */
    public java.lang.String getScala() {
        return scala;
    }


    /**
     * Sets the scala value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param scala
     */
    public void setScala(java.lang.String scala) {
        this.scala = scala;
    }


    /**
     * Gets the piano value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return piano
     */
    public java.lang.String getPiano() {
        return piano;
    }


    /**
     * Sets the piano value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param piano
     */
    public void setPiano(java.lang.String piano) {
        this.piano = piano;
    }


    /**
     * Gets the interno value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return interno
     */
    public java.lang.String getInterno() {
        return interno;
    }


    /**
     * Sets the interno value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param interno
     */
    public void setInterno(java.lang.String interno) {
        this.interno = interno;
    }


    /**
     * Gets the serviceLevel value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @return serviceLevel
     */
    public org.apache.axis.types.UnsignedByte getServiceLevel() {
        return serviceLevel;
    }


    /**
     * Sets the serviceLevel value for this VerificaDomiciliatiperIndirizzo.
     * 
     * @param serviceLevel
     */
    public void setServiceLevel(org.apache.axis.types.UnsignedByte serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaDomiciliatiperIndirizzo)) return false;
        VerificaDomiciliatiperIndirizzo other = (VerificaDomiciliatiperIndirizzo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tipoInterr==null && other.getTipoInterr()==null) || 
             (this.tipoInterr!=null &&
              this.tipoInterr.equals(other.getTipoInterr()))) &&
            this.codiceVia == other.getCodiceVia() &&
            ((this.denominazioneVia==null && other.getDenominazioneVia()==null) || 
             (this.denominazioneVia!=null &&
              this.denominazioneVia.equals(other.getDenominazioneVia()))) &&
            this.numero == other.getNumero() &&
            ((this.lettera==null && other.getLettera()==null) || 
             (this.lettera!=null &&
              this.lettera.equals(other.getLettera()))) &&
            ((this.lotto==null && other.getLotto()==null) || 
             (this.lotto!=null &&
              this.lotto.equals(other.getLotto()))) &&
            ((this.palazzina==null && other.getPalazzina()==null) || 
             (this.palazzina!=null &&
              this.palazzina.equals(other.getPalazzina()))) &&
            ((this.km==null && other.getKm()==null) || 
             (this.km!=null &&
              this.km.equals(other.getKm()))) &&
            ((this.scala==null && other.getScala()==null) || 
             (this.scala!=null &&
              this.scala.equals(other.getScala()))) &&
            ((this.piano==null && other.getPiano()==null) || 
             (this.piano!=null &&
              this.piano.equals(other.getPiano()))) &&
            ((this.interno==null && other.getInterno()==null) || 
             (this.interno!=null &&
              this.interno.equals(other.getInterno()))) &&
            ((this.serviceLevel==null && other.getServiceLevel()==null) || 
             (this.serviceLevel!=null &&
              this.serviceLevel.equals(other.getServiceLevel())));
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
        if (getTipoInterr() != null) {
            _hashCode += getTipoInterr().hashCode();
        }
        _hashCode += getCodiceVia();
        if (getDenominazioneVia() != null) {
            _hashCode += getDenominazioneVia().hashCode();
        }
        _hashCode += getNumero();
        if (getLettera() != null) {
            _hashCode += getLettera().hashCode();
        }
        if (getLotto() != null) {
            _hashCode += getLotto().hashCode();
        }
        if (getPalazzina() != null) {
            _hashCode += getPalazzina().hashCode();
        }
        if (getKm() != null) {
            _hashCode += getKm().hashCode();
        }
        if (getScala() != null) {
            _hashCode += getScala().hashCode();
        }
        if (getPiano() != null) {
            _hashCode += getPiano().hashCode();
        }
        if (getInterno() != null) {
            _hashCode += getInterno().hashCode();
        }
        if (getServiceLevel() != null) {
            _hashCode += getServiceLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaDomiciliatiperIndirizzo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaDomiciliatiperIndirizzo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoInterr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "TipoInterr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "CodiceVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("denominazioneVia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "DenominazioneVia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numero");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Numero"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lettera");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Lettera"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lotto");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Lotto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("palazzina");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Palazzina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("km");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Km"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("scala");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Scala"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("piano");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Piano"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Interno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceLevel");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "ServiceLevel"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
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

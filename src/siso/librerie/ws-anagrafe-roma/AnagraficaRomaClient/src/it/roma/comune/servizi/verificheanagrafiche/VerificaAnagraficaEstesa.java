/**
 * VerificaAnagraficaEstesa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaAnagraficaEstesa  implements java.io.Serializable {
    private java.lang.String sesso;

    private java.lang.String cognome;

    private java.lang.String nome;

    private short annoIniziale;

    private short annoFinale;

    private java.lang.String patern;

    private java.lang.String matern;

    private java.lang.String domicilio;

    private java.lang.String comuneNascita;

    private org.apache.axis.types.UnsignedByte serviceLevel;

    public VerificaAnagraficaEstesa() {
    }

    public VerificaAnagraficaEstesa(
           java.lang.String sesso,
           java.lang.String cognome,
           java.lang.String nome,
           short annoIniziale,
           short annoFinale,
           java.lang.String patern,
           java.lang.String matern,
           java.lang.String domicilio,
           java.lang.String comuneNascita,
           org.apache.axis.types.UnsignedByte serviceLevel) {
           this.sesso = sesso;
           this.cognome = cognome;
           this.nome = nome;
           this.annoIniziale = annoIniziale;
           this.annoFinale = annoFinale;
           this.patern = patern;
           this.matern = matern;
           this.domicilio = domicilio;
           this.comuneNascita = comuneNascita;
           this.serviceLevel = serviceLevel;
    }


    /**
     * Gets the sesso value for this VerificaAnagraficaEstesa.
     * 
     * @return sesso
     */
    public java.lang.String getSesso() {
        return sesso;
    }


    /**
     * Sets the sesso value for this VerificaAnagraficaEstesa.
     * 
     * @param sesso
     */
    public void setSesso(java.lang.String sesso) {
        this.sesso = sesso;
    }


    /**
     * Gets the cognome value for this VerificaAnagraficaEstesa.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this VerificaAnagraficaEstesa.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the nome value for this VerificaAnagraficaEstesa.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this VerificaAnagraficaEstesa.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the annoIniziale value for this VerificaAnagraficaEstesa.
     * 
     * @return annoIniziale
     */
    public short getAnnoIniziale() {
        return annoIniziale;
    }


    /**
     * Sets the annoIniziale value for this VerificaAnagraficaEstesa.
     * 
     * @param annoIniziale
     */
    public void setAnnoIniziale(short annoIniziale) {
        this.annoIniziale = annoIniziale;
    }


    /**
     * Gets the annoFinale value for this VerificaAnagraficaEstesa.
     * 
     * @return annoFinale
     */
    public short getAnnoFinale() {
        return annoFinale;
    }


    /**
     * Sets the annoFinale value for this VerificaAnagraficaEstesa.
     * 
     * @param annoFinale
     */
    public void setAnnoFinale(short annoFinale) {
        this.annoFinale = annoFinale;
    }


    /**
     * Gets the patern value for this VerificaAnagraficaEstesa.
     * 
     * @return patern
     */
    public java.lang.String getPatern() {
        return patern;
    }


    /**
     * Sets the patern value for this VerificaAnagraficaEstesa.
     * 
     * @param patern
     */
    public void setPatern(java.lang.String patern) {
        this.patern = patern;
    }


    /**
     * Gets the matern value for this VerificaAnagraficaEstesa.
     * 
     * @return matern
     */
    public java.lang.String getMatern() {
        return matern;
    }


    /**
     * Sets the matern value for this VerificaAnagraficaEstesa.
     * 
     * @param matern
     */
    public void setMatern(java.lang.String matern) {
        this.matern = matern;
    }


    /**
     * Gets the domicilio value for this VerificaAnagraficaEstesa.
     * 
     * @return domicilio
     */
    public java.lang.String getDomicilio() {
        return domicilio;
    }


    /**
     * Sets the domicilio value for this VerificaAnagraficaEstesa.
     * 
     * @param domicilio
     */
    public void setDomicilio(java.lang.String domicilio) {
        this.domicilio = domicilio;
    }


    /**
     * Gets the comuneNascita value for this VerificaAnagraficaEstesa.
     * 
     * @return comuneNascita
     */
    public java.lang.String getComuneNascita() {
        return comuneNascita;
    }


    /**
     * Sets the comuneNascita value for this VerificaAnagraficaEstesa.
     * 
     * @param comuneNascita
     */
    public void setComuneNascita(java.lang.String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }


    /**
     * Gets the serviceLevel value for this VerificaAnagraficaEstesa.
     * 
     * @return serviceLevel
     */
    public org.apache.axis.types.UnsignedByte getServiceLevel() {
        return serviceLevel;
    }


    /**
     * Sets the serviceLevel value for this VerificaAnagraficaEstesa.
     * 
     * @param serviceLevel
     */
    public void setServiceLevel(org.apache.axis.types.UnsignedByte serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaAnagraficaEstesa)) return false;
        VerificaAnagraficaEstesa other = (VerificaAnagraficaEstesa) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sesso==null && other.getSesso()==null) || 
             (this.sesso!=null &&
              this.sesso.equals(other.getSesso()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            this.annoIniziale == other.getAnnoIniziale() &&
            this.annoFinale == other.getAnnoFinale() &&
            ((this.patern==null && other.getPatern()==null) || 
             (this.patern!=null &&
              this.patern.equals(other.getPatern()))) &&
            ((this.matern==null && other.getMatern()==null) || 
             (this.matern!=null &&
              this.matern.equals(other.getMatern()))) &&
            ((this.domicilio==null && other.getDomicilio()==null) || 
             (this.domicilio!=null &&
              this.domicilio.equals(other.getDomicilio()))) &&
            ((this.comuneNascita==null && other.getComuneNascita()==null) || 
             (this.comuneNascita!=null &&
              this.comuneNascita.equals(other.getComuneNascita()))) &&
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
        if (getSesso() != null) {
            _hashCode += getSesso().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        _hashCode += getAnnoIniziale();
        _hashCode += getAnnoFinale();
        if (getPatern() != null) {
            _hashCode += getPatern().hashCode();
        }
        if (getMatern() != null) {
            _hashCode += getMatern().hashCode();
        }
        if (getDomicilio() != null) {
            _hashCode += getDomicilio().hashCode();
        }
        if (getComuneNascita() != null) {
            _hashCode += getComuneNascita().hashCode();
        }
        if (getServiceLevel() != null) {
            _hashCode += getServiceLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaAnagraficaEstesa.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaAnagraficaEstesa"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sesso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Sesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoIniziale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "AnnoIniziale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoFinale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "AnnoFinale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patern");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Patern"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matern");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Matern"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("domicilio");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "Domicilio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuneNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "ComuneNascita"));
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

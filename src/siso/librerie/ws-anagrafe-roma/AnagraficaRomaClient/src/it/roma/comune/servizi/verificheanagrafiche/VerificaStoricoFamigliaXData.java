/**
 * VerificaStoricoFamigliaXData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class VerificaStoricoFamigliaXData  implements java.io.Serializable {
    private org.apache.axis.types.UnsignedByte tipoInterr;

    private int codiceIndividuale;

    private java.lang.String codiceFiscale;

    private java.lang.String sesso;

    private java.lang.String cognome;

    private java.lang.String nome;

    private org.apache.axis.types.UnsignedByte giornoNascita;

    private org.apache.axis.types.UnsignedByte meseNascita;

    private short annoNascita;

    private org.apache.axis.types.UnsignedByte giornoRif;

    private org.apache.axis.types.UnsignedByte meseRif;

    private short annoRif;

    private org.apache.axis.types.UnsignedByte serviceLevel;

    public VerificaStoricoFamigliaXData() {
    }

    public VerificaStoricoFamigliaXData(
           org.apache.axis.types.UnsignedByte tipoInterr,
           int codiceIndividuale,
           java.lang.String codiceFiscale,
           java.lang.String sesso,
           java.lang.String cognome,
           java.lang.String nome,
           org.apache.axis.types.UnsignedByte giornoNascita,
           org.apache.axis.types.UnsignedByte meseNascita,
           short annoNascita,
           org.apache.axis.types.UnsignedByte giornoRif,
           org.apache.axis.types.UnsignedByte meseRif,
           short annoRif,
           org.apache.axis.types.UnsignedByte serviceLevel) {
           this.tipoInterr = tipoInterr;
           this.codiceIndividuale = codiceIndividuale;
           this.codiceFiscale = codiceFiscale;
           this.sesso = sesso;
           this.cognome = cognome;
           this.nome = nome;
           this.giornoNascita = giornoNascita;
           this.meseNascita = meseNascita;
           this.annoNascita = annoNascita;
           this.giornoRif = giornoRif;
           this.meseRif = meseRif;
           this.annoRif = annoRif;
           this.serviceLevel = serviceLevel;
    }


    /**
     * Gets the tipoInterr value for this VerificaStoricoFamigliaXData.
     * 
     * @return tipoInterr
     */
    public org.apache.axis.types.UnsignedByte getTipoInterr() {
        return tipoInterr;
    }


    /**
     * Sets the tipoInterr value for this VerificaStoricoFamigliaXData.
     * 
     * @param tipoInterr
     */
    public void setTipoInterr(org.apache.axis.types.UnsignedByte tipoInterr) {
        this.tipoInterr = tipoInterr;
    }


    /**
     * Gets the codiceIndividuale value for this VerificaStoricoFamigliaXData.
     * 
     * @return codiceIndividuale
     */
    public int getCodiceIndividuale() {
        return codiceIndividuale;
    }


    /**
     * Sets the codiceIndividuale value for this VerificaStoricoFamigliaXData.
     * 
     * @param codiceIndividuale
     */
    public void setCodiceIndividuale(int codiceIndividuale) {
        this.codiceIndividuale = codiceIndividuale;
    }


    /**
     * Gets the codiceFiscale value for this VerificaStoricoFamigliaXData.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this VerificaStoricoFamigliaXData.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the sesso value for this VerificaStoricoFamigliaXData.
     * 
     * @return sesso
     */
    public java.lang.String getSesso() {
        return sesso;
    }


    /**
     * Sets the sesso value for this VerificaStoricoFamigliaXData.
     * 
     * @param sesso
     */
    public void setSesso(java.lang.String sesso) {
        this.sesso = sesso;
    }


    /**
     * Gets the cognome value for this VerificaStoricoFamigliaXData.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this VerificaStoricoFamigliaXData.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the nome value for this VerificaStoricoFamigliaXData.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this VerificaStoricoFamigliaXData.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the giornoNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @return giornoNascita
     */
    public org.apache.axis.types.UnsignedByte getGiornoNascita() {
        return giornoNascita;
    }


    /**
     * Sets the giornoNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @param giornoNascita
     */
    public void setGiornoNascita(org.apache.axis.types.UnsignedByte giornoNascita) {
        this.giornoNascita = giornoNascita;
    }


    /**
     * Gets the meseNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @return meseNascita
     */
    public org.apache.axis.types.UnsignedByte getMeseNascita() {
        return meseNascita;
    }


    /**
     * Sets the meseNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @param meseNascita
     */
    public void setMeseNascita(org.apache.axis.types.UnsignedByte meseNascita) {
        this.meseNascita = meseNascita;
    }


    /**
     * Gets the annoNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @return annoNascita
     */
    public short getAnnoNascita() {
        return annoNascita;
    }


    /**
     * Sets the annoNascita value for this VerificaStoricoFamigliaXData.
     * 
     * @param annoNascita
     */
    public void setAnnoNascita(short annoNascita) {
        this.annoNascita = annoNascita;
    }


    /**
     * Gets the giornoRif value for this VerificaStoricoFamigliaXData.
     * 
     * @return giornoRif
     */
    public org.apache.axis.types.UnsignedByte getGiornoRif() {
        return giornoRif;
    }


    /**
     * Sets the giornoRif value for this VerificaStoricoFamigliaXData.
     * 
     * @param giornoRif
     */
    public void setGiornoRif(org.apache.axis.types.UnsignedByte giornoRif) {
        this.giornoRif = giornoRif;
    }


    /**
     * Gets the meseRif value for this VerificaStoricoFamigliaXData.
     * 
     * @return meseRif
     */
    public org.apache.axis.types.UnsignedByte getMeseRif() {
        return meseRif;
    }


    /**
     * Sets the meseRif value for this VerificaStoricoFamigliaXData.
     * 
     * @param meseRif
     */
    public void setMeseRif(org.apache.axis.types.UnsignedByte meseRif) {
        this.meseRif = meseRif;
    }


    /**
     * Gets the annoRif value for this VerificaStoricoFamigliaXData.
     * 
     * @return annoRif
     */
    public short getAnnoRif() {
        return annoRif;
    }


    /**
     * Sets the annoRif value for this VerificaStoricoFamigliaXData.
     * 
     * @param annoRif
     */
    public void setAnnoRif(short annoRif) {
        this.annoRif = annoRif;
    }


    /**
     * Gets the serviceLevel value for this VerificaStoricoFamigliaXData.
     * 
     * @return serviceLevel
     */
    public org.apache.axis.types.UnsignedByte getServiceLevel() {
        return serviceLevel;
    }


    /**
     * Sets the serviceLevel value for this VerificaStoricoFamigliaXData.
     * 
     * @param serviceLevel
     */
    public void setServiceLevel(org.apache.axis.types.UnsignedByte serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VerificaStoricoFamigliaXData)) return false;
        VerificaStoricoFamigliaXData other = (VerificaStoricoFamigliaXData) obj;
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
            this.codiceIndividuale == other.getCodiceIndividuale() &&
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.sesso==null && other.getSesso()==null) || 
             (this.sesso!=null &&
              this.sesso.equals(other.getSesso()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.giornoNascita==null && other.getGiornoNascita()==null) || 
             (this.giornoNascita!=null &&
              this.giornoNascita.equals(other.getGiornoNascita()))) &&
            ((this.meseNascita==null && other.getMeseNascita()==null) || 
             (this.meseNascita!=null &&
              this.meseNascita.equals(other.getMeseNascita()))) &&
            this.annoNascita == other.getAnnoNascita() &&
            ((this.giornoRif==null && other.getGiornoRif()==null) || 
             (this.giornoRif!=null &&
              this.giornoRif.equals(other.getGiornoRif()))) &&
            ((this.meseRif==null && other.getMeseRif()==null) || 
             (this.meseRif!=null &&
              this.meseRif.equals(other.getMeseRif()))) &&
            this.annoRif == other.getAnnoRif() &&
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
        _hashCode += getCodiceIndividuale();
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getSesso() != null) {
            _hashCode += getSesso().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getGiornoNascita() != null) {
            _hashCode += getGiornoNascita().hashCode();
        }
        if (getMeseNascita() != null) {
            _hashCode += getMeseNascita().hashCode();
        }
        _hashCode += getAnnoNascita();
        if (getGiornoRif() != null) {
            _hashCode += getGiornoRif().hashCode();
        }
        if (getMeseRif() != null) {
            _hashCode += getMeseRif().hashCode();
        }
        _hashCode += getAnnoRif();
        if (getServiceLevel() != null) {
            _hashCode += getServiceLevel().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VerificaStoricoFamigliaXData.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", ">VerificaStoricoFamigliaXData"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoInterr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "TipoInterr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIndividuale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "CodiceIndividuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "CodiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setFieldName("giornoNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "GiornoNascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("meseNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "MeseNascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoNascita");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "annoNascita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giornoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "GiornoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("meseRif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "MeseRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "unsignedByte"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("annoRif");
        elemField.setXmlName(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "AnnoRif"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "short"));
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

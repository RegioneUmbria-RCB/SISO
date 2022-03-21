/**
 * PersonaCompleta.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto;

public class PersonaCompleta  implements java.io.Serializable {
    private java.lang.String codiceFiscale;

    private java.lang.String codiceStatoISTAT;

    private java.lang.String cognome;

    private java.lang.String descrizioneCittadinanza;

    private java.lang.String nome;

    private java.lang.String sesso;

    private java.lang.String statoCivile;

    public PersonaCompleta() {
    }

    public PersonaCompleta(
           java.lang.String codiceFiscale,
           java.lang.String codiceStatoISTAT,
           java.lang.String cognome,
           java.lang.String descrizioneCittadinanza,
           java.lang.String nome,
           java.lang.String sesso,
           java.lang.String statoCivile) {
           this.codiceFiscale = codiceFiscale;
           this.codiceStatoISTAT = codiceStatoISTAT;
           this.cognome = cognome;
           this.descrizioneCittadinanza = descrizioneCittadinanza;
           this.nome = nome;
           this.sesso = sesso;
           this.statoCivile = statoCivile;
    }


    /**
     * Gets the codiceFiscale value for this PersonaCompleta.
     * 
     * @return codiceFiscale
     */
    public java.lang.String getCodiceFiscale() {
        return codiceFiscale;
    }


    /**
     * Sets the codiceFiscale value for this PersonaCompleta.
     * 
     * @param codiceFiscale
     */
    public void setCodiceFiscale(java.lang.String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Gets the codiceStatoISTAT value for this PersonaCompleta.
     * 
     * @return codiceStatoISTAT
     */
    public java.lang.String getCodiceStatoISTAT() {
        return codiceStatoISTAT;
    }


    /**
     * Sets the codiceStatoISTAT value for this PersonaCompleta.
     * 
     * @param codiceStatoISTAT
     */
    public void setCodiceStatoISTAT(java.lang.String codiceStatoISTAT) {
        this.codiceStatoISTAT = codiceStatoISTAT;
    }


    /**
     * Gets the cognome value for this PersonaCompleta.
     * 
     * @return cognome
     */
    public java.lang.String getCognome() {
        return cognome;
    }


    /**
     * Sets the cognome value for this PersonaCompleta.
     * 
     * @param cognome
     */
    public void setCognome(java.lang.String cognome) {
        this.cognome = cognome;
    }


    /**
     * Gets the descrizioneCittadinanza value for this PersonaCompleta.
     * 
     * @return descrizioneCittadinanza
     */
    public java.lang.String getDescrizioneCittadinanza() {
        return descrizioneCittadinanza;
    }


    /**
     * Sets the descrizioneCittadinanza value for this PersonaCompleta.
     * 
     * @param descrizioneCittadinanza
     */
    public void setDescrizioneCittadinanza(java.lang.String descrizioneCittadinanza) {
        this.descrizioneCittadinanza = descrizioneCittadinanza;
    }


    /**
     * Gets the nome value for this PersonaCompleta.
     * 
     * @return nome
     */
    public java.lang.String getNome() {
        return nome;
    }


    /**
     * Sets the nome value for this PersonaCompleta.
     * 
     * @param nome
     */
    public void setNome(java.lang.String nome) {
        this.nome = nome;
    }


    /**
     * Gets the sesso value for this PersonaCompleta.
     * 
     * @return sesso
     */
    public java.lang.String getSesso() {
        return sesso;
    }


    /**
     * Sets the sesso value for this PersonaCompleta.
     * 
     * @param sesso
     */
    public void setSesso(java.lang.String sesso) {
        this.sesso = sesso;
    }


    /**
     * Gets the statoCivile value for this PersonaCompleta.
     * 
     * @return statoCivile
     */
    public java.lang.String getStatoCivile() {
        return statoCivile;
    }


    /**
     * Sets the statoCivile value for this PersonaCompleta.
     * 
     * @param statoCivile
     */
    public void setStatoCivile(java.lang.String statoCivile) {
        this.statoCivile = statoCivile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PersonaCompleta)) return false;
        PersonaCompleta other = (PersonaCompleta) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFiscale==null && other.getCodiceFiscale()==null) || 
             (this.codiceFiscale!=null &&
              this.codiceFiscale.equals(other.getCodiceFiscale()))) &&
            ((this.codiceStatoISTAT==null && other.getCodiceStatoISTAT()==null) || 
             (this.codiceStatoISTAT!=null &&
              this.codiceStatoISTAT.equals(other.getCodiceStatoISTAT()))) &&
            ((this.cognome==null && other.getCognome()==null) || 
             (this.cognome!=null &&
              this.cognome.equals(other.getCognome()))) &&
            ((this.descrizioneCittadinanza==null && other.getDescrizioneCittadinanza()==null) || 
             (this.descrizioneCittadinanza!=null &&
              this.descrizioneCittadinanza.equals(other.getDescrizioneCittadinanza()))) &&
            ((this.nome==null && other.getNome()==null) || 
             (this.nome!=null &&
              this.nome.equals(other.getNome()))) &&
            ((this.sesso==null && other.getSesso()==null) || 
             (this.sesso!=null &&
              this.sesso.equals(other.getSesso()))) &&
            ((this.statoCivile==null && other.getStatoCivile()==null) || 
             (this.statoCivile!=null &&
              this.statoCivile.equals(other.getStatoCivile())));
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
        if (getCodiceFiscale() != null) {
            _hashCode += getCodiceFiscale().hashCode();
        }
        if (getCodiceStatoISTAT() != null) {
            _hashCode += getCodiceStatoISTAT().hashCode();
        }
        if (getCognome() != null) {
            _hashCode += getCognome().hashCode();
        }
        if (getDescrizioneCittadinanza() != null) {
            _hashCode += getDescrizioneCittadinanza().hashCode();
        }
        if (getNome() != null) {
            _hashCode += getNome().hashCode();
        }
        if (getSesso() != null) {
            _hashCode += getSesso().hashCode();
        }
        if (getStatoCivile() != null) {
            _hashCode += getStatoCivile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PersonaCompleta.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "PersonaCompleta"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFiscale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "codiceFiscale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceStatoISTAT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "codiceStatoISTAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cognome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "cognome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizioneCittadinanza");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "descrizioneCittadinanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nome");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "nome"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sesso");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "sesso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statoCivile");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it", "statoCivile"));
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

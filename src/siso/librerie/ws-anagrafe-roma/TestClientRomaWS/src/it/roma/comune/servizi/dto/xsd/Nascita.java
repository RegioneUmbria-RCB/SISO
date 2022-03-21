/**
 * Nascita.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto.xsd;

public class Nascita  implements java.io.Serializable {
    private java.lang.String anno;

    private java.lang.String codiceComuneISTAT;

    private java.lang.String codiceProvinciaISTAT;

    private java.lang.String codiceStatoISTAT;

    private it.roma.comune.servizi.dto.xsd.Genitori genitori;

    private java.lang.String giorno;

    private java.lang.String mese;

    private java.lang.String nomeComune;

    private java.lang.String nomeStato;

    private java.lang.String siglaProvincia;

    public Nascita() {
    }

    public Nascita(
           java.lang.String anno,
           java.lang.String codiceComuneISTAT,
           java.lang.String codiceProvinciaISTAT,
           java.lang.String codiceStatoISTAT,
           it.roma.comune.servizi.dto.xsd.Genitori genitori,
           java.lang.String giorno,
           java.lang.String mese,
           java.lang.String nomeComune,
           java.lang.String nomeStato,
           java.lang.String siglaProvincia) {
           this.anno = anno;
           this.codiceComuneISTAT = codiceComuneISTAT;
           this.codiceProvinciaISTAT = codiceProvinciaISTAT;
           this.codiceStatoISTAT = codiceStatoISTAT;
           this.genitori = genitori;
           this.giorno = giorno;
           this.mese = mese;
           this.nomeComune = nomeComune;
           this.nomeStato = nomeStato;
           this.siglaProvincia = siglaProvincia;
    }


    /**
     * Gets the anno value for this Nascita.
     * 
     * @return anno
     */
    public java.lang.String getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this Nascita.
     * 
     * @param anno
     */
    public void setAnno(java.lang.String anno) {
        this.anno = anno;
    }


    /**
     * Gets the codiceComuneISTAT value for this Nascita.
     * 
     * @return codiceComuneISTAT
     */
    public java.lang.String getCodiceComuneISTAT() {
        return codiceComuneISTAT;
    }


    /**
     * Sets the codiceComuneISTAT value for this Nascita.
     * 
     * @param codiceComuneISTAT
     */
    public void setCodiceComuneISTAT(java.lang.String codiceComuneISTAT) {
        this.codiceComuneISTAT = codiceComuneISTAT;
    }


    /**
     * Gets the codiceProvinciaISTAT value for this Nascita.
     * 
     * @return codiceProvinciaISTAT
     */
    public java.lang.String getCodiceProvinciaISTAT() {
        return codiceProvinciaISTAT;
    }


    /**
     * Sets the codiceProvinciaISTAT value for this Nascita.
     * 
     * @param codiceProvinciaISTAT
     */
    public void setCodiceProvinciaISTAT(java.lang.String codiceProvinciaISTAT) {
        this.codiceProvinciaISTAT = codiceProvinciaISTAT;
    }


    /**
     * Gets the codiceStatoISTAT value for this Nascita.
     * 
     * @return codiceStatoISTAT
     */
    public java.lang.String getCodiceStatoISTAT() {
        return codiceStatoISTAT;
    }


    /**
     * Sets the codiceStatoISTAT value for this Nascita.
     * 
     * @param codiceStatoISTAT
     */
    public void setCodiceStatoISTAT(java.lang.String codiceStatoISTAT) {
        this.codiceStatoISTAT = codiceStatoISTAT;
    }


    /**
     * Gets the genitori value for this Nascita.
     * 
     * @return genitori
     */
    public it.roma.comune.servizi.dto.xsd.Genitori getGenitori() {
        return genitori;
    }


    /**
     * Sets the genitori value for this Nascita.
     * 
     * @param genitori
     */
    public void setGenitori(it.roma.comune.servizi.dto.xsd.Genitori genitori) {
        this.genitori = genitori;
    }


    /**
     * Gets the giorno value for this Nascita.
     * 
     * @return giorno
     */
    public java.lang.String getGiorno() {
        return giorno;
    }


    /**
     * Sets the giorno value for this Nascita.
     * 
     * @param giorno
     */
    public void setGiorno(java.lang.String giorno) {
        this.giorno = giorno;
    }


    /**
     * Gets the mese value for this Nascita.
     * 
     * @return mese
     */
    public java.lang.String getMese() {
        return mese;
    }


    /**
     * Sets the mese value for this Nascita.
     * 
     * @param mese
     */
    public void setMese(java.lang.String mese) {
        this.mese = mese;
    }


    /**
     * Gets the nomeComune value for this Nascita.
     * 
     * @return nomeComune
     */
    public java.lang.String getNomeComune() {
        return nomeComune;
    }


    /**
     * Sets the nomeComune value for this Nascita.
     * 
     * @param nomeComune
     */
    public void setNomeComune(java.lang.String nomeComune) {
        this.nomeComune = nomeComune;
    }


    /**
     * Gets the nomeStato value for this Nascita.
     * 
     * @return nomeStato
     */
    public java.lang.String getNomeStato() {
        return nomeStato;
    }


    /**
     * Sets the nomeStato value for this Nascita.
     * 
     * @param nomeStato
     */
    public void setNomeStato(java.lang.String nomeStato) {
        this.nomeStato = nomeStato;
    }


    /**
     * Gets the siglaProvincia value for this Nascita.
     * 
     * @return siglaProvincia
     */
    public java.lang.String getSiglaProvincia() {
        return siglaProvincia;
    }


    /**
     * Sets the siglaProvincia value for this Nascita.
     * 
     * @param siglaProvincia
     */
    public void setSiglaProvincia(java.lang.String siglaProvincia) {
        this.siglaProvincia = siglaProvincia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Nascita)) return false;
        Nascita other = (Nascita) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.codiceComuneISTAT==null && other.getCodiceComuneISTAT()==null) || 
             (this.codiceComuneISTAT!=null &&
              this.codiceComuneISTAT.equals(other.getCodiceComuneISTAT()))) &&
            ((this.codiceProvinciaISTAT==null && other.getCodiceProvinciaISTAT()==null) || 
             (this.codiceProvinciaISTAT!=null &&
              this.codiceProvinciaISTAT.equals(other.getCodiceProvinciaISTAT()))) &&
            ((this.codiceStatoISTAT==null && other.getCodiceStatoISTAT()==null) || 
             (this.codiceStatoISTAT!=null &&
              this.codiceStatoISTAT.equals(other.getCodiceStatoISTAT()))) &&
            ((this.genitori==null && other.getGenitori()==null) || 
             (this.genitori!=null &&
              this.genitori.equals(other.getGenitori()))) &&
            ((this.giorno==null && other.getGiorno()==null) || 
             (this.giorno!=null &&
              this.giorno.equals(other.getGiorno()))) &&
            ((this.mese==null && other.getMese()==null) || 
             (this.mese!=null &&
              this.mese.equals(other.getMese()))) &&
            ((this.nomeComune==null && other.getNomeComune()==null) || 
             (this.nomeComune!=null &&
              this.nomeComune.equals(other.getNomeComune()))) &&
            ((this.nomeStato==null && other.getNomeStato()==null) || 
             (this.nomeStato!=null &&
              this.nomeStato.equals(other.getNomeStato()))) &&
            ((this.siglaProvincia==null && other.getSiglaProvincia()==null) || 
             (this.siglaProvincia!=null &&
              this.siglaProvincia.equals(other.getSiglaProvincia())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getCodiceComuneISTAT() != null) {
            _hashCode += getCodiceComuneISTAT().hashCode();
        }
        if (getCodiceProvinciaISTAT() != null) {
            _hashCode += getCodiceProvinciaISTAT().hashCode();
        }
        if (getCodiceStatoISTAT() != null) {
            _hashCode += getCodiceStatoISTAT().hashCode();
        }
        if (getGenitori() != null) {
            _hashCode += getGenitori().hashCode();
        }
        if (getGiorno() != null) {
            _hashCode += getGiorno().hashCode();
        }
        if (getMese() != null) {
            _hashCode += getMese().hashCode();
        }
        if (getNomeComune() != null) {
            _hashCode += getNomeComune().hashCode();
        }
        if (getNomeStato() != null) {
            _hashCode += getNomeStato().hashCode();
        }
        if (getSiglaProvincia() != null) {
            _hashCode += getSiglaProvincia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Nascita.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "Nascita"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceComuneISTAT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "codiceComuneISTAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceProvinciaISTAT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "codiceProvinciaISTAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceStatoISTAT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "codiceStatoISTAT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("genitori");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "genitori"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "Genitori"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("giorno");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "giorno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mese");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "mese"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeComune");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "nomeComune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeStato");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "nomeStato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("siglaProvincia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "siglaProvincia"));
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

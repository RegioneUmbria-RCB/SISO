/**
 * DatiAnagrafeRoma.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.dto.xsd;

public class DatiAnagrafeRoma  implements java.io.Serializable {
    private java.lang.String codiceFamiglia;

    private java.lang.String codiceIndividuale;

    private java.lang.String descrizione;

    private java.lang.String flagPresenzaInFamiglia;

    private java.lang.String flagVivoResidente;

    private java.lang.String residente;

    private java.lang.String vivo;

    public DatiAnagrafeRoma() {
    }

    public DatiAnagrafeRoma(
           java.lang.String codiceFamiglia,
           java.lang.String codiceIndividuale,
           java.lang.String descrizione,
           java.lang.String flagPresenzaInFamiglia,
           java.lang.String flagVivoResidente,
           java.lang.String residente,
           java.lang.String vivo) {
           this.codiceFamiglia = codiceFamiglia;
           this.codiceIndividuale = codiceIndividuale;
           this.descrizione = descrizione;
           this.flagPresenzaInFamiglia = flagPresenzaInFamiglia;
           this.flagVivoResidente = flagVivoResidente;
           this.residente = residente;
           this.vivo = vivo;
    }


    /**
     * Gets the codiceFamiglia value for this DatiAnagrafeRoma.
     * 
     * @return codiceFamiglia
     */
    public java.lang.String getCodiceFamiglia() {
        return codiceFamiglia;
    }


    /**
     * Sets the codiceFamiglia value for this DatiAnagrafeRoma.
     * 
     * @param codiceFamiglia
     */
    public void setCodiceFamiglia(java.lang.String codiceFamiglia) {
        this.codiceFamiglia = codiceFamiglia;
    }


    /**
     * Gets the codiceIndividuale value for this DatiAnagrafeRoma.
     * 
     * @return codiceIndividuale
     */
    public java.lang.String getCodiceIndividuale() {
        return codiceIndividuale;
    }


    /**
     * Sets the codiceIndividuale value for this DatiAnagrafeRoma.
     * 
     * @param codiceIndividuale
     */
    public void setCodiceIndividuale(java.lang.String codiceIndividuale) {
        this.codiceIndividuale = codiceIndividuale;
    }


    /**
     * Gets the descrizione value for this DatiAnagrafeRoma.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this DatiAnagrafeRoma.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the flagPresenzaInFamiglia value for this DatiAnagrafeRoma.
     * 
     * @return flagPresenzaInFamiglia
     */
    public java.lang.String getFlagPresenzaInFamiglia() {
        return flagPresenzaInFamiglia;
    }


    /**
     * Sets the flagPresenzaInFamiglia value for this DatiAnagrafeRoma.
     * 
     * @param flagPresenzaInFamiglia
     */
    public void setFlagPresenzaInFamiglia(java.lang.String flagPresenzaInFamiglia) {
        this.flagPresenzaInFamiglia = flagPresenzaInFamiglia;
    }


    /**
     * Gets the flagVivoResidente value for this DatiAnagrafeRoma.
     * 
     * @return flagVivoResidente
     */
    public java.lang.String getFlagVivoResidente() {
        return flagVivoResidente;
    }


    /**
     * Sets the flagVivoResidente value for this DatiAnagrafeRoma.
     * 
     * @param flagVivoResidente
     */
    public void setFlagVivoResidente(java.lang.String flagVivoResidente) {
        this.flagVivoResidente = flagVivoResidente;
    }


    /**
     * Gets the residente value for this DatiAnagrafeRoma.
     * 
     * @return residente
     */
    public java.lang.String getResidente() {
        return residente;
    }


    /**
     * Sets the residente value for this DatiAnagrafeRoma.
     * 
     * @param residente
     */
    public void setResidente(java.lang.String residente) {
        this.residente = residente;
    }


    /**
     * Gets the vivo value for this DatiAnagrafeRoma.
     * 
     * @return vivo
     */
    public java.lang.String getVivo() {
        return vivo;
    }


    /**
     * Sets the vivo value for this DatiAnagrafeRoma.
     * 
     * @param vivo
     */
    public void setVivo(java.lang.String vivo) {
        this.vivo = vivo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DatiAnagrafeRoma)) return false;
        DatiAnagrafeRoma other = (DatiAnagrafeRoma) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codiceFamiglia==null && other.getCodiceFamiglia()==null) || 
             (this.codiceFamiglia!=null &&
              this.codiceFamiglia.equals(other.getCodiceFamiglia()))) &&
            ((this.codiceIndividuale==null && other.getCodiceIndividuale()==null) || 
             (this.codiceIndividuale!=null &&
              this.codiceIndividuale.equals(other.getCodiceIndividuale()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.flagPresenzaInFamiglia==null && other.getFlagPresenzaInFamiglia()==null) || 
             (this.flagPresenzaInFamiglia!=null &&
              this.flagPresenzaInFamiglia.equals(other.getFlagPresenzaInFamiglia()))) &&
            ((this.flagVivoResidente==null && other.getFlagVivoResidente()==null) || 
             (this.flagVivoResidente!=null &&
              this.flagVivoResidente.equals(other.getFlagVivoResidente()))) &&
            ((this.residente==null && other.getResidente()==null) || 
             (this.residente!=null &&
              this.residente.equals(other.getResidente()))) &&
            ((this.vivo==null && other.getVivo()==null) || 
             (this.vivo!=null &&
              this.vivo.equals(other.getVivo())));
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
        if (getCodiceFamiglia() != null) {
            _hashCode += getCodiceFamiglia().hashCode();
        }
        if (getCodiceIndividuale() != null) {
            _hashCode += getCodiceIndividuale().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getFlagPresenzaInFamiglia() != null) {
            _hashCode += getFlagPresenzaInFamiglia().hashCode();
        }
        if (getFlagVivoResidente() != null) {
            _hashCode += getFlagVivoResidente().hashCode();
        }
        if (getResidente() != null) {
            _hashCode += getResidente().hashCode();
        }
        if (getVivo() != null) {
            _hashCode += getVivo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DatiAnagrafeRoma.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "DatiAnagrafeRoma"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceFamiglia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "codiceFamiglia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codiceIndividuale");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "codiceIndividuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagPresenzaInFamiglia");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "flagPresenzaInFamiglia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("flagVivoResidente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "flagVivoResidente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("residente");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "residente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vivo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dto.servizi.comune.roma.it/xsd", "vivo"));
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

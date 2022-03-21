/**
 * NVA.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public interface NVA extends javax.xml.rpc.Service {

/**
 * Interrogazione Anagrafe comunale
 */
    public java.lang.String getNVASoapAddress();

    public it.roma.comune.servizi.verificheanagrafiche.NVASoap getNVASoap() throws javax.xml.rpc.ServiceException;

    public it.roma.comune.servizi.verificheanagrafiche.NVASoap getNVASoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

/**
 * ServiziUtenzeWS_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.netservice.pda.utenze;

public interface ServiziUtenzeWS_Service extends javax.xml.rpc.Service {
    public java.lang.String getUtenzeAddress();

    public it.netservice.pda.utenze.ServiziUtenzeWS_PortType getUtenze() throws javax.xml.rpc.ServiceException;

    public it.netservice.pda.utenze.ServiziUtenzeWS_PortType getUtenze(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}

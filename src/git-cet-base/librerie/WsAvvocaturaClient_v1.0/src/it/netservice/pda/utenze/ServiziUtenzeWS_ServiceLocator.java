/**
 * ServiziUtenzeWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.netservice.pda.utenze;

public class ServiziUtenzeWS_ServiceLocator extends org.apache.axis.client.Service implements it.netservice.pda.utenze.ServiziUtenzeWS_Service {

    public ServiziUtenzeWS_ServiceLocator() {
    }


    public ServiziUtenzeWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ServiziUtenzeWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for Utenze
    private java.lang.String Utenze_address = "http://monza.ul.consiglioordineavvocati.it/utenze";
    //private java.lang.String Utenze_address = "https://monza.ul.consiglioordineavvocati.it:8082/utenze";
    
    public java.lang.String getUtenzeAddress() {
        return Utenze_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String UtenzeWSDDServiceName = "Utenze";

    public java.lang.String getUtenzeWSDDServiceName() {
        return UtenzeWSDDServiceName;
    }

    public void setUtenzeWSDDServiceName(java.lang.String name) {
        UtenzeWSDDServiceName = name;
    }

    public it.netservice.pda.utenze.ServiziUtenzeWS_PortType getUtenze() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(Utenze_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getUtenze(endpoint);
    }

    public it.netservice.pda.utenze.ServiziUtenzeWS_PortType getUtenze(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.netservice.pda.utenze.ServiziUtenzeWSBindingStub _stub = new it.netservice.pda.utenze.ServiziUtenzeWSBindingStub(portAddress, this);
            _stub.setPortName(getUtenzeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setUtenzeEndpointAddress(java.lang.String address) {
        Utenze_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.netservice.pda.utenze.ServiziUtenzeWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.netservice.pda.utenze.ServiziUtenzeWSBindingStub _stub = new it.netservice.pda.utenze.ServiziUtenzeWSBindingStub(new java.net.URL(Utenze_address), this);
                _stub.setPortName(getUtenzeWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("Utenze".equals(inputPortName)) {
            return getUtenze();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://netservice.it/pda/utenze", "ServiziUtenzeWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://netservice.it/pda/utenze", "Utenze"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("Utenze".equals(portName)) {
            setUtenzeEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

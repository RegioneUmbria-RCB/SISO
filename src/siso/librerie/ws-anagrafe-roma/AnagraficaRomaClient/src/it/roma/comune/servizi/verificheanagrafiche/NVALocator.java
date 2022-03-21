/**
 * NVALocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.verificheanagrafiche;

public class NVALocator extends org.apache.axis.client.Service implements it.roma.comune.servizi.verificheanagrafiche.NVA {

/**
 * Interrogazione Anagrafe comunale
 */

    public NVALocator() {
    }


    public NVALocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public NVALocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for NVASoap
    private java.lang.String NVASoap_address = "http://localhost/webdev2/PA/pri/Verifiche/NVA.asmx";

    public java.lang.String getNVASoapAddress() {
        return NVASoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String NVASoapWSDDServiceName = "NVASoap";

    public java.lang.String getNVASoapWSDDServiceName() {
        return NVASoapWSDDServiceName;
    }

    public void setNVASoapWSDDServiceName(java.lang.String name) {
        NVASoapWSDDServiceName = name;
    }

    public it.roma.comune.servizi.verificheanagrafiche.NVASoap getNVASoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(NVASoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getNVASoap(endpoint);
    }

    public it.roma.comune.servizi.verificheanagrafiche.NVASoap getNVASoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.roma.comune.servizi.verificheanagrafiche.NVASoapStub _stub = new it.roma.comune.servizi.verificheanagrafiche.NVASoapStub(portAddress, this);
            _stub.setPortName(getNVASoapWSDDServiceName());
             return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setNVASoapEndpointAddress(java.lang.String address) {
        NVASoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.roma.comune.servizi.verificheanagrafiche.NVASoap.class.isAssignableFrom(serviceEndpointInterface)) {
                it.roma.comune.servizi.verificheanagrafiche.NVASoapStub _stub = new it.roma.comune.servizi.verificheanagrafiche.NVASoapStub(new java.net.URL(NVASoap_address), this);
                _stub.setPortName(getNVASoapWSDDServiceName());
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
        if ("NVASoap".equals(inputPortName)) {
            return getNVASoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "NVA");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servizi.comune.roma.it/verificheanagrafiche", "NVASoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("NVASoap".equals(portName)) {
            setNVASoapEndpointAddress(address);
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

/**
 * ClientAnagrafeRomaLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.test;

public class ClientAnagrafeRomaLocator extends org.apache.axis.client.Service implements it.roma.comune.servizi.test.ClientAnagrafeRoma {

    public ClientAnagrafeRomaLocator() {
    }


    public ClientAnagrafeRomaLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClientAnagrafeRomaLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClientAnagrafeRomaHttpSoap11Endpoint
    private java.lang.String ClientAnagrafeRomaHttpSoap11Endpoint_address = "http://localhost:8099/ClientRomaWS/services/ClientAnagrafeRoma.ClientAnagrafeRomaHttpSoap11Endpoint/";

    public java.lang.String getClientAnagrafeRomaHttpSoap11EndpointAddress() {
        return ClientAnagrafeRomaHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName = "ClientAnagrafeRomaHttpSoap11Endpoint";

    public java.lang.String getClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName() {
        return ClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName;
    }

    public void setClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        ClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName = name;
    }

    public it.roma.comune.servizi.test.ClientAnagrafeRomaPortType getClientAnagrafeRomaHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClientAnagrafeRomaHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClientAnagrafeRomaHttpSoap11Endpoint(endpoint);
    }

    public it.roma.comune.servizi.test.ClientAnagrafeRomaPortType getClientAnagrafeRomaHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            it.roma.comune.servizi.test.ClientAnagrafeRomaSoap11BindingStub _stub = new it.roma.comune.servizi.test.ClientAnagrafeRomaSoap11BindingStub(portAddress, this);
            _stub.setPortName(getClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClientAnagrafeRomaHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        ClientAnagrafeRomaHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.roma.comune.servizi.test.ClientAnagrafeRomaPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                it.roma.comune.servizi.test.ClientAnagrafeRomaSoap11BindingStub _stub = new it.roma.comune.servizi.test.ClientAnagrafeRomaSoap11BindingStub(new java.net.URL(ClientAnagrafeRomaHttpSoap11Endpoint_address), this);
                _stub.setPortName(getClientAnagrafeRomaHttpSoap11EndpointWSDDServiceName());
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
        if ("ClientAnagrafeRomaHttpSoap11Endpoint".equals(inputPortName)) {
            return getClientAnagrafeRomaHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://test.servizi.comune.roma.it", "ClientAnagrafeRoma");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://test.servizi.comune.roma.it", "ClientAnagrafeRomaHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClientAnagrafeRomaHttpSoap11Endpoint".equals(portName)) {
            setClientAnagrafeRomaHttpSoap11EndpointEndpointAddress(address);
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

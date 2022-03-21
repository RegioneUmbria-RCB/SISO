/**
 * ControllaStatoProcessoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package it.webred.rulengine.brick.reperimento.executor.caronte.client;

public class ControllaStatoProcessoServiceLocator extends org.apache.axis.client.Service implements it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoService {

    public ControllaStatoProcessoServiceLocator() {
    }


    public ControllaStatoProcessoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ControllaStatoProcessoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    
    // costruttore aggiunto Filippo Mazzini per impostare dinamicamente ControllaStatoProcesso_address
    public ControllaStatoProcessoServiceLocator(java.lang.String ControllaStatoProcesso_address) {
    	this();
    	this.ControllaStatoProcesso_address = ControllaStatoProcesso_address;
    }

    // Use to get a proxy class for ControllaStatoProcesso
    private java.lang.String ControllaStatoProcesso_address = "http://localhost:8080/caronte/services/ControllaStatoProcesso";

    public java.lang.String getControllaStatoProcessoAddress() {
        return ControllaStatoProcesso_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ControllaStatoProcessoWSDDServiceName = "ControllaStatoProcesso";

    public java.lang.String getControllaStatoProcessoWSDDServiceName() {
        return ControllaStatoProcessoWSDDServiceName;
    }

    public void setControllaStatoProcessoWSDDServiceName(java.lang.String name) {
        ControllaStatoProcessoWSDDServiceName = name;
    }

    public it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso getControllaStatoProcesso() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ControllaStatoProcesso_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getControllaStatoProcesso(endpoint);
    }

    public it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso getControllaStatoProcesso(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
        	it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoSoapBindingStub _stub = new it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoSoapBindingStub(portAddress, this);
            _stub.setPortName(getControllaStatoProcessoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setControllaStatoProcessoEndpointAddress(java.lang.String address) {
        ControllaStatoProcesso_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso.class.isAssignableFrom(serviceEndpointInterface)) {
            	it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoSoapBindingStub _stub = new it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoSoapBindingStub(new java.net.URL(ControllaStatoProcesso_address), this);
                _stub.setPortName(getControllaStatoProcessoWSDDServiceName());
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
        if ("ControllaStatoProcesso".equals(inputPortName)) {
            return getControllaStatoProcesso();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://client.caronte.webred.it", "ControllaStatoProcessoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://client.caronte.webred.it", "ControllaStatoProcesso"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ControllaStatoProcesso".equals(portName)) {
            setControllaStatoProcessoEndpointAddress(address);
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

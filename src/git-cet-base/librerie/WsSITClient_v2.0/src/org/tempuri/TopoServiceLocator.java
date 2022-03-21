/**
 * TopoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class TopoServiceLocator extends org.apache.axis.client.Service implements org.tempuri.TopoService {

    public TopoServiceLocator() {
    }


    public TopoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TopoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_ITopoService
    private java.lang.String BasicHttpBinding_ITopoService_address = "http://nportale.comune.milano.it/WcfTopo/TopoService.svc";

    public java.lang.String getBasicHttpBinding_ITopoServiceAddress() {
        return BasicHttpBinding_ITopoService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_ITopoServiceWSDDServiceName = "BasicHttpBinding_ITopoService";

    public java.lang.String getBasicHttpBinding_ITopoServiceWSDDServiceName() {
        return BasicHttpBinding_ITopoServiceWSDDServiceName;
    }

    public void setBasicHttpBinding_ITopoServiceWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_ITopoServiceWSDDServiceName = name;
    }

    public org.tempuri.ITopoService getBasicHttpBinding_ITopoService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_ITopoService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_ITopoService(endpoint);
    }

    public org.tempuri.ITopoService getBasicHttpBinding_ITopoService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_ITopoServiceStub _stub = new org.tempuri.BasicHttpBinding_ITopoServiceStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_ITopoServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_ITopoServiceEndpointAddress(java.lang.String address) {
        BasicHttpBinding_ITopoService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.ITopoService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_ITopoServiceStub _stub = new org.tempuri.BasicHttpBinding_ITopoServiceStub(new java.net.URL(BasicHttpBinding_ITopoService_address), this);
                _stub.setPortName(getBasicHttpBinding_ITopoServiceWSDDServiceName());
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
        if ("BasicHttpBinding_ITopoService".equals(inputPortName)) {
            return getBasicHttpBinding_ITopoService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "TopoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_ITopoService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_ITopoService".equals(portName)) {
            setBasicHttpBinding_ITopoServiceEndpointAddress(address);
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

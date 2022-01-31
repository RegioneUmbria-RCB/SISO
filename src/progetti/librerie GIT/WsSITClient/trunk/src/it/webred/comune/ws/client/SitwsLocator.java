/**
 * SitwsLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.webred.comune.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.rmi.*;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.*;

public class SitwsLocator extends Service implements Sitws {

    public SitwsLocator() {
    }


    public SitwsLocator(EngineConfiguration config) {
        super(config);
    }

    public SitwsLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for sitwsSoap
    private java.lang.String sitwsSoap_address = "http://nportale.comune.milano.it/wsTopo/sitws.asmx";

    public java.lang.String getsitwsSoapAddress() {
        return sitwsSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String sitwsSoapWSDDServiceName = "sitwsSoap";

    public java.lang.String getsitwsSoapWSDDServiceName() {
        return sitwsSoapWSDDServiceName;
    }

    public void setsitwsSoapWSDDServiceName(String name) {
        sitwsSoapWSDDServiceName = name;
    }

    public SitwsSoap getsitwsSoap() throws ServiceException {
       URL endpoint;
        try {
            endpoint = new URL(sitwsSoap_address);
        }
        catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getsitwsSoap(endpoint);
    }

    public SitwsSoap getsitwsSoap(URL portAddress) throws ServiceException {
        try {
            SitwsSoapStub _stub = new SitwsSoapStub(portAddress, this);
            _stub.setPortName(getsitwsSoapWSDDServiceName());
            return _stub;
        }
        catch (AxisFault e) {
            return null;
        }
    }

    public void setsitwsSoapEndpointAddress(java.lang.String address) {
        sitwsSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (SitwsSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                SitwsSoapStub _stub = new SitwsSoapStub(new URL(sitwsSoap_address), this);
                _stub.setPortName(getsitwsSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("sitwsSoap".equals(inputPortName)) {
            return getsitwsSoap();
        }
        else  {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://www.comune.milano.it/sit2006/sit2006", "sitws");
    }

    private HashSet ports = null;

    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://www.comune.milano.it/sit2006/sit2006", "sitwsSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws ServiceException {
        
if ("sitwsSoap".equals(portName)) {
            setsitwsSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(QName portName, java.lang.String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}

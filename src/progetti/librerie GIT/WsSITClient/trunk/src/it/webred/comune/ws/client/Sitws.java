/**
 * Sitws.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.webred.comune.ws.client;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface Sitws extends Service {
	
    public String getsitwsSoapAddress();

    public SitwsSoap getsitwsSoap() throws ServiceException;

    public SitwsSoap getsitwsSoap(URL portAddress) throws ServiceException;
}

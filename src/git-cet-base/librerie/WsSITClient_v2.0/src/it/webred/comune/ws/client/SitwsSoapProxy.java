package it.webred.comune.ws.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

public class SitwsSoapProxy implements SitwsSoap {
  private String _endpoint = null;
  private SitwsSoap sitwsSoap = null;
  
  public SitwsSoapProxy() {
    _initSitwsSoapProxy();
  }
  
  public SitwsSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSitwsSoapProxy();
  }
  
  private void _initSitwsSoapProxy() {
    try {
      sitwsSoap = (new SitwsLocator()).getsitwsSoap();
      if (sitwsSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sitwsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sitwsSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sitwsSoap != null)
      ((Stub)sitwsSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public SitwsSoap getSitwsSoap() {
    if (sitwsSoap == null)
      _initSitwsSoapProxy();
    return sitwsSoap;
  }
  
  public String sitRequest(String pRequestXML) throws RemoteException{
    if (sitwsSoap == null)
      _initSitwsSoapProxy();
    return sitwsSoap.sitRequest(pRequestXML);
  }
  
  
}
package it.netservice.pda.utenze;

public class ServiziUtenzeWSProxy implements it.netservice.pda.utenze.ServiziUtenzeWS_PortType {
  private String _endpoint = null;
  private it.netservice.pda.utenze.ServiziUtenzeWS_PortType serviziUtenzeWS_PortType = null;
  
  public ServiziUtenzeWSProxy() {
    _initServiziUtenzeWSProxy();
  }
  
  public ServiziUtenzeWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initServiziUtenzeWSProxy();
  }
  
  private void _initServiziUtenzeWSProxy() {
    try {
      serviziUtenzeWS_PortType = (new it.netservice.pda.utenze.ServiziUtenzeWS_ServiceLocator()).getUtenze();
      if (serviziUtenzeWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)serviziUtenzeWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)serviziUtenzeWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (serviziUtenzeWS_PortType != null)
      ((javax.xml.rpc.Stub)serviziUtenzeWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.netservice.pda.utenze.ServiziUtenzeWS_PortType getServiziUtenzeWS_PortType() {
    if (serviziUtenzeWS_PortType == null)
      _initServiziUtenzeWSProxy();
    return serviziUtenzeWS_PortType;
  }
  
  public java.lang.Boolean isPdAUser(java.lang.String arg0) throws java.rmi.RemoteException{
    if (serviziUtenzeWS_PortType == null)
      _initServiziUtenzeWSProxy();
    return serviziUtenzeWS_PortType.isPdAUser(arg0);
  }
  
  
}
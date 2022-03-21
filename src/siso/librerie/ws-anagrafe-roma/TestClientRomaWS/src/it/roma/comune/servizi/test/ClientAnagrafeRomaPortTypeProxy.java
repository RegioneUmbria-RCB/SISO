package it.roma.comune.servizi.test;

public class ClientAnagrafeRomaPortTypeProxy implements it.roma.comune.servizi.test.ClientAnagrafeRomaPortType {
  private String _endpoint = null;
  private it.roma.comune.servizi.test.ClientAnagrafeRomaPortType clientAnagrafeRomaPortType = null;
  
  public ClientAnagrafeRomaPortTypeProxy() {
    _initClientAnagrafeRomaPortTypeProxy();
  }
  
  public ClientAnagrafeRomaPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initClientAnagrafeRomaPortTypeProxy();
  }
  
  private void _initClientAnagrafeRomaPortTypeProxy() {
    try {
      clientAnagrafeRomaPortType = (new it.roma.comune.servizi.test.ClientAnagrafeRomaLocator()).getClientAnagrafeRomaHttpSoap11Endpoint();
      if (clientAnagrafeRomaPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)clientAnagrafeRomaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)clientAnagrafeRomaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (clientAnagrafeRomaPortType != null)
      ((javax.xml.rpc.Stub)clientAnagrafeRomaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.roma.comune.servizi.test.ClientAnagrafeRomaPortType getClientAnagrafeRomaPortType() {
    if (clientAnagrafeRomaPortType == null)
      _initClientAnagrafeRomaPortTypeProxy();
    return clientAnagrafeRomaPortType;
  }
  
  public it.roma.comune.servizi.dto.xsd.RicercaResult eseguiRicercaAnagraficaEstesa(java.lang.String wsURL, java.lang.String cognome, java.lang.String nome, java.lang.String sesso, java.lang.String annoIniziale, java.lang.String annoFinale) throws java.rmi.RemoteException{
    if (clientAnagrafeRomaPortType == null)
      _initClientAnagrafeRomaPortTypeProxy();
    return clientAnagrafeRomaPortType.eseguiRicercaAnagraficaEstesa(wsURL, cognome, nome, sesso, annoIniziale, annoFinale);
  }
  
  public it.roma.comune.servizi.dto.xsd.RicercaResult verificaDatiAnagrafici(java.lang.String URL, java.lang.String cognome, java.lang.String nome, java.lang.String annoNascita, java.lang.String meseNascita, java.lang.String giornoNascita, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (clientAnagrafeRomaPortType == null)
      _initClientAnagrafeRomaPortTypeProxy();
    return clientAnagrafeRomaPortType.verificaDatiAnagrafici(URL, cognome, nome, annoNascita, meseNascita, giornoNascita, codiceFiscale);
  }
  
  public it.roma.comune.servizi.dto.xsd.RicercaResult ricercaPerCodiceIndividuale(java.lang.String wsURL, java.lang.String codiceIndividuale) throws java.rmi.RemoteException{
    if (clientAnagrafeRomaPortType == null)
      _initClientAnagrafeRomaPortTypeProxy();
    return clientAnagrafeRomaPortType.ricercaPerCodiceIndividuale(wsURL, codiceIndividuale);
  }
  
  
}
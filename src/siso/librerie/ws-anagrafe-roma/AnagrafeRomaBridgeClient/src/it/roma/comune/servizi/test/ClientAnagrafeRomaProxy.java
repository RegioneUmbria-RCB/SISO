package it.roma.comune.servizi.test;

public class ClientAnagrafeRomaProxy implements it.roma.comune.servizi.test.ClientAnagrafeRoma {
  private String _endpoint = null;
  private it.roma.comune.servizi.test.ClientAnagrafeRoma clientAnagrafeRoma = null;
  
  public ClientAnagrafeRomaProxy() {
    _initClientAnagrafeRomaProxy();
  }
  
  public ClientAnagrafeRomaProxy(String endpoint) {
    _endpoint = endpoint;
    _initClientAnagrafeRomaProxy();
  }
  
  private void _initClientAnagrafeRomaProxy() {
    try {
      clientAnagrafeRoma = (new it.roma.comune.servizi.test.ClientAnagrafeRomaServiceLocator()).getClientAnagrafeRoma();
      if (clientAnagrafeRoma != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)clientAnagrafeRoma)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)clientAnagrafeRoma)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (clientAnagrafeRoma != null)
      ((javax.xml.rpc.Stub)clientAnagrafeRoma)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.roma.comune.servizi.test.ClientAnagrafeRoma getClientAnagrafeRoma() {
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma;
  }
  
  public it.roma.comune.servizi.dto.RicercaResult ricercaPerCodiceIndividuale(java.lang.String chiaveCifratura, java.lang.String wsURL, java.lang.String codiceIndividuale) throws java.rmi.RemoteException{
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma.ricercaPerCodiceIndividuale(chiaveCifratura, wsURL, codiceIndividuale);
  }
  
  public it.roma.comune.servizi.dto.RicercaResult eseguiRicercaStatoFamigliaConv(java.lang.String chiaveCifratura, java.lang.String wsURL, java.lang.String codiceIndividuale, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma.eseguiRicercaStatoFamigliaConv(chiaveCifratura, wsURL, codiceIndividuale, codiceFiscale);
  }
  
  public it.roma.comune.servizi.dto.RicercaResult verificaDatiAnagrafici(java.lang.String chiaveCifratura, java.lang.String URL, java.lang.String cognome, java.lang.String nome, java.lang.String sesso, java.lang.String annoNascita, java.lang.String meseNascita, java.lang.String giornoNascita, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma.verificaDatiAnagrafici(chiaveCifratura, URL, cognome, nome, sesso, annoNascita, meseNascita, giornoNascita, codiceFiscale);
  }
  
  public it.roma.comune.servizi.dto.RicercaResult verificaDatiAnagraficiCompleta(java.lang.String chiaveCifratura, java.lang.String URL, java.lang.String cognome, java.lang.String nome, java.lang.String sesso, java.lang.String annoNascita, java.lang.String meseNascita, java.lang.String giornoNascita, java.lang.String codiceFiscale) throws java.rmi.RemoteException{
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma.verificaDatiAnagraficiCompleta(chiaveCifratura, URL, cognome, nome, sesso, annoNascita, meseNascita, giornoNascita, codiceFiscale);
  }
  
  public it.roma.comune.servizi.dto.RicercaResult eseguiRicercaAnagraficaEstesa(java.lang.String chiaveCifratura, java.lang.String wsURL, java.lang.String cognome, java.lang.String nome, java.lang.String sesso, java.lang.String annoIniziale, java.lang.String annoFinale) throws java.rmi.RemoteException{
    if (clientAnagrafeRoma == null)
      _initClientAnagrafeRomaProxy();
    return clientAnagrafeRoma.eseguiRicercaAnagraficaEstesa(chiaveCifratura, wsURL, cognome, nome, sesso, annoIniziale, annoFinale);
  }
  
  
}
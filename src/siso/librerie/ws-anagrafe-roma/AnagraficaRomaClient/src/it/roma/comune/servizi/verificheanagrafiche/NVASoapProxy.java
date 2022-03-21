package it.roma.comune.servizi.verificheanagrafiche;

public class NVASoapProxy implements it.roma.comune.servizi.verificheanagrafiche.NVASoap {
  private String _endpoint = null;
  private it.roma.comune.servizi.verificheanagrafiche.NVASoap nVASoap = null;
  
  public NVASoapProxy() {
    _initNVASoapProxy();
  }
  
  public NVASoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initNVASoapProxy();
  }
  
  private void _initNVASoapProxy() {
    try {
      nVASoap = (new it.roma.comune.servizi.verificheanagrafiche.NVALocator()).getNVASoap();
      if (nVASoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)nVASoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)nVASoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (nVASoap != null)
      ((javax.xml.rpc.Stub)nVASoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.NVASoap getNVASoap() {
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap;
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse verificaAnagrafica(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaAnagrafica(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelliResponse verificaLeggeRutelli(it.roma.comune.servizi.verificheanagrafiche.VerificaLeggeRutelli parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaLeggeRutelli(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse verificaAnagraficaEstesa(it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaAnagraficaEstesa(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaDocumentiResponse verificaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaDocumenti(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdentResponse verificaCartaIdent(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdent parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaCartaIdent(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaElettoraleResponse verificaElettorale(it.roma.comune.servizi.verificheanagrafiche.VerificaElettorale parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaElettorale(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitareResponse verificaLevaMilitare(it.roma.comune.servizi.verificheanagrafiche.VerificaLevaMilitare parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaLevaMilitare(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConvResponse verificaSitFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaSitFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaSitFamigliaConv(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse verificaStatoFamigliaConv(it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaStatoFamigliaConv(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumentiResponse verificaStoricaDocumenti(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaDocumenti parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaStoricaDocumenti(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuoResponse verificaStoricaIndividuo(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricaIndividuo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaStoricaIndividuo(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioniResponse verificaVaccinazioni(it.roma.comune.servizi.verificheanagrafiche.VerificaVaccinazioni parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaVaccinazioni(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzoResponse verificaDomiciliatiperIndirizzo(it.roma.comune.servizi.verificheanagrafiche.VerificaDomiciliatiperIndirizzo parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaDomiciliatiperIndirizzo(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCiviciResponse verificaDomXRangeCivici(it.roma.comune.servizi.verificheanagrafiche.VerificaDomXRangeCivici parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaDomXRangeCivici(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaCartaIdResponse verificaCartaId(it.roma.comune.servizi.verificheanagrafiche.VerificaCartaId parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaCartaId(parameters, logHeader);
  }
  
  public it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXDataResponse verificaStoricoFamigliaXData(it.roma.comune.servizi.verificheanagrafiche.VerificaStoricoFamigliaXData parameters, it.roma.comune.servizi.verificheanagrafiche.LogHeader logHeader) throws java.rmi.RemoteException{
    if (nVASoap == null)
      _initNVASoapProxy();
    return nVASoap.verificaStoricoFamigliaXData(parameters, logHeader);
  }
  
  
}
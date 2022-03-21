package it.webred.rulengine.brick.reperimento.executor.caronte.client;

public class ControllaStatoProcessoProxy implements it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso {
  private String _endpoint = null;
  private it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso controllaStatoProcesso = null;
  //variabile aggiunta Filippo Mazzini per impostare dinamicamente ControllaStatoProcesso_address in ControllaStatoProcessoServiceLocator
  private java.lang.String ControllaStatoProcesso_address = null;
  
  public ControllaStatoProcessoProxy() {
    _initControllaStatoProcessoProxy();
  }
  
  //costruttore aggiunto Filippo Mazzini per impostare dinamicamente ControllaStatoProcesso_address in ControllaStatoProcessoServiceLocator
  public ControllaStatoProcessoProxy(java.lang.String ControllaStatoProcesso_address) {
	this.ControllaStatoProcesso_address = ControllaStatoProcesso_address;
	_initControllaStatoProcessoProxy();
  }
  
  private void _initControllaStatoProcessoProxy() {
    try {
      //controllaStatoProcesso = (new it.webred.caronte.client.ControllaStatoProcessoServiceLocator()).getControllaStatoProcesso();
      //sostituito con: (Filippo Mazzini)
      if (ControllaStatoProcesso_address != null && !ControllaStatoProcesso_address.equals("")) {
    	  controllaStatoProcesso = (new it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoServiceLocator(ControllaStatoProcesso_address)).getControllaStatoProcesso();
      }else{
    	  controllaStatoProcesso = (new it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcessoServiceLocator()).getControllaStatoProcesso();
      }
      //fine sostituito
      if (controllaStatoProcesso != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)controllaStatoProcesso)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)controllaStatoProcesso)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (controllaStatoProcesso != null)
      ((javax.xml.rpc.Stub)controllaStatoProcesso)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public it.webred.rulengine.brick.reperimento.executor.caronte.client.ControllaStatoProcesso getControllaStatoProcesso() {
    if (controllaStatoProcesso == null)
      _initControllaStatoProcessoProxy();
    return controllaStatoProcesso;
  }
  
  public it.webred.rulengine.brick.reperimento.executor.caronte.client.StatoCaronte getStato(java.lang.String processId) throws java.rmi.RemoteException{
    if (controllaStatoProcesso == null)
      _initControllaStatoProcessoProxy();
    return controllaStatoProcesso.getStato(processId);
  }
  
  
}
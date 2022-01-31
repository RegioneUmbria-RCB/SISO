package org.tempuri;

public class ITopoServiceProxy implements org.tempuri.ITopoService {
  private String _endpoint = null;
  private org.tempuri.ITopoService iTopoService = null;
  
  public ITopoServiceProxy() {
    _initITopoServiceProxy();
  }
  
  public ITopoServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initITopoServiceProxy();
  }
  
  private void _initITopoServiceProxy() {
    try {
      iTopoService = (new org.tempuri.TopoServiceLocator()).getBasicHttpBinding_ITopoService();
      if (iTopoService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iTopoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iTopoService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iTopoService != null)
      ((javax.xml.rpc.Stub)iTopoService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.ITopoService getITopoService() {
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService;
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo sitGetInfo(java.lang.String pToken) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetInfo(pToken);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase sitGetCiviciFPointBase(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetCiviciFPointBase(pToken, pCoordinateX, pCoordinateY, pReference, pState, pNumber, pDistance);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended sitGetCiviciFPointExtended(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetCiviciFPointExtended(pToken, pCoordinateX, pCoordinateY, pReference, pState, pNumber, pDistance);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo sitGetVieFTopo(java.lang.String pToken, java.lang.String pToponomy, java.lang.String pType) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetVieFTopo(pToken, pToponomy, pType);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode sitGetViaFCode(java.lang.String pToken, java.lang.Integer pStreetCode, java.lang.String pCivic, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetViaFCode(pToken, pStreetCode, pCivic, pState, pReference);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc sitGetCivicoFidc(java.lang.String pToken, java.lang.Integer pIDC, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetCivicoFidc(pToken, pIDC, pReference);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory sitGetCivicoStory(java.lang.String pToken, java.lang.Integer pIDC, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetCivicoStory(pToken, pIDC, pData1, pData2);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange sitGetCivicoChange(java.lang.String pToken, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetCivicoChange(pToken, pData1, pData2);
  }
  
  public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia sitGetTipoVia(java.lang.String pToken, java.lang.String pType) throws java.rmi.RemoteException{
    if (iTopoService == null)
      _initITopoServiceProxy();
    return iTopoService.sitGetTipoVia(pToken, pType);
  }
  
  
}
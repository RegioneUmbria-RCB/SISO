/**
 * ITopoService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface ITopoService extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetInfo sitGetInfo(java.lang.String pToken) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointBase sitGetCiviciFPointBase(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCiviciFPointExtended sitGetCiviciFPointExtended(java.lang.String pToken, java.lang.Double pCoordinateX, java.lang.Double pCoordinateY, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, java.lang.Integer pNumber, java.lang.Double pDistance) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetVieFTopo sitGetVieFTopo(java.lang.String pToken, java.lang.String pToponomy, java.lang.String pType) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetViaFCode sitGetViaFCode(java.lang.String pToken, java.lang.Integer pStreetCode, java.lang.String pCivic, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumStatoCivico pState, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoFidc sitGetCivicoFidc(java.lang.String pToken, java.lang.Integer pIDC, org.datacontract.schemas._2004._07.WcfTopo.TopoServiceEnumReference pReference) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoStory sitGetCivicoStory(java.lang.String pToken, java.lang.Integer pIDC, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetCivicoChange sitGetCivicoChange(java.lang.String pToken, java.lang.String pData1, java.lang.String pData2) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.WcfTopo.TopoServiceTypeGetTipoVia sitGetTipoVia(java.lang.String pToken, java.lang.String pType) throws java.rmi.RemoteException;
}

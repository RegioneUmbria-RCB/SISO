package it.webred.WsSitClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.jdom.*;
import org.jdom.input.*;

import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import it.webred.WsSitClient.dto.CivicoDTO;
import it.webred.WsSitClient.dto.RequestDTO;
import it.webred.WsSitClient.dto.ResponseDTO;
import it.webred.WsSitClient.dto.ViaDTO;
import it.webred.comune.ws.client.SitwsSoapProxy;

public class WsSITClient {
	
	private static final Logger log = Logger.getLogger("it.webred.ct.proc");
	
	public final static String  GAUSS_BOAGA = "GB";
	public final static String  WGS_WEB_MARCATOR = "WGSWM";
	public final static String  WGS_1984 = "WGS84";
	
	public final static String STATO_TUTTI = "0";
	public final static String STATO_APPLICATO = "2";
	public final static String STATO_ITER_IN_CORSO = "4";
	public final static String STATO_PROVVISORIO = "5";
	public final static String STATO_SOPPRESSO = "99";

	public final static String CIVICI_NO = "";
	public final static String CIVICI_ALL = "*";

	
	private static ResourceBundle bundle;
	static {
		bundle = ResourceBundle.getBundle("it.webred.WsSitClient.resources.xmlrequest");
	}
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
	try{
        
			
		System.out.println("INPUT TOKEN:");
		
		 BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		 String input = bufferRead.readLine();
		
		// String response = getXmlSitResponse(pRequestXML_0id);
		 
		 RequestDTO request = new RequestDTO();
		 request.setToken(input);
		 request.setDataIniRif(SDF.parse("01/01/2013"));
		 request.setDataFinRif(new Date());
		 
		 request.setCivico("*");
		 
		 System.out.println("CODVIA:");
		 String codVia = bufferRead.readLine();
		 request.setCodiceVia(codVia);
		
		 ResponseDTO response = sitGetCiviciVia(request);
		
		 
		 System.out.println("Versione: "+response.getListaCivici());
		 System.out.println("Err Code: "+response.getErrCode());
		 System.out.println("Err Desc: "+response.getErrDescrizione());
		
		 for(CivicoDTO civ : response.getListaCivici())
			 System.out.println(civ.getIdc());
		
	} catch (Exception e) { 
	
		log.error("Exception: "+ e.getMessage(),e );
	
	}

}
	
	private static String getXmlSitResponse(String pRequestXML_0id) throws RemoteException {
		
		SitwsSoapProxy testid = new SitwsSoapProxy();
		
	     String pRequestXML_0idTemp = null;
		    if(!pRequestXML_0id.equals("")){
		     pRequestXML_0idTemp  = pRequestXML_0id;
		    }
		    
			   String sitRequest2mtemp = testid.sitRequest(pRequestXML_0idTemp);
			if(sitRequest2mtemp == null){
				return sitRequest2mtemp;
			
			}else{
			    String tempResultreturnp3 = String.valueOf(sitRequest2mtemp);
			    
			    return tempResultreturnp3;
			}
			
	}
	
	
	private static ResponseDTO verificaXmlSitResponse(String xmlResponse){
		ResponseDTO res = new ResponseDTO();
		
		if(xmlResponse!=null){
			SAXBuilder saxBuilder = new SAXBuilder();
			try {
			    Document doc = saxBuilder.build(new StringReader(xmlResponse));
			    List<Element> nodi = doc.getRootElement().getChildren();
			    for(Element nodo : nodi){
			    	if(nodo.getName().equalsIgnoreCase("ERROR")){
			    		res.setFlgErrore(true);
			    		List<Element> errori = nodo.getChildren();
			    		for(Element err : errori){
			    			if(err.getName().equalsIgnoreCase("CODE"))
			    				res.setErrCode(err.getValue());
			    			if(err.getName().equalsIgnoreCase("MSG"))
			    				res.setErrDescrizione(err.getValue());
			    		}
			    	}
			    }
			    		 
			} catch (JDOMException e) {
			    // handle JDOMException
			} catch (IOException e) {
			    // handle IOException
			}
		}else{
			res.setFlgErrore(true);
			res.setErrCode("CLN");
			res.setErrDescrizione("Stringa di Risposta NULL");
		}
		
		return res;
	}
	
	
	public static ResponseDTO sitGetInfo(RequestDTO request) {
		ResponseDTO res = new ResponseDTO();
		try {
			String xmlReq = bundle.getString("sitGetInfo");
			
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("TOKEN", request.getToken());
			
			xmlReq = valorizzaXmlRequest(xmlReq,params);
			
			log.debug(xmlReq);
			
			String xmlRes = getXmlSitResponse(xmlReq);
			
			res = verificaXmlSitResponse(xmlRes);
			
			if(xmlRes!=null && !res.getFlgErrore()){
				SAXBuilder saxBuilder = new SAXBuilder();
			
			    Document doc = saxBuilder.build(new StringReader(xmlRes));
			    List<Element> nodi = doc.getRootElement().getChildren();
			    for(Element nodo : nodi){
			    	if(nodo.getName().equalsIgnoreCase("REV"))
			    		res.setVersione(new BigDecimal(nodo.getValue()));
			    }
		    }
		} catch (Exception e) {
			res.setErrCode("CLN");
			res.setErrDescrizione(e.getMessage());
		} 

		
		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoChange(RequestDTO request) throws Exception{
		
		String xmlReq = bundle.getString("sitGetCivicoChange");
		
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("TOKEN", request.getToken());
		params.put("DATA1", request.getDataIniRif()!=null ? SDF.format(request.getDataIniRif()): null);
		params.put("DATA2", request.getDataFinRif()!=null ? SDF.format(request.getDataFinRif()): null);
		
		xmlReq = valorizzaXmlRequest(xmlReq,params);
		
		log.debug(xmlReq);
		
		String xmlRes;
	
		xmlRes = getXmlSitResponse(xmlReq);
		
		ResponseDTO res = verificaXmlSitResponse(xmlRes);
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(xmlRes!=null && !res.getFlgErrore()){
		SAXBuilder saxBuilder = new SAXBuilder();
		
		    Document doc = saxBuilder.build(new StringReader(xmlRes));
		    List<Element> nodi = doc.getRootElement().getChildren();
		    for(Element nodo : nodi){
		    	if(nodo.getName().equalsIgnoreCase("CIVICO")){
		    		CivicoDTO civ = new CivicoDTO();
		    		civ.setId(nodo.getAttributeValue("id"));
		    		List<Element> civInfo = nodo.getChildren();
		    		for(Element ci : civInfo){
		    			if(ci.getName().equalsIgnoreCase("IDC"))
		    				civ.setIdc(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("DATA_APPLICAZIONE"))
		    				civ.setDataApplicazione(ci.getValue()!=null && !ci.getValue().isEmpty() ? SDF.parse(ci.getValue()) : null);
		    			if(ci.getName().equalsIgnoreCase("DATA_SOPPRESSIONE"))
		    				civ.setDataSoppressione(ci.getValue()!=null && !ci.getValue().isEmpty() ? SDF.parse(ci.getValue()) : null);
		    			
		    		}
		    		
		    		listaCivico.add(civ);
		    	}	
		    }
		
		}
		
		res.setListaCivici(listaCivico);
		
		return res;
		
	}
	
   public static ResponseDTO sitGetVieFtopo(RequestDTO request) throws Exception{
		
		String xmlReq = bundle.getString("sitGetVieFTopo");
		
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("TOKEN", request.getToken());
		params.put("TOPONOMY", request.getToponomy());
		params.put("TYPE", request.getType());
		
		
		xmlReq = valorizzaXmlRequest(xmlReq,params);
		
		log.debug(xmlReq);
		
		String xmlRes;
	
		xmlRes = getXmlSitResponse(xmlReq);
		
		ResponseDTO res = verificaXmlSitResponse(xmlRes);
		List<ViaDTO> listaVie = new ArrayList<ViaDTO>();
		
		if(xmlRes!=null && !res.getFlgErrore()){
		SAXBuilder saxBuilder = new SAXBuilder();
	
		    Document doc = saxBuilder.build(new StringReader(xmlRes));
		    List<Element> nodi = doc.getRootElement().getChildren();
		    for(Element nodo : nodi){
		    	if(nodo.getName().equalsIgnoreCase("VIA")){
		    		ViaDTO via = new ViaDTO();
		    		via.setId(nodo.getAttributeValue("id"));
		    		List<Element> viaInfo = nodo.getChildren();
		    		for(Element ci : viaInfo)
		    			valorizzaVia(via, ci);
		    		
		    		listaVie.add(via);
		    	}	
		    }
		
		}
		
		res.setListaVie(listaVie);
		
		return res;
		
	}
   
   public static ResponseDTO sitGetCiviciVia(RequestDTO request) throws Exception{
		
		String xmlReq = bundle.getString("sitGetViaFCode");
		
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("TOKEN", request.getToken());
		params.put("CODICEVIA", request.getCodiceVia());
		params.put("CIVICO", request.getCivico()!=null ? request.getCivico() : CIVICI_ALL);
		params.put("STATE", request.getState()!=null ? request.getState() : STATO_TUTTI);
		params.put("REFERENCE", request.getReference()!=null ? request.getReference() : GAUSS_BOAGA);
		
		xmlReq = valorizzaXmlRequest(xmlReq,params);
		
		//log.debug(xmlReq);
		
		String xmlRes;
	
		xmlRes = getXmlSitResponse(xmlReq);
		
		ResponseDTO res = verificaXmlSitResponse(xmlRes);
		
		List<CivicoDTO> listaCiviciOut = new ArrayList<CivicoDTO>();
		
		if(xmlRes!=null && !res.getFlgErrore()){
		SAXBuilder saxBuilder = new SAXBuilder();
	
		    Document doc = saxBuilder.build(new StringReader(xmlRes));
		    Element civici = doc.getRootElement().getChild("CIVICI");
	    	if(civici!=null){
	    		
	    		List<Element> lstCivico = civici.getChildren("CIVICO");
	    		for(Element civ : lstCivico){
	    			
	    			CivicoDTO civico = new CivicoDTO();
		    		civico.setId(civ.getAttributeValue("id"));
		    		civico.setIdc(civ.getChild("IDC").getValue());
		    		civico.setCodStato(civ.getChild("COD_STATO").getValue());
		    				
    				request.setIdcCivico(civico.getIdc());
    				ResponseDTO resCivico = sitGetCivicoFidc(request);
    				if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0)
    					 listaCiviciOut.add(resCivico.getListaCivici().get(0));
    				 else{
    					 
    					 civico.setCoordx(civ.getChild("COORDX").getValue());
    			    	 civico.setCoordy(civ.getChild("COORDY").getValue());
    			    	 civico.setToponimo(civ.getChild("TOPONIMO").getValue());
    					 
    					 log.debug("Dettaglio Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civico.printCivico());
    					 listaCiviciOut.add(civico);
    				 }
	    			
	    		}
	    	}
	   
		}
		
		res.setListaCivici(listaCiviciOut);
		
		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoFidc(RequestDTO request) throws Exception{
		
		String xmlReq = bundle.getString("sitGetCivicoFidc");
		
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("TOKEN", request.getToken());
		params.put("IDC", request.getIdcCivico());
		params.put("REFERENCE", request.getReference()!=null ? request.getReference() : GAUSS_BOAGA);
		
		xmlReq = valorizzaXmlRequest(xmlReq,params);
		
		//log.debug(xmlReq);
		
		String xmlRes;
	
		xmlRes = getXmlSitResponse(xmlReq);
		
		ResponseDTO res = verificaXmlSitResponse(xmlRes);
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(xmlRes!=null && !res.getFlgErrore()){
		SAXBuilder saxBuilder = new SAXBuilder();
		
		    Document doc = saxBuilder.build(new StringReader(xmlRes));
		    List<Element> nodi = doc.getRootElement().getChildren();
		    for(Element nodo : nodi){
		    	if(nodo.getName().equalsIgnoreCase("CIVICO")){
		    		CivicoDTO civ = new CivicoDTO();
		    		List<Element> civInfo = nodo.getChildren();
		    		for(Element ci : civInfo){
		    			if(ci.getName().equalsIgnoreCase("IDC"))
		    				civ.setIdc(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("COORDX"))
		    				civ.setCoordx(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("COORDY"))
		    				civ.setCoordy(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("NUMCOMPLETO"))
		    				civ.setNumCompleto(ci.getValue());
		    			
		    			valorizzaVia(civ, ci);
		    			
		    			if(ci.getName().equalsIgnoreCase("NUMERO"))
		    				civ.setNumero(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("LETTERA"))
		    				civ.setLettera(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("BARRA"))
		    				civ.setBarra(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("COD_STATO"))
		    				civ.setCodStato(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("DESC_STATO"))
		    				civ.setDescStato(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("DATA_APPLICAZIONE"))
		    				civ.setDataApplicazione(ci.getValue()!=null && !ci.getValue().isEmpty() ? SDF.parse(ci.getValue()) : null);
		    			if(ci.getName().equalsIgnoreCase("DATA_SOPPRESSIONE"))
		    				civ.setDataSoppressione(ci.getValue()!=null && !ci.getValue().isEmpty() ? SDF.parse(ci.getValue()) : null);
		    			if(ci.getName().equalsIgnoreCase("RESIDENZIALE"))
		    				civ.setResidenziale(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("ZDN"))
		    				civ.setZdn(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("CAP"))
		    				civ.setCap(ci.getValue());
		    			if(ci.getName().equalsIgnoreCase("URL"))
		    				civ.setUrl(ci.getValue());
		    		}
		    		
		    		listaCivico.add(civ);
		    	}	
		    }
		
		}
		
		res.setListaCivici(listaCivico);
		
		return res;
	}
	
	
	private static ViaDTO valorizzaVia(ViaDTO via, Element ci){
		
		if(ci.getName().equalsIgnoreCase("TOPONIMO"))
			via.setToponimo(ci.getValue());
		if(ci.getName().equalsIgnoreCase("CODICEVIA"))
			via.setCodVia(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO_VIA"))
			via.setTopoVia(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO1"))
			via.setTopo1(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO2"))
			via.setTopo2(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO3"))
			via.setTopo3(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO4"))
			via.setTopo4(ci.getValue());
		if(ci.getName().equalsIgnoreCase("TOPO5"))
			via.setTopo5(ci.getValue());
		
		return via;
	}
	
	
	public static ResponseDTO sitGetCivicoChangeDati(RequestDTO request) throws Exception {
			
		 ResponseDTO response = sitGetCivicoChange(request);
		 List<CivicoDTO> listaCiviciIn = response.getListaCivici();
		 List<CivicoDTO> listaCiviciOut = new ArrayList<CivicoDTO>();
		 if(listaCiviciIn!=null){
			 for(CivicoDTO civ : listaCiviciIn){
				 request.setIdcCivico(civ.getIdc());
				 ResponseDTO resCivico = sitGetCivicoFidc(request);
				 if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0)
					 listaCiviciOut.add(resCivico.getListaCivici().get(0));
				 else{
					 log.debug("Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civ.printCivico());
					 listaCiviciOut.add(civ);
				 }
				 
			 }
		 }
		
		 response.setListaCivici(listaCiviciOut);
		 return response;
	}
		
	
	private static String valorizzaXmlRequest(String xmlReq, HashMap<String,String> parametri) throws JDOMException, IOException{
		SAXBuilder saxBuilder = new SAXBuilder();
		
		    Document doc = saxBuilder.build(new StringReader(xmlReq));
		    List<Element> nodi = doc.getRootElement().getChildren();
		    for(Element nodo : nodi){
		    	String valore = parametri.get(nodo.getName());
		    	if(valore!=null)
		    		nodo.setText(valore);
		    }
		    
		    xmlReq = new XMLOutputter().outputString(doc);
		   
		
		return xmlReq;
	}
	
	
}

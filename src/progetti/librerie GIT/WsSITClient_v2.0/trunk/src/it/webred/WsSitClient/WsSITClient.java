package it.webred.WsSitClient;

import it.webred.WsSitClient.dto.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.datacontract.schemas._2004._07.WcfTopo.*;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.tempuri.ITopoServiceProxy;

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

	public final static String CIVICI_NO = "-";
	public final static String CIVICI_ALL = "";
	
	private final static HashMap<String,TopoServiceEnumReference> mappaReference = new HashMap<String,TopoServiceEnumReference>(){{
	       put(GAUSS_BOAGA,TopoServiceEnumReference.GausBoaga); 
	       put(WGS_WEB_MARCATOR,TopoServiceEnumReference.WGSWebMercator);
	       put(WGS_1984,TopoServiceEnumReference.WGS84);
	 }};
	 
	 private final static HashMap<String,TopoServiceEnumStatoCivico> mappaStato = new HashMap<String,TopoServiceEnumStatoCivico>(){{
	       put(STATO_APPLICATO,TopoServiceEnumStatoCivico.Applicato); 
	       put(STATO_TUTTI,TopoServiceEnumStatoCivico.Tutti);
	       put(STATO_SOPPRESSO,TopoServiceEnumStatoCivico.Soppresso);
	       put(STATO_PROVVISORIO,TopoServiceEnumStatoCivico.Provvisorio);
	       put(STATO_ITER_IN_CORSO,TopoServiceEnumStatoCivico.IterInCorso);
	 }};
	
	private static TopoServiceEnumReference decodeReference(String sRef){
		TopoServiceEnumReference ref = null;
		if(sRef!=null)
		 ref = mappaReference.get(sRef);
		
		if(ref==null)
			ref = mappaReference.get(GAUSS_BOAGA);
		return ref;
	}
	
	private static TopoServiceEnumStatoCivico decodeStato(String sStato){
		TopoServiceEnumStatoCivico stato = null;
		if(sStato!=null)
		 stato = mappaStato.get(sStato);
		
		if(stato==null)
			stato = mappaStato.get(STATO_TUTTI);
		return stato;
	}
	
	private static ResourceBundle bundle;
	static {
		bundle = ResourceBundle.getBundle("it.webred.WsSitClient.resources.xmlrequest");
	}
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
	try{
        
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		/*System.out.println("INPUT TOKEN:");
		
		 
		 String input = bufferRead.readLine();*/
		
		// String response = getXmlSitResponse(pRequestXML_0id);
		 
		 RequestDTO request = new RequestDTO();
		 request.setToken("13hiweb!CdMf01");
		 request.setDataIniRif(SDF.parse("01/04/2013"));
		 request.setDataFinRif(new Date());
		
		 System.out.println("CODVIA:");
		 String codVia = bufferRead.readLine();
		 request.setCodiceVia(codVia);
		
		 ResponseDTO response = sitGetCiviciVia(request);
		//ResponseDTO response = sitGetVieFtopo(request);
		 
		 System.out.println("Lista: "+response.getListaCivici().size());
		 System.out.println("Err Code: "+response.getErrCode());
		 System.out.println("Err Desc: "+response.getErrDescrizione());
	
	/*	 System.out.println("Dati Civici Via - dettaglio da IDC:"+codVia);
		 for(CivicoDTO civ : response.getListaCivici())
			 System.out.println(civ.print());*/
		
	} catch (Exception e) { 
	
		System.out.println("Exception: "+ e.getMessage());
	
	}

}
	

	private static ResponseDTO verificaSitResponse(TopoServiceTypeError err){
		
		ResponseDTO res = new ResponseDTO();
		
		if(err!=null){
			if(!err.getCode().equals("000")){
				res.setFlgErrore(true);
				res.setErrCode(err.getCode());
				res.setErrDescrizione(err.getMessage());
			}
		}
		
		return res;
	}
	
	
	public static ResponseDTO sitGetInfo(RequestDTO request) {
		
			ResponseDTO res = new ResponseDTO();
		try {
			
		   ITopoServiceProxy proxy = new ITopoServiceProxy();
		   String pToken_1id=  request.getToken();
	       java.lang.String pToken_1idTemp = null;
	       if(!pToken_1id.equals("")){
	        pToken_1idTemp  = pToken_1id;
	       }
	       TopoServiceTypeGetInfo info = proxy.sitGetInfo(pToken_1idTemp);

	       res = verificaSitResponse(info.getErr());
	       
	       if(!res.getFlgErrore())
	    	   res.setVersione(new BigDecimal(info.getRevision()));
			
		} catch (Exception e) {
			res.setErrCode("CLN");
			res.setErrDescrizione(e.getMessage());
		} 

		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoChange(RequestDTO request) throws Exception{
		
	   ITopoServiceProxy proxy = new ITopoServiceProxy();
	   
	   String token = request.getToken();
	   String dataIni = request.getDataIniRif()!=null ? SDF.format(request.getDataIniRif()): null;
	   String dataFin = request.getDataFinRif()!=null ? SDF.format(request.getDataFinRif()): null;
	   
       TopoServiceTypeGetCivicoChange resWs = proxy.sitGetCivicoChange(token, dataIni, dataFin);
	   
       ResponseDTO res = verificaSitResponse(resWs.getErr());
	  
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			TopoServiceTypeGetCivicoChangeCivic[] lstCivWs = resWs.getCivic();
			for(TopoServiceTypeGetCivicoChangeCivic civWs :lstCivWs){
				
				CivicoDTO civParz = new CivicoDTO();
				civParz.setIdc(civWs.getIDC().toString());
				civParz.setDataApplicazione(civWs.getDateUp()!=null && !civWs.getDateUp().isEmpty() ? SDF.parse(civWs.getDateUp()) : null);
				civParz.setDataSoppressione(civWs.getDateDown()!=null && !civWs.getDateDown().isEmpty() ? SDF.parse(civWs.getDateDown()) : null);
				
				listaCivico.add(civParz);
			}	
		   }	
		   
		res.setListaCivici(listaCivico);
		
		return res;
		
	}
	
   public static ResponseDTO sitGetVieFtopo(RequestDTO request) throws Exception{
	   
	   ITopoServiceProxy proxy = new ITopoServiceProxy();
	   
	   String token = request.getToken();
	   String toponomy = request.getToponomy()!=null ? request.getToponomy() : "";
	   String type = request.getType()!=null ? request.getType() : "";
	   
       log.debug("Token:["+token+"]");
       log.debug("Toponomy:["+toponomy+"]");
       log.debug("Type:["+type+"]");
       
       TopoServiceTypeGetVieFTopo vieTopo = proxy.sitGetVieFTopo(token, toponomy, type);
    		   
       ResponseDTO res = verificaSitResponse(vieTopo.getErr());
	   
       List<ViaDTO> lstVie = new ArrayList<ViaDTO>();
       
       if(!res.getFlgErrore()){
    	   TopoServiceTypeGetVieFTopoStreet[] lstStreet = vieTopo.getStreet();
    	   
    	   for(TopoServiceTypeGetVieFTopoStreet street : lstStreet){
    		   ViaDTO via = new ViaDTO();
    		    via.setToponimo(street.getToponym());
				via.setCodVia(street.getStreetCode().toString());
				via.setTopoVia(street.getTopoVia());
				via.setTopo1(street.getTopo1());
				via.setTopo2(street.getTopo2());
				via.setTopo3(street.getTopo3());
				via.setTopo4(street.getTopo4());
				via.setTopo5(street.getTopo5());
				
				//System.out.println(via.print());
			
    		   lstVie.add(via);
    	   }
    	   
       }
      
       res.setListaVie(lstVie);
		return res;
		
	}
   
   public static ResponseDTO sitGetCiviciVia(RequestDTO request) throws Exception{
	   
	   ITopoServiceProxy proxy = new ITopoServiceProxy();
	   
	   //Parametri
	   String token = request.getToken();
	   Integer codVia = request.getCodiceVia()!=null ? new Integer(request.getCodiceVia()) : null;
	   String civico = request.getCivico()!=null ? request.getCivico() : CIVICI_ALL;
	   TopoServiceEnumStatoCivico stato = decodeStato(request.getState());
	   TopoServiceEnumReference reference = decodeReference(request.getReference());
		
       TopoServiceTypeGetViaFCode resWs = proxy.sitGetViaFCode(token, codVia, civico, stato, reference);
	   
		ResponseDTO res = verificaSitResponse(resWs.getErr());
		
		List<CivicoDTO> listaCiviciOut = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			
			//Memorizzo i dati della Via
			ViaDTO via = new ViaDTO();
			via.setCodVia(resWs.getStreetCode().toString());
			via.setToponimo(resWs.getToponym());
			via.setTopoVia(resWs.getTopoVia());
			via.setTopo1(resWs.getTopo1());
			via.setTopo2(resWs.getTopo2());
			via.setTopo3(resWs.getTopo3());
			via.setTopo4(resWs.getTopo4());
			via.setTopo5(resWs.getTopo5());
			
		/*	System.out.println("Dati Via con codice:"+codVia);
			System.out.println(via.print());*/
			
			List <ViaDTO> lstVie = new ArrayList<ViaDTO>();
			lstVie.add(via);
			res.setListaVie(lstVie);
			
			TopoServiceTypeGetViaFCodeCivic[] lstCiviciWs = resWs.getCivic();
			
		//	System.out.println("sitGetViaFCode - Lista Civici via: ");
			for(TopoServiceTypeGetViaFCodeCivic civWs : lstCiviciWs){
				
				CivicoDTO civParz = new CivicoDTO();
				civParz.setIdc(civWs.getIDC().toString());
				civParz.setCoordx(civWs.getCoordinateX().toString());
				civParz.setCoordy(civWs.getCoordinateY().toString());
				civParz.setToponimo(civWs.getToponym());
				civParz.setCodStato(civWs.getStateCode().toString());
				
		//		System.out.println(civParz.print());
				
				//Ricerco i dati completi del civico per codice
				request.setIdcCivico(civWs.getIDC().toString());
				
				ResponseDTO resCivico = sitGetCivicoFidc(request);
				if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0){
					listaCiviciOut.add(resCivico.getListaCivici().get(0));
			/*		System.out.println("sitGetCivicoFidc - Civico Dettaglio da codice: "+civWs.getIDC().toString());
					System.out.println(resCivico.getListaCivici().get(0).print());
					System.out.println("");*/
				}else{
					log.debug("Dettaglio Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civParz.print());
					listaCiviciOut.add(civParz);
				}
			}
	    }
	   
		res.setListaCivici(listaCiviciOut);
		
		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoFidc(RequestDTO request) throws Exception{
		
	   ITopoServiceProxy proxy = new ITopoServiceProxy();
		   
	   String token = request.getToken();
	   Integer idc = request.getIdcCivico()!=null ? new Integer(request.getIdcCivico()) : null;
	   TopoServiceEnumReference reference = decodeReference(request.getReference());
		
	/*   log.debug("Token:["+token+"]");
       log.debug("idc:["+idc+"]");
       log.debug("reference:["+reference.getValue()+"]");*/
	  
       TopoServiceTypeGetCivicoFidc civicoWs = proxy.sitGetCivicoFidc(token, idc, reference);
   	
		ResponseDTO res = verificaSitResponse(civicoWs.getErr());
		
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			
			Integer idcVal = civicoWs.getIDC();
			
			if(idcVal!=null && idcVal.intValue()>0){
				CivicoDTO civ = new CivicoDTO();
			
				civ.setIdc(idcVal.toString());
				civ.setCoordx(civicoWs.getCoordinateX().toString());
				civ.setCoordy(civicoWs.getCoordinateY().toString());
				civ.setNumCompleto(civicoWs.getComplete());
				
				//Dati Via
				civ.setToponimo(civicoWs.getToponym());
				civ.setCodVia(civicoWs.getStreetCode().toString());
				civ.setTopoVia(civicoWs.getTopoVia());
				civ.setTopo1(civicoWs.getTopo1());
				civ.setTopo2(civicoWs.getTopo2());
				civ.setTopo3(civicoWs.getTopo3());
				civ.setTopo4(civicoWs.getTopo4());
				civ.setTopo5(civicoWs.getTopo5());
				
				civ.setNumero(civicoWs.getNumber());
				civ.setLettera(civicoWs.getCharacter());
				civ.setBarra(civicoWs.getBar());
				civ.setCodStato(civicoWs.getStateCode().toString());
				civ.setDescStato(civicoWs.getStateDescription());
				civ.setDataApplicazione(civicoWs.getDateUp()!=null && !civicoWs.getDateUp().isEmpty() ? SDF.parse(civicoWs.getDateUp()) : null);
				civ.setDataSoppressione(civicoWs.getDateDown()!=null && !civicoWs.getDateDown().isEmpty() ? SDF.parse(civicoWs.getDateDown()) : null);
				civ.setResidenziale(civicoWs.getResidential().toString());
				civ.setZdn(civicoWs.getZDN());
				civ.setCap(civicoWs.getCAP());
				civ.setUrl(civicoWs.getURL());
				
				listaCivico.add(civ);
			}
				
		}
		
		res.setListaCivici(listaCivico);
		
		return res;
	}
	
	
	
	public static ResponseDTO sitGetCivicoChangeDati(RequestDTO request) throws Exception {
			
		 ResponseDTO response = sitGetCivicoChange(request);
		 List<CivicoDTO> listaCiviciIn = response.getListaCivici();
		 List<CivicoDTO> listaCiviciOut = new ArrayList<CivicoDTO>();
		 if(listaCiviciIn!=null){
			 for(CivicoDTO civ : listaCiviciIn){
		//		 System.out.println("Civico Variato: "+civ.print());
				 request.setIdcCivico(civ.getIdc());
				 ResponseDTO resCivico = sitGetCivicoFidc(request);
				 if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0){
					 listaCiviciOut.add(resCivico.getListaCivici().get(0));
			/*		 System.out.println("sitGetCivicoFidc - Civico Dettaglio da codice: "+civ.getIdc());
					 System.out.println(resCivico.getListaCivici().get(0).print());
					 System.out.println("");*/
				 }else{
					 log.debug("Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civ.print());
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

package it.webred.WsSitClient;

import it.webred.WsSitClient.dto.CivicoDTO;
import it.webred.WsSitClient.dto.RequestDTO;
import it.webred.WsSitClient.dto.ResponseDTO;
import it.webred.WsSitClient.dto.ViaDTO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.NamedValue;
import org.apache.log4j.Logger;
import org.tempuri.Toponomastica40Stub;
import org.tempuri.Toponomastica40Stub.ArrayOfTopoServiceTypeGetCivicoChangeCivic;
import org.tempuri.Toponomastica40Stub.ArrayOfTopoServiceTypeGetViaFCodeCivic;
import org.tempuri.Toponomastica40Stub.ArrayOfTopoServiceTypeGetVieFTopoStreet;
import org.tempuri.Toponomastica40Stub.SitGetCivicoChange;
import org.tempuri.Toponomastica40Stub.SitGetCivicoChangeResponse;
import org.tempuri.Toponomastica40Stub.SitGetCivicoFidc;
import org.tempuri.Toponomastica40Stub.SitGetCivicoFidcResponse;
import org.tempuri.Toponomastica40Stub.SitGetInfo;
import org.tempuri.Toponomastica40Stub.SitGetInfoResponse;
import org.tempuri.Toponomastica40Stub.SitGetViaFCode;
import org.tempuri.Toponomastica40Stub.SitGetViaFCodeResponse;
import org.tempuri.Toponomastica40Stub.SitGetVieFTopo;
import org.tempuri.Toponomastica40Stub.SitGetVieFTopoResponse;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeError;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeGetCivicoChangeCivic;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeGetCivicoFidc;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeGetViaFCode;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeGetViaFCodeCivic;
import org.tempuri.Toponomastica40Stub.TopoServiceTypeGetVieFTopoStreet;

import sun.misc.BASE64Encoder;

public class WsSITClient {
	
	private static final Logger log = Logger.getLogger("it.webred.ct.proc");
	
	public final static int  GAUSS_BOAGA = 1;
	public final static int  WGS_WEB_MARCATOR = 3;
	public final static int  WGS_1984 = 2;
	
	public final static int STATO_TUTTI = 0;
	public final static int STATO_APPLICATO = 2;
	public final static int STATO_ITER_IN_CORSO = 4;
	public final static int STATO_PROVVISORIO = 5;
	public final static int STATO_FITTIZIO = 6;
	public final static int STATO_CENTROIDE = 8;
	public final static int STATO_SOPPRESSO = 99;

	public final static String CIVICI_NO = "-";
	public final static String CIVICI_ALL = "";
	
	private final static HashMap<Integer,TopoServiceEnumReference> mappaReference = new HashMap<Integer,TopoServiceEnumReference>(){{
	       put(GAUSS_BOAGA,TopoServiceEnumReference.GausBoaga); 
	       put(WGS_WEB_MARCATOR,TopoServiceEnumReference.WGSWebMercator);
	       put(WGS_1984,TopoServiceEnumReference.WGS84);
	 }};
	 
	 private final static HashMap<Integer,TopoServiceEnumStatoCivico> mappaStato = new HashMap<Integer,TopoServiceEnumStatoCivico>(){{
	       put(STATO_APPLICATO,TopoServiceEnumStatoCivico.Applicato); 
	       put(STATO_TUTTI,TopoServiceEnumStatoCivico.Tutti);
	       put(STATO_SOPPRESSO,TopoServiceEnumStatoCivico.Soppresso);
	       put(STATO_PROVVISORIO,TopoServiceEnumStatoCivico.Provvisorio);
	       put(STATO_ITER_IN_CORSO,TopoServiceEnumStatoCivico.IterInCorso);
	       put(STATO_FITTIZIO,TopoServiceEnumStatoCivico.Fittizio);
	       put(STATO_CENTROIDE,TopoServiceEnumStatoCivico.Centroide);
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
	
	private static SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

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
	
	private static Toponomastica40Stub valorizzaHeaderAuth(Toponomastica40Stub proxy,RequestDTO request) throws Exception{
		
		try{
			ServiceClient cl = proxy._getServiceClient();
			String headerKey = org.apache.axis2.transport.http.HTTPConstants.HEADER_AUTHORIZATION;
			
			String tokenEncoded = request.getTokenAuth();
			
			String accessToken = "Bearer "+tokenEncoded;
			
			 //Retrive Options object from ServiceClient
			 Options options = cl.getOptions();
			 //Instantiate an ArrayList of type NamedValue from org.apache.axis2.context.NamedValue
			 List<NamedValue> namedValuePairs = new ArrayList<NamedValue>();
			 //Add as much as headers you want using below code
			 namedValuePairs.add(new NamedValue(headerKey, accessToken));
			 //Finally add namedValuePairs to options, and add options to msgContext
			 options.setProperty(org.apache.axis2.transport.http.HTTPConstants.HTTP_HEADERS, namedValuePairs);
			 cl.setOptions(options);

			 proxy._setServiceClient(cl);
		
		}catch(Exception e){
			throw e;
		}
		
		
		return proxy;
	}
	
	public static ResponseDTO sitGetInfo(RequestDTO request) {
		
		ResponseDTO res = new ResponseDTO();
		try {
		
			Toponomastica40Stub proxy = new Toponomastica40Stub();
			proxy = valorizzaHeaderAuth(proxy, request);
			
			SitGetInfo sitGetInfo = new SitGetInfo();
			
		   String pToken_1id=  request.getToken();
	       if(!pToken_1id.equals("")){
	    	   sitGetInfo.setPToken(pToken_1id);
	       }
	       SitGetInfoResponse info = proxy.sitGetInfo(sitGetInfo);

	       res = verificaSitResponse(info.getSitGetInfoResult().getErr());
	       
	       if(!res.getFlgErrore())
	    	   res.setVersione(new BigDecimal(info.getSitGetInfoResult().getRevision()));
			
		} catch (Exception e) {
			res.setErrCode("CLN");
			res.setErrDescrizione(e.getMessage());
		} 

		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoChange(RequestDTO request) throws Exception{
		
		Toponomastica40Stub proxy = new Toponomastica40Stub();
		proxy = valorizzaHeaderAuth(proxy, request);

		SitGetCivicoChange sitGetCivChange = new SitGetCivicoChange();
		sitGetCivChange.setPToken(request.getToken());
		sitGetCivChange.setPData1(request.getDataIniRif()!=null ? SDF.format(request.getDataIniRif()): null);
		sitGetCivChange.setPData2(request.getDataFinRif()!=null ? SDF.format(request.getDataFinRif()): null);
	   
		SitGetCivicoChangeResponse resWs = proxy.sitGetCivicoChange(sitGetCivChange);
	   
        ResponseDTO res = verificaSitResponse(resWs.getSitGetCivicoChangeResult().getErr());
	  
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			ArrayOfTopoServiceTypeGetCivicoChangeCivic arrlstCivWs = resWs.getSitGetCivicoChangeResult().getCivic();
			TopoServiceTypeGetCivicoChangeCivic[] lstCivWs=  arrlstCivWs.getTopoServiceTypeGetCivicoChangeCivic();
			for(TopoServiceTypeGetCivicoChangeCivic civWs :lstCivWs){
				
				CivicoDTO civParz = new CivicoDTO();
				civParz.setIdc(Integer.toString(civWs.getIDC()));
				civParz.setDataApplicazione(civWs.getDateUp()!=null && !civWs.getDateUp().isEmpty() ? SDF.parse(civWs.getDateUp()) : null);
				civParz.setDataSoppressione(civWs.getDateDown()!=null && !civWs.getDateDown().isEmpty() ? SDF.parse(civWs.getDateDown()) : null);
				
				listaCivico.add(civParz);
			}	
		   }	
		res.setListaCivici(listaCivico);
		
		return res;
		
	}
	
	public static ResponseDTO sitGetVieFtopo(RequestDTO request) throws Exception{
	   
		//ITopoServiceProxy proxy = new ITopoServiceProxy();
		Toponomastica40Stub proxy = new Toponomastica40Stub();
		ResponseDTO res = null;
		try {
		
		proxy = valorizzaHeaderAuth(proxy, request);
		
		String token = request.getToken();
		String toponomy = request.getToponomy()!=null ? request.getToponomy() : "";
		String type = request.getType()!=null ? request.getType() : "";
		
		SitGetVieFTopo sitGetVieFTopo = new SitGetVieFTopo();
		
		sitGetVieFTopo.setPToken(token);
		sitGetVieFTopo.setPToponomy(toponomy);
		sitGetVieFTopo.setPType(type);
		   
		log.debug("Token:["+token+"]");
		log.debug("Toponomy:["+toponomy+"]");
		log.debug("Type:["+type+"]");
       
		SitGetVieFTopoResponse vieTopo = proxy.sitGetVieFTopo(sitGetVieFTopo);
    		   
		res = verificaSitResponse(vieTopo.getSitGetVieFTopoResult().getErr());
	   
		List<ViaDTO> lstVie = new ArrayList<ViaDTO>();
       
		if(!res.getFlgErrore()){
			ArrayOfTopoServiceTypeGetVieFTopoStreet arrlstStreet = vieTopo.getSitGetVieFTopoResult().getStreet();
			TopoServiceTypeGetVieFTopoStreet[] lstStreet = arrlstStreet.getTopoServiceTypeGetVieFTopoStreet();
    	   
		   for(TopoServiceTypeGetVieFTopoStreet street : lstStreet){
			   ViaDTO via = new ViaDTO();
			    via.setToponimo(street.getToponym());
				via.setCodVia(Integer.toString(street.getStreetCode()));
				via.setTopoVia(street.getTopoVia());
				via.setTopo1(street.getTopo1());
				via.setTopo2(street.getTopo2());
				via.setTopo3(street.getTopo3());
				via.setTopo4(street.getTopo4());
				via.setTopo5(street.getTopo5());
			
			   lstVie.add(via);
		   }
    	   
		}
      
		res.setListaVie(lstVie);
		return res;
		
		}catch (Exception e){
			String errMess = e.getMessage();
			throw e;
		}
		
		
	}
   
	public static ResponseDTO sitGetCiviciVia(RequestDTO request) throws Exception{
	   
		Toponomastica40Stub proxy = new Toponomastica40Stub();	 
		proxy = valorizzaHeaderAuth(proxy, request);
	   
	   //Parametri
	   String token = request.getToken();
	   Integer codVia = request.getCodiceVia()!=null ? new Integer(request.getCodiceVia()) : null;
	   String civico = request.getCivico()!=null ? request.getCivico() : CIVICI_ALL;
	   
	   Integer stato =request.getState()!=null?Integer.valueOf(request.getState()):STATO_TUTTI;
	   Integer reference = request.getReference()!=null?Integer.valueOf(request.getReference()):GAUSS_BOAGA;
	  // TopoServiceEnumStatoCivico stato = decodeStato(request.getState());
	   //TopoServiceEnumReference reference = decodeReference(request.getReference());
	   
	   SitGetViaFCode sitGetViaFCode = new SitGetViaFCode();
	   
	   sitGetViaFCode.setPToken(token);
	   sitGetViaFCode.setPCivic(civico);
	   if (codVia!=null)
		   sitGetViaFCode.setPStreetCode(codVia);
	   sitGetViaFCode.setPReference(reference);
	   sitGetViaFCode.setPState(stato);
	   
	   SitGetViaFCodeResponse resWs = proxy.sitGetViaFCode(sitGetViaFCode);
	   
	   ResponseDTO res = verificaSitResponse(resWs.getSitGetViaFCodeResult().getErr());
		
	   List<CivicoDTO> listaCiviciOut = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			
			TopoServiceTypeGetViaFCode topoViaFcode = resWs.getSitGetViaFCodeResult();
			//Memorizzo i dati della Via
			ViaDTO via = new ViaDTO();
			via.setCodVia(Integer.toString(topoViaFcode.getStreetCode()));
			via.setToponimo(topoViaFcode.getToponym());
			via.setTopoVia(topoViaFcode.getTopoVia());
			via.setTopo1(topoViaFcode.getTopo1());
			via.setTopo2(topoViaFcode.getTopo2());
			via.setTopo3(topoViaFcode.getTopo3());
			via.setTopo4(topoViaFcode.getTopo4());
			via.setTopo5(topoViaFcode.getTopo5());
			
			List <ViaDTO> lstVie = new ArrayList<ViaDTO>();
			lstVie.add(via);
			res.setListaVie(lstVie);
			
			ArrayOfTopoServiceTypeGetViaFCodeCivic arrLstCiviciWs= topoViaFcode.getCivic();
			
			TopoServiceTypeGetViaFCodeCivic[] lstCiviciWs = arrLstCiviciWs!=null?arrLstCiviciWs.getTopoServiceTypeGetViaFCodeCivic():null;
			
		//	System.out.println("sitGetViaFCode - Lista Civici via: ");
			if (lstCiviciWs!=null){
				for(TopoServiceTypeGetViaFCodeCivic civWs : lstCiviciWs){
					
					CivicoDTO civParz = new CivicoDTO();
					civParz.setIdc(Integer.toString(civWs.getIDC()));
					civParz.setCoordx(Double.toString(civWs.getCoordinateX()));
					civParz.setCoordy(Double.toString(civWs.getCoordinateY()));
					civParz.setToponimo(civWs.getToponym());
					civParz.setCodStato(Integer.toString(civWs.getStateCode()));
					
					//Ricerco i dati completi del civico per codice
					request.setIdcCivico(civParz.getIdc());
					
					ResponseDTO resCivico = sitGetCivicoFidc(request);
					if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0){
						listaCiviciOut.add(resCivico.getListaCivici().get(0));
					}else{
						log.debug("Dettaglio Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civParz.print());
						listaCiviciOut.add(civParz);
					}
				}
			}
	    }
	   
		res.setListaCivici(listaCiviciOut);
		
		return res;
		
	}
	
	public static ResponseDTO sitGetCivicoFidc(RequestDTO request) throws Exception{
		
		Toponomastica40Stub proxy = new Toponomastica40Stub();
		proxy = valorizzaHeaderAuth(proxy, request);
		   
	   String token = request.getToken();
	   Integer idc = request.getIdcCivico()!=null ? new Integer(request.getIdcCivico()) : null;
	   //TopoServiceEnumReference reference = decodeReference(request.getReference());
	   Integer reference = request.getReference()!=null?Integer.valueOf(request.getReference()):GAUSS_BOAGA;
		
	   SitGetCivicoFidc sitGetCivicoFidc = new SitGetCivicoFidc();
	   sitGetCivicoFidc.setPToken(token);
	   if (idc!=null)
		   sitGetCivicoFidc.setPIDC(idc);
	   sitGetCivicoFidc.setPReference(reference);
	  
	   SitGetCivicoFidcResponse civicoWs = proxy.sitGetCivicoFidc(sitGetCivicoFidc);
   	
		ResponseDTO res = verificaSitResponse(civicoWs.getSitGetCivicoFidcResult().getErr());
		
		List<CivicoDTO> listaCivico = new ArrayList<CivicoDTO>();
		
		if(!res.getFlgErrore()){
			
			TopoServiceTypeGetCivicoFidc civWs = civicoWs.getSitGetCivicoFidcResult();
			
			Integer idcVal = civWs.getIDC();
			
			if(idcVal!=null && idcVal.intValue()>0){
				CivicoDTO civ = new CivicoDTO();
				
				civ.setIdc(idcVal.toString());
				civ.setCoordx(Double.toString(civWs.getCoordinateX()));
				civ.setCoordy(Double.toString(civWs.getCoordinateY()));
				civ.setNumCompleto(civWs.getComplete());
				
				//Dati Via
				civ.setToponimo(civWs.getToponym());
				civ.setCodVia(Integer.toString(civWs.getStreetCode()));
				civ.setTopoVia(civWs.getTopoVia());
				civ.setTopo1(civWs.getTopo1());
				civ.setTopo2(civWs.getTopo2());
				civ.setTopo3(civWs.getTopo3());
				civ.setTopo4(civWs.getTopo4());
				civ.setTopo5(civWs.getTopo5());

				civ.setNumero(civWs.getNumber());
				civ.setLettera(civWs.getCharacter());
				civ.setBarra(civWs.getBar());
				civ.setCodStato(Integer.toString(civWs.getStateCode()));
				civ.setDescStato(civWs.getStateDescription());
				civ.setDataApplicazione(civWs.getDateUp()!=null && !civWs.getDateUp().isEmpty() ? SDF.parse(civWs.getDateUp()) : null);
				civ.setDataSoppressione(civWs.getDateDown()!=null && !civWs.getDateDown().isEmpty() ? SDF.parse(civWs.getDateDown()) : null);
				civ.setResidenziale(Integer.toString(civWs.getResidential()));
				civ.setZdn(civWs.getZDN());
				civ.setCap(civWs.getCAP());
				civ.setUrl(civWs.getURL());
				civ.setBarra2(civWs.getBar2());
				
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
//			 int numCiviciIn = listaCiviciIn.size();
//			 if (numCiviciIn>20)
//				 numCiviciIn= 20;
//			 int contaCivici = 0;
			 for(CivicoDTO civ : listaCiviciIn){

//				 if (contaCivici <= numCiviciIn){
					 request.setIdcCivico(civ.getIdc());
					 ResponseDTO resCivico = sitGetCivicoFidc(request);
					 if(!resCivico.getFlgErrore() && resCivico.getListaCivici().size()>0){
						 listaCiviciOut.add(resCivico.getListaCivici().get(0));
	
					 }else{
						 log.debug("Civico non trovato ["+resCivico.getErrCode()+" "+resCivico.getErrDescrizione()+"]"+civ.print());
						 listaCiviciOut.add(civ);
					 }
					 
//					 contaCivici++;
//				 }else{
//					 break;
//				 }
				 
			 }
		 }
		
		 response.setListaCivici(listaCiviciOut);
		 return response;
	}
		
	
	/**
	 * Test generazione Token per authorization alle Api
	 */
	private void getToken ()
	{
		//String tokenEncoded = "683687a78d464519097f565e9fb28713";//ambiente test
		//String tokenEncoded = "9f6a18cf97c855a8c5d8023d2127e54c";//ambiente produzione
		
		String submitUrl = "https://api.comune.milano.it/token";
		String consumerKey ="HskSJRvncnqoUsl6RnOSWSR25YAa";
		String consumerSecret ="eONHZ5ywqiXyrpiTNu2dPZQ3udga";
		try {
			String applicationToken = consumerKey + ":" + consumerSecret;
			BASE64Encoder base64Encoder = new BASE64Encoder();
			applicationToken = "Basic " + base64Encoder.encode(applicationToken.getBytes()).trim();
			String payload = "grant_type=client_credentials";
			
		
			
			URL url = new URL(submitUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Authorization", applicationToken);
			
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
			
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuffer jsonString = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	            jsonString.append(line);
	            System.out.println(line);
	        }
	        
	        br.close();
	        conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
}

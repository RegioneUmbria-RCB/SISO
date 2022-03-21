package it.umbriadigitale.soclav.wsclient;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.handler.MessageContext;

import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;

import it.gov.lavoro.servizi.servicerdc.RDCServiceStub;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Beneficiari;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Esito_Type;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Richiesta_RDC_beneficiari_dato_CodiceFiscale;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Richiesta_RDC_beneficiari_dato_CodiceFiscaleE;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Richiesta_RDC_beneficiari_dato_codProtocolloInps;
import it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Risposta_RDC_beneficiari;
import it.gov.lavoro.servizi.servicerdc.types.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE;
import it.gov.lavoro.servizi.servizicoap.ServizicoapWSServiceSkeleton;
import it.gov.lavoro.servizi.servizicoap.ServizicoapWSServiceStub;
import it.gov.lavoro.servizi.servizicoap.types.RichiestaSAP;
import it.gov.lavoro.servizi.servizicoap.types.Richiesta_richiestaSAP_Type;
import it.gov.lavoro.servizi.servizicoap.types.Richiesta_verificaEsistenzaSAP_Type;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_richiestaSAP_Type;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP;
import it.gov.lavoro.servizi.servizicoap.types.Risposta_verificaEsistenzaSAP_Type;
import it.gov.lavoro.servizi.servizicoap.types.VerificaEsistenzaSAP;


public class ClientRdcWS {


	
	

	  //AXIS-2 CLIENT
	  public Risposta_RDC_beneficiari estraiNucleoFamiliare(String URLWS, String username, String password, String cf, String numProtINPS) throws IOException, MalformedURLException  {
	 
		  RDCServiceStub rdcService = new RDCServiceStub(URLWS);
		   
	        /*******************UserName & Password ******************************/
		  HttpTransportProperties.Authenticator basicAuth = new HttpTransportProperties.Authenticator();
		  basicAuth.setUsername(username);
		  basicAuth.setPassword(password);
		  final Options clientOptions = rdcService._getServiceClient().getOptions();
		  clientOptions.setProperty(HTTPConstants.AUTHENTICATE, basicAuth);
		  basicAuth.setPreemptiveAuthentication(true);
	        /**********************************************************************/
	     
	            
		 
		  Risposta_RDC_beneficiari risposta = null;
		  Esito_Type esito = null;
		  Beneficiari beneficiari = null;
		  
		  if(cf != null && !cf.trim().equals("")) {
			  
			  Richiesta_RDC_beneficiari_dato_CodiceFiscaleE richiesta_RDC_beneficiari_dato_CodiceFiscale = new Richiesta_RDC_beneficiari_dato_CodiceFiscaleE();
			  Richiesta_RDC_beneficiari_dato_CodiceFiscale param;
			  
			  param = new Richiesta_RDC_beneficiari_dato_CodiceFiscale();
			  param.setCodFiscale(cf);
			  richiesta_RDC_beneficiari_dato_CodiceFiscale.setRichiesta_RDC_beneficiari_dato_CodiceFiscale(param);;
			  
			  risposta = rdcService.ricevi_RDC_by_codiceFiscale(richiesta_RDC_beneficiari_dato_CodiceFiscale );
			  esito = risposta.getRisposta_RDC_beneficiari().getEsito();
			  beneficiari = risposta.getRisposta_RDC_beneficiari().getBeneficiari();
		  }
		  else if(numProtINPS != null & !numProtINPS.trim().equals("")) {
			  

			  it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE richiesta_RDC_beneficiari_dato_codProtocolloInps;
			  richiesta_RDC_beneficiari_dato_codProtocolloInps = new it.gov.lavoro.servizi.servicerdc.RDCServiceStub.Richiesta_RDC_beneficiari_dato_codProtocolloInpsE();
			  
			  Richiesta_RDC_beneficiari_dato_codProtocolloInps param = new Richiesta_RDC_beneficiari_dato_codProtocolloInps();
			  param.setCodProtocolloInps(numProtINPS);
			  
			   richiesta_RDC_beneficiari_dato_codProtocolloInps.setRichiesta_RDC_beneficiari_dato_codProtocolloInps(param );
			   risposta = rdcService.ricevi_RDC_by_codProtocolloInps(richiesta_RDC_beneficiari_dato_codProtocolloInps);
			
			  esito = risposta.getRisposta_RDC_beneficiari().getEsito();
			  beneficiari = risposta.getRisposta_RDC_beneficiari().getBeneficiari();
			   
		  }
		  
	 	  return risposta;
	  }
	  
	  

	
	  public Risposta_verificaEsistenzaSAP_Type verificaSAP(String URLWS, String username, String password, String codiceFiscale) {
		  
		  
		  EsitoOp esitoOp = new EsitoOp();
		  try {
		  ServizicoapWSServiceStub serviziCoap = new ServizicoapWSServiceStub(URLWS);
		   
	        /*******************UserName & Password ******************************/
		  HttpTransportProperties.Authenticator basicAuth = new HttpTransportProperties.Authenticator();
		  basicAuth.setUsername(username);
		  basicAuth.setPassword(password);
		  final Options clientOptions = serviziCoap._getServiceClient().getOptions();
		  clientOptions.setProperty(HTTPConstants.AUTHENTICATE, basicAuth);
		  basicAuth.setPreemptiveAuthentication(true);
	        /**********************************************************************/
	     
			String buildRequestString = "&lt;VerificaSAP&gt;\r\n" + 
					"\r\n" + 
					"&lt;codiceFiscale&gt;" + codiceFiscale + "&lt;/codiceFiscale&gt;\r\n" + 
							"\r\n" + 
							"&lt;/VerificaSAP&gt;";	
		  VerificaEsistenzaSAP verificaEsistenzaSAP = new VerificaEsistenzaSAP();
		  Richiesta_verificaEsistenzaSAP_Type param = new Richiesta_verificaEsistenzaSAP_Type();
		  param.setCodiceFiscale(buildRequestString);
		verificaEsistenzaSAP.setVerificaEsistenzaSAP(param );
		Risposta_verificaEsistenzaSAP esitoControlloSap = serviziCoap.verificaEsistenzaSAP(verificaEsistenzaSAP );
		
		
		
		  return esitoControlloSap.getRisposta_verificaEsistenzaSAP();
		  
		  
		  }catch(Exception ex){
			    ex.printStackTrace();
				esitoOp.setEx(ex);
				esitoOp.setCodEsito("-1000");
				esitoOp.setMessaggioErrore("Errore verifica SAP per Codice Fiscale" + codiceFiscale);
		  }
		  return null;
	  }

	 
	  
	  public Risposta_richiestaSAP_Type estraiSAP(String URLWS, String username, String password, String codiceSap) {
		  
		  
		  EsitoOp esitoOp = new EsitoOp();
		  try {
		  ServizicoapWSServiceStub serviziCoap = new ServizicoapWSServiceStub(URLWS);
		   
	        /*******************UserName & Password ******************************/
		  HttpTransportProperties.Authenticator basicAuth = new HttpTransportProperties.Authenticator();
		  basicAuth.setUsername(username);
		  basicAuth.setPassword(password);
		  final Options clientOptions = serviziCoap._getServiceClient().getOptions();
		  clientOptions.setProperty(HTTPConstants.AUTHENTICATE, basicAuth);
		  basicAuth.setPreemptiveAuthentication(true);
	        /**********************************************************************/
	 	 
			String buildRequestString = "&lt;IDSAP&gt;\r\n" + 
					"\r\n" + 
					"&lt;IdentificativoSap&gt;" + codiceSap + "&lt;/IdentificativoSap&gt;\r\n" + 
							"\r\n" + 
							"&lt;/IDSAP&gt;";	
			
		RichiestaSAP richiestaSAP10;
		richiestaSAP10 = new RichiestaSAP();
		Richiesta_richiestaSAP_Type param;
		param = new Richiesta_richiestaSAP_Type();
		param.setCodiceSAP(buildRequestString);
		richiestaSAP10.setRichiestaSAP(param);
		
		Risposta_richiestaSAP esitoControlloSap = serviziCoap.richiestaSAP(richiestaSAP10);
		
		
		
		  return esitoControlloSap.getRisposta_richiestaSAP();
		  
		  
		  }catch(Exception ex){
			  ex.printStackTrace();
			esitoOp.setEx(ex);
			esitoOp.setCodEsito("-1000");
			esitoOp.setMessaggioErrore("Errore richiesta SAP per codice SAP " + codiceSap);
		  }
		  return null;
	  }
}

package it.marche.regione.pddnica.client;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

import it.siso.isee.obj.Esito;
import it.siso.isee.obj.SicurezzaIdentificazioneMittente;

public class ISEESoapClient extends ISEEClientMaster {

	 	private DatiRichiestaISEE datiRichiesta;
	 	private String esitoXml = null;
	 	
		public ISEESoapClient(DatiRichiestaISEE _datiRichiesta) {
			this.datiRichiesta = _datiRichiesta;
			super.setSoapAction(_datiRichiesta.getAction());
			super.setSoapEndpointUrl(_datiRichiesta.getEndPoint());
		}

	    public Esito consultaAttestazioneCF(RicercaCF ricercaCF) {
	    	   Esito esito = new Esito();
	    		try {
	    	   String xmlAsString = getXmlObjectConsultaAttestazione(ricercaCF.getCodiceFiscale(), ricercaCF.getDataValidita(), ricercaCF.getPrestazioneDaErogare(), ricercaCF.getProtocolloDomandaEnteErogatore(), ricercaCF.getStatodomandaPrestazione());
	    	  
	    	   callSoapWebService( xmlAsString);
	    	   esito.setConsultazioneEsito(super.getEsito());
	    	   esito.setDescErrore(super.getDescrErrore());
	    	   
	    	   if(super.getEsito().equals("OK")) {
	    		  this.esitoXml = super.getNodeText("XmlEsitoAttestazione");
	    		 
	    		  XmlISEEParser iseeParser = new XmlISEEParser(this.esitoXml, true);
	    		  esito.setAttestazione(iseeParser.estraiAttestazione());
	    		}
	    		}
	    		catch(EccezioneClient exClient) {
		    		   esito.setEccezioneClient(exClient);
		    	   }
	    	   return esito;
	    }
	    
	    
	    public Esito consultaDichiarazioneCF(RicercaCF ricercaCF) {
	    	   Esito esito = new Esito();
	    	
	    	   try {
	    	   String xmlAsString = getXmlObjectConsultaDichiarazione(ricercaCF.getCodiceFiscale(), ricercaCF.getDataValidita(), ricercaCF.getPrestazioneDaErogare(), ricercaCF.getProtocolloDomandaEnteErogatore(), ricercaCF.getStatodomandaPrestazione());
	    	  
	    	   callSoapWebService( xmlAsString);
	    	   
	    	   esito.setConsultazioneEsito(super.getEsito());
	    	   esito.setDescErrore(super.getDescrErrore());
	    	   if(super.getEsito().equals("OK")) {
	    		  this.esitoXml = super.getNodeText("XmlEsitoDichiarazione");
	    		  XmlISEEParser iseeParser = new XmlISEEParser(this.esitoXml, true);
	    		  esito.setDichiarazione(iseeParser.estraiDichiarazione());
	    		}
	    	   }
	    	   catch(EccezioneClient exClient) {
	    		   esito.setEccezioneClient(exClient);
	    	   }
	    	   return esito;
	    }
	    public Esito verificaEsistenzaProcolloCF(RicercaEP ricercaEP) {
	    	   Esito esito = new Esito();
	    	
	    	   try {
	    	   String xmlAsString = getXmlObjectVerificaEsistenzaProtocollo(ricercaEP.getCf(), ricercaEP.getNumeroProtocolloDSU());
	    	  
	    	   callSoapWebService( xmlAsString);
	    	   
	    	   esito.setConsultazioneEsito(super.getEsito());
	    	   esito.setDescErrore(super.getDescrErrore());
	    	   if(super.getEsito().equals("OK")) {
	    		  this.esitoXml = super.getNodeText("XmlEsitoEsistenzaProtocollo");
	    		  //DA VERIFICARE COME IMPLEMENTARE
//	    		  XmlISEEParser iseeParser = new XmlISEEParser(this.esitoXml, true);
//	    		  esito.setDichiarazione(iseeParser.estraiDichiarazione());
	    		}
	    	   }
	    	   catch(EccezioneClient exClient) {
	    		   esito.setEccezioneClient(exClient);
	    	   }
	    	   return esito;
	    }
	    protected String getNodeText(String tagName){
	    	 SOAPBody bodyResponse;
	    	 String result = "";
			try {
				bodyResponse = soapResponse.getSOAPBody();
				NodeList listaEsito = bodyResponse.getElementsByTagName("Esito");
		        org.w3c.dom.Node esitoNode = listaEsito.item(0);
		        result = esitoNode.getTextContent();
			} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return result; 
	    }

	    protected  String getXmlObjectConsultaAttestazione(String cf, String dataValidita, String prestazioneDaErogare, String protocolloDomandaEnteErogatore, String statoDomandaPrestazione) {
			
			String xmlAsString = "";
			
			
			xmlAsString = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			xmlAsString += "			<s:Header/>";
			xmlAsString += "                  <s:Body>";
			xmlAsString += "                          <ConsultazioneAttestazione xmlns=\"http://soa.inps.it/wsiseepdd/consultazioneattestazionerichiesta.xsd\">";
			xmlAsString += "		                          <SicurezzaIdentificazioneMittente>";
			xmlAsString += "							    <CodiceEnte>" + datiRichiesta.getMittente().getCodiceEnte()  + "</CodiceEnte>";
			xmlAsString += "							    <CodiceUfficio>" + datiRichiesta.getMittente().getCodiceUfficio()  + "</CodiceUfficio>";
			xmlAsString += "							    <CFOperatore>" + datiRichiesta.getMittente().getCfOperatore()  + "</CFOperatore>";
			xmlAsString += "						    </SicurezzaIdentificazioneMittente>";
			xmlAsString += " 						     <request>";
			xmlAsString += "							       <Attestazione>";
			xmlAsString += "								       <RicercaCF>";
			xmlAsString += "      									<CodiceFiscale>" + cf + "</CodiceFiscale>";
			xmlAsString += "                                                      <DataValidita>" +  dataValidita + "</DataValidita>";
			xmlAsString += "                                                     <PrestazioneDaErogare>" + prestazioneDaErogare + "</PrestazioneDaErogare>";
			xmlAsString += "                                                      <ProtocolloDomandaEnteErogatore>" + protocolloDomandaEnteErogatore + "</ProtocolloDomandaEnteErogatore>";
			xmlAsString += "                                                     <StatodomandaPrestazione>" + statoDomandaPrestazione + "</StatodomandaPrestazione>";
			xmlAsString += "                                               </RicercaCF>";
			xmlAsString += "                                       </Attestazione>";
			xmlAsString += "                                 </request>";
			xmlAsString += "                           </ConsultazioneAttestazione>";
			xmlAsString += "                   </s:Body>";
			xmlAsString += "            </s:Envelope>";
			
			return xmlAsString;
		}
	    
 protected  String getXmlObjectConsultaDichiarazione(String cf, String dataValidita, String prestazioneDaErogare, String protocolloDomandaEnteErogatore, String statoDomandaPrestazione) {
			
			String xmlAsString = "";
			
			
			xmlAsString = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">";
			xmlAsString += "			<s:Header/>";
			xmlAsString += "                  <s:Body>";
			xmlAsString += "                          <ConsultazioneDichiarazione xmlns=\"http://soa.inps.it/wsiseepdd/consultazionedichiarazionerichiesta.xsd\">";
			xmlAsString += "		                          <SicurezzaIdentificazioneMittente>";
			xmlAsString += "							    <CodiceEnte>" + datiRichiesta.getMittente().getCodiceEnte()  + "</CodiceEnte>";
			xmlAsString += "							    <CodiceUfficio>" + datiRichiesta.getMittente().getCodiceUfficio()  + "</CodiceUfficio>";
			xmlAsString += "							    <CFOperatore>" + datiRichiesta.getMittente().getCfOperatore()  + "</CFOperatore>";
			xmlAsString += "						    </SicurezzaIdentificazioneMittente>";
			xmlAsString += " 						     <request>";
			xmlAsString += "							       <Dichiarazione>";
			xmlAsString += "								       <RicercaCF>";
			xmlAsString += "      									<CodiceFiscale>" + cf + "</CodiceFiscale>";
			xmlAsString += "                                                      <DataValidita>" +  dataValidita + "</DataValidita>";
			xmlAsString += "                                                     <PrestazioneDaErogare>" + prestazioneDaErogare + "</PrestazioneDaErogare>";
			xmlAsString += "                                                      <ProtocolloDomandaEnteErogatore>" + protocolloDomandaEnteErogatore + "</ProtocolloDomandaEnteErogatore>";
			xmlAsString += "                                                     <StatodomandaPrestazione>" + statoDomandaPrestazione + "</StatodomandaPrestazione>";
			xmlAsString += "                                               </RicercaCF>";
			xmlAsString += "                                       </Dichiarazione>";
			xmlAsString += "                                 </request>";
			xmlAsString += "                           </ConsultazioneDichiarazione>";
			xmlAsString += "                   </s:Body>";
			xmlAsString += "            </s:Envelope>";
			
			return xmlAsString;
		}

 
 protected  String getXmlObjectVerificaEsistenzaProtocollo(String cf, String protocolloDSU) {
		
		String xmlAsString = "";
		/***
		 *    <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
                 <s:Header/>
                  <s:Body>
      <VerificaEsistenzaProtocollo xmlns="http://soa.inps.it/wsiseepdd/VerificaEsistenzaProtocolloRichiesta.xsd">
        <SicurezzaIdentificazioneMittente>
		    <CodiceEnte>DSU00081</CodiceEnte>
		    <CodiceUfficio>DirStud</CodiceUfficio>
		    <CFOperatore>CCCRRT57P09L500V</CFOperatore>
	    </SicurezzaIdentificazioneMittente>
         <!--Optional:-->
         <request>
            <CodiceFiscaleDichiarante>LVNDVD00D23I608Q</CodiceFiscaleDichiarante>
            <ProtocolloDsu>INPS-ISEE-2020-00597381W-00</ProtocolloDsu>
         </request>
      </VerificaEsistenzaProtocollo>
   </s:Body>
</s:Envelope>
		 */
		
		xmlAsString = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xmlAsString += "			<s:Header/>";
		xmlAsString += "                  <s:Body>";
		xmlAsString += "                           <VerificaEsistenzaProtocollo xmlns=\"http://soa.inps.it/wsiseepdd/VerificaEsistenzaProtocolloRichiesta.xsd\">";
		xmlAsString += "		                          <SicurezzaIdentificazioneMittente>";
		xmlAsString += "							    <CodiceEnte>" + datiRichiesta.getMittente().getCodiceEnte()  + "</CodiceEnte>";
		xmlAsString += "							    <CodiceUfficio>" + datiRichiesta.getMittente().getCodiceUfficio()  + "</CodiceUfficio>";
		xmlAsString += "							    <CFOperatore>" + datiRichiesta.getMittente().getCfOperatore()  + "</CFOperatore>";
		xmlAsString += "						    </SicurezzaIdentificazioneMittente>";
		xmlAsString += " 						     <request>";
		
		xmlAsString += "      									<CodiceFiscaleDichiarante>" + cf + "</CodiceFiscaleDichiarante>";
		xmlAsString += "                                        <ProtocolloDsu>" +  protocolloDSU + "</ProtocolloDsu>";
    	xmlAsString += "                            </request>";
		xmlAsString += "                           </VerificaEsistenzaProtocollo>";
		xmlAsString += "                   </s:Body>";
		xmlAsString += "            </s:Envelope>";
		
		return xmlAsString;
	  }

	}
 

package it.webred.siso.ws.client.anag.marche.client;

import it.finmatica.www.sa4hl7.Sa4Hl7ServiceStub;
import it.finmatica.www.schema.hl7.RequestDocument;
import it.finmatica.www.schema.hl7.RequestDocument.Request;
import it.finmatica.www.schema.hl7.ResponseDocument;
import it.finmatica.www.schema.hl7.ResponseDocument.Response;
import it.webred.siso.ws.client.agag.marche.dto.RicercaAnagraficaDTO;
import it.webred.siso.ws.client.anag.marche.xml.XmlAssistitiTransform;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.java.security.SSLProtocolSocketFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;

public class RicercaAnagraficaClient {

	private  String msg = null;
	private Integer codice = 0;
	private Exception eccezione;
	private String percorsoJks;
	private String passwordJks;
	public RicercaAnagraficaClient(String percorsoJks, String passwordJks){
		this.percorsoJks = percorsoJks;
		this.passwordJks = passwordJks;
	}
	
	private void configureServiceClient(Sa4Hl7ServiceStub stub) {

		ServiceClient client = stub._getServiceClient();
	    SSLContext ctx;
	    try {
	      KeyStore truststore = KeyStore.getInstance("JKS");

	      FileInputStream in = new FileInputStream(percorsoJks);
	      truststore.load(in,passwordJks.toCharArray());
	      
	      ctx = SSLContext.getInstance("SSL");
	      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	      tmf.init(truststore);
	      ctx.init(null, tmf.getTrustManagers(), null);
	    

	     SSLProtocolSocketFactory sslFactory = new SSLProtocolSocketFactory(ctx);
 
	    Protocol prot = new  Protocol("https",(ProtocolSocketFactory)sslFactory, 443);
		  client.getOptions().setProperty(HTTPConstants.CUSTOM_PROTOCOL_HANDLER,
	        prot);
	    } catch (Exception e) {
		      e.printStackTrace();
		      
	    }
	}	
	
 
 private XmlString componiRicercaPerCF(String param_CF){
		
	   XmlOptions options = new XmlOptions();
		 options.setSaveCDataLengthThreshold( 1 );
		 org.apache.xmlbeans.XmlString message = null;
		//MESSAGGIO PER CODICE FISCALE 
		message = org.apache.xmlbeans.XmlString.Factory.newInstance(options);
		message.setStringValue("<AssistitoRequest versione=\"1\"><CodiceFiscale>" +param_CF +"</CodiceFiscale></AssistitoRequest>");
		return message;
 }
 
 private  String xmlSingleQuotedEscape(String s) {
     StringBuilder sb = new StringBuilder();
     for (int i = 0; i < s.length(); i++) {
       char c = s.charAt(i);
       switch (c) {
         case '\'': sb.append("&quot;"); break;
         case '&': sb.append("&amp;"); break;
         case '<': sb.append("&lt;"); break;
         case '\n': sb.append("&#xA;"); break;

         case '\000': case '\001': case '\002': case '\003': case '\004':
         case '\005': case '\006': case '\007': case '\010': case '\013':
         case '\014': case '\016': case '\017': case '\020': case '\021':
         case '\022': case '\023': case '\024': case '\025': case '\026':
         case '\027': case '\030': case '\031': case '\032': case '\033':
         case '\034': case '\035': case '\036': case '\037':
           // do nothing, these are disallowed characters
           break;
         default:   sb.append(c);
       }
     }
     return sb.toString();
   }
 
 private XmlString componiRicercaPerDatiAnag(String nome, String cognome, String annoNascita, String sesso){
		
	   XmlOptions options = new XmlOptions();
		 options.setSaveCDataLengthThreshold( 1 );
		 org.apache.xmlbeans.XmlString message = null;
		 StringBuilder sb = new StringBuilder();
		 sb.append("<AssistitiElencoRequest versione=\"1\">");
		 if(cognome != null && !cognome.equals(""))
			 sb.append("<Cognome>" + xmlSingleQuotedEscape(cognome) + "</Cognome>");
		 if(nome != null && !nome.equals(""))
			 sb.append("<Nome>" +xmlSingleQuotedEscape( nome) + "</Nome>");
		 if(annoNascita != null && !annoNascita.equals(""))
			 sb.append("<NascitaAnno>" + annoNascita + "</NascitaAnno>");
		
		 if(sesso != null && !sesso.equals(""))
			 sb.append("<Sesso>" + sesso + "</Sesso>");
		 sb.append("</AssistitiElencoRequest>");
		 //MESSAGGIO PER COGNOME E NOME 
		message = org.apache.xmlbeans.XmlString.Factory.newInstance(options);
		message.setStringValue(sb.toString());
		return message;
}
 private XmlString componiRicercaPerFiltro(String filtro){
		
	   XmlOptions options = new XmlOptions();
		 options.setSaveCDataLengthThreshold( 1 );
		 org.apache.xmlbeans.XmlString message = null;
		 StringBuilder sb = new StringBuilder();
		 sb.append("<AssistitiElencoRequest versione=\"1\">");
		 sb.append("<Filtro>" + xmlSingleQuotedEscape(filtro) + "</Filtro>");
		  
		 sb.append("</AssistitiElencoRequest>");
		 //MESSAGGIO PER COGNOME E NOME 
		message = org.apache.xmlbeans.XmlString.Factory.newInstance(options);
		message.setStringValue(sb.toString());
		return message;
}	
 private XmlString componiRicercaPerAssistitoId(String assistitoId){
		
	   XmlOptions options = new XmlOptions();
		 options.setSaveCDataLengthThreshold( 1 );
		 org.apache.xmlbeans.XmlString message = null;
		 	//MESSAGGIO PER COGNOME E NOME 
		message = org.apache.xmlbeans.XmlString.Factory.newInstance(options);
		message.setStringValue("<AssistitoRequest versione=\"1\"><AssistitoId>" + assistitoId + "</AssistitoId></AssistitoRequest>");
		
		return message;
}
 private XmlString ricercaAnagrafica(org.apache.xmlbeans.XmlString message) throws IOException, Exception{
	 
	 Exception eccezione = null;
	 Integer MAX_ITERATOR = 4;
	 boolean done = false;
	 while (!done && MAX_ITERATOR > -1) {
		     try {
		    	 Sa4Hl7ServiceStub stub = new  Sa4Hl7ServiceStub();
		      	this.configureServiceClient(stub);
		 		RequestDocument request0 =   RequestDocument.Factory.newInstance();
		 		Request re = request0.addNewRequest();
		 	 	re.xsetMessage(message);
		 	 	re.setCreator("APCV3-SER-SOC");
		 		ResponseDocument rd =	stub.sa4Hl7Receive(request0);
		 		Response res = rd.getResponse();
		 		String Msg = res.getMessage();
		 		
	//	 		System.out.println("**********	RICHIESTA	*****************");
	//	 		System.out.println(request0.xmlText());
	//	 		System.out.println("**********	 			*****************");
	//	 		
	//	 		System.out.println("**********	INIZIO XML RISPOSTA	*****************");
	//	 		System.out.println(res.xmlText());
	//	 		System.out.println("**********	FINE XML RISPOSTA		*****************");
	//	 		
	//	 		System.out.println("**********	INIZIO Messaggio RISPOSTA	*****************");
	//	 		System.out.println(Msg);
	//	 		System.out.println("**********	FINE Messaggio RISPOSTA		*****************");
		 		
		 		
		         done = true;
		         return res.xgetMessage();
		     } 
		     catch (Exception ex) {
		    	 MAX_ITERATOR--;
		    	 eccezione = ex;
		    	 Thread.sleep(500);  
		     }
		     if(!done){
		    	 this.codice = -99;
		    	 this.msg = "Non è stato possibile completare l'estrazione del dettaglio dell'assistito"; 
		    	 this.eccezione = eccezione;
		     }
	 }
	  return null;
	}
 
    private PersonaResult eseguiRicercaPerCF(String cf) throws IOException, Exception {
    	
    	XmlString message =  this.componiRicercaPerCF(cf);
    	XmlString result = ricercaAnagrafica(message);
    	//DEBUG
//    	XmlString result = XmlString.Factory.parse(new File("C:\\progetti\\SISO\\ANAGRAFE_MARCHE_SISO-927\\ANAGRAFE_RispostaDettaglioSoggetto1.xml"));
		XmlAssistitiTransform xmlTransform = new XmlAssistitiTransform();
		PersonaResult pr = xmlTransform.elaboraXML(result);
		this.codice = xmlTransform.getCodice();
		this.msg = xmlTransform.getMessaggio();
		return pr;
    }
    
    private ArrayList<PersonaResult> iterRicercaPersona(List<String> listaAssistitiID) throws Exception{
    
    	ArrayList<PersonaResult> listaPersoneResult = new ArrayList<PersonaResult>();
    	 for(String s : listaAssistitiID){
			XmlString messageAssID =  this.componiRicercaPerAssistitoId(s);
	    	XmlString resultAssID = ricercaAnagrafica(messageAssID);
	    	if(resultAssID != null){
	    		XmlAssistitiTransform xmlTransformAssID = new XmlAssistitiTransform();
				PersonaResult personaResult =	xmlTransformAssID.elaboraXML(resultAssID);
				listaPersoneResult.add(personaResult);
	    	}
	    	
		}
    	 return listaPersoneResult;
    }
private ArrayList<PersonaResult> eseguiRicercaPerDatiAnagrafici(String nome, String cognome, String annoNascita, String sesso) throws IOException, Exception {
    	
		XmlString message =  this.componiRicercaPerDatiAnag(nome, cognome, annoNascita, sesso);
		XmlString result = ricercaAnagrafica(message);
	
		//DEBUG
		//XmlString result = XmlString.Factory.parse(new File("C:\\progetti\\SISO\\ANAGRAFE_MARCHE_SISO-927\\ANAGRAFE_RispostaEsitoPositivoCognomeNome.xml"));
		XmlAssistitiTransform xmlTransform = new XmlAssistitiTransform();
		List<String> listaAssistitiID =	xmlTransform.elaboraXMLElenco(result);
		this.codice = xmlTransform.getCodice();
		this.msg = xmlTransform.getMessaggio();
		if(codice == 0){
			return iterRicercaPersona(listaAssistitiID);
		}
		
		return null;
    }

private List<PersonaResult> eseguiRicercaPerIdentificativo(String identificativo) throws IOException, Exception {
	XmlString messageAssID =  this.componiRicercaPerAssistitoId(identificativo);
	XmlString resultAssID = ricercaAnagrafica(messageAssID);
	List<PersonaResult> res = null;
	
	if(resultAssID != null){
		XmlAssistitiTransform xmlTransform = new XmlAssistitiTransform();
		PersonaResult personaResult =	xmlTransform.elaboraXML(resultAssID);
		this.codice = xmlTransform.getCodice();
		this.msg = xmlTransform.getMessaggio();
		res = new ArrayList<PersonaResult>();
		res.add(personaResult);
	}
	return res;
}

private ArrayList<PersonaResult> eseguiRicercaPerDatiAnagrafici(String filtro) throws IOException, Exception {
	
	XmlString message =  this.componiRicercaPerFiltro(filtro);
	XmlString result = ricercaAnagrafica(message);

	//DEBUG
	//XmlString result = XmlString.Factory.parse(new File("C:\\progetti\\SISO\\ANAGRAFE_MARCHE_SISO-927\\ANAGRAFE_RispostaEsitoPositivoCognomeNome.xml"));
	XmlAssistitiTransform xmlTransform = new XmlAssistitiTransform();
	List<String> listaAssistitiID =	xmlTransform.elaboraXMLElenco(result);
	this.codice = xmlTransform.getCodice();
	this.msg = xmlTransform.getMessaggio();
	if(codice == 0){
		return iterRicercaPersona(listaAssistitiID);
	}
	
	return null;
}

public RicercaPersonaResult ricercaPerDatiAnagrafici(RicercaAnagraficaDTO ricercaDTO){
		 
		 System.out.println("ricercaPerDatiAnagrafici:"+ricercaDTO.stampaParametri()); 
		 RicercaPersonaResult personaResult = new RicercaPersonaResult();
		
		 if(ricercaDTO == null){
			 msg = "Oggetto ricercaDTO Assente.";
			 codice = -10;
		 }
		
		 boolean noDatiAnagrafici = (ricercaDTO.getCognome() == null || ricercaDTO.getCognome().equals(""))
				 && (ricercaDTO.getNome()  == null || ricercaDTO.getNome().equals(""))
				 	&& (ricercaDTO.getCf() == null || ricercaDTO.getCf().equals(""));
		 
		 boolean noIdentificativo = ricercaDTO.getIdAssistito()==null || ricercaDTO.getIdAssistito().trim().isEmpty();
		 
		 if(noIdentificativo && noDatiAnagrafici){
			 msg = "Parametri di ricerca assenti.";
			 codice = -11;
		 }
		 try{
			 if(ricercaDTO.getCf() != null && !ricercaDTO.getCf().equals("")){
				 personaResult.addAssistito(eseguiRicercaPerCF(ricercaDTO.getCf()));
				 
			 }
			 else if(ricercaDTO.getFiltro() != null){
				 personaResult.setElencoAssisiti((this.eseguiRicercaPerDatiAnagrafici(ricercaDTO.getFiltro())));
			 }else if(ricercaDTO.getIdAssistito() != null){
				 personaResult.setElencoAssisiti(this.eseguiRicercaPerIdentificativo(ricercaDTO.getIdAssistito()));
			 }else if(ricercaDTO.getCognome() != null){
				 personaResult.setElencoAssisiti((this.eseguiRicercaPerDatiAnagrafici(ricercaDTO.getNome(), ricercaDTO.getCognome(), ricercaDTO.getAnnoNascita(), ricercaDTO.getSesso())));
			 }
		 }catch(Exception e){
			this.codice = -12;
			this.msg = "attenzione si è verificato un errore durante la ricerca";
			this.eccezione = e;
		 }
		 personaResult.setCodice(codice);
		 personaResult.setMessaggio(msg);
		 personaResult.setEccezione(eccezione);
		 return personaResult;
	 }
 
}

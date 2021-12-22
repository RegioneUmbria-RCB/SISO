package it.roma.comune.servizi.client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;

import it.roma.comune.servizi.config.ConfigClass;
import it.roma.comune.servizi.dto.Componente;
import it.roma.comune.servizi.dto.DatiAnagrafeRoma;
import it.roma.comune.servizi.dto.DatiIndirizzo;
import it.roma.comune.servizi.dto.Famiglia;
//import it.roma.comune.servizi.dto.Famiglia;
import it.roma.comune.servizi.dto.Genitori;
import it.roma.comune.servizi.dto.Nascita;
import it.roma.comune.servizi.dto.Persona;
import it.roma.comune.servizi.dto.PersonaCompleta;
import it.roma.comune.servizi.dto.RicercaResult;
import it.roma.comune.servizi.test.TestMain;
import it.roma.comune.servizi.test.TestVerificaAnagrafica;
import it.roma.comune.servizi.verificheanagrafiche.LogHeader;
import it.roma.comune.servizi.verificheanagrafiche.NVALocator;
import it.roma.comune.servizi.verificheanagrafiche.NVASoap;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesa;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponse;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConv;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponse;
import it.roma.comune.servizi.verificheanagrafiche.VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.MessageElement;
import org.apache.axis.types.UnsignedByte;
import org.apache.ws.security.util.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.EntityReference;
 
//@WebService(targetNamespace = "http://client.servizi.comune.roma.it/", portName = "RicercaAnagraficaClientPort", serviceName = "RicercaAnagraficaClientService")
public class RicercaAnagraficaClient {
	
	private EngineConfiguration config = new FileProvider("it/roma/comune/servizi/config/client-deploy.wsdd");
	private NVALocator s= new NVALocator(config);
	private NVASoap soap=null;
	private String wsURL;
	private String username;
	private String modDebug = "N0";

	public RicercaAnagraficaClient() {
		modDebug =ConfigClass.getProperty("modDebug");
	}

	private String password;
	private String esito = "OK";
	
	public RicercaAnagraficaClient(String wsURL, String username, String password) {
		this();
		this.wsURL = wsURL;
		this.username = username;
		this.password = password;
		 
	}
	
	//@WebMethod(operationName = "eseguiRicercaAnagraficaCompleta", action = "urn:EseguiRicercaAnagraficaCompleta")
	 public RicercaResult eseguiRicercaAnagraficaCompleta(	String wsURL, 
														String cognome, 
														String nome, 
														String sesso,
														String annoNascita,
														String meseNascita,
														String giornoNascita, 
														String codiceFiscale) throws Exception{
		  RicercaResult rs = new RicercaResult();
		  
			try {
				//soap= s.getNVASoap(new URL("http://10.173.2.133/pa/pri/verifiche/nva.asmx"));
				if(wsURL != null){
					this.wsURL = wsURL;
				}
				soap= s.getNVASoap(new URL(this.wsURL));
				System.out.println(" RicercaAnagraficaClient -> eseguiRicerca: Codice Fiscale [" + (codiceFiscale != null ? codiceFiscale : "") + "]");
				
				 rs.setElencoPersona(this.ricerca(null, cognome, nome, sesso, annoNascita, meseNascita, giornoNascita, codiceFiscale));
				 rs.setEsito(this.esito);
			
			//FINE DEBUG
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return rs;
		}
	//@WebMethod(operationName = "eseguiRicercaAnagrafica", action = "urn:EseguiRicercaAnagrafica")
	public RicercaResult eseguiRicercaAnagrafica(	String wsURL, 
													String cognome, 
													String nome, 
													String sesso,
													String annoNascita,
													String meseNascita,
													String giornoNascita, 
													String codiceFiscale) throws Exception{
	  RicercaResult rs = new RicercaResult();
	  
		try {
			//soap= s.getNVASoap(new URL("http://10.173.2.133/pa/pri/verifiche/nva.asmx"));
			if(wsURL != null){
				this.wsURL = wsURL;
			}
			soap= s.getNVASoap(new URL(this.wsURL));
			System.out.println(" RicercaAnagraficaClient -> eseguiRicerca: Codice Fiscale [" + (codiceFiscale != null ? codiceFiscale : "") + "]");
			
			if(modDebug.equals("SI"))
				 rs.setElencoPersona(doTestDebug());
			else 
			 rs.setElencoPersona(this.ricerca(null, cognome, nome, sesso, annoNascita, meseNascita, giornoNascita, codiceFiscale));
			 rs.setEsito(this.esito);
		
		//FINE DEBUG
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return rs;
	}
	
	    //@WebMethod(operationName = "eseguiRicercaCompleta", action = "urn:EseguiRicercaCompleta")
		public RicercaResult eseguiRicercaCompleta(	String wsURL, 
														String cognome, 
														String nome, 
														String sesso,
														String annoNascita,
														String meseNascita,
														String giornoNascita, 
														String codiceFiscale) throws Exception{
		  RicercaResult rs = new RicercaResult();
		  
			try {
				//soap= s.getNVASoap(new URL("http://10.173.2.133/pa/pri/verifiche/nva.asmx"));
				if(wsURL != null){
					this.wsURL = wsURL;
				}
				soap= s.getNVASoap(new URL(this.wsURL));
				System.out.println(" RicercaAnagraficaClient -> eseguiRicerca: Codice Fiscale [" + (codiceFiscale != null ? codiceFiscale : "") + "]");
				
				 if(modDebug.equals("SI"))
					 rs.setElencoPersona(doTestEstesoDebug());
				else 
					rs.setElencoPersona(this.ricerca(null, cognome, nome, sesso, annoNascita, meseNascita, giornoNascita, codiceFiscale));
				 rs.setEsito(this.esito);
			
			//FINE DEBUG
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return rs;
		}
	//@WebMethod(operationName = "eseguiRicercaAnagraficaEstesa", action = "urn:EseguiRicercaAnagraficaEstesa")
	public  RicercaResult eseguiRicercaAnagraficaEstesa(	String wsURL, String cognome, String nome, String sesso, String annoIniziale,String annoFinale) throws Exception{
		  RicercaResult rs = new RicercaResult();
			
		try {
		//soap= s.getNVASoap(new URL("http://10.173.2.133/pa/pri/verifiche/nva.asmx"));
			if(wsURL != null){
				this.wsURL = wsURL;
			}
			soap= s.getNVASoap(new URL(this.wsURL));
			 
			 if(modDebug.equals("SI"))
				 rs.setElencoPersona(doTestEstesoDebug());
			else 
				 rs.setElencoPersona(ricercaEstesa(cognome, nome, sesso, annoIniziale, annoFinale));
			
			
			rs.setEsito(this.esito);
		//FINE DEBUG
		} catch (ServiceException e) {
			throw e;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		  catch (Exception e) {
			// TODO Auto-generated catch block
			  throw e;
		}
		 return rs;
	}
	
	//@WebMethod(operationName = "ricercaPerCodiceIndividuale", action = "urn:RicercaPerCodiceIndividuale")
	public  RicercaResult ricercaPerCodiceIndividuale(String	wsURL, String codiceIndividuale ) throws Exception{
		  RicercaResult rs = new RicercaResult();
			
		try {
				if(wsURL != null){
					this.wsURL = wsURL;
				}
				soap= s.getNVASoap(new URL(this.wsURL));
		
				 if(modDebug.equals("SI"))
					 rs.setElencoPersona(doTestDebug());
				else
					rs.setElencoPersona(this.ricerca(codiceIndividuale,  
										null, 
										null,
										null,
										null,
										null,
										null, 
										null)); 
				 rs.setEsito(this.esito);
		 
		} catch (ServiceException e) {
				throw e;
		} catch (MalformedURLException e) {
			throw e;
		}
		catch (Exception e) {
			throw e;
		}
		 return rs;
}
	
	//@WebMethod(operationName = "verificaStatoFamigliaConv", action = "urn:VerificaStatoFamigliaConv")
		public  RicercaResult verificaStatoFamigliaConv(String	wsURL, String codiceIndividuale, String codiceFiscale ) throws Exception{
			  RicercaResult rs = new RicercaResult();
				
			try {
					if(wsURL != null){
						this.wsURL = wsURL;
					}
					soap= s.getNVASoap(new URL(this.wsURL));
					 if(modDebug.equals("SI")){
						 rs.setFamiglia(
								  this.ricercaStatoFamigliaConvTEST(codiceIndividuale, codiceFiscale)
								 ); 
						 rs.setEsito(this.esito);
					 }else{
						 rs.setFamiglia(
								  this.ricercaStatoFamigliaConv(codiceIndividuale, codiceFiscale)
								  ); 
						 rs.setEsito(this.esito);
					 }
					
			 
			} catch (ServiceException e) {
				this.esito = "KO";
					throw e;
			} catch (MalformedURLException e) {
				this.esito = "KO";
				throw e;
			}
			catch (Exception e) {
				this.esito = "KO";
				throw e;
			}
			 return rs;
	}
	
	
	private static String getUID(){
		byte[] bytes = new byte[128];
		String temp=String.valueOf(new Random().nextDouble()) + String.valueOf(System.currentTimeMillis());
		java.security.MessageDigest sha=null;
		try {
		sha = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random r= new Random(System.currentTimeMillis());
		r.nextBytes(bytes);
        sha.reset();
        sha.update(bytes);
        return Base64.encode(sha.digest());
	}
	public String getEsito() {
		return esito;
	}
	private void setEsito(String esito) {
		this.esito = esito;
	}
	
	
	
	private   Persona[] ricerca( String codiceIndividuale, String cognome, String nome, String sesso, String annoNascita, String meseNascita,String giornoNascita, String codiceFiscale)throws Exception{
		VerificaAnagrafica va= new VerificaAnagrafica();
		UnsignedByte tipo= null;
		System.out.println(" RicercaAnagraficaClient -> ricerca: Codice Fiscale [" + (codiceFiscale != null ? codiceFiscale : "") + "]");
		
		if(codiceIndividuale != null && !codiceIndividuale.equals("-")){
//			va.setCodiceIndividuale(Integer.parseInt(codiceIndividuale));
			va.setCodiceIndividuale(codiceIndividuale.trim());
			tipo= new UnsignedByte(1);
		}
		else if(codiceFiscale != null && !codiceFiscale.equals("-")){
			va.setCodiceFiscale(codiceFiscale);
			tipo= new UnsignedByte(2);
		}
		else{
			tipo= new UnsignedByte(3);
			
			if(cognome != null && !cognome.equals("-"))
				va.setCognome(cognome);
			if(annoNascita != null && !annoNascita.equals("-"))
				va.setAnnoNascita(Short.parseShort(annoNascita));
			if(meseNascita != null && !meseNascita.equals("-"))
				va.setMeseNascita(  new UnsignedByte(meseNascita) );
			if(giornoNascita != null && !giornoNascita.equals("-"))
				va.setGiornoNascita(  new UnsignedByte(giornoNascita) );
			if(sesso != null && !sesso.equals("-"))
				va.setSesso(sesso);
			if(nome != null && !nome.equals("-"))
				va.setNome(nome);
		}
		
		va.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(this.getUID());
		VerificaAnagraficaResponse resp=null;
		try {
			resp=soap.verificaAnagrafica(va, header);
			
			VerificaAnagraficaResponseVerificaAnagraficaResult r=  resp.getVerificaAnagraficaResult();
			//System.out.println("********** Elemento [VerificaAnagraficaResponseVerificaAnagraficaResult]" + r != null ? "Esiste" : "nullo");	
			//System.out.println("********** Elemento [resp]" + resp != null ? resp.toString() : "nullo");			
			 List<Persona>  arr_persona = new ArrayList<Persona>();
				for(int i=0; i< r.get_any().length; i++ ){
					  NodeList nListM = 	r.get_any()[i].getElementsByTagName("Messaggio");
					 for (int temp = 0; temp < nListM.getLength(); temp++) {
						 //System.out.println("********** Elemento Messaggio : " + nListM.item(temp).toString());
							
						 esito = elaboraXMLMessaggio(nListM.item(temp));
	 
	 				}
					 
					 NodeList nList = 	r.get_any()[i].getElementsByTagName("Persona");
						
	 				 for (int temp = 0; temp < nList.getLength(); temp++) {
	 					 arr_persona.add(elaboraXML(nList.item(temp)));
	 
	 					}
				  }
				
				//System.out.println("********** Numero di persone individuate : " + arr_persona.size());
				 Persona[] persone = new Persona[arr_persona.size()];
				 return  arr_persona.toArray(persone);
				 
				 
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		 
	}
	private  Famiglia ricercaStatoFamigliaConvTEST( String codiceIndividuale,  String codiceFiscale)throws Exception{
		
		Famiglia famiglia = null;
		
		 
		try {
			 
			//MessageElement[] me =  convertXMLStringtoMessageElement("C:\\progetti\\SISO\\ANAGRAFE_ROMA\\EsempixSISO\\Esempi x SISO\\NETVFC (Verifica StatoFamigliaConvivenza).xml");
			MessageElement[] me =  convertXMLStringtoMessageElement("VerificaStatoFamigliaConvivenza.xml");
			
			List<Componente>  arr_persona = new ArrayList<Componente>();
			for(int i=0; i< me.length; i++ ){
				  
				NodeList nListM = 	me[i].getElementsByTagName("Famiglia");
					 for (int temp = 0; temp < nListM.getLength(); temp++) {
						 //System.out.println("********** Elemento Messaggio : " + nListM.item(temp).toString());
							
						 famiglia = elaboraXMLFamiglia(nListM.item(temp));
	 
	 				}
				 
				
			  }
				
 			 
				 
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		 return famiglia;
	}
	
	private  Famiglia ricercaStatoFamigliaConv( String codiceIndividuale,  String codiceFiscale)throws Exception{
		
		Famiglia famiglia = null;
		
		VerificaStatoFamigliaConv vsfc= new VerificaStatoFamigliaConv();
		UnsignedByte tipo= null;
		System.out.println(" RicercaAnagraficaClient -> ricercaStatoFamigliaConv: Codice Fiscale/codiceIndividuale [" + (codiceFiscale != null ? codiceFiscale : "") + "/"  + (codiceIndividuale != null ? codiceIndividuale : "") + "]");
		
		if(codiceIndividuale != null && !codiceIndividuale.equals("-")){
			vsfc.setCodiceIndividuale(codiceIndividuale);
			tipo= new UnsignedByte(1);
		}
		else if(codiceFiscale != null && !codiceFiscale.equals("-")){
			vsfc.setCodiceFiscale(codiceFiscale);
			tipo= new UnsignedByte(2);
		}
		 
		vsfc.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(this.getUID());
		VerificaStatoFamigliaConvResponse resp=null;
		try {
			resp=soap.verificaStatoFamigliaConv(vsfc, header);
			
			VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult r=  resp.getVerificaStatoFamigliaConvResult();

			List<Componente>  arr_persona = new ArrayList<Componente>();
			for(int i=0; i< r.get_any().length; i++ ){
				  
				NodeList nListM = 	r.get_any()[i].getElementsByTagName("Famiglia");
					 for (int temp = 0; temp < nListM.getLength(); temp++) {
						 //System.out.println("********** Elemento Messaggio : " + nListM.item(temp).toString());
							
						 famiglia = elaboraXMLFamiglia(nListM.item(temp));
	 
	 				}
				 
				
			  }
				
 			 
				 
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		 return famiglia;
	}

	private   Persona[] verifica( String codiceIndividuale, String cognome, String nome, String sesso, String annoNascita, String meseNascita,String giornoNascita, String codiceFiscale)throws Exception{
		VerificaStatoFamigliaConv va= new VerificaStatoFamigliaConv();
		UnsignedByte tipo= null;
		if(codiceIndividuale != null && !codiceIndividuale.equals("-")){
			va.setCodiceIndividuale(codiceIndividuale);
			tipo= new UnsignedByte(1);
		}
		else if(codiceFiscale != null && !codiceFiscale.equals("-")){
			va.setCodiceFiscale(codiceFiscale);
			tipo= new UnsignedByte(2);
		}
		else{
			tipo= new UnsignedByte(3);
			
			if(cognome != null && !cognome.equals("-"))
				va.setCognome(cognome);
			if(annoNascita != null && !annoNascita.equals("-"))
				va.setAnnoNascita(Short.parseShort(annoNascita));
			if(meseNascita != null && !meseNascita.equals("-"))
				va.setMeseNascita(  new UnsignedByte(meseNascita) );
			if(giornoNascita != null && !giornoNascita.equals("-"))
				va.setGiornoNascita(  new UnsignedByte(giornoNascita) );
			if(sesso != null && !sesso.equals("-"))
				va.setSesso(sesso);
			if(nome != null && !nome.equals("-"))
				va.setNome(nome);
		}
		
		va.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(this.getUID());
		VerificaStatoFamigliaConvResponse resp=null;
		try {
			resp=soap.verificaStatoFamigliaConv(va, header);
			
			VerificaStatoFamigliaConvResponseVerificaStatoFamigliaConvResult r=  resp.getVerificaStatoFamigliaConvResult(); 
			//System.out.println("********** Elemento [VerificaAnagraficaResponseVerificaAnagraficaResult]" + r != null ? "Esiste" : "nullo");	
			System.out.println("********** Elemento [resp]" + resp != null ? resp.toString() : "nullo");			
//			 List<Persona>  arr_persona = new ArrayList<Persona>();
//				for(int i=0; i< r.get_any().length; i++ ){
//					  NodeList nListM = 	r.get_any()[i].getElementsByTagName("Messaggio");
//					 for (int temp = 0; temp < nListM.getLength(); temp++) {
//						 //System.out.println("********** Elemento Messaggio : " + nListM.item(temp).toString());
//							
//						 esito = elaboraXMLMessaggio(nListM.item(temp));
//	 
//	 				}
//					 
//					 NodeList nList = 	r.get_any()[i].getElementsByTagName("Persona");
//						
//	 				 for (int temp = 0; temp < nList.getLength(); temp++) {
//	 					 arr_persona.add(elaboraXML(nList.item(temp)));
//	 
//	 					}
//				  }
//				
//				//System.out.println("********** Numero di persone individuate : " + arr_persona.size());
//				 Persona[] persone = new Persona[arr_persona.size()];
//				 return  arr_persona.toArray(persone);
				 return null;
				 
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			throw  e;
		}
		 
	}
	
	private   Persona[] ricercaEstesa(String cognome,  String nome, String sesso, String annoIniziale,String  annoFinale )throws Exception{
		VerificaAnagraficaEstesa vae= new VerificaAnagraficaEstesa();
		 
			 	
			if(cognome != null && !cognome.equals("-"))
				vae.setCognome(cognome);
			if(annoIniziale != null && !annoIniziale.equals("-"))
				vae.setAnnoIniziale( Short.parseShort(annoIniziale));
			if(annoFinale != null && !annoFinale.equals("-"))
				vae.setAnnoFinale(Short.parseShort(annoFinale) );
			 
			if(sesso != null && !sesso.equals("-"))
				vae.setSesso(sesso);
			if(nome != null && !nome.equals("-"))
				vae.setNome(nome);
	 
		
	 	LogHeader header= new LogHeader();
		
		header.setLogGuid(this.getUID());
		VerificaAnagraficaEstesaResponse resp=null;
		try {
			resp=soap.verificaAnagraficaEstesa(vae, header);
			//System.out.println("********** Elemento [resp]" + resp != null ? resp.toString() : "nullo");			
		
			VerificaAnagraficaEstesaResponseVerificaAnagraficaEstesaResult r=  resp.getVerificaAnagraficaEstesaResult();
			//System.out.println("********** Elemento [VerificaAnagraficaResponseVerificaAnagraficaResult]" + r != null ? "Esiste" : "nullo");	
			
			 List<Persona>  arr_persona = new ArrayList<Persona>();
				 
			  for(int i=0; i< r.get_any().length; i++ ){
				  
				   
					 NodeList nListM = 	r.get_any()[i].getElementsByTagName("Messaggio");
					 for (int temp = 0; temp < nListM.getLength(); temp++) {
						 
						 //System.out.println("**********Ricerca Base Elemento Messaggio : " +nListM.item(temp).toString());
						 esito = elaboraXMLMessaggio(nListM.item(temp));
	 
	 				}
					 
					 NodeList nList = 	r.get_any()[i].getElementsByTagName("Persona");
						
	 				 for (int temp = 0; temp < nList.getLength(); temp++) {
	 					 arr_persona.add(elaboraXML(nList.item(temp)));
	 
	 					}
	 				 NodeList nListPE = r.get_any()[i].getElementsByTagName("PersonaElenco");
					 for (int temp = 0; temp < nListPE.getLength(); temp++) {
	 					 arr_persona.add(elaboraPersonaElencoXML(nListPE.item(temp)));
	 
	 					}
				  }
				//System.out.println("********** Numero di persone individuate ESTESA : " + arr_persona.size());
				
				 Persona[] persone = new Persona[arr_persona.size()];
				 return  arr_persona.toArray(persone);
				 
				 
		  } catch (Exception e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
			throw  e;
		}
		 
	} 
	
	private Persona[] doTestDebug() throws Exception{
		
		try{
			//MessageElement[] me =  convertXMLStringtoMessageElement("C:\\progetti\\SISO\\ANAGRAFE_ROMA\\EsempixSISO\\Esempi x SISO\\NETVA(Verifica Anagrafica).xml");
			MessageElement[] me =  convertXMLStringtoMessageElement("PersonaAIRE.xml");
			
			List<Persona>  arr_persona = new ArrayList<Persona>();
			for(int i=0; i< me.length; i++ ){
				 NodeList nList = 	me[i].getElementsByTagName("Persona");
					
 				 for (int temp = 0; temp < nList.getLength(); temp++) {
 					 arr_persona.add(elaboraXML(nList.item(i)));
 
 					}
			  }
				 Persona[] persone = new Persona[arr_persona.size()];
				 return  arr_persona.toArray(persone);
			}
			catch(Exception ex){
				 throw ex;
			}
		 
	}
 	
	private  Persona[] doTestEstesoDebug() throws Exception{
		
		try{
			//MessageElement[] me =  convertXMLStringtoMessageElement("C:\\progetti\\SISO\\ANAGRAFE_ROMA\\EsempixSISO\\Esempi x SISO\\NETVA(Verifica Anagrafica).xml");
			MessageElement[] me =  convertXMLStringtoMessageElement("ElencoEstesoPersone.xml");
			
			List<Persona>  arr_persona = new ArrayList<Persona>();
			
			
			for(int i=0; i< me.length; i++ ){
				
				 NodeList nListM = me[i].getElementsByTagName("Messaggio");
				 for (int temp = 0; temp < nListM.getLength(); temp++) {
					 esito = elaboraXMLMessaggio(nListM.item(temp));
 
 				}
				 NodeList nListPE = 	me[i].getElementsByTagName("PersonaElenco");
				 for (int temp = 0; temp < nListPE.getLength(); temp++) {
 					 arr_persona.add(elaboraPersonaElencoXML(nListPE.item(temp)));
 
 					}
				NodeList nList = 	me[i].getElementsByTagName("Persona");
				 for (int temp = 0; temp < nList.getLength(); temp++) {
 					 arr_persona.add(elaboraXML(nList.item(temp)));
 
 					}
			  }
			 Persona[] persone = new Persona[arr_persona.size()];
			 return  arr_persona.toArray(persone);
			}
			catch(Exception ex){
				 throw ex;
			}
		 
	}

	 
	
	private  MessageElement[] convertXMLStringtoMessageElement(String absXmlFile) throws SAXException, IOException, ParserConfigurationException{
	       
		//File file = new File(absXmlFile);
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(absXmlFile).getFile());
		MessageElement[] m = new MessageElement[1];
        Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element element = XMLDoc.getDocumentElement();
        m[0] = new MessageElement(element);
        return m;
    }
	private  Persona elaboraXML(Node node) {
	    // do something with the current node instead of System.out
	    //System.out.println(node.getNodeName());
		Persona persona = new Persona();
		
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	if(currentNode.getNodeName().equals("Nascita")){
	        		persona.setDatiDiNascita(this.getNascitaFromXml(currentNode));
	        	}
	        	if(currentNode.getNodeName().equals("PersonaCompleta")){
	        		persona.setPersonaCompleta(this.getPersonaFromXml(currentNode)); 
	        	}
	        	if(currentNode.getNodeName().equals("DatiIndirizzo")){
	        		persona.setDatiIndirizzo(this.getDatiIndirizzoFromXml(currentNode)); 
	        	}
	        	if(currentNode.getNodeName().equals("DatiAnagrafeRoma")){
	        		persona.setDatiAnagrafeRoma(this.getDatiAnagrafeRomaFromXml(currentNode)); 
	        	}
 
	        	elaboraXML(currentNode);
	        }
	    }
	    return persona;
	}
	private  Famiglia elaboraXMLFamiglia(Node node) {
	    // do something with the current node instead of System.out
	    //System.out.println(node.getNodeName());
		Famiglia famiglia = new Famiglia();
		List<Componente>  arr_persona = new ArrayList<Componente>();
		
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	if(currentNode.getNodeName().equals("FlagFamigliaConvivenza")){
	        		famiglia.setFlagFamigliaConvivenza(currentNode.getFirstChild().getNodeValue());
	    	    }
	        	if(currentNode.getNodeName().equals("DatiIndirizzo")){
	        		famiglia.setDatiIndirizzo(this.getDatiIndirizzoFromXml(currentNode)); 
	        	}
	        	if(currentNode.getNodeName().equals("CodiceFamiglia")){
	        		famiglia.setCodiceFamiglia(currentNode.getFirstChild().getNodeValue());
	        	}
	        	if(currentNode.getNodeName().equals("Componente")){ 
	        		 arr_persona.add(elaboraXMLComponente(currentNode));
	        	}
	        	
	        	//elaboraXMLFamiglia(currentNode);
	        }
	        
	    }
	     
	    Componente[] componenti = new Componente[arr_persona.size()];
	    componenti =  arr_persona.toArray(componenti);
	    famiglia.setComponenti(componenti);
	   
	    return famiglia;
	}
	private  Componente elaboraXMLComponente(Node node) {
	    // do something with the current node instead of System.out
	    //System.out.println(node.getNodeName());
		Componente componente = new Componente();
		
	    NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	            //calls this method for all the children which is Element
	        	if(currentNode.getNodeName().equals("Nascita")){
	        		componente.setNascita(this.getNascitaFromXml(currentNode));
	        	}
	        	if(currentNode.getNodeName().equals("PersonaCompleta")){
	        		componente.setPersonaCompleta(this.getPersonaFromXml(currentNode)); 
	        	}
	        	if(currentNode.getNodeName().equals("RapportoParentela")){
	        		componente.setRapportoParentela(currentNode.getFirstChild().getNodeValue());
	        	}
	        	if(currentNode.getNodeName().equals("DatiAnagrafeRoma")){
	        		componente.setDatiAnagrafeRoma(this.getDatiAnagrafeRomaFromXml(currentNode)); 
	        	}
 
	        	elaboraXMLComponente(currentNode);
	        }
	    }
	    return componente;
	}
	private  String elaboraXMLMessaggio(Node node) {
	    // do something with the current node instead of System.out
	    //System.out.println("********* Messaggio Nome del nodo=" +  node.getNodeName());
	    
		String messaggio = null;
		
	    //odeList nodeList = node.getChildNodes();
	    //messaggio = node.getNodeValue(); //getNodeValues(nodeList, "Messaggio");
	    //messaggio = node.getTextContent();    
		
		   NodeList children = node.getChildNodes();
	        for (int i = 0; i < children.getLength(); i++)
	        {
	            Node child = children.item(i);
	
	            if ((child instanceof CharacterData && !(child instanceof Comment)) || child instanceof EntityReference)
	            {
	            	messaggio =  child.getNodeValue();
	               // System.out.println("********* Messaggio child del nodo=" + messaggio);
	            }
	            else if (child.getNodeType() == Node.ELEMENT_NODE)
	            {
	            	messaggio = elaboraXMLMessaggio(child);
	            }
	        }

	    //System.out.println("********* Messaggio=" + messaggio == null ? "NULL" : messaggio);
	    
	    return messaggio;
	}
	private Nascita getNascitaFromXml(Node node){
		Nascita nascita = new Nascita();
		
		NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	           	if(currentNode.getNodeName().equals("DatadiNascita")){
	        		
	           		NodeList nodeListdatanascita = currentNode.getChildNodes();
	        		 
					nascita.setAnno(this.getNodeValues(nodeListdatanascita, "Anno"));
					nascita.setMese(this.getNodeValues(nodeListdatanascita, "Mese"));
					nascita.setGiorno(this.getNodeValues(nodeListdatanascita, "Giorno"));
		        		     
	        			
	        		}
	           		
 
	        	if(currentNode.getNodeName().equals("LuogodiNascita")){
	        		
	        		NodeList nodeListdatanascita = currentNode.getChildNodes();
	        		
                  	nascita.setNomeComune(this.getNodeValue(nodeListdatanascita, "NomeComune"));
		        	nascita.setSiglaProvincia(this.getNodeValue(nodeListdatanascita, "SiglaProvincia"));
		        	nascita.setCodiceComuneISTAT(this.getNodeValue(nodeListdatanascita, "CodiceComuneISTAT"));
		        	nascita.setCodiceProvinciaISTAT(this.getNodeValue(nodeListdatanascita, "CodiceProvinciaISTAT"));
		        	nascita.setCodiceStatoISTAT(this.getNodeValue(nodeListdatanascita, "CodiceStatoISTAT"));
		        			 
	    
           	    }
	        	if(currentNode.getNodeName().equals("Genitori")){
	        		
	        		Genitori _genitori = new Genitori();
	        		
	        		NodeList nodeListdatanascita = currentNode.getChildNodes();
	        		for(int j=0; j< nodeListdatanascita.getLength(); j++){
	        			if (nodeListdatanascita.item(j).getNodeType() == Node.ELEMENT_NODE){
	        				if(nodeListdatanascita.item(j).getNodeName().equals("Padre")){
		        				_genitori.setPadre(this.getNodeValues(nodeListdatanascita.item(j).getChildNodes(), "Nominativo"));
		        		    }
		        			if(nodeListdatanascita.item(j).getNodeName().equals("Madre")){
		        				_genitori.setMadre(this.getNodeValues(nodeListdatanascita.item(j).getChildNodes(), "Nominativo"));
		 	        		}
	        			}
	        			
	        		}
	        		nascita.setGenitori(_genitori);
	        	}
	        	 
	        }
	    }
		
		return nascita;
	}
	private DatiAnagrafeRoma getDatiAnagrafeElencoRomaFromXml(Node node){
		DatiAnagrafeRoma datiAnagrafeRoma = new DatiAnagrafeRoma();
		
		NodeList nodeList = node.getChildNodes();
	          
   		datiAnagrafeRoma.setCodiceIndividuale(this.getNodeValue(nodeList, "CodiceIndividuale"));
	 
   		datiAnagrafeRoma.setResidente(this.getNodeValues(nodeList, "Residente"));
   		datiAnagrafeRoma.setVivo(this.getNodeValues(nodeList, "Vivo"));
   		datiAnagrafeRoma.setFlagVivoResidente(this.getNodeValues(nodeList, "FlagVivoResidente"));
   		datiAnagrafeRoma.setDescrizione(this.getNodeValues(nodeList, "Descrizione"));
	       
		return datiAnagrafeRoma;
	}
	private DatiIndirizzo getDatiIndirizzoEstesoFromXml(Node node){
		DatiIndirizzo datiIndirizzo = new DatiIndirizzo();
		
		NodeList nodeList = node.getChildNodes();
		datiIndirizzo.setIndirizzoBreve(this.getNodeValues(nodeList, "Indirizzo"));
		
		return datiIndirizzo;
	}
	private  Persona elaboraPersonaElencoXML(Node node) {
		   
		Persona persona = new Persona();
		persona.setDatiDiNascita(this.getNascitaFromXml(node));
		persona.setPersonaCompleta(this.getPersonaFromXml(node));    
        persona.setDatiIndirizzo(this.getDatiIndirizzoFromXml(node)); 
    	persona.setDatiAnagrafeRoma (this.getDatiAnagrafeElencoRomaFromXml(node)); 
    	persona.setDatiIndirizzo(getDatiIndirizzoEstesoFromXml(node));
	    return persona;
	}
	
	private DatiIndirizzo getDatiIndirizzoFromXml(Node node){
		DatiIndirizzo datiIndirizzo = new DatiIndirizzo();
		
		NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	           	if(currentNode.getNodeName().equals("Indirizzo")){
	           		datiIndirizzo.setIndirizzoBreve(this.getNodeValues(currentNode.getChildNodes(), "indirizzoBreve"));
	        		datiIndirizzo.setToponimo(this.getNodeValues(currentNode.getChildNodes(), "Toponimo"));
	        		datiIndirizzo.setMunicipio(this.getNodeValues(currentNode.getChildNodes(), "Municipio"));
	        		datiIndirizzo.setNumero(this.getNodeValues(currentNode.getChildNodes(), "Numero"));
	           	} 
	           	NodeList nodeListSub = currentNode.getChildNodes();
	           	for (int j = 0; j < nodeListSub.getLength(); j++) {
	           	
	           	 Node currentNodeSub = nodeListSub.item(j);
	           	  if (currentNodeSub.getNodeType() == Node.ELEMENT_NODE) {
		         	if(currentNodeSub.getNodeName().equals("IndirizzoItaliano")){
		           		datiIndirizzo.setIndirizzoBreve(this.getNodeValues(currentNodeSub.getChildNodes(), "indirizzoBreve"));
		        		datiIndirizzo.setToponimo(this.getNodeValues(currentNodeSub.getChildNodes(), "Toponimo"));
		        		datiIndirizzo.setMunicipio(this.getNodeValues(currentNodeSub.getChildNodes(), "Municipio"));
		        		datiIndirizzo.setNumero(this.getNodeValues(currentNodeSub.getChildNodes(), "Numero"));
		        		datiIndirizzo.setCap(this.getNodeValues(currentNodeSub.getChildNodes(), "CAP"));
		           	} 	
	           	  }
	           	}
	        }
	        //
	    }
		
		return datiIndirizzo;
	}
	private DatiAnagrafeRoma getDatiAnagrafeRomaFromXml(Node node){
		DatiAnagrafeRoma datiAnagrafeRoma = new DatiAnagrafeRoma();
		
		NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	           	if(currentNode.getNodeName().equals("DatiIndividuo")){
	           		datiAnagrafeRoma.setCodiceIndividuale(this.getNodeValue(currentNode.getChildNodes(), "CodiceIndividuale"));
				 
	           		datiAnagrafeRoma.setResidente(this.getNodeValues(currentNode.getChildNodes(), "Residente"));
	           		datiAnagrafeRoma.setVivo(this.getNodeValues(currentNode.getChildNodes(), "Vivo"));
	           		datiAnagrafeRoma.setFlagVivoResidente(this.getNodeValues(currentNode.getChildNodes(), "FlagVivoResidente"));
				} 
	        }
	    }
		
		return datiAnagrafeRoma;
	}
	
	private PersonaCompleta getPersonaFromXml(Node node){
		PersonaCompleta personaCompleta = new PersonaCompleta();
		
		NodeList nodeList = node.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
	           	if(currentNode.getNodeName().equals("Sesso")){
	        		  
	           		personaCompleta.setSesso(currentNode.getFirstChild().getNodeValue() );
				}
	        	if(currentNode.getNodeName().equals("Nome")){
	        		  
	        		personaCompleta.setNome(currentNode.getFirstChild().getNodeValue());
				}	
 
	        	if(currentNode.getNodeName().equals("Cognome")){
	        		  
	        		personaCompleta.setCognome(currentNode.getFirstChild().getNodeValue() );
				}
	        	if(currentNode.getNodeName().equals("CodiceFiscale")){
	        		  
	        		personaCompleta.setCodiceFiscale(currentNode.getFirstChild().getNodeValue() );
				}
	        	if(currentNode.getNodeName().equals("StatoCivile")){
	        		  
	        		personaCompleta.setStatoCivile(currentNode.getFirstChild().getNodeValue());
				} 
	        	if(currentNode.getNodeName().equals("Cittadinanza")){
	        		personaCompleta.setCodiceStatoISTAT(this.getNodeValues(currentNode.getChildNodes(), "CodiceStatoISTAT"));
	        		personaCompleta.setDescrizioneCittadinanza(this.getNodeValues(currentNode.getChildNodes(), "DescrizioneCittadinanza"));
	           	
	        	}
	        	
	        }
	    }
		
		return personaCompleta;
	}
	
	private String getNodeValues(NodeList node, String elementName){
		String res = getNodeValue( node,  elementName);
		if(res == null)
		for(int j=0; j< node.getLength(); j++){
			if (node.item(j).getNodeType() == Node.ELEMENT_NODE){
				  if(node.item(j).hasChildNodes()){
					  res = getNodeValue(node.item(j).getChildNodes(),  elementName);
					  if(res != null)
						  return res;
				  }
			}
		}
		return res;
	}
	
	private String getNodeValue(NodeList node, String elementName){
		String _nodeValue = null;
		
		for(int j=0; j< node.getLength(); j++){
			if (node.item(j).getNodeType() == Node.ELEMENT_NODE){
				if(node.item(j).getNodeName().equals(elementName)){
					_nodeValue = node.item(j).getFirstChild().getNodeValue();
					return _nodeValue;
    		    }else if(node.item(j).hasChildNodes()){
    		    	_nodeValue = getNodeValue(node.item(j).getChildNodes(),  elementName);
					  if(_nodeValue != null)
						  return _nodeValue;
				  }
			 }
			
		}
		 
		return null;
	}
	
}

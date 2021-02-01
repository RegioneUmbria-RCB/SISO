package it.roma.comune.servizi.test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
 















import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.axis.message.MessageElement;
import org.apache.axis.types.UnsignedByte;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.roma.comune.servizi.dto.DatiAnagrafeRoma;
import it.roma.comune.servizi.dto.DatiIndirizzo;
import it.roma.comune.servizi.dto.Genitori;
import it.roma.comune.servizi.dto.Nascita;
import it.roma.comune.servizi.dto.Persona;
import it.roma.comune.servizi.dto.PersonaCompleta;
import it.roma.comune.servizi.verificheanagrafiche.LogHeader;
import it.roma.comune.servizi.verificheanagrafiche.NVASoap;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagrafica;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponse;
import it.roma.comune.servizi.verificheanagrafiche.VerificaAnagraficaResponseVerificaAnagraficaResult;

public class TestVerificaAnagrafica {

	NVASoap soap;
	private String esito = "OK";
	
	public TestVerificaAnagrafica(NVASoap soap){
		this.soap=soap;
	}
	public void doTest(String tipoStr, String codiceIndividuale, String cognome, String nome, String sesso, String annoNascita, String meseNascita,String giornoNascita, String codiceFiscale){
		VerificaAnagrafica va= new VerificaAnagrafica();
		if(codiceIndividuale != null && !codiceIndividuale.equals("-"))
			va.setCodiceIndividuale( codiceIndividuale.trim());
			//va.setCodiceIndividuale(Integer.parseInt(codiceIndividuale));
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
		if(codiceFiscale != null && !codiceFiscale.equals("-"))
			va.setCodiceFiscale(codiceFiscale);
		
		UnsignedByte tipo= new UnsignedByte(Long.parseLong(tipoStr));
		va.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(TestMain.getUID());
		VerificaAnagraficaResponse resp=null;
		try {
			resp=soap.verificaAnagrafica(va, header);
			ArrayList  arr_persona = new ArrayList();
			VerificaAnagraficaResponseVerificaAnagraficaResult r=  resp.getVerificaAnagraficaResult();
				for(int i=0; i< r.get_any().length; i++ ){
					 System.out.println(r.get_any()[i].toString());
					 NodeList nListM = 	r.get_any()[i].getElementsByTagName("Messaggio");
					 for (int temp = 0; temp < nListM.getLength(); temp++) {
						 esito = elaboraXMLMessaggio(nListM.item(i));
	 
	 				}
					 
					 NodeList nList = 	r.get_any()[i].getElementsByTagName("Persona");
						
	 				 for (int temp = 0; temp < nList.getLength(); temp++) {
	 					 arr_persona.add(elaboraXML(nList.item(i)));
	 
	 					}
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("");
		}
		//System.out.print("Ricerca di : " + va.getCognome() + " " + String.valueOf(va.getAnnoNascita()) + " " + va.getSesso());
		System.out.print("Ricerca di : " + va.getCodiceFiscale());
	 
	}
	
	 
	private  String elaboraXMLMessaggio(Node node) {
	    String messaggio = null;
		
	    NodeList nodeList = node.getChildNodes();
	    messaggio = this.getNodeValues(nodeList, "Messaggio");
	        
	        
	    
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
	        }
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
	
	private  Persona elaboraPersonaElencoXML(Node node) {
	   
		Persona persona = new Persona();
		persona.setDatiDiNascita(this.getNascitaFromXml(node));
	       
        persona.setPersonaCompleta(this.getPersonaFromXml(node)); 
    	persona.setDatiAnagrafeRoma (this.getDatiAnagrafeElencoRomaFromXml(node)); 
	    return persona;
	}
	public ArrayList doTestDebug() throws Exception{
	
		try{
			//MessageElement[] me =  convertXMLStringtoMessageElement("C:\\progetti\\SISO\\ANAGRAFE_ROMA\\EsempixSISO\\Esempi x SISO\\NETVA(Verifica Anagrafica).xml");
			MessageElement[] me =  convertXMLStringtoMessageElement("./resources/ElencoPersone.xml");
			
			ArrayList  arr_persona = new ArrayList();
			
			
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
			return arr_persona;
			}
			catch(Exception ex){
				 throw ex;
			}
		 
	}
	
	public static MessageElement[] convertXMLStringtoMessageElement(String absXmlFile) throws SAXException, IOException, ParserConfigurationException{
       
		File file = new File(absXmlFile);
		MessageElement[] m = new MessageElement[1];
        Document XMLDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
        Element element = XMLDoc.getDocumentElement();
        m[0] = new MessageElement(element);
        return m;
    }
	public void doTest(){
		VerificaAnagrafica va= new VerificaAnagrafica();
		//va.setCodiceIndividuale(11);
		va.setCognome("ROSSI");
 		va.setAnnoNascita((short)1973);
 		va.setSesso("F");
		//va.setCodiceFiscale("RNATNA73D50H501K");
		UnsignedByte tipo= new UnsignedByte(1);
		va.setTipoInterr(tipo);
		
		LogHeader header= new LogHeader();
		
		header.setLogGuid(TestMain.getUID());
		VerificaAnagraficaResponse resp=null;
		try {
			resp=soap.verificaAnagrafica(va, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.print("Ricerca di : " + va.getCognome() + " " + String.valueOf(va.getAnnoNascita()) + " " + va.getSesso());
		System.out.print("Ricerca di : " + va.getCodiceFiscale());
		VerificaAnagraficaResponseVerificaAnagraficaResult r=  resp.getVerificaAnagraficaResult();
		try{
 			   
			for(int i=0; i< r.get_any().length; i++ ){
 
				 System.out.println(r.get_any()[i].toString());
				 System.out.println("-----------------------------------");
			}
			//System.out.println(r.get_any()[0].toString());
			}
			catch(Exception ex){
				System.out.println("Eccezione .. " + ex.getStackTrace());
			}
	}
}

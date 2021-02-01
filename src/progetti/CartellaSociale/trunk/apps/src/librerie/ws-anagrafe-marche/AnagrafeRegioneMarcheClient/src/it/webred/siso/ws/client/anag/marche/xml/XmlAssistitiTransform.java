package it.webred.siso.ws.client.anag.marche.xml;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.xmlbeans.XmlString;

import it.webred.siso.ws.client.anag.marche.client.PersonaResult;
import it.webred.siso.ws.client.anag.marche.client.RicercaPersonaResult;

public class XmlAssistitiTransform {
	
	private int codice = 0;
	private String messaggio = "";
	
	public int getCodice() {
		return codice;
	}

	public void setCodice(int codice) {
		this.codice = codice;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
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
	
	private Node findNamedChild( NodeList nodes, String elementName )
	{
	  Node result = null;
	  
	  for ( int i = 0; i < nodes.getLength(); i++ )
	  {
	    Node item = nodes.item( i );
	    if ( elementName.equals( item.getNodeName() ) )
	    {
	    	result = item;
	    	break;
	    }
	  }
	  return result;
	}

	 
	private String getNodeValue(NodeList node, String elementName){
		String _nodeValue = null;
		try{
			for(int j=0; j< node.getLength(); j++){
				if (node.item(j).getNodeType() == Node.ELEMENT_NODE){
					if(node.item(j).getNodeName().equals(elementName)){
						if(node.item(j).getFirstChild() == null){
							return null;
						}
						_nodeValue = node.item(j).getFirstChild().getNodeValue();
						return _nodeValue;
	    		    }else if(node.item(j).hasChildNodes()){
	    		    	_nodeValue = getNodeValue(node.item(j).getChildNodes(),  elementName);
						  if(_nodeValue != null)
							  return _nodeValue;
					  }
				 }
				
			}
		}
		catch(Exception ex){
			
		}
		
		 
		return null;
	}
	private List<String> getValues(NodeList node, String elementName){
		List<String> listaValori = new ArrayList<String>();
		try{
			for(int j=0; j< node.getLength(); j++){
				if (node.item(j).getNodeType() == Node.ELEMENT_NODE){
					if(node.item(j).getNodeName().equals(elementName)){
						if(node.item(j).getFirstChild() != null){
							String value = node.item(j).getFirstChild().getNodeValue();
							listaValori.add(value);
						}
						
	    		    }else if(node.item(j).hasChildNodes()){
	    		    	String value =  getNodeValue(node.item(j).getChildNodes(),  elementName);
						  if(value != null)
							  listaValori.add(value);
					  }
				 }
				
			}
		}
		catch(Exception ex){
			
		}
		return listaValori;
	}
	private PersonaResult getDatiPersonaFromXml(Node node){
		PersonaResult personaResult = new PersonaResult();
		
		NodeList nodeList = node.getChildNodes();
	          
		personaResult.setAssistitoId(this.getNodeValue(nodeList, XmlConstants.AssistitoID));
		personaResult.setCapDomicilio(this.getNodeValue(nodeList, XmlConstants.DomicilioCap));
		personaResult.setCapRes(this.getNodeValue(nodeList, XmlConstants.ResidenzaCap));
		personaResult.setCodfisc(this.getNodeValue(nodeList, XmlConstants.CodiceFiscale));
		personaResult.setCodIstatCittadinanza(this.getNodeValue(nodeList, XmlConstants.NazionalitaIstat));
		//personaResult.setCodStatoNas(this.getNodeValue(nodeList, XmlConstants.NascitaIstat));
		personaResult.setCognome(this.getNodeValue(nodeList, XmlConstants.Cognome));
		personaResult.setDataMor(this.getNodeValue(nodeList, XmlConstants.DecessoData));
		personaResult.setDataNascita(this.getNodeValue(nodeList, XmlConstants.NascitaData));
		personaResult.setDescComDomicilio(this.getNodeValue(nodeList, XmlConstants.DomicilioDescrizione));
		personaResult.setDescComRes(this.getNodeValue(nodeList, XmlConstants.ResidenzaDescrizione));
		personaResult.setDesComNas(this.getNodeValue(nodeList, XmlConstants.NascitaDescrizione));
		personaResult.setDesStatoNas(this.getNodeValue(nodeList, XmlConstants.NazionalitaDescrizione));
		personaResult.setIndirizzoDomicilio(this.getNodeValue(nodeList, XmlConstants.DomicilioIndirizzo));
		personaResult.setIndirizzoResidenza(this.getNodeValue(nodeList, XmlConstants.ResidenzaIndirizzo));
		personaResult.setIstatComDomicilio(this.getNodeValue(nodeList, XmlConstants.DomicilioIstat));
		personaResult.setIstatComNas(this.getNodeValue(nodeList, XmlConstants.NascitaIstat));
		personaResult.setIstatComResidenza(this.getNodeValue(nodeList, XmlConstants.ResidenzaIstat));
		personaResult.setIstatNazione(this.getNodeValue(nodeList, XmlConstants.ResidenzaStato));
		personaResult.setNome(this.getNodeValue(nodeList, XmlConstants.Nome));
		personaResult.setRecaptelefonico(this.getNodeValue(nodeList, XmlConstants.RecapitoTelefonico));
		personaResult.setSesso(this.getNodeValue(nodeList, XmlConstants.Sesso));
		personaResult.setSiglaProvDomicilio(this.getNodeValue(nodeList, XmlConstants.DomicilioProvinciaSigla));
		personaResult.setSiglaProvNas(this.getNodeValue(nodeList, XmlConstants.NascitaProvinciaSigla));
		personaResult.setSiglaProvRes(this.getNodeValue(nodeList, XmlConstants.ResidenzaProvinciaSigla));
		personaResult.setStatoCivile(this.getNodeValue(nodeList, XmlConstants.StatoCivile));
		personaResult.setRecaptelefonicoSecondario(this.getNodeValue(nodeList, XmlConstants.RecapitoTelefonicoSecondario));
		personaResult.setDocumentoSanitario(this.getNodeValue(nodeList, XmlConstants.DocumentoSanitario));
		personaResult.setDocumentoSanitarioScadenza(this.getNodeValue(nodeList, XmlConstants.DocumentoSanitarioScadenza));
		
		//sezione medico
		Node xmlMedico =  findNamedChild(nodeList, XmlConstants.MEDICO);
		if(xmlMedico != null){
			personaResult.setMedicoCodiceFiscale(this.getNodeValue(xmlMedico.getChildNodes(), XmlConstants.CodiceFiscaleMedico));
			personaResult.setMedicoCognomeNome(this.getNodeValue(xmlMedico.getChildNodes(), XmlConstants.CognomeNomeMedico));
			personaResult.setMedicoDataRevoca(this.getNodeValue(xmlMedico.getChildNodes(), XmlConstants.RevocaDataMedico));
			personaResult.setMedicoDataScelta(this.getNodeValue(xmlMedico.getChildNodes(), XmlConstants.SceltaDataMedico));
		 }
		 	
		 
		
		
		return personaResult;
	}
	
	private Node componiNodoXml(String xml) throws Exception{
		
		RicercaPersonaResult ricercaPersonaResult = new RicercaPersonaResult();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		ByteArrayInputStream bis = new ByteArrayInputStream(xml.getBytes());
		Document doc = db.parse(bis);
		Node n = doc.getFirstChild();
		return n;
		//NodeList nl = n.getChildNodes();
		//Node an,an2;

		//return nl;
//		for (int i=0; i < nl.getLength(); i++) {
//		    an = nl.item(i);
//		    if(an.getNodeType()==Node.ELEMENT_NODE) {
//		        NodeList nl2 = an.getChildNodes();
//
//		        for(int i2=0; i2<nl2.getLength(); i2++) {
//		            an2 = nl2.item(i2);
//		            // DEBUG PRINTS
//		            System.out.println(an2.getNodeName() + ": type (" + an2.getNodeType() + "):");
//		            if(an2.hasChildNodes()) System.out.println(an2.getFirstChild().getTextContent());
//		            if(an2.hasChildNodes()) System.out.println(an2.getFirstChild().getNodeValue());
//		            System.out.println("an NOME : " + an.getNodeName());
//		            System.out.println(an2.getTextContent());
//		            System.out.println(an2.getNodeValue());
//		        }
//		    }
//		}
		 
	}
public  PersonaResult  elaboraXML(XmlString node) throws Exception{
		
		Node nodo = node.getDomNode();
		PersonaResult pr = new PersonaResult();
		
	    NodeList nodeList = nodo.getChildNodes();
	    for (int i = 0; i < nodeList.getLength(); i++) {
	        Node currentNode = nodeList.item(i);
	        if (currentNode.getNodeType() == Node.TEXT_NODE) {
	            String xmlCDATAValue = currentNode.getNodeValue();
	            try {
					
	            	Node nodoCDATA = this.componiNodoXml(xmlCDATAValue);
					if(nodoCDATA.getNodeName().equals( XmlConstants.AssistitoResponse)){
						if(nodoCDATA.getChildNodes() == null || nodoCDATA.getChildNodes().getLength() == 0){
							this.codice = -1;
							this.messaggio = "Nessun assistito presente";
						}
						if(nodoCDATA != null){
							  // estraggo le informazioni riguardanti la persona
							  pr =  this.getDatiPersonaFromXml(nodoCDATA);
							 
						  }
					}
				
				 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw e;
				}
	           // org.apache.xmlbeans.XmlString message = null;
	    		//MESSAGGIO PER CODICE FISCALE 
//	    		message = org.apache.xmlbeans.XmlString.Factory.newInstance();
//	    		message.setStringValue(xmlCDATAValue);
//	    		NodeList childNodeList =   message.getDomNode().getChildNodes();
//	    		
//	    		  for (int j = 0; j < childNodeList.getLength(); j++) {
//	    		        Node currentDATA = childNodeList.item(j);
//	    		       System.out.println(  currentDATA.getNodeName());
//	    		  }
	        }
	    }
	    return pr;
	}

public  List<String>  elaboraXMLElenco(XmlString node) throws Exception{
	
	Node nodo = node.getDomNode();
	List<String> listaAssistitoID = new ArrayList<String>();
	
    NodeList nodeList = nodo.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
        Node currentNode = nodeList.item(i);
        if (currentNode.getNodeType() == Node.TEXT_NODE) {
            String xmlCDATAValue = currentNode.getNodeValue();
            try {
				 
            	Node nodoCDATA = this.componiNodoXml(xmlCDATAValue);
            	if(nodoCDATA.getNodeName().equals( XmlConstants.AssistitiElencoResponse)){ 
					if(nodoCDATA.getChildNodes() == null || nodoCDATA.getChildNodes().getLength() == 0){
						this.codice = -1;
						this.messaggio = "Nessun assistito presente";
					}
					if(nodoCDATA != null){
						  // estraggo le informazioni riguardanti la persona
						listaAssistitoID =  this.getValues(nodoCDATA.getChildNodes(), XmlConstants.AssistitoIDElenco);
						 
					  }
            	}
			 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				throw e;
			}
 
        }
    }
    return listaAssistitoID;
}


}

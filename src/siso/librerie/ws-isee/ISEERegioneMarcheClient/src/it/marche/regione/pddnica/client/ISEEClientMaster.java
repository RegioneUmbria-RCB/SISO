package it.marche.regione.pddnica.client;

import java.io.StringReader;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import org.apache.commons.codec.binary.Base64;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ISEEClientMaster {
	
	private String soapEndpointUrl;
	private String soapAction;
	
	/***
	 *      <xs:enumeration value="OK"/>
            <xs:enumeration value="RICHIESTA_INVALIDA"/>
            <xs:enumeration value="ERRORE_INTERNO"/>
            <xs:enumeration value="DATI_NON_TROVATI"/>
	 */
	private String esito;
	private EccezioneClient errore = null;
	
	private String descrErrore;
	protected SOAPMessage soapResponse;
	
	
	
	
	
    public EccezioneClient getErrore() {
		return errore;
	}



	public void setErrore(EccezioneClient errore) {
		this.errore = errore;
	}



	protected  SOAPMessage createSOAPRequest( String requestMessage) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
      
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(requestMessage));
        org.w3c.dom.Document document = builder.parse(is);
        DOMSource domSource = new DOMSource(document);
        
        SOAPPart soapPart = soapMessage.getSOAPPart();
        soapPart.setContent(domSource);
        
        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }
    protected String getNodeText(String tagName){
    	 SOAPBody bodyResponse;
    	 String result = "";
		try {
			bodyResponse = soapResponse.getSOAPBody();
			NodeList listaEsito = bodyResponse.getElementsByTagName(tagName);
	        org.w3c.dom.Node esitoNode = listaEsito.item(0);
	        result = esitoNode.getTextContent();
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return result; 
    }
    protected  void callSoapWebService(String messageRequest) throws EccezioneClient{
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            soapResponse = soapConnection.call(createSOAPRequest(messageRequest), soapEndpointUrl);

            setEsito(getNodeText("Esito"));
            setDescrErrore(getNodeText("DescErrore"));
             
            // Print the SOAP Response
//            System.out.println("Response SOAP Message:");
//            soapResponse.writeTo(System.out);
//            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
        	e.printStackTrace();
        	EccezioneClient excClient = new EccezioneClient("Si è verificato un errore durante la procedura di invio dati SOAP per ISEE tramite porta di dominio", e,-1000);
        	 throw excClient;
 
        }
    }
    
    
	public String getSoapEndpointUrl() {
		return soapEndpointUrl;
	}
	public void setSoapEndpointUrl(String soapEndpointUrl) {
		this.soapEndpointUrl = soapEndpointUrl;
	}
	public String getSoapAction() {
		return soapAction;
	}
	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getDescrErrore() {
		return descrErrore;
	}
	public void setDescrErrore(String descrErrore) {
		this.descrErrore = descrErrore;
	}

	public SOAPMessage getSoapResponse() {
		return soapResponse;
	}

	public void setSoapResponse(SOAPMessage soapResponse) {
		this.soapResponse = soapResponse;
	}

}

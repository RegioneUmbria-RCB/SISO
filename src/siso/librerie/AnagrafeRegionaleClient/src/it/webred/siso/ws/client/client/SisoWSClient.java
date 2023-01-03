package it.webred.siso.ws.client.client;

import it.webred.siso.ws.client.client.exception.FaultResponseException;
import it.webred.siso.ws.client.client.exception.SisoClientException;
import it.webred.siso.ws.client.client.model.fault.Fault;
import it.webred.siso.ws.client.client.model.fault.Fault.Faultstring;
import it.webred.siso.ws.client.esb.SISOServicePortType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;

import org.jboss.logging.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;


public abstract class SisoWSClient {

	protected static Logger logger = Logger.getLogger("ud-rest-lib");
	
	public void execute(Context context) throws JAXBException, SisoClientException, FaultResponseException {

		try {
			
			// Marshall request
			Marshaller m = context.getComntextRequest().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty("jaxb.fragment", Boolean.TRUE);
			com.sun.xml.bind.marshaller.NamespacePrefixMapper prefixMapper = getNamespacePrefixMapper();
			if (prefixMapper!=null)
				m.setProperty("com.sun.xml.bind.namespacePrefixMapper", prefixMapper);
//					m.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", prefixMapper);
	        
			StringWriter sw = new StringWriter();
			m.marshal(context.getInput(), sw);
			String payloadString = sw.toString();
			logger.debug("ANAGRAFE REGIONALE CLIENT: richiesta ["+payloadString+"]");
		
			// Call service
			SISOServicePortType port = context.getServiceStub().getSISOServiceHttpSoap11Endpoint();
			String resp = port.doService(payloadString, context.getServiceId());
					
			String ritorno = resp.trim();
			//System.out.println("Result size per "+payloadString+" "+ritorno.length());
//			logger.debug("ANAGRAFE REGIONALE CLIENT: risposta ["+ritorno+"]");
			
			XMLFilterImpl filter = context.getNamespaceFilter();
			if (filter==null) {
				Unmarshaller um = context.getComntextResponse().createUnmarshaller();
				InputStream stream = new ByteArrayInputStream(ritorno.getBytes(StandardCharsets.UTF_8));
				context.setOutput(um.unmarshal(stream));
			} else {
				
				//InputSource is = new InputSource(new StringReader(ritorno));  
				XMLReader reader;
				try {
					reader = XMLReaderFactory.createXMLReader();
				} catch (SAXException e1) {
					throw new SisoClientException(e1);
				}
				filter.setParent(reader);  
				//SAXSource source = new SAXSource(filter, is); 

				InputStream stream = new ByteArrayInputStream(ritorno.getBytes(StandardCharsets.UTF_16));
				

				
				Object object = null;
				try {
					final Source source = new SAXSource(filter, new InputSource(stream));
					object = unmarshall(context.getComntextResponse(), source);
					context.setOutput(object);
				} catch (UnmarshalException e) {
					// e' possibile che restituisca un altro xml, con un formato diverso , magari un xml con segnalazione di errore.
					try {
						stream.reset() ;
					} catch (IOException e1) {
						throw new SisoClientException(e1);
					}
					final Source source = new SAXSource(filter, new InputSource(stream));
					object = (Fault)unmarshall(context.getFaultResponse() , source);
				}
				
				 
				if (object instanceof Fault) {
					Faultstring faultString = ((Fault)object).getFaultstring();
					throw new FaultResponseException(faultString!=null?faultString.getValue():"Risposta servizio : Errore generico "); 
				}
				
			}

		} 
		finally {
		}
		
	}

	protected abstract com.sun.xml.bind.marshaller.NamespacePrefixMapper getNamespacePrefixMapper();
		
	private Object unmarshall (JAXBContext jaxbContext, Source source) throws JAXBException, SisoClientException {
		
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller(); 
		unmarshaller.setEventHandler(new ValidationEventHandler()  
		{  
		    public boolean handleEvent(ValidationEvent event)  
		    {  
		        return false;  
		    }  
		});  
		return unmarshaller.unmarshal(source);
	}	
		

	
	
	


	
}

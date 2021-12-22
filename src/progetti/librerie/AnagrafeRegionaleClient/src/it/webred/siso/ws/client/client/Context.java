package it.webred.siso.ws.client.client;

import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import it.webred.siso.ws.client.atlante.model.Login.request.Login;
import it.webred.siso.ws.client.client.exception.SisoClientException;
import it.webred.siso.ws.client.client.model.fault.Fault;
import it.webred.siso.ws.client.esb.DoService;
import it.webred.siso.ws.client.esb.SISOService;





import org.xml.sax.helpers.XMLFilterImpl;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public abstract class Context {


	protected SISOService serviceStub;
	protected DoService doService;
	protected URL urlWSDL;

	/*
	 * Recupera il messaggio dalla Fault Class
	 */

	protected abstract Class getInputClass();

	protected abstract Class getOutputClass();



	protected abstract XMLFilterImpl   getNamespaceFilter();

	public JAXBContext getComntextResponse() throws SisoClientException {
		try {

			if (getOutputClass() != null)
				return JAXBContext.newInstance(getOutputClass());


		} catch (Exception e) {
			throw new SisoClientException(e);
		}
		return null;
	}
	
	public JAXBContext getFaultResponse() throws SisoClientException {
		try {

				return JAXBContext.newInstance(Fault.class);


		} catch (Exception e) {
			throw new SisoClientException(e);
		}
	}
	
	
	public JAXBContext getComntextRequest() throws SisoClientException {
		try {
			
			
				return JAXBContext.newInstance(getInputClass());


		} catch (JAXBException e) {
			throw new SisoClientException(e);
		}
	}

	



	
	
	public Context(URL urlWSDL) throws SisoClientException {
		if (doService == null || serviceStub == null ) {
				if(urlWSDL!=null){
					serviceStub = new SISOService(urlWSDL);
				}else{
					serviceStub = new SISOService();
				}
		}
	}




	
	public abstract Object getInput() ;

	public abstract Object  getOutput() ;

	public abstract void  setInput(Object input) ;

	public abstract  void setOutput(Object output) ;


	public SISOService getServiceStub() {
		return serviceStub;
	}

	public void setServiceStub(SISOService serviceStub) {
		this.serviceStub = serviceStub; 
	}





	public abstract String getServiceId() ;

	
}

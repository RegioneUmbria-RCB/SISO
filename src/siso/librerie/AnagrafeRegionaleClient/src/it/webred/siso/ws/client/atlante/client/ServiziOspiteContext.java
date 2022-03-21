package it.webred.siso.ws.client.atlante.client;

import it.webred.siso.ws.client.atlante.model.GetServiziOspite.request.GetServiziOspite;
import it.webred.siso.ws.client.atlante.model.GetServiziOspite.response.GetServiziOspiteResponse;
import it.webred.siso.ws.client.client.exception.SisoClientException;

import java.net.URL;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class ServiziOspiteContext extends it.webred.siso.ws.client.client.Context {

	GetServiziOspite requestServiziOspite ;
	GetServiziOspiteResponse getServizioOspoteResponse;

	public ServiziOspiteContext(URL urlWSDL) throws SisoClientException {
		super(urlWSDL);
	}



	@Override
	public String getServiceId() {
		return "atl";
	}




	@Override
	public GetServiziOspite getInput() {
		return requestServiziOspite;
	}

	@Override
	public GetServiziOspiteResponse getOutput() {
		return getServizioOspoteResponse;
	}



	public void setInput(Object input) {
		requestServiziOspite = (GetServiziOspite)input;
	}



	
	public void setOutput(Object output) {
		getServizioOspoteResponse =  (GetServiziOspiteResponse)output;
		
	}



	@Override
	protected Class getInputClass() {
		return GetServiziOspite.class;
	}



	@Override
	protected Class getOutputClass() {
		return GetServiziOspiteResponse.class;
	}


	public class AtlanteResponseNameSpaceFilter extends XMLFilterImpl  
	{  
	    private String usedNamespaceUri;  
	    private boolean isAddNamespace;  
	  
	    // State variable  
	    private boolean isAddedNamespaceAlready = false;  
	  
	    public AtlanteResponseNameSpaceFilter(String namespaceUri, boolean addNamespace)  
	    {  
	        super();  
	  
	        if (addNamespace)  
	            usedNamespaceUri = namespaceUri;  
	        else  
	            usedNamespaceUri = "";  
	  
	        isAddNamespace = addNamespace;  
	    }  
	  
	    @Override  
	    public void startDocument() throws SAXException  
	    {  
	        super.startDocument();  
	        if (isAddNamespace)  
	        {  
	            startControlledPrefixMapping();  
	        }  
	    }  
	  
	    @Override  
	    public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException  
	    {  
	        super.startElement(usedNamespaceUri, arg1, arg2, arg3);  
	    }  
	  
	    @Override  
	    public void endElement(String arg0, String arg1, String arg2) throws SAXException  
	    {  
	        super.endElement(usedNamespaceUri, arg1, arg2);  
	    }  
	  
	    @Override  
	    public void startPrefixMapping(String prefix, String url) throws SAXException  
	    {  
	        if (isAddNamespace)  
	        {  
	            startControlledPrefixMapping();  
	        }  
	        else  
	        {  
	            // Remove the namespace, i.e. don?t call startPrefixMapping for parent!  
	        }  
	    }  
	  
	    private void startControlledPrefixMapping() throws SAXException  
	    {  
	        if (isAddNamespace && !isAddedNamespaceAlready)  
	        {  
	            // We should add namespace since it is set and has not yet been done.  
	            super.startPrefixMapping("", usedNamespaceUri);  
	  
	            // Make sure we dont do it twice  
	            isAddedNamespaceAlready = true;  
	        }  
	    }  
	}


	@Override
	protected XMLFilterImpl getNamespaceFilter() {
		String namespaceUri = new String("http://schemas.datacontract.org/2004/07/AtlanteWebServices.ObjectModel.Messages.GestioneServizi").intern();  
		XMLFilterImpl atlanteXmlFilter = new  AtlanteResponseNameSpaceFilter(namespaceUri, false);
		return atlanteXmlFilter;
	}















}

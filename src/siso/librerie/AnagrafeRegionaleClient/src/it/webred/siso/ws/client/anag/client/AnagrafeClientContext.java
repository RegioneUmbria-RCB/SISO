package it.webred.siso.ws.client.anag.client;

import java.net.URL;

import org.xml.sax.helpers.XMLFilterImpl;

import it.webred.siso.ws.client.anag.model.Input;
import it.webred.siso.ws.client.anag.model.Output;
import it.webred.siso.ws.client.client.Context;
import it.webred.siso.ws.client.client.exception.SisoClientException;

public class AnagrafeClientContext extends Context {

	Input input ;
	Output output;

	public AnagrafeClientContext(URL urlWSDL) throws SisoClientException {
		super(urlWSDL);
	}



	@Override
	public String getServiceId() {
		return "anag";
	}


	@Override
	public Input getInput() {
		return input;
	}

	@Override
	public Output getOutput() {
		return output;
	}



	public void setInput(Object input) {
		this.input = (Input)input;
	}
	
	public void setOutput(Object output) {
		this.output =  (Output)output;
		
	}



	@Override
	protected Class getInputClass() {
		// TODO Auto-generated method stub
		return Input.class;
	}



	@Override
	protected Class getOutputClass() {
		// TODO Auto-generated method stub
		return Output.class;
	}



	@Override
	protected XMLFilterImpl getNamespaceFilter() {
		// TODO Auto-generated method stub
		return null;
	}















}

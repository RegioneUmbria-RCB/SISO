package it.webred.siso.ws.client.atlante.client;

import java.net.URL;

import org.xml.sax.helpers.XMLFilterImpl;

import it.webred.siso.ws.client.atlante.model.Login.request.Login;
import it.webred.siso.ws.client.atlante.model.Login.response.LoginResponse;
import it.webred.siso.ws.client.client.Context;
import it.webred.siso.ws.client.client.exception.SisoClientException;

public class LoginContext extends Context {

	Login logi ;
	LoginResponse loginResponse;

	public LoginContext(URL urlWSDL) throws SisoClientException {
		super(urlWSDL);
	}



	@Override
	public String getServiceId() {
		return "atl";
	}




	@Override
	public Login getInput() {
		return logi;
	}

	@Override
	public LoginResponse getOutput() {
		return loginResponse;
	}



	public void setInput(Object input) {
		logi = (Login)input;
	}



	
	public void setOutput(Object output) {
		loginResponse =  (LoginResponse)output;
		
	}



	@Override
	protected Class getInputClass() {
		// TODO Auto-generated method stub
		return Login.class;
	}



	@Override
	protected Class getOutputClass() {
		// TODO Auto-generated method stub
		return LoginResponse.class;
	}



	@Override
	protected XMLFilterImpl getNamespaceFilter() {
		// TODO Auto-generated method stub
		return null;
	}





















}

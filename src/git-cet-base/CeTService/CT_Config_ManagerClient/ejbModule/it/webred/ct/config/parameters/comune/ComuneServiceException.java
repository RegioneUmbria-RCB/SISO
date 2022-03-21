package it.webred.ct.config.parameters.comune;


import it.webred.ct.config.parameters.ParameterServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class ComuneServiceException extends ParameterServiceException{

	public ComuneServiceException() {}
	
	public ComuneServiceException(String msg) {
		super(msg);		
	}
	
	public ComuneServiceException(Throwable t) {
		super(t);		
	}
	
}

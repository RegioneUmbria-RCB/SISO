package it.webred.ct.config.parameters.fonte;


import it.webred.ct.config.parameters.ParameterServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class FonteServiceException extends ParameterServiceException{

	public FonteServiceException() {}
	
	public FonteServiceException(String msg) {
		super(msg);		
	}
	
	public FonteServiceException(Throwable t) {
		super(t);		
	}
	
}

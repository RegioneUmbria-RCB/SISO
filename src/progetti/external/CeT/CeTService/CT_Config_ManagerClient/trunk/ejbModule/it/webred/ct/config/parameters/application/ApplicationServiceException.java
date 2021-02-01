package it.webred.ct.config.parameters.application;


import it.webred.ct.config.parameters.ParameterServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class ApplicationServiceException extends ParameterServiceException{

	public ApplicationServiceException() {}
	
	public ApplicationServiceException(String msg) {
		super(msg);		
	}
	
	public ApplicationServiceException(Throwable t) {
		super(t);		
	}
	
}

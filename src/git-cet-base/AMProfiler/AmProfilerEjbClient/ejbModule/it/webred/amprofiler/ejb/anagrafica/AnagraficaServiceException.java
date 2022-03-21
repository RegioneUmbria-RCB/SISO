package it.webred.amprofiler.ejb.anagrafica;

import javax.ejb.ApplicationException;

@ApplicationException
public class AnagraficaServiceException extends RuntimeException{

	public AnagraficaServiceException() {}
	
	public AnagraficaServiceException(String msg) {
		super(msg);		
	}
	
	public AnagraficaServiceException(Throwable t) {
		super(t);		
	}
	
}

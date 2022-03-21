package it.webred.ct.data.access.basic.indice.soggetto;

import javax.ejb.ApplicationException;

@ApplicationException
public class SoggettoServiceException extends RuntimeException{

	public SoggettoServiceException() {}
	
	public SoggettoServiceException(String msg) {
		super(msg);		
	}
	
	public SoggettoServiceException(Throwable t) {
		super(t);		
	}
}

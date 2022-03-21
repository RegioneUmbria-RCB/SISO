package it.webred.ct.data.access.basic.indice.oggetto;

import javax.ejb.ApplicationException;

@ApplicationException
public class OggettoServiceException extends RuntimeException{

	public OggettoServiceException() {}
	
	public OggettoServiceException(String msg) {
		super(msg);		
	}
	
	public OggettoServiceException(Throwable t) {
		super(t);		
	}

}

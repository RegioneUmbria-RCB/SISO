package it.webred.ct.data.access.basic.fonti;


import javax.ejb.ApplicationException;

@ApplicationException
public class FontiServiceException extends RuntimeException {

	public FontiServiceException() {}
	
	public FontiServiceException(String msg) {
		super(msg);		
	}
	
	public FontiServiceException(Throwable t) {
		super(t);		
	}
	
}

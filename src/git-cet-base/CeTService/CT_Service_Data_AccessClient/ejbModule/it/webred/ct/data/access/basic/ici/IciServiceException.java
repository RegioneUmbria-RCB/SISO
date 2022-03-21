package it.webred.ct.data.access.basic.ici;

import javax.ejb.ApplicationException;

@ApplicationException
public class IciServiceException extends RuntimeException {
	public IciServiceException() {}
	
	public IciServiceException(String msg) {
		super(msg);		
	}
	
	public IciServiceException(Throwable t) {
		super(t);		
	}
}

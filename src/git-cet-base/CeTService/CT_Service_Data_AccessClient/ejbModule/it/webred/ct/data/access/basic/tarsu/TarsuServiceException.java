package it.webred.ct.data.access.basic.tarsu;


import javax.ejb.ApplicationException;

@ApplicationException
public class TarsuServiceException extends RuntimeException {

	public TarsuServiceException() {}
	
	public TarsuServiceException(String msg) {
		super(msg);		
	}
	
	public TarsuServiceException(Throwable t) {
		super(t);		
	}
	
}

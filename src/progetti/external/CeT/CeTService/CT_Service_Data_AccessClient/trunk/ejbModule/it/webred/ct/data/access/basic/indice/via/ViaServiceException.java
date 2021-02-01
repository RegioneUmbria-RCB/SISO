package it.webred.ct.data.access.basic.indice.via;


import javax.ejb.ApplicationException;

@ApplicationException
public class ViaServiceException extends RuntimeException{

	public ViaServiceException() {}
	
	public ViaServiceException(String msg) {
		super(msg);		
	}
	
	public ViaServiceException(Throwable t) {
		super(t);		
	}
	
}

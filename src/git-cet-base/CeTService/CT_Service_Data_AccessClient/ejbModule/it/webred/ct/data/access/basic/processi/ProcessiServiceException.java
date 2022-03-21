package it.webred.ct.data.access.basic.processi;


import javax.ejb.ApplicationException;

@ApplicationException
public class ProcessiServiceException extends RuntimeException {

	public ProcessiServiceException() {}
	
	public ProcessiServiceException(String msg) {
		super(msg);		
	}
	
	public ProcessiServiceException(Throwable t) {
		super(t);		
	}
	
}

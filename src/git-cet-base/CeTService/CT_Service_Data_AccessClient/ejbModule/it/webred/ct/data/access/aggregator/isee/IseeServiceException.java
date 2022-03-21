package it.webred.ct.data.access.aggregator.isee;


import javax.ejb.ApplicationException;

@ApplicationException
public class IseeServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IseeServiceException() {}
	
	public IseeServiceException(String msg) {
		super(msg);		
	}
	
	public IseeServiceException(Throwable t) {
		super(t);		
	}
	
}

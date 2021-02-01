package it.webred.ct.config.luoghi;

import javax.ejb.ApplicationException;

@ApplicationException
public class LuoghiServiceException extends RuntimeException{

	public LuoghiServiceException() {}
	
	public LuoghiServiceException(String msg) {
		super(msg);		
	}
	
	public LuoghiServiceException(Throwable t) {
		super(t);		
	}
	
}

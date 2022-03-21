package it.webred.amprofiler.ejb.user;

import javax.ejb.ApplicationException;

@ApplicationException
public class UserServiceException extends RuntimeException{

	public UserServiceException() {}
	
	public UserServiceException(String msg) {
		super(msg);		
	}
	
	public UserServiceException(Throwable t) {
		super(t);		
	}
	
}

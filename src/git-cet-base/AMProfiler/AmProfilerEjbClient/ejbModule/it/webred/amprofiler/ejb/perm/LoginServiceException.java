package it.webred.amprofiler.ejb.perm;

public class LoginServiceException extends RuntimeException {

	public LoginServiceException() {}
	
	public LoginServiceException(String msg) {
		super(msg);		
	}
	
	public LoginServiceException(Throwable t) {
		super(t);		
	}

}

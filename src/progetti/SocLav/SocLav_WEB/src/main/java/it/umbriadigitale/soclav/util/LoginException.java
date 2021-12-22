package it.umbriadigitale.soclav.util;
 
public class LoginException extends RuntimeException {

	public LoginException() {}
	
	public LoginException(String msg) {
		super(msg);		
	}
	
	public LoginException(Throwable t) {
		super(t);		
	}

}


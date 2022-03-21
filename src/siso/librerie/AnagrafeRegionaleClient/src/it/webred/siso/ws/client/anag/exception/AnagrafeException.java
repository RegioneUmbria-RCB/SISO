package it.webred.siso.ws.client.anag.exception;

import it.webred.siso.ws.client.client.exception.SisoClientException;

public class AnagrafeException extends SisoClientException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178802261568528858L;
	/**
	 * 
	 */
	
	
	  public AnagrafeException() { super(); }
	  public AnagrafeException(String message) { super(message); }
	  public AnagrafeException(String message, Throwable cause ) { super(message, cause); }
	  public AnagrafeException(Throwable cause) { super(cause); }
}

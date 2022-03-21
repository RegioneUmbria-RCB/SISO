package it.webred.siso.ws.client.atlante.exception;

import it.webred.siso.ws.client.client.exception.SisoClientException;

public class AtlanteException extends SisoClientException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178802261568528858L;
	/**
	 * 
	 */
	
	
	public AtlanteException() { super(); }
	  public AtlanteException(String message) { super(message); }
	  public AtlanteException(String message, Throwable cause ) { super(message, cause); }
	  public AtlanteException(Throwable cause) { super(cause); }
}

package it.webred.cs.sociosan.ejb.exception;

import it.webred.siso.ws.client.atlante.exception.AtlanteException;

public class SocioSanitarioException extends Exception {

	public SocioSanitarioException(Exception e) {
		super(e);
	}
	
	public SocioSanitarioException(String message , Exception e) {
		super(message, e);
	}
	
	public SocioSanitarioException(String message) {
		super(message);
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

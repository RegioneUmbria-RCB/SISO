package it.webred.siso.ws.client.client.exception;

public class FaultResponseException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178802261568528858L;
	/**
	 * 
	 */
	
	
	public FaultResponseException() { super(); }
	  public FaultResponseException(String message) { super(message); }
	  public FaultResponseException(String message, Throwable cause ) { super(message, cause); }
	  public FaultResponseException(Throwable cause) { super(cause); }
}

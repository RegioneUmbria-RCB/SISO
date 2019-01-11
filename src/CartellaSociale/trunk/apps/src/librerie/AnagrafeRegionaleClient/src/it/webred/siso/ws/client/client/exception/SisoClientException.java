package it.webred.siso.ws.client.client.exception;

public class SisoClientException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178802261568528858L;
	/**
	 * 
	 */
	
	
	public SisoClientException() { super(); }
	  public SisoClientException(String message) { super(message); }
	  public SisoClientException(String message, Throwable cause ) { super(message, cause); }
	  public SisoClientException(Throwable cause) { super(cause); }
}

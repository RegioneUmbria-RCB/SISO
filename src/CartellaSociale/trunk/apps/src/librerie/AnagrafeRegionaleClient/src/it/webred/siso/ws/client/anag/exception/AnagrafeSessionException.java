package it.webred.siso.ws.client.anag.exception;
import java.lang.Exception;

public class AnagrafeSessionException extends Exception{
	/**
	 * 
	 */
	public static final String ERROR_INVALID_LOGIN = "Dati di login non validi.";
	public static final String ERROR_NO_SESSION_OPENED = "Nessuna sessione aperta, effettuare il login.";
	public static final String ERROR_NO_SESSION_OPENED_ON_CLOSE = "Sessione non aperta, impossibile chiudere la sessione.";
	public static final String ERROR_CLOSE = "Risposta non prevista nella chiusura della sessione.";
	
	
	private static final long serialVersionUID = -5782211492250305579L;
	public AnagrafeSessionException() { super(); }
	  public AnagrafeSessionException(String message) { super(message); }
	  public AnagrafeSessionException(String message, Throwable cause ) { super(message, cause); }
	  public AnagrafeSessionException(Throwable cause) { super(cause); }
}

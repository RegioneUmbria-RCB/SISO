package it.webred.mailing;


/**
 * @author Alessandro Feriani
 * 
 */
public class MailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MailException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

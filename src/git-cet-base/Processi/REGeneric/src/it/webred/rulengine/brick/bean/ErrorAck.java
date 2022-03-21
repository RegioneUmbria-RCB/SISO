package it.webred.rulengine.brick.bean;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe per la gestione delle anomalie di tipo errore.
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public class ErrorAck extends CommandAck
{
	private Throwable exception;
	
	/**
	 * Costruttore per la creazione di un ErrorAck con il messaggio di errore
	 * @param message
	 */
	public ErrorAck(String message)
	{
		super(message);
	}
	

	/**
	 *  Tramite questo costruttore lo stacktrace viene scritto vome messaggio dell'ack
	*	@param t
	*/
	public ErrorAck(Throwable t) {

		super(t.getMessage());
		this.exception = t;
		
		/*
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		this.setMessage(stacktrace);
		*/
	}



	public Throwable getException() {
		return exception;
	}

}

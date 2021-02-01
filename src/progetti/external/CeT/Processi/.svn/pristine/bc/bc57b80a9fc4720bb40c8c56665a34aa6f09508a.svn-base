package it.webred.rulengine.brick.bean;

import java.io.Serializable;

/**
 * Classe per la gestione delle anomalie dei Command.
 * Ci possono essere tre tipi di anomalie:
 * - ErroreAck, quando c'è una eccezione nel command,<br>
 * - ApllicationAck, quando il COMMAND è andato bene, anche se ci sono anomalie,<br>
 * - RejectAck, quando il command non ha trovato i parametri per la sua esecuzione.<br>
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public abstract class CommandAck implements Serializable
{
	private String message;


	/**
	 * Costruttore per la creazione di un CommandAck con il messaggio di errore
	 * 
	 * @param message
	 */
	public CommandAck(String message)
	{
		this.message = message;
	}
	
	
	protected void appendMessage(String toAppend) {
		this.message += (" "+toAppend);
	}
	
	
	/**
	 * @return
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}



}

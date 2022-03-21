package it.webred.rulengine.brick.bean;

/**
 * Classe per la gestione delle anomalie di tipo warning bloccante.
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public class RejectAck extends CommandAck
{

	/**
	 * Costruttore per la creazione di un RejectAck con il messaggio di errore
	 * @param message
	 */
	public RejectAck(String message)
	{
		super(message);
	}


}

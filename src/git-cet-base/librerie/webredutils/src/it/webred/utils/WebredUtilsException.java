package it.webred.utils;

/**
 * Classe per la gestione delle eccezione all'interno del progetto RuleEngine.

 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2009/02/06 14:51:25 $
 */
public class WebredUtilsException extends Exception
{
	private static final long serialVersionUID = 790191233876005832L;

	/**
	 * Costruttore per la creazione di una WebredUtilsException in base al messaggio di errore.
	 * @param messaggio
	 */
	public WebredUtilsException(String messaggio)
	{
		super(messaggio);
	}
	public WebredUtilsException(String messaggio,Throwable e )
	{
		super(messaggio, e);
	}
	
}

package it.webred.rulengine.exception;

/**
 * Classe per la gestione delle eccezione all'interno del progetto RuleEngine.

 * 
 * @author sisani
 * @version $Revision: 1.2 $ $Date: 2008/09/10 09:11:29 $
 */
public class RulEngineException extends Exception
{
	private static final long serialVersionUID = 790191233876005832L;

	/**
	 * Costruttore per la creazione di una RulEngineException in base al messaggio di errore.
	 * @param messaggio
	 */
	public RulEngineException(String messaggio)
	{
		super(messaggio);
	}
	public RulEngineException(String messaggio,Throwable e )
	{
		super(messaggio, e);
	}
	
}

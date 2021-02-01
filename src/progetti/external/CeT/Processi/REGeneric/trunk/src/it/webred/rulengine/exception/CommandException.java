package it.webred.rulengine.exception;

/**
 * Classe per la gestione delle eccezione all'interno dei command del progetto RuleEngine.
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:25 $
 */
public class CommandException extends Exception
{
	private static final long serialVersionUID = -2322928333766720649L;

	/**
	 * Costruttore per la creazione die una CommandException in base a un eccezione 
	 * 
	 * @param e
	 */
	public CommandException(Exception e)
	{
		super(e);
	}

	/**
	 * Costruttore per la creazione die una CommandException in base a un messaggio 
	 * di errore.
	 * 
	 * @param e
	 */
	public CommandException(String e)
	{
		super(e);
	}
}

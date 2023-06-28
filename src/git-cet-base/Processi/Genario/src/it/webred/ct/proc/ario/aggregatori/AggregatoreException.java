package it.webred.ct.proc.ario.aggregatori;

import it.webred.rulengine.exception.RulEngineException;


public class AggregatoreException extends RulEngineException {

	public AggregatoreException(String messaggio) {
		super(messaggio);
	}
	public AggregatoreException(String messaggio,Throwable e)
	{
		super(messaggio, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

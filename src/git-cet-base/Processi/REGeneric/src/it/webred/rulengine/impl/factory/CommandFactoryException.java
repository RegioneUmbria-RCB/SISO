package it.webred.rulengine.impl.factory;

import it.webred.rulengine.exception.RulEngineException;

public class CommandFactoryException extends RulEngineException {

	public CommandFactoryException(String messaggio) {
		super(messaggio);
		// TODO Auto-generated constructor stub
	}

	public CommandFactoryException(String messaggio, Exception e) {
		super(messaggio,e);
		// TODO Auto-generated constructor stub
	}
	
}

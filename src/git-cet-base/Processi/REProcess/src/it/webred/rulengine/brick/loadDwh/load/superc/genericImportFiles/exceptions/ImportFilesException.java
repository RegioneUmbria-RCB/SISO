package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions;

import it.webred.rulengine.exception.RulEngineException;

public class ImportFilesException extends RulEngineException {

	public ImportFilesException(String messaggio) {
		super(messaggio);
		// TODO Auto-generated constructor stub
	}

	
	public ImportFilesException(String messaggio,Throwable e )
	{
		super(messaggio, e);
	}
	
}

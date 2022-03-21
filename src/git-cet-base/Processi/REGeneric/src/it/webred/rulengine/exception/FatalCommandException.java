package it.webred.rulengine.exception;

@SuppressWarnings("serial")
public class FatalCommandException extends Exception {
	
	public FatalCommandException(Exception e) {
		super(e);
	}
	
	public FatalCommandException(String e)
	{
		super(e);
	}
}

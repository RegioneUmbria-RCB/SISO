package it.webred.rulengine.brick.loadDwh.load.cnc;

public class CNCParseException extends Exception {
	
	public CNCParseException() {}
	
	public CNCParseException(Throwable t) {
		super(t);
	}
	
	public CNCParseException(String msg) {
		super(msg);
	}
}

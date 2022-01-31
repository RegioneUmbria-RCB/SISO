package it.webred.wsClient;

import javax.ejb.ApplicationException;

@ApplicationException
public class WsAvvocaturaException extends RuntimeException{

	public WsAvvocaturaException() {}
	
	public WsAvvocaturaException(String msg) {
		super(msg);		
	}
	
	public WsAvvocaturaException(Throwable t) {
		super(t);		
	}
	
}

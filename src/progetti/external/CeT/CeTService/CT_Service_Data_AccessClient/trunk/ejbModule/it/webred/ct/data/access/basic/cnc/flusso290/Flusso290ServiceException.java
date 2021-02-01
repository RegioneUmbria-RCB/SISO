package it.webred.ct.data.access.basic.cnc.flusso290;

import it.webred.ct.data.access.basic.cnc.CNCServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class Flusso290ServiceException extends CNCServiceException {

	public Flusso290ServiceException() {}
	
	public Flusso290ServiceException(String msg) {
		super(msg);		
	}
	
	public Flusso290ServiceException(Throwable t) {
		super(t);		
	}
	
}

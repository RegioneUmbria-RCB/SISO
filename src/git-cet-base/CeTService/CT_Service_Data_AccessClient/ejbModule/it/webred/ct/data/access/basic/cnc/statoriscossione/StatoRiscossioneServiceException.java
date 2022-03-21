package it.webred.ct.data.access.basic.cnc.statoriscossione;

import javax.ejb.ApplicationException;

import it.webred.ct.data.access.basic.cnc.CNCServiceException;

@ApplicationException
public class StatoRiscossioneServiceException extends CNCServiceException {

	public StatoRiscossioneServiceException() {}
	
	public StatoRiscossioneServiceException(String msg) {
		super(msg);		
	}
	
	public StatoRiscossioneServiceException(Throwable t) {
		super(t);		
	}

}

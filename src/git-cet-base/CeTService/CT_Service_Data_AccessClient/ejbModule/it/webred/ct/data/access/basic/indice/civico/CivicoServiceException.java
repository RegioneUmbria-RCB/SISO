package it.webred.ct.data.access.basic.indice.civico;

import javax.ejb.ApplicationException;

@ApplicationException
public class CivicoServiceException extends RuntimeException{

	public CivicoServiceException() {}
	
	public CivicoServiceException(String msg) {
		super(msg);		
	}
	
	public CivicoServiceException(Throwable t) {
		super(t);		
	}
	
}

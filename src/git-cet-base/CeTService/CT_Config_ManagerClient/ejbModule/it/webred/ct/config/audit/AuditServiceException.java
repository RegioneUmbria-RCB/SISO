package it.webred.ct.config.audit;

import javax.ejb.ApplicationException;

@ApplicationException
public class AuditServiceException extends RuntimeException{

	public AuditServiceException() {}
	
	public AuditServiceException(String msg) {
		super(msg);		
	}
	
	public AuditServiceException(Throwable t) {
		super(t);		
	}
	
}

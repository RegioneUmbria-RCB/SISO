package it.webred.ct.data.access.basic.diagnostiche.ici;

import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class DiagnosticheIciServiceException extends DiagnosticheServiceException {
	
	public DiagnosticheIciServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheIciServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheIciServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheIciServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

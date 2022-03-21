package it.webred.ct.data.access.basic.diagnostiche;

import javax.ejb.ApplicationException;

@ApplicationException
public class DiagnosticheServiceException extends RuntimeException {
	
	public DiagnosticheServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

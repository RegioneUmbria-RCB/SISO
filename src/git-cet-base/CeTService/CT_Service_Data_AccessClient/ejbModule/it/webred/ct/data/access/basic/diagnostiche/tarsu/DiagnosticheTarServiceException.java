package it.webred.ct.data.access.basic.diagnostiche.tarsu;

import it.webred.ct.data.access.basic.diagnostiche.DiagnosticheServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class DiagnosticheTarServiceException extends DiagnosticheServiceException {
	
	public DiagnosticheTarServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheTarServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheTarServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DiagnosticheTarServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

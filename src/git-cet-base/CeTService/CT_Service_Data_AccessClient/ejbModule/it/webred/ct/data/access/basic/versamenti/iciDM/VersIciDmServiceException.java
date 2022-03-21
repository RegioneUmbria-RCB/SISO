package it.webred.ct.data.access.basic.versamenti.iciDM;

import javax.ejb.ApplicationException;

@ApplicationException
public class VersIciDmServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public VersIciDmServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public VersIciDmServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public VersIciDmServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public VersIciDmServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

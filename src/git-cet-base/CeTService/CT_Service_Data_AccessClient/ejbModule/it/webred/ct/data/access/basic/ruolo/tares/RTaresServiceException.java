package it.webred.ct.data.access.basic.ruolo.tares;

import javax.ejb.ApplicationException;

@ApplicationException
public class RTaresServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RTaresServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RTaresServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RTaresServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RTaresServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

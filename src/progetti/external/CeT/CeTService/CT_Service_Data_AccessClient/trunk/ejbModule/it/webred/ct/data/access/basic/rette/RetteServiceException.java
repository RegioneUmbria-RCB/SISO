package it.webred.ct.data.access.basic.rette;

import javax.ejb.ApplicationException;

@ApplicationException
public class RetteServiceException extends RuntimeException {
	
	public RetteServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RetteServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RetteServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RetteServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

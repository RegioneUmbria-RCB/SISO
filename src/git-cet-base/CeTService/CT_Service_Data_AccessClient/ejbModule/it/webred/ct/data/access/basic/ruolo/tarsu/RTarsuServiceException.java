package it.webred.ct.data.access.basic.ruolo.tarsu;

import javax.ejb.ApplicationException;

@ApplicationException
public class RTarsuServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RTarsuServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RTarsuServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RTarsuServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RTarsuServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}

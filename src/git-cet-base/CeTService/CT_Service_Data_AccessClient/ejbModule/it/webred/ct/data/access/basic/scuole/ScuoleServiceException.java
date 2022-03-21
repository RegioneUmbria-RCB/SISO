package it.webred.ct.data.access.basic.scuole;

import javax.ejb.ApplicationException;

@ApplicationException
public class ScuoleServiceException extends RuntimeException {
	
	public ScuoleServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ScuoleServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScuoleServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ScuoleServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

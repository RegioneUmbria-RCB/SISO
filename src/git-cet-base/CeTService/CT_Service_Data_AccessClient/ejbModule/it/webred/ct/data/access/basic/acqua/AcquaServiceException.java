package it.webred.ct.data.access.basic.acqua;

import javax.ejb.ApplicationException;

@ApplicationException
public class AcquaServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public AcquaServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public AcquaServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AcquaServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public AcquaServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

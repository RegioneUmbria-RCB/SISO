package it.webred.ct.data.access.basic.cosap;

import javax.ejb.ApplicationException;

@ApplicationException
public class CosapServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CosapServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CosapServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CosapServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CosapServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

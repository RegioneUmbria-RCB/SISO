package it.webred.ct.data.access.basic.f24;

import javax.ejb.ApplicationException;

@ApplicationException
public class F24ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public F24ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public F24ServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public F24ServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public F24ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

package it.webred.ct.data.access.basic.redditi;

import javax.ejb.ApplicationException;

@ApplicationException
public class RedditiServiceException extends RuntimeException {

	public RedditiServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RedditiServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RedditiServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public RedditiServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}

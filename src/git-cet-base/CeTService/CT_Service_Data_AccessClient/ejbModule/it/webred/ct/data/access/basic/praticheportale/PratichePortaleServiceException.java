package it.webred.ct.data.access.basic.praticheportale;

import javax.ejb.ApplicationException;

@ApplicationException
public class PratichePortaleServiceException extends RuntimeException {
	
	public PratichePortaleServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public PratichePortaleServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PratichePortaleServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public PratichePortaleServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

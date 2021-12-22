package it.webred.cs.csa.ejb.client;

import javax.ejb.ApplicationException;

@ApplicationException
public class CarSocialeServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CarSocialeServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CarSocialeServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CarSocialeServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CarSocialeServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

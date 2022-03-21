package it.webred.ct.rulengine.service.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class VerificaException extends RuntimeException {

	public VerificaException(String cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

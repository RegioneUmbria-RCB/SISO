package it.webred.ct.rulengine.service.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class ServiceException extends RuntimeException {

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

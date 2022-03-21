package it.webred.ct.data.access.basic.traffico;

import javax.ejb.ApplicationException;

@ApplicationException
public class TrafficoServiceException extends RuntimeException {
	
	public TrafficoServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public TrafficoServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public TrafficoServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public TrafficoServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

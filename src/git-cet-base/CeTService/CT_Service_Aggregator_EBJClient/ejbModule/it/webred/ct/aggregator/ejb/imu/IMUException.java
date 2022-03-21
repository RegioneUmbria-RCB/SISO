package it.webred.ct.aggregator.ejb.imu;

import javax.ejb.ApplicationException;

@ApplicationException
public class IMUException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IMUException() {
		super();
		
	}

	public IMUException(String s, Throwable t) {
		super(s, t);
		
	}

	public IMUException(String s) {
		super(s);
		
	}

	public IMUException(Throwable t) {
		super(t);
		
	}

}
package it.webred.ct.data.access.basic.imu;

import javax.ejb.ApplicationException;

@ApplicationException
public class SaldoImuServiceException extends RuntimeException {
	public SaldoImuServiceException() {}
	
	public SaldoImuServiceException(String msg) {
		super(msg);		
	}
	
	public SaldoImuServiceException(Throwable t) {
		super(t);		
	}
}

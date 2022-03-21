package it.webred.ct.controller.ejbclient.utilities;



import javax.ejb.ApplicationException;

@ApplicationException
public class ControllerUtilitiesServiceException extends RuntimeException {
	
	public ControllerUtilitiesServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ControllerUtilitiesServiceException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ControllerUtilitiesServiceException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ControllerUtilitiesServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

}

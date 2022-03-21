package it.webred.ct.data.access.basic.anagrafe;

import javax.ejb.ApplicationException;

@ApplicationException
public class AnagrafeException extends RuntimeException {

	public AnagrafeException() {
		super();
		
	}

	public AnagrafeException(String s, Throwable t) {
		super(s, t);
		
	}

	public AnagrafeException(String s) {
		super(s);
		
	}

	public AnagrafeException(Throwable t) {
		super(t);
		
	}

}

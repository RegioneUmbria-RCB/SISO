package it.webred.ct.aggregator.ejb.immobiliAccatastati;

import javax.ejb.ApplicationException;

@ApplicationException
public class ImmobiliAccatastatiException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ImmobiliAccatastatiException() {
		super();
		
	}

	public ImmobiliAccatastatiException(String s, Throwable t) {
		super(s, t);
		
	}

	public ImmobiliAccatastatiException(String s) {
		super(s);
		
	}

	public ImmobiliAccatastatiException(Throwable t) {
		super(t);
		
	}

}
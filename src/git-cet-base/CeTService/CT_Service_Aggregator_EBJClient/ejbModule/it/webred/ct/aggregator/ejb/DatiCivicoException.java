package it.webred.ct.aggregator.ejb;

import javax.ejb.ApplicationException;

@ApplicationException
public class DatiCivicoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatiCivicoException() {
		super();
		
	}

	public DatiCivicoException(String s, Throwable t) {
		super(s, t);
		
	}

	public DatiCivicoException(String s) {
		super(s);
		
	}

	public DatiCivicoException(Throwable t) {
		super(t);
		
	}

}
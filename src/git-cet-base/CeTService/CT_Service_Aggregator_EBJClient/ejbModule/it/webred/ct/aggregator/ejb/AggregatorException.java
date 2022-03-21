package it.webred.ct.aggregator.ejb;

import javax.ejb.ApplicationException;

@ApplicationException
public class AggregatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AggregatorException() {
		super();
		
	}

	public AggregatorException(String s, Throwable t) {
		super(s, t);
		
	}

	public AggregatorException(String s) {
		super(s);
		
	}

	public AggregatorException(Throwable t) {
		super(t);
		
	}

}
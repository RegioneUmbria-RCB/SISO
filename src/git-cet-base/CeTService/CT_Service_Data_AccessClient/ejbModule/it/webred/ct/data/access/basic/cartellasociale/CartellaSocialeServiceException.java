package it.webred.ct.data.access.basic.cartellasociale;

import javax.ejb.ApplicationException;

@ApplicationException
public class CartellaSocialeServiceException extends RuntimeException {

	public CartellaSocialeServiceException() {
		super();
		
	}

		public CartellaSocialeServiceException(String arg0) {
		super(arg0);
		
	}

	public CartellaSocialeServiceException(Throwable arg0) {
		super(arg0);
		
	}

}

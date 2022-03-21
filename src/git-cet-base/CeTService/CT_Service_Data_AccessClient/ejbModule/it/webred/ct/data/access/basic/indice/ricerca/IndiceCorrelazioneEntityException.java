package it.webred.ct.data.access.basic.indice.ricerca;

import it.webred.ct.data.access.basic.indice.IndiceServiceException;

import javax.ejb.ApplicationException;

@ApplicationException
public class IndiceCorrelazioneEntityException extends IndiceServiceException {
	
	public IndiceCorrelazioneEntityException() {
		super();
	}
	
	public IndiceCorrelazioneEntityException(String msg) {
		super(msg);		
	}
	
	public IndiceCorrelazioneEntityException(Throwable t) {
		super(t);		
	}
}

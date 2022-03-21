package it.webred.ct.data.access.basic.indice.ricerca;

import javax.ejb.ApplicationException;

import it.webred.ct.data.access.basic.indice.IndiceServiceException;

@ApplicationException
public class IndiceCorrelazioneServiceException extends IndiceServiceException {

	public IndiceCorrelazioneServiceException() {}
	
	public IndiceCorrelazioneServiceException(String msg) {
		super(msg);		
	}
	
	public IndiceCorrelazioneServiceException(Throwable t) {
		super(t);		
	}

}

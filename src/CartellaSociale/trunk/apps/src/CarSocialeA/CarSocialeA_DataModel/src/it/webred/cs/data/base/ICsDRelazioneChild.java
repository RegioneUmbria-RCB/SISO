package it.webred.cs.data.base;

import it.webred.cs.data.model.CsDRelazione;

import java.io.Serializable;

public interface ICsDRelazioneChild extends Serializable {

	public Long getRelazioneId();

	public void setRelazioneId(Long diarioId);

	public CsDRelazione getCsDRelazione();

	public void setCsDRelazione(CsDRelazione csDRelazione);
	
}
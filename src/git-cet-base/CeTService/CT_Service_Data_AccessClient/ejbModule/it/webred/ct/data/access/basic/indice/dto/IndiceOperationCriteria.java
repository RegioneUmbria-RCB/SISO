package it.webred.ct.data.access.basic.indice.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class IndiceOperationCriteria extends CeTBaseObject implements Serializable{

	private String hash;
	private long idSorgente;
	private String progressivo;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public long getIdSorgente() {
		return idSorgente;
	}
	public void setIdSorgente(long idSorgente) {
		this.idSorgente = idSorgente;
	}
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	
}

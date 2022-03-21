package it.webred.ct.data.access.basic.pregeo.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class RicercaPregeoDTO extends CeTBaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date dataRif; 
	private String foglio;
	private String particella;
	private String nomeFilePdf;
	private boolean foglioPregeo=false; // Se la ricerca con questo valore attivo si ricerca direttamente il valore passato, altrimenti (es.foglio catastale) 
	                                    //si fa la substring del campo di PregeoInfo
	private Integer limit = 0;
	
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public Date getDataRif() {
		return dataRif;
	}
	public void setDataRif(Date dataRif) {
		this.dataRif = dataRif;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public boolean isFoglioPregeo() {
		return foglioPregeo;
	}
	public void setFoglioPregeo(boolean foglioPregeo) {
		this.foglioPregeo = foglioPregeo;
	}
	public String getNomeFilePdf() {
		return nomeFilePdf;
	}
	public void setNomeFilePdf(String nomeFilePdf) {
		this.nomeFilePdf = nomeFilePdf;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}

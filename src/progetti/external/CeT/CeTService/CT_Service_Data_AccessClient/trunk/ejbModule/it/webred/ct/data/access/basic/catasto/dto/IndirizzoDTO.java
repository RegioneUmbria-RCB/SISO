package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class IndirizzoDTO extends CeTBaseObject{
	
	private static final long serialVersionUID = 1L;
	private String numCivico;
	private String strada;
	private String idCivico;
	private Integer idImmobile;
	private Integer progressivo;
	private Integer seq;
	private String sezione;
	
	public String getNumCivico() {
		return numCivico;
	}
	public void setNumCivico(String numCivico) {
		this.numCivico = numCivico;
	}
	public String getStrada() {
		return strada;
	}
	public void setStrada(String strada) {
		this.strada = strada;
	}
	public void setIdCivico(String idCivico) {
		this.idCivico = idCivico;
	}
	public String getIdCivico() {
		return idCivico;
	}
	public Integer getIdImmobile() {
		return idImmobile;
	}
	public void setIdImmobile(Integer idImmobile) {
		this.idImmobile = idImmobile;
	}
	public Integer getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	
}

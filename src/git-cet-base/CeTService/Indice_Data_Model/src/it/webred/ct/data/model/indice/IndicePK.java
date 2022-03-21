package it.webred.ct.data.model.indice;

import java.io.Serializable;

public class IndicePK implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ctrHash;
	private long fkEnteSorgente;
	private String idDwh;
	private long progEs;
	
	public String getCtrHash() {
		return ctrHash;
	}
	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}
	public long getFkEnteSorgente() {
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(long fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}
	public String getIdDwh() {
		return idDwh;
	}
	public void setIdDwh(String idDwh) {
		this.idDwh = idDwh;
	}
	public long getProgEs() {
		return progEs;
	}
	public void setProgEs(long progEs) {
		this.progEs = progEs;
	}
	
	
	
	
}

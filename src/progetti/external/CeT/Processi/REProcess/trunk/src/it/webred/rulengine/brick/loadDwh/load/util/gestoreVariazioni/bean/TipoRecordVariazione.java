package it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni.bean;

public class TipoRecordVariazione {
	
	private Integer fkEnteSorgente;
	private Integer progEs;
	private String  tipoDato;
	
	public TipoRecordVariazione(Integer fkEnte, Integer progEs, String tipoDato){
		this.fkEnteSorgente=fkEnte;
		this.progEs=progEs;
		this.tipoDato=tipoDato;
	}
	
	public Integer getFkEnteSorgente() {
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(Integer fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}
	public Integer getProgEs() {
		return progEs;
	}
	public void setProgEs(Integer progEs) {
		this.progEs = progEs;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	
}

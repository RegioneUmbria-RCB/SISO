package it.webred.rulengine.brick.loadDwh.load.util.gestoreVariazioni.bean;

public class SitCorrelazioneVariazioni {
	
	private String  idDwh;
	private Integer fkEnteSorgente;
	private Integer progEs;
	private String  tipoDato;
	private String  tipoVariazione;
	
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
	public String getIdDwh() {
		return idDwh;
	}
	public void setIdDwh(String idDwh) {
		this.idDwh = idDwh;
	}
	public String getTipoVariazione() {
		return tipoVariazione;
	}
	public void setTipoVariazione(String tipoVariazione) {
		this.tipoVariazione = tipoVariazione;
	}
	
}

package it.webred.ct.data.access.basic.indice.civico.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaCivicoDTO;

public class RicercaCivicoIndiceDTO extends RicercaCivicoDTO {
	
	private static final long serialVersionUID = 1L;
	private String enteSorgente;
	private String progEs;
	
	private String destEnteSorgente;
	private String destProgEs;
	
	public String getEnteSorgente() {
		return enteSorgente;
	}
	public void setEnteSorgente(String enteSorgente) {
		this.enteSorgente = enteSorgente;
	}
	public String getProgEs() {
		return progEs;
	}
	public void setProgEs(String progEs) {
		this.progEs = progEs;
	}
	public String getDestEnteSorgente() {
		return destEnteSorgente;
	}
	public void setDestEnteSorgente(String destEnteSorgente) {
		this.destEnteSorgente = destEnteSorgente;
	}
	public String getDestProgEs() {
		return destProgEs;
	}
	public void setDestProgEs(String destProgEs) {
		this.destProgEs = destProgEs;
	}
	
	
	
}

package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsDColloquio;

import java.io.Serializable;

public class ColloquioDTO implements Serializable {

	private CsDColloquio colloquio;
	private boolean riservato;
	private boolean abilitato4riservato;
	
	private Long diarioDoveSelected;
	private Long diarioConChiSelected;
	private Long diarioTipoSelected;
	
	public CsDColloquio getColloquio() {
		return colloquio;
	}
	public void setColloquio(CsDColloquio colloquio) {
		this.colloquio = colloquio;
	}
	public boolean isRiservato() {
		return riservato;
	}
	public void setRiservato(boolean riservato) {
		this.riservato = riservato;
	}
	public boolean isAbilitato4riservato() {
		return abilitato4riservato;
	}
	public void setAbilitato4riservato(boolean abilitato4riservato) {
		this.abilitato4riservato = abilitato4riservato;
	}
	public Long getDiarioDoveSelected() {
		return diarioDoveSelected;
	}
	public void setDiarioDoveSelected(Long diarioDoveSelected) {
		this.diarioDoveSelected = diarioDoveSelected;
	}
	public Long getDiarioConChiSelected() {
		return diarioConChiSelected;
	}
	public void setDiarioConChiSelected(Long diarioConChiSelected) {
		this.diarioConChiSelected = diarioConChiSelected;
	}
	public Long getDiarioTipoSelected() {
		return diarioTipoSelected;
	}
	public void setDiarioTipoSelected(Long diarioTipoSelected) {
		this.diarioTipoSelected = diarioTipoSelected;
	}
	
	
	
}

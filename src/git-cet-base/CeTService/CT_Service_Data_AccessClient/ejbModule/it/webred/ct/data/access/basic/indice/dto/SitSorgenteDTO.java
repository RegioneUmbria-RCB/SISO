package it.webred.ct.data.access.basic.indice.dto;

import it.webred.ct.data.model.indice.SitEnteSorgente;

import java.io.Serializable;
import java.util.List;

public class SitSorgenteDTO implements Serializable{

	private SitEnteSorgente enteSorgente;
	
	private String progressivoES;
	
	private String informazione;
	
	private List listaTotali;

	public SitEnteSorgente getEnteSorgente() {
		return enteSorgente;
	}

	public void setEnteSorgente(SitEnteSorgente enteSorgente) {
		this.enteSorgente = enteSorgente;
	}

	public String getProgressivoES() {
		return progressivoES;
	}

	public void setProgressivoES(String progressivoES) {
		this.progressivoES = progressivoES;
	}

	public List getListaTotali() {
		return listaTotali;
	}

	public void setListaTotali(List listaTotali) {
		this.listaTotali = listaTotali;
	}

	public String getInformazione() {
		return informazione;
	}

	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}
	
}

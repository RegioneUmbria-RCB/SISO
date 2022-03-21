package it.webred.ct.data.access.basic.indice.via.dto;

import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitViaUnico;

import java.io.Serializable;

public class SitViaDTO extends SitOperationDTO implements Serializable{

	private String sedIme;
	private String indirizzo;
	private String fonteDati;
	private long fkEnteSorgente;
	private SitViaUnico sitViaUnico;
	
	public SitViaUnico getSitViaUnico() {
		return sitViaUnico;
	}
	public void setSitViaUnico(SitViaUnico sitViaUnico) {
		this.sitViaUnico = sitViaUnico;
	}
	public String getSedIme() {
		return sedIme;
	}
	public void setSedIme(String sedIme) {
		this.sedIme = sedIme;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getFonteDati() {
		return fonteDati;
	}
	public void setFonteDati(String fonteDati) {
		this.fonteDati = fonteDati;
	}
	public long getFkEnteSorgente() {
		return fkEnteSorgente;
	}
	public void setFkEnteSorgente(long fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}
}

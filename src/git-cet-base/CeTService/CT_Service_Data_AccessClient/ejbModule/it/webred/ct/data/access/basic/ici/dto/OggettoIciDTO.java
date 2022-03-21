package it.webred.ct.data.access.basic.ici.dto;
import it.webred.ct.data.model.ici.SitTIciOggetto;

import java.io.Serializable;
//RAPPRESENTA GLI OGGETTI PRESENTI IN SIT_T_ICI_OGGETTO PER UN CONTRIBUENTE
public class OggettoIciDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titolo; //
	private SitTIciOggetto oggettoIci;
	
	public OggettoIciDTO(String titolo, SitTIciOggetto oggettoIci){
		this.titolo =titolo;
		this.oggettoIci=oggettoIci;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public SitTIciOggetto getOggettoIci() {
		return oggettoIci;
	}
	public void setOggettoIci(SitTIciOggetto oggettoIci) {
		this.oggettoIci = oggettoIci;
	}
	
}


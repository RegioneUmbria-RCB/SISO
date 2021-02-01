package it.webred.ct.data.access.basic.tarsu.dto;


import it.webred.ct.data.model.tarsu.SitTTarOggetto;

import java.io.Serializable;

public class OggettoTarsuDTO implements Serializable {
	/**
	 * RAPPRESENTA GLI OGGETTI PRESENTI IN SIT_T_TAR_OGGETTO PER UN SOGGETTO
	 */
	private static final long serialVersionUID = 1L;
	private String titolo; //
	private SitTTarOggetto oggettoTarsu;
	
	public OggettoTarsuDTO() {
		super();
	}
	
	public OggettoTarsuDTO(String titolo, SitTTarOggetto oggettoTarsu) {
		super();
		this.titolo=titolo;
		this.oggettoTarsu=oggettoTarsu;
	}
	
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public SitTTarOggetto getOggettoTarsu() {
		return oggettoTarsu;
	}
	public void setOggettoTarsu(SitTTarOggetto oggettoTarsu) {
		this.oggettoTarsu = oggettoTarsu;
	}
	
}

package it.webred.cs.csa.ejb.dto.cartella;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class DatiAggCasoGitDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedHashMap <String, String> elementiVariati;
	private LinkedHashMap <String, String> elementiOriginali;
	
	private String tipologiaVariazione;
	
	public LinkedHashMap <String, String> getElementiVariati() {
		return elementiVariati;
	}
	public void setElementiVariati(LinkedHashMap <String, String> elementiVariati) {
		this.elementiVariati = elementiVariati;
	}
	public void setElementiOriginali(LinkedHashMap<String, String> elementiOriginali) {
		this.elementiOriginali = elementiOriginali;
	}
	public String getTipologiaVariazione() {
		return tipologiaVariazione;
	}
	public void setTipologiaVariazione(String tipologiaVariazione) {
		this.tipologiaVariazione = tipologiaVariazione;
	}
	public LinkedHashMap <String, String> getElementiOriginali() {
		return elementiOriginali;
	}
}

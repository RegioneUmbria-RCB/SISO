package it.webred.cs.csa.ejb.dto.rest;
 

import java.io.Serializable;
import java.util.List;

public class TrascodificheResponseDTO implements Serializable {

	private int esito;
	public int getEsito() {
		return esito;
	}
	public void setEsito(int esito) {
		this.esito = esito;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	private String descrizione;
	private static final long serialVersionUID = 7609974482932118973L;
	private List<String> intervento;
	private List<InterventoDTO> interventoCustom;
	private List<TabellaDecodificaBaseDTO> titoloStudio;
	private List<TabellaDecodificaBaseDTO> tipologiaFamiglia;
	private List<TabellaDecodificaDTO> gruppoVulnerabile;
	private List<TabellaDecodificaExtDTO> statoCivile;
	private List<TabellaDecodificaBaseDTO> condLavoro;
	
	
	
	
	public List<TabellaDecodificaBaseDTO> getCondLavoro() {
		return condLavoro;
	}
	public void setCondLavoro(List<TabellaDecodificaBaseDTO> condLavoro) {
		this.condLavoro = condLavoro;
	}
	public List<TabellaDecodificaDTO> getGruppoVulnerabile() {
		return gruppoVulnerabile;
	}
	public void setGruppoVulnerabile(List<TabellaDecodificaDTO> gruppoVulnerabile) {
		this.gruppoVulnerabile = gruppoVulnerabile;
	}
	public List<TabellaDecodificaExtDTO> getStatoCivile() {
		return statoCivile;
	}
	public void setStatoCivile(List<TabellaDecodificaExtDTO> statoCivile) {
		this.statoCivile = statoCivile;
	}
	public List<TabellaDecodificaBaseDTO> getTitoloStudio() {
		return titoloStudio;
	}
	public void setTitoloStudio(List<TabellaDecodificaBaseDTO> titoloStudio) {
		this.titoloStudio = titoloStudio;
	}
	public List<TabellaDecodificaBaseDTO> getTipologiaFamiglia() {
		return tipologiaFamiglia;
	}
	public void setTipologiaFamiglia(List<TabellaDecodificaBaseDTO> tipologiaFamiglia) {
		this.tipologiaFamiglia = tipologiaFamiglia;
	}
	public List<InterventoDTO> getInterventoCustom() {
		return interventoCustom;
	}
	public void setInterventoCustom(List<InterventoDTO> interventoCustom) {
		this.interventoCustom = interventoCustom;
	}
	public List<String> getIntervento() {
		return intervento;
	}
	public void setIntervento(List<String> intervento) {
		this.intervento = intervento;
	}
 
	
			
}

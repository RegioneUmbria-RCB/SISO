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
	private List<TabellaDecodificaBaseDTO> tiCodIstat;
	private List<TabellaDecodificaBaseDTO> fseCondIngMercatoLavoro;
	private List<TabellaDecodificaBaseDTO> fseGruppoVulnerabile;
	private List<TabellaDecodificaBaseDTO> fseTitoloStudio;
	private List<TabellaDecodificaBaseDTO> fseDurataRicercaLavoro;
	private List<TabellaDecodificaBaseDTO> fseTipologiaLavoro;
	private List<TabellaDecodificaBaseDTO> fseOrarioLavoro;
	private List<TabellaDecodificaBaseDTO> fseAzDimensione;
	
	/* Per estrarre queste informazioni serve l'ente TITOLARE, non è sufficiente il capofila*/
	/*private List<TabellaDecodificaBaseDTO> fonteFinanziamento;
	private List<TabellaDecodificaBaseDTO> progetto;
	private List<TabellaDecodificaBaseDTO> sottocorsoAttivita;*/

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
	public List<TabellaDecodificaBaseDTO> getTiCodIstat() {
		return tiCodIstat;
	}
	public void setTiCodIstat(List<TabellaDecodificaBaseDTO> tiCodIstat) {
		this.tiCodIstat = tiCodIstat;
	}
	public List<TabellaDecodificaBaseDTO> getFseGruppoVulnerabile() {
		return fseGruppoVulnerabile;
	}
	public void setFseGruppoVulnerabile(
			List<TabellaDecodificaBaseDTO> fseGruppoVulnerabile) {
		this.fseGruppoVulnerabile = fseGruppoVulnerabile;
	}
	public List<TabellaDecodificaBaseDTO> getFseTitoloStudio() {
		return fseTitoloStudio;
	}
	public void setFseTitoloStudio(List<TabellaDecodificaBaseDTO> fseTitoloStudio) {
		this.fseTitoloStudio = fseTitoloStudio;
	}
	public List<TabellaDecodificaBaseDTO> getFseDurataRicercaLavoro() {
		return fseDurataRicercaLavoro;
	}
	public void setFseDurataRicercaLavoro(
			List<TabellaDecodificaBaseDTO> fseDurataRicercaLavoro) {
		this.fseDurataRicercaLavoro = fseDurataRicercaLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getFseTipologiaLavoro() {
		return fseTipologiaLavoro;
	}
	public void setFseTipologiaLavoro(
			List<TabellaDecodificaBaseDTO> fseTipologiaLavoro) {
		this.fseTipologiaLavoro = fseTipologiaLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getFseOrarioLavoro() {
		return fseOrarioLavoro;
	}
	public void setFseOrarioLavoro(List<TabellaDecodificaBaseDTO> fseOrarioLavoro) {
		this.fseOrarioLavoro = fseOrarioLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getFseAzDimensione() {
		return fseAzDimensione;
	}
	public void setFseAzDimensione(List<TabellaDecodificaBaseDTO> fseAzDimensione) {
		this.fseAzDimensione = fseAzDimensione;
	}
	public List<TabellaDecodificaBaseDTO> getFseCondIngMercatoLavoro() {
		return fseCondIngMercatoLavoro;
	}
	public void setFseCondIngMercatoLavoro(
			List<TabellaDecodificaBaseDTO> fseCondIngMercatoLavoro) {
		this.fseCondIngMercatoLavoro = fseCondIngMercatoLavoro;
	}
		
}

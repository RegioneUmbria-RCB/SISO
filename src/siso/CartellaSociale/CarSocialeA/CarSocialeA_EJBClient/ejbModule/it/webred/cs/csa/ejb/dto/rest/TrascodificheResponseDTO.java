package it.webred.cs.csa.ejb.dto.rest;
 

import java.io.Serializable;
import java.util.List;

public class TrascodificheResponseDTO implements Serializable {

	private int esito;
	private String descrizione;
	private static final long serialVersionUID = 7609974482932118973L;
	private List<String> tbServizi; //intervento;
	private List<InterventoDTO> tbPrestazioni; //interventoCustom;
	private List<TabellaDecodificaBaseDTO> tbTitoloStudio;
	private List<TabellaDecodificaBaseDTO> tbTipologiaFamiliare;
	private List<TabellaDecodificaDTO> tbGruppoVulnerabile;
	private List<TabellaDecodificaExtDTO> tbStatoCivile;
	private List<TabellaDecodificaBaseDTO> tbCondLavoro;
	
	private List<TabellaDecodificaExtDTO> tbTiCodIstat;
	/* Per estrarre queste informazioni serve l'ente TITOLARE, non è sufficiente il capofila*/
	private List<TabellaDecodificaBaseDTO> tbFonteFinanziamento;
	private List<TabellaDecodificaBaseDTO> tbProgetto;
	private List<TabellaDecodificaExtDTO> tbSottocorsoAttivita;
	
	private List<TabellaDecodificaBaseDTO> tbFseGruppoVulnerabile;	
	private List<TabellaDecodificaBaseDTO> tbFseTitoloStudio;
	private List<TabellaDecodificaBaseDTO> tbFseCondIngMercatoLavoro;
	private List<TabellaDecodificaBaseDTO> tbFseDurataRicercaLavoro;
	private List<TabellaDecodificaBaseDTO> tbFseTipologiaLavoro;
	private List<TabellaDecodificaBaseDTO> tbFseOrarioLavoro;
	private List<TabellaDecodificaBaseDTO> tbFseAzDimensione;
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
	public List<String> getTbServizi() {
		return tbServizi;
	}
	public void setTbServizi(List<String> tbServizi) {
		this.tbServizi = tbServizi;
	}
	public List<InterventoDTO> getTbPrestazioni() {
		return tbPrestazioni;
	}
	public void setTbPrestazioni(List<InterventoDTO> tbPrestazioni) {
		this.tbPrestazioni = tbPrestazioni;
	}
	public List<TabellaDecodificaBaseDTO> getTbTitoloStudio() {
		return tbTitoloStudio;
	}
	public void setTbTitoloStudio(List<TabellaDecodificaBaseDTO> tbTitoloStudio) {
		this.tbTitoloStudio = tbTitoloStudio;
	}
	public List<TabellaDecodificaBaseDTO> getTbTipologiaFamiliare() {
		return tbTipologiaFamiliare;
	}
	public void setTbTipologiaFamiliare(List<TabellaDecodificaBaseDTO> tbTipologiaFamiliare) {
		this.tbTipologiaFamiliare = tbTipologiaFamiliare;
	}
	public List<TabellaDecodificaDTO> getTbGruppoVulnerabile() {
		return tbGruppoVulnerabile;
	}
	public void setTbGruppoVulnerabile(List<TabellaDecodificaDTO> tbGruppoVulnerabile) {
		this.tbGruppoVulnerabile = tbGruppoVulnerabile;
	}
	public List<TabellaDecodificaExtDTO> getTbStatoCivile() {
		return tbStatoCivile;
	}
	public void setTbStatoCivile(List<TabellaDecodificaExtDTO> tbStatoCivile) {
		this.tbStatoCivile = tbStatoCivile;
	}
	public List<TabellaDecodificaBaseDTO> getTbCondLavoro() {
		return tbCondLavoro;
	}
	public void setTbCondLavoro(List<TabellaDecodificaBaseDTO> tbCondLavoro) {
		this.tbCondLavoro = tbCondLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseGruppoVulnerabile() {
		return tbFseGruppoVulnerabile;
	}
	public void setTbFseGruppoVulnerabile(List<TabellaDecodificaBaseDTO> tbFseGruppoVulnerabile) {
		this.tbFseGruppoVulnerabile = tbFseGruppoVulnerabile;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseTitoloStudio() {
		return tbFseTitoloStudio;
	}
	public void setTbFseTitoloStudio(List<TabellaDecodificaBaseDTO> tbFseTitoloStudio) {
		this.tbFseTitoloStudio = tbFseTitoloStudio;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseCondIngMercatoLavoro() {
		return tbFseCondIngMercatoLavoro;
	}
	public void setTbFseCondIngMercatoLavoro(List<TabellaDecodificaBaseDTO> tbFseCondIngMercatoLavoro) {
		this.tbFseCondIngMercatoLavoro = tbFseCondIngMercatoLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseDurataRicercaLavoro() {
		return tbFseDurataRicercaLavoro;
	}
	public void setTbFseDurataRicercaLavoro(List<TabellaDecodificaBaseDTO> tbFseDurataRicercaLavoro) {
		this.tbFseDurataRicercaLavoro = tbFseDurataRicercaLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseTipologiaLavoro() {
		return tbFseTipologiaLavoro;
	}
	public void setTbFseTipologiaLavoro(List<TabellaDecodificaBaseDTO> tbFseTipologiaLavoro) {
		this.tbFseTipologiaLavoro = tbFseTipologiaLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseOrarioLavoro() {
		return tbFseOrarioLavoro;
	}
	public void setTbFseOrarioLavoro(List<TabellaDecodificaBaseDTO> tbFseOrarioLavoro) {
		this.tbFseOrarioLavoro = tbFseOrarioLavoro;
	}
	public List<TabellaDecodificaBaseDTO> getTbFseAzDimensione() {
		return tbFseAzDimensione;
	}
	public void setTbFseAzDimensione(List<TabellaDecodificaBaseDTO> tbFseAzDimensione) {
		this.tbFseAzDimensione = tbFseAzDimensione;
	}
	public List<TabellaDecodificaExtDTO> getTbTiCodIstat() {
		return tbTiCodIstat;
	}
	public void setTbTiCodIstat(List<TabellaDecodificaExtDTO> tbTiCodIstat) {
		this.tbTiCodIstat = tbTiCodIstat;
	}
	public List<TabellaDecodificaBaseDTO> getTbFonteFinanziamento() {
		return tbFonteFinanziamento;
	}
	public void setTbFonteFinanziamento(List<TabellaDecodificaBaseDTO> tbFonteFinanziamento) {
		this.tbFonteFinanziamento = tbFonteFinanziamento;
	}
	public List<TabellaDecodificaBaseDTO> getTbProgetto() {
		return tbProgetto;
	}
	public void setTbProgetto(List<TabellaDecodificaBaseDTO> tbProgetto) {
		this.tbProgetto = tbProgetto;
	}
	public List<TabellaDecodificaExtDTO> getTbSottocorsoAttivita() {
		return tbSottocorsoAttivita;
	}
	public void setTbSottocorsoAttivita(List<TabellaDecodificaExtDTO> tbSottocorsoAttivita) {
		this.tbSottocorsoAttivita = tbSottocorsoAttivita;
	}	
}

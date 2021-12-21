package it.siso.isee.obj;

import java.io.Serializable;

import sun.org.mozilla.javascript.internal.Delegator;

public class Sottoscrizione implements Serializable {

	private DatiDelegato delegato;
	
	private String codiceFiscaleSottoscrittore;
	private String protocolloMittente;
	//OBBLIGATORIO 
	private String luogoSottoscrizione;
	//OBBLIGATORIO
	private String dataSottoscrizione;
	private String noteSottoscrizione;
	private boolean FlagImpedimentoTemporaneo;
	private boolean flagRappresentanzaLegale;
	private boolean flagPEC;
	private String indirizzoPEC;
	private boolean flagAutorizzazioneRitiro;
	private String NumeroRicevuta;
	public DatiDelegato getDelegato() {
		return delegato;
	}
	public void setDelegato(DatiDelegato delegato) {
		this.delegato = delegato;
	}
	public String getCodiceFiscaleSottoscrittore() {
		return codiceFiscaleSottoscrittore;
	}
	public void setCodiceFiscaleSottoscrittore(String codiceFiscaleSottoscrittore) {
		this.codiceFiscaleSottoscrittore = codiceFiscaleSottoscrittore;
	}
	public String getProtocolloMittente() {
		return protocolloMittente;
	}
	public void setProtocolloMittente(String protocolloMittente) {
		this.protocolloMittente = protocolloMittente;
	}
	public String getLuogoSottoscrizione() {
		return luogoSottoscrizione;
	}
	public void setLuogoSottoscrizione(String luogoSottoscrizione) {
		this.luogoSottoscrizione = luogoSottoscrizione;
	}
	public String getDataSottoscrizione() {
		return dataSottoscrizione;
	}
	public void setDataSottoscrizione(String dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}
	public String getNoteSottoscrizione() {
		return noteSottoscrizione;
	}
	public void setNoteSottoscrizione(String noteSottoscrizione) {
		this.noteSottoscrizione = noteSottoscrizione;
	}
	public boolean isFlagImpedimentoTemporaneo() {
		return FlagImpedimentoTemporaneo;
	}
	public void setFlagImpedimentoTemporaneo(boolean flagImpedimentoTemporaneo) {
		FlagImpedimentoTemporaneo = flagImpedimentoTemporaneo;
	}
	public boolean isFlagRappresentanzaLegale() {
		return flagRappresentanzaLegale;
	}
	public void setFlagRappresentanzaLegale(boolean flagRappresentanzaLegale) {
		this.flagRappresentanzaLegale = flagRappresentanzaLegale;
	}
	public boolean isFlagPEC() {
		return flagPEC;
	}
	public void setFlagPEC(boolean flagPEC) {
		this.flagPEC = flagPEC;
	}
	public String getIndirizzoPEC() {
		return indirizzoPEC;
	}
	public void setIndirizzoPEC(String indirizzoPEC) {
		this.indirizzoPEC = indirizzoPEC;
	}
	public boolean isFlagAutorizzazioneRitiro() {
		return flagAutorizzazioneRitiro;
	}
	public void setFlagAutorizzazioneRitiro(boolean flagAutorizzazioneRitiro) {
		this.flagAutorizzazioneRitiro = flagAutorizzazioneRitiro;
	}
	public String getNumeroRicevuta() {
		return NumeroRicevuta;
	}
	public void setNumeroRicevuta(String numeroRicevuta) {
		NumeroRicevuta = numeroRicevuta;
	}
	
	
}

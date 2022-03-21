package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class InfoIseeDTO extends CeTBaseObject implements Serializable {
		
	private List<IseePersonaDTO> listaFamiglia;
	private boolean abitazioneProprieta;
	private boolean abitazioneLocazione;
	private BigDecimal rendimentoMedioTitoli; 
	private BigDecimal canoneAnnualeLocazione;
	private BigDecimal sommaRedditiFamiglia;/*READ*/
	private BigDecimal iciAbitazioneProprieta;/*READ*/
	private BigDecimal mutuoResiduoAbitazione;/*READ*/
	private BigDecimal iciAltriImmobili;/*READ*/
	private BigDecimal sommaPatrimonioMobiliare;/*READ*/
	private BigDecimal numHnd;
	private boolean flagUnicoGenitore;
	private boolean flagLavoroAmboGenitori;
	
	public InfoIseeDTO() {
		super();
	}

	public List<IseePersonaDTO> getListaFamiglia() {
		return listaFamiglia;
	}

	public void setListaFamiglia(List<IseePersonaDTO> listaFamiglia) {
		this.listaFamiglia = listaFamiglia;
	}

	public boolean isAbitazioneProprieta() {
		return abitazioneProprieta;
	}

	public void setAbitazioneProprieta(boolean abitazioneProprieta) {
		this.abitazioneProprieta = abitazioneProprieta;
	}

	public boolean isAbitazioneLocazione() {
		return abitazioneLocazione;
	}

	public void setAbitazioneLocazione(boolean abitazioneLocazione) {
		this.abitazioneLocazione = abitazioneLocazione;
	}

	public BigDecimal getRendimentoMedioTitoli() {
		return rendimentoMedioTitoli;
	}

	public void setRendimentoMedioTitoli(BigDecimal rendimentoMedioTitoli) {
		this.rendimentoMedioTitoli = rendimentoMedioTitoli;
	}

	public BigDecimal getCanoneAnnualeLocazione() {
		return canoneAnnualeLocazione;
	}

	public void setCanoneAnnualeLocazione(BigDecimal canoneAnnualeLocazione) {
		this.canoneAnnualeLocazione = canoneAnnualeLocazione;
	}

	public BigDecimal getSommaRedditiFamiglia() {
		return sommaRedditiFamiglia;
	}

	public void setSommaRedditiFamiglia(BigDecimal sommaRedditiFamiglia) {
		this.sommaRedditiFamiglia = sommaRedditiFamiglia;
	}

	public BigDecimal getIciAbitazioneProprieta() {
		return iciAbitazioneProprieta;
	}

	public void setIciAbitazioneProprieta(BigDecimal iciAbitazioneProprieta) {
		this.iciAbitazioneProprieta = iciAbitazioneProprieta;
	}

	public BigDecimal getNumHnd() {
		return numHnd;
	}

	public void setNumHnd(BigDecimal numHnd) {
		this.numHnd = numHnd;
	}

	public boolean isFlagUnicoGenitore() {
		return flagUnicoGenitore;
	}

	public void setFlagUnicoGenitore(boolean flagUnicoGenitore) {
		this.flagUnicoGenitore = flagUnicoGenitore;
	}

	public boolean isFlagLavoroAmboGenitori() {
		return flagLavoroAmboGenitori;
	}

	public void setFlagLavoroAmboGenitori(boolean flagLavoroAmboGenitori) {
		this.flagLavoroAmboGenitori = flagLavoroAmboGenitori;
	}

	public BigDecimal getSommaPatrimonioMobiliare() {
		return sommaPatrimonioMobiliare;
	}

	public void setSommaPatrimonioMobiliare(BigDecimal sommaPatrimonioMobiliare) {
		this.sommaPatrimonioMobiliare = sommaPatrimonioMobiliare;
	}

	public BigDecimal getMutuoResiduoAbitazione() {
		return mutuoResiduoAbitazione;
	}

	public void setMutuoResiduoAbitazione(BigDecimal mutuoResiduoAbitazione) {
		this.mutuoResiduoAbitazione = mutuoResiduoAbitazione;
	}

	public BigDecimal getIciAltriImmobili() {
		return iciAltriImmobili;
	}

	public void setIciAltriImmobili(BigDecimal iciAltriImmobili) {
		this.iciAltriImmobili = iciAltriImmobili;
	}

}

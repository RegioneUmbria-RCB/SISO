package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class IseeCalcDataIn extends CeTBaseObject implements Serializable {
		
	//calcolo ISEE
	private BigDecimal sommaRedditi;
	private BigDecimal sommaPatrimonioMobiliare; 
	private BigDecimal rendimentoMedioTitoli; 
	private BigDecimal canoneLocazione; 
	private BigDecimal sogliaMaxCanone = new BigDecimal(5164.57);
	private BigDecimal immobiliNonAb;
	private BigDecimal immobiliAb;
	private BigDecimal residuoMutuoAb;
	private BigDecimal sogliaMinAbitazione = new BigDecimal(51645.69);
	private BigDecimal sogliaMaxDetrazione = new BigDecimal(15493.71);
	private BigDecimal moltiplicatoreISP = new BigDecimal(0.2);
	private Integer numComponenti;
	private BigDecimal numHnd;
	private boolean flagUnicoGenitore;
	private boolean flagLavoroAmboGenitori;
	
	public IseeCalcDataIn() {
		super();
	}

	public BigDecimal getSommaRedditi() {
		return sommaRedditi;
	}

	public void setSommaRedditi(BigDecimal sommaRedditi) {
		this.sommaRedditi = sommaRedditi;
	}

	public BigDecimal getSommaPatrimonioMobiliare() {
		return sommaPatrimonioMobiliare;
	}

	public void setSommaPatrimonioMobiliare(BigDecimal sommaPatrimonioMobiliare) {
		this.sommaPatrimonioMobiliare = sommaPatrimonioMobiliare;
	}

	public BigDecimal getRendimentoMedioTitoli() {
		return rendimentoMedioTitoli;
	}

	public void setRendimentoMedioTitoli(BigDecimal rendimentoMedioTitoli) {
		this.rendimentoMedioTitoli = rendimentoMedioTitoli;
	}

	public BigDecimal getCanoneLocazione() {
		return canoneLocazione;
	}

	public void setCanoneLocazione(BigDecimal canoneLocazione) {
		this.canoneLocazione = canoneLocazione;
	}

	public BigDecimal getSogliaMaxCanone() {
		return sogliaMaxCanone;
	}

	public void setSogliaMaxCanone(BigDecimal sogliaMaxCanone) {
		this.sogliaMaxCanone = sogliaMaxCanone;
	}

	public BigDecimal getImmobiliNonAb() {
		return immobiliNonAb;
	}

	public void setImmobiliNonAb(BigDecimal immobiliNonAb) {
		this.immobiliNonAb = immobiliNonAb;
	}

	public BigDecimal getImmobiliAb() {
		return immobiliAb;
	}

	public void setImmobiliAb(BigDecimal immobiliAb) {
		this.immobiliAb = immobiliAb;
	}

	public BigDecimal getResiduoMutuoAb() {
		return residuoMutuoAb;
	}

	public void setResiduoMutuoAb(BigDecimal residuoMutuoAb) {
		this.residuoMutuoAb = residuoMutuoAb;
	}

	public BigDecimal getSogliaMinAbitazione() {
		return sogliaMinAbitazione;
	}

	public void setSogliaMinAbitazione(BigDecimal sogliaMinAbitazione) {
		this.sogliaMinAbitazione = sogliaMinAbitazione;
	}

	public BigDecimal getSogliaMaxDetrazione() {
		return sogliaMaxDetrazione;
	}

	public void setSogliaMaxDetrazione(BigDecimal sogliaMaxDetrazione) {
		this.sogliaMaxDetrazione = sogliaMaxDetrazione;
	}

	public BigDecimal getMoltiplicatoreISP() {
		return moltiplicatoreISP;
	}

	public void setMoltiplicatoreISP(BigDecimal moltiplicatoreISP) {
		this.moltiplicatoreISP = moltiplicatoreISP;
	}

	public Integer getNumComponenti() {
		return numComponenti;
	}

	public void setNumComponenti(Integer numComponenti) {
		this.numComponenti = numComponenti;
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
}

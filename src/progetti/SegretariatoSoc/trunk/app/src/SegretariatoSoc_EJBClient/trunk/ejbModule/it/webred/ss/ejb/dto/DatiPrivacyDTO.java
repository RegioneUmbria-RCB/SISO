package it.webred.ss.ejb.dto;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ss.data.model.SsOOrganizzazione;

public class DatiPrivacyDTO extends CeTBaseObject {

	private String cf;
	
	private Long organizzazioneId;
	
	private Date dtSottoscrizione;
	
	private Long schedaId;
	 
	private SsOOrganizzazione organizzazione;
	
	private Boolean flagCentriImpiego;
	
	private boolean beneficiarioRdC;

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}

	public Date getDtSottoscrizione() {
		return dtSottoscrizione;
	}

	public void setDtSottoscrizione(Date dtSottoscrizione) {
		this.dtSottoscrizione = dtSottoscrizione;
	}

	public Long getSchedaId() {
		return schedaId;
	}

	public void setSchedaId(Long schedaId) {
		this.schedaId = schedaId;
	}

	public SsOOrganizzazione getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(SsOOrganizzazione organizzazione) {
		this.organizzazione = organizzazione;
	}

	public Boolean getFlagCentriImpiego() {
		return flagCentriImpiego;
	}

	public void setFlagCentriImpiego(Boolean flagCentriImpiego) {
		this.flagCentriImpiego = flagCentriImpiego;
	}

	public boolean isBeneficiarioRdC() {
		return beneficiarioRdC;
	}

	public void setBeneficiarioRdC(boolean beneficiarioRdC) {
		this.beneficiarioRdC = beneficiarioRdC;
	}
	
}

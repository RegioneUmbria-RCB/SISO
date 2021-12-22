package it.webred.cs.csa.ejb.dto.mobi.upload;

import java.io.Serializable;



public class CsRelRelazioneProblRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private long relazioneDiarioId;
	
	
	private long problId;
	
	
	private long relazioneDiarioRifId;
	
	
	private Boolean flagRisolta;
	
	
	private Boolean flagVerificata;


	public long getRelazioneDiarioId() {
		return relazioneDiarioId;
	}


	public void setRelazioneDiarioId(long relazioneDiarioId) {
		this.relazioneDiarioId = relazioneDiarioId;
	}


	public long getProblId() {
		return problId;
	}


	public void setProblId(long problId) {
		this.problId = problId;
	}


	public long getRelazioneDiarioRifId() {
		return relazioneDiarioRifId;
	}


	public void setRelazioneDiarioRifId(long relazioneDiarioRifId) {
		this.relazioneDiarioRifId = relazioneDiarioRifId;
	}


	public Boolean getFlagRisolta() {
		return flagRisolta;
	}


	public void setFlagRisolta(Boolean flagRisolta) {
		this.flagRisolta = flagRisolta;
	}


	public Boolean getFlagVerificata() {
		return flagVerificata;
	}


	public void setFlagVerificata(Boolean flagVerificata) {
		this.flagVerificata = flagVerificata;
	}
	
}

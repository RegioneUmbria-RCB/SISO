package it.webred.ss.data.model.tb;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
public class SsUffOperatoreAnagraficaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ORG_ID")
    private Long organizzazioneId;
	
	@Column(name="UFFICIO_ID")
    private Long ufficioId;

	private String operatore;
	
	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}

	public Long getUfficioId() {
		return ufficioId;
	}

	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}

	public String getOperatore() {
		return operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SsUffOperatoreAnagraficaPK)) {
			return false;
		}
		SsUffOperatoreAnagraficaPK castOther = (SsUffOperatoreAnagraficaPK)other;
		return 
			(this.operatore==castOther.operatore) && 
			(this.organizzazioneId.longValue()==castOther.organizzazioneId.longValue()) && 
			(this.ufficioId.longValue()==castOther.ufficioId.longValue());
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.operatore.hashCode();
		hash = hash * prime + this.organizzazioneId.hashCode();
		hash = hash * prime + this.ufficioId.hashCode();
		
		return hash;
	}
    
}
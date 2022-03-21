package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;

@Embeddable
public class SsRelUffPcontOrgPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="PUNTO_CONTATTO_ID")
    private Long puntoContattoId;
	
	@Column(name="ORGANIZZAZIONE_ID")
    private Long organizzazioneId;
	
	@Column(name="UFFICIO_ID")
    private Long ufficioId;

	public Long getPuntoContattoId() {
		return puntoContattoId;
	}

	public void setPuntoContattoId(Long puntoContattoId) {
		this.puntoContattoId = puntoContattoId;
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SsRelUffPcontOrgPK)) {
			return false;
		}
		SsRelUffPcontOrgPK castOther = (SsRelUffPcontOrgPK)other;
		return 
			(this.puntoContattoId.longValue()==castOther.puntoContattoId.longValue()) && 
			(this.organizzazioneId.longValue()==castOther.organizzazioneId.longValue()) && 
			(this.ufficioId.longValue()==castOther.ufficioId.longValue());
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.puntoContattoId.hashCode();
		hash = hash * prime + this.organizzazioneId.hashCode();
		hash = hash * prime + this.ufficioId.hashCode();
		
		return hash;
	}
    
}
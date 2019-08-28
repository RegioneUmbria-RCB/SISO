package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SsSchedaPrivacyPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cf;
		
	@Column(name="ORGANIZZAZIONE_ID")
	private Long organizzazioneId;

	public String getCf() {
		return cf;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SsSchedaPrivacyPK)) {
			return false;
		}
		SsSchedaPrivacyPK castOther = (SsSchedaPrivacyPK)other;
		return 
			this.cf.equals(castOther.cf) && 
			(this.organizzazioneId == castOther.organizzazioneId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cf.hashCode();
		hash = hash * prime + ((int) (this.organizzazioneId ^ (this.organizzazioneId >>> 32)));
		
		return hash;
    }
	

}
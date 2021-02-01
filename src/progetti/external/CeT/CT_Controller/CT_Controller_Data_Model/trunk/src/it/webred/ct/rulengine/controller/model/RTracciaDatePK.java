package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the R_TRACCIA_STATI database table.
 * 
 */
@Embeddable
public class RTracciaDatePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String belfiore;

	@Column(name="ID_FONTE")
	private Long idFonte;

    public RTracciaDatePK() {
    }
    
	public RTracciaDatePK(String belfiore, Long idFonte) {
		super();
		this.belfiore = belfiore;
		this.idFonte = idFonte;
	}
	
	public String getBelfiore() {
		return this.belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public Long getIdFonte() {
		return this.idFonte;
	}
	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RTracciaDatePK)) {
			return false;
		}
		RTracciaDatePK castOther = (RTracciaDatePK)other;
		return 
			this.belfiore.equals(castOther.belfiore)
			&& (this.idFonte == castOther.idFonte);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.belfiore.hashCode();
		hash = hash * prime + ((int) (this.idFonte ^ (this.idFonte >>> 32)));
		
		return hash;
    }
}
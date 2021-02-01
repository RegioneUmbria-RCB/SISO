package it.webred.ct.data.model.segnalazionequalificata;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the S_OF_PRAT_FONTE database table.
 * 
 */
@Embeddable
public class SOfPratFontePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_PRATICA")
	private long fkPratica;

	@Column(name="FK_FONTE")
	private String fkFonte;

    public SOfPratFontePK() {
    }
	public long getFkPratica() {
		return this.fkPratica;
	}
	public void setFkPratica(long fkPratica) {
		this.fkPratica = fkPratica;
	}
	public String getFkFonte() {
		return this.fkFonte;
	}
	public void setFkFonte(String fkFonte) {
		this.fkFonte = fkFonte;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SOfPratFontePK)) {
			return false;
		}
		SOfPratFontePK castOther = (SOfPratFontePK)other;
		return 
			(this.fkPratica == castOther.fkPratica)
			&& this.fkFonte.equals(castOther.fkFonte);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.fkPratica ^ (this.fkPratica >>> 32)));
		hash = hash * prime + this.fkFonte.hashCode();
		
		return hash;
    }
}
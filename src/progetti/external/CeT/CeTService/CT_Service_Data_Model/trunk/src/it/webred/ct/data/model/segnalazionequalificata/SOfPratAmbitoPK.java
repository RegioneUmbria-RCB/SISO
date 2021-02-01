package it.webred.ct.data.model.segnalazionequalificata;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the S_OF_PRAT_AMBITO database table.
 * 
 */
@Embeddable
public class SOfPratAmbitoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_PRATICA")
	private long fkPratica;

	@Column(name="FK_AMBITO")
	private long fkAmbito;

    public SOfPratAmbitoPK() {
    }
	public long getFkPratica() {
		return this.fkPratica;
	}
	public void setFkPratica(long fkPratica) {
		this.fkPratica = fkPratica;
	}
	public long getFkAmbito() {
		return this.fkAmbito;
	}
	public void setFkAmbito(long fkAmbito) {
		this.fkAmbito = fkAmbito;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SOfPratAmbitoPK)) {
			return false;
		}
		SOfPratAmbitoPK castOther = (SOfPratAmbitoPK)other;
		return 
			(this.fkPratica == castOther.fkPratica)
			&& (this.fkAmbito == castOther.fkAmbito);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.fkPratica ^ (this.fkPratica >>> 32)));
		hash = hash * prime + ((int) (this.fkAmbito ^ (this.fkAmbito >>> 32)));
		
		return hash;
    }
}
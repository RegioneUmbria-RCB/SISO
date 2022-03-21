package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SIT_CRES_FERMATA_CENTRO database table.
 * 
 */
@Embeddable
public class SitCresFermataCentroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_CRES_FERMATA")
	private String fkCresFermata;

	@Column(name="FK_IDCRES")
	private String fkIdcres;

    public SitCresFermataCentroPK() {
    }
	public String getFkCresFermata() {
		return this.fkCresFermata;
	}
	public void setFkCresFermata(String fkCresFermata) {
		this.fkCresFermata = fkCresFermata;
	}
	public String getFkIdcres() {
		return this.fkIdcres;
	}
	public void setFkIdcres(String fkIdcres) {
		this.fkIdcres = fkIdcres;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitCresFermataCentroPK)) {
			return false;
		}
		SitCresFermataCentroPK castOther = (SitCresFermataCentroPK)other;
		return 
			this.fkCresFermata.equals(castOther.fkCresFermata)
			&& this.fkIdcres.equals(castOther.fkIdcres);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkCresFermata.hashCode();
		hash = hash * prime + this.fkIdcres.hashCode();
		
		return hash;
    }
}
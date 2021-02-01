package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_FONTE_COMUNE database table.
 * 
 */
@Embeddable
public class AmFonteComunePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_FONTE")
	private Integer fkAmFonte;

	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

    public AmFonteComunePK() {
    }
	public Integer getFkAmFonte() {
		return this.fkAmFonte;
	}
	public void setFkAmFonte(Integer fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}
	public String getFkAmComune() {
		return this.fkAmComune;
	}
	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmFonteComunePK)) {
			return false;
		}
		AmFonteComunePK castOther = (AmFonteComunePK)other;
		return 
			this.fkAmFonte.equals(castOther.fkAmFonte)
			&& this.fkAmComune.equals(castOther.fkAmComune);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmFonte.hashCode();
		hash = hash * prime + this.fkAmComune.hashCode();
		
		return hash;
    }
}
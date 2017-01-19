package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the CS_D_DIARIO_DOC database table.
 * 
 */
@Embeddable
public class CsCfgParametriPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="SEZIONE")
	private String sezione;
	
	@Column(name="CHIAVE")
	private String chiave;

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsCfgParametriPK)) {
			return false;
		}
		CsCfgParametriPK castOther = (CsCfgParametriPK)other;
		return 
			(this.sezione.equals(castOther.sezione))
			&& (this.chiave.equals(castOther.chiave));
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
 		hash = hash * prime + this.sezione.hashCode();
 		hash = hash * prime + this.chiave.hashCode();
		
		return hash;
	}
}
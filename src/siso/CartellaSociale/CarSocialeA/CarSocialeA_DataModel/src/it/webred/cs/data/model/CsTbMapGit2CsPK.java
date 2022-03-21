package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CsTbMapGit2CsPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="COD_ORIG")
	private String codOrig;
	
	@Column(name="ENTE")
	private String ente;

	public String getCodOrig() {
		return codOrig;
	}

	public String getEnte() {
		return ente;
	}

	public void setCodOrig(String codOrig) {
		this.codOrig = codOrig;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsTbMapGit2CsPK)) {
			return false;
		}
		CsTbMapGit2CsPK castOther = (CsTbMapGit2CsPK)other;
		return 
			(this.ente.equals(castOther.ente))
			&& (this.codOrig.equals(castOther.codOrig));
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
 		hash = hash * prime + this.ente.hashCode();
 		hash = hash * prime + this.codOrig.hashCode();
		
		return hash;
	}
}

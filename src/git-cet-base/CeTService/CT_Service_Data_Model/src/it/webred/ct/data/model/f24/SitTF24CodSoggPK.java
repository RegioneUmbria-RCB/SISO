package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class SitTF24CodSoggPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String codice;

	private String anno;

	public SitTF24CodSoggPK() {
	}
	public String getCodice() {
		return this.codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getAnno() {
		return this.anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitTF24CodSoggPK)) {
			return false;
		}
		SitTF24CodSoggPK castOther = (SitTF24CodSoggPK)other;
		return 
			this.codice.equals(castOther.codice)
			&& this.anno.equals(castOther.anno);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codice.hashCode();
		hash = hash * prime + this.anno.hashCode();
		
		return hash;
	}
}
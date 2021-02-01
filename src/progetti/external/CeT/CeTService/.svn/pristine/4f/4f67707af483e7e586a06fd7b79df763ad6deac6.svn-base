package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;


@Embeddable
public class SitTCodTributoAnnoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long anno;

	private String codice;

	private String tipo;

	public SitTCodTributoAnnoPK() {
	}
	public long getAnno() {
		return this.anno;
	}
	public void setAnno(long anno) {
		this.anno = anno;
	}
	public String getCodice() {
		return this.codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getTipo() {
		return this.tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitTCodTributoAnnoPK)) {
			return false;
		}
		SitTCodTributoAnnoPK castOther = (SitTCodTributoAnnoPK)other;
		return 
			(this.anno == castOther.anno)
			&& this.codice.equals(castOther.codice)
			&& this.tipo.equals(castOther.tipo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.anno ^ (this.anno >>> 32)));
		hash = hash * prime + this.codice.hashCode();
		hash = hash * prime + this.tipo.hashCode();
		
		return hash;
	}
}
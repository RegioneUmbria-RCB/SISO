package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_REL_CATSOC_TIPO_INTER database table.
 * 
 */
@Embeddable
public class CsRelCatsocTipoInterPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CATEGORIA_SOCIALE_ID", insertable=false, updatable=false)
	private long categoriaSocialeId;

	@Column(name="TIPO_INTERVENTO_ID", insertable=false, updatable=false)
	private long tipoInterventoId;

	public CsRelCatsocTipoInterPK() {
	}
	public long getCategoriaSocialeId() {
		return this.categoriaSocialeId;
	}
	public void setCategoriaSocialeId(long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}
	public long getTipoInterventoId() {
		return this.tipoInterventoId;
	}
	public void setTipoInterventoId(long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelCatsocTipoInterPK)) {
			return false;
		}
		CsRelCatsocTipoInterPK castOther = (CsRelCatsocTipoInterPK)other;
		return 
			(this.categoriaSocialeId == castOther.categoriaSocialeId)
			&& (this.tipoInterventoId == castOther.tipoInterventoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.categoriaSocialeId ^ (this.categoriaSocialeId >>> 32)));
		hash = hash * prime + ((int) (this.tipoInterventoId ^ (this.tipoInterventoId >>> 32)));
		
		return hash;
	}
}
package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_REL_SETTORE_CATSOC database table.
 * 
 */
@Embeddable
public class CsRelSettoreCatsocPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CATEGORIA_SOCIALE_ID", insertable=false, updatable=false)
	private long categoriaSocialeId;

	@Column(name="SETTORE_ID", insertable=false, updatable=false)
	private long settoreId;

	public CsRelSettoreCatsocPK() {
	}
	public long getCategoriaSocialeId() {
		return this.categoriaSocialeId;
	}
	public void setCategoriaSocialeId(long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}
	public long getSettoreId() {
		return this.settoreId;
	}
	public void setSettoreId(long settoreId) {
		this.settoreId = settoreId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelSettoreCatsocPK)) {
			return false;
		}
		CsRelSettoreCatsocPK castOther = (CsRelSettoreCatsocPK)other;
		return 
			(this.categoriaSocialeId == castOther.categoriaSocialeId)
			&& (this.settoreId == castOther.settoreId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.categoriaSocialeId ^ (this.categoriaSocialeId >>> 32)));
		hash = hash * prime + ((int) (this.settoreId ^ (this.settoreId >>> 32)));
		
		return hash;
	}
}
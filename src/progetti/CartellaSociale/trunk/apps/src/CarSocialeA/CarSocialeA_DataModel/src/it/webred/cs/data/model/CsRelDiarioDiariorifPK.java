package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_REL_CATSOC_TIPO_INTER database table.
 * 
 */
@Embeddable
public class CsRelDiarioDiariorifPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="DIARIO_ID", insertable=false, updatable=false)
	private long diarioId;

	@Column(name="DIARIO_ID_RIF", insertable=false, updatable=false)
	private long diarioIdRif;

	public CsRelDiarioDiariorifPK() {
	}

	
	public long getDiarioId() {
		return diarioId;
	}


	public void setDiarioId(long diarioId) {
		this.diarioId = diarioId;
	}


	public long getDiarioIdRif() {
		return diarioIdRif;
	}


	public void setDiarioIdRif(long diarioIdRif) {
		this.diarioIdRif = diarioIdRif;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelDiarioDiariorifPK)) {
			return false;
		}
		CsRelDiarioDiariorifPK castOther = (CsRelDiarioDiariorifPK)other;
		return 
			(this.diarioId == castOther.diarioId)
			&& (this.diarioIdRif == castOther.diarioIdRif);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.diarioId ^ (this.diarioId >>> 32)));
		hash = hash * prime + ((int) (this.diarioIdRif ^ (this.diarioIdRif >>> 32)));
		
		return hash;
	}
}
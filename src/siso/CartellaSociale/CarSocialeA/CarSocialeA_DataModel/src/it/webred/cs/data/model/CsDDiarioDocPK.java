package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * The primary key class for the CS_D_DIARIO_DOC database table.
 * 
 */
@Embeddable
public class CsDDiarioDocPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DIARIO_ID")
	private long diarioId;
	
	@Column(name="DOCUMENTO_ID")
	private long documentoId;
	
	public long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(long diarioId) {
		this.diarioId = diarioId;
	}

	public long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(long documentoId) {
		this.documentoId = documentoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsDDiarioDocPK)) {
			return false;
		}
		CsDDiarioDocPK castOther = (CsDDiarioDocPK)other;
		return 
			(this.diarioId == castOther.diarioId)
			&& (this.documentoId == castOther.documentoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.diarioId ^ (this.diarioId >>> 32)));
		hash = hash * prime + ((int) (this.documentoId ^ (this.documentoId >>> 32)));
		
		return hash;
	}
}
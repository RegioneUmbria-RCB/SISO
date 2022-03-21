package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;

public class CsDSinaEsegPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "SINA_ID", insertable = false, updatable = false)
	private long sinaId;

	@Column(name = "DOMANDA_ID", insertable = false, updatable = false)
	private long domandaId;

	@Column(name = "RISPOSTA_ID", insertable = false, updatable = false)
	private long rispostaId;

	public CsDSinaEsegPK() {
	}

	public long getSinaId() {
		return sinaId;
	}

	public void setSinaId(long sinaId) {
		this.sinaId = sinaId;
	}

	public long getDomandaId() {
		return domandaId;
	}

	public void setDomandaId(long domandaId) {
		this.domandaId = domandaId;
	}

	public long getRispostaId() {
		return rispostaId;
	}

	public void setRispostaId(long rispostaId) {
		this.rispostaId = rispostaId;
	}

	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsDSinaEsegPK)) {
			return false;
		}
		CsDSinaEsegPK castOther = (CsDSinaEsegPK)other;
		return 
			(this.sinaId == castOther.sinaId) && (this.domandaId == castOther.domandaId) && (this.rispostaId == castOther.rispostaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.sinaId ^ (this.sinaId >>> 32)));
		hash = hash * prime + ((int) (this.domandaId ^ (this.domandaId >>> 32)));
		hash = hash * prime + ((int) (this.rispostaId ^ (this.rispostaId >>> 32)));
		
		return hash;
	}
}

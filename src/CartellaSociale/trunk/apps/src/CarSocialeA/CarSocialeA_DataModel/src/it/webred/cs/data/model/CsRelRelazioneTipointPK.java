package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The primary key class for the CS_REL_RELAZIONE_TIPOINT database table.
 * 
 */
@Embeddable
public class CsRelRelazioneTipointPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="RELAZIONE_DIARIO_ID")
	private long relazioneDiarioId;
	
	@Column(name="TIPO_INTERVENTO_ID")
	private long tipoInterventoId;

	public CsRelRelazioneTipointPK()
	{
		super();
	}
	public CsRelRelazioneTipointPK(long relazioneDiarioId, long tipoInterventoId) {
		this.relazioneDiarioId=relazioneDiarioId;
		this.tipoInterventoId = tipoInterventoId;
	}

	public long getRelazioneDiarioId() {
		return relazioneDiarioId;
	}

	public void setRelazioneDiarioId(long relazioneDiarioId) {
		this.relazioneDiarioId = relazioneDiarioId;
	}

	public long getTipoInterventoId() {
		return tipoInterventoId;
	}

	public void setTipoInterventoId(long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsRelRelazioneTipointPK)) {
			return false;
		}
		CsRelRelazioneTipointPK castOther = (CsRelRelazioneTipointPK)other;
		return 
			(this.relazioneDiarioId == castOther.relazioneDiarioId)
			&& (this.tipoInterventoId == castOther.tipoInterventoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.relazioneDiarioId ^ (this.relazioneDiarioId >>> 32)));
		hash = hash * prime + ((int) (this.tipoInterventoId ^ (this.tipoInterventoId >>> 32)));
		
		return hash;
	}
}
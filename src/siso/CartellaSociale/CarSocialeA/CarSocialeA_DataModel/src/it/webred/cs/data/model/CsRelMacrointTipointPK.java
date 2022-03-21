package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The primary key class for the CS_REL_RELAZIONE_TIPOINT database table.
 * 
 */
@Embeddable
public class CsRelMacrointTipointPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="MACRO_INT_ID")
	private long macroIntId;
	
	@Column(name="TIPO_INTERVENTO_ID")
	private long tipoInterventoId;

	public CsRelMacrointTipointPK()
	{
		super();
	}
	
	
	
	public long getMacroIntId() {
		return macroIntId;
	}



	public void setMacroIntId(long macroIntId) {
		this.macroIntId = macroIntId;
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
		if (!(other instanceof CsRelMacrointTipointPK)) {
			return false;
		}
		CsRelMacrointTipointPK castOther = (CsRelMacrointTipointPK)other;
		return 
			(this.macroIntId == castOther.macroIntId)
			&& (this.tipoInterventoId == castOther.tipoInterventoId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.macroIntId ^ (this.macroIntId >>> 32)));
		hash = hash * prime + ((int) (this.tipoInterventoId ^ (this.tipoInterventoId >>> 32)));
		
		return hash;
	}
}
package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SIT_D_PERSONA_ST database table.
 * 
 */
@Embeddable
public class SitDPersonaStPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_EXT_D_PERSONA")
	protected String idExtDPersona;

	@Column(name="N_ORD")
	protected long nOrd;

	public SitDPersonaStPK() {
	}
	public String getIdExtDPersona() {
		return this.idExtDPersona;
	}
	public void setIdExtDPersona(String idExtDPersona) {
		this.idExtDPersona = idExtDPersona;
	}
	public long getNOrd() {
		return this.nOrd;
	}
	public void setNOrd(long nOrd) {
		this.nOrd = nOrd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitDPersonaStPK)) {
			return false;
		}
		SitDPersonaStPK castOther = (SitDPersonaStPK)other;
		return 
			this.idExtDPersona.equals(castOther.idExtDPersona)
			&& (this.nOrd == castOther.nOrd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idExtDPersona.hashCode();
		hash = hash * prime + ((int) (this.nOrd ^ (this.nOrd >>> 32)));
		
		return hash;
	}
}
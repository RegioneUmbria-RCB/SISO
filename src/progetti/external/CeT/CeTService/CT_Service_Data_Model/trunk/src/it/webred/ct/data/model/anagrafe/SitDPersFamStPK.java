package it.webred.ct.data.model.anagrafe;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the SIT_D_PERS_FAM_ST database table.
 * 
 */
@Embeddable
public class SitDPersFamStPK extends SitDPersonaStPK {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_EXT_D_FAMIGLIA")
	private String idExtDFamiglia;

	public SitDPersFamStPK() {
	}

	public String getIdExtDFamiglia() {
		return this.idExtDFamiglia;
	}
	public void setIdExtDFamiglia(String idExtDFamiglia) {
		this.idExtDFamiglia = idExtDFamiglia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitDPersFamStPK)) {
			return false;
		}
		SitDPersFamStPK castOther = (SitDPersFamStPK)other;
		return 
			(this.nOrd == castOther.nOrd)
			&& this.idExtDPersona.equals(castOther.idExtDPersona)
			&& this.idExtDFamiglia.equals(castOther.idExtDFamiglia);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.nOrd ^ (this.nOrd >>> 32)));
		hash = hash * prime + this.idExtDPersona.hashCode();
		hash = hash * prime + this.idExtDFamiglia.hashCode();
		
		return hash;
	}
}
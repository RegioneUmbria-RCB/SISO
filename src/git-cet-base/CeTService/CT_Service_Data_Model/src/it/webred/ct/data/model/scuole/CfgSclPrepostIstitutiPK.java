package it.webred.ct.data.model.scuole;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CFG_SCL_PREPOST_ISTITUTI database table.
 * 
 */
@Embeddable
public class CfgSclPrepostIstitutiPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ANNO_SCOLASTICO")
	private String annoScolastico;

	@Column(name="ID_SIT_SCL_ISTITUTI")
	private String idSitSclIstituti;

    public CfgSclPrepostIstitutiPK() {
    }
	public String getAnnoScolastico() {
		return this.annoScolastico;
	}
	public void setAnnoScolastico(String annoScolastico) {
		this.annoScolastico = annoScolastico;
	}
	public String getIdSitSclIstituti() {
		return this.idSitSclIstituti;
	}
	public void setIdSitSclIstituti(String idSitSclIstituti) {
		this.idSitSclIstituti = idSitSclIstituti;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CfgSclPrepostIstitutiPK)) {
			return false;
		}
		CfgSclPrepostIstitutiPK castOther = (CfgSclPrepostIstitutiPK)other;
		return 
			this.annoScolastico.equals(castOther.annoScolastico)
			&& this.idSitSclIstituti.equals(castOther.idSitSclIstituti);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.annoScolastico.hashCode();
		hash = hash * prime + this.idSitSclIstituti.hashCode();
		
		return hash;
    }
}
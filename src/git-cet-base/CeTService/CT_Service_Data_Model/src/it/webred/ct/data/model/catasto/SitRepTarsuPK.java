package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the SIT_REP_TARSU database table.
 * 
 */
@Embeddable
public class SitRepTarsuPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String pkid;

	@Column(name="PKID_UIU")
	private BigDecimal pkidUiu;

    public SitRepTarsuPK() {
    }
    
	public String getPkid() {
		return pkid;
	}

	public void setPkid(String pkid) {
		this.pkid = pkid;
	}

	public BigDecimal getPkidUiu() {
		return pkidUiu;
	}

	public void setPkidUiu(BigDecimal pkidUiu) {
		this.pkidUiu = pkidUiu;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitRepTarsuPK)) {
			return false;
		}
		SitRepTarsuPK castOther = (SitRepTarsuPK)other;
		return 
			this.pkidUiu.equals(castOther.pkidUiu)
			&& this.pkid.equals(castOther.pkid);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pkidUiu.hashCode();
		hash = hash * prime + this.pkid.hashCode();
		
		return hash;
    }
}

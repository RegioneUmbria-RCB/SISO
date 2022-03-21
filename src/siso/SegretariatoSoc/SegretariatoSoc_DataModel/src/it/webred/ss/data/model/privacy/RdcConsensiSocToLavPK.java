package it.webred.ss.data.model.privacy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdcConsensiSocToLavPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cf;
		
	@Column(name="COD_ENTE_FROM")
	private String codEnteFrom;

	public String getCf() {
		return cf;
	}

	public String getCodEnteFrom() {
		return codEnteFrom;
	}



	public void setCodEnteFrom(String codEnteFrom) {
		this.codEnteFrom = codEnteFrom;
	}



	public void setCf(String cf) {
		this.cf = cf;
	}



	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RdcConsensiSocToLavPK)) {
			return false;
		}
		RdcConsensiSocToLavPK castOther = (RdcConsensiSocToLavPK)other;
		return 
			this.cf.equals(castOther.cf) && 
			(this.codEnteFrom == castOther.codEnteFrom);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cf.hashCode();
		hash = hash * prime + this.codEnteFrom.hashCode();
		return hash;
    }
	

}
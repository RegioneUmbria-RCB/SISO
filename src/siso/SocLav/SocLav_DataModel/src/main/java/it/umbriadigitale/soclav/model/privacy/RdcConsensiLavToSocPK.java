package it.umbriadigitale.soclav.model.privacy;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RdcConsensiLavToSocPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cf;
		
	@Column(name="COD_ENTE_TO")
	private String codEnteTo;

	public String getCf() {
		return cf;
	}

	public String getCodEnteTo() {
		return codEnteTo;
	}


	public void setCodEnteTo(String codEnteTo) {
		this.codEnteTo = codEnteTo;
	}




	public void setCf(String cf) {
		this.cf = cf;
	}



	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RdcConsensiLavToSocPK)) {
			return false;
		}
		RdcConsensiLavToSocPK castOther = (RdcConsensiLavToSocPK)other;
		return 
			this.cf.equals(castOther.cf) && 
			(this.codEnteTo == castOther.codEnteTo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cf.hashCode();
		hash = hash * prime + this.codEnteTo.hashCode();
		return hash;
    }
	

}
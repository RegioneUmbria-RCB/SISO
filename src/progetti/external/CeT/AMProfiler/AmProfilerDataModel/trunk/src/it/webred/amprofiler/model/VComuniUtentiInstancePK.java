package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_COMUNI_UTENTI_INSTANCE database table.
 * 
 */
@Embeddable
public class VComuniUtentiInstancePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_APPLICATION")
	private String fkAmApplication;

	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

	@Column(name="FK_AM_INSTANCE")
	private String fkAmInstance;

	@Column(name="FK_AM_USER")
	private String fkAmUser;

	private String url;

    public VComuniUtentiInstancePK() {
    }

	public String getFkAmApplication() {
		return this.fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}

	public String getFkAmComune() {
		return this.fkAmComune;
	}

	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

	public String getFkAmInstance() {
		return this.fkAmInstance;
	}

	public void setFkAmInstance(String fkAmInstance) {
		this.fkAmInstance = fkAmInstance;
	}

	public String getFkAmUser() {
		return this.fkAmUser;
	}

	public void setFkAmUser(String fkAmUser) {
		this.fkAmUser = fkAmUser;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VComuniUtentiInstancePK)) {
			return false;
		}
		VComuniUtentiInstancePK castOther = (VComuniUtentiInstancePK)other;
		return 
			this.fkAmApplication.equals(castOther.fkAmApplication)
			&& this.fkAmComune.equals(castOther.fkAmComune)
			&& this.fkAmInstance.equals(castOther.fkAmInstance)
			&& this.fkAmUser.equals(castOther.fkAmUser)
			&& this.url.equals(castOther.url);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmApplication.hashCode();
		hash = hash * prime + this.fkAmComune.hashCode();
		hash = hash * prime + this.fkAmInstance.hashCode();
		hash = hash * prime + this.fkAmUser.hashCode();
		hash = hash * prime + this.url.hashCode();

		
		return hash;
    }

}
package it.umbriadigitale.argo.data.cs.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ImportSiruProgettiAttivitaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ATTIVITA_ID")
	private String attivitaId;

	@Column(name="COD_LOCALE_PROGETTO")
	private String codLocaleProgetto;

	public ImportSiruProgettiAttivitaPK() {}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ImportSiruProgettiAttivitaPK)) {
			return false;
		}
		ImportSiruProgettiAttivitaPK castOther = (ImportSiruProgettiAttivitaPK)other;
		return 
			this.codLocaleProgetto.equals(castOther.codLocaleProgetto)
			&& (this.attivitaId == castOther.attivitaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.attivitaId.hashCode();
		hash = hash * prime + this.codLocaleProgetto.hashCode();
		
		return hash;
	}

	public String getAttivitaId() {
		return attivitaId;
	}

	public void setAttivitaId(String attivitaId) {
		this.attivitaId = attivitaId;
	}

	public String getCodLocaleProgetto() {
		return codLocaleProgetto;
	}

	public void setCodLocaleProgetto(String codLocaleProgetto) {
		this.codLocaleProgetto = codLocaleProgetto;
	}
	
	
}
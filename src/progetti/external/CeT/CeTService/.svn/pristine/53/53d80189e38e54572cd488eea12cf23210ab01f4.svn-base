package it.webred.ct.data.model.ici;

import it.webred.ct.data.model.catasto.SitRepTarsuPK;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The primary key class for the VIciTSoggAll database table.
 * 
 */
@Embeddable
public class VIciTSoggAllPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
		
	private String id;

	@Column(name="ID_EXT_OGG_ICI")
	private String idExtOggIci;
	
	@Column(name="TIPO_SOGGETTO")
	private String tipoSoggetto;

    public VIciTSoggAllPK() {
    }
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExtOggIci() {
		return idExtOggIci;
	}

	public void setIdExtOggIci(String idExtOggIci) {
		this.idExtOggIci = idExtOggIci;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VIciTSoggAllPK)) {
			return false;
		}
		VIciTSoggAllPK castOther = (VIciTSoggAllPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.idExtOggIci.equals(castOther.idExtOggIci);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.idExtOggIci.hashCode();
		
		return hash;
    }
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
}

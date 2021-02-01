package it.webred.ct.data.model.tarsu;

import it.webred.ct.data.model.ici.VIciTSoggAllPK;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the VTarSoggAll database table.
 * 
 */
@Embeddable
public class VTTarSoggAllPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
		
	private String id;

	@Column(name="ID_EXT_OGG_RSU")
	private String idExtOggRsu;
	
	public VTTarSoggAllPK() {
		
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VTTarSoggAllPK)) {
			return false;
		}
		VTTarSoggAllPK castOther = (VTTarSoggAllPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.idExtOggRsu.equals(castOther.idExtOggRsu);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.idExtOggRsu.hashCode();
		
		return hash;
    }

}

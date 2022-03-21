package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the R_TRACCIA_STATI database table.
 * 
 */
@Embeddable
public class RTracciaForniturePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String belfiore;

	@Column(name="ID_FONTE")
	private Long idFonte;

	private Long istante;

    public RTracciaForniturePK() {
    }
    
	public RTracciaForniturePK(String belfiore, Long idFonte, Long istante) {
		super();
		this.belfiore = belfiore;
		this.idFonte = idFonte;
		this.istante = istante;
	}
	
	public String getBelfiore() {
		return this.belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public Long getIdFonte() {
		return this.idFonte;
	}
	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}
	public Long getIstante() {
		return this.istante;
	}
	public void setIstante(Long istante) {
		this.istante = istante;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RTracciaForniturePK)) {
			return false;
		}
		RTracciaForniturePK castOther = (RTracciaForniturePK)other;
		return 
			this.belfiore.equals(castOther.belfiore)
			&& (this.idFonte == castOther.idFonte)
			&& (this.istante == castOther.istante);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.belfiore.hashCode();
		hash = hash * prime + ((int) (this.idFonte ^ (this.idFonte >>> 32)));
		hash = hash * prime + ((int) (this.istante ^ (this.istante >>> 32)));
		
		return hash;
    }
}
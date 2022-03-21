package it.webred.ct.data.model.centriestivi;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_CRES_FERMATA_CENTRO database table.
 * 
 */
@Entity
@Table(name="SIT_CRES_FERMATA_CENTRO")
public class SitCresFermataCentro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitCresFermataCentroPK id;

    public SitCresFermataCentro() {
    }

	public SitCresFermataCentroPK getId() {
		return this.id;
	}

	public void setId(SitCresFermataCentroPK id) {
		this.id = id;
	}
	
}
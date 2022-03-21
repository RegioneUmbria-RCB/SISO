package it.webred.ct.data.model.segnalazionequalificata;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the S_OF_PRAT_AMBITO database table.
 * 
 */
@Entity
@Table(name="S_OF_PRAT_AMBITO")
public class SOfPratAmbito implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SOfPratAmbitoPK id;

    public SOfPratAmbito() {
    }

	public SOfPratAmbitoPK getId() {
		return this.id;
	}

	public void setId(SOfPratAmbitoPK id) {
		this.id = id;
	}
	
}
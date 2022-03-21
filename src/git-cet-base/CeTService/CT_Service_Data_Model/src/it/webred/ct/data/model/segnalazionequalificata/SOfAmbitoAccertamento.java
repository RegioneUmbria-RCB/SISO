package it.webred.ct.data.model.segnalazionequalificata;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the S_OF_AMBITO_ACCERTAMENTO database table.
 * 
 */
@Entity
@Table(name="S_OF_AMBITO_ACCERTAMENTO")
public class SOfAmbitoAccertamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String ambito;

	private String specie;

    public SOfAmbitoAccertamento() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAmbito() {
		return this.ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public String getSpecie() {
		return this.specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

}
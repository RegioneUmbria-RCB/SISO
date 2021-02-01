package it.webred.cs.sample.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TRAINING_PRJ_COMUNE")
public class TrainingPrjComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descrizione;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}

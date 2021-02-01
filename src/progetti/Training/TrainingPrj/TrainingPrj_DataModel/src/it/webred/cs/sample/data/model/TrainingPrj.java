package it.webred.cs.sample.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SIT_ENTE database table.
 * 
 */
@Entity
@Table(name="TRAINING_PRJ")
public class TrainingPrj implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String cognome;

	private String nome;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
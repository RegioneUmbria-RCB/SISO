package eu.smartpeg.rilevazionepresenze.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the AR_RP_MOTIVAZIONE database table.
 * 
 */
@Entity
@Table(name="AR_RP_MOTIVAZIONE")
@NamedQuery(name="Motivazione.findAll", query="SELECT m FROM Motivazione m ORDER BY m.descrizione")
public class Motivazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descrizione;

	public Motivazione() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	
	
}
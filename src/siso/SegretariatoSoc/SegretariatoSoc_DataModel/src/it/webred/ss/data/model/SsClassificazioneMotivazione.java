package it.webred.ss.data.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
* The persistent class for the SS_CLASSIFICAZIONE_MOTIVAZIONE database table.
* 
*/
@Entity
@Table(name="SS_CLASSIFICAZIONE_MOTIVAZIONE")
@NamedQuery(name="SsClassificazioneMotivazione.findAll", query="SELECT c FROM SsClassificazioneMotivazione c ORDER BY c.descrizione")
public class SsClassificazioneMotivazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	private String descrizione;
	
	private String abilitato;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
}



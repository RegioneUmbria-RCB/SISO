package it.webred.amprofiler.model.perm;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ente {

	@Id
	private String ente;
	private String descrizione;

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	
}

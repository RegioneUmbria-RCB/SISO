package it.webred.amprofiler.model.perm;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PermEnte {

	@Id
	private String ente;

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	
}

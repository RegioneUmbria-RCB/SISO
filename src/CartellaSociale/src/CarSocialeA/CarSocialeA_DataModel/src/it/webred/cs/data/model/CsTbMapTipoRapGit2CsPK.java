package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CsTbMapTipoRapGit2CsPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="COD_ORIG")
	private String codOrig;
	
	@Column(name="ENTE")
	private String ente;

	public String getCodOrig() {
		return codOrig;
	}

	public String getEnte() {
		return ente;
	}

	public void setCodOrig(String codOrig) {
		this.codOrig = codOrig;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}


}

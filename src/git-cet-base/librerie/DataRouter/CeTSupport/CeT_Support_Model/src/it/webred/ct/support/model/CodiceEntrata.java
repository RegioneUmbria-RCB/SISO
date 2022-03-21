package it.webred.ct.support.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe di test per verifcare il funzionamento del datasource router
 * 
 * 
 * Francesco Azzola - 2010
 * 
 * */

@Entity
@Table(name="COD_ENTRATA")
public class CodiceEntrata implements Serializable {

	@Id
	@Column(name="CODICE")
	private String codice;
	
	@Column(name="DESCR")
	private String descr;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}
	
	
}

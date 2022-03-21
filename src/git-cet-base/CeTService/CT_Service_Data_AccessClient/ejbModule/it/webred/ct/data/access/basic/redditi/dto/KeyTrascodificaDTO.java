package it.webred.ct.data.access.basic.redditi.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class KeyTrascodificaDTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String annoImposta;

	private String codiceRiga;

	private String tipoModello;

	public String getAnnoImposta() {
		return annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCodiceRiga() {
		return codiceRiga;
	}

	public void setCodiceRiga(String codiceRiga) {
		this.codiceRiga = codiceRiga;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}
	

}

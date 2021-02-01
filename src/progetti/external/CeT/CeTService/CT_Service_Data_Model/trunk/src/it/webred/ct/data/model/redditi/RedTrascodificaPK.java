package it.webred.ct.data.model.redditi;

import java.io.Serializable;

import javax.persistence.Column;

public class RedTrascodificaPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	@Column(name="CODICE_RIGA")
	private String codiceRiga;
	
	@Column(name="TIPO_MODELLO")
	private String tipoModello;

    public RedTrascodificaPK() {
    }

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCodiceRiga() {
		return this.codiceRiga;
	}

	public void setCodiceRiga(String codiceRiga) {
		this.codiceRiga = codiceRiga;
	}
	
	public String getTipoModello() {
		return this.tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RedTrascodificaPK)) {
			return false;
		}
		RedTrascodificaPK castOther = (RedTrascodificaPK)other;
		return 
			this.annoImposta.equals(castOther.annoImposta)
			&& this.codiceRiga.equals(castOther.codiceRiga)
			&& this.tipoModello.equals(castOther.tipoModello);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.annoImposta.hashCode();
		hash = hash * prime + this.codiceRiga.hashCode();
		hash = hash * prime + this.tipoModello.hashCode();
		return hash;
    }
}

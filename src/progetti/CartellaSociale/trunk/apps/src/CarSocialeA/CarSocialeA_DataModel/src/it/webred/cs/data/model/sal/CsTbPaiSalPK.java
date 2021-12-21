package it.webred.cs.data.model.sal;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CS_TB_PAI_SAL database table.
 * 
 */
@Embeddable
public class CsTbPaiSalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String dominio;

	private String codice;

	public CsTbPaiSalPK() {
	}
	public String getDominio() {
		return this.dominio;
	}
	public void setDominio(String dominio) {
		this.dominio = dominio;
	}
	public String getCodice() {
		return this.codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsTbPaiSalPK)) {
			return false;
		}
		CsTbPaiSalPK castOther = (CsTbPaiSalPK)other;
		return 
			this.dominio.equals(castOther.dominio)
			&& this.codice.equals(castOther.codice);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.dominio.hashCode();
		hash = hash * prime + this.codice.hashCode();
		
		return hash;
	}
}
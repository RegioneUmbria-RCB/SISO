package it.webred.ct.data.model.redditi;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * The primary key class for the RedDescrizione database table.
 * 
 */
@Embeddable
public class RedDescrizionePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="IDE_TELEMATICO")
	private String ideTelematico;
	
	@Column(name="CODICE_FISCALE_DIC")
	private String codiceFiscaleDic;
	
	@Column(name="TIPO_MODELLO")
	private String tipoModello;
	
	public  RedDescrizionePK() {
		 
	}
	public String getIdeTelematico() {
		return this.ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}

	public String getCodiceFiscaleDic() {
		return this.codiceFiscaleDic;
	}

	public void setCodiceFiscaleDic(String codiceFiscaleDic) {
		this.codiceFiscaleDic = codiceFiscaleDic;
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
		if (!(other instanceof RedDescrizionePK)) {
			return false;
		}
		RedDescrizionePK castOther = (RedDescrizionePK)other;
		return 
			this.ideTelematico.equals(castOther.ideTelematico)
			&& this.codiceFiscaleDic.equals(castOther.codiceFiscaleDic)
			&& this.tipoModello.equals(castOther.tipoModello);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ideTelematico.hashCode();
		hash = hash * prime + this.codiceFiscaleDic.hashCode();
		hash = hash * prime + this.tipoModello.hashCode();
		return hash;
    }
}

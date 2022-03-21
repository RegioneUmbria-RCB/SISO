package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CsADatiEsterniSoggettoPK implements Serializable {

	private static final long serialVersionUID = 8072777170371778010L;

	@Column(name = "ID_SOGGETTO")
	private Long idSoggetto;

	@Column(name = "ID_DATI_ESTERNI")
	private Long idDatiEsterni;

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public Long getIdDatiEsterni() {
		return idDatiEsterni;
	}

	public void setIdDatiEsterni(Long idDatiEsterni) {
		this.idDatiEsterni = idDatiEsterni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idDatiEsterni == null) ? 0 : idDatiEsterni.hashCode());
		result = prime * result + ((idSoggetto == null) ? 0 : idSoggetto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CsADatiEsterniSoggettoPK other = (CsADatiEsterniSoggettoPK) obj;
		if (idDatiEsterni == null) {
			if (other.idDatiEsterni != null)
				return false;
		} else if (!idDatiEsterni.equals(other.idDatiEsterni))
			return false;
		if (idSoggetto == null) {
			if (other.idSoggetto != null)
				return false;
		} else if (!idSoggetto.equals(other.idSoggetto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CsADatiEsterniSoggettoPK [idSoggetto=" + idSoggetto + ", idDatiEsterni=" + idDatiEsterni + "]";
	}

}

package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="SIT_T_COD_TRIBUTO")
public class SitTCodTributo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String codice;

	private String descrizione;

	//bi-directional many-to-one association to SitTCodTributoAnno
	@OneToMany(mappedBy="sitTCodTributo")
	private List<SitTCodTributoAnno> sitTCodTributoAnnos;

	public SitTCodTributo() {
	}

	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<SitTCodTributoAnno> getSitTCodTributoAnnos() {
		return this.sitTCodTributoAnnos;
	}

	public void setSitTCodTributoAnnos(List<SitTCodTributoAnno> sitTCodTributoAnnos) {
		this.sitTCodTributoAnnos = sitTCodTributoAnnos;
	}

}
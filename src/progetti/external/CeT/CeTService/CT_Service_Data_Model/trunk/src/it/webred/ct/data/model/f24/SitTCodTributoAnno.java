package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="SIT_T_COD_TRIBUTO_ANNO")
public class SitTCodTributoAnno implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitTCodTributoAnnoPK id;

	//bi-directional many-to-one association to SitTCodTributo
	@ManyToOne
	@JoinColumn(name="CODICE")
	private SitTCodTributo sitTCodTributo;

	public SitTCodTributoAnno() {
	}

	public SitTCodTributoAnnoPK getId() {
		return this.id;
	}

	public void setId(SitTCodTributoAnnoPK id) {
		this.id = id;
	}

	public SitTCodTributo getSitTCodTributo() {
		return this.sitTCodTributo;
	}

	public void setSitTCodTributo(SitTCodTributo sitTCodTributo) {
		this.sitTCodTributo = sitTCodTributo;
	}

}
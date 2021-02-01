package it.webred.cs.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CS_A_DATI_EXT_TO_SOGG")
public class CsADatiEsterniSoggetto {

//	@EmbeddedId
//	private CsADatiEsterniSoggettoPK id;

	@Id
	@SequenceGenerator(name = "CS_A_DATI_EXT_TO_SOGG_ID_GENERATOR", sequenceName = "SQ_CS_DATI_ESTERNI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_A_DATI_EXT_TO_SOGG_ID_GENERATOR")
	private Long id;

	@Column(name = "COD_ENTE")
	private String codiceEnte;

	@Column(name = "ID_SOGGETTO")
	private Long idSoggetto;

	@Column(name = "ID_DATI_ESTERNI")
	private Long idDatiEsterni;

	// relations

	@ManyToOne
	@JoinColumn(name = "ID_SOGGETTO", insertable = false, updatable = false)
	private CsASoggettoDatiEsterni soggetto;

	@ManyToOne
	@JoinColumn(name = "ID_DATI_ESTERNI", insertable = false, updatable = false)
	private CsADatiEsterni datiEsterni;

	// ctor

	public CsADatiEsterniSoggetto() {
//		id = new CsADatiEsterniSoggettoPK();
	}

	public CsADatiEsterniSoggetto(CsASoggettoDatiEsterni soggetto, CsADatiEsterni datiEsterni, String codiceEnte) {
		idSoggetto = soggetto.getId();
		idDatiEsterni = datiEsterni.getId();
//		this.soggetto = soggetto;
//		this.datiEsterni = datiEsterni;
		this.codiceEnte = codiceEnte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getCodiceEnte() {
		return codiceEnte;
	}

	public void setCodiceEnte(String codiceEnte) {
		this.codiceEnte = codiceEnte;
	}

	public CsASoggettoDatiEsterni getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(CsASoggettoDatiEsterni soggetto) {
		this.soggetto = soggetto;
	}

	public CsADatiEsterni getDatiEsterni() {
		return datiEsterni;
	}

	public void setDatiEsterni(CsADatiEsterni datiEsterni) {
		this.datiEsterni = datiEsterni;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CsADatiEsterniSoggetto other = (CsADatiEsterniSoggetto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CsADatiEsterniSoggetto [id=" + id + ", codiceEnte=" + codiceEnte + ", soggetto=" + soggetto + ", datiEsterni=" + datiEsterni + "]";
	}

}

package it.webred.cs.data.model;

import java.util.Date;

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

	@Column(name = "STATO_DOMANDA")
	private String statoDomanda;

	@Column(name = "COD_PRESTAZIONE")
	private String codicePrestazione;

	@Column(name = "ENTITA_SERVIZIO")
	private String entitaServizio;
	
	@Column(name = "DT_APERTURA_DOMANDA")
	private Date dtAperturaDomanda;
	
	@Column(name = "DT_CHIUSURA_DOMANDA")
	private Date dtChiusuraDomanda;
	
	@Column(name = "TIPO_PRESTAZIONE")
	private String tipoPrestazione;
	
	@Column(name = "INDIRIZZO")
	private String indirizzo;
	
	@ManyToOne
	@JoinColumn(name = "ID_SOGGETTO", insertable = false, updatable = false)
	private CsASoggettoDatiEsterni soggetto;

	@ManyToOne
	@JoinColumn(name = "ID_DATI_ESTERNI", insertable = false, updatable = false)
	private CsADatiEsterni datiEsterni;

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
 
		
		// relations

	public String getCodicePrestazione() {
		return codicePrestazione;
	}

	public void setCodicePrestazione(String codicePrestazione) {
		this.codicePrestazione = codicePrestazione;
	}

	public String getEntitaServizio() {
		return entitaServizio;
	}

	public void setEntitaServizio(String entitaServizio) {
		this.entitaServizio = entitaServizio;
	}

	public Date getDtAperturaDomanda() {
		return dtAperturaDomanda;
	}

	public void setDtAperturaDomanda(Date dtAperturaDomanda) {
		this.dtAperturaDomanda = dtAperturaDomanda;
	}

	public Date getDtChiusuraDomanda() {
		return dtChiusuraDomanda;
	}

	public void setDtChiusuraDomanda(Date dtChiusuraDomanda) {
		this.dtChiusuraDomanda = dtChiusuraDomanda;
	}

	public String getTipoPrestazione() {
		return tipoPrestazione;
	}

	public void setTipoPrestazione(String tipoPrestazione) {
		this.tipoPrestazione = tipoPrestazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getStatoDomanda() {
		return statoDomanda;
	}

	public void setStatoDomanda(String statoDomanda) {
		this.statoDomanda = statoDomanda;
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

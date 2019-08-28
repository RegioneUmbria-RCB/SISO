package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the AR_FF_PROGETTO_ORG database table.
 * SISO-575
 */
@Entity
@Table(name="AR_FF_PROGETTO_ORG")
@NamedQuery(name="ArFfProgettoOrg.findAll", query="SELECT a FROM ArFfProgettoOrg a")
public class ArFfProgettoOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private BigDecimal abilitato;

	private BigDecimal importo;

	//bi-directional many-to-one association to ArFfProgetto
	@ManyToOne
	@JoinColumn(name="PROGETTO_ID")
	private ArFfProgetto arFfProgetto;

	//bi-directional many-to-one association to ArOOrganizzazione
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID")
	private ArOOrganizzazione arOOrganizzazione;

	public ArFfProgettoOrg() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(BigDecimal abilitato) {
		this.abilitato = abilitato;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public ArFfProgetto getArFfProgetto() {
		return this.arFfProgetto;
	}

	public void setArFfProgetto(ArFfProgetto arFfProgetto) {
		this.arFfProgetto = arFfProgetto;
	}

	public ArOOrganizzazione getArOOrganizzazione() {
		return this.arOOrganizzazione;
	}

	public void setArOOrganizzazione(ArOOrganizzazione arOOrganizzazione) {
		this.arOOrganizzazione = arOOrganizzazione;
	}

}
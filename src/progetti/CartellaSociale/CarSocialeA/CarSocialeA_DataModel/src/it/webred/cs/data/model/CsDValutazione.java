package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CS_D_VALUTAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_D_VALUTAZIONE")
@NamedQuery(name="CsDValutazione.findAll", query="SELECT c FROM CsDValutazione c")
public class CsDValutazione implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	@Column(name="DESCRIZIONE_SCHEDA")
	private String descrizioneScheda;

	@Column(name="VERSIONE_SCHEDA")
	private String versioneScheda;

	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;

	public CsDValutazione() {
	}

	public Long getDiarioId() {
		return this.diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public String getDescrizioneScheda() {
		return this.descrizioneScheda;
	}

	public void setDescrizioneScheda(String descrizioneScheda) {
		this.descrizioneScheda = descrizioneScheda;
	}

	public String getVersioneScheda() {
		return this.versioneScheda;
	}

	public void setVersioneScheda(String versioneScheda) {
		this.versioneScheda = versioneScheda;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

}
package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_REL_CATSOC_TIPO_INTER database table.
 * 
 */
@Entity
@Table(name="CS_REL_DIARIO_DIARIORIF")
@NamedQuery(name="CsRelDiarioDiariorif.findAll", query="SELECT c FROM CsRelDiarioDiariorif c")
public class CsRelDiarioDiariorif implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelDiarioDiariorifPK id;

	@ManyToOne
	@JoinColumn(name="DIARIO_ID",insertable=false, updatable=false)
	private CsDDiario csDDiario;

	@ManyToOne
	@JoinColumn(name="DIARIO_ID_RIF",insertable=false, updatable=false)
	private CsDDiario csDDiarioRif;

	public CsRelDiarioDiariorif() {
	}

	public CsRelDiarioDiariorifPK getId() {
		return id;
	}

	public void setId(CsRelDiarioDiariorifPK id) {
		this.id = id;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public CsDDiario getCsDDiarioRif() {
		return csDDiarioRif;
	}

	public void setCsDDiarioRif(CsDDiario csDDiarioRif) {
		this.csDDiarioRif = csDDiarioRif;
	}


}
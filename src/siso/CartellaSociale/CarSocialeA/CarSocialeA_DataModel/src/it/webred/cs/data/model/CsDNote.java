package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_D_NOTE database table.
 * 
 */
@Entity
@Table(name="CS_D_NOTE")
@NamedQuery(name="CsDNote.findAll", query="SELECT c FROM CsDNote c")
public class CsDNote implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;

	public CsDNote() {
	}

	public Long getDiarioId() {
		return this.diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}
}
package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CS_IT_COMUNICAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_IT_COMUNICAZIONE")
@NamedQuery(name="CsItComunicazione.findAll", query="SELECT c FROM CsItComunicazione c")
public class CsItComunicazione implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_IT_COMUNICAZIONE_STEPID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_IT_COMUNICAZIONE_STEPID_GENERATOR")
	@Column(name="STEP_ID")
	private long stepId;

	private String note;

	//bi-directional many-to-one association to CsItComunicazione
	@ManyToOne
	@JoinColumn(name="COMUNICAZIONE_STEP_ID")
	private CsItComunicazione csItComunicazione;

	//bi-directional many-to-one association to CsItComunicazione
	@OneToMany(mappedBy="csItComunicazione")
	private List<CsItComunicazione> csItComunicaziones;

	//bi-directional one-to-one association to CsItStep
	@OneToOne
	@JoinColumn(name="STEP_ID")
	private CsItStep csItStep;

	public CsItComunicazione() {
	}

	public long getStepId() {
		return this.stepId;
	}

	public void setStepId(long stepId) {
		this.stepId = stepId;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CsItComunicazione getCsItComunicazione() {
		return this.csItComunicazione;
	}

	public void setCsItComunicazione(CsItComunicazione csItComunicazione) {
		this.csItComunicazione = csItComunicazione;
	}

	public List<CsItComunicazione> getCsItComunicaziones() {
		return this.csItComunicaziones;
	}

	public void setCsItComunicaziones(List<CsItComunicazione> csItComunicaziones) {
		this.csItComunicaziones = csItComunicaziones;
	}

	public CsItComunicazione addCsItComunicazione(CsItComunicazione csItComunicazione) {
		getCsItComunicaziones().add(csItComunicazione);
		csItComunicazione.setCsItComunicazione(this);

		return csItComunicazione;
	}

	public CsItComunicazione removeCsItComunicazione(CsItComunicazione csItComunicazione) {
		getCsItComunicaziones().remove(csItComunicazione);
		csItComunicazione.setCsItComunicazione(null);

		return csItComunicazione;
	}

	public CsItStep getCsItStep() {
		return this.csItStep;
	}

	public void setCsItStep(CsItStep csItStep) {
		this.csItStep = csItStep;
	}

}
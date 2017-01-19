package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CS_IT_AGGIORNAMENTO database table.
 * 
 */
@Entity
@Table(name="CS_IT_AGGIORNAMENTO")
@NamedQuery(name="CsItAggiornamento.findAll", query="SELECT c FROM CsItAggiornamento c")
public class CsItAggiornamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_IT_AGGIORNAMENTO_STEPID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_IT_AGGIORNAMENTO_STEPID_GENERATOR")
	@Column(name="STEP_ID")
	private long stepId;

	private String note;

	//bi-directional one-to-one association to CsItStep
	@OneToOne
	@JoinColumn(name="STEP_ID")
	private CsItStep csItStep;

	public CsItAggiornamento() {
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

	public CsItStep getCsItStep() {
		return this.csItStep;
	}

	public void setCsItStep(CsItStep csItStep) {
		this.csItStep = csItStep;
	}

}
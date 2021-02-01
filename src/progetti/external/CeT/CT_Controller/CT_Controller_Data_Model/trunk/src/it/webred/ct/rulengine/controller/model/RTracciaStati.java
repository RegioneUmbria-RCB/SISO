package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the R_TRACCIA_STATI database table.
 * 
 */
@Entity
@Table(name="R_TRACCIA_STATI")
public class RTracciaStati implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RTracciaStatiPK id;

	private String note;
	
	private String processid;

	//bi-directional many-to-one association to RTracciaStati
    @ManyToOne
	@JoinColumn(name="ID_STATO")
	private RAnagStati RAnagStati;

    public RTracciaStati() {
    }

	public RTracciaStatiPK getId() {
		return this.id;
	}

	public void setId(RTracciaStatiPK id) {
		this.id = id;
	}
	
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public RAnagStati getRAnagStati() {
		return this.RAnagStati;
	}

	public void setRAnagStati(RAnagStati RAnagStati) {
		this.RAnagStati = RAnagStati;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}
	
}
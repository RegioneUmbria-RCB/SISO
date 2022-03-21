package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the CS_REL_RELAZIONE_PROB database table.
 * 
 */
@Entity
@Table(name="CS_REL_RELAZIONE_PROB")
@NamedQuery(name="CsRelRelazioneProbl.findAll", query="SELECT c FROM CsRelRelazioneProbl c")
public class CsRelRelazioneProbl implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelRelazioneProblPK id;
	
	//uni-directional many-to-one association to CsTbProbl
	@ManyToOne
	@JoinColumn(name="PROB_ID", insertable=false, updatable=false)
	private CsTbProbl csTbProbl;

	//uni-directional many-to-one association to CsDRelazione
	@ManyToOne
	@JoinColumn(name="RELAZIONE_DIARIO_ID", insertable=false, updatable=false)
	private CsDRelazione csDRelazione;

	//uni-directional many-to-one association to CsDRelazione
	@ManyToOne
	@JoinColumn(name="RELAZIONE_DIARIO_RIF_ID")
	private CsDRelazione csDRelazioneRif;

	@Column(name="FLAG_RISOLTA", nullable=false)	
	private Boolean flagRisolta;
	
	@Column(name="FLAG_VERIFICATA", nullable=false)	
	private Boolean flagVerificata;
	
	
	public CsRelRelazioneProbl() {
	}

	public CsTbProbl getCsTbProbl() {
		return this.csTbProbl;
	}

	public void setCsTbProbl(CsTbProbl csProbl) {
		this.csTbProbl = csProbl;
	}

	public CsDRelazione getCsDRelazione() {
		return this.csDRelazione;
	}

	public void setCsDRelazione(CsDRelazione csDRelazione) {
		this.csDRelazione = csDRelazione;
	}

	public CsRelRelazioneProblPK getId() {
		return id;
	}

	public void setId(CsRelRelazioneProblPK id) {
		this.id = id;
	}

	public Boolean getFlagRisolta() {
		return flagRisolta;
	}

	/**
	 * @param flagRisolta the flagRisolta to set
	 */
	public void setFlagRisolta(Boolean flagRisolta) {
		this.flagRisolta = flagRisolta;
	}

	/**
	 * @return the csDRelazioneRif
	 */
	public CsDRelazione getCsDRelazioneRif() {
		return csDRelazioneRif;
	}

	/**
	 * @param csDRelazioneRif the csDRelazioneRif to set
	 */
	public void setCsDRelazioneRif(CsDRelazione csDRelazioneRif) {
		this.csDRelazioneRif = csDRelazioneRif;
	}
	
	public Boolean getFlagVerificata() {
		return flagVerificata;
	}

	public void setFlagVerificata(Boolean flagVerificata) {
		this.flagVerificata = flagVerificata;
	}

	public boolean equals(Object other) {	
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof CsRelRelazioneProbl))return false;
	    CsRelRelazioneProbl otherMyClass = (CsRelRelazioneProbl)other;
	    
	    return (id.equals(otherMyClass.id));			
	}
}
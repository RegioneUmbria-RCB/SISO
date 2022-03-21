package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R_00C_INIZIO_FILE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R_00C_INIZIO_FILE")
public class VInizioFlusso implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="COD_DEST_MITT")
	private String codDestMitt;

	@Column(name="COD_TRACCIATO")
	private String codTracciato;

	@Column(name="DT_CREAZIONE_FILE")
	private String dtCreazioneFile;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String filler;

	private String filler1;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public VInizioFlusso() {
    }

	public String getCodDestMitt() {
		return this.codDestMitt;
	}

	public void setCodDestMitt(String codDestMitt) {
		this.codDestMitt = codDestMitt;
	}

	public String getCodTracciato() {
		return this.codTracciato;
	}

	public void setCodTracciato(String codTracciato) {
		this.codTracciato = codTracciato;
	}

	public String getDtCreazioneFile() {
		return this.dtCreazioneFile;
	}

	public void setDtCreazioneFile(String dtCreazioneFile) {
		this.dtCreazioneFile = dtCreazioneFile;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFiller1() {
		return this.filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}
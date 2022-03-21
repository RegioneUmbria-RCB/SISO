package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_FR_00C_INIZIO_FILE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_FR_00C_INIZIO_FILE")
public class SInizioFile implements Serializable {
	private static final long serialVersionUID = 1L;

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

	@Id
	private String id;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public SInizioFile() {
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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

}
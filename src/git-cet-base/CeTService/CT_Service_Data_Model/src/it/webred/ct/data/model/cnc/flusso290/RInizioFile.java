package it.webred.ct.data.model.cnc.flusso290;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_N0_INIZIO_FILE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_N0_INIZIO_FILE")
public class RInizioFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_ENTE_IMPOSITORE")
	private String codEnteImpositore;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_INVIO_FORNITURA")
	private String dtInvioFornitura;

	@Id
	@Column(name="FILE_ID")
	private String fileId;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public RInizioFile() {
    }

	public String getCodEnteImpositore() {
		return this.codEnteImpositore;
	}

	public void setCodEnteImpositore(String codEnteImpositore) {
		this.codEnteImpositore = codEnteImpositore;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtInvioFornitura() {
		return this.dtInvioFornitura;
	}

	public void setDtInvioFornitura(String dtInvioFornitura) {
		this.dtInvioFornitura = dtInvioFornitura;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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
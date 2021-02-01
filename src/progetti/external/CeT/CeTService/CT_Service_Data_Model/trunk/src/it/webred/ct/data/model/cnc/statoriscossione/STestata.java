package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_FR0_TESTATA database table.
 * 
 */
@Entity
@Table(name="RE_CNC_FR0_TESTATA")
public class STestata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CODICE_AMBITO")
	private BigDecimal codiceAmbito;

	@Column(name="DATA_CREAZIONE")
	private String dataCreazione;

	@Column(name="DATA_RIFERIMENTO")
	private String dataRiferimento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Id
	private String id;

	@Column(name="ID_FILE")
	private String idFile;

	@Column(name="ID_FILE_ORIG")
	private String idFileOrig;

	private BigDecimal mittente;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	private String release;

	@Column(name="TIPO_FILE")
	private String tipoFile;

    public STestata() {
    }

	public BigDecimal getCodiceAmbito() {
		return this.codiceAmbito;
	}

	public void setCodiceAmbito(BigDecimal codiceAmbito) {
		this.codiceAmbito = codiceAmbito;
	}

	public String getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getDataRiferimento() {
		return this.dataRiferimento;
	}

	public void setDataRiferimento(String dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdFile() {
		return this.idFile;
	}

	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}

	public String getIdFileOrig() {
		return this.idFileOrig;
	}

	public void setIdFileOrig(String idFileOrig) {
		this.idFileOrig = idFileOrig;
	}

	public BigDecimal getMittente() {
		return this.mittente;
	}

	public void setMittente(BigDecimal mittente) {
		this.mittente = mittente;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(BigDecimal progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getRelease() {
		return this.release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getTipoFile() {
		return this.tipoFile;
	}

	public void setTipoFile(String tipoFile) {
		this.tipoFile = tipoFile;
	}

}
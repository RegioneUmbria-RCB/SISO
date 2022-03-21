package it.webred.ct.data.model.cnc.flusso290;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_N9_FINE_FILE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_N9_FINE_FILE")
public class RFineFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_ENTE_IMPOSITORE")
	private String codEnteImpositore;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Id
	@Column(name="FILE_ID")
	private String fileId;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="TOT_REC")
	private String totRec;

	@Column(name="TOT_REC_N1")
	private String totRecN1;

	@Column(name="TOT_REC_N2")
	private String totRecN2;

	@Column(name="TOT_REC_N3")
	private String totRecN3;

	@Column(name="TOT_REC_N4")
	private String totRecN4;

	@Column(name="TOT_REC_N5")
	private String totRecN5;

    public RFineFile() {
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

	public String getTotRec() {
		return this.totRec;
	}

	public void setTotRec(String totRec) {
		this.totRec = totRec;
	}

	public String getTotRecN1() {
		return this.totRecN1;
	}

	public void setTotRecN1(String totRecN1) {
		this.totRecN1 = totRecN1;
	}

	public String getTotRecN2() {
		return this.totRecN2;
	}

	public void setTotRecN2(String totRecN2) {
		this.totRecN2 = totRecN2;
	}

	public String getTotRecN3() {
		return this.totRecN3;
	}

	public void setTotRecN3(String totRecN3) {
		this.totRecN3 = totRecN3;
	}

	public String getTotRecN4() {
		return this.totRecN4;
	}

	public void setTotRecN4(String totRecN4) {
		this.totRecN4 = totRecN4;
	}

	public String getTotRecN5() {
		return this.totRecN5;
	}

	public void setTotRecN5(String totRecN5) {
		this.totRecN5 = totRecN5;
	}

}
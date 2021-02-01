package it.webred.ct.data.model.cnc.flusso290;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_N5_FINE_RUOLO database table.
 * 
 */
@Entity
@Table(name="RE_CNC_N5_FINE_RUOLO")
public class RFineRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_COMUNE_ISCR_RUOLO")
	private String codComuneIscrRuolo;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Id
	@Column(name="FILE_ID")
	private String fileId;

	private String processid;

	@Column(name="PROGRESSIVO_MINUTA")
	private String progressivoMinuta;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="TOT_IMPOSTA")
	private String totImposta;

	@Column(name="TOT_REC")
	private String totRec;

	@Column(name="TOT_REC_N2")
	private String totRecN2;

	@Column(name="TOT_REC_N3")
	private String totRecN3;

	@Column(name="TOT_REC_N4")
	private String totRecN4;

    public RFineRuolo() {
    }

	public String getCodComuneIscrRuolo() {
		return this.codComuneIscrRuolo;
	}

	public void setCodComuneIscrRuolo(String codComuneIscrRuolo) {
		this.codComuneIscrRuolo = codComuneIscrRuolo;
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

	public String getProgressivoMinuta() {
		return this.progressivoMinuta;
	}

	public void setProgressivoMinuta(String progressivoMinuta) {
		this.progressivoMinuta = progressivoMinuta;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getTotImposta() {
		return this.totImposta;
	}

	public void setTotImposta(String totImposta) {
		this.totImposta = totImposta;
	}

	public String getTotRec() {
		return this.totRec;
	}

	public void setTotRec(String totRec) {
		this.totRec = totRec;
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

}
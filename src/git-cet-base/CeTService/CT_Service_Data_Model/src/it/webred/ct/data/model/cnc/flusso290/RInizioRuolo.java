package it.webred.ct.data.model.cnc.flusso290;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_N1_INIZIO_RUOLO database table.
 * 
 */
@Entity
@Table(name="RE_CNC_N1_INIZIO_RUOLO")
public class RInizioRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_ART")
	private String codArt;

	@Column(name="COD_COMUNE_ISCR_RUOLO")
	private String codComuneIscrRuolo;

	@Column(name="COD_RUOLO_COATTIVO")
	private String codRuoloCoattivo;

	@Column(name="COD_RUOLO_ICIAP")
	private String codRuoloIciap;

	@Column(name="COD_SEDE")
	private String codSede;

	@Column(name="COD_TIPO_COMPENSO")
	private String codTipoCompenso;

	@Column(name="COD_TIPO_RUOLO")
	private String codTipoRuolo;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Id
	@Column(name="FILE_ID")
	private String fileId;

	private String filler;

	@Column(name="NUMERO_CONVENZIONE")
	private String numeroConvenzione;

	@Column(name="NUMERO_RUOLO")
	private String numeroRuolo;

	private String processid;

	@Column(name="PROGRESSIVO_MINUTA")
	private String progressivoMinuta;

	@Column(name="RATE_RUOLO")
	private String rateRuolo;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public RInizioRuolo() {
    }

	public String getCodArt() {
		return this.codArt;
	}

	public void setCodArt(String codArt) {
		this.codArt = codArt;
	}

	public String getCodComuneIscrRuolo() {
		return this.codComuneIscrRuolo;
	}

	public void setCodComuneIscrRuolo(String codComuneIscrRuolo) {
		this.codComuneIscrRuolo = codComuneIscrRuolo;
	}

	public String getCodRuoloCoattivo() {
		return this.codRuoloCoattivo;
	}

	public void setCodRuoloCoattivo(String codRuoloCoattivo) {
		this.codRuoloCoattivo = codRuoloCoattivo;
	}

	public String getCodRuoloIciap() {
		return this.codRuoloIciap;
	}

	public void setCodRuoloIciap(String codRuoloIciap) {
		this.codRuoloIciap = codRuoloIciap;
	}

	public String getCodSede() {
		return this.codSede;
	}

	public void setCodSede(String codSede) {
		this.codSede = codSede;
	}

	public String getCodTipoCompenso() {
		return this.codTipoCompenso;
	}

	public void setCodTipoCompenso(String codTipoCompenso) {
		this.codTipoCompenso = codTipoCompenso;
	}

	public String getCodTipoRuolo() {
		return this.codTipoRuolo;
	}

	public void setCodTipoRuolo(String codTipoRuolo) {
		this.codTipoRuolo = codTipoRuolo;
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

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getNumeroConvenzione() {
		return this.numeroConvenzione;
	}

	public void setNumeroConvenzione(String numeroConvenzione) {
		this.numeroConvenzione = numeroConvenzione;
	}

	public String getNumeroRuolo() {
		return this.numeroRuolo;
	}

	public void setNumeroRuolo(String numeroRuolo) {
		this.numeroRuolo = numeroRuolo;
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

	public String getRateRuolo() {
		return this.rateRuolo;
	}

	public void setRateRuolo(String rateRuolo) {
		this.rateRuolo = rateRuolo;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

}
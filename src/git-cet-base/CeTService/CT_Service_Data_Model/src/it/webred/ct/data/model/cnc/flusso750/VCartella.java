package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7D_CARTELLA database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7D_CARTELLA")

public class VCartella implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULPartita chiavePartita;
	
	@Column(name="COD_STATO_RICORSO")
	private String codStatoRicorso;

	
	@Column(name="COD_TIPOLOGIA_ATTO")
	private String codTipologiaAtto;


	private String descrizione;

	@Column(name="DESCRIZIONE_1")
	private String descrizione1;

	@Column(name="DESCRIZIONE_2")
	private String descrizione2;

	@Column(name="DESCRIZIONE_BILINGUE")
	private String descrizioneBilingue;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_SCADENZA_PRIVILEGIO")
	private Date dtScadenzaPrivilegio;

	private String filler;

	@Column(name="NUMERO_PARTITA")
	private BigDecimal numeroPartita;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;


	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public VCartella() {
    }


	public String getCodStatoRicorso() {
		return this.codStatoRicorso;
	}

	public void setCodStatoRicorso(String codStatoRicorso) {
		this.codStatoRicorso = codStatoRicorso;
	}



	public String getCodTipologiaAtto() {
		return this.codTipologiaAtto;
	}

	public void setCodTipologiaAtto(String codTipologiaAtto) {
		this.codTipologiaAtto = codTipologiaAtto;
	}



	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione1() {
		return this.descrizione1;
	}

	public void setDescrizione1(String descrizione1) {
		this.descrizione1 = descrizione1;
	}

	public String getDescrizione2() {
		return this.descrizione2;
	}

	public void setDescrizione2(String descrizione2) {
		this.descrizione2 = descrizione2;
	}

	public String getDescrizioneBilingue() {
		return this.descrizioneBilingue;
	}

	public void setDescrizioneBilingue(String descrizioneBilingue) {
		this.descrizioneBilingue = descrizioneBilingue;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtScadenzaPrivilegio() {
		return this.dtScadenzaPrivilegio;
	}

	public void setDtScadenzaPrivilegio(Date dtScadenzaPrivilegio) {
		this.dtScadenzaPrivilegio = dtScadenzaPrivilegio;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public BigDecimal getNumeroPartita() {
		return this.numeroPartita;
	}

	public void setNumeroPartita(BigDecimal numeroPartita) {
		this.numeroPartita = numeroPartita;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public ChiaveULPartita getChiavePartita() {
		return chiavePartita;
	}


	public void setChiavePartita(ChiaveULPartita chiavePartita) {
		this.chiavePartita = chiavePartita;
	}
	
	

}
package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7C_CARTELLA_SPEC database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7C_CARTELLA_SPEC")
public class VCartellaSpeciale implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULPartita chiavePartita;
	
	@Column(name="COD_TIPO_ENTRATA")
	private String codTipoEntrata;


	@Column(name="COD_TIPOLOGIA_ATTO")
	private String codTipologiaAtto;



    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="NUMERO_PARTITA")
	private String numeroPartita;

	@Column(name="PARTE_SPECIALE")
	private String parteSpeciale;

	private String processid;

	@Column(name="PROGRESSIVO_RECORD")
	private String progressivoRecord;


	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public VCartellaSpeciale() {
    }



	public String getCodTipoEntrata() {
		return this.codTipoEntrata;
	}

	public void setCodTipoEntrata(String codTipoEntrata) {
		this.codTipoEntrata = codTipoEntrata;
	}


	public String getCodTipologiaAtto() {
		return this.codTipologiaAtto;
	}

	public void setCodTipologiaAtto(String codTipologiaAtto) {
		this.codTipologiaAtto = codTipologiaAtto;
	}
	

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getNumeroPartita() {
		return this.numeroPartita;
	}

	public void setNumeroPartita(String numeroPartita) {
		this.numeroPartita = numeroPartita;
	}

	public String getParteSpeciale() {
		return this.parteSpeciale;
	}

	public void setParteSpeciale(String parteSpeciale) {
		this.parteSpeciale = parteSpeciale;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(String progressivoRecord) {
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
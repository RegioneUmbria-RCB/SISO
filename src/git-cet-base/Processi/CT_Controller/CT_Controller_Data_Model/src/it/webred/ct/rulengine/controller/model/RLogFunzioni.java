package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the R_LOG_FUNZIONI database table.
 * 
 */
@Entity
@Table(name="R_LOG_FUNZIONI")
public class RLogFunzioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="R_LOG_FUNZIONI_ID_GENERATOR", sequenceName="R_LOG_FUNZIONI_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="R_LOG_FUNZIONI_ID_GENERATOR")
	private long id;

	@Column(name="DATA_FINE")
	private Timestamp dataFine;

	@Column(name="DATA_INI")
	private Timestamp dataIni;

	@Column(name="ID_STATO")
	private BigDecimal idStato;

	@Column(name="NOME_FUNZIONE")
	private String nomeFunzione;

	private String note;

	private String operatore;

	private String parametri;

    public RLogFunzioni() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getDataFine() {
		return this.dataFine;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public Timestamp getDataIni() {
		return this.dataIni;
	}

	public void setDataIni(Timestamp dataIni) {
		this.dataIni = dataIni;
	}

	public BigDecimal getIdStato() {
		return this.idStato;
	}

	public void setIdStato(BigDecimal idStato) {
		this.idStato = idStato;
	}

	public String getNomeFunzione() {
		return this.nomeFunzione;
	}

	public void setNomeFunzione(String nomeFunzione) {
		this.nomeFunzione = nomeFunzione;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperatore() {
		return this.operatore;
	}

	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	public String getParametri() {
		return this.parametri;
	}

	public void setParametri(String parametri) {
		this.parametri = parametri;
	}

}
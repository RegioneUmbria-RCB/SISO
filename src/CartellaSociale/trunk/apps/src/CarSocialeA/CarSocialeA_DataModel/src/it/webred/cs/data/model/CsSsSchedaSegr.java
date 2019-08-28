package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_SS_SCHEDA_SEGR database table.
 * 
 */
@Entity
@Table(name="CS_SS_SCHEDA_SEGR")
public class CsSsSchedaSegr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="FLG_STATO")
	private String flgStato;
	
	@Column(name="COD_ENTE")
	private String codEnte;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	private String stato;
	
	@Column(name="NOTA_STATO")
	private String notaStato;

	@Column(name="FLG_ESISTENTE")
	private Boolean flgEsistente;
	
	//uni-directional one-to-one association to CsASoggetto
	@OneToOne
	@JoinColumn(name="SOGGETTO_ID")
	private CsASoggettoLAZY csASoggetto;
	
	//uni-directional many-to-one association to CsCCategoriaSociale
	@ManyToOne
	@JoinColumn(name="CATEGORIA_SOCIALE_ID")
	private CsCCategoriaSociale csCCategoriaSociale;
	
	public CsSsSchedaSegr() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getFlgStato() {
		return this.flgStato;
	}

	public void setFlgStato(String flgStato) {
		this.flgStato = flgStato;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public CsCCategoriaSociale getCsCCategoriaSociale() {
		return csCCategoriaSociale;
	}

	public void setCsCCategoriaSociale(CsCCategoriaSociale csCCategoriaSociale) {
		this.csCCategoriaSociale = csCCategoriaSociale;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNotaStato() {
		return notaStato;
	}

	public void setNotaStato(String notaStato) {
		this.notaStato = notaStato;
	}

	public Boolean getFlgEsistente() {
		return flgEsistente;
	}

	public void setFlgEsistente(Boolean flgEsistente) {
		this.flgEsistente = flgEsistente;
	}

}
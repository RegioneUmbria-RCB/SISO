package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_REL_SETT_CSOC_TIPO_INTER database table.
 * 
 */
@Entity
@Table(name="CS_CFG_INT_PR_FORM")
public class CsCInterventoPrForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private BigDecimal abilitato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVENTO_ID")  //Al momento ignorare
	private CsCTipoIntervento csIIntervento;
	
	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVENTO_CUSTOM_ID") //Al momento ignorare
	private CsCTipoInterventoCustom csIInterventoCustom;
	
	@ManyToOne 
	@JoinColumn(name="CATEGORIA_SOCIALE_ID") //Al momento ignorare
	private CsCCategoriaSociale csCCategoriaSociale;

	@Column(name="FF_PROGETTO_DESCRIZIONE") 	//--> Usare come filtro per recuperare la form
	private String ffProgettoDescrizione;

	@Column(name="RIF_FORM_INTERVENTO_PR_DETT")
	private String rifFormInterventoPrDett;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(BigDecimal abilitato) {
		this.abilitato = abilitato;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsCTipoIntervento getCsIIntervento() {
		return csIIntervento;
	}

	public void setCsIIntervento(CsCTipoIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsCTipoInterventoCustom getCsIInterventoCustom() {
		return csIInterventoCustom;
	}

	public void setCsIInterventoCustom(CsCTipoInterventoCustom csIInterventoCustom) {
		this.csIInterventoCustom = csIInterventoCustom;
	}

	public CsCCategoriaSociale getCsCCategoriaSociale() {
		return csCCategoriaSociale;
	}

	public void setCsCCategoriaSociale(CsCCategoriaSociale csCCategoriaSociale) {
		this.csCCategoriaSociale = csCCategoriaSociale;
	}

	public String getFfProgettoDescrizione() {
		return ffProgettoDescrizione;
	}

	public void setFfProgettoDescrizione(String ffProgettoDescrizione) {
		this.ffProgettoDescrizione = ffProgettoDescrizione;
	}

	public String getRifFormInterventoPrDett() {
		return rifFormInterventoPrDett;
	}

	public void setRifFormInterventoPrDett(String rifFormInterventoPrDett) {
		this.rifFormInterventoPrDett = rifFormInterventoPrDett;
	}

}
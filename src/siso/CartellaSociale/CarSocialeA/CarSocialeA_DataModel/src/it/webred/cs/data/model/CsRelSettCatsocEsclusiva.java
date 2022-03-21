package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the CS_REL_SETT_CATSOC_ESCLUSIVA database table.
 * 
 */
@Entity
@Table(name="CS_REL_SETT_CATSOC_ESCLUSIVA")
@NamedQuery(name="CsRelSettCatsocEsclusiva.findAll", query="SELECT c FROM CsRelSettCatsocEsclusiva c")
public class CsRelSettCatsocEsclusiva implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelSettCatsocEsclusivaPK id;
	
	private boolean abilitato;
	
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
	
	//mono-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name="SCS_SETTORE_ID",insertable=false, updatable=false)
	private CsOSettore csOSettore;
	
	//mono-directional many-to-one association to CsOSettore
	@ManyToOne
	@JoinColumn(name="TIPO_DIARIO_ID",insertable=false, updatable=false)
	private CsTbTipoDiario csTbTipoDiario;

	public CsRelSettCatsocEsclusiva() {
	}

	public CsRelSettCatsocEsclusivaPK getId() {
		return id;
	}

	public void setId(CsRelSettCatsocEsclusivaPK id) {
		this.id = id;
	}

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

	public CsOSettore getCsOSettore() {
		return csOSettore;
	}

	public void setCsOSettore(CsOSettore csOSettore) {
		this.csOSettore = csOSettore;
	}

	public CsTbTipoDiario getCsTbTipoDiario() {
		return csTbTipoDiario;
	}

	public void setCsTbTipoDiario(CsTbTipoDiario csTbTipoDiario) {
		this.csTbTipoDiario = csTbTipoDiario;
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

}
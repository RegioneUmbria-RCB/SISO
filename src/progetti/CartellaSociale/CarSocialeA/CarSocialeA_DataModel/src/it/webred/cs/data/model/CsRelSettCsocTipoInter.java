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
@Table(name="CS_REL_SETT_CSOC_TIPO_INTER")
@NamedQuery(name="CsRelSettCsocTipoInter.findAll", query="SELECT c FROM CsRelSettCsocTipoInter c")
public class CsRelSettCsocTipoInter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelSettCsocTipoInterPK id;

	private Boolean abilitato;

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



	//bi-directional many-to-one association to CsRelCatsocTipoInter
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CSTI_TIPO_INTERVENTO_ID", referencedColumnName="TIPO_INTERVENTO_ID",   insertable=false, updatable=false),
		@JoinColumn(name="SCS_CATEGORIA_SOCIALE_ID", referencedColumnName="CATEGORIA_SOCIALE_ID",insertable=false, updatable=false)
		})
	private CsRelCatsocTipoInter csRelCatsocTipoInter;

	//bi-directional many-to-one association to CsRelSettoreCatsoc
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="SCS_CATEGORIA_SOCIALE_ID", referencedColumnName="CATEGORIA_SOCIALE_ID",insertable=false, updatable=false),
		@JoinColumn(name="SCS_SETTORE_ID", referencedColumnName="SETTORE_ID",insertable=false, updatable=false)
		})
	private CsRelSettoreCatsoc csRelSettoreCatsoc;

	public CsRelSettCsocTipoInter() {
	}

	public CsRelSettCsocTipoInterPK getId() {
		return this.id;
	}

	public void setId(CsRelSettCsocTipoInterPK id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
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

	public CsRelCatsocTipoInter getCsRelCatsocTipoInter() {
		return this.csRelCatsocTipoInter;
	}

	public void setCsRelCatsocTipoInter(CsRelCatsocTipoInter csRelCatsocTipoInter) {
		this.csRelCatsocTipoInter = csRelCatsocTipoInter;
	}

	public CsRelSettoreCatsoc getCsRelSettoreCatsoc() {
		return this.csRelSettoreCatsoc;
	}

	public void setCsRelSettoreCatsoc(CsRelSettoreCatsoc csRelSettoreCatsoc) {
		this.csRelSettoreCatsoc = csRelSettoreCatsoc;
	}
	
	
}
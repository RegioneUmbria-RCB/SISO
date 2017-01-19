package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_REL_CATSOC_TIPO_INTER database table.
 * 
 */
@Entity
@Table(name="CS_REL_CATSOC_TIPO_INTER")
@NamedQuery(name="CsRelCatsocTipoInter.findAll", query="SELECT c FROM CsRelCatsocTipoInter c")
public class CsRelCatsocTipoInter implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsRelCatsocTipoInterPK id;

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

	//bi-directional many-to-one association to CsCTipoIntervento
	@ManyToOne
	@JoinColumn(name="TIPO_INTERVENTO_ID",insertable=false, updatable=false)
	private CsCTipoIntervento csCTipoIntervento;

	//bi-directional many-to-one association to CsCCategoriaSociale
	@ManyToOne
	@JoinColumn(name="CATEGORIA_SOCIALE_ID",insertable=false, updatable=false)
	private CsCCategoriaSociale csCCategoriaSociale;

	//bi-directional many-to-one association to CsRelSettCsocTipoInter
	@OneToMany(mappedBy="csRelCatsocTipoInter")
	private List<CsRelSettCsocTipoInter> csRelSettCsocTipoInters;

	
	public CsRelCatsocTipoInter() {
	}

	public CsRelCatsocTipoInterPK getId() {
		return this.id;
	}

	public void setId(CsRelCatsocTipoInterPK id) {
		this.id = id;
	}

	public BigDecimal getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(BigDecimal abilitato) {
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

	public CsCTipoIntervento getCsCTipoIntervento() {
		return this.csCTipoIntervento;
	}

	public void setCsCTipoIntervento(CsCTipoIntervento csCTipoIntervento) {
		this.csCTipoIntervento = csCTipoIntervento;
	}

	public CsCCategoriaSociale getCsCCategoriaSociale() {
		return this.csCCategoriaSociale;
	}

	public void setCsCCategoriaSociale(CsCCategoriaSociale csCCategoriaSociale) {
		this.csCCategoriaSociale = csCCategoriaSociale;
	}

	public List<CsRelSettCsocTipoInter> getCsRelSettCsocTipoInters() {
		return this.csRelSettCsocTipoInters;
	}

	public void setCsRelSettCsocTipoInters(List<CsRelSettCsocTipoInter> csRelSettCsocTipoInters) {
		this.csRelSettCsocTipoInters = csRelSettCsocTipoInters;
	}

	public CsRelSettCsocTipoInter addCsRelSettCsocTipoInter(CsRelSettCsocTipoInter csRelSettCsocTipoInter) {
		getCsRelSettCsocTipoInters().add(csRelSettCsocTipoInter);
		csRelSettCsocTipoInter.setCsRelCatsocTipoInter(this);

		return csRelSettCsocTipoInter;
	}

	public CsRelSettCsocTipoInter removeCsRelSettCsocTipoInter(CsRelSettCsocTipoInter csRelSettCsocTipoInter) {
		getCsRelSettCsocTipoInters().remove(csRelSettCsocTipoInter);
		csRelSettCsocTipoInter.setCsRelCatsocTipoInter(null);

		return csRelSettCsocTipoInter;
	}
}
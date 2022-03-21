package it.webred.ss.data.model.privacy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="RDC_CONSENSI_SOC_TO_LAV")
public class RdcConsensiSocToLav implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RdcConsensiSocToLavPK id;
	
	@Column(name="FLAG_CONSENSO")
	private Boolean flagConsenso;
	
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Column(name="USER_MOD")
	private String userMod;

	public RdcConsensiSocToLavPK getId() {
		return id;
	}

	public void setId(RdcConsensiSocToLavPK id) {
		this.id = id;
	}

	public Boolean getFlagConsenso() {
		return flagConsenso;
	}

	public void setFlagConsenso(Boolean flagConsenso) {
		this.flagConsenso = flagConsenso;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDtMod() {
		return dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public String getUserMod() {
		return userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

}
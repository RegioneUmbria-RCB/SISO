package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_VAL_QUOTA")
@NamedQuery(name="CsIValQuota.findAll", query="SELECT c FROM CsIValQuota c")
public class CsIValQuota implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_VAL_QUOTA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_VAL_QUOTA_ID_GENERATOR")
	private long id;
	
	@Column(name="VAL_QUOTA")
	private BigDecimal valQuota;
	
	@Column(name="NOTE")
	private String note;
	  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	//bi-directional one-to-one association to CsIRigaQuota
	@OneToOne(mappedBy="csIValQuota")
	private CsIRigaQuota csIRigaQuota;
	
	@OneToOne(mappedBy="csIValQuota", fetch= FetchType.LAZY)
	private CsIInterventoEseg csIInterventoEseg;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getValQuota() {
		return valQuota;
	}

	public void setValQuota(BigDecimal valQuota) {
		this.valQuota = valQuota;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public CsIRigaQuota getCsIRigaQuota() {
		return csIRigaQuota;
	}

	public void setCsIRigaQuota(CsIRigaQuota csIRigaQuota) {
		this.csIRigaQuota = csIRigaQuota;
	}

	public CsIInterventoEseg getCsIInterventoEseg() {
		return csIInterventoEseg;
	}

	public void setCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {
		this.csIInterventoEseg = csIInterventoEseg;
	}
}

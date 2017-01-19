package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_RIGA_QUOTA")
@NamedQuery(name="CsIRigaQuota.findAll", query="SELECT c FROM CsIRigaQuota c")
public class CsIRigaQuota implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_RIGA_ID_GENERATOR", sequenceName="SQ_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_RIGA_ID_GENERATOR")
	private Long id; 
	
	@Column(name="FLAG_ASSEGNAZ_DIMINUZ")
	private String flagAssegnazDiminuz;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_DA")
	private Date dataDa;
			
	//bi-directional many-to-one association to CsIQuota
	@ManyToOne
	@JoinColumn(name="QUOTA_ID")
	private CsIQuota csIQuota;
	
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

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="VAL_QUOTA_ID")
	private CsIValQuota csIValQuota;
	
	
	public CsIValQuota getCsIValQuota() {
		return csIValQuota;
	}

	public void setCsIValQuota(CsIValQuota csIValQuota) {
		this.csIValQuota = csIValQuota;
	}

	public String getFlagAssegnazDiminuz() {
		return flagAssegnazDiminuz;
	}

	public void setFlagAssegnazDiminuz(String flagAssegnazDiminuz) {
		this.flagAssegnazDiminuz = flagAssegnazDiminuz;
	}

	public CsIQuota getCsIQuota() {
		return csIQuota;
	}

	public void setCsIQuota(CsIQuota csIQuota) {
		this.csIQuota = csIQuota;
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

	public Date getDataDa() {
		return dataDa;
	}

	public void setDataDa(Date dataDa) {
		this.dataDa = dataDa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

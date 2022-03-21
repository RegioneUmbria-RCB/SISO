package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_QUOTA_RIPARTIZ")
@NamedQuery(name="CsIQuotaRipartiz.findAll", query="SELECT c FROM CsIQuotaRipartiz c")
public class CsIQuotaRipartiz implements Serializable {	
	private static final long serialVersionUID = 1L;

	@Id	
	@SequenceGenerator(name="CS_I_QUOTARIP_ID_GENERATOR", sequenceName="SQ_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_QUOTARIP_ID_GENERATOR")
	private Long id; 
	
	@OneToOne
	@JoinColumn(name="QUOTA_ID")
	private CsIQuota csIQuota;	
	
	@Column(name="FLAG_PERIODO_RIPARTIZ")
	private String flagPeriodoRipartiz;
	
	@Column(name="VAL_QUOTA_PERIODO")
	private BigDecimal valQuotaPeriodo;

	@Column(name="N_GG_SETTIMANALI")
	private Integer nGgSettimanali;
	
	@Column(name="N_TOT_ACCESSI_SERVIZIO")
	private Integer nTotAccessiServizio;
	
	@Column(name="FLAG_UNATANTUM")
	private boolean flagUnatantum;
	
	@Column(name="FLAG_IRREGOLARE")
	private boolean flagIrregolare;	//SISO-556

	@Column(name="FLAG_REGOLARE")
	private boolean flagRegolare;  //SISO-556
	
	@Column(name="FLAG_RENDICONTO")
	private String flagRendiconto;
	  
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


	public CsIQuota getCsIQuota() {
		return csIQuota;
	}

	public void setCsIQuota(CsIQuota csIQuota) {
		this.csIQuota = csIQuota;
	}

	public String getFlagPeriodoRipartiz() {
		return flagPeriodoRipartiz;
	}

	public void setFlagPeriodoRipartiz(String flagPeriodoRipartiz) {
		this.flagPeriodoRipartiz = flagPeriodoRipartiz;
	}

	public BigDecimal getValQuotaPeriodo() {
		return valQuotaPeriodo;
	}

	public void setValQuotaPeriodo(BigDecimal valQuotaPeriodo) {
		this.valQuotaPeriodo = valQuotaPeriodo;
	}

	public Integer getnGgSettimanali() {
		return nGgSettimanali;
	}

	public void setnGgSettimanali(Integer nGgSettimanali) {
		this.nGgSettimanali = nGgSettimanali;
	}

	public Integer getnTotAccessiServizio() {
		return nTotAccessiServizio;
	}

	public void setnTotAccessiServizio(Integer nTotAccessiServizio) {
		this.nTotAccessiServizio = nTotAccessiServizio;
	}

	public boolean isFlagUnatantum() {
		return flagUnatantum;
	}

	public void setFlagUnatantum(boolean flagUnatantum) {
		this.flagUnatantum = flagUnatantum;
	}

	public String getFlagRendiconto() {
		return flagRendiconto;
	}

	public void setFlagRendiconto(String flagRendiconto) {
		this.flagRendiconto = flagRendiconto;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFlagIrregolare() {
		return flagIrregolare;
	}

	public void setFlagIrregolare(boolean flagIrregolare) {
		this.flagIrregolare = flagIrregolare;
	}

	public boolean isFlagRegolare() {
		return flagRegolare;
	}

	public void setFlagRegolare(boolean flagRegolare) {
		this.flagRegolare = flagRegolare;
	}
	
	
	
}

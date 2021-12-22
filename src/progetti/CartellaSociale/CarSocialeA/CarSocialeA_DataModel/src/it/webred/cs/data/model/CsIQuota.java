package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_I_QUOTA")
@NamedQuery(name="CsIQuota.findAll", query="SELECT c FROM CsIQuota c")
public class CsIQuota implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_QUOTA_ID_GENERATOR", sequenceName="SQ_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_QUOTA_ID_GENERATOR")
	private Long id;
	
	//one-directional many-to-one association to csTbUnitaMisura 
	@ManyToOne
	@JoinColumn(name="UNITA_MISURA_ID")
	private CsTbUnitaMisura csTbUnitaMisura;
	
	@Column(name="TARIFFA")
	private BigDecimal tariffa;
	
	@Column(name="DESC_TARIFFA")
	private String descTariffa;
	
	@Column(name="COMP_UTENTE")
	private BigDecimal compUtente;
	
	@Column(name="FLAG_PERIODO_COMP")
	private String flagPeriodoComp;
	
	@Column(name="COMP_UTENTE_PERIODO")
	private BigDecimal compUtentePeriodo;
	
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
	
	//TODO ML: controllare annotation 
	//http://progetti.asc.webred.it/browse/SISO-456
	@OneToMany(mappedBy="csIQuota", fetch=FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CsIRigaQuota> csIRigheQuota;
	
	@OneToOne(mappedBy="csIQuota", cascade = CascadeType.ALL, orphanRemoval = true)
	private CsIQuotaRipartiz csIQuotaRipartiz;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CsTbUnitaMisura getCsTbUnitaMisura() {
		return csTbUnitaMisura;
	}

	public void setCsTbUnitaMisura(CsTbUnitaMisura csTbUnitaMisura) {
		this.csTbUnitaMisura = csTbUnitaMisura;
	}

	public BigDecimal getTariffa() {
		return tariffa;
	}

	public void setTariffa(BigDecimal tariffa) {
		this.tariffa = tariffa;
	}

	public String getDescTariffa() {
		return descTariffa;
	}

	public void setDescTariffa(String descTariffa) {
		this.descTariffa = descTariffa;
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
	
	public Set<CsIRigaQuota> getCsIRigheQuota() {
		return csIRigheQuota;
	}

	public void setCsIRigheQuota(Set<CsIRigaQuota> csIRigheQuota) {
		this.csIRigheQuota = csIRigheQuota;
	}

	public CsIQuotaRipartiz getCsIQuotaRipartiz() {
		return csIQuotaRipartiz;
	}

	public void setCsIQuotaRipartiz(CsIQuotaRipartiz csIQuotaRipartiz) {
		this.csIQuotaRipartiz = csIQuotaRipartiz;
	}

	public BigDecimal getCompUtente() {
		return compUtente;
	}

	public void setCompUtente(BigDecimal compUtente) {
		this.compUtente = compUtente;
	}

	public String getFlagPeriodoComp() {
		return flagPeriodoComp;
	}

	public void setFlagPeriodoComp(String flagPeriodoComp) {
		this.flagPeriodoComp = flagPeriodoComp;
	}

	public BigDecimal getCompUtentePeriodo() {
		return compUtentePeriodo;
	}

	public void setCompUtentePeriodo(BigDecimal compUtentePeriodo) {
		this.compUtentePeriodo = compUtentePeriodo;
	}

	public boolean isValuta(){
		return getCsTbUnitaMisura()!=null && "€".equals(getCsTbUnitaMisura().getValore());
	}
	
	public boolean isOreMinuti(){
		return getCsTbUnitaMisura()!=null && "ore/minuti".equalsIgnoreCase(getCsTbUnitaMisura().getValore());
	}
	
	public boolean isKm(){
		return getCsTbUnitaMisura()!=null && "km".equalsIgnoreCase(getCsTbUnitaMisura().getValore());
	}
	
}

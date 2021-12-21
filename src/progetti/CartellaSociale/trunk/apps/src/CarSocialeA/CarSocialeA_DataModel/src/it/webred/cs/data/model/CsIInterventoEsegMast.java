package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
@Table(name="CS_I_INTERVENTO_ESEG_MAST")
@NamedQuery(name="CsIInterventoEsegMast.findAll", query="SELECT c FROM CsIInterventoEsegMast c")
public class CsIInterventoEsegMast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_INTERVENTO_ESEG_MAST_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_INTERVENTO_ESEG_MAST_ID_GENERATOR")
	private long id;
	
	@Column(name="COMPART_ALTRE")
	private BigDecimal compartAltre;

	@Column(name="COMPART_SSN")
	private BigDecimal compartSsn;

	@Column(name="COMPART_UTENTI")
	private BigDecimal compartUtenti;

	@Column(name="DESC_INTERVENTO_ESEG")
	private String descInterventoEseg;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="NOTE_ALTRE_COMPART")
	private String noteAltreCompart;

	@Column(name="PERC_GESTITA_ENTE")
	private BigDecimal percGestitaEnte;

	@Column(name="SPESA")
	private BigDecimal spesa;
	
	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVENTO_CUSTOM_ID")
	private CsCTipoInterventoCustom csIInterventoCustom;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	@Column(name="VALORE_GESTITA_ENTE")
	private BigDecimal valoreGestitaEnte;
	
	@Column(name="CATEGORIA_SOCIALE_ID")
	private Long categoriaSocialeId;
	
	@Column(name="PROT_DOM_PREST")
	private String protDomPrest;
	
	@Column(name="DATA_DOM_PREST")
	private Date dataDomPrest;
	
	
	//bi-directional many-to-one association to CsIInterventoEseg
	@OneToMany(mappedBy="csIInterventoEsegMast", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<CsIInterventoEseg> csIInterventoEsegs;
	
	//bi-directional many-to-one association to CsIInterventoEseg
	@OneToMany(mappedBy="master", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<CsIInterventoEsegMastSogg> beneficiari;	
	
	//bi-directional many-to-one association to CsIP
	@OneToOne(mappedBy="csIInterventoEsegMast", cascade = CascadeType.ALL, orphanRemoval=true)
	private CsIPs csIPs;

	//bi-directional many-to-one association to CsCTipoIntervento
	@ManyToOne
	@JoinColumn(name="TIPO_INTERVENTO_ID")
	private CsCTipoIntervento csCTipoIntervento;

	@Column(name="INTERVENTO_ID")
	private Long interventoProgrammatoId; //FK DI CS_I_INTERVENTO: eliminata mappatura per alleggerire il caricamento
	
	@Column(name="FLAG_SPESA_CALC")
	private Boolean flagSpesaCalc;
	
	@Column(name="TIPO_BENEFICIARIO")
	private String tipoBeneficiario;
	
	//bi-directional many-to-one association to CsOOperatoreSettore
	@ManyToOne
	@JoinColumn(name="OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore csOOperatoreSettore;
	
	@ManyToOne
	@JoinColumn(name="SETTORE_EROGANTE_ID")
	private CsOSettore settoreErogante;

	//bi-directional many-to-one association to csIInterventoEseg vecchia
//	@OneToMany(mappedBy = "csIInterventoEsegMast", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	private List<CsIInterventoEseg> csIInterventoEseg;
	//TODO ML: http://progetti.asc.webred.it/browse/SISO-456
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "QUOTA_ID")
	private CsIQuota csIQuota;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="INT_PROGETTO_ID")
	private CsIInterventoPr csIInterventoPr;
	
	@Column(name = "DIARIO_PAI_ID")
	private BigDecimal diarioPaiId;
	
	//SISO-812
	@Column(name = "SETT_SECONDO_LIVELLO")
	private Long settoreSecondoLivello;
	
	@Column(name="TOT_BENEFICIARI")
	private Integer totBeneficiari;
	
	
	public CsIInterventoEsegMast(){
		beneficiari= new HashSet<CsIInterventoEsegMastSogg>();
		csIInterventoEsegs= new HashSet<CsIInterventoEseg>();
	}
	
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getCompartAltre() {
		return this.compartAltre;
	}

	public void setCompartAltre(BigDecimal compartAltre) {
		if(compartAltre!=null){
			if(compartAltre.compareTo(new BigDecimal((long)0))==0)
				this.compartAltre=null;
			else			
				this.compartAltre = compartAltre;
		}else			
			this.compartAltre = compartAltre;
	}

	public BigDecimal getCompartSsn() {
		return this.compartSsn;
	}

	public void setCompartSsn(BigDecimal compartSsn) {
		if(compartSsn!=null){
			if(compartSsn.compareTo(new BigDecimal((long)0))==0)
				this.compartSsn=null;
			else
				this.compartSsn = compartSsn;
		}else
			this.compartSsn = compartSsn;
	}

	public BigDecimal getCompartUtenti() {
		return this.compartUtenti;
	}

	public void setCompartUtenti(BigDecimal compartUtenti) {
		if(compartUtenti!=null){
			if(compartUtenti.compareTo(new BigDecimal((long)0))==0)
				this.compartUtenti=null;
			else
				this.compartUtenti = compartUtenti;
		}else
			this.compartUtenti = compartUtenti;
	}

	public String getDescInterventoEseg() {
		return this.descInterventoEseg;
	}

	public void setDescInterventoEseg(String descInterventoEseg) {
		this.descInterventoEseg = descInterventoEseg;
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

	public String getNoteAltreCompart() {
		return this.noteAltreCompart;
	}

	public void setNoteAltreCompart(String noteAltreCompart) {
		this.noteAltreCompart = noteAltreCompart;
	}

	public BigDecimal getPercGestitaEnte() {
		return this.percGestitaEnte;
	}

	public void setPercGestitaEnte(BigDecimal percGestitaEnte) {
		if(percGestitaEnte!=null){
			if(percGestitaEnte.compareTo(new BigDecimal((long)0))==0)
				this.percGestitaEnte=null;
			else
				this.percGestitaEnte = percGestitaEnte;
		}else
			this.percGestitaEnte = percGestitaEnte;
	}

	public BigDecimal getSpesa() {
     	return this.spesa; //SISO-806
	}

	public void setSpesa(BigDecimal spesa) {
//		this.spesa = spesa; //SISO-810
		this.spesa = spesa.setScale(2, RoundingMode.HALF_EVEN); //SISO-806
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

	public BigDecimal getValoreGestitaEnte() {
       return this.valoreGestitaEnte;		
	}

	public void setValoreGestitaEnte(BigDecimal valoreGestitaEnte) {
		if(valoreGestitaEnte!=null){
			if(valoreGestitaEnte.compareTo(new BigDecimal((long)0))==0)
				this.valoreGestitaEnte=null;
			else
				this.valoreGestitaEnte = valoreGestitaEnte.setScale(2, RoundingMode.CEILING); //SISO-806;
		}else
			this.valoreGestitaEnte = valoreGestitaEnte;
	}

	public Set<CsIInterventoEseg> getCsIInterventoEsegs() {
		return this.csIInterventoEsegs;
	}

	public void setCsIInterventoEsegs(Set<CsIInterventoEseg> csIInterventoEsegs) {
		if(getCsIInterventoEsegs()==null)
			csIInterventoEsegs= new HashSet<CsIInterventoEseg>();
		
		this.csIInterventoEsegs.clear();
		if(csIInterventoEsegs!=null)
			this.csIInterventoEsegs.addAll(csIInterventoEsegs);
	}

	public CsIInterventoEseg addCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {				
		if(getCsIInterventoEsegs()==null)
			csIInterventoEsegs= new HashSet<CsIInterventoEseg>();
		csIInterventoEsegs.add(csIInterventoEseg);
		csIInterventoEseg.setCsIInterventoEsegMast(this);

		return csIInterventoEseg;
	}

	public CsIInterventoEseg removeCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {
		getCsIInterventoEsegs().remove(csIInterventoEseg);
		csIInterventoEseg.setCsIInterventoEsegMast(null);

		return csIInterventoEseg;
	}

	public CsCTipoIntervento getCsCTipoIntervento() {
		return this.csCTipoIntervento;
	}

	public void setCsCTipoIntervento(CsCTipoIntervento csCTipoIntervento) {
		this.csCTipoIntervento = csCTipoIntervento;
	}

	public CsCTipoInterventoCustom getCsIInterventoCustom() {
		return csIInterventoCustom;
	}

	public void setCsIInterventoCustom(CsCTipoInterventoCustom csIInterventoCustom) {
		this.csIInterventoCustom = csIInterventoCustom;
	}
	
	public Long getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(Long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public Boolean getFlagSpesaCalc() {
		return flagSpesaCalc;
	}

	public void setFlagSpesaCalc(Boolean flagSpesaCalc) {
		this.flagSpesaCalc = flagSpesaCalc;
	}

	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}

	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}

	public CsOOperatoreSettore getCsOOperatoreSettore() {
		return csOOperatoreSettore;
	}

	public void setCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		this.csOOperatoreSettore = csOOperatoreSettore;
	}

	public Set<CsIInterventoEsegMastSogg> getBeneficiari() {
		return beneficiari;
	}
	
	public void setBeneficiari(Set<CsIInterventoEsegMastSogg> set) {
		//this.beneficiari = set; //This will override the set that Hibernate is tracking.
		if(beneficiari==null) beneficiari= new HashSet<CsIInterventoEsegMastSogg>();
		this.beneficiari.clear();
	    if (set != null) this.beneficiari.addAll(set);
	}

	public CsIInterventoEsegMastSogg addBeneficiario(CsIInterventoEsegMastSogg b) {				
		if(beneficiari==null) beneficiari= new HashSet<CsIInterventoEsegMastSogg>();
		beneficiari.add(b);
		b.setMaster(this);
		b.getId().setMasterId(this.getId());

		return b;
	}

	public CsIInterventoEsegMastSogg removeBeneficiario(CsIInterventoEsegMastSogg b) {
		getBeneficiari().remove(b);
		b.setMaster(null);
		b.getId().setMasterId(null);
		return b;
	}
	//TODO ML: http://progetti.asc.webred.it/browse/SISO-456
	public CsIQuota getCsIQuota() {
		return csIQuota;
	}

	public void setCsIQuota(CsIQuota csIQuota) {
		this.csIQuota = csIQuota;
	}
	
	public String getProtDomPrest() {
		return protDomPrest;
	}

	public void setProtDomPrest(String protDomPrest) {
		this.protDomPrest = protDomPrest;
	}

	public CsIPs getCsIPs() {
		return this.csIPs;
	}

	public void setCsIPs(CsIPs csIPs) {
		this.csIPs = csIPs;
	}
	
	public CsIInterventoPr getCsIInterventoPr() {
		return csIInterventoPr;
	}

	public void setCsIInterventoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}
	
	public CsOSettore getSettoreErogante() {
		return settoreErogante;
	}

	public void setSettoreErogante(CsOSettore settoreErogante) {
		this.settoreErogante = settoreErogante;
	}
	
	public CsIInterventoEsegMastSogg getBeneficiarioRiferimento(){
		if(this.getBeneficiari()!=null){
			Iterator<CsIInterventoEsegMastSogg> it =  this.getBeneficiari().iterator();
			while(it.hasNext()){
				CsIInterventoEsegMastSogg s = it.next();
				if(s.getRiferimento().booleanValue())
					return s;
			}
		}return null;
	}

	public BigDecimal getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(BigDecimal diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}
	
	public Long getSettoreSecondoLivello() {
		return settoreSecondoLivello;
	}

	public void setSettoreSecondoLivello(Long settoreSecondoLivello) {
		this.settoreSecondoLivello = settoreSecondoLivello;
	}
	
	public Date getDataDomPrest() {
		return dataDomPrest;
	}

	public void setDataDomPrest(Date dataDomPrest) {
		this.dataDomPrest = dataDomPrest;
	}
	
	public Integer getTotBeneficiari() {
		return totBeneficiari;
	}

	public void setTotBeneficiari(Integer totBeneficiari) {
		this.totBeneficiari = totBeneficiari;
	}

	public Long getInterventoProgrammatoId() {
		return interventoProgrammatoId;
	}

	public void setInterventoProgrammatoId(Long interventoProgrammatoId) {
		this.interventoProgrammatoId = interventoProgrammatoId;
	}

	@Override
	public String toString() {
		return "CsIInterventoEsegMast [id=" + id 
				+ ", compartAltre=" + compartAltre + ", compartSsn="
				+ compartSsn + ", compartUtenti=" + compartUtenti
				+ ", descInterventoEseg=" + descInterventoEseg + ", dtIns="
				+ dtIns + ", dtMod=" + dtMod + ", noteAltreCompart="
				+ noteAltreCompart + ", percGestitaEnte=" + percGestitaEnte
				+ ", spesa=" + spesa + ", csIInterventoCustom="
				+ csIInterventoCustom + ", userIns=" + userIns + ", usrMod="
				+ usrMod + ", valoreGestitaEnte=" + valoreGestitaEnte
				+ ", categoriaSocialeId=" + categoriaSocialeId  + ", protDomPrest=" + protDomPrest
				+ ", csIInterventoEsegs=" + csIInterventoEsegs
				+ ", beneficiari=" + beneficiari + ", csCTipoIntervento="
				+ csCTipoIntervento + ", csIIntervento=" + interventoProgrammatoId 
				+ ", flagSpesaCalc=" + flagSpesaCalc + ", flagCompartCalc="
				+ ", tipoBeneficiario=" + tipoBeneficiario
				+ ", csOOperatoreSettore=" + csOOperatoreSettore
				+ ", csIQuota=" + csIQuota + ", csIPs=" + csIPs + "]";

	}

}
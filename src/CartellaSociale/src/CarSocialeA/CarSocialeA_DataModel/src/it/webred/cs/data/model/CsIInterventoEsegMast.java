package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the CS_I_INTERVENTO_ESEG_MAST database table.
 * 
 */
@Entity
@Table(name="CS_I_INTERVENTO_ESEG_MAST")
@NamedQuery(name="CsIInterventoEsegMast.findAll", query="SELECT c FROM CsIInterventoEsegMast c")
public class CsIInterventoEsegMast implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_INTERVENTO_ESEG_MAST_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_INTERVENTO_ESEG_MAST_ID_GENERATOR")
	private long id;
	
	@Column(name="CODICE_FIN1")
	private String codiceFin1;
	
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

	@Column(name="FF_ORIGINE_ID")
	private Long ffOrigineId;

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
	
	@Column(name="GEST_ZONA")
	private Boolean gestZona;
	 
	@Column(name="PROT_DOM_PREST")
	private String protDomPrest;
	
	//bi-directional many-to-one association to CsIInterventoEseg
	@OneToMany(mappedBy="csIInterventoEsegMast", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<CsIInterventoEseg> csIInterventoEsegs;	
	
	//bi-directional many-to-one association to CsIInterventoEseg
	@OneToMany(mappedBy="master", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CsIInterventoEsegMastSogg> beneficiari;	

	//bi-directional many-to-one association to CsCTipoIntervento
	@ManyToOne
	@JoinColumn(name="TIPO_INTERVENTO_ID")
	private CsCTipoIntervento csCTipoIntervento;

	//bi-directional many-to-one association to CsIIntervento
	@ManyToOne
	@JoinColumn(name="INTERVENTO_ID")
	private CsIIntervento csIIntervento;

/*	//TODO:Campo ridondante aggiornato automaticamente da settoreErogante--> andrà rimosso alla correzione della query di caricamento
	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	private CsOOrganizzazione organizzazioneErogante;*/
	
	//Settore EROGANTE
	@ManyToOne
	@JoinColumn(name="SETTORE_EROGANTE_ID")
	private CsOSettore settoreErogante;
	
	@ManyToOne
	@JoinColumn(name="SETTORE_TITOLARE_ID")
	private CsOSettore settoreTitolare;
	
	@Column(name="FLAG_SPESA_CALC")
	private Boolean flagSpesaCalc;
	
	@Column(name="FLAG_COMPART_CALC")
	private Boolean flagCompartCalc;
	
	@Column(name="TIPO_BENEFICIARIO")
	private String tipoBeneficiario;
	
	//bi-directional many-to-one association to CsOOperatoreSettore
	@ManyToOne
	@JoinColumn(name="OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore csOOperatoreSettore;

	
	//bi-directional many-to-one association to csIInterventoEseg vecchia
//	@OneToMany(mappedBy = "csIInterventoEsegMast", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//	private List<CsIInterventoEseg> csIInterventoEseg;
	//TODO ML: http://progetti.asc.webred.it/browse/SISO-456
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "QUOTA_ID")
	private CsIQuota csIQuota;
	


	//bi-directional many-to-one association to CsIP
	@OneToOne(mappedBy="csIInterventoEsegMast", cascade = CascadeType.ALL)
	private CsIPs csIPs;
		
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCodiceFin1() {
		return this.codiceFin1;
	}

	public void setCodiceFin1(String codiceFin1) {
		this.codiceFin1 = codiceFin1;
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
		return this.spesa;
	}

	public void setSpesa(BigDecimal spesa) {
		if(spesa!=null){
			if(spesa.compareTo(new BigDecimal((long)0))==0)
				this.spesa=null;
			else
				this.spesa = spesa;
		}else
			this.spesa = spesa;
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
			this.valoreGestitaEnte = valoreGestitaEnte;
		}else
			this.valoreGestitaEnte = valoreGestitaEnte;
	}

	public Set<CsIInterventoEseg> getCsIInterventoEsegs() {
		return this.csIInterventoEsegs;
	}

	public void setCsIInterventoEsegs(Set<CsIInterventoEseg> csIInterventoEsegs) {
		this.csIInterventoEsegs = csIInterventoEsegs;
	}

	public CsIInterventoEseg addCsIInterventoEseg(CsIInterventoEseg csIInterventoEseg) {				
		if(csIInterventoEsegs==null)
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

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsCTipoInterventoCustom getCsIInterventoCustom() {
		return csIInterventoCustom;
	}

	public void setCsIInterventoCustom(CsCTipoInterventoCustom csIInterventoCustom) {
		this.csIInterventoCustom = csIInterventoCustom;
	}
	
	public Long getFfOrigineId() {
		return ffOrigineId;
	}

	public void setFfOrigineId(Long ffOrigineId) {
		this.ffOrigineId = ffOrigineId;
	}
	
	public Long getCategoriaSocialeId() {
		return categoriaSocialeId;
	}

	public void setCategoriaSocialeId(Long categoriaSocialeId) {
		this.categoriaSocialeId = categoriaSocialeId;
	}

	public Boolean getGestZona() {
		return gestZona;
	}

	public void setGestZona(Boolean gestZona) {
		this.gestZona = gestZona;
	}

	public Boolean getFlagSpesaCalc() {
		return flagSpesaCalc;
	}

	public Boolean getFlagCompartCalc() {
		return flagCompartCalc;
	}

	public void setFlagSpesaCalc(Boolean flagSpesaCalc) {
		this.flagSpesaCalc = flagSpesaCalc;
	}

	public void setFlagCompartCalc(Boolean flagCompartCalc) {
		this.flagCompartCalc = flagCompartCalc;
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
		this.beneficiari = set;
	}

	public CsIInterventoEsegMastSogg addBeneficiario(CsIInterventoEsegMastSogg b) {				
		if(beneficiari==null)
			beneficiari= new HashSet<CsIInterventoEsegMastSogg>();
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

	public CsOSettore getSettoreErogante() {
		return settoreErogante;
	}

	public CsOSettore getSettoreTitolare() {
		return settoreTitolare;
	}

	public void setSettoreErogante(CsOSettore settoreErogante) {
		this.settoreErogante = settoreErogante;
	}

	public void setSettoreTitolare(CsOSettore settoreTitolare) {
		this.settoreTitolare = settoreTitolare;
	}


	
	public CsIPs getCsIPs() {
		return this.csIPs;
	}

	public void setCsIPs(CsIPs csIPs) {
		this.csIPs = csIPs;


	}

	@Override
	public String toString() {
		return "CsIInterventoEsegMast [id=" + id + ", codiceFin1=" + codiceFin1
				+ ", compartAltre=" + compartAltre + ", compartSsn="
				+ compartSsn + ", compartUtenti=" + compartUtenti
				+ ", descInterventoEseg=" + descInterventoEseg + ", dtIns="
				+ dtIns + ", dtMod=" + dtMod + ", ffOrigineId=" + ffOrigineId
				 + ", noteAltreCompart="
				+ noteAltreCompart + ", percGestitaEnte=" + percGestitaEnte
				+ ", spesa=" + spesa + ", csIInterventoCustom="
				+ csIInterventoCustom + ", userIns=" + userIns + ", usrMod="
				+ usrMod + ", valoreGestitaEnte=" + valoreGestitaEnte
				+ ", categoriaSocialeId=" + categoriaSocialeId + ", gestZona="
				+ gestZona + ", protDomPrest=" + protDomPrest
				+ ", csIInterventoEsegs=" + csIInterventoEsegs
				+ ", beneficiari=" + beneficiari + ", csCTipoIntervento="
				+ csCTipoIntervento + ", csIIntervento=" + csIIntervento 
				+ ", flagSpesaCalc=" + flagSpesaCalc + ", flagCompartCalc="
				+ flagCompartCalc + ", tipoBeneficiario=" + tipoBeneficiario
				+ ", csOOperatoreSettore=" + csOOperatoreSettore
				+ ", csIQuota=" + csIQuota + ", csIPs=" + csIPs + "]";

	}
}
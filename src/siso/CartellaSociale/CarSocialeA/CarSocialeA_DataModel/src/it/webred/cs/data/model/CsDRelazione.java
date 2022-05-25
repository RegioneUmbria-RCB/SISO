package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * The persistent class for the CS_D_RELAZIONE database table.
 * 
 */
@Entity
@Table(name="CS_D_RELAZIONE")
@NamedQuery(name="CsDRelazione.findAll", query="SELECT c FROM CsDRelazione c")
public class CsDRelazione implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_PROSSIMA_RELAZIONE_AL")
	private Date dataProssimaRelazioneAl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_PROSSIMA_RELAZIONE_DAL")
	private Date dataProssimaRelazioneDal;

	@Column(name="FONTI_UTILIZZATE")
	private String fontiUtilizzate;
	
	private String proposta;

	@Column(name="SITUAZIONE_AMB")
	private String situazioneAmb;

	@Column(name="SITUAZIONE_PARENTALE")
	private String situazioneParentale;

	@Column(name="SITUAZIONE_SANITARIA")
	private String situazioneSanitaria;
	
	@Column(name="ORG_SERVIZIO")
	private String organizzazioneServizio;
	
	@Column(name="RICHIESTA_INDAGINE")
	private String richiestaIndagine;

	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	@JsonIgnore
	private CsDDiario csDDiario;
	
	//bi-directional many-to-many association to CsCTipoIntervento	
	@ManyToMany(fetch = FetchType.EAGER, cascade={})
	@JoinTable(name="CS_REL_RELAZIONE_TIPOINT", 
		joinColumns={@JoinColumn(name="RELAZIONE_DIARIO_ID", insertable=false, updatable=false)}, 
		inverseJoinColumns={@JoinColumn(name="TIPO_INTERVENTO_ID", insertable=false, updatable=false)})
	@JsonIgnore
	private Set<CsCTipoIntervento> csCTipoInterventos;		
		
	//uni-directional many-to-one association to CsTbMacroAttivita
	@ManyToOne
	@JoinColumn(name="MACRO_ATT_ID")
	private CsTbMacroAttivita macroAttivita;
	
	//uni-directional many-to-one association to CsTbMicroAttivita
	@ManyToOne
	@JoinColumn(name="MICRO_ATT_ID")
	private CsTbMicroAttivita microAttivita;
	
	@Lob
	@Column(name="TESTO")
	@Basic(fetch=FetchType.LAZY)
	@JsonIgnore
	private String testo;

	@Column(name="FLAG_RILEVAZ_PROBLEMATICHE")
	private Boolean flagRilevazioneProblematiche;

	//bi-directional one-to-many association to CsRelRelazioneProbl
	@OneToMany(mappedBy="csDRelazione", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonIgnore
	private List<CsRelRelazioneProbl> csRelRelazioneProbl;
	
	//bi-directional one-to-many association to CsRelRelazioneTipoint
	@OneToMany(mappedBy="csDRelazione", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonIgnore
	private List<CsRelRelazioneTipoint> csRelRelazioneTipoint;
	
	//bi-directional one-to-many association to CsRelRelazioneProbl
	@OneToMany(mappedBy="csDRelazioneRif", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CsRelRelazioneProbl> csRelRelazioneProblReverseRif;
		
	// bi-directional many-to-many association to CsCDiarioConchi
	@ManyToMany(fetch = FetchType.EAGER )
	@Fetch(FetchMode.SELECT)
	@JoinTable(name="CS_D_RELAZIONE_CON_CHI", 
			   joinColumns={@JoinColumn(name="RELAZIONE_ID") }, 
			   inverseJoinColumns={@JoinColumn(name="DIARIO_CON_CHI_ID")})
	 private List<CsCDiarioConchi> lstConChi;
	
	// bi-directional many-to-many association to CsCDiarioConchi
	@ManyToMany(fetch = FetchType.EAGER )
	@Fetch(FetchMode.SELECT)
	@JoinTable(name="CS_D_RELAZIONE_RIUNIONE_CON", 
			   joinColumns={@JoinColumn(name="RELAZIONE_ID") }, 
			   inverseJoinColumns={@JoinColumn(name="SETTORE_ID")})
	 private List<CsOSettore> lstRiunioneConChi;

	
	@Column(name="CON_CHI_ALTRO")
	private String conChiAltro;
	
	@Column(name="NUM_OPERATORI")	
	private Long numOperatori;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ORE_IMPIEGATE")
	private Date oreImpiegate;
	
	//uni-directional one-to-one association to CsDTriage
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DIARIO_ID")
	private CsDTriage csDTriage;
	
	//uni-directional one-to-one association to CsDRelSal
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="DIARIO_ID")
	private CsDRelSal csDRelSal;
	
	
	public CsDRelazione() {
		this.lstConChi = new ArrayList<CsCDiarioConchi>();
		this.lstRiunioneConChi = new ArrayList<CsOSettore>();
	}
	

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}
	
	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}


	public String getProposta() {
		return this.proposta;
	}

	public void setProposta(String proposta) {
		this.proposta = proposta;
	}

	public String getSituazioneAmb() {
		return this.situazioneAmb;
	}

	public void setSituazioneAmb(String situazioneAmb) {
		this.situazioneAmb = situazioneAmb;
	}

	public String getSituazioneParentale() {
		return this.situazioneParentale;
	}

	public void setSituazioneParentale(String situazioneParentale) {
		this.situazioneParentale = situazioneParentale;
	}

	public String getSituazioneSanitaria() {
		return this.situazioneSanitaria;
	}

	public void setSituazioneSanitaria(String situazioneSanitaria) {
		this.situazioneSanitaria = situazioneSanitaria;
	}

	public Date getDataProssimaRelazioneAl() {
		return dataProssimaRelazioneAl;
	}

	public void setDataProssimaRelazioneAl(Date dataProssimaRelazioneAl) {
		this.dataProssimaRelazioneAl = dataProssimaRelazioneAl;
	}

	public Date getDataProssimaRelazioneDal() {
		return dataProssimaRelazioneDal;
	}

	public void setDataProssimaRelazioneDal(Date dataProssimaRelazioneDal) {
		this.dataProssimaRelazioneDal = dataProssimaRelazioneDal;
	}

	public String getFontiUtilizzate() {
		return fontiUtilizzate;
	}

	public void setFontiUtilizzate(String fontiUtilizzate) {
		this.fontiUtilizzate = fontiUtilizzate;
	}

	public Set<CsCTipoIntervento> getCsCTipoInterventos() {
		return csCTipoInterventos;
	}

	public void setCsCTipoInterventos(Set<CsCTipoIntervento> csCTipoInterventos) {
		this.csCTipoInterventos = csCTipoInterventos;
	}

	/**
	 * @return the microAttivita
	 */
	public CsTbMicroAttivita getMicroAttivita() {
		return microAttivita;
	}

	/**
	 * @param microAttivita the microAttivita to set
	 */
	public void setMicroAttivita(CsTbMicroAttivita microAttivita) {
		this.microAttivita = microAttivita;
	}

	/**
	 * @return the macroAttivita
	 */
	public CsTbMacroAttivita getMacroAttivita() {
		return macroAttivita;
	}

	/**
	 * @param macroAttivita the macroAttivita to set
	 */
	public void setMacroAttivita(CsTbMacroAttivita macroAttivita) {
		this.macroAttivita = macroAttivita;
	}

	/**
	 * @return the testo
	 */
	public String getTesto() {
		return testo;
	}

	/**
	 * @param testo the testo to set
	 */
	public void setTesto(String testo) {
		this.testo = testo;
	}

	public Boolean getFlagRilevazioneProblematiche() {
		return flagRilevazioneProblematiche;
	}

	public void setFlagRilevazioneProblematiche(Boolean flagRilevazioneProblematiche) {
		this.flagRilevazioneProblematiche = flagRilevazioneProblematiche;
	}

	/**
	 * @return the csRelRelazioneProbl
	 */
	public List<CsRelRelazioneProbl> getCsRelRelazioneProbl() {
		return csRelRelazioneProbl;
	}

	/**
	 * @param csRelRelazioneProbl the csRelRelazioneProbl to set
	 */
	public void setCsRelRelazioneProbl(List<CsRelRelazioneProbl> csRelRelazioneProbl) {
		this.csRelRelazioneProbl = csRelRelazioneProbl;
	}
	
	
	/**
	 * @return the conChiAltro
	 */
	public String getConChiAltro() {
		return conChiAltro;
	}

	/**
	 * @param conChiAltro the conChiAltro to set
	 */
	public void setConChiAltro(String conChiAltro) {
		this.conChiAltro = conChiAltro;
	}

	/**
	 * @return the csRelRelazioneTipoint
	 */
	public List<CsRelRelazioneTipoint> getCsRelRelazioneTipoint() {
		return csRelRelazioneTipoint;
	}

	/**
	 * @param csRelRelazioneTipoint the csRelRelazioneTipoint to set
	 */
	public void setCsRelRelazioneTipoint(
			List<CsRelRelazioneTipoint> csRelRelazioneTipoint) {
		this.csRelRelazioneTipoint = csRelRelazioneTipoint;
	}

	/**
	 * @return the numOperatori
	 */
	public Long getNumOperatori() {
		return numOperatori;
	}

	/**
	 * @param numOperatori the numOperatori to set
	 */
	public void setNumOperatori(Long numOperatori) {
		this.numOperatori = numOperatori;
	}

	/**
	 * @return the oreImpiegate
	 */
	public Date getOreImpiegate() {
		return oreImpiegate;
	}

	/**
	 * @param oreImpiegate the oreImpiegate to set
	 */
	public void setOreImpiegate(Date oreImpiegate) {
		this.oreImpiegate = oreImpiegate;
	}

	/**
	 * @return the csRelRelazioneProblReverseRif
	 */
	public List<CsRelRelazioneProbl> getCsRelRelazioneProblReverseRif() {
		return csRelRelazioneProblReverseRif;
	}

	/**
	 * @param csRelRelazioneProblReverseRif the csRelRelazioneProblReverseRif to set
	 */
	public void setCsRelRelazioneProblReverseRif(
			List<CsRelRelazioneProbl> csRelRelazioneProblReverseRif) {
		this.csRelRelazioneProblReverseRif = csRelRelazioneProblReverseRif;
	}

	public String getOrganizzazioneServizio() {
		return organizzazioneServizio;
	}

	public void setOrganizzazioneServizio(String organizzazioneServizio) {
		this.organizzazioneServizio = organizzazioneServizio;
	}

	public CsDTriage getCsDTriage() {
		return csDTriage;
	}

	public void setCsDTriage(CsDTriage csDTriage) {
		this.csDTriage = csDTriage;
	}

	public List<CsCDiarioConchi> getLstConChi() {
		return lstConChi;
	}

	public void setLstConChi(List<CsCDiarioConchi> lstConChi) {
		this.lstConChi = lstConChi;
	}

	public String getRichiestaIndagine() {
		return richiestaIndagine;
	}

	public void setRichiestaIndagine(String richiestaIndagine) {
		this.richiestaIndagine = richiestaIndagine;
	}

	public CsDRelSal getCsDRelSal() {
		return csDRelSal;
	}

	public void setCsDRelSal(CsDRelSal csDRelSal) {
		this.csDRelSal = csDRelSal;
	}

	public List<CsOSettore> getLstRiunioneConChi() {
		return lstRiunioneConChi;
	}

	public void setLstRiunioneConChi(List<CsOSettore> lstRiunioneConChi) {
		this.lstRiunioneConChi = lstRiunioneConChi;
	}
	
	
}
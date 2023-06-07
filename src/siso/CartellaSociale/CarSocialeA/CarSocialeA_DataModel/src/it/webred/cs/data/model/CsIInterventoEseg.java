package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CS_I_INTERVENTO_ESEG database table.
 * 
 */
@Entity
@Table(name="CS_I_INTERVENTO_ESEG")
@NamedQuery(name="CsIInterventoEseg.findAll", query="SELECT c FROM CsIInterventoEseg c")
public class CsIInterventoEseg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_INTERVENTO_ESEG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_INTERVENTO_ESEG_ID_GENERATOR")
	private Long id;

	@Column(name="COMPART_ALTRE")
	private BigDecimal compartAltre;

	@Column(name="COMPART_SSN")
	private BigDecimal compartSsn;

	@Column(name="COMPART_UTENTI")
	private BigDecimal compartUtenti;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ESECUZIONE")
	private Date dataEsecuzione;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ESECUZIONE_A")
	private Date dataEsecuzioneA;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_EVENTO")
	private Date dataEvento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="ENTE_OPERATORE_EROGANTE")
	private String enteOperatoreErogante;

	@Column(name="NOME_OPERATORE_EROG")
	private String nomeOperatoreErog;

	@Column(name="NOTE")
	private String note;

	@Column(name="NOTE_ALTRE_COMPART")
	private String noteAltreCompart;

	@Column(name="PERC_GESTITA_ENTE")
	private BigDecimal percGestitaEnte;

	@Column(name="SPESA")
	private BigDecimal spesa;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="VALORE_GESTITA_ENTE")
	private BigDecimal valoreGestitaEnte;

	//bi-directional many-to-one association to CsCfgIntEsegStato
	@ManyToOne
	@JoinColumn(name="STATO_ID")
	private CsCfgIntEsegStato stato;	
	 
	@ManyToOne 
	@JoinColumn(name="INTERVENTO_ESEG_MAST_ID", insertable=false, updatable=false)
	private CsIInterventoEsegMast csIInterventoEsegMast; 
	
	@Basic
	@Column(name="INTERVENTO_ESEG_MAST_ID")
	private Long masterId;

	//bi-directional many-to-one association to CsOOperatoreSettore
	@ManyToOne
	@JoinColumn(name="OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore csOOperatoreSettore;

	//bi-directional many-to-one association to csIInterventoEsegValores
	@OneToMany(mappedBy = "csIInterventoEseg", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CsIInterventoEsegValore> csIInterventoEsegValores;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="VAL_QUOTA_ID" )
	private CsIValQuota csIValQuota;

	public CsIInterventoEseg() {
		csIValQuota = new CsIValQuota();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Date getDataEsecuzione() {
		return this.dataEsecuzione;
	}

	public void setDataEsecuzione(Date dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}

	public Date getDtIns() {
		return this.dtIns!=null ? this.dtIns : new Date();
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getEnteOperatoreErogante() {
		return this.enteOperatoreErogante;
	}

	public void setEnteOperatoreErogante(String enteOperatoreErogante) {
		this.enteOperatoreErogante = enteOperatoreErogante;
	}

	public String getNomeOperatoreErog() {
		return this.nomeOperatoreErog;
	}

	public void setNomeOperatoreErog(String nomeOperatoreErog) {
		this.nomeOperatoreErog = nomeOperatoreErog;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
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
//				this.spesa = spesa;
				this.spesa = spesa.setScale(2, RoundingMode.CEILING);//SISO_806
		}else
			this.spesa = spesa;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
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

	public CsCfgIntEsegStato getStato() {
		return stato;
	}

	public void setStato(CsCfgIntEsegStato stato) {
		this.stato = stato;
	}

	public CsIInterventoEsegMast getCsIInterventoEsegMast() {
		return this.csIInterventoEsegMast;
	}

	public void setCsIInterventoEsegMast(CsIInterventoEsegMast csIInterventoEsegMast) {
		this.csIInterventoEsegMast = csIInterventoEsegMast;
		this.masterId = this.csIInterventoEsegMast!=null ? csIInterventoEsegMast.getId() : null;
	}

	public CsOOperatoreSettore getCsOOperatoreSettore() {
		return this.csOOperatoreSettore;
	}

	public void setCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		this.csOOperatoreSettore = csOOperatoreSettore;
	}

	public List<CsIInterventoEsegValore> getCsIInterventoEsegValores() {
		return csIInterventoEsegValores;
	}

	public void setCsIInterventoEsegValores(List<CsIInterventoEsegValore> csIInterventoEsegValores) {
		this.csIInterventoEsegValores = csIInterventoEsegValores;
	}

	public CsIValQuota getCsIValQuota() {
		return csIValQuota;
	}

	public void setCsIValQuota(CsIValQuota csIValQuota) {
		this.csIValQuota = csIValQuota;
	}

	public Date getDataEsecuzioneA() {
		return dataEsecuzioneA;
	}

	public void setDataEsecuzioneA(Date dataEsecuzioneA) {
		this.dataEsecuzioneA = dataEsecuzioneA;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}
}
package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * The persistent class for the CS_D_DIARIO database table.
 * 
 */
@Entity
@Table(name = "CS_D_DIARIO")
@NamedQuery(name = "CsDDiario.findAll", query = "SELECT c FROM CsDDiario c")
public class CsDDiario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CS_D_DIARIO_ID_GENERATOR", sequenceName = "SQ_ID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CS_D_DIARIO_ID_GENERATOR")
	private long id;

	// bi-directional many-to-one association to CsACaso
	@ManyToOne
	@JoinColumn(name = "CASO_ID")
	private CsACaso csACaso;

	// bi-directional one-to-many association to CsDSina
	@OneToMany(mappedBy = "csDDiario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CsDSina> csDSinas;

	// //bi-directional one-to-one association to CsDColloquio
	// @OneToOne(mappedBy="csDDiario")
	// private CsDColloquio csDColloquio;
	//
	// //bi-directional one-to-one association to CsDColloquio
	// @OneToOne(mappedBy="csDDiario", cascade={CascadeType.ALL})
	// private CsDPai csDPai;

	// //bi-directional one-to-one association to CsDNote
	// @OneToOne(mappedBy="csDDiario")
	// private CsDNote csDNote;
	//
	// //bi-directional one-to-one association to CsDRelazionePeriodica
	// @OneToOne(mappedBy="csDDiario")
	// private CsDRelazione csDRelazione;
	//
	// //bi-directional one-to-one association to CsDDocIndividuale
	// @OneToOne(mappedBy="csDDiario")
	// private CsDDocIndividuale csDDocIndividuale;

	// //bi-directional one-to-one association to CsDDocIndividuale
	// @OneToOne(mappedBy="csDDiario")
	// private CsDIsee csDIsee;
	//
	// //bi-directional one-to-one association to CsDScuola
	// @OneToOne(mappedBy="csDDiario")
	// private CsDScuola csDScuola;

	// //bi-directional one-to-one association to CsFlgIntervento
	// @OneToOne(mappedBy="csDDiario")
	// private CsFlgIntervento csFlgIntervento;

	@ManyToOne
	@JoinColumn(name = "TIPO_DIARIO_ID")
	private CsTbTipoDiario csTbTipoDiario;

	@ManyToOne(fetch = FetchType.EAGER)
	// Necessario caricamento contestuale
	@JoinColumn(name = "OPERATORE_SETTORE_ID")
	private CsOOperatoreSettore csOOperatoreSettore;

	// bi-directional many-to-one association to CsDClob
	@ManyToOne
	@JoinColumn(name = "CLOB_ID")
	private CsDClob csDClob;

	// //bi-directional one-to-one association to CsDValutazione
	// @OneToOne(mappedBy="csDDiario")
	// private CsDValutazione csDValutazione;

	// bi-directional many-to-many association to CsDDiario
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CS_REL_DIARIO_DIARIORIF", joinColumns = { @JoinColumn(name = "DIARIO_ID") }, inverseJoinColumns = { @JoinColumn(name = "DIARIO_ID_RIF") })
	private List<CsDDiario> csDDiariFiglio;

	// bi-directional many-to-many association to CsDDiario
	@ManyToMany(mappedBy = "csDDiariFiglio", fetch = FetchType.LAZY)
	private List<CsDDiario> csDDiariPadre;

	// bi-directional one-to-many association to CsDDiarioDoc
	@OneToMany(mappedBy = "csDDIario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<CsDDiarioDoc> csDDiarioDocs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_MOD")
	private Date dtMod;

	@Column(name = "USER_INS")
	private String userIns;

	@Column(name = "USR_MOD")
	private String usrMod;

	@Column(name = "RESPONSABILE_ID")
	private Long responsabileCaso;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DT_AMMINISTRATIVA")
	private Date dtAmministrativa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ATTIVAZIONE_DA")
	private Date dtAttivazioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_ATTIVAZIONE_A")
	private Date dtAttivazioneA;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_SOSPENSIONE_DA")
	private Date dtSospensioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_SOSPENSIONE_A")
	private Date dtSospensioneA;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CHIUSURA_DA")
	private Date dtChiusuraDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CHIUSURA_A")
	private Date dtChiusuraA;

	@Column(name = "SCHEDA_ID")
	private Long schedaId;

	//SISO-812
	@Column(name = "VIS_SECONDO_LIVELLO")
	private Long visSecondoLivello;
	
	public CsDDiario() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public CsTbTipoDiario getCsTbTipoDiario() {
		return this.csTbTipoDiario;
	}

	public void setCsTbTipoDiario(CsTbTipoDiario csTbTipoDiario) {
		this.csTbTipoDiario = csTbTipoDiario;
	}

	public CsACaso getCsACaso() {
		return this.csACaso;
	}

	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}

	public CsOOperatoreSettore getCsOOperatoreSettore() {
		return csOOperatoreSettore;
	}

	public void setCsOOperatoreSettore(CsOOperatoreSettore csOOperatoreSettore) {
		this.csOOperatoreSettore = csOOperatoreSettore;
	}

	public List<CsDDiario> getCsDDiariPadre() {
		return csDDiariPadre;
	}

	public void setCsDDiariPadre(List<CsDDiario> csDDiariPadre) {
		this.csDDiariPadre = csDDiariPadre;
	}

	public List<CsDDiario> getCsDDiariFiglio() {
		return csDDiariFiglio;
	}

	public void setCsDDiariFiglio(List<CsDDiario> csDDiariFiglio) {
		this.csDDiariFiglio = csDDiariFiglio;
	}

	public void addCsDDiariFiglio(CsDDiario csDDiario) {
		if (this.csDDiariFiglio == null)
			this.csDDiariFiglio = new ArrayList<CsDDiario>();

		this.csDDiariFiglio.add(csDDiario);
	}

	public CsDClob getCsDClob() {
		return this.csDClob;
	}

	public void setCsDClob(CsDClob csDClob) {
		this.csDClob = csDClob;
	}

	public Set<CsDDiarioDoc> getCsDDiarioDocs() {
		return csDDiarioDocs;
	}

	public void setCsDDiarioDocs(Set<CsDDiarioDoc> csDDiarioDocs) {
		this.csDDiarioDocs = csDDiarioDocs;
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

	public Long getResponsabileCaso() {
		return responsabileCaso;
	}

	public void setResponsabileCaso(Long responsabileCaso) {
		this.responsabileCaso = responsabileCaso;
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

	public Date getDtAmministrativa() {
		return dtAmministrativa;
	}

	public void setDtAmministrativa(Date dtAmministrativa) {
		this.dtAmministrativa = dtAmministrativa;
	}

	public Date getDtAttivazioneDa() {
		return dtAttivazioneDa;
	}

	public void setDtAttivazioneDa(Date dtAttivazioneDa) {
		this.dtAttivazioneDa = dtAttivazioneDa;
	}

	public Date getDtAttivazioneA() {
		return dtAttivazioneA;
	}

	public void setDtAttivazioneA(Date dtAttivazioneA) {
		this.dtAttivazioneA = dtAttivazioneA;
	}

	public Date getDtSospensioneDa() {
		return dtSospensioneDa;
	}

	public Date getDtSospensioneA() {
		return dtSospensioneA;
	}

	public Date getDtChiusuraDa() {
		return dtChiusuraDa;
	}

	public Date getDtChiusuraA() {
		return dtChiusuraA;
	}

	public void setDtSospensioneDa(Date dtSospensioneDa) {
		this.dtSospensioneDa = dtSospensioneDa;
	}

	public void setDtSospensioneA(Date dtSospensioneA) {
		this.dtSospensioneA = dtSospensioneA;
	}

	public void setDtChiusuraDa(Date dtChiusuraDa) {
		this.dtChiusuraDa = dtChiusuraDa;
	}

	public void setDtChiusuraA(Date dtChiusuraA) {
		this.dtChiusuraA = dtChiusuraA;
	}

	public Long getSchedaId() {
		return schedaId;
	}

	public void setSchedaId(Long schedaId) {
		this.schedaId = schedaId;
	}

	public List<CsDSina> getCsDSinas() {
		return csDSinas;
	}

	public void setCsDSinas(List<CsDSina> csDSinas) {
		this.csDSinas = csDSinas;
	}

	public Long getVisSecondoLivello() {
		return visSecondoLivello;
	}

	public void setVisSecondoLivello(Long visSecondoLivello) {
		this.visSecondoLivello = visSecondoLivello;
	}

	

}
package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_RESI_MINORE database table.
 * 
 */
@Entity
@Table(name="CS_I_RESI_MINORE")
@NamedQuery(name="CsIResiMinore.findAll", query="SELECT c FROM CsIResiMinore c")
public class CsIResiMinore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@SequenceGenerator(name="CS_I_RESI_MINORE_INTERVENTOID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_RESI_MINORE_INTERVENTOID_GENERATOR")
	//@Column(name="INTERVENTO_ID")
	private long interventoId;

	@Column(name="COMP_CITTA")
	private String compCitta;

	@Column(name="COMP_DENOMINAZIONE")
	private String compDenominazione;

	@Column(name="COMP_INDIRIZZO")
	private String compIndirizzo;

	@Column(name="COMP_TEL")
	private String compTel;

	@Column(name="CONTRIBUTO_MENSILE_FAMI_ORI")
	private BigDecimal contributoMensileFamiOri;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PROV_AG")
	private Date dtProvAg;

	@Column(name="FLAG_ALTRO")
	private BigDecimal flagAltro;

	@Column(name="FLAG_INCONTRI_PROTETTI")
	private BigDecimal flagIncontriProtetti;

	@Column(name="FLAG_INTERVENTO_EDU")
	private BigDecimal flagInterventoEdu;

	@Column(name="FLAG_RIMBORSO_TESTI")
	private BigDecimal flagRimborsoTesti;

	@Column(name="FLAG_SENZA_PROVVEDIMENTO")
	private BigDecimal flagSenzaProvvedimento;

	@Column(name="FLAG_SPESE_VACANZE")
	private BigDecimal flagSpeseVacanze;

	@Column(name="FLAG_SPESESANI")
	private BigDecimal flagSpesesani;

	private BigDecimal importo;

	@Column(name="N_PROVV_AG")
	private String nProvvAg;

	@Column(name="VALORE_RETTA")
	private BigDecimal valoreRetta;

	//bi-directional many-to-one association to CsAComponente
	@ManyToOne
	@JoinColumn(name="COMPONENTE_ID")
	private CsAComponente csAComponente;

	//bi-directional many-to-one association to CsCComunita
	@ManyToOne
	@JoinColumn(name="COMUNITA_SETTORE_ID")
	private CsCComunita csCComunita;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	//bi-directional many-to-one association to CsTbTipoRetta
	@ManyToOne
	@JoinColumn(name="TIPO_RETTA_ID")
	private CsTbTipoRetta csTbTipoRetta;

	//bi-directional many-to-one association to CsTbTipoRientriFami
	@ManyToOne
	@JoinColumn(name="TIPO_RIENTRI_FAMI_ID")
	private CsTbTipoRientriFami csTbTipoRientriFami;

	public CsIResiMinore() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public String getCompCitta() {
		return this.compCitta;
	}

	public void setCompCitta(String compCitta) {
		this.compCitta = compCitta;
	}

	public String getCompDenominazione() {
		return this.compDenominazione;
	}

	public void setCompDenominazione(String compDenominazione) {
		this.compDenominazione = compDenominazione;
	}

	public String getCompIndirizzo() {
		return this.compIndirizzo;
	}

	public void setCompIndirizzo(String compIndirizzo) {
		this.compIndirizzo = compIndirizzo;
	}

	public String getCompTel() {
		return this.compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public BigDecimal getContributoMensileFamiOri() {
		return this.contributoMensileFamiOri;
	}

	public void setContributoMensileFamiOri(BigDecimal contributoMensileFamiOri) {
		this.contributoMensileFamiOri = contributoMensileFamiOri;
	}

	public Date getDtProvAg() {
		return this.dtProvAg;
	}

	public void setDtProvAg(Date dtProvAg) {
		this.dtProvAg = dtProvAg;
	}

	public BigDecimal getFlagAltro() {
		return this.flagAltro;
	}

	public void setFlagAltro(BigDecimal flagAltro) {
		this.flagAltro = flagAltro;
	}

	public BigDecimal getFlagIncontriProtetti() {
		return this.flagIncontriProtetti;
	}

	public void setFlagIncontriProtetti(BigDecimal flagIncontriProtetti) {
		this.flagIncontriProtetti = flagIncontriProtetti;
	}

	public BigDecimal getFlagInterventoEdu() {
		return this.flagInterventoEdu;
	}

	public void setFlagInterventoEdu(BigDecimal flagInterventoEdu) {
		this.flagInterventoEdu = flagInterventoEdu;
	}

	public BigDecimal getFlagRimborsoTesti() {
		return this.flagRimborsoTesti;
	}

	public void setFlagRimborsoTesti(BigDecimal flagRimborsoTesti) {
		this.flagRimborsoTesti = flagRimborsoTesti;
	}

	public BigDecimal getFlagSenzaProvvedimento() {
		return this.flagSenzaProvvedimento;
	}

	public void setFlagSenzaProvvedimento(BigDecimal flagSenzaProvvedimento) {
		this.flagSenzaProvvedimento = flagSenzaProvvedimento;
	}

	public BigDecimal getFlagSpeseVacanze() {
		return this.flagSpeseVacanze;
	}

	public void setFlagSpeseVacanze(BigDecimal flagSpeseVacanze) {
		this.flagSpeseVacanze = flagSpeseVacanze;
	}

	public BigDecimal getFlagSpesesani() {
		return this.flagSpesesani;
	}

	public void setFlagSpesesani(BigDecimal flagSpesesani) {
		this.flagSpesesani = flagSpesesani;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getNProvvAg() {
		return this.nProvvAg;
	}

	public void setNProvvAg(String nProvvAg) {
		this.nProvvAg = nProvvAg;
	}

	public BigDecimal getValoreRetta() {
		return this.valoreRetta;
	}

	public void setValoreRetta(BigDecimal valoreRetta) {
		this.valoreRetta = valoreRetta;
	}

	public CsAComponente getCsAComponente() {
		return this.csAComponente;
	}

	public void setCsAComponente(CsAComponente csAComponente) {
		this.csAComponente = csAComponente;
	}

	public CsCComunita getCsCComunita() {
		return this.csCComunita;
	}

	public void setCsCComunita(CsCComunita csCComunita) {
		this.csCComunita = csCComunita;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsTbTipoRetta getCsTbTipoRetta() {
		return this.csTbTipoRetta;
	}

	public void setCsTbTipoRetta(CsTbTipoRetta csTbTipoRetta) {
		this.csTbTipoRetta = csTbTipoRetta;
	}

	public CsTbTipoRientriFami getCsTbTipoRientriFami() {
		return this.csTbTipoRientriFami;
	}

	public void setCsTbTipoRientriFami(CsTbTipoRientriFami csTbTipoRientriFami) {
		this.csTbTipoRientriFami = csTbTipoRientriFami;
	}

}
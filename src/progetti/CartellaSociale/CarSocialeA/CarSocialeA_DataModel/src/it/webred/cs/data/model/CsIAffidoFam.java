package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_AFFIDO_FAM database table.
 * 
 */
@Entity
@Table(name="CS_I_AFFIDO_FAM")
@NamedQuery(name="CsIAffidoFam.findAll", query="SELECT c FROM CsIAffidoFam c")
public class CsIAffidoFam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	@Column(name="ACCREDITO_A")
	private String accreditoA;

	@Column(name="AFFIDO_NUM_GIORNI_SETT")
	private BigDecimal affidoNumGiorniSett;

	@Column(name="ALTRO_DESC")
	private String altroDesc;

	private BigDecimal anno;

	@Column(name="CONTRIBUTO_FAMI_ORI")
	private BigDecimal contributoFamiOri;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PROV_AG")
	private Date dtProvAg;

	@Column(name="FAM_CELLULARE")
	private String famCellulare;

	@Column(name="FAM_CF")
	private String famCf;

	@Column(name="FAM_CITTA")
	private String famCitta;

	@Column(name="FAM_INDIRIZZO")
	private String famIndirizzo;

	@Column(name="FAM_TEL")
	private String famTel;

	@Column(name="FAMIGLIA_AFFIDATARIA")
	private String famigliaAffidataria;

	@Column(name="FLAG_A_DIURNO_GIORNALIERO")
	private BigDecimal flagADiurnoGiornaliero;

	@Column(name="FLAG_A_DIURNO_MESE")
	private BigDecimal flagADiurnoMese;

	@Column(name="FLAG_A_ETERO_FAM")
	private BigDecimal flagAEteroFam;

	@Column(name="FLAG_A_PARENTI")
	private BigDecimal flagAParenti;

	@Column(name="FLAG_ALTRO")
	private BigDecimal flagAltro;

	@Column(name="FLAG_CONSENSUALE")
	private BigDecimal flagConsensuale;

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

	private String iban;

	private BigDecimal importo;

	private String intestazione;

	@Column(name="N_PROVV_AG")
	private String nProvvAg;

	@Column(name="QUOTA_PARTICOLARE")
	private BigDecimal quotaParticolare;

	@Column(name="TIPO_RISCOSSIONE")
	private String tipoRiscossione;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsIAffidoFam() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public String getAccreditoA() {
		return this.accreditoA;
	}

	public void setAccreditoA(String accreditoA) {
		this.accreditoA = accreditoA;
	}

	public BigDecimal getAffidoNumGiorniSett() {
		return this.affidoNumGiorniSett;
	}

	public void setAffidoNumGiorniSett(BigDecimal affidoNumGiorniSett) {
		this.affidoNumGiorniSett = affidoNumGiorniSett;
	}

	public String getAltroDesc() {
		return this.altroDesc;
	}

	public void setAltroDesc(String altroDesc) {
		this.altroDesc = altroDesc;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public BigDecimal getContributoFamiOri() {
		return this.contributoFamiOri;
	}

	public void setContributoFamiOri(BigDecimal contributoFamiOri) {
		this.contributoFamiOri = contributoFamiOri;
	}

	public Date getDtProvAg() {
		return this.dtProvAg;
	}

	public void setDtProvAg(Date dtProvAg) {
		this.dtProvAg = dtProvAg;
	}

	public String getFamCellulare() {
		return this.famCellulare;
	}

	public void setFamCellulare(String famCellulare) {
		this.famCellulare = famCellulare;
	}

	public String getFamCf() {
		return this.famCf;
	}

	public void setFamCf(String famCf) {
		this.famCf = famCf;
	}

	public String getFamCitta() {
		return this.famCitta;
	}

	public void setFamCitta(String famCitta) {
		this.famCitta = famCitta;
	}

	public String getFamIndirizzo() {
		return this.famIndirizzo;
	}

	public void setFamIndirizzo(String famIndirizzo) {
		this.famIndirizzo = famIndirizzo;
	}

	public String getFamTel() {
		return this.famTel;
	}

	public void setFamTel(String famTel) {
		this.famTel = famTel;
	}

	public String getFamigliaAffidataria() {
		return this.famigliaAffidataria;
	}

	public void setFamigliaAffidataria(String famigliaAffidataria) {
		this.famigliaAffidataria = famigliaAffidataria;
	}

	public BigDecimal getFlagADiurnoGiornaliero() {
		return this.flagADiurnoGiornaliero;
	}

	public void setFlagADiurnoGiornaliero(BigDecimal flagADiurnoGiornaliero) {
		this.flagADiurnoGiornaliero = flagADiurnoGiornaliero;
	}

	public BigDecimal getFlagADiurnoMese() {
		return this.flagADiurnoMese;
	}

	public void setFlagADiurnoMese(BigDecimal flagADiurnoMese) {
		this.flagADiurnoMese = flagADiurnoMese;
	}

	public BigDecimal getFlagAEteroFam() {
		return this.flagAEteroFam;
	}

	public void setFlagAEteroFam(BigDecimal flagAEteroFam) {
		this.flagAEteroFam = flagAEteroFam;
	}

	public BigDecimal getFlagAParenti() {
		return this.flagAParenti;
	}

	public void setFlagAParenti(BigDecimal flagAParenti) {
		this.flagAParenti = flagAParenti;
	}

	public BigDecimal getFlagAltro() {
		return this.flagAltro;
	}

	public void setFlagAltro(BigDecimal flagAltro) {
		this.flagAltro = flagAltro;
	}

	public BigDecimal getFlagConsensuale() {
		return this.flagConsensuale;
	}

	public void setFlagConsensuale(BigDecimal flagConsensuale) {
		this.flagConsensuale = flagConsensuale;
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

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getIntestazione() {
		return this.intestazione;
	}

	public void setIntestazione(String intestazione) {
		this.intestazione = intestazione;
	}

	public String getNProvvAg() {
		return this.nProvvAg;
	}

	public void setNProvvAg(String nProvvAg) {
		this.nProvvAg = nProvvAg;
	}

	public BigDecimal getQuotaParticolare() {
		return this.quotaParticolare;
	}

	public void setQuotaParticolare(BigDecimal quotaParticolare) {
		this.quotaParticolare = quotaParticolare;
	}

	public String getTipoRiscossione() {
		return this.tipoRiscossione;
	}

	public void setTipoRiscossione(String tipoRiscossione) {
		this.tipoRiscossione = tipoRiscossione;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

}
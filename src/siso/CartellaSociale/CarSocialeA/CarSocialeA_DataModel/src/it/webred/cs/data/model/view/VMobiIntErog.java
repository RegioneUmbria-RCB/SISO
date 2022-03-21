package it.webred.cs.data.model.view;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_MOBI_INT_EROG database table.
 * 
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="V_MOBI_INT_EROG")
@NamedQuery(name="VMobiIntErog.findAll", query="SELECT v FROM VMobiIntErog v")
public class VMobiIntErog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ER_ID")
	private BigDecimal erId;

	@Column(name="COMP_CONVIVENTE")
	private String compConvivente;

	@Column(name="COMP_DISPONIBILITA")
	private String compDisponibilita;

	@Column(name="ER_CS_CODICE_MEMO")
	private String erCsCodiceMemo;

	@Column(name="ER_CS_DESCRIZIONE")
	private String erCsDescrizione;

	@Column(name="ER_CS_ID")
	private BigDecimal erCsId;

	@Column(name="ER_DESC_INTERVENTO_ESEG")
	private String erDescInterventoEseg;

	@Column(name="ER_EROGAZIONE_POSSIBILE")
	private BigDecimal erErogazionePossibile;

	@Column(name="ER_OPE_COG_INS")
	private String erOpeCogIns;

	@Column(name="ER_OPE_NOME_INS")
	private String erOpeNomeIns;

	@Column(name="ER_ORGANIZZAZIONE_ER_BELFIORE")
	private String erOrganizzazioneErBelfiore;

	@Column(name="ER_ORGANIZZAZIONE_ER_ID")
	private BigDecimal erOrganizzazioneErId;

	@Column(name="ER_ORGANIZZAZIONE_ER_NOME")
	private String erOrganizzazioneErNome;

	@Column(name="ER_ORGANIZZAZIONE_TIT_BELFIORE")
	private String erOrganizzazioneTitBelfiore;

	@Column(name="ER_ORGANIZZAZIONE_TIT_ID")
	private BigDecimal erOrganizzazioneTitId;

	@Column(name="ER_ORGANIZZAZIONE_TIT_NOME")
	private String erOrganizzazioneTitNome;

	@Column(name="ER_PRIMO_STATO")
	private String erPrimoStato;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="ER_PRIMO_STATO_DT")
	private Date erPrimoStatoDt;

	@Column(name="ER_PRIMO_STATO_ER_POSSIBILE")
	private String erPrimoStatoErPossibile;

	@Column(name="ER_PRIMO_STATO_TIPO")
	private String erPrimoStatoTipo;

	@Column(name="ER_SETTORE_EROG")
	private String erSettoreErog;

	@Column(name="ER_SETTORE_EROG_ID")
	private BigDecimal erSettoreErogId;

	@Column(name="ER_SETTORE_TIT")
	private String erSettoreTit;

	@Column(name="ER_SETTORE_TIT_ID")
	private BigDecimal erSettoreTitId;

	@Column(name="ER_SOGG_CF")
	private String erSoggCf;

	@Column(name="ER_SOGG_COGNOME")
	private String erSoggCognome;

	@Column(name="ER_SOGG_NOME")
	private String erSoggNome;
	
	@Column(name="ER_SOGG_CASO_ID")
	private BigDecimal erSoggCasoId;

	@Column(name="ER_TIPO_BENEFICIARIO")
	private String erTipoBeneficiario;

	@Column(name="ER_ULTIMO_STATO")
	private String erUltimoStato;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="ER_ULTIMO_STATO_DT")
	private Date erUltimoStatoDt;

	@Column(name="ER_ULTIMO_STATO_ER_POSSIBILE")
	private String erUltimoStatoErPossibile;

	@Column(name="ER_ULTIMO_STATO_TIPO")
	private String erUltimoStatoTipo;

	@Column(name="ER_UNITA_MISURA")
	private String erUnitaMisura;

	@Column(name="FLG_CASO_ID")
	private BigDecimal flgCasoId;

	@Column(name="FLG_CASO_IDENTIFICATIVO")
	private BigDecimal flgCasoIdentificativo;

	@Column(name="FLG_DESCR_SOSPENSIONE")
	private String flgDescrSospensione;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_AMMINISTRATIVA")
	private Date flgDtAmministrativa;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_ATTIVAZIONE_A")
	private Date flgDtAttivazioneA;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_ATTIVAZIONE_DA")
	private Date flgDtAttivazioneDa;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_CHIUSURA_A")
	private Date flgDtChiusuraA;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_CHIUSURA_DA")
	private Date flgDtChiusuraDa;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_INS")
	private Date flgDtIns;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_MOD")
	private Date flgDtMod;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_SOSPENSIONE_A")
	private Date flgDtSospensioneA;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_SOSPENSIONE_DA")
	private Date flgDtSospensioneDa;

	@Column(name="FLG_FLAG_ATT_SOSP_C")
	private String flgFlagAttSospC;

	@Column(name="FLG_RESPINTO")
	private BigDecimal flgRespinto;

	@Column(name="FLG_TIPO_ATTIVAZIONE")
	private String flgTipoAttivazione;

	@Column(name="I_BELFIORE_ORG")
	private String iBelfioreOrg;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="I_FINE_AL")
	private Date iFineAl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="I_FINE_DAL")
	private Date iFineDal;

	@Column(name="I_FLAG_PRIMAER_RINNOVO")
	private String iFlagPrimaerRinnovo;

	@Column(name="I_ID")
	private BigDecimal iId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="I_INIZIO_AL")
	private Date iInizioAl;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(name="I_INIZIO_DAL")
	private Date iInizioDal;

	@Column(name="I_NOME_ORG")
	private String iNomeOrg;

	@Column(name="I_SETTORE")
	private String iSettore;

	@Column(name="I_SETTORE_ID")
	private BigDecimal iSettoreId;

	@Column(name="QR_FLAG_PERIODO_RIPARTIZ")
	private String qrFlagPeriodoRipartiz;

	@Column(name="QR_FREQUENZA_RENDICONTO")
	private String qrFrequenzaRendiconto;

	@Column(name="QR_FREQUENZA_SERVIZIO")
	private String qrFrequenzaServizio;

	@Column(name="QR_N_GG_SETTIMANALI")
	private BigDecimal qrNGgSettimanali;

	@Column(name="QR_N_TOT_ACCESSI_SERVIZIO")
	private BigDecimal qrNTotAccessiServizio;

	@Column(name="SOGGRIF_DENOM")
	private String soggrifDenom;

	@Column(name="SOGGRIF_TELCELL")
	private String soggrifTelcell;

	@Column(name="TI_COD_ISTAT")
	private String tiCodIstat;

	@Column(name="TI_DESC_ISTAT")
	private String tiDescIstat;

	@Column(name="TI_ID")
	private BigDecimal tiId;

	@Column(name="TI_SUB_COD_ISTAT")
	private String tiSubCodIstat;

	@Column(name="TIC_CODICE_MEMO")
	private String ticCodiceMemo;

	@Column(name="TIC_DESCRIZIONE")
	private String ticDescrizione;

	@Column(name="TIC_ID")
	private BigDecimal ticId;

	@Column(name="Z_ZONA_SOCIALE")
	private String zZonaSociale;

	public VMobiIntErog() {
	}

	public String getCompConvivente() {
		return this.compConvivente;
	}

	public void setCompConvivente(String compConvivente) {
		this.compConvivente = compConvivente;
	}

	public String getCompDisponibilita() {
		return this.compDisponibilita;
	}

	public void setCompDisponibilita(String compDisponibilita) {
		this.compDisponibilita = compDisponibilita;
	}

	public String getErCsCodiceMemo() {
		return this.erCsCodiceMemo;
	}

	public void setErCsCodiceMemo(String erCsCodiceMemo) {
		this.erCsCodiceMemo = erCsCodiceMemo;
	}

	public String getErCsDescrizione() {
		return this.erCsDescrizione;
	}

	public void setErCsDescrizione(String erCsDescrizione) {
		this.erCsDescrizione = erCsDescrizione;
	}

	public BigDecimal getErCsId() {
		return this.erCsId;
	}

	public void setErCsId(BigDecimal erCsId) {
		this.erCsId = erCsId;
	}

	public String getErDescInterventoEseg() {
		return this.erDescInterventoEseg;
	}

	public void setErDescInterventoEseg(String erDescInterventoEseg) {
		this.erDescInterventoEseg = erDescInterventoEseg;
	}

	public BigDecimal getErErogazionePossibile() {
		return this.erErogazionePossibile;
	}

	public void setErErogazionePossibile(BigDecimal erErogazionePossibile) {
		this.erErogazionePossibile = erErogazionePossibile;
	}

	public BigDecimal getErId() {
		return this.erId;
	}

	public void setErId(BigDecimal erId) {
		this.erId = erId;
	}

	public String getErOpeCogIns() {
		return this.erOpeCogIns;
	}

	public void setErOpeCogIns(String erOpeCogIns) {
		this.erOpeCogIns = erOpeCogIns;
	}

	public String getErOpeNomeIns() {
		return this.erOpeNomeIns;
	}

	public void setErOpeNomeIns(String erOpeNomeIns) {
		this.erOpeNomeIns = erOpeNomeIns;
	}

	public String getErOrganizzazioneErBelfiore() {
		return this.erOrganizzazioneErBelfiore;
	}

	public void setErOrganizzazioneErBelfiore(String erOrganizzazioneErBelfiore) {
		this.erOrganizzazioneErBelfiore = erOrganizzazioneErBelfiore;
	}

	public BigDecimal getErOrganizzazioneErId() {
		return this.erOrganizzazioneErId;
	}

	public void setErOrganizzazioneErId(BigDecimal erOrganizzazioneErId) {
		this.erOrganizzazioneErId = erOrganizzazioneErId;
	}

	public String getErOrganizzazioneErNome() {
		return this.erOrganizzazioneErNome;
	}

	public void setErOrganizzazioneErNome(String erOrganizzazioneErNome) {
		this.erOrganizzazioneErNome = erOrganizzazioneErNome;
	}

	public String getErOrganizzazioneTitBelfiore() {
		return this.erOrganizzazioneTitBelfiore;
	}

	public void setErOrganizzazioneTitBelfiore(String erOrganizzazioneTitBelfiore) {
		this.erOrganizzazioneTitBelfiore = erOrganizzazioneTitBelfiore;
	}

	public BigDecimal getErOrganizzazioneTitId() {
		return this.erOrganizzazioneTitId;
	}

	public void setErOrganizzazioneTitId(BigDecimal erOrganizzazioneTitId) {
		this.erOrganizzazioneTitId = erOrganizzazioneTitId;
	}

	public String getErOrganizzazioneTitNome() {
		return this.erOrganizzazioneTitNome;
	}

	public void setErOrganizzazioneTitNome(String erOrganizzazioneTitNome) {
		this.erOrganizzazioneTitNome = erOrganizzazioneTitNome;
	}

	public String getErPrimoStato() {
		return this.erPrimoStato;
	}

	public void setErPrimoStato(String erPrimoStato) {
		this.erPrimoStato = erPrimoStato;
	}

	public Date getErPrimoStatoDt() {
		return this.erPrimoStatoDt;
	}

	public void setErPrimoStatoDt(Date erPrimoStatoDt) {
		this.erPrimoStatoDt = erPrimoStatoDt;
	}

	public String getErPrimoStatoErPossibile() {
		return this.erPrimoStatoErPossibile;
	}

	public void setErPrimoStatoErPossibile(String erPrimoStatoErPossibile) {
		this.erPrimoStatoErPossibile = erPrimoStatoErPossibile;
	}

	public String getErPrimoStatoTipo() {
		return this.erPrimoStatoTipo;
	}

	public void setErPrimoStatoTipo(String erPrimoStatoTipo) {
		this.erPrimoStatoTipo = erPrimoStatoTipo;
	}

	public String getErSettoreErog() {
		return this.erSettoreErog;
	}

	public void setErSettoreErog(String erSettoreErog) {
		this.erSettoreErog = erSettoreErog;
	}

	public BigDecimal getErSettoreErogId() {
		return this.erSettoreErogId;
	}

	public void setErSettoreErogId(BigDecimal erSettoreErogId) {
		this.erSettoreErogId = erSettoreErogId;
	}

	public String getErSettoreTit() {
		return this.erSettoreTit;
	}

	public void setErSettoreTit(String erSettoreTit) {
		this.erSettoreTit = erSettoreTit;
	}

	public BigDecimal getErSettoreTitId() {
		return this.erSettoreTitId;
	}

	public void setErSettoreTitId(BigDecimal erSettoreTitId) {
		this.erSettoreTitId = erSettoreTitId;
	}

	public String getErSoggCf() {
		return this.erSoggCf;
	}

	public void setErSoggCf(String erSoggCf) {
		this.erSoggCf = erSoggCf;
	}

	public String getErSoggCognome() {
		return this.erSoggCognome;
	}

	public void setErSoggCognome(String erSoggCognome) {
		this.erSoggCognome = erSoggCognome;
	}

	public String getErSoggNome() {
		return this.erSoggNome;
	}

	public void setErSoggNome(String erSoggNome) {
		this.erSoggNome = erSoggNome;
	}

	public String getErTipoBeneficiario() {
		return this.erTipoBeneficiario;
	}

	public void setErTipoBeneficiario(String erTipoBeneficiario) {
		this.erTipoBeneficiario = erTipoBeneficiario;
	}

	public String getErUltimoStato() {
		return this.erUltimoStato;
	}

	public void setErUltimoStato(String erUltimoStato) {
		this.erUltimoStato = erUltimoStato;
	}

	public Date getErUltimoStatoDt() {
		return this.erUltimoStatoDt;
	}

	public void setErUltimoStatoDt(Date erUltimoStatoDt) {
		this.erUltimoStatoDt = erUltimoStatoDt;
	}

	public String getErUltimoStatoErPossibile() {
		return this.erUltimoStatoErPossibile;
	}

	public void setErUltimoStatoErPossibile(String erUltimoStatoErPossibile) {
		this.erUltimoStatoErPossibile = erUltimoStatoErPossibile;
	}

	public String getErUltimoStatoTipo() {
		return this.erUltimoStatoTipo;
	}

	public void setErUltimoStatoTipo(String erUltimoStatoTipo) {
		this.erUltimoStatoTipo = erUltimoStatoTipo;
	}

	public String getErUnitaMisura() {
		return this.erUnitaMisura;
	}

	public void setErUnitaMisura(String erUnitaMisura) {
		this.erUnitaMisura = erUnitaMisura;
	}

	public BigDecimal getFlgCasoId() {
		return this.flgCasoId;
	}

	public void setFlgCasoId(BigDecimal flgCasoId) {
		this.flgCasoId = flgCasoId;
	}

	public BigDecimal getFlgCasoIdentificativo() {
		return this.flgCasoIdentificativo;
	}

	public void setFlgCasoIdentificativo(BigDecimal flgCasoIdentificativo) {
		this.flgCasoIdentificativo = flgCasoIdentificativo;
	}

	public String getFlgDescrSospensione() {
		return this.flgDescrSospensione;
	}

	public void setFlgDescrSospensione(String flgDescrSospensione) {
		this.flgDescrSospensione = flgDescrSospensione;
	}

	public Date getFlgDtAmministrativa() {
		return this.flgDtAmministrativa;
	}

	public void setFlgDtAmministrativa(Date flgDtAmministrativa) {
		this.flgDtAmministrativa = flgDtAmministrativa;
	}

	public Date getFlgDtAttivazioneA() {
		return this.flgDtAttivazioneA;
	}

	public void setFlgDtAttivazioneA(Date flgDtAttivazioneA) {
		this.flgDtAttivazioneA = flgDtAttivazioneA;
	}

	public Date getFlgDtAttivazioneDa() {
		return this.flgDtAttivazioneDa;
	}

	public void setFlgDtAttivazioneDa(Date flgDtAttivazioneDa) {
		this.flgDtAttivazioneDa = flgDtAttivazioneDa;
	}

	public Date getFlgDtChiusuraA() {
		return this.flgDtChiusuraA;
	}

	public void setFlgDtChiusuraA(Date flgDtChiusuraA) {
		this.flgDtChiusuraA = flgDtChiusuraA;
	}

	public Date getFlgDtChiusuraDa() {
		return this.flgDtChiusuraDa;
	}

	public void setFlgDtChiusuraDa(Date flgDtChiusuraDa) {
		this.flgDtChiusuraDa = flgDtChiusuraDa;
	}

	public Date getFlgDtIns() {
		return this.flgDtIns;
	}

	public void setFlgDtIns(Date flgDtIns) {
		this.flgDtIns = flgDtIns;
	}

	public Date getFlgDtMod() {
		return this.flgDtMod;
	}

	public void setFlgDtMod(Date flgDtMod) {
		this.flgDtMod = flgDtMod;
	}

	public Date getFlgDtSospensioneA() {
		return this.flgDtSospensioneA;
	}

	public void setFlgDtSospensioneA(Date flgDtSospensioneA) {
		this.flgDtSospensioneA = flgDtSospensioneA;
	}

	public Date getFlgDtSospensioneDa() {
		return this.flgDtSospensioneDa;
	}

	public void setFlgDtSospensioneDa(Date flgDtSospensioneDa) {
		this.flgDtSospensioneDa = flgDtSospensioneDa;
	}

	public String getFlgFlagAttSospC() {
		return this.flgFlagAttSospC;
	}

	public void setFlgFlagAttSospC(String flgFlagAttSospC) {
		this.flgFlagAttSospC = flgFlagAttSospC;
	}

	public BigDecimal getFlgRespinto() {
		return this.flgRespinto;
	}

	public void setFlgRespinto(BigDecimal flgRespinto) {
		this.flgRespinto = flgRespinto;
	}

	public String getFlgTipoAttivazione() {
		return this.flgTipoAttivazione;
	}

	public void setFlgTipoAttivazione(String flgTipoAttivazione) {
		this.flgTipoAttivazione = flgTipoAttivazione;
	}

	public String getIBelfioreOrg() {
		return this.iBelfioreOrg;
	}

	public void setIBelfioreOrg(String iBelfioreOrg) {
		this.iBelfioreOrg = iBelfioreOrg;
	}

	public Date getIFineAl() {
		return this.iFineAl;
	}

	public void setIFineAl(Date iFineAl) {
		this.iFineAl = iFineAl;
	}

	public Date getIFineDal() {
		return this.iFineDal;
	}

	public void setIFineDal(Date iFineDal) {
		this.iFineDal = iFineDal;
	}

	public String getIFlagPrimaerRinnovo() {
		return this.iFlagPrimaerRinnovo;
	}

	public void setIFlagPrimaerRinnovo(String iFlagPrimaerRinnovo) {
		this.iFlagPrimaerRinnovo = iFlagPrimaerRinnovo;
	}

	public BigDecimal getIId() {
		return this.iId;
	}

	public void setIId(BigDecimal iId) {
		this.iId = iId;
	}

	public Date getIInizioAl() {
		return this.iInizioAl;
	}

	public void setIInizioAl(Date iInizioAl) {
		this.iInizioAl = iInizioAl;
	}

	public Date getIInizioDal() {
		return this.iInizioDal;
	}

	public void setIInizioDal(Date iInizioDal) {
		this.iInizioDal = iInizioDal;
	}

	public String getINomeOrg() {
		return this.iNomeOrg;
	}

	public void setINomeOrg(String iNomeOrg) {
		this.iNomeOrg = iNomeOrg;
	}

	public String getISettore() {
		return this.iSettore;
	}

	public void setISettore(String iSettore) {
		this.iSettore = iSettore;
	}

	public BigDecimal getISettoreId() {
		return this.iSettoreId;
	}

	public void setISettoreId(BigDecimal iSettoreId) {
		this.iSettoreId = iSettoreId;
	}

	public String getQrFlagPeriodoRipartiz() {
		return this.qrFlagPeriodoRipartiz;
	}

	public void setQrFlagPeriodoRipartiz(String qrFlagPeriodoRipartiz) {
		this.qrFlagPeriodoRipartiz = qrFlagPeriodoRipartiz;
	}

	public String getQrFrequenzaRendiconto() {
		return this.qrFrequenzaRendiconto;
	}

	public void setQrFrequenzaRendiconto(String qrFrequenzaRendiconto) {
		this.qrFrequenzaRendiconto = qrFrequenzaRendiconto;
	}

	public String getQrFrequenzaServizio() {
		return this.qrFrequenzaServizio;
	}

	public void setQrFrequenzaServizio(String qrFrequenzaServizio) {
		this.qrFrequenzaServizio = qrFrequenzaServizio;
	}

	public BigDecimal getQrNGgSettimanali() {
		return this.qrNGgSettimanali;
	}

	public void setQrNGgSettimanali(BigDecimal qrNGgSettimanali) {
		this.qrNGgSettimanali = qrNGgSettimanali;
	}

	public BigDecimal getQrNTotAccessiServizio() {
		return this.qrNTotAccessiServizio;
	}

	public void setQrNTotAccessiServizio(BigDecimal qrNTotAccessiServizio) {
		this.qrNTotAccessiServizio = qrNTotAccessiServizio;
	}

	public String getSoggrifDenom() {
		return this.soggrifDenom;
	}

	public void setSoggrifDenom(String soggrifDenom) {
		this.soggrifDenom = soggrifDenom;
	}

	public String getSoggrifTelcell() {
		return this.soggrifTelcell;
	}

	public void setSoggrifTelcell(String soggrifTelcell) {
		this.soggrifTelcell = soggrifTelcell;
	}

	public String getTiCodIstat() {
		return this.tiCodIstat;
	}

	public void setTiCodIstat(String tiCodIstat) {
		this.tiCodIstat = tiCodIstat;
	}

	public String getTiDescIstat() {
		return this.tiDescIstat;
	}

	public void setTiDescIstat(String tiDescIstat) {
		this.tiDescIstat = tiDescIstat;
	}

	public BigDecimal getTiId() {
		return this.tiId;
	}

	public void setTiId(BigDecimal tiId) {
		this.tiId = tiId;
	}

	public String getTiSubCodIstat() {
		return this.tiSubCodIstat;
	}

	public void setTiSubCodIstat(String tiSubCodIstat) {
		this.tiSubCodIstat = tiSubCodIstat;
	}

	public String getTicCodiceMemo() {
		return this.ticCodiceMemo;
	}

	public void setTicCodiceMemo(String ticCodiceMemo) {
		this.ticCodiceMemo = ticCodiceMemo;
	}

	public String getTicDescrizione() {
		return this.ticDescrizione;
	}

	public void setTicDescrizione(String ticDescrizione) {
		this.ticDescrizione = ticDescrizione;
	}

	public BigDecimal getTicId() {
		return this.ticId;
	}

	public void setTicId(BigDecimal ticId) {
		this.ticId = ticId;
	}

	public String getZZonaSociale() {
		return this.zZonaSociale;
	}

	public void setZZonaSociale(String zZonaSociale) {
		this.zZonaSociale = zZonaSociale;
	}

	public BigDecimal getErSoggCasoId() {
		return erSoggCasoId;
	}

	public void setErSoggCasoId(BigDecimal erSoggCasoId) {
		this.erSoggCasoId = erSoggCasoId;
	}

}
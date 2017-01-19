package it.webred.cs.data.model.view;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the V_MOBI_INT_EROG database table.
 * 
 */
@Entity
@Table(name="V_MOBI_INT_EROG")
@NamedQuery(name="VMobiIntErog.findAll", query="SELECT v FROM VMobiIntErog v")
public class VMobiIntErog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ER_ID")
	private BigDecimal erId;

	@Column(name="ER_EROGAZIONE_POSSIBILE")
	private BigDecimal erErogazionePossibile;
	
	@Column(name="ER_SOGG_COGNOME")
	private String erSoggCognome;

	@Column(name="ER_SOGG_NOME")
	private String erSoggNome;



	@Column(name="ER_SOGG_CF")
	private String erSoggCf;

	@Column(name="ER_ORGANIZZAZIONE_ER_ID")
	private BigDecimal erOrganizzazioneErId;
	
	@Column(name="ER_ORGANIZZAZIONE_ER_NOME")
	private String erOrganizzazioneErNome;
	
	@Column(name="ER_ORGANIZZAZIONE_ER_BELFIORE")
	private String erOrganizzazioneErBelfiore;

	@Column(name="ER_ORGANIZZAZIONE_TIT_ID")
	private BigDecimal erOrganizzazioneTitId;

	@Column(name="ER_ORGANIZZAZIONE_TIT_NOME")
	private String erOrganizzazioneTitNome;

	@Column(name="ER_ORGANIZZAZIONE_TIT_BELFIORE")
	private String erOrganizzazioneTitBelfiore;

	@Column(name="I_ID")
	private BigDecimal iId;

	@Column(name="C_CASO_ID")
	private BigDecimal cCasoId;

	@Column(name="C_CASO_IDENTIFICATIVO")
	private BigDecimal cCasoIdentificativo;

	@Column(name="COMP_CONVIVENTE")
	private String compConvivente;

	@Column(name="COMP_DISPONIBILITA")
	private String compDisponibilita;

	@Column(name="CS_ID")
	private BigDecimal csId;

	@Column(name="ER_DESC_INTERVENTO_ESEG")
	private String erDescInterventoEseg;


	@Column(name="ER_OPE_COG_INS")
	private String erOpeCogIns;

	@Column(name="ER_OPE_NOME_INS")
	private String erOpeNomeIns;

	@Column(name="ER_SETTORE_EROG")
	private String erSettoreErog;

	@Column(name="ER_SETTORE_EROG_ID")
	private BigDecimal erSettoreErogId;

	@Column(name="ER_SETTORE_TIT")
	private String erSettoreTit;

	@Column(name="ER_SETTORE_TIT_ID")
	private BigDecimal erSettoreTitId;

	@Column(name="ER_TIPO_BENEFICIARIO")
	private String erTipoBeneficiario;

	@Column(name="ER_UNITA_MISURA")
	private String erUnitaMisura;

	@Column(name="FLG_DESCR_SOSPENSIONE")
	private String flgDescrSospensione;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_AMMINISTRATIVA")
	private Date flgDtAmministrativa;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_ATTIVAZIONE_A")
	private Date flgDtAttivazioneA;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_ATTIVAZIONE_DA")
	private Date flgDtAttivazioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_CHIUSURA_A")
	private Date flgDtChiusuraA;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_CHIUSURA_DA")
	private Date flgDtChiusuraDa;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_INS")
	private Date flgDtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_MOD")
	private Date flgDtMod;

	@Temporal(TemporalType.DATE)
	@Column(name="FLG_DT_SOSPENSIONE_A")
	private Date flgDtSospensioneA;

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

	@Column(name="I_CS_CODICE_MEMO")
	private String iCsCodiceMemo;

	@Column(name="I_CS_DESCRIZIONE")
	private String iCsDescrizione;

	@Temporal(TemporalType.DATE)
	@Column(name="I_FINE_AL")
	private Date iFineAl;

	@Temporal(TemporalType.DATE)
	@Column(name="I_FINE_DAL")
	private Date iFineDal;

	@Column(name="I_FLAG_PRIMAER_RINNOVO")
	private String iFlagPrimaerRinnovo;


	@Temporal(TemporalType.DATE)
	@Column(name="I_INIZIO_AL")
	private Date iInizioAl;

	@Temporal(TemporalType.DATE)
	@Column(name="I_INIZIO_DAL")
	private Date iInizioDal;

	@Column(name="I_NOME_ORG")
	private String iNomeOrg;

	@Column(name="I_SETTORE")
	private String iSettore;

	@Column(name="I_SETTORE_ID")
	private BigDecimal iSettoreId;

	@Column(name="I_TI_COD_ISTAT")
	private String iTiCodIstat;

	@Column(name="I_TI_DESC_ISTAT")
	private String iTiDescIstat;

	@Column(name="I_TI_SUB_COD_ISTAT")
	private String iTiSubCodIstat;

	@Column(name="I_TIC_CODICE_MEMO")
	private String iTicCodiceMemo;

	@Column(name="I_TIC_DESCRIZIONE")
	private String iTicDescrizione;

	@Column(name="QR_FLAG_OCCASIONALE")
	private BigDecimal qrFlagOccasionale;

	@Column(name="QR_FLAG_PERIODICO")
	private BigDecimal qrFlagPeriodico;

	@Column(name="QR_N_GG_SETTIMANALI")
	private BigDecimal qrNGgSettimanali;

	@Column(name="QR_N_TOT_ACCESSI_SERVIZIO")
	private BigDecimal qrNTotAccessiServizio;

	@Column(name="SOGGRIF_DENOM")
	private String soggrifDenom;

	@Column(name="SOGGRIF_TELCELL")
	private String soggrifTelcell;

	@Column(name="TI_ID")
	private BigDecimal tiId;

	@Column(name="TIC_ID")
	private BigDecimal ticId;

	@Column(name="Z_ZONA_SOCIALE")
	private String zZonaSociale;

	public VMobiIntErog() {
	}

	
	
	public BigDecimal getErErogazionePossibile() {
		return erErogazionePossibile;
	}



	public void setErErogazionePossibile(BigDecimal erErogazionePossibile) {
		this.erErogazionePossibile = erErogazionePossibile;
	}



	public String getErSoggCognome() {
		return erSoggCognome;
	}



	public void setErSoggCognome(String erSoggCognome) {
		this.erSoggCognome = erSoggCognome;
	}



	public String getErSoggNome() {
		return erSoggNome;
	}

	public String getErSoggCf() {
		return erSoggCf;
	}



	public void setErSoggCf(String erSoggCf) {
		this.erSoggCf = erSoggCf;
	}
	

	public void setErSoggNome(String erSoggNome) {
		this.erSoggNome = erSoggNome;
	}



	public BigDecimal getErOrganizzazioneErId() {
		return erOrganizzazioneErId;
	}



	public void setErOrganizzazioneErId(BigDecimal erOrganizzazioneErId) {
		this.erOrganizzazioneErId = erOrganizzazioneErId;
	}



	public String getErOrganizzazioneErNome() {
		return erOrganizzazioneErNome;
	}



	public void setErOrganizzazioneErNome(String erOrganizzazioneErNome) {
		this.erOrganizzazioneErNome = erOrganizzazioneErNome;
	}



	public String getErOrganizzazioneErBelfiore() {
		return erOrganizzazioneErBelfiore;
	}



	public void setErOrganizzazioneErBelfiore(String erOrganizzazioneErBelfiore) {
		this.erOrganizzazioneErBelfiore = erOrganizzazioneErBelfiore;
	}



	public BigDecimal getErOrganizzazioneTitId() {
		return erOrganizzazioneTitId;
	}



	public void setErOrganizzazioneTitId(BigDecimal erOrganizzazioneTitId) {
		this.erOrganizzazioneTitId = erOrganizzazioneTitId;
	}



	public String getErOrganizzazioneTitNome() {
		return erOrganizzazioneTitNome;
	}



	public void setErOrganizzazioneTitNome(String erOrganizzazioneTitNome) {
		this.erOrganizzazioneTitNome = erOrganizzazioneTitNome;
	}



	public String getErOrganizzazioneTitBelfiore() {
		return erOrganizzazioneTitBelfiore;
	}



	public void setErOrganizzazioneTitBelfiore(String erOrganizzazioneTitBelfiore) {
		this.erOrganizzazioneTitBelfiore = erOrganizzazioneTitBelfiore;
	}



	public BigDecimal getiId() {
		return iId;
	}



	public void setiId(BigDecimal iId) {
		this.iId = iId;
	}



	public BigDecimal getcCasoId() {
		return cCasoId;
	}



	public void setcCasoId(BigDecimal cCasoId) {
		this.cCasoId = cCasoId;
	}



	public BigDecimal getcCasoIdentificativo() {
		return cCasoIdentificativo;
	}



	public void setcCasoIdentificativo(BigDecimal cCasoIdentificativo) {
		this.cCasoIdentificativo = cCasoIdentificativo;
	}



	public String getiBelfioreOrg() {
		return iBelfioreOrg;
	}



	public void setiBelfioreOrg(String iBelfioreOrg) {
		this.iBelfioreOrg = iBelfioreOrg;
	}



	public String getiCsCodiceMemo() {
		return iCsCodiceMemo;
	}



	public void setiCsCodiceMemo(String iCsCodiceMemo) {
		this.iCsCodiceMemo = iCsCodiceMemo;
	}



	public String getiCsDescrizione() {
		return iCsDescrizione;
	}



	public void setiCsDescrizione(String iCsDescrizione) {
		this.iCsDescrizione = iCsDescrizione;
	}



	public Date getiFineAl() {
		return iFineAl;
	}



	public void setiFineAl(Date iFineAl) {
		this.iFineAl = iFineAl;
	}



	public Date getiFineDal() {
		return iFineDal;
	}



	public void setiFineDal(Date iFineDal) {
		this.iFineDal = iFineDal;
	}



	public String getiFlagPrimaerRinnovo() {
		return iFlagPrimaerRinnovo;
	}



	public void setiFlagPrimaerRinnovo(String iFlagPrimaerRinnovo) {
		this.iFlagPrimaerRinnovo = iFlagPrimaerRinnovo;
	}



	public Date getiInizioAl() {
		return iInizioAl;
	}



	public void setiInizioAl(Date iInizioAl) {
		this.iInizioAl = iInizioAl;
	}



	public Date getiInizioDal() {
		return iInizioDal;
	}



	public void setiInizioDal(Date iInizioDal) {
		this.iInizioDal = iInizioDal;
	}



	public String getiNomeOrg() {
		return iNomeOrg;
	}



	public void setiNomeOrg(String iNomeOrg) {
		this.iNomeOrg = iNomeOrg;
	}



	public String getiSettore() {
		return iSettore;
	}



	public void setiSettore(String iSettore) {
		this.iSettore = iSettore;
	}



	public BigDecimal getiSettoreId() {
		return iSettoreId;
	}



	public void setiSettoreId(BigDecimal iSettoreId) {
		this.iSettoreId = iSettoreId;
	}



	public String getiTiCodIstat() {
		return iTiCodIstat;
	}



	public void setiTiCodIstat(String iTiCodIstat) {
		this.iTiCodIstat = iTiCodIstat;
	}



	public String getiTiDescIstat() {
		return iTiDescIstat;
	}



	public void setiTiDescIstat(String iTiDescIstat) {
		this.iTiDescIstat = iTiDescIstat;
	}



	public String getiTiSubCodIstat() {
		return iTiSubCodIstat;
	}



	public void setiTiSubCodIstat(String iTiSubCodIstat) {
		this.iTiSubCodIstat = iTiSubCodIstat;
	}



	public String getiTicCodiceMemo() {
		return iTicCodiceMemo;
	}



	public void setiTicCodiceMemo(String iTicCodiceMemo) {
		this.iTicCodiceMemo = iTicCodiceMemo;
	}



	public String getiTicDescrizione() {
		return iTicDescrizione;
	}



	public void setiTicDescrizione(String iTicDescrizione) {
		this.iTicDescrizione = iTicDescrizione;
	}



	public String getzZonaSociale() {
		return zZonaSociale;
	}



	public void setzZonaSociale(String zZonaSociale) {
		this.zZonaSociale = zZonaSociale;
	}



	public BigDecimal getCCasoId() {
		return this.cCasoId;
	}

	public void setCCasoId(BigDecimal cCasoId) {
		this.cCasoId = cCasoId;
	}

	public BigDecimal getCCasoIdentificativo() {
		return this.cCasoIdentificativo;
	}

	public void setCCasoIdentificativo(BigDecimal cCasoIdentificativo) {
		this.cCasoIdentificativo = cCasoIdentificativo;
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

	public BigDecimal getCsId() {
		return this.csId;
	}

	public void setCsId(BigDecimal csId) {
		this.csId = csId;
	}

	public String getErDescInterventoEseg() {
		return this.erDescInterventoEseg;
	}

	public void setErDescInterventoEseg(String erDescInterventoEseg) {
		this.erDescInterventoEseg = erDescInterventoEseg;
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

	public String getErTipoBeneficiario() {
		return this.erTipoBeneficiario;
	}

	public void setErTipoBeneficiario(String erTipoBeneficiario) {
		this.erTipoBeneficiario = erTipoBeneficiario;
	}

	public String getErUnitaMisura() {
		return this.erUnitaMisura;
	}

	public void setErUnitaMisura(String erUnitaMisura) {
		this.erUnitaMisura = erUnitaMisura;
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

	public String getICsCodiceMemo() {
		return this.iCsCodiceMemo;
	}

	public void setICsCodiceMemo(String iCsCodiceMemo) {
		this.iCsCodiceMemo = iCsCodiceMemo;
	}

	public String getICsDescrizione() {
		return this.iCsDescrizione;
	}

	public void setICsDescrizione(String iCsDescrizione) {
		this.iCsDescrizione = iCsDescrizione;
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

	public String getITiCodIstat() {
		return this.iTiCodIstat;
	}

	public void setITiCodIstat(String iTiCodIstat) {
		this.iTiCodIstat = iTiCodIstat;
	}

	public String getITiDescIstat() {
		return this.iTiDescIstat;
	}

	public void setITiDescIstat(String iTiDescIstat) {
		this.iTiDescIstat = iTiDescIstat;
	}

	public String getITiSubCodIstat() {
		return this.iTiSubCodIstat;
	}

	public void setITiSubCodIstat(String iTiSubCodIstat) {
		this.iTiSubCodIstat = iTiSubCodIstat;
	}

	public String getITicCodiceMemo() {
		return this.iTicCodiceMemo;
	}

	public void setITicCodiceMemo(String iTicCodiceMemo) {
		this.iTicCodiceMemo = iTicCodiceMemo;
	}

	public String getITicDescrizione() {
		return this.iTicDescrizione;
	}

	public void setITicDescrizione(String iTicDescrizione) {
		this.iTicDescrizione = iTicDescrizione;
	}

	public BigDecimal getQrFlagOccasionale() {
		return this.qrFlagOccasionale;
	}

	public void setQrFlagOccasionale(BigDecimal qrFlagOccasionale) {
		this.qrFlagOccasionale = qrFlagOccasionale;
	}

	public BigDecimal getQrFlagPeriodico() {
		return this.qrFlagPeriodico;
	}

	public void setQrFlagPeriodico(BigDecimal qrFlagPeriodico) {
		this.qrFlagPeriodico = qrFlagPeriodico;
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

	public BigDecimal getTiId() {
		return this.tiId;
	}

	public void setTiId(BigDecimal tiId) {
		this.tiId = tiId;
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

}
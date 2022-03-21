package it.webred.cs.data.model.pti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the CS_PAI_PTI database table.
 * 
 */
@Entity
@Table(name = "CS_PAI_PTI_REVISIONI")
public class CsPaiPTIRevisioni implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2755378314172065359L;

	@Id
	@Column(name = "ID")
	@SequenceGenerator(name = "PTI_REVISIONI_ID_GENERATOR", sequenceName = "SQ_PAI_PTI_REVISIONI", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PTI_REVISIONI_ID_GENERATOR")
	private Long id;

	@Column(name = "ID_PAI_PTI")
	private Long idPaiPTI;

	@Column(name = "COD_ROUTING")
	private String codRouting;
	
	@Column(name = "MOTIVO_REVISIONE")
	private String motivoRevisione;
	
	@Column(name = "PROROGA")
	private String proroga;
	
	@Column(name = "VERIFICA_OGNI")
	private BigDecimal verificaOgni;

	@Column(name = "VERIFICA_UNITA_MISURA")
	private String verificaUnitaMisura;

	@Column(name = "OBIETTIVI_BREVE")
	private String obiettiviBreve;

	@Column(name = "OBIETTIVI_MEDIO")
	private String obiettiviMedio;

	@Column(name = "OBIETTIVI_LUNGO")
	private String obiettiviLungo;

	@Column(name = "RAGGIUNTI_BREVE")
	private Integer raggiuntiBreve;

	@Column(name = "RAGGIUNTI_MEDIO")
	private Integer raggiuntiMedio;

	@Column(name = "RAGGIUNTI_LUNGO")
	private Integer raggiuntiLungo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_MONITORAGGIO")
	private Date dataMonitoraggio;

	@Column(name = "MONITORAGGIO_OBIETTIVI")
	private Boolean monitoraggioObiettivi;
	
	//pti
	@Temporal(TemporalType.DATE)
	@Column(name = "DATA_REDAZIONE_PTI")
	private Date dataRedazione;

	@Column(name = "FLG_EMERGENZA")
	private Boolean flgEmergenza;

	@Column(name = "FLG_COND_PRES_ADULTI")
	private Boolean flgCondVerifPresenzaAdulti;

	@Column(name = "FLG_COINV_FAM")
	private Boolean flgCoinvFamiglia;

	@Column(name = "FLG_DISABILITA")
	private Boolean flgDisabilita;

	@Temporal(TemporalType.DATE)
	@Column(name = "PERIODO_INS_PIAN_DA")
	private Date periodoInsPianificazioneDa;

	@Temporal(TemporalType.DATE)
	@Column(name = "PERIODO_INS_PIAN_A")
	private Date periodoInsPianificazioneA;

	@Column(name = "FLG_INTERVENTI_DISABILI")
	private Boolean flgInterventiDisabili;

	@Column(name = "FLG_GRAVIDANZA")
	private Boolean flgGravidanza;

	@Column(name = "FLG_NEONATO")
	private Boolean flgNeonato;

	@Column(name = "FLG_AREA_PENALE")
	private Boolean flgAreaPenale;

	@Column(name = "ID_CASE_MANAGER")
	private Long idCaseManager;
	
	@Column(name = "CASE_MANAGER")
	private String caseManager;
	
	@Column(name = "FLG_PROR_RICH_MAGG")
	private Boolean flgProrRichMagg;

	@Column(name = "FLG_PROR_LIMITE_ETA")
	private Boolean flgProrLimiteEta;

	@Column(name = "FLG_ESISTE_EDU_PEDA")
	private Boolean flgEsisteEduPeda;

	@Column(name = "FLG_INVIO_SEGN_TM")
	private Boolean flgInvioSegnTM;

	@Column(name = "DIARIO_SINBA_ID")
	private Long diarioSinbaId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS")
	private Date dtIns;
	
	@Column(name = "USR_INS")
	private String userIns;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdPaiPTI() {
		return idPaiPTI;
	}

	public void setIdPaiPTI(Long idPaiPTI) {
		this.idPaiPTI = idPaiPTI;
	}

	public String getCodRouting() {
		return codRouting;
	}

	public void setCodRouting(String codRouting) {
		this.codRouting = codRouting;
	}

	public String getMotivoRevisione() {
		return motivoRevisione;
	}

	public void setMotivoRevisione(String motivoRevisione) {
		this.motivoRevisione = motivoRevisione;
	}

	public BigDecimal getVerificaOgni() {
		return verificaOgni;
	}

	public void setVerificaOgni(BigDecimal verificaOgni) {
		this.verificaOgni = verificaOgni;
	}

	public String getVerificaUnitaMisura() {
		return verificaUnitaMisura;
	}

	public void setVerificaUnitaMisura(String verificaUnitaMisura) {
		this.verificaUnitaMisura = verificaUnitaMisura;
	}

	public String getObiettiviBreve() {
		return obiettiviBreve;
	}

	public void setObiettiviBreve(String obiettiviBreve) {
		this.obiettiviBreve = obiettiviBreve;
	}

	public String getObiettiviMedio() {
		return obiettiviMedio;
	}

	public void setObiettiviMedio(String obiettiviMedio) {
		this.obiettiviMedio = obiettiviMedio;
	}

	public String getObiettiviLungo() {
		return obiettiviLungo;
	}

	public void setObiettiviLungo(String obiettiviLungo) {
		this.obiettiviLungo = obiettiviLungo;
	}

	public Integer getRaggiuntiBreve() {
		return raggiuntiBreve;
	}

	public void setRaggiuntiBreve(Integer raggiuntiBreve) {
		this.raggiuntiBreve = raggiuntiBreve;
	}

	public Integer getRaggiuntiMedio() {
		return raggiuntiMedio;
	}

	public void setRaggiuntiMedio(Integer raggiuntiMedio) {
		this.raggiuntiMedio = raggiuntiMedio;
	}

	public Integer getRaggiuntiLungo() {
		return raggiuntiLungo;
	}

	public void setRaggiuntiLungo(Integer raggiuntiLungo) {
		this.raggiuntiLungo = raggiuntiLungo;
	}

	public Date getDataMonitoraggio() {
		return dataMonitoraggio;
	}

	public void setDataMonitoraggio(Date dataMonitoraggio) {
		this.dataMonitoraggio = dataMonitoraggio;
	}

	public Boolean getMonitoraggioObiettivi() {
		return monitoraggioObiettivi;
	}

	public void setMonitoraggioObiettivi(Boolean monitoraggioObiettivi) {
		this.monitoraggioObiettivi = monitoraggioObiettivi;
	}

	public Date getDataRedazione() {
		return dataRedazione;
	}

	public void setDataRedazione(Date dataRedazione) {
		this.dataRedazione = dataRedazione;
	}

	public Boolean getFlgEmergenza() {
		return flgEmergenza;
	}

	public void setFlgEmergenza(Boolean flgEmergenza) {
		this.flgEmergenza = flgEmergenza;
	}

	public Boolean getFlgCondVerifPresenzaAdulti() {
		return flgCondVerifPresenzaAdulti;
	}

	public void setFlgCondVerifPresenzaAdulti(Boolean flgCondVerifPresenzaAdulti) {
		this.flgCondVerifPresenzaAdulti = flgCondVerifPresenzaAdulti;
	}

	public Boolean getFlgCoinvFamiglia() {
		return flgCoinvFamiglia;
	}

	public void setFlgCoinvFamiglia(Boolean flgCoinvFamiglia) {
		this.flgCoinvFamiglia = flgCoinvFamiglia;
	}

	public Boolean getFlgDisabilita() {
		return flgDisabilita;
	}

	public void setFlgDisabilita(Boolean flgDisabilita) {
		this.flgDisabilita = flgDisabilita;
	}

	public Date getPeriodoInsPianificazioneDa() {
		return periodoInsPianificazioneDa;
	}

	public void setPeriodoInsPianificazioneDa(Date periodoInsPianificazioneDa) {
		this.periodoInsPianificazioneDa = periodoInsPianificazioneDa;
	}

	public Date getPeriodoInsPianificazioneA() {
		return periodoInsPianificazioneA;
	}

	public void setPeriodoInsPianificazioneA(Date periodoInsPianificazioneA) {
		this.periodoInsPianificazioneA = periodoInsPianificazioneA;
	}

	public Boolean getFlgInterventiDisabili() {
		return flgInterventiDisabili;
	}

	public void setFlgInterventiDisabili(Boolean flgInterventiDisabili) {
		this.flgInterventiDisabili = flgInterventiDisabili;
	}

	public Boolean getFlgGravidanza() {
		return flgGravidanza;
	}

	public void setFlgGravidanza(Boolean flgGravidanza) {
		this.flgGravidanza = flgGravidanza;
	}

	public Boolean getFlgNeonato() {
		return flgNeonato;
	}

	public void setFlgNeonato(Boolean flgNeonato) {
		this.flgNeonato = flgNeonato;
	}

	public Boolean getFlgAreaPenale() {
		return flgAreaPenale;
	}

	public void setFlgAreaPenale(Boolean flgAreaPenale) {
		this.flgAreaPenale = flgAreaPenale;
	}

	public Long getIdCaseManager() {
		return idCaseManager;
	}

	public void setIdCaseManager(Long idCaseManager) {
		this.idCaseManager = idCaseManager;
	}

	public Boolean getFlgProrRichMagg() {
		return flgProrRichMagg;
	}

	public void setFlgProrRichMagg(Boolean flgProrRichMagg) {
		this.flgProrRichMagg = flgProrRichMagg;
	}

	public Boolean getFlgProrLimiteEta() {
		return flgProrLimiteEta;
	}

	public void setFlgProrLimiteEta(Boolean flgProrLimiteEta) {
		this.flgProrLimiteEta = flgProrLimiteEta;
	}

	public Boolean getFlgEsisteEduPeda() {
		return flgEsisteEduPeda;
	}

	public void setFlgEsisteEduPeda(Boolean flgEsisteEduPeda) {
		this.flgEsisteEduPeda = flgEsisteEduPeda;
	}

	public Boolean getFlgInvioSegnTM() {
		return flgInvioSegnTM;
	}

	public void setFlgInvioSegnTM(Boolean flgInvioSegnTM) {
		this.flgInvioSegnTM = flgInvioSegnTM;
	}

	public Long getDiarioSinbaId() {
		return diarioSinbaId;
	}

	public void setDiarioSinbaId(Long diarioSinbaId) {
		this.diarioSinbaId = diarioSinbaId;
	}

	public String getProroga() {
		return proroga;
	}

	public void setProroga(String proroga) {
		this.proroga = proroga;
	}

	public Date getDtIns() {
		return dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getCaseManager() {
		return caseManager;
	}

	public void setCaseManager(String caseManager) {
		this.caseManager = caseManager;
	}

}

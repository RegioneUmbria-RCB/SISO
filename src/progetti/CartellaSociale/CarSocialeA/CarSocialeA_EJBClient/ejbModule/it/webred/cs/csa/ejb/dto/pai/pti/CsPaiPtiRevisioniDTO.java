package it.webred.cs.csa.ejb.dto.pai.pti;

import java.math.BigDecimal;
import java.util.Date;

public class CsPaiPtiRevisioniDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2755378314172065359L;

	private Long id;

	private Long idPaiPTI;

	private String codRouting;
	
	private String motivoRevisione;
	
	private String proroga;
	
	private BigDecimal verificaOgni;

	private String verificaUnitaMisura;

	private String obiettiviBreve;

	private String obiettiviMedio;

	private String obiettiviLungo;

	private Integer raggiuntiBreve;

	private Integer raggiuntiMedio;

	private Integer raggiuntiLungo;
	
	private Date dataMonitoraggio;

	private Boolean monitoraggioObiettivi;
	
	//pti
	private Date dataRedazione;

	private Boolean flgEmergenza;

	private Boolean flgCondVerifPresenzaAdulti;

	private Boolean flgCoinvFamiglia;

	private Boolean flgDisabilita;

	private Date periodoInsPianificazioneDa;

	private Date periodoInsPianificazioneA;

	private Boolean flgInterventiDisabili;

	private Boolean flgGravidanza;

	private Boolean flgNeonato;

	private Boolean flgAreaPenale;

	private Long idCaseManager;
	
	private String caseManager;

	private Boolean flgProrRichMagg;

	private Boolean flgProrLimiteEta;

	private Boolean flgEsisteEduPeda;

	private Boolean flgInvioSegnTM;

	private Long diarioSinbaId;
	
	private Date dtIns;
	
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

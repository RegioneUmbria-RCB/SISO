package it.webred.cs.csa.ejb.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ErogazioniSearchCriteria extends PaginationDTO {

	private static final long serialVersionUID = 1L;

	private boolean permessoAutorizzativo;
	private boolean searchConRichiesta = false;
	private boolean searchErogatiNoIntervento = false;
	private boolean loadDettaglioErogazione = false;

	//SISO-748
	private boolean searchByCaso = false;
	private Long casoId;
	private Long diarioPaiId;

	private Long settoreId;
	private Long organizzazioneId;

	private Long[] lstTipoIntervento;
	private Long[] lstTipoInterventoCustom;
	private String[] lstTipoInterventoInps; //SISO-1162
	private Date dataErogazione;
	private Date dataEvento;
	private String statoErogazione;

	//frida aggiunti
	private String dataInizio;
	private String dataFine;
	private Long operatoreId;
	private String organizzazioneBelfiore;//MOD-RL

	private String lineaFinanziamento;
	private String[] lstTipoBeneficiario;

	private Long idMaster; //SISO-538

	private String denominazione;
	private String cognome;
	private String nome;
	private String codiceFiscale;
	private boolean searchByBeneficiario = false;

	private List<BigDecimal> lstMasterId; //Valorizzo in un secondo momento, filtrando la tabella dei soggetti

	private String descCatSociale;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isPermessoAutorizzativo() {
		return permessoAutorizzativo;
	}

	public Long getSettoreId() {
		return settoreId;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public String getCognome() {
		return cognome;
	}

	public String getNome() {
		return nome;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public Date getDataErogazione() {
		return dataErogazione;
	}

	public void setDataErogazione(Date dataErogazione) {
		this.dataErogazione = dataErogazione;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public void setPermessoAutorizzativo(boolean permessoAutorizzativo) {
		this.permessoAutorizzativo = permessoAutorizzativo;
	}

	public void setSettoreId(Long settoreId) {
		this.settoreId = settoreId;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public void setStatoErogazione(String statoErogazione) {
		this.statoErogazione = statoErogazione;
	}

	public String getStatoErogazione() {
		return statoErogazione;
	}

	public boolean isSearchErogatiNoIntervento() {
		return searchErogatiNoIntervento;
	}

	public boolean isSearchConRichiesta() {
		return searchConRichiesta;
	}

	public void setSearchConRichiesta(boolean searchConRichiesta) {
		this.searchConRichiesta = searchConRichiesta;
	}

	public void setSearchErogatiNoIntervento(boolean searchErogatiNoIntervento) {
		this.searchErogatiNoIntervento = searchErogatiNoIntervento;
	}

	public Long[] getLstTipoIntervento() {
		return lstTipoIntervento;
	}

	public void setLstTipoIntervento(Long[] lstTipoIntervento) {
		this.lstTipoIntervento = lstTipoIntervento;
	}

	public Long[] getLstTipoInterventoCustom() {
		return lstTipoInterventoCustom;
	}

	public void setLstTipoInterventoCustom(Long[] lstTipoInterventoCustom) {
		this.lstTipoInterventoCustom = lstTipoInterventoCustom;
	}

	//SISO_1162
	public String[] getLstTipoInterventoInps() {
		return lstTipoInterventoInps;
	}

	public void setLstTipoInterventoInps(String[] lstTipoInterventoInps) {
		this.lstTipoInterventoInps = lstTipoInterventoInps;
	}

	public String getLineaFinanziamento() {
		return lineaFinanziamento;
	}

	public void setLineaFinanziamento(String lineaFinanziamento) {
		this.lineaFinanziamento = lineaFinanziamento;
	}

	public String[] getLstTipoBeneficiario() {
		return lstTipoBeneficiario;
	}

	public void setLstTipoBeneficiario(String[] lstTipoBeneficiario) {
		this.lstTipoBeneficiario = lstTipoBeneficiario;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}

	public String getDataFine() {
		return dataFine;
	}

	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}

	public Long getOperatoreId() {
		return operatoreId;
	}

	public void setOperatoreId(Long operatoreId) {
		this.operatoreId = operatoreId;
	}

	public List<BigDecimal> getLstMasterId() {
		return lstMasterId;
	}

	public void setLstMasterId(List<BigDecimal> lstMasterId) {
		this.lstMasterId = lstMasterId;
	}

	public boolean isSearchByBeneficiario() {
		return searchByBeneficiario;
	}

	public void setSearchByBeneficiario(boolean searchByBeneficiario) {
		this.searchByBeneficiario = searchByBeneficiario;
	}

	//INIZIO MOD-RL
	public String getOrganizzazioneBelfiore() {
		return organizzazioneBelfiore;
	}

	public void setOrganizzazioneBelfiore(String organizzazioneBelfiore) {
		this.organizzazioneBelfiore = organizzazioneBelfiore;
	}
	//FINE MOD-RL

	//inizio SISO-538
	public Long getIdMaster() {
		return idMaster;
	}

	public void setIdMaster(Long idMaster) {
		this.idMaster = idMaster;
	}

	//fine SISO-538
	public boolean isSearchByCaso() {
		return searchByCaso;
	}

	public void setSearchByCaso(boolean searchByCaso) {
		this.searchByCaso = searchByCaso;
	}

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public String getDescCatSociale() {
		return descCatSociale;
	}

	public void setDescCatSociale(String descCatSociale) {
		this.descCatSociale = descCatSociale;
	}

	public boolean isLoadDettaglioErogazione() {
		return loadDettaglioErogazione;
	}

	public void setLoadDettaglioErogazione(boolean loadDettaglioErogazione) {
		this.loadDettaglioErogazione = loadDettaglioErogazione;
	}

}
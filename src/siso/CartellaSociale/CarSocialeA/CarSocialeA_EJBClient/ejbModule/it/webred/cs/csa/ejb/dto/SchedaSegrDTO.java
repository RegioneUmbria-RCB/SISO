package it.webred.cs.csa.ejb.dto;

public class SchedaSegrDTO extends PaginationDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id; //Identificativo della scheda UDC (Campo SCHEDA_ID in CS_SS_SCHEDA_SEGR)
	private String provenienza;
	
	private boolean nuovoInserimento;
	private boolean tipoSchedaPropostaPic;
	private Long idCatSociale;
	private Long idSettore;
	private Long idAnagrafica;
	private String cf;
	private boolean searchBySoggetto = false;
	
	private Long enteSchedaAccessoId;
	private String enteDestinatario; //Codice Belfiore dell'ente destinatario della scheda CS_SS_SCHEDA_SEGR
	
	// variabili usate dai filtri della View
	private boolean onlyNew;
	
	private String dataAccesso;
	private String operatore;
	private String ufficio;
	private String soggettoSegnalato;
	private String tipoIntervento;
	private String categoriaSociale;
	private String[] lstProvenienza;	// SISO-938
	
	private boolean loadListaUDCFascicolo = false;
	private boolean loadStatoIterCartella = false;

	public boolean isNuovoInserimento() {
		return nuovoInserimento;
	}
	public void setNuovoInserimento(boolean nuovoInserimento) {
		this.nuovoInserimento = nuovoInserimento;
	}
	public boolean isOnlyNew() {
		return onlyNew;
	}
	public void setOnlyNew(boolean onlyNew) {
		this.onlyNew = onlyNew;
	}
	public Long getIdCatSociale() {
		return idCatSociale;
	}
	public void setIdCatSociale(Long idCatSociale) {
		this.idCatSociale = idCatSociale;
	}
	public Long getIdSettore() {
		return idSettore;
	}
	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}
	public String getDataAccesso() {
		return dataAccesso;
	}
	public void setDataAccesso(String dataAccesso) {
		this.dataAccesso = dataAccesso;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public String getSoggettoSegnalato() {
		return soggettoSegnalato;
	}
	public void setSoggettoSegnalato(String soggettoSegnalato) {
		this.soggettoSegnalato = soggettoSegnalato;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public Long getIdAnagrafica() {
		return idAnagrafica;
	}
	public void setIdAnagrafica(Long idAnagrafica) {
		this.idAnagrafica = idAnagrafica;
	}
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public String getCategoriaSociale() {
		return categoriaSociale;
	}
	public void setCategoriaSociale(String categoriaSociale) {
		this.categoriaSociale = categoriaSociale;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String[] getLstProvenienza() {
		return lstProvenienza;
	}
	public void setLstProvenienza(String[] lstProvenienza) {
		this.lstProvenienza = lstProvenienza;
	}
	public boolean isSearchBySoggetto() {
		return searchBySoggetto;
	}
	public void setSearchBySoggetto(boolean searchBySoggetto) {
		this.searchBySoggetto = searchBySoggetto;
	}
	public boolean isLoadListaUDCFascicolo() {
		return loadListaUDCFascicolo;
	}
	public void setLoadListaUDCFascicolo(boolean loadListaUDCFascicolo) {
		this.loadListaUDCFascicolo = loadListaUDCFascicolo;
	}
	public String getEnteDestinatario() {
		return enteDestinatario;
	}
	public void setEnteDestinatario(String enteDestinatario) {
		this.enteDestinatario = enteDestinatario;
	}
	public boolean isTipoSchedaPropostaPic() {
		return tipoSchedaPropostaPic;
	}
	public void setTipoSchedaPropostaPic(boolean tipoSchedaPropostaPic) {
		this.tipoSchedaPropostaPic = tipoSchedaPropostaPic;
	}
	public Long getEnteSchedaAccessoId() {
		return enteSchedaAccessoId;
	}
	public void setEnteSchedaAccessoId(Long enteSchedaAccessoId) {
		this.enteSchedaAccessoId = enteSchedaAccessoId;
	}
	public boolean isLoadStatoIterCartella() {
		return loadStatoIterCartella;
	}
	public void setLoadStatoIterCartella(boolean loadStatoIterCartella) {
		this.loadStatoIterCartella = loadStatoIterCartella;
	}
	
}

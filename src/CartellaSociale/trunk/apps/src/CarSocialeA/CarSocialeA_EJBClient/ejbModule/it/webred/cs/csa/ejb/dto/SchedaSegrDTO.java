package it.webred.cs.csa.ejb.dto;

public class SchedaSegrDTO extends PaginationDTO {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String flgStato;
	private Long idCatSociale;
	private Long idSettore;
	private Long idAnagrafica;
	private String cf;
	
	private boolean onlyNew;
	
	private String dataAccesso;
	private String operatore;
	private String ufficio;
	private String soggSegnalante;
	private String tipoIntervento;
	private String categoriaSociale;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFlgStato() {
		return flgStato;
	}
	public void setFlgStato(String flgStato) {
		this.flgStato = flgStato;
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
	public String getSoggSegnalante() {
		return soggSegnalante;
	}
	public void setSoggSegnalante(String soggSegnalante) {
		this.soggSegnalante = soggSegnalante;
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
	
}

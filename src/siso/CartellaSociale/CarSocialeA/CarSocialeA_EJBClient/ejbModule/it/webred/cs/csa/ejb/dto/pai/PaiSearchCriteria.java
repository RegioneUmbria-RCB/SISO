package it.webred.cs.csa.ejb.dto.pai;

import it.webred.cs.csa.ejb.dto.PaginationDTO;

public class PaiSearchCriteria extends PaginationDTO{

	private static final long serialVersionUID = 1L;

	private Long casoId;
	private String cf;
	private boolean loadListaPaiFascicolo;
	private boolean loadListaExtCompleta;
	private boolean loadListaExtSoggetto;

	private String codiceFiscale;
	private Long diarioId;
	private Long tipoPaiId;
	
	//Condizioni di filtro
	private String tipoBeneficiario;
	private String denominazione;
	private Boolean daChiudere;
	private Boolean daControllare;
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Long getCasoId() {
		return casoId;
	}
	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public Long getDiarioId() {
		return diarioId;
	}
	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}
	public String getTipoBeneficiario() {
		return tipoBeneficiario;
	}
	public void setTipoBeneficiario(String tipoBeneficiario) {
		this.tipoBeneficiario = tipoBeneficiario;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public Long getTipoPaiId() {
		return tipoPaiId;
	}
	public void setTipoPaiId(Long tipoPaiId) {
		this.tipoPaiId = tipoPaiId;
	}
	public Boolean getDaChiudere() {
		return daChiudere;
	}
	public void setDaChiudere(Boolean daChiudere) {
		this.daChiudere = daChiudere;
	}
	public Boolean getDaControllare() {
		return daControllare;
	}
	public void setDaControllare(Boolean daControllare) {
		this.daControllare = daControllare;
	}
	public boolean isLoadListaPaiFascicolo() {
		return loadListaPaiFascicolo;
	}
	public void setLoadListaPaiFascicolo(boolean loadListaPaiFascicolo) {
		this.loadListaPaiFascicolo = loadListaPaiFascicolo;
	}
	public boolean isLoadListaExtCompleta() {
		return loadListaExtCompleta;
	}
	public void setLoadListaExtCompleta(boolean loadListaExtCompleta) {
		this.loadListaExtCompleta = loadListaExtCompleta;
	}
	public boolean isLoadListaExtSoggetto() {
		return loadListaExtSoggetto;
	}
	public void setLoadListaExtSoggetto(boolean loadListaExtSoggetto) {
		this.loadListaExtSoggetto = loadListaExtSoggetto;
	}
}
package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class CasoSearchCriteria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private boolean permessoCasiSettore;
	private boolean permessoCasiOrganizzazione;
	
	private String denominazione;
	private String codiceFiscale;
	private String dataNascita;
	private Long idOrganizzazione;
	private Long idSettore;
	private Long idOperatore;
	private String username;
	private Long idCatSociale;
	private Long idLastIter;
	private boolean withResponsabile;
	private String lstCatSociale;
	
	private Long idOperatoreAltro; //Altro operatore da ricercare, nel caso di permessi su settore
	private Boolean opResponsabile;
	
	private String dataApertura;
	
	private String lstStati;
	private String residenza;
	
	/*Filtro Dati Sociali*/
	private Long titStudioId;
	private Long condLavoroId;
	private String tipoTutela;
	private String[] tribunale;
	
	//SISO-812
	private Boolean permessiScheda = false;
	
	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getDataNascita() {
		return dataNascita;
	}
	
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Long getIdSettore() {
		return idSettore;
	}

	public void setIdSettore(Long idSettore) {
		this.idSettore = idSettore;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getIdOperatore() {
		return idOperatore;
	}

	public void setIdOperatore(Long idOperatore) {
		this.idOperatore = idOperatore;
	}

	public boolean isPermessoCasiSettore() {
		return permessoCasiSettore;
	}

	public void setPermessoCasiSettore(boolean permessoCasiSettore) {
		this.permessoCasiSettore = permessoCasiSettore;
	}

	public Long getIdCatSociale() {
		return idCatSociale;
	}

	public void setIdCatSociale(Long idCatSociale) {
		this.idCatSociale = idCatSociale;
	}

	public Long getIdLastIter() {
		return idLastIter;
	}

	public void setIdLastIter(Long idLastIter) {
		this.idLastIter = idLastIter;
	}

	public boolean isWithResponsabile() {
		return withResponsabile;
	}

	public void setWithResponsabile(boolean withResponsabile) {
		this.withResponsabile = withResponsabile;
	}

	public Long getIdOrganizzazione() {
		return idOrganizzazione;
	}

	public void setIdOrganizzazione(Long idOrganizzazione) {
		this.idOrganizzazione = idOrganizzazione;
	}

	public boolean isPermessoCasiOrganizzazione() {
		return permessoCasiOrganizzazione;
	}

	public void setPermessoCasiOrganizzazione(boolean permessoCasiOrganizzazione) {
		this.permessoCasiOrganizzazione = permessoCasiOrganizzazione;
	}

	public String getLstCatSociale() {
		return lstCatSociale;
	}

	public void setLstCatSociale(String lstCatSociale) {
		this.lstCatSociale = lstCatSociale;
	}

	public String getLstStati() {
		return lstStati;
	}

	public void setLstStati(String lstStati) {
		this.lstStati = lstStati;
	}

	public Long getIdOperatoreAltro() {
		return idOperatoreAltro;
	}

	public void setIdOperatoreAltro(Long idOperatoreAltro) {
		this.idOperatoreAltro = idOperatoreAltro;
	}

	public Long getTitStudioId() {
		return titStudioId;
	}

	public Long getCondLavoroId() {
		return condLavoroId;
	}

	public String getTipoTutela() {
		return tipoTutela;
	}

	public void setTitStudioId(Long titStudioId) {
		this.titStudioId = titStudioId;
	}

	public void setCondLavoroId(Long condLavoroId) {
		this.condLavoroId = condLavoroId;
	}

	public void setTipoTutela(String tipoTutela) {
		this.tipoTutela = tipoTutela;
	}

	public String getDataApertura() {
		return dataApertura;
	}

	public void setDataApertura(String dataApertura) {
		this.dataApertura = dataApertura;
	}

	public Boolean getOpResponsabile() {
		return opResponsabile;
	}

	public void setOpResponsabile(Boolean opResponsabile) {
		this.opResponsabile = opResponsabile;
	}

	public String[] getTribunale() {
		return tribunale;
	}

	public void setTribunale(String[] tribunale) {
		this.tribunale = tribunale;
	}

	public Boolean getPermessiScheda() {
		return permessiScheda;
	}

	public void setPermessiScheda(Boolean permessiScheda) {
		this.permessiScheda = permessiScheda;
	}

	public String getResidenza() {
		return residenza;
	}

	public void setResidenza(String residenza) {
		this.residenza = residenza;
	}
	
}

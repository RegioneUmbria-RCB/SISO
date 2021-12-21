package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;

public class ConfigurazioneFseDTO implements Serializable {

	private CampoFseDTO azRagioneSociale;
	private CampoFseDTO azPi;
	private CampoFseDTO azCf;
	private CampoFseDTO azVia;
	private CampoFseDTO azComune;
	private CampoFseDTO azCodAteco;
	private CampoFseDTO azFormaGiuridica;
	private CampoFseDTO azDimensione;
	
	private CampoFseDTO lavoroTipo;
	private CampoFseDTO lavoroOrario;
	
	private CampoFseDTO annoTitoloStudio;
	private CampoFseDTO inattivoAltroCorso;
	private CampoFseDTO durataRicercaLavoro;
	
	private CampoFseDTO pagIban;
	private CampoFseDTO pagResDom;
	
	private CampoFseDTO soggettoAttuatore;
	private CampoFseDTO dataSottoscrizione;
	
	public CampoFseDTO getAzRagioneSociale() {
		return azRagioneSociale;
	}
	public void setAzRagioneSociale(CampoFseDTO azRagioneSociale) {
		this.azRagioneSociale = azRagioneSociale;
	}
	public CampoFseDTO getAzPi() {
		return azPi;
	}
	public void setAzPi(CampoFseDTO azPi) {
		this.azPi = azPi;
	}
	public CampoFseDTO getAzCf() {
		return azCf;
	}
	public void setAzCf(CampoFseDTO azCf) {
		this.azCf = azCf;
	}
	public CampoFseDTO getAzVia() {
		return azVia;
	}
	public void setAzVia(CampoFseDTO azVia) {
		this.azVia = azVia;
	}
	public CampoFseDTO getAzComune() {
		return azComune;
	}
	public void setAzComune(CampoFseDTO azComune) {
		this.azComune = azComune;
	}
	public CampoFseDTO getAzCodAteco() {
		return azCodAteco;
	}
	public void setAzCodAteco(CampoFseDTO azCodAteco) {
		this.azCodAteco = azCodAteco;
	}
	public CampoFseDTO getAzFormaGiuridica() {
		return azFormaGiuridica;
	}
	public void setAzFormaGiuridica(CampoFseDTO azFormaGiuridica) {
		this.azFormaGiuridica = azFormaGiuridica;
	}	
	public CampoFseDTO getAzDimensione() {
		return azDimensione;
	}
	public void setAzDimensione(CampoFseDTO azDimensione) {
		this.azDimensione = azDimensione;
	}
	public CampoFseDTO getLavoroTipo() {
		return lavoroTipo;
	}
	public void setLavoroTipo(CampoFseDTO lavoroTipo) {
		this.lavoroTipo = lavoroTipo;
	}
	public CampoFseDTO getLavoroOrario() {
		return lavoroOrario;
	}
	public void setLavoroOrario(CampoFseDTO lavoroOrario) {
		this.lavoroOrario = lavoroOrario;
	}
	public CampoFseDTO getAnnoTitoloStudio() {
		return annoTitoloStudio;
	}
	public void setAnnoTitoloStudio(CampoFseDTO annoTitoloStudio) {
		this.annoTitoloStudio = annoTitoloStudio;
	}
	public CampoFseDTO getInattivoAltroCorso() {
		return inattivoAltroCorso;
	}
	public void setInattivoAltroCorso(CampoFseDTO inattivoAltroCorso) {
		this.inattivoAltroCorso = inattivoAltroCorso;
	}
	public CampoFseDTO getPagIban() {
		return pagIban;
	}
	public void setPagIban(CampoFseDTO pagIban) {
		this.pagIban = pagIban;
	}
	public CampoFseDTO getPagResDom() {
		return pagResDom;
	}
	public void setPagResDom(CampoFseDTO pagResDom) {
		this.pagResDom = pagResDom;
	}
	public CampoFseDTO getDurataRicercaLavoro() {
		return durataRicercaLavoro;
	}
	public void setDurataRicercaLavoro(CampoFseDTO durataRicercaLavoro) {
		this.durataRicercaLavoro = durataRicercaLavoro;
	}
	public CampoFseDTO getSoggettoAttuatore() {
		return soggettoAttuatore;
	}
	public void setSoggettoAttuatore(CampoFseDTO soggettoAttuatore) {
		this.soggettoAttuatore = soggettoAttuatore;
	}
	public CampoFseDTO getDataSottoscrizione() {
		return dataSottoscrizione;
	}
	public void setDataSottoscrizione(CampoFseDTO dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}
	public boolean isRenderSezAzienda() {
		return this.azRagioneSociale.isAbilitato() || this.azCf.isAbilitato() || this.azPi.isAbilitato() || 
			   this.azVia.isAbilitato() || this.azComune.isAbilitato() || this.azCodAteco.isAbilitato() ||
			   this.azFormaGiuridica.isAbilitato() || this.azDimensione.isAbilitato();
	}
	
	public boolean isRenderSezLavoro(){
		return this.lavoroOrario.isAbilitato() || this.lavoroTipo.isAbilitato();
	}
	
	public boolean isRenderSezPagamento(){
		return this.pagIban.isAbilitato() || this.pagResDom.isAbilitato();
	}
}

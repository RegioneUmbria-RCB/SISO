package it.umbriadigitale.soclav.service.dto.gepi;

import java.io.Serializable;
import java.util.Date;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.DatiAnagrafici;

public class GePiBeneficiarioDTO extends DatiAnagrafici implements Serializable  {

	private String relazioneParentale;
	private String condOccupazionale;
	private String didFirmata;
	private String statoPattoLavoro;
	private String disabilita;
	private String statoBeneficio;
	private boolean convivente;
	private String titoloStudio;
	private String qualificaProfessionale;
	private String frequenzaCorsi;
	private String prestazioniINPS;
	private String titoloSoggiorno;
	private String naspi;
	private String periodoDisoccupazione;
	private String studente;
	
	private boolean richiedente;
	private Date ultimaModifica;
	
	//Dati elaborati
	private Boolean consensoRilasciato;
	private String codSAP;
	private boolean existsCartellaSociale;

	public Boolean getConsensoRilasciato() {
		return consensoRilasciato;
	}

	public void setConsensoRilasciato(Boolean consensoRilasciato) {
		this.consensoRilasciato = consensoRilasciato;
	}

	public String getRelazioneParentale() {
		return relazioneParentale;
	}

	public void setRelazioneParentale(String relazioneParentale) {
		this.relazioneParentale = relazioneParentale;
	}

	public String getCondOccupazionale() {
		return condOccupazionale;
	}

	public void setCondOccupazionale(String condOccupazionale) {
		this.condOccupazionale = condOccupazionale;
	}
	public String getStatoPattoLavoro() {
		return statoPattoLavoro;
	}

	public void setStatoPattoLavoro(String statoPattoLavoro) {
		this.statoPattoLavoro = statoPattoLavoro;
	}

	public String getDisabilita() {
		return disabilita;
	}

	public void setDisabilita(String disabilita) {
		this.disabilita = disabilita;
	}

	public String getStatoBeneficio() {
		return statoBeneficio;
	}

	public void setStatoBeneficio(String statoBeneficio) {
		this.statoBeneficio = statoBeneficio;
	}

	public boolean isConvivente() {
		return convivente;
	}

	public void setConvivente(boolean convivente) {
		this.convivente = convivente;
	}

	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}

	public String getQualificaProfessionale() {
		return qualificaProfessionale;
	}

	public void setQualificaProfessionale(String qualificaProfessionale) {
		this.qualificaProfessionale = qualificaProfessionale;
	}

	public String getFrequenzaCorsi() {
		return frequenzaCorsi;
	}

	public void setFrequenzaCorsi(String frequenzaCorsi) {
		this.frequenzaCorsi = frequenzaCorsi;
	}

	public String getPrestazioniINPS() {
		return prestazioniINPS;
	}

	public void setPrestazioniINPS(String prestazioniINPS) {
		this.prestazioniINPS = prestazioniINPS;
	}

	public String getTitoloSoggiorno() {
		return titoloSoggiorno;
	}

	public void setTitoloSoggiorno(String titoloSoggiorno) {
		this.titoloSoggiorno = titoloSoggiorno;
	}

	public String getNaspi() {
		return naspi;
	}

	public void setNaspi(String naspi) {
		this.naspi = naspi;
	}

	public String getPeriodoDisoccupazione() {
		return periodoDisoccupazione;
	}

	public void setPeriodoDisoccupazione(String periodoDisoccupazione) {
		this.periodoDisoccupazione = periodoDisoccupazione;
	}

	public String getStudente() {
		return studente;
	}

	public void setStudente(String studente) {
		this.studente = studente;
	}

	public Date getUltimaModifica() {
		return ultimaModifica;
	}

	public void setUltimaModifica(Date ultimaModifica) {
		this.ultimaModifica = ultimaModifica;
	}

	public String getDidFirmata() {
		return didFirmata;
	}

	public void setDidFirmata(String didFirmata) {
		this.didFirmata = didFirmata;
	}

	public boolean isRichiedente() {
		return richiedente;
	}

	public void setRichiedente(boolean richiedente) {
		this.richiedente = richiedente;
	}

	public boolean isExistsCartellaSociale() {
		return existsCartellaSociale;
	}

	public void setExistsCartellaSociale(boolean existsCartellaSociale) {
		this.existsCartellaSociale = existsCartellaSociale;
	}

	public String getCodSAP() {
		return codSAP;
	}

	public void setCodSAP(String codSAP) {
		this.codSAP = codSAP;
	}
	
}

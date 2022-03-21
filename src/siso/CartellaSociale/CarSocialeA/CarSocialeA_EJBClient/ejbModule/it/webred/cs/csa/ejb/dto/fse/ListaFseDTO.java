package it.webred.cs.csa.ejb.dto.fse;

import java.io.Serializable;
import java.util.Date;

public class ListaFseDTO implements Serializable {
	
	private String identificativo;
	private String tipoFse;
	private String residenzaComuneDesc;
	private String domicilioComuneDesc;
	private String enteTitolare;
	
	//Campi tracciato
	private String progettoCod; //Codice MEMO attività
	private String cf;
	private String cognome;
	private String nome;
	private Date dataNascita;
	private String nascitaNazioneIstat;
	private String nascitaComuneIstat;
	private String nascitaComuneDesc;
	private String cittadinanzaCod;
	
	private String residenzaNazioneIstat;
	private String residenzaComuneIstat;
	private String residenzaIndirizzo;
	private String residenzaCap;
	
	private String domicilioComuneIstat;
	private String domicilioIndirizzo;
	private String domicilioCap;
	private String telefono;
	private String cellulare;
	private String email;
	private String titoloStudioCod;
	private String condOccupazioneCod;
	private String periodoDisoccupazione;
	private String grVulnerabile;
	private Date dataSottoscrizione;
	private String progettoDenominazione; //Allegato H
	private String soggettoAttuatore;
	
	public String getIdentificativo() {
		return identificativo;
	}
	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}
	public String getTipoFse() {
		return tipoFse;
	}
	public void setTipoFse(String tipoFse) {
		this.tipoFse = tipoFse;
	}
	public String getResidenzaComuneDesc() {
		return residenzaComuneDesc;
	}
	public void setResidenzaComuneDesc(String residenzaComuneDesc) {
		this.residenzaComuneDesc = residenzaComuneDesc;
	}
	public String getProgettoCod() {
		return progettoCod;
	}
	public void setProgettoCod(String progettoCod) {
		this.progettoCod = progettoCod;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getNascitaComuneIstat() {
		return nascitaComuneIstat;
	}
	public void setNascitaComuneIstat(String nascitaComuneIstat) {
		this.nascitaComuneIstat = nascitaComuneIstat;
	}
	public String getNascitaComuneDesc() {
		return nascitaComuneDesc;
	}
	public void setNascitaComuneDesc(String nascitaComuneDesc) {
		this.nascitaComuneDesc = nascitaComuneDesc;
	}
	public String getCittadinanzaCod() {
		return cittadinanzaCod;
	}
	public void setCittadinanzaCod(String cittadinanzaCod) {
		this.cittadinanzaCod = cittadinanzaCod;
	}
	public String getResidenzaComuneIstat() {
		return residenzaComuneIstat;
	}
	public void setResidenzaComuneIstat(String residenzaComuneIstat) {
		this.residenzaComuneIstat = residenzaComuneIstat;
	}
	public String getResidenzaIndirizzo() {
		return residenzaIndirizzo;
	}
	public void setResidenzaIndirizzo(String residenzaIndirizzo) {
		this.residenzaIndirizzo = residenzaIndirizzo;
	}
	public String getResidenzaCap() {
		return residenzaCap;
	}
	public void setResidenzaCap(String residenzaCap) {
		this.residenzaCap = residenzaCap;
	}
	public String getDomicilioComuneIstat() {
		return domicilioComuneIstat;
	}
	public void setDomicilioComuneIstat(String domicilioComuneIstat) {
		this.domicilioComuneIstat = domicilioComuneIstat;
	}
	public String getDomicilioIndirizzo() {
		return domicilioIndirizzo;
	}
	public void setDomicilioIndirizzo(String domicilioIndirizzo) {
		this.domicilioIndirizzo = domicilioIndirizzo;
	}
	public String getDomicilioCap() {
		return domicilioCap;
	}
	public void setDomicilioCap(String domicilioCap) {
		this.domicilioCap = domicilioCap;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTitoloStudioCod() {
		return titoloStudioCod;
	}
	public void setTitoloStudioCod(String titoloStudioCod) {
		this.titoloStudioCod = titoloStudioCod;
	}
	public String getCondOccupazioneCod() {
		return condOccupazioneCod;
	}
	public void setCondOccupazioneCod(String condOccupazioneCod) {
		this.condOccupazioneCod = condOccupazioneCod;
	}
	public String getPeriodoDisoccupazione() {
		return periodoDisoccupazione;
	}
	public void setPeriodoDisoccupazione(String periodoDisoccupazione) {
		this.periodoDisoccupazione = periodoDisoccupazione;
	}
	public String getGrVulnerabile() {
		return grVulnerabile;
	}
	public void setGrVulnerabile(String grVulnerabile) {
		this.grVulnerabile = grVulnerabile;
	}
	public Date getDataSottoscrizione() {
		return dataSottoscrizione;
	}
	public void setDataSottoscrizione(Date dataSottoscrizione) {
		this.dataSottoscrizione = dataSottoscrizione;
	}
	public String getProgettoDenominazione() {
		return progettoDenominazione;
	}
	public void setProgettoDenominazione(String progettoDenominazione) {
		this.progettoDenominazione = progettoDenominazione;
	}
	public String getSoggettoAttuatore() {
		return soggettoAttuatore;
	}
	public void setSoggettoAttuatore(String soggettoAttuatore) {
		this.soggettoAttuatore = soggettoAttuatore;
	}
	public String getDomicilioComuneDesc() {
		return domicilioComuneDesc;
	}
	public void setDomicilioComuneDesc(String domicilioComuneDesc) {
		this.domicilioComuneDesc = domicilioComuneDesc;
	}
	public String getDenominazione(){
		return cognome+" "+nome;
	}
	public String getResidenzaNazioneIstat() {
		return residenzaNazioneIstat;
	}
	public void setResidenzaNazioneIstat(String residenzaNazioneIstat) {
		this.residenzaNazioneIstat = residenzaNazioneIstat;
	}
	public String getNascitaNazioneIstat() {
		return nascitaNazioneIstat;
	}
	public void setNascitaNazioneIstat(String nascitaNazioneIstat) {
		this.nascitaNazioneIstat = nascitaNazioneIstat;
	}
	public String getEnteTitolare() {
		return enteTitolare;
	}
	public void setEnteTitolare(String enteTitolare) {
		this.enteTitolare = enteTitolare;
	}
	
}

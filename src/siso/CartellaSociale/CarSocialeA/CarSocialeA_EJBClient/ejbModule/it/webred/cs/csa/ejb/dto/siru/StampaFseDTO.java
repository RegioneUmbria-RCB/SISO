package it.webred.cs.csa.ejb.dto.siru;

import java.io.Serializable;

public class StampaFseDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cognome = "";
	private String nome = "";
	private String codiceFiscale = "";
	private String sesso="";
	private String cittadinanza = "";
	
	private String dataNascita = "";
	private String annoNascita = "";
	private String luogoNascita = "";
	
	private String viaResidenza = "";
			
	private String telefono = "";
	private String cellulare = "";
	private String email = "";
	
	private String capResidenza = "";
	private String siglaProvResidenza = "";
	private String comuneResidenza = "";
	private String domicilioCap = "";
	private String domicilioSiglaProv = "";
	
	private String domicilioComune = "";
	private String viaDomicilio = "";
	private Boolean comunicaVul = false;
	private String idVulnerabile = "";
	private String descrizioneVulnerabile = "";

	private String lavoroDurataRicerca = "";
	//private String descrizioneCondLavoro = "";
	private String titoloStudio = "";
	private String titoloStudioTooltip = "";
	private String ffProgettoDescrizione = "";
	private String codProgetto = "";
	private String codAttivita = "";
	private String condLavoro = ""; //Condizione ING MERCATO
	private boolean durataRicercaLavoro = false;
	
	private String dtSottoscrizione;
	private String soggettoAttuatore="";
	
	public String getTitoloStudio() {
		return titoloStudio;
	}

	public void setTitoloStudio(String s) {
		this.titoloStudio = s!=null?s:"";
	}

	public void setTelefono(String s) {
		this.telefono = s!=null?s:"";
	}

	public void setCellulare(String s) {
		this.cellulare = s!=null?s:"";
	}

	public void setEmail(String s) {
		this.email = s!=null?s:"";
	}

	public void setLuogoNascita(String s) {
		this.luogoNascita = s!=null?s:"";
	}

	public void setCapResidenza(String s) {
		this.capResidenza = s!=null?s:"";
	}

	public void setSiglaProvResidenza(String s) {
		this.siglaProvResidenza = s!=null?s:"";
	}

	public void setComuneResidenza(String s) {
		this.comuneResidenza = s!=null?s:"";
	}

	public void setDomicilioCap(String s) {
		this.domicilioCap = s!=null?s:"";
	}

	public void setDomicilioSiglaProv(String s) {
		this.domicilioSiglaProv = s!=null?s:"";
	}

	public void setDomicilioComune(String s) {
		this.domicilioComune = s!=null?s:"";
	}

	public void setViaDomicilio(String s) {
		this.viaDomicilio = s!=null?s:"";
	}

	public void setComunicaVul(Boolean comunicaVul) {
		this.comunicaVul = comunicaVul;
	}

	public void setIdVulnerabile(String s) {
		this.idVulnerabile = s!=null?s:"";
	}

	public void setDescrizioneVulnerabile(String s) {
		this.descrizioneVulnerabile = s!=null?s:"";
	}

	public void setLavoroDurataRicerca(String s) {
		this.lavoroDurataRicerca = s;
		//this.lavoroDurataRicerca = s!=null?s:"";
	}

/*	public void setDescrizioneCondLavoro(String s) {
		this.descrizioneCondLavoro = s!=null?s:"";
	}*/

	public void setFfProgettoDescrizione(String s) {
		this.ffProgettoDescrizione = s!=null?s:"";
	}

	public void setCodProgetto(String s) {
		this.codProgetto = s!=null?s:"";
	}

	public void setCodAttivita(String s) {
		this.codAttivita = s!=null?s:"";
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCellulare() {
		return cellulare;
	}

	public String getEmail() {
		return email;
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public String getCapResidenza() {
		return capResidenza;
	}

	public String getSiglaProvResidenza() {
		return siglaProvResidenza;
	}

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public String getDomicilioCap() {
		return domicilioCap;
	}

	public String getDomicilioSiglaProv() {
		return domicilioSiglaProv;
	}

	public String getDomicilioComune() {
		return domicilioComune;
	}

	public String getViaDomicilio() {
		return viaDomicilio;
	}

	public Boolean getComunicaVul() {
		return comunicaVul;
	}

	public String getIdVulnerabile() {
		return idVulnerabile;
	}

	public String getDescrizioneVulnerabile() {
		return descrizioneVulnerabile;
	}

	public String getLavoroDurataRicerca() {
		return lavoroDurataRicerca;
	}

/*	public String getDescrizioneCondLavoro() {
		return descrizioneCondLavoro;
	}*/

	public String getFfProgettoDescrizione() {
		return ffProgettoDescrizione;
	}

	public String getCodProgetto() {
		return codProgetto;
	}

	public String getCodAttivita() {
		return codAttivita;
	}

	public String getCondLavoro() {
		return condLavoro;
	}

	public boolean isDurataRicercaLavoro() {
		return durataRicercaLavoro;
	}

	public void setCondLavoro(String s) {
		this.condLavoro = s!=null?s:"";
	}

	public void setDurataRicercaLavoro(boolean durataRicercaLavoro) {
		this.durataRicercaLavoro = durataRicercaLavoro;
	}

	public String getViaResidenza() {
		return viaResidenza;
	}

	public void setViaResidenza(String viaResidenza) {
		this.viaResidenza = viaResidenza;
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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public String getAnnoNascita() {
		return annoNascita;
	}

	public void setAnnoNascita(String annoNascita) {
		this.annoNascita = annoNascita;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getDtSottoscrizione() {
		return dtSottoscrizione;
	}

	public void setDtSottoscrizione(String dtSottoscrizione) {
		this.dtSottoscrizione = dtSottoscrizione;
	}

	public String getSoggettoAttuatore() {
		return soggettoAttuatore;
	}

	public void setSoggettoAttuatore(String soggettoAttuatore) {
		this.soggettoAttuatore = soggettoAttuatore;
	}

	public String getTitoloStudioTooltip() {
		return titoloStudioTooltip;
	}

	public void setTitoloStudioTooltip(String titoloStudioTooltip) {
		this.titoloStudioTooltip = titoloStudioTooltip;
	}
	
}

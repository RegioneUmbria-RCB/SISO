package it.webred.cs.json.valSinba.ver1.tabs;

import it.webred.cs.data.model.ArTbPrestazioniInps;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiGeneraliBean {

	private Date dataRiferimentoValutazione;
	private String codiceAnonimoBeneficiario;
	private String annoNascita;
	private String regioneResidenzaBeneficiario;
	private String nazioneResidenzaBeneficiario;

	private int scuolaFrequentata;
	private int condizioneLavoro;
	
	private int numeroComponentiISEE;
	private int fasciaEtaBeneficiario;
	private int fasciaISEEBeneficiario;
	
	// Lista prestazioni aggiunte ??
	private List<String> prestazioniSel = new ArrayList<String>();
	
	@JsonIgnore private String codicePrestazione;
//	@JsonIgnore private List<String> lstNazioneResidenza;
//	@JsonIgnore private List<String> lstRegioneResidenza;
	@JsonIgnore private List<ArTbPrestazioniInps> lstPrestazioni;
	
	
	public void aggiungiPrestazione(){
		
		if(codicePrestazione != null && !"".equals(codicePrestazione) && !prestazioniSel.contains(codicePrestazione) )
		{
			prestazioniSel.add(codicePrestazione);
			codicePrestazione = "";
		}
	}
	
	public void rimuoviPrestazione(String codice){
		if(codice != null && !"".equals(codice) )
		{
			prestazioniSel.remove(codice);
		}
		
	}

	public List<ArTbPrestazioniInps> getLstPrestazioni() {
		return lstPrestazioni;
	}



	public void setLstPrestazioni(List<ArTbPrestazioniInps> lstPrestazioni) {
		this.lstPrestazioni = lstPrestazioni;
	}



	public String getCodicePrestazione() {
		return codicePrestazione;
	}



	public void setCodicePrestazione(String codicePrestazione) {
		this.codicePrestazione = codicePrestazione;
	}



	public Date getDataRiferimentoValutazione() {
		return dataRiferimentoValutazione;
	}

	public void setDataRiferimentoValutazione(Date dataRiferimentoValutazione) {
		if(dataRiferimentoValutazione!=null)
			this.dataRiferimentoValutazione = new Date(dataRiferimentoValutazione.getTime());
		else
			this.dataRiferimentoValutazione = dataRiferimentoValutazione;
	}

	public String getCodiceAnonimoBeneficiario() {
		return codiceAnonimoBeneficiario;
	}

	public void setCodiceAnonimoBeneficiario(String codiceAnonimoBeneficiario) {
		this.codiceAnonimoBeneficiario = codiceAnonimoBeneficiario;
	}

	

	public String getAnnoNascita() {
		return annoNascita;
	}


	public void setAnnoNascita(String annoNascita) {
		this.annoNascita = annoNascita;
	}


	
	public String getNazioneResidenzaBeneficiario() {
		return nazioneResidenzaBeneficiario;
	}


	public void setNazioneResidenzaBeneficiario(String nazioneResidenzaBeneficiario) {
		this.nazioneResidenzaBeneficiario = nazioneResidenzaBeneficiario;
	}


	public int getScuolaFrequentata() {
		return scuolaFrequentata;
	}


	public void setScuolaFrequentata(int scuolaFrequentata) {
		this.scuolaFrequentata = scuolaFrequentata;
	}


	public int getCondizioneLavoro() {
		return condizioneLavoro;
	}


	public void setCondizioneLavoro(int condizioneLavoro) {
		this.condizioneLavoro = condizioneLavoro;
	}


	public int getNumeroComponentiISEE() {
		return numeroComponentiISEE;
	}


	public void setNumeroComponentiISEE(int numeroComponentiISEE) {
		this.numeroComponentiISEE = numeroComponentiISEE;
	}


	public int getFasciaEtaBeneficiario() {
		return fasciaEtaBeneficiario;
	}


	public void setFasciaEtaBeneficiario(int fasciaEtaBeneficiario) {
		this.fasciaEtaBeneficiario = fasciaEtaBeneficiario;
	}


	public int getFasciaISEEBeneficiario() {
		return fasciaISEEBeneficiario;
	}


	public void setFasciaISEEBeneficiario(int fasciaISEEBeneficiario) {
		this.fasciaISEEBeneficiario = fasciaISEEBeneficiario;
	}


	public List<String> getPrestazioniSel() {
		return prestazioniSel;
	}

	public void setPrestazioniSel(List<String> prestazioniSel) {
		this.prestazioniSel = prestazioniSel;
	}

	public String getRegioneResidenzaBeneficiario() {
		return regioneResidenzaBeneficiario;
	}

	public void setRegioneResidenzaBeneficiario(String regioneResidenzaBeneficiario) {
		this.regioneResidenzaBeneficiario = regioneResidenzaBeneficiario;
	}
	
}

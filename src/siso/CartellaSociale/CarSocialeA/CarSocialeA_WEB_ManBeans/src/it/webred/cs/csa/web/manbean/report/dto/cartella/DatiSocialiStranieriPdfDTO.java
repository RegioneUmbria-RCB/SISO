package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;

public class DatiSocialiStranieriPdfDTO extends StoricoPdfDTO {

	private String paeseOrigineNucleoFamiliare = EMPTY_VALUE;
	private boolean minoreStranieroNonAccompagnato;

	private boolean bambinoInEtaNonScolastica;
	private boolean attestatoConoscenzaLinguaItaliana;
	private String altreLingueConosciute = EMPTY_VALUE;

	private String attestatoItaLivello = EMPTY_VALUE;
	private String attestatoItaIstituto = EMPTY_VALUE;
	private String attestatoItaComune = EMPTY_VALUE;

	private Integer autovalutazioneComprensione;
	private Integer autovalutazioneParlato;
	private Integer autovalutazioneLettura;
	private Integer autovalutazioneScrittura;

	private String condizioneGiuridicaStatus = EMPTY_VALUE;

	private boolean condizioneGiuridicaPresenzaOltre3Mesi;

	private String condizioneGiuridicaPermessoStato = EMPTY_VALUE;
	private String condizioneGiuridicaPermessoTipo = EMPTY_VALUE;
	private String condizioneGiuridicaPermessoId = EMPTY_VALUE;
	private String condizioneGiuridicaPermessoScadenza = EMPTY_VALUE;

	private String arrivoInItaliaStatus = EMPTY_VALUE;
	private String arrivoInItaliaAnnoPrimoArrivo = EMPTY_VALUE;
	private boolean daSolo;
	private String arrivoInItaliaAnnoPrimoPermesso = EMPTY_VALUE;
	private String arrivoInItaliaValicoDiFrontiera = EMPTY_VALUE;
	private String arrivoInItaliaUltimoPaeseProvenienza = EMPTY_VALUE;

	private String arrivoInRegioneStatus = EMPTY_VALUE;
	private String arrivoInRegioneAnnoPrimoArrivo = EMPTY_VALUE;
	private boolean protezioneInternazionale;
	private String arrivoInRegioneComuneTitolareSprar = EMPTY_VALUE;
	private String arrivoInRegioneStrutturaAccoglienza = EMPTY_VALUE;
	private String arrivoInRegioneStrutturaAccoglienzaIndirizzo = EMPTY_VALUE;



	public String getPaeseOrigineNucleoFamiliare() {
		return paeseOrigineNucleoFamiliare;
	}



	public void setPaeseOrigineNucleoFamiliare(String paeseOrigineNucleoFamiliare) {
		this.paeseOrigineNucleoFamiliare = paeseOrigineNucleoFamiliare;
	}



	public boolean isMinoreStranieroNonAccompagnato() {
		return minoreStranieroNonAccompagnato;
	}



	public void setMinoreStranieroNonAccompagnato(boolean minoreStranieroNonAccompagnato) {
		this.minoreStranieroNonAccompagnato = minoreStranieroNonAccompagnato;
	}



	public boolean isBambinoInEtaNonScolastica() {
		return bambinoInEtaNonScolastica;
	}



	public void setBambinoInEtaNonScolastica(boolean bambinoInEtaNonScolastica) {
		this.bambinoInEtaNonScolastica = bambinoInEtaNonScolastica;
	}



	public boolean isAttestatoConoscenzaLinguaItaliana() {
		return attestatoConoscenzaLinguaItaliana;
	}



	public void setAttestatoConoscenzaLinguaItaliana(boolean attestatoConoscenzaLinguaItaliana) {
		this.attestatoConoscenzaLinguaItaliana = attestatoConoscenzaLinguaItaliana;
	}



	public String getAttestatoItaLivello() {
		return attestatoItaLivello;
	}



	public void setAttestatoItaLivello(String attestatoItaLivello) {
		this.attestatoItaLivello = attestatoItaLivello;
	}



	public String getAttestatoItaIstituto() {
		return attestatoItaIstituto;
	}



	public void setAttestatoItaIstituto(String attestatoItaIstituto) {
		this.attestatoItaIstituto = attestatoItaIstituto;
	}



	public String getAttestatoItaComune() {
		return attestatoItaComune;
	}



	public void setAttestatoItaComune(String attestatoItaComune) {
		this.attestatoItaComune = attestatoItaComune;
	}


	public Integer getAutovalutazioneComprensione() {
		return autovalutazioneComprensione;
	}



	public Integer getAutovalutazioneParlato() {
		return autovalutazioneParlato;
	}



	public Integer getAutovalutazioneLettura() {
		return autovalutazioneLettura;
	}



	public Integer getAutovalutazioneScrittura() {
		return autovalutazioneScrittura;
	}



	public void setAutovalutazioneComprensione(Integer autovalutazioneComprensione) {
		this.autovalutazioneComprensione = autovalutazioneComprensione;
	}



	public void setAutovalutazioneParlato(Integer autovalutazioneParlato) {
		this.autovalutazioneParlato = autovalutazioneParlato;
	}



	public void setAutovalutazioneLettura(Integer autovalutazioneLettura) {
		this.autovalutazioneLettura = autovalutazioneLettura;
	}



	public void setAutovalutazioneScrittura(Integer autovalutazioneScrittura) {
		this.autovalutazioneScrittura = autovalutazioneScrittura;
	}



	public String getCondizioneGiuridicaStatus() {
		return condizioneGiuridicaStatus;
	}



	public void setCondizioneGiuridicaStatus(String condizioneGiuridicaStatus) {
		this.condizioneGiuridicaStatus = condizioneGiuridicaStatus;
	}



	public boolean isCondizioneGiuridicaPresenzaOltre3Mesi() {
		return condizioneGiuridicaPresenzaOltre3Mesi;
	}



	public void setCondizioneGiuridicaPresenzaOltre3Mesi(boolean condizioneGiuridicaPresenzaOltre3Mesi) {
		this.condizioneGiuridicaPresenzaOltre3Mesi = condizioneGiuridicaPresenzaOltre3Mesi;
	}



	public String getCondizioneGiuridicaPermessoStato() {
		return condizioneGiuridicaPermessoStato;
	}



	public void setCondizioneGiuridicaPermessoStato(String condizioneGiuridicaPermessoStato) {
		this.condizioneGiuridicaPermessoStato = condizioneGiuridicaPermessoStato;
	}



	public String getCondizioneGiuridicaPermessoTipo() {
		return condizioneGiuridicaPermessoTipo;
	}



	public void setCondizioneGiuridicaPermessoTipo(String condizioneGiuridicaPermessoTipo) {
		this.condizioneGiuridicaPermessoTipo = condizioneGiuridicaPermessoTipo;
	}



	public String getCondizioneGiuridicaPermessoId() {
		return condizioneGiuridicaPermessoId;
	}



	public void setCondizioneGiuridicaPermessoId(String condizioneGiuridicaPermessoId) {
		this.condizioneGiuridicaPermessoId = condizioneGiuridicaPermessoId;
	}



	public String getCondizioneGiuridicaPermessoScadenza() {
		return condizioneGiuridicaPermessoScadenza;
	}



	public void setCondizioneGiuridicaPermessoScadenza(String condizioneGiuridicaPermessoScadenza) {
		this.condizioneGiuridicaPermessoScadenza = condizioneGiuridicaPermessoScadenza;
	}



	public String getArrivoInItaliaStatus() {
		return arrivoInItaliaStatus;
	}



	public void setArrivoInItaliaStatus(String arrivoInItaliaStatus) {
		this.arrivoInItaliaStatus = arrivoInItaliaStatus;
	}



	public String getArrivoInItaliaAnnoPrimoArrivo() {
		return arrivoInItaliaAnnoPrimoArrivo;
	}



	public void setArrivoInItaliaAnnoPrimoArrivo(String arrivoInItaliaAnnoPrimoArrivo) {
		this.arrivoInItaliaAnnoPrimoArrivo = arrivoInItaliaAnnoPrimoArrivo;
	}



	public boolean isDaSolo() {
		return daSolo;
	}



	public void setDaSolo(boolean daSolo) {
		this.daSolo = daSolo;
	}



	public String getArrivoInItaliaAnnoPrimoPermesso() {
		return arrivoInItaliaAnnoPrimoPermesso;
	}



	public void setArrivoInItaliaAnnoPrimoPermesso(String arrivoInItaliaAnnoPrimoPermesso) {
		this.arrivoInItaliaAnnoPrimoPermesso = arrivoInItaliaAnnoPrimoPermesso;
	}



	public String getArrivoInItaliaValicoDiFrontiera() {
		return arrivoInItaliaValicoDiFrontiera;
	}



	public void setArrivoInItaliaValicoDiFrontiera(String arrivoInItaliaValicoDiFrontiera) {
		this.arrivoInItaliaValicoDiFrontiera = arrivoInItaliaValicoDiFrontiera;
	}



	public String getArrivoInItaliaUltimoPaeseProvenienza() {
		return arrivoInItaliaUltimoPaeseProvenienza;
	}



	public void setArrivoInItaliaUltimoPaeseProvenienza(String arrivoInItaliaUltimoPaeseProvenienza) {
		this.arrivoInItaliaUltimoPaeseProvenienza = arrivoInItaliaUltimoPaeseProvenienza;
	}



	public String getArrivoInRegioneStatus() {
		return arrivoInRegioneStatus;
	}



	public void setArrivoInRegioneStatus(String arrivoInRegioneStatus) {
		this.arrivoInRegioneStatus = arrivoInRegioneStatus;
	}



	public String getArrivoInRegioneAnnoPrimoArrivo() {
		return arrivoInRegioneAnnoPrimoArrivo;
	}



	public void setArrivoInRegioneAnnoPrimoArrivo(String arrivoInRegioneAnnoPrimoArrivo) {
		this.arrivoInRegioneAnnoPrimoArrivo = arrivoInRegioneAnnoPrimoArrivo;
	}



	public boolean isProtezioneInternazionale() {
		return protezioneInternazionale;
	}



	public void setProtezioneInternazionale(boolean protezioneInternazionale) {
		this.protezioneInternazionale = protezioneInternazionale;
	}



	public String getArrivoInRegioneComuneTitolareSprar() {
		return arrivoInRegioneComuneTitolareSprar;
	}



	public void setArrivoInRegioneComuneTitolareSprar(String arrivoInRegioneComuneTitolareSprar) {
		this.arrivoInRegioneComuneTitolareSprar = arrivoInRegioneComuneTitolareSprar;
	}



	public String getArrivoInRegioneStrutturaAccoglienza() {
		return arrivoInRegioneStrutturaAccoglienza;
	}



	public void setArrivoInRegioneStrutturaAccoglienza(String arrivoInRegioneStrutturaAccoglienza) {
		this.arrivoInRegioneStrutturaAccoglienza = arrivoInRegioneStrutturaAccoglienza;
	}



	public String getArrivoInRegioneStrutturaAccoglienzaIndirizzo() {
		return arrivoInRegioneStrutturaAccoglienzaIndirizzo;
	}



	public void setArrivoInRegioneStrutturaAccoglienzaIndirizzo(String arrivoInRegioneStrutturaAccoglienzaIndirizzo) {
		this.arrivoInRegioneStrutturaAccoglienzaIndirizzo = arrivoInRegioneStrutturaAccoglienzaIndirizzo;
	}



	public String getAltreLingueConosciute() {
		return altreLingueConosciute;
	}



	public void setAltreLingueConosciute(String altreLingueConosciute) {
		this.altreLingueConosciute = altreLingueConosciute;
	}

}

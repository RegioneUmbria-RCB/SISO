package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.ArTbPrestazioniInps;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SinbaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
//	private String dataValutazione;
	private String codiceAnonimoBeneficiario;
	private String annoNascita;
	private String codRegioneResidenza;
	private String codNazioneResidenza;
	
	//private String codNazioneResidenza;
	
	private String minoreStranieroNonAccompagnato;
	private String condizioneMinore;
	private String luogoVita;
	private String scuolaFrequentata;
	private String condizioneLavoro;

	private String disabile;
	private String tipoDisabilita;
	private String invCivCertificazioni;
	
	//private String codNazioneResidenzaFam;
	//private String codRegioneResidenzaFam;
	private String cittadinanzaMadre;
	private String cittadinanzaPadre;
	private String regioneResidenzaMadre;
	private String regioneResidenzaPadre;
	private String titoloStudioMadre;
	private String titoloStudioPadre;
	private String occupazioneMadre;
	private String occupazionePadre;
	
	private String dataPrimaSegnalazione;
	private String fonteSegnalazione;
	private String valutazioneMinore;
	private String valutazioneFamigliaMinore;
	private String segnalazioneAutoritaGiudiziaria;
	private String dataSegnalazione;
	private String provvedimentoGiudiziario;
	private String dataProvvedimentoGiudiziario;
	private String autoritaProvvedimentoGiudiziario;
	private String potestaTutela;
	private String tipoProvvedimento;
	private String formaInterventoAffido;
	private String tipoInterventoAffido;
	private String durataAffido;
	private String carattereAffido;
	private String esitoAffido;
	private String carattereInserimentoResidenziale;
	private String formaInserimentoResidenziale;
	private String tipoInserimentoResidenziale;
	private String esitoInserimentoStruttura;
	private String motivazioneChiusuraCarico;
	private String situazioneChiusuraCarico;
	private String collaborazioneInterventi;
	
	private String codicePrestazione;
	
	private String numeroCompIsee;
	private String fasciaEtaBeneficiario;
	private String fasciaIseeBeneficiario;
	
	// Lista prestazioni aggiunte ??
	private List<String> prestazioniSel=new ArrayList<String>();
	private List<String> componentiFamigliaSel=new ArrayList<String>();
	
	//private List<String> lstNazioneResidenza;
	
	private List<ArTbPrestazioniInps> lstPrestazioni;
	
	/*
	 * public String getDataValutazione() { return dataValutazione; } public void
	 * setDataValutazione(String dataRiferimentoValutazione) { this.dataValutazione
	 * = dataRiferimentoValutazione; }
	 */
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
	public String getScuolaFrequentata() {
		return scuolaFrequentata;
	}
	public void setScuolaFrequentata(String scuolaFrequentata) {
		this.scuolaFrequentata = scuolaFrequentata;
	}
	public String getCondizioneLavoro() {
		return condizioneLavoro;
	}
	public void setCondizioneLavoro(String condizioneLavoro) {
		this.condizioneLavoro = condizioneLavoro;
	}
	public String getFasciaEtaBeneficiario() {
		return fasciaEtaBeneficiario;
	}
	public void setFasciaEtaBeneficiario(String fasciaEtaBeneficiario) {
		this.fasciaEtaBeneficiario = fasciaEtaBeneficiario;
	}
	public String getCodicePrestazione() {
		return codicePrestazione;
	}
	public void setCodicePrestazione(String codicePrestazione) {
		this.codicePrestazione = codicePrestazione;
	}
	public List<String> getPrestazioniSel() {
		return prestazioniSel;
	}
	public void setPrestazioniSel(List<String> prestazioniSel) {
		this.prestazioniSel = prestazioniSel;
	}
	public List<ArTbPrestazioniInps> getLstPrestazioni() {
		return lstPrestazioni;
	}
	public void setLstPrestazioni(List<ArTbPrestazioniInps> lstPrestazioni) {
		this.lstPrestazioni = lstPrestazioni;
	}
	public List<String> getComponentiFamigliaSel() {
		return componentiFamigliaSel;
	}
	public void setComponentiFamigliaSel(List<String> componentiFamigliaSel) {
		this.componentiFamigliaSel = componentiFamigliaSel;
	}
	public String getNumeroCompIsee() {
		return numeroCompIsee;
	}
	public void setNumeroCompIsee(String numeroCompIsee) {
		this.numeroCompIsee = numeroCompIsee;
	}
	public String getFasciaIseeBeneficiario() {
		return fasciaIseeBeneficiario;
	}
	public void setFasciaIseeBeneficiario(String fasciaIseeBeneficiario) {
		this.fasciaIseeBeneficiario = fasciaIseeBeneficiario;
	}
	public String getCondizioneMinore() {
		return condizioneMinore;
	}
	public void setCondizioneMinore(String condizioneMinore) {
		this.condizioneMinore = condizioneMinore;
	}
	public String getLuogoVita() {
		return luogoVita;
	}
	public void setLuogoVita(String luogoVita) {
		this.luogoVita = luogoVita;
	}
	
	public String getMinoreStranieroNonAccompagnato() {
		return minoreStranieroNonAccompagnato;
	}
	public void setMinoreStranieroNonAccompagnato(String minoreStranieroNonAccompagnato) {
		this.minoreStranieroNonAccompagnato = minoreStranieroNonAccompagnato;
	}
	public String getCittadinanzaMadre() {
		return cittadinanzaMadre;
	}
	public void setCittadinanzaMadre(String cittadinanzaMadre) {
		this.cittadinanzaMadre = cittadinanzaMadre;
	}
	public String getCittadinanzaPadre() {
		return cittadinanzaPadre;
	}
	public void setCittadinanzaPadre(String cittadinanzaPadre) {
		this.cittadinanzaPadre = cittadinanzaPadre;
	}
	public String getRegioneResidenzaMadre() {
		return regioneResidenzaMadre;
	}
	public void setRegioneResidenzaMadre(String regioneResidenzaMadre) {
		this.regioneResidenzaMadre = regioneResidenzaMadre;
	}
	public String getRegioneResidenzaPadre() {
		return regioneResidenzaPadre;
	}
	public void setRegioneResidenzaPadre(String regioneResidenzaPadre) {
		this.regioneResidenzaPadre = regioneResidenzaPadre;
	}
	public String getTitoloStudioMadre() {
		return titoloStudioMadre;
	}
	public void setTitoloStudioMadre(String titoloStudioMadre) {
		this.titoloStudioMadre = titoloStudioMadre;
	}
	public String getTitoloStudioPadre() {
		return titoloStudioPadre;
	}
	public void setTitoloStudioPadre(String titoloStudioPadre) {
		this.titoloStudioPadre = titoloStudioPadre;
	}
	public String getOccupazioneMadre() {
		return occupazioneMadre;
	}
	public void setOccupazioneMadre(String occupazioneMadre) {
		this.occupazioneMadre = occupazioneMadre;
	}
	public String getOccupazionePadre() {
		return occupazionePadre;
	}
	public void setOccupazionePadre(String occupazionePadre) {
		this.occupazionePadre = occupazionePadre;
	}
	public String getDisabile() {
		return disabile;
	}
	public void setDisabile(String disabile) {
		this.disabile = disabile;
	}
	public String getTipoDisabilita() {
		return tipoDisabilita;
	}
	public void setTipoDisabilita(String tipoDisabilita) {
		this.tipoDisabilita = tipoDisabilita;
	}
	public String getDataSegnalazione() {
		return dataSegnalazione;
	}
	public void setDataSegnalazione(String dataSegnalazione) {
		this.dataSegnalazione = dataSegnalazione;
	}
	public String getFonteSegnalazione() {
		return fonteSegnalazione;
	}
	public void setFonteSegnalazione(String fonteSegnalazione) {
		this.fonteSegnalazione = fonteSegnalazione;
	}
	public String getSegnalazioneAutoritaGiudiziaria() {
		return segnalazioneAutoritaGiudiziaria;
	}
	public void setSegnalazioneAutoritaGiudiziaria(
			String segnalazioneAutoritaGiudiziaria) {
		this.segnalazioneAutoritaGiudiziaria = segnalazioneAutoritaGiudiziaria;
	}
	public String getProvvedimentoGiudiziario() {
		return provvedimentoGiudiziario;
	}
	public void setProvvedimentoGiudiziario(String provvedimentoGiudiziario) {
		this.provvedimentoGiudiziario = provvedimentoGiudiziario;
	}
	public String getValutazioneMinore() {
		return valutazioneMinore;
	}
	public void setValutazioneMinore(String valutazioneMinore) {
		this.valutazioneMinore = valutazioneMinore;
	}
	public String getValutazioneFamigliaMinore() {
		return valutazioneFamigliaMinore;
	}
	public void setValutazioneFamigliaMinore(String valutazioneFamigliaMinore) {
		this.valutazioneFamigliaMinore = valutazioneFamigliaMinore;
	}
	public String getAutoritaProvvedimentoGiudiziario() {
		return autoritaProvvedimentoGiudiziario;
	}
	public void setAutoritaProvvedimentoGiudiziario(
			String autoritaProvvedimentoGiudiziario) {
		this.autoritaProvvedimentoGiudiziario = autoritaProvvedimentoGiudiziario;
	}
	public String getDataProvvedimentoGiudiziario() {
		return dataProvvedimentoGiudiziario;
	}
	public void setDataProvvedimentoGiudiziario(String dataProvvedimentoGiudiziario) {
		this.dataProvvedimentoGiudiziario = dataProvvedimentoGiudiziario;
	}
	public String getTipoProvvedimento() {
		return tipoProvvedimento;
	}
	public void setTipoProvvedimento(String tipoProvvedimento) {
		this.tipoProvvedimento = tipoProvvedimento;
	}
	public String getPotestaTutela() {
		return potestaTutela;
	}
	public void setPotestaTutela(String potestaTutela) {
		this.potestaTutela = potestaTutela;
	}
	public String getEsitoInserimentoStruttura() {
		return esitoInserimentoStruttura;
	}
	public void setEsitoInserimentoStruttura(String esitoInserimentoStruttura) {
		this.esitoInserimentoStruttura = esitoInserimentoStruttura;
	}
	public String getCollaborazioneInterventi() {
		return collaborazioneInterventi;
	}
	public void setCollaborazioneInterventi(String collaborazioneInterventi) {
		this.collaborazioneInterventi = collaborazioneInterventi;
	}
	public String getMotivazioneChiusuraCarico() {
		return motivazioneChiusuraCarico;
	}
	public void setMotivazioneChiusuraCarico(String motivazioneChiusuraCarico) {
		this.motivazioneChiusuraCarico = motivazioneChiusuraCarico;
	}
	public String getSituazioneChiusuraCarico() {
		return situazioneChiusuraCarico;
	}
	public void setSituazioneChiusuraCarico(String situazioneChiusuraCarico) {
		this.situazioneChiusuraCarico = situazioneChiusuraCarico;
	}
	public String getInvCivCertificazioni() {
		return invCivCertificazioni;
	}
	public void setInvCivCertificazioni(String invCivCertificazioni) {
		this.invCivCertificazioni = invCivCertificazioni;
	}
	public String getDataPrimaSegnalazione() {
		return dataPrimaSegnalazione;
	}
	public void setDataPrimaSegnalazione(String dataPrimaSegnalazione) {
		this.dataPrimaSegnalazione = dataPrimaSegnalazione;
	}
	public String getFormaInterventoAffido() {
		return formaInterventoAffido;
	}
	public void setFormaInterventoAffido(String formaInterventoAffido) {
		this.formaInterventoAffido = formaInterventoAffido;
	}
	public String getTipoInterventoAffido() {
		return tipoInterventoAffido;
	}
	public void setTipoInterventoAffido(String tipoInterventoAffido) {
		this.tipoInterventoAffido = tipoInterventoAffido;
	}
	public String getDurataAffido() {
		return durataAffido;
	}
	public void setDurataAffido(String durataAffido) {
		this.durataAffido = durataAffido;
	}
	public String getCarattereAffido() {
		return carattereAffido;
	}
	public void setCarattereAffido(String carattereAffido) {
		this.carattereAffido = carattereAffido;
	}
	public String getEsitoAffido() {
		return esitoAffido;
	}
	public void setEsitoAffido(String esitoAffido) {
		this.esitoAffido = esitoAffido;
	}
	public String getCarattereInserimentoResidenziale() {
		return carattereInserimentoResidenziale;
	}
	public void setCarattereInserimentoResidenziale(String carattereInserimentoResidenziale) {
		this.carattereInserimentoResidenziale = carattereInserimentoResidenziale;
	}
	public String getFormaInserimentoResidenziale() {
		return formaInserimentoResidenziale;
	}
	public void setFormaInserimentoResidenziale(String formaInserimentoResidenziale) {
		this.formaInserimentoResidenziale = formaInserimentoResidenziale;
	}
	public String getTipoInserimentoResidenziale() {
		return tipoInserimentoResidenziale;
	}
	public void setTipoInserimentoResidenziale(String tipoInserimentoResidenziale) {
		this.tipoInserimentoResidenziale = tipoInserimentoResidenziale;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCodRegioneResidenza() {
		return codRegioneResidenza;
	}
	public void setCodRegioneResidenza(String codRegioneResidenza) {
		this.codRegioneResidenza = codRegioneResidenza;
	}
	public String getCodNazioneResidenza() {
		return codNazioneResidenza;
	}
	public void setCodNazioneResidenza(String codNazioneResidenza) {
		this.codNazioneResidenza = codNazioneResidenza;
	}
	
}

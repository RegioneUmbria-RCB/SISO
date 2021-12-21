package it.webred.cs.csa.ejb.dto;


public class CsDRelSalDTO {

	private Long relazioneId;
	
    //Valutazione con servizio inviante
	private Boolean consulenza;
	private Boolean presentazioneSituazione;
	private Boolean condivisionePercorsoSsal;
	private Boolean verifica;
	private Boolean riProgettazione;
	//Orientamento
	private Boolean ricercaContestoIdoneo;
	private Boolean ricercaOpportunita;
	private Boolean colloquio;
	private Boolean bilancioRisorse;
	private Boolean bilancioCompetenze;
	private Boolean counselingPsicosociale;
	private Boolean redazioneCondPrgPers;
	private Boolean ricercaAttivaLavoro;
	
	//Mediazione
	private Boolean azioniContestoOspitante;
	private Boolean protocolloContesto;
	private Boolean tutoraggioAccompagnamento;
	private Boolean monitoraggio;
	private Boolean costrEsperienzaSituazione;
	private Boolean verificaAnalisiAndamento;
	private Boolean gruppoConfrUtentiPrg;
	private Boolean sostegnoLavoratoriOccupati;
	private Boolean attivitaChiusuraPercorso;

	
	private Boolean rifiutoAttUtente;

	private byte[] docVerbale;

	private String note;
	
	public Long getRelazioneId() {
		return relazioneId;
	}

	public void setRelazioneId(Long relazioneId) {
		this.relazioneId = relazioneId;
	}

	public Boolean getAttivitaChiusuraPercorso() {
		return attivitaChiusuraPercorso;
	}

	public void setAttivitaChiusuraPercorso(Boolean attivitaChiusuraPercorso) {
		this.attivitaChiusuraPercorso = attivitaChiusuraPercorso;
	}

	public Boolean getAzioniContestoOspitante() {
		return azioniContestoOspitante;
	}

	public void setAzioniContestoOspitante(Boolean azioniContestoOspitante) {
		this.azioniContestoOspitante = azioniContestoOspitante;
	}

	public Boolean getBilancioCompetenze() {
		return bilancioCompetenze;
	}

	public void setBilancioCompetenze(Boolean bilancioCompetenze) {
		this.bilancioCompetenze = bilancioCompetenze;
	}

	public Boolean getBilancioRisorse() {
		return bilancioRisorse;
	}

	public void setBilancioRisorse(Boolean bilancioRisorse) {
		this.bilancioRisorse = bilancioRisorse;
	}

	public Boolean getColloquio() {
		return colloquio;
	}

	public void setColloquio(Boolean colloquio) {
		this.colloquio = colloquio;
	}

	public Boolean getCondivisionePercorsoSsal() {
		return condivisionePercorsoSsal;
	}

	public void setCondivisionePercorsoSsal(Boolean condivisionePercorsoSsal) {
		this.condivisionePercorsoSsal = condivisionePercorsoSsal;
	}

	public Boolean getConsulenza() {
		return consulenza;
	}

	public void setConsulenza(Boolean consulenza) {
		this.consulenza = consulenza;
	}

	public Boolean getCostrEsperienzaSituazione() {
		return costrEsperienzaSituazione;
	}

	public void setCostrEsperienzaSituazione(Boolean costrEsperienzaSituazione) {
		this.costrEsperienzaSituazione = costrEsperienzaSituazione;
	}

	public Boolean getCounselingPsicosociale() {
		return counselingPsicosociale;
	}

	public void setCounselingPsicosociale(Boolean counselingPsicosociale) {
		this.counselingPsicosociale = counselingPsicosociale;
	}

	public byte[] getDocVerbale() {
		return docVerbale;
	}

	public void setDocVerbale(byte[] docVerbale) {
		this.docVerbale = docVerbale;
	}

	public Boolean getGruppoConfrUtentiPrg() {
		return gruppoConfrUtentiPrg;
	}

	public void setGruppoConfrUtentiPrg(Boolean gruppoConfrUtentiPrg) {
		this.gruppoConfrUtentiPrg = gruppoConfrUtentiPrg;
	}

	public Boolean getMonitoraggio() {
		return monitoraggio;
	}

	public void setMonitoraggio(Boolean monitoraggio) {
		this.monitoraggio = monitoraggio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Boolean getPresentazioneSituazione() {
		return presentazioneSituazione;
	}

	public void setPresentazioneSituazione(Boolean presentazioneSituazione) {
		this.presentazioneSituazione = presentazioneSituazione;
	}

	public Boolean getProtocolloContesto() {
		return protocolloContesto;
	}

	public void setProtocolloContesto(Boolean protocolloContesto) {
		this.protocolloContesto = protocolloContesto;
	}

	public Boolean getRedazioneCondPrgPers() {
		return redazioneCondPrgPers;
	}

	public void setRedazioneCondPrgPers(Boolean redazioneCondPrgPers) {
		this.redazioneCondPrgPers = redazioneCondPrgPers;
	}

	public Boolean getRiProgettazione() {
		return riProgettazione;
	}

	public void setRiProgettazione(Boolean riProgettazione) {
		this.riProgettazione = riProgettazione;
	}

	public Boolean getRicercaAttivaLavoro() {
		return ricercaAttivaLavoro;
	}

	public void setRicercaAttivaLavoro(Boolean ricercaAttivaLavoro) {
		this.ricercaAttivaLavoro = ricercaAttivaLavoro;
	}

	public Boolean getRicercaContestoIdoneo() {
		return ricercaContestoIdoneo;
	}

	public void setRicercaContestoIdoneo(Boolean ricercaContestoIdoneo) {
		this.ricercaContestoIdoneo = ricercaContestoIdoneo;
	}

	public Boolean getRicercaOpportunita() {
		return ricercaOpportunita;
	}

	public void setRicercaOpportunita(Boolean ricercaOpportunita) {
		this.ricercaOpportunita = ricercaOpportunita;
	}

	public Boolean getRifiutoAttUtente() {
		return rifiutoAttUtente;
	}

	public void setRifiutoAttUtente(Boolean rifiutoAttUtente) {
		this.rifiutoAttUtente = rifiutoAttUtente;
	}

	public Boolean getSostegnoLavoratoriOccupati() {
		return sostegnoLavoratoriOccupati;
	}

	public void setSostegnoLavoratoriOccupati(Boolean sostegnoLavoratoriOccupati) {
		this.sostegnoLavoratoriOccupati = sostegnoLavoratoriOccupati;
	}

	public Boolean getTutoraggioAccompagnamento() {
		return tutoraggioAccompagnamento;
	}

	public void setTutoraggioAccompagnamento(Boolean tutoraggioAccompagnamento) {
		this.tutoraggioAccompagnamento = tutoraggioAccompagnamento;
	}

	public Boolean getVerifica() {
		return verifica;
	}

	public void setVerifica(Boolean verifica) {
		this.verifica = verifica;
	}

	public Boolean getVerificaAnalisiAndamento() {
		return verificaAnalisiAndamento;
	}

	public void setVerificaAnalisiAndamento(Boolean verificaAnalisiAndamento) {
		this.verificaAnalisiAndamento = verificaAnalisiAndamento;
	}
	
	
}

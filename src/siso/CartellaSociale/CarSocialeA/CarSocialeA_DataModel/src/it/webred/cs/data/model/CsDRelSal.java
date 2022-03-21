package it.webred.cs.data.model;

import javax.persistence.*;

import it.webred.cs.data.base.ICsDRelazioneChild;


/**
 * The persistent class for the CS_D_REL_SAL database table.
 * 
 */
@Entity
@Table(name="CS_D_REL_SAL")
@NamedQuery(name="CsDRelSal.findAll", query="SELECT c FROM CsDRelSal c")
public class CsDRelSal implements ICsDRelazioneChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="RELAZIONE_ID")
	private Long relazioneId;

	@Column(name="ATTIVITA_CHIUSURA_PERCORSO")
	private Boolean attivitaChiusuraPercorso;

	@Column(name="AZIONI_CONTESTO_OSPITANTE")
	private Boolean azioniContestoOspitante;

	@Column(name="BILANCIO_COMPETENZE")
	private Boolean bilancioCompetenze;

	@Column(name="BILANCIO_RISORSE")
	private Boolean bilancioRisorse;

	private Boolean colloquio;

	@Column(name="CONDIVISIONE_PERCORSO_SSAL")
	private Boolean condivisionePercorsoSsal;

	private Boolean consulenza;

	@Column(name="COSTR_ESPERIENZA_SITUAZIONE")
	private Boolean costrEsperienzaSituazione;

	@Column(name="COUNSELING_PSICOSOCIALE")
	private Boolean counselingPsicosociale;

	@Lob
	@Column(name="DOC_VERBALE")
	private byte[] docVerbale;

	@Column(name="GRUPPO_CONFR_UTENTI_PRG")
	private Boolean gruppoConfrUtentiPrg;

	private Boolean monitoraggio;

	private String note;

	@Column(name="PRESENTAZIONE_SITUAZIONE")
	private Boolean presentazioneSituazione;

	@Column(name="PROTOCOLLO_CONTESTO")
	private Boolean protocolloContesto;

	@Column(name="REDAZIONE_COND_PRG_PERS")
	private Boolean redazioneCondPrgPers;

	@Column(name="RI_PROGETTAZIONE")
	private Boolean riProgettazione;

	@Column(name="RICERCA_ATTIVA_LAVORO")
	private Boolean ricercaAttivaLavoro;

	@Column(name="RICERCA_CONTESTO_IDONEO")
	private Boolean ricercaContestoIdoneo;

	@Column(name="RICERCA_OPPORTUNITA")
	private Boolean ricercaOpportunita;

	@Column(name="RIFIUTO_ATT_UTENTE")
	private Boolean rifiutoAttUtente;

	@Column(name="SOSTEGNO_LAVORATORI_OCCUPATI")
	private Boolean sostegnoLavoratoriOccupati;

	@Column(name="TUTORAGGIO_ACCOMPAGNAMENTO")
	private Boolean tutoraggioAccompagnamento;

	private Boolean verifica;

	@Column(name="VERIFICA_ANALISI_ANDAMENTO")
	private Boolean verificaAnalisiAndamento;

	public CsDRelSal() {
	}

	public Long getRelazioneId() {
		return this.relazioneId;
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

	@Override
	public CsDRelazione getCsDRelazione() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCsDRelazione(CsDRelazione csDRelazione) {
		// TODO Auto-generated method stub
		
	}

	

}
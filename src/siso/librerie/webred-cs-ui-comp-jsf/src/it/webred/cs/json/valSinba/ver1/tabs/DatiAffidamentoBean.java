package it.webred.cs.json.valSinba.ver1.tabs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatiAffidamentoBean {
	
	private int formaIntervento;
	private int esito;
	private int tipoIntervento;
	private int durata;
	private int carattere;
	private int carattereInserimento;
	private int esitoInserimentoStruttura;
	private int collaborazioneInterventi;
	private int formaInserimento;
	private int motivazioneChiusuraCarico;
	private int tipoInserimento;
	private int situazioneChiusuraCarico;
	
	//SISO-981 Inizio
	private boolean datiDaProgettoPAI = false;
	private String messaggioDaPai = "";
	//SISO-981 Fine
	
	public DatiAffidamentoBean() {
		
	}


	public int getFormaIntervento() {
		return formaIntervento;
	}


	public void setFormaIntervento(int formaIntervento) {
		this.formaIntervento = formaIntervento;
	}


	public int getEsito() {
		return esito;
	}


	public void setEsito(int esito) {
		this.esito = esito;
	}


	public int getTipoIntervento() {
		return tipoIntervento;
	}


	public void setTipoIntervento(int tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}


	public int getDurata() {
		return durata;
	}


	public void setDurata(int durata) {
		this.durata = durata;
	}


	public int getCarattere() {
		return carattere;
	}


	public void setCarattere(int carattere) {
		this.carattere = carattere;
	}


	public int getCarattereInserimento() {
		return carattereInserimento;
	}


	public void setCarattereInserimento(int carattereInserimento) {
		this.carattereInserimento = carattereInserimento;
	}


	public int getEsitoInserimentoStruttura() {
		return esitoInserimentoStruttura;
	}


	public void setEsitoInserimentoStruttura(int esitoInserimentoStruttura) {
		this.esitoInserimentoStruttura = esitoInserimentoStruttura;
	}


	public int getCollaborazioneInterventi() {
		return collaborazioneInterventi;
	}


	public void setCollaborazioneInterventi(int collaborazioneInterventi) {
		this.collaborazioneInterventi = collaborazioneInterventi;
	}


	public int getFormaInserimento() {
		return formaInserimento;
	}


	public void setFormaInserimento(int formaInserimento) {
		this.formaInserimento = formaInserimento;
	}


	public int getMotivazioneChiusuraCarico() {
		return motivazioneChiusuraCarico;
	}


	public void setMotivazioneChiusuraCarico(int motivazioneChiusuraCarico) {
		this.motivazioneChiusuraCarico = motivazioneChiusuraCarico;
	}


	public int getTipoInserimento() {
		return tipoInserimento;
	}


	public void setTipoInserimento(int tipoInserimento) {
		this.tipoInserimento = tipoInserimento;
	}


	public int getSituazioneChiusuraCarico() {
		return situazioneChiusuraCarico;
	}


	public void setSituazioneChiusuraCarico(int situazioneChiusuraCarico) {
		this.situazioneChiusuraCarico = situazioneChiusuraCarico;
	}

	//SISO-981 Inizio
	public boolean isDatiDaProgettoPAI() {
		return datiDaProgettoPAI;
	}


	public void setDatiDaProgettoPAI(boolean datiDaProgettoPAI) {
		this.datiDaProgettoPAI = datiDaProgettoPAI;
	}


	public String getMessaggioDaPai() {
		return messaggioDaPai;
	}


	public void setMessaggioDaPai(String messaggioDaPai) {
		this.messaggioDaPai = messaggioDaPai;
	}
	//SISO-981 Fine
	
	
}

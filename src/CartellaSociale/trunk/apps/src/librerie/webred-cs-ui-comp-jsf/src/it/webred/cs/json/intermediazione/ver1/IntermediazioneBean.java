package it.webred.cs.json.intermediazione.ver1;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.jsf.bean.ComuneBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IntermediazioneBean extends JsonBaseBean {

	private String tipoIntermediazione;

	private RicercaAbitazione ricercaAbitazione;
	
	private String[] questione;
	private String questioneDettaglio;
	

	@Override
	public IntermediazioneBean autoClone() throws Exception{
		return (IntermediazioneBean)super.autoClone(); //TODO: Implementare, altrimenti vengono copiati i soli riferimenti
	}
	
	public String getTipoIntermediazione() {
		return tipoIntermediazione;
	}
	public void setTipoIntermediazione(String tipoIntermediazione) {
		this.tipoIntermediazione = tipoIntermediazione;
	}
	public String[] getQuestione() {
		return questione;
	}
	public String getQuestioneDettaglio() {
		return questioneDettaglio;
	}
	public void setQuestione(String[] questione) {
		this.questione = questione;
	}
	public void setQuestioneDettaglio(String questioneDettaglio) {
		this.questioneDettaglio = questioneDettaglio;
	}
	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		//TODO: Implementare controlli
		return messages;
	}
	public void resetDatiRichiestaAbitativa() {
		this.ricercaAbitazione=null;
	}
	public RicercaAbitazione getRicercaAbitazione() {
		return ricercaAbitazione;
	}
	public void setRicercaAbitazione(RicercaAbitazione ricercaAbitazione) {
		this.ricercaAbitazione = ricercaAbitazione;
	}
	
}

package it.webred.siso.ws.client.anag.marche.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RicercaPersonaResult implements Serializable{

	private List<PersonaResult> elencoAssisiti = new ArrayList<PersonaResult>();
    private String messaggio;
    private Integer codice;
    private Exception eccezione;
	
	public void setElencoAssisiti(List<PersonaResult> elencoAssisiti) {
		this.elencoAssisiti = elencoAssisiti;
	}

	public Exception getEccezione() {
		return eccezione;
	}

	public void setEccezione(Exception eccezione) {
		this.eccezione = eccezione;
	}

	public Integer getCodice() {
		return codice;
	}

	public void setCodice(Integer codice) {
		this.codice = codice;
	}

	public String getMessaggio() {
		return messaggio;
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	 

	public List<PersonaResult> getElencoAssisiti() {
		return elencoAssisiti;
	}
	
	public void addAssistito(PersonaResult personaResult){
		this.elencoAssisiti.add(personaResult);
	}

}

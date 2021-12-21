package it.webred.siso.ws.ricerca.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RicercaAnagraficaResult implements Serializable{

	private List<PersonaDettaglio> elencoAssistiti = new ArrayList<PersonaDettaglio>();
	private List<FamiliareDettaglio> elencoFamiliari = new ArrayList<FamiliareDettaglio>();
    private String messaggio;
    private Integer codice;
    private Exception eccezione;
	

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
	
	public void addAssistito(PersonaDettaglio personaResult){
		this.elencoAssistiti.add(personaResult);
	}

	public List<FamiliareDettaglio> getElencoFamiliari() {
		return elencoFamiliari;
	}

	public void setElencoFamiliari(List<FamiliareDettaglio> elencoFamiliari) {
		this.elencoFamiliari = elencoFamiliari;
	}

	public List<PersonaDettaglio> getElencoAssistiti() {
		return elencoAssistiti;
	}

	public void setElencoAssistiti(List<PersonaDettaglio> elencoAssistiti) {
		this.elencoAssistiti = elencoAssistiti;
	}
}

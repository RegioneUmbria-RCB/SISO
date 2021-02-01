package it.webred.siso.ws.ricerca.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RicercaAnagraficaResult implements Serializable{

	private List<PersonaDettaglio> elencoAssisiti = new ArrayList<PersonaDettaglio>();
	private List<FamiliareDettaglio> elencoFamiliari = new ArrayList<FamiliareDettaglio>();
    private String messaggio;
    private Integer codice;
    private Exception eccezione;
	
	public void setElencoAssisiti(List<PersonaDettaglio> elencoAssisiti) {
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

	public List<PersonaDettaglio> getElencoAssisiti() {
		return elencoAssisiti;
	}
	
	public void addAssistito(PersonaDettaglio personaResult){
		this.elencoAssisiti.add(personaResult);
	}

	public List<FamiliareDettaglio> getElencoFamiliari() {
		return elencoFamiliari;
	}

	public void setElencoFamiliari(List<FamiliareDettaglio> elencoFamiliari) {
		this.elencoFamiliari = elencoFamiliari;
	}

}

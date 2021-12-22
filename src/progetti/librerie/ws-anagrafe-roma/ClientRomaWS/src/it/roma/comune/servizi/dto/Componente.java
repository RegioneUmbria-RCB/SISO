package it.roma.comune.servizi.dto;

import java.io.Serializable;

public class Componente implements Serializable {
	
	private DatiAnagrafeRoma datiAnagrafeRoma;
	private PersonaCompleta personaCompleta;
	private String rapportoParentela;
	private Nascita nascita;
	
	 private java.lang.String codiceIndividuale;
	    
    public java.lang.String getCodiceIndividuale() {
		return codiceIndividuale;
	}

	public void setCodiceIndividuale(java.lang.String codiceIndividuale) {
		this.codiceIndividuale = codiceIndividuale;
	}
	
	public DatiAnagrafeRoma getDatiAnagrafeRoma() {
		return datiAnagrafeRoma;
	}
	public void setDatiAnagrafeRoma(DatiAnagrafeRoma datiAnagrafeRoma) {
		this.datiAnagrafeRoma = datiAnagrafeRoma;
	}
	public PersonaCompleta getPersonaCompleta() {
		return personaCompleta;
	}
	public void setPersonaCompleta(PersonaCompleta personaCompleta) {
		this.personaCompleta = personaCompleta;
	}
	public String getRapportoParentela() {
		return rapportoParentela;
	}
	public void setRapportoParentela(String rapportoParentela) {
		this.rapportoParentela = rapportoParentela;
	}
	public Nascita getNascita() {
		return nascita;
	}
	public void setNascita(Nascita nascita) {
		this.nascita = nascita;
	}
	
}

package it.roma.comune.servizi.dto;

public class Persona implements java.io.Serializable{

	
	
	private PersonaCompleta personaCompleta;

	private DatiAnagrafeRoma datiAnagrafeRoma;
	private Nascita datiDiNascita;
 	private DatiIndirizzo datiIndirizzo;
	public DatiAnagrafeRoma getDatiAnagrafeRoma() {
		return datiAnagrafeRoma;
	}
	public void setDatiAnagrafeRoma(DatiAnagrafeRoma datiAnagrafeRoma) {
		this.datiAnagrafeRoma = datiAnagrafeRoma;
	}
	public Nascita getDatiDiNascita() {
		return datiDiNascita;
	}
	public void setDatiDiNascita(Nascita datiDiNascita) {
		this.datiDiNascita = datiDiNascita;
	}
	 
	public DatiIndirizzo getDatiIndirizzo() {
		return datiIndirizzo;
	}
	public void setDatiIndirizzo(DatiIndirizzo datiIndirizzo) {
		this.datiIndirizzo = datiIndirizzo;
	}
	
	public PersonaCompleta getPersonaCompleta() {
		return personaCompleta;
	}
	public void setPersonaCompleta(PersonaCompleta personaCompleta) {
		this.personaCompleta = personaCompleta;
	}
}

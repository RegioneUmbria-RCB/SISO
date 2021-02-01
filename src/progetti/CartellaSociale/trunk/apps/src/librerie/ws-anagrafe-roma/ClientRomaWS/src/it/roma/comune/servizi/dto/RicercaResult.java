package it.roma.comune.servizi.dto;

 
  
public class RicercaResult  implements java.io.Serializable{
	
	public RicercaResult(){
		
	}
	private Famiglia famiglia;
	private Persona[]  elencoPersona;
	private String esito;
	public Persona[] getElencoPersona() {
		return elencoPersona;
	}
	public void setElencoPersona(Persona[] elencoPersona) {
		this.elencoPersona = elencoPersona;
	}
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public Famiglia getFamiglia() {
		return famiglia;
	}
	public void setFamiglia(Famiglia famiglia) {
		this.famiglia = famiglia;
	}
	
}

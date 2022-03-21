package it.webred.rulengine.brick.core;

public class BeanCivico {
	
	private String civico;
	private String indirizzo;
	private String flagPrincipale;
	
	public BeanCivico(){
		civico = "";
		indirizzo = "";
		flagPrincipale = "";
	}
	
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getFlagPrincipale() {
		return flagPrincipale;
	}
	public void setFlagPrincipale(String flagPrincipale) {
		this.flagPrincipale = flagPrincipale;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	

}

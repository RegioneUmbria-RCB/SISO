package it.webred.mui.model;

public class Familiare {

	public Familiare() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String toString(){
		return String.valueOf(codiceFiscale);
	}
	private String codiceFiscale;
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public boolean equals(Object obj){
		return ( this.codiceFiscale.equals( ((Familiare)obj).codiceFiscale));
	}

}

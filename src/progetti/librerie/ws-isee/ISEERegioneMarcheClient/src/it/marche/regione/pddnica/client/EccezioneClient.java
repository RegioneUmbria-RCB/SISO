package it.marche.regione.pddnica.client;

import java.io.Serializable;

public class EccezioneClient extends Exception implements Serializable {

	private int codice = 0;
	
	public EccezioneClient(String messaggio, Throwable causa, int codice) {
		super(causa);
		this.messaggio = messaggio;
		this.codice = codice;
	}
	
	public int getCodice() {
		return codice;
	}

 

	private String messaggio = "";

	public String getMessaggio() {
		return messaggio;
	}

 
	
	
}

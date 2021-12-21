package it.marche.regione.pddnica.client;

import java.io.Serializable;

import it.siso.isee.obj.SicurezzaIdentificazioneMittente;

public class DatiRichiestaISEE implements Serializable{

	private SicurezzaIdentificazioneMittente mittente;
	private String endPoint;
	private String action;
	
	
	public DatiRichiestaISEE() {
	
	}
	
	public SicurezzaIdentificazioneMittente getMittente() {
		return mittente;
	}
	public void setMittente(SicurezzaIdentificazioneMittente mittente) {
		this.mittente = mittente;
	}
	public String getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}

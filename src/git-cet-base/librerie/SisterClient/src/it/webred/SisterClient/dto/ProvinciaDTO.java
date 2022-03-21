package it.webred.SisterClient.dto;

import java.io.Serializable;

public class ProvinciaDTO implements Serializable {

	private String provincia;
	private String numFabbricati;
	private String numTerreni;
	
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getNumFabbricati() {
		return numFabbricati;
	}
	public void setNumFabbricati(String numFabbricati) {
		this.numFabbricati = numFabbricati;
	}
	public String getNumTerreni() {
		return numTerreni;
	}
	public void setNumTerreni(String numTerreni) {
		this.numTerreni = numTerreni;
	}

	
}

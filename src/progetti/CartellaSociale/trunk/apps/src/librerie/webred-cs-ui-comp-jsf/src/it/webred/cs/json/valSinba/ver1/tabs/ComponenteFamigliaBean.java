package it.webred.cs.json.valSinba.ver1.tabs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponenteFamigliaBean {
	
	private String tipo;
	private int tipoID;
	private String cittadinanza;
	private String cittadinanzaID;
	private String regione;
	private String regioneID;
	private String titoloStudio;
	private int titoloStudioID;
	private String occupazione;
	private int occupazioneID;
	
	@JsonIgnore 
	public boolean isNotNull()
	{
		if (
			this.tipoID != 0 &&
			this.cittadinanzaID != null && !"".equals(cittadinanzaID) &&
			this.regioneID != null && !"".equals(regioneID) &&
			this.titoloStudioID != 0 &&
			this.occupazioneID != 0
			)
			return true;
		return false;
			
	}
	
	public ComponenteFamigliaBean() {
		
	}

	public String getCittadinanzaID() {
		return cittadinanzaID;
	}



	public void setCittadinanzaID(String cittadinanzaID) {
		this.cittadinanzaID = cittadinanzaID;
	}



	public String getRegioneID() {
		return regioneID;
	}



	public void setRegioneID(String regioneID) {
		this.regioneID = regioneID;
	}



	public int getTitoloStudioID() {
		return titoloStudioID;
	}



	public void setTitoloStudioID(int titoloStudioID) {
		this.titoloStudioID = titoloStudioID;
	}



	public int getOccupazioneID() {
		return occupazioneID;
	}



	public void setOccupazioneID(int occupazioneID) {
		this.occupazioneID = occupazioneID;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getCittadinanza() {
		return cittadinanza;
	}



	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}



	public String getRegione() {
		return regione;
	}



	public void setRegione(String regione) {
		this.regione = regione;
	}



	public String getTitoloStudio() {
		return titoloStudio;
	}



	public void setTitoloStudio(String titoloStudio) {
		this.titoloStudio = titoloStudio;
	}



	public String getOccupazione() {
		return occupazione;
	}



	public void setOccupazione(String occupazione) {
		this.occupazione = occupazione;
	}



	public int getTipoID() {
		return tipoID;
	}



	public void setTipoID(int tipoID) {
		this.tipoID = tipoID;
	}
	
	

}

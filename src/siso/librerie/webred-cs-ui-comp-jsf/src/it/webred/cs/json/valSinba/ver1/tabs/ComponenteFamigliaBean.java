package it.webred.cs.json.valSinba.ver1.tabs;

import org.apache.commons.lang3.StringUtils;

import it.webred.cs.json.valSinba.ver1.tabs.DatiFamigliaMan.TIPO_COMPONENTE;

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
		boolean genitore = TIPO_COMPONENTE.MADRE.codice == tipoID || TIPO_COMPONENTE.PADRE.codice== tipoID;
		boolean valorizzato = !StringUtils.isBlank(cittadinanzaID) && !StringUtils.isBlank(regioneID) && titoloStudioID !=0 && occupazioneID!=0;
		if((genitore && valorizzato)||(tipoID!=0 && !genitore))
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

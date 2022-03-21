package it.webred.cs.json.abitazione.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbitazioneBean extends JsonBaseBean {

	private String tipoAbitazione;
	private String numVani;
	private String titoloGodimento;
	private String proprietarioGestore;
	private String note;
	//#ROMA_CAPITALE
	private String struttura;
	private String area;
	private String unitaAbitativa;
	
	
	public List<String> checkObbligatorieta(){
		List<String> messages = new LinkedList<String>();
		//TODO: Implementare controlli
		return messages;
		
	}

	public String getTipoAbitazione() {
		return tipoAbitazione;
	}


	public String getTitoloGodimento() {
		return titoloGodimento;
	}


	public String getProprietarioGestore() {
		return proprietarioGestore;
	}


	public void setTipoAbitazione(String tipoAbitazione) {
		this.tipoAbitazione = tipoAbitazione;
	}

	public String getNumVani() {
		return numVani;
	}

	public void setNumVani(String numVani) {
		this.numVani = numVani;
	}

	public void setTitoloGodimento(String titoloGodimento) {
		this.titoloGodimento = titoloGodimento;
	}


	public void setProprietarioGestore(String proprietarioGestore) {
		this.proprietarioGestore = proprietarioGestore;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}

	public String getUnitaAbitativa() {
		return unitaAbitativa;
	}
public void setUnitaAbitativa(String unitaAbitativa) {
		this.unitaAbitativa = unitaAbitativa;
	}

	public String getStruttura() {
		return struttura;
	}

	public void setStruttura(String struttura) {
		this.struttura = struttura;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	
	


}

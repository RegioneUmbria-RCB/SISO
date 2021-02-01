package it.webred.rulengine.dto;

import java.io.Serializable;
import java.util.List;

public class ConfigurazioneEnte implements Serializable {
	
	private List enteProperties;
	private List enteFonteDati;
	
	//info supplementari su provenienza chiamata
	private Long idSched;  //id processo cheduler che chiama il comando

	
	public ConfigurazioneEnte(List enteProperties, List enteFonteDati) {
		super();
		this.enteProperties = enteProperties;
		this.enteFonteDati = enteFonteDati;
	}

	public List getEnteProperties() {
		return enteProperties;
	}

	public void setEnteProperties(List enteProperties) {
		this.enteProperties = enteProperties;
	}

	public List getEnteFonteDati() {
		return enteFonteDati;
	}

	public void setEnteFonteDati(List enteFonteDati) {
		this.enteFonteDati = enteFonteDati;
	}

	public Long getIdSched() {
		return idSched;
	}

	public void setIdSched(Long idSched) {
		this.idSched = idSched;
	}
	
	
}

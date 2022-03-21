package it.webred.ct.data.access.basic.segnalazionequalificata.dto;

import java.util.List;

import javax.faces.model.SelectItem;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class AmbitiAccertamentoSpecieDTO extends CeTBaseObject{
	private static final long serialVersionUID = 1L;
	
	private String specie;
	private List<SelectItem> ambiti;
	
	public AmbitiAccertamentoSpecieDTO(String specie, List<SelectItem> ambiti){
		this.specie = specie;
		this.ambiti = ambiti;
	}
	
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public void setAmbiti(List<SelectItem> ambiti) {
		this.ambiti = ambiti;
	}
	public List<SelectItem> getAmbiti() {
		return ambiti;
	}
}

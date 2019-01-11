package it.webred.cs.json.familiariConviventi.ver1;

import it.webred.cs.json.dto.JsonBaseBean;

import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FigliBean extends JsonBaseBean {

	private int numMaschi=0;
	private int numFemmine=0;
	
	@Override
	public FigliBean autoClone() throws Exception{
		return (FigliBean)super.autoClone(); //TODO: Implementare, altrimenti vengono copiati i soli riferimenti
	}
	
	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		//TODO: Implementare controlli
		return messages;
	}

	public int getNumMaschi() {
		return numMaschi;
	}

	public int getNumFemmine() {
		return numFemmine;
	}

	public void setNumMaschi(int numMaschi) {
		this.numMaschi = numMaschi;
	}

	public void setNumFemmine(int numFemmine) {
		this.numFemmine = numFemmine;
	}
	
	
	
}

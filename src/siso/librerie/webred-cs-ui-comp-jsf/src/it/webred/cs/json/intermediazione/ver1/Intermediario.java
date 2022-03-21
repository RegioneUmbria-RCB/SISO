package it.webred.cs.json.intermediazione.ver1;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.dto.KeyValueDTO;
import it.webred.jsf.bean.ComuneBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intermediario extends JsonBaseBean {

	/*Parametri List*/
	private boolean nessuno=false;
	private boolean annunci=false;
	private boolean amici=false;
	private boolean agenziaImmobiliare=false;
	private boolean altro=false;
	
	private boolean amiciStranieri=false;
	private String descAgenziaImm;
	private String descAltro;
	
	
	public Boolean getNessuno() {
		return nessuno;
	}

	public Boolean getAnnunci() {
		return annunci;
	}

	public Boolean getAmici() {
		return amici;
	}

	public Boolean getAgenziaImmobiliare() {
		return agenziaImmobiliare;
	}

	public Boolean getAltro() {
		return altro;
	}

	public Boolean getAmiciStranieri() {
		return amiciStranieri;
	}

	public String getDescAgenziaImm() {
		return descAgenziaImm;
	}

	public String getDescAltro() {
		return descAltro;
	}

	public void setNessuno(Boolean nessuno) {
		this.nessuno = nessuno;
	}

	public void setAnnunci(Boolean annunci) {
		this.annunci = annunci;
	}

	public void setAmici(Boolean amici) {
		this.amici = amici;
		if(!this.amici)
			this.amiciStranieri=false;
	}

	public void setAgenziaImmobiliare(Boolean agenziaImmobiliare) {
		this.agenziaImmobiliare = agenziaImmobiliare;
		if(!this.agenziaImmobiliare)
			this.descAgenziaImm=null;
	}

	public void setAltro(Boolean altro) {
		this.altro = altro;
		if(!this.altro)
			this.descAltro=null;
	}

	public void setAmiciStranieri(Boolean amiciStranieri) {
		this.amiciStranieri = amiciStranieri;
	}


	public void setDescAgenziaImm(String descAgenziaImm) {
		this.descAgenziaImm = descAgenziaImm;
	}



	public void setDescAltro(String descAltro) {
		this.descAltro = descAltro;
	}



	@Override
	public List<String> checkObbligatorieta() {
		List<String> messages = new LinkedList<String>();
		//TODO: Implementare controlli
		return messages;
	}
	
}

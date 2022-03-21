package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LingueStraniere implements Serializable {

	public List<LinguaStraniera> linguastraniera;

	public List<LinguaStraniera> getLinguastraniera() {
		return linguastraniera;
	}

	public void setLinguastraniera(List<LinguaStraniera> linguastraniera) {
		this.linguastraniera = linguastraniera;
	}
	
	
}

package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ConoscenzeInformatiche implements Serializable {

	private List<ConoscenzaInformatica> conoscenzainformatica;

	public List<ConoscenzaInformatica> getConoscenzainformatica() {
		return conoscenzainformatica;
	}

	public void setConoscenzainformatica(List<ConoscenzaInformatica> conoscenzainformatica) {
		this.conoscenzainformatica = conoscenzainformatica;
	}
	
	
}

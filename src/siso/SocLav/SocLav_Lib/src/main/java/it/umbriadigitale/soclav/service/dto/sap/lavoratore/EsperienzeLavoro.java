package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza.EsperienzaLavoro;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class EsperienzeLavoro implements Serializable {
	
	private List<EsperienzaLavoro> esperienzalavoro;

	public List<EsperienzaLavoro> getEsperienzalavoro() {
		return esperienzalavoro;
	}

	public void setEsperienzalavoro(List<EsperienzaLavoro> esperienzalavoro) {
		this.esperienzalavoro = esperienzalavoro;
	}
}

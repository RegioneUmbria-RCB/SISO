package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import it.umbriadigitale.soclav.service.dto.sap.lavoratore.politicaattiva.PoliticaAttiva;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class PoliticheAttive implements Serializable {
	
	private List<PoliticaAttiva> politiche_attive;

	public List<PoliticaAttiva> getPolitiche_attive() {
		return politiche_attive;
	}

	public void setPolitiche_attive(List<PoliticaAttiva> politiche_attive) {
		this.politiche_attive = politiche_attive;
	}

}

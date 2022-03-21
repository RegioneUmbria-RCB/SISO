package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class ListeSpeciali implements Serializable {
	
	private List<ListaSpeciale> listespeciali;

	public List<ListaSpeciale> getListespeciali() {
		return listespeciali;
	}

	public void setListespeciali(List<ListaSpeciale> listespeciali) {
		this.listespeciali = listespeciali;
	}
	
}

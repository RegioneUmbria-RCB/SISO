package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class LstTitoliStudio implements Serializable {

	private List<TitoliStudio> titolostudio;

	public List<TitoliStudio> getTitolostudio() {
		return titolostudio;
	}

	public void setTitolostudio(List<TitoliStudio> titolostudio) {
		this.titolostudio = titolostudio;
	}
}

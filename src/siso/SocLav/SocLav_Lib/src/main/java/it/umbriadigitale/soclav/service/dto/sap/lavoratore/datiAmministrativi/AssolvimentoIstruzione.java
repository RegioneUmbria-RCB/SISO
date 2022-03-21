package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class AssolvimentoIstruzione implements Serializable {

	/*
	 * <assolvimentoistruzione>
			<obbligoformativo>SI</obbligoformativo>
		</assolvimentoistruzione>    
	*/
	
	private String obbligoformativo;

	public String getObbligoformativo() {
		return obbligoformativo;
	}

	public void setObbligoformativo(String obbligoformativo) {
		this.obbligoformativo = obbligoformativo;
	}
	
	
}

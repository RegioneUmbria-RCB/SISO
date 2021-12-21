package it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class ModalitaLavoro implements Serializable {

	/*
	 * <modalitalavoro>    
		<codmodalitalavoro>FT</codmodalitalavoro>
	   </modalitalavoro>
	*/
	
	private String codmodalitalavoro;
	
	private String desmodalitalavoro;

	public String getCodmodalitalavoro() {
		return codmodalitalavoro;
	}

	public void setCodmodalitalavoro(String codmodalitalavoro) {
		this.codmodalitalavoro = codmodalitalavoro;
	}

	public String getDesmodalitalavoro() {
		return desmodalitalavoro;
	}

	public void setDesmodalitalavoro(String desmodalitalavoro) {
		this.desmodalitalavoro = desmodalitalavoro;
	}
}

package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class PeriodoDisoccupazione implements Serializable {

	/*
	 * <periodidisoccupazione>
			<dataingresso>2017-09-27</dataingresso>
			<tipoingresso>D</tipoingresso>
		</periodidisoccupazione>        
	 */
	
	private Date dataingresso;
	private String tipoingresso;
	
	public String destipoingresso;
	
	public Date getDataingresso() {
		return dataingresso;
	}
	public void setDataingresso(Date dataingresso) {
		this.dataingresso = dataingresso;
	}
	public String getTipoingresso() {
		return tipoingresso;
	}
	public void setTipoingresso(String tipoingresso) {
		this.tipoingresso = tipoingresso;
	}
	public String getDestipoingresso() {
		return destipoingresso;
	}
	public void setDestipoingresso(String destipoingresso) {
		this.destipoingresso = destipoingresso;
	}	
}

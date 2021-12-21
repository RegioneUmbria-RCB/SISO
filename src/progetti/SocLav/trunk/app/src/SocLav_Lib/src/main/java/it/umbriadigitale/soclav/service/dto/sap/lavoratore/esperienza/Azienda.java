package it.umbriadigitale.soclav.service.dto.sap.lavoratore.esperienza;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class Azienda implements Serializable {

	/*
	<azienda>    
	<codicefiscale>00433490422</codicefiscale>    
	<datorelavoro> Fileni Alimentare SpA</datorelavoro>    
	<indirizzoazienda>Località Cerrete Collicelli N° 8</indirizzoazienda>    
	<codateco>00.00.00</codateco>
	</azienda>
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String codicefiscale;
	private String datorelavoro;
	private String indirizzoazienda;
	private String codateco;
	
	private String desateco;
	
	public String getCodicefiscale() {
		return codicefiscale;
	}
	public void setCodicefiscale(String codicefiscale) {
		this.codicefiscale = codicefiscale;
	}
	public String getDatorelavoro() {
		return datorelavoro;
	}
	public void setDatorelavoro(String datorelavoro) {
		this.datorelavoro = datorelavoro;
	}
	public String getIndirizzoazienda() {
		return indirizzoazienda;
	}
	public void setIndirizzoazienda(String indirizzoazienda) {
		this.indirizzoazienda = indirizzoazienda;
	}
	public String getCodateco() {
		return codateco;
	}
	public void setCodateco(String codateco) {
		this.codateco = codateco;
	}
	public String getDesateco() {
		return desateco;
	}
	public void setDesateco(String desateco) {
		this.desateco = desateco;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

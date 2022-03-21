package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAmministrativi;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class ListaSpeciale implements Serializable {
	
	/*
	 * <listespeciali>    
			<tipolista>3</tipolista>    
			<dataiscrizione>2017-03-01</dataiscrizione>    
			<datafineiscrizione>2017-12-31</datafineiscrizione>    
			<codprovincia>043</codprovincia>
		</listespeciali>
	 */
	private String tipolista;
	private Date dataiscrizione;
	private Date datafineiscrizione;
	private String codprovincia;
	
	private String destipolista;
	private String desprovincia;
	
	public String getTipolista() {
		return tipolista;
	}
	public void setTipolista(String tipolista) {
		this.tipolista = tipolista;
	}
	public String getCodprovincia() {
		return codprovincia;
	}
	public void setCodprovincia(String codprovincia) {
		this.codprovincia = codprovincia;
	}
	public void setDataiscrizione(Date dataiscrizione) {
		this.dataiscrizione = dataiscrizione;
	}
	public void setDatafineiscrizione(Date datafineiscrizione) {
		this.datafineiscrizione = datafineiscrizione;
	}
	public Date getDataiscrizione() {
		return dataiscrizione;
	}
	public Date getDatafineiscrizione() {
		return datafineiscrizione;
	}
	public String getDestipolista() {
		return destipolista;
	}
	public void setDestipolista(String destipolista) {
		this.destipolista = destipolista;
	}
	public String getDesprovincia() {
		return desprovincia;
	}
	public void setDesprovincia(String desprovincia) {
		this.desprovincia = desprovincia;
	}
	
	
}

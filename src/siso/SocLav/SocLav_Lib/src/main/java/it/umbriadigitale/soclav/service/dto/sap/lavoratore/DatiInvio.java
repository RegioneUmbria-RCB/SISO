package it.umbriadigitale.soclav.service.dto.sap.lavoratore;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class DatiInvio implements Serializable {

	private Date dataultimoagg;
	private String identificativosap;
	private String codiceentetit;
	private String tipovariazione;
	private Date datadinascita;
	
	/*Decodifica*/
	private String desentetit;
	private String destipovariazione;
	
	public String getIdentificativosap() {
		return identificativosap;
	}
	public void setIdentificativosap(String identificativosap) {
		this.identificativosap = identificativosap;
	}
	public String getCodiceentetit() {
		return codiceentetit;
	}
	public void setCodiceentetit(String codiceentetit) {
		this.codiceentetit = codiceentetit;
	}
	public String getTipovariazione() {
		return tipovariazione;
	}
	public void setTipovariazione(String tipovariazione) {
		this.tipovariazione = tipovariazione;
	}
	public Date getDatadinascita() {
		return datadinascita;
	}
	public void setDatadinascita(Date datadinascita) {
		this.datadinascita = datadinascita;
	}
	public Date getDataultimoagg() {
		return dataultimoagg;
	}
	public void setDataultimoagg(Date dataultimoagg) {
		this.dataultimoagg = dataultimoagg;
	}
	public String getDesentetit() {
		return desentetit;
	}
	public void setDesentetit(String desentetit) {
		this.desentetit = desentetit;
	}
	public String getDestipovariazione() {
		return destipovariazione;
	}
	public void setDestipovariazione(String destipovariazione) {
		this.destipovariazione = destipovariazione;
	}
}

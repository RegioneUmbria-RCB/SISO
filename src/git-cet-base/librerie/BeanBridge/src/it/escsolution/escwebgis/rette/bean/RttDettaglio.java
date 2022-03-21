package it.escsolution.escwebgis.rette.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RttDettaglio extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String codBolletta;
	String dtIniServizio;
	String dtFinServizio;
	String desOggetto;
	String ubicazione;
	String categoria;
	String codVoce;
	String desVoce;
	String valore;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCodBolletta() {
		return codBolletta;
	}
	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}
	public String getDtIniServizio() {
		return dtIniServizio;
	}
	public void setDtIniServizio(String dtIniServizio) {
		this.dtIniServizio = dtIniServizio;
	}
	public String getDtFinServizio() {
		return dtFinServizio;
	}
	public void setDtFinServizio(String dtFinServizio) {
		this.dtFinServizio = dtFinServizio;
	}
	public String getDesOggetto() {
		return desOggetto;
	}
	public void setDesOggetto(String desOggetto) {
		this.desOggetto = desOggetto;
	}
	public String getUbicazione() {
		return ubicazione;
	}
	public void setUbicazione(String ubicazione) {
		this.ubicazione = ubicazione;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getCodVoce() {
		return codVoce;
	}
	public void setCodVoce(String codVoce) {
		this.codVoce = codVoce;
	}
	public String getDesVoce() {
		return desVoce;
	}
	public void setDesVoce(String desVoce) {
		this.desVoce = desVoce;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
}

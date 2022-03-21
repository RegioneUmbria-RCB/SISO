package it.escsolution.escwebgis.concessioni.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;
import java.util.ArrayList;

public class StoricoConcessioniIndirizzo extends EscObject implements Serializable {

	private String id;
	private String idExt;
	private String idOrig;
	private String sedime;
	private String indirizzo;
	private String civLiv1;
	private String indirizzoCatastale;
	private String indirizzoViarioRif;
	private ArrayList<StoricoConcessioniCatasto> datiCatastali;
	
	public String getChiave(){ 
		return id;
	}
	
	public String getIndirizzoCompleto() {
		String indirizzoCompleto = "";
		String sedime = getSedime() == null ? "" : getSedime();
		String indirizzo = getIndirizzo() == null ? "" : getIndirizzo();
		String civico = getCivLiv1() == null ? "" : getCivLiv1();
		indirizzoCompleto = sedime.trim();
		if (!indirizzo.trim().equals("")) {
			if (!indirizzoCompleto.trim().equals("")) {
				indirizzoCompleto += " ";
			}
			indirizzoCompleto += indirizzo;
		}
		if (!civico.trim().equals("")) {
			if (!indirizzoCompleto.trim().equals("")) {
				indirizzoCompleto += ", ";
			}
			indirizzoCompleto += civico;
		}
		return indirizzoCompleto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdOrig() {
		return idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getSedime() {
		return sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCivLiv1() {
		return civLiv1;
	}

	public void setCivLiv1(String civLiv1) {
		this.civLiv1 = civLiv1;
	}

	public String getIndirizzoCatastale() {
		return indirizzoCatastale;
	}

	public void setIndirizzoCatastale(String indirizzoCatastale) {
		this.indirizzoCatastale = indirizzoCatastale;
	}

	public String getIndirizzoViarioRif() {
		return indirizzoViarioRif;
	}

	public void setIndirizzoViarioRif(String indirizzoViarioRif) {
		this.indirizzoViarioRif = indirizzoViarioRif;
	}

	public ArrayList<StoricoConcessioniCatasto> getDatiCatastali() {
		return datiCatastali;
	}

	public void setDatiCatastali(ArrayList<StoricoConcessioniCatasto> datiCatastali) {
		this.datiCatastali = datiCatastali;
	}
	
	public void addDatiCatastali(StoricoConcessioniCatasto datoCatastale) {
		if (datiCatastali == null)
			datiCatastali = new ArrayList<StoricoConcessioniCatasto>();
		datiCatastali.add(datoCatastale);
	}
	
}

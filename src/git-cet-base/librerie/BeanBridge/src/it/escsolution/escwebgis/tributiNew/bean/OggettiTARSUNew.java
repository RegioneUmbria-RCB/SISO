package it.escsolution.escwebgis.tributiNew.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class OggettiTARSUNew extends EscObject implements Serializable {

	private String codEnte;
	private String comune;
	private String desEnte;
	private String chiave;
	private String idExt;
	private String desClsRsu;
	private String sez;
	private String foglio;
	private String numero;
	private String sub;
	private String supTot;
	private String datIni;
	private String datFin;
	private String tipOgg;
	private String desTipOgg;
	private String desInd;
	private String numCiv;
	private String espCiv;
	private String scala;
	private String piano;
	private String interno;
	private String tmsAgg;
	private String tmsBon;	
	private String provenienza;

	private boolean evidenzia;
	
	private String latitudine;
	private String longitudine;
	
	private String idVia;
	private String supCatasto;

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	 
	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getDesEnte() {
		return desEnte;
	}

	public void setDesEnte(String desEnte) {
		this.desEnte = desEnte;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}
	

	public String getIdExt() {
		return idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getDesClsRsu() {
		return desClsRsu;
	}

	public void setDesClsRsu(String desClsRsu) {
		this.desClsRsu = desClsRsu;
	}

	public String getSez() {
		return sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getSupTot() {
		return supTot;
	}

	public void setSupTot(String supTot) {
		this.supTot = supTot;
	}

	public String getDatIni() {
		return datIni;
	}

	public void setDatIni(String datIni) {
		this.datIni = datIni;
	}

	public String getDatFin() {
		return datFin;
	}

	public void setDatFin(String datFin) {
		this.datFin = datFin;
	}

	public String getTipOgg() {
		return tipOgg;
	}

	public void setTipOgg(String tipOgg) {
		this.tipOgg = tipOgg;
	}

	public String getDesTipOgg() {
		return desTipOgg;
	}

	public void setDesTipOgg(String desTipOgg) {
		this.desTipOgg = desTipOgg;
	}

	public String getDesInd() {
		return desInd;
	}

	public void setDesInd(String desInd) {
		this.desInd = desInd;
	}

	public String getNumCiv() {
		return numCiv;
	}

	public void setNumCiv(String numCiv) {
		this.numCiv = numCiv;
	}

	public String getEspCiv() {
		return espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
	}

	public String getScala() {
		return scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getPiano() {
		return piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public String getInterno() {
		return interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public String getTmsAgg() {
		return tmsAgg;
	}

	public void setTmsAgg(String tmsAgg) {
		this.tmsAgg = tmsAgg;
	}

	public String getTmsBon() {
		return tmsBon;
	}

	public void setTmsBon(String tmsBon) {
		this.tmsBon = tmsBon;
	}

	public boolean isEvidenzia() {
		return evidenzia;
	}

	public void setEvidenzia(boolean evidenzia) {
		this.evidenzia = evidenzia;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(String latitudine) {
		this.latitudine = latitudine;
	}

	public String getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(String longitudine) {
		this.longitudine = longitudine;
	}

	public String getIdVia() {
		return idVia;
	}

	public void setIdVia(String idVia) {
		this.idVia = idVia;
	}
	
	public String getSupCatasto() {
		return supCatasto;
	}

	public void setSupCatasto(String supCatasto) {
		this.supCatasto = supCatasto;
	}
	
	public String getIdFonte() {
		return "2";
	}
	
	public String getTipoFonte() {
		return "OGGETTI TARSU";
	}

	public String getDiaKey() {
		if (diaKey != null && !diaKey.equals("")) {
			return diaKey;
		}
		diaKey = "";
		if (foglio != null && !foglio.equals("") && numero!=null && !numero.equals("")) {
			diaKey += lpad(foglio,4)+"|"+lpad(numero,5);
		}
		return diaKey;
	}
	
	private String lpad(String s, int num){
		while(s.length()!=num)
			s = "0"+s;
			
		return s;
	}
	
	
}

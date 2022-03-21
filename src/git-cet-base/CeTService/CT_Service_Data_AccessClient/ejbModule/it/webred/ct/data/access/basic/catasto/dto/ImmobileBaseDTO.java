package it.webred.ct.data.access.basic.catasto.dto;

import it.webred.ct.data.model.catasto.Sitiuiu;

import java.io.Serializable;
import java.util.Date;

public class ImmobileBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String codNazionale;
	private String codiFiscLuna;
	private String sez;
	private String foglio;
	private String particella;
	private String sub;
	private String unimm;
	private boolean graffato;
	
	private Date dataFineVal;
	private Date dataInizioVal;
	
	private ImmobileBaseDTO principale; //Unità principale, se la corrente è graffata
	private String lstGraffati;
	private String lstStorico;
	
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
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getUnimm() {
		return unimm;
	}
	
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCodNazionale() {
		return codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public String getCodiFiscLuna() {
		return codiFiscLuna;
	}
	public void setCodiFiscLuna(String codiFiscLuna) {
		this.codiFiscLuna = codiFiscLuna;
	}
	public Date getDataFineVal() {
		return dataFineVal;
	}
	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public Date getDataInizioVal() {
		return dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	public ImmobileBaseDTO getPrincipale() {
		return principale;
	}
	public void setPrincipale(ImmobileBaseDTO principale) {
		this.principale = principale;
	}
	public boolean isGraffato() {
		return graffato;
	}
	public void setGraffato(boolean graffato) {
		this.graffato = graffato;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getLstGraffati() {
		return lstGraffati;
	}
	public void setLstGraffati(String lstGraffati) {
		this.lstGraffati = lstGraffati;
	}
	public String getLstStorico() {
		return lstStorico;
	}
	public void setLstStorico(String lstStorico) {
		this.lstStorico = lstStorico;
	}
	
}

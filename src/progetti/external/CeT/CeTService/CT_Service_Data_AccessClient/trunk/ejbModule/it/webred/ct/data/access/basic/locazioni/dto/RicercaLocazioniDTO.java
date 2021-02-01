package it.webred.ct.data.access.basic.locazioni.dto;

import it.webred.ct.data.model.locazioni.LocazioneBPK;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class RicercaLocazioniDTO extends CeTBaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	LocazioneBPK key ;
	private String codFis;
	private Date dtRif;
	
	private Date dtRifIni;
	private Date dtRifFin;
	
	private String sezione = "";
	private String foglio = "";
	private String particella = "";
	private String subalterno = "";
	
	private String tipoCoinvolgimento = "";
	
	private Integer limit = 0;
	
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public Date getDtRif() {
		return dtRif;
	}
	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}
	public LocazioneBPK getKey() {
		return key;
	}
	public void setKey(LocazioneBPK key) {
		this.key = key;
	}
	public void setDtRifFin(Date dtRifFin) {
		this.dtRifFin = dtRifFin;
	}
	public Date getDtRifFin() {
		return dtRifFin;
	}
	public void setDtRifIni(Date dtRifIni) {
		this.dtRifIni = dtRifIni;
	}
	public Date getDtRifIni() {
		return dtRifIni;
	}
	public String getSezione() {
		return sezione;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
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
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getTipoCoinvolgimento() {
		return tipoCoinvolgimento;
	}
	public void setTipoCoinvolgimento(String tipoCoinvolgimento) {
		this.tipoCoinvolgimento = tipoCoinvolgimento;
	}



}

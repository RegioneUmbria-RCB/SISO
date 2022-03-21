package it.webred.ct.data.access.basic.imu.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.Date;

public class SaldoImuBaseDTO extends CeTBaseObject{

	private String codfisc;
	private String progressivo;
	private Date dtIns;
	private String dati;
	
	public String getCodfisc() {
		return codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public String getDati() {
		return dati;
	}
	public void setDati(String dati) {
		this.dati = dati;
	}
	
}

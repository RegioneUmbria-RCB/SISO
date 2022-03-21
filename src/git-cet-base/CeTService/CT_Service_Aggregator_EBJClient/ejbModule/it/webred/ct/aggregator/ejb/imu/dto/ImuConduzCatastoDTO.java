package it.webred.ct.aggregator.ejb.imu.dto;

import java.io.Serializable;

public class ImuConduzCatastoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tipImm="";
	private String tipSogg="";
	private String codFisc="";
	private String denom="";
	private String datNas="";
	private String sede="";   //Cod.Belfiore
	private String tit="";
	private String percPoss="";
	private String dataInizioTit="";
    private String dataFineTit="";
	
	public String getPercPoss() {
		return percPoss;
	}

	public void setPercPoss(String percPoss) {
		this.percPoss = percPoss;
	}

    public String stampaRecord(){
		String r =  tipImm+"|"+tipSogg +"|"+ codFisc+"|"+denom+"|"+datNas+"|"+sede+"|"+tit+"|"+percPoss+"|"+dataInizioTit+"|"+dataFineTit;
		return r;
	}

	public String getTipSogg() {
		return tipSogg;
	}


	public String getCodFisc() {
		return codFisc;
	}


	public String getDenom() {
		return denom;
	}


	public String getDatNas() {
		return datNas;
	}


	public String getTit() {
		return tit;
	}


	public String getDataInizioTit() {
		return dataInizioTit;
	}


	public String getDataFineTit() {
		return dataFineTit;
	}


	public void setTipSogg(String tipSogg) {
		this.tipSogg = tipSogg;
	}


	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}


	public void setDenom(String denom) {
		this.denom = denom;
	}


	public void setDatNas(String datNas) {
		this.datNas = datNas;
	}

	public void setTit(String tit) {
		this.tit = tit;
	}

	public void setDataInizioTit(String dataInizioTit) {
		this.dataInizioTit = dataInizioTit;
	}


	public void setDataFineTit(String dataFineTit) {
		this.dataFineTit = dataFineTit;
	}

	public String getTipImm() {
		return tipImm;
	}

	public void setTipImm(String tipImm) {
		this.tipImm = tipImm;
	}

	public String getSede() {
		return sede;
	}

	public void setSede(String sede) {
		this.sede = sede;
	}

	
}

package it.webred.rulengine.brick.elab.demog;

import java.sql.Date;

	public class SitDPersFamSt  {
	private static final long serialVersionUID = 1L;

	private String idExtDFamiglia;
	private String idExtDPersona;
	private String relazPar;
	private Date dtInizioDato;
	private Date dtFineDato;
	
	public SitDPersFamSt(SitDPersFamSt pf){
		this.idExtDPersona = pf.getIdExtDPersona();
	}
	
	public SitDPersFamSt(){
		
	}
	
	public String getIdExtDFamiglia() {
		return idExtDFamiglia;
	}
	public void setIdExtDFamiglia(String idExtDFamiglia) {
		this.idExtDFamiglia = idExtDFamiglia;
	}
	public String getIdExtDPersona() {
		return idExtDPersona;
	}
	public void setIdExtDPersona(String idExtDPersona) {
		this.idExtDPersona = idExtDPersona;
	}
	public String getRelazPar() {
		return relazPar;
	}
	public void setRelazPar(String relazPar) {
		this.relazPar = relazPar;
	}
	public Date getDtInizioDato() {
		return dtInizioDato;
	}
	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}
	public Date getDtFineDato() {
		return dtFineDato;
	}
	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
		
		
	}
	

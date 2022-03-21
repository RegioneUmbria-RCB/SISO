package it.webred.rulengine.brick.idoneitaAlloggi.bean;

import java.io.Serializable;

public class DatiTarsu implements Serializable {
	
	private static final long serialVersionUID = -8912647943970741475L;
	
	private String codFisPIva = "";
	private double supTot =  0d;;
	private String denominazione = "";
	
	public DatiTarsu() {
	
	}//-------------------------------------------------------------------------

	public String getCodFisPIva() {
		return codFisPIva;
	}

	public void setCodFisPIva(String codFisPIva) {
		this.codFisPIva = codFisPIva;
	}

	public double getSupTot() {
		return supTot;
	}

	public void setSupTot(double supTot) {
		this.supTot = supTot;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	
	
}

